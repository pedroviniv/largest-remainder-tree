/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.alfaconsultoria.sogo.commons.largest.remainder.custom;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import static java.util.stream.Collectors.toList;
import java.util.stream.IntStream;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class Adjuster {
    
    public static class Correction {
        
        private Node toCorrect;
        private BigDecimal expectedSecond;
        private int index;

        public Correction(Node toCorrect, BigDecimal expectedSecond, int index) {
            this.toCorrect = toCorrect;
            this.expectedSecond = expectedSecond;
            this.index = index;
        }
        
        /**
         * increments (or decrements depending on the value sign) a 
         * value into {@link Node#getFirst()}.
         * @param value 
         */
        public void addToFirst(final BigDecimal value) {
            this.toCorrect.addToFirst(value);
        }

        /**
         * increments (or decrements depending on the value sign) a 
         * value into {@link Node#getSecond()}.
         * @param value 
         */
        public void addToSecond(final BigDecimal value) {
            this.toCorrect.addToSecond(value);
        }

        /**
         * {@link Node} that must be mutated in order to his second number
         * correspond to the {@link Correction#getExpectedSecond() }.
         * @return 
         */
        public Node getToCorrect() {
            return toCorrect;
        }

        /**
         * {@link Node#getSecond()} expected value.
         * @return 
         */
        public BigDecimal getExpectedSecond() {
            return expectedSecond;
        }

        /**
         * index of {@link Node} in the input list.
         * @return 
         */
        public int getIndex() {
            return index;
        }
        
        /**
         * difference between {@link Correction#expectedSecond} and {@link Node#getSecond()}.
         * This implementation uses {@link Comparable#compareTo(java.lang.Object) } to get the difference
         * between both numbers.
         * @return 
         */
        public BigDecimal getSecondDifference() {
            return BigDecimal.valueOf(this.expectedSecond.compareTo(toCorrect.getSecond()));
        }

        @Override
        public String toString() {
            return "Correction{" + "toCorrect=" + toCorrect + ", expectedSecond=" + expectedSecond + ", index=" + index + '}';
        }
    }
    
    public List<Node> adjust(final Integer expectedFirstsSum,
            final List<Integer> expectedSeconds, final List<Node> input) {
        
        final List<BigDecimal> expectedSecondsBigDecimal = expectedSeconds.stream()
                .map(BigDecimal::valueOf)
                .collect(toList());
        
        return this.adjust(BigDecimal.valueOf(expectedFirstsSum),
                expectedSecondsBigDecimal, input);
    }
    
    /**
     * adjusts the input list, mutating each {@link Node#getFirst()} and {@link Node#getFirst()}
     * in order that each {@link Node#getSecond()} correspond to each expectedSeconds AND the sum of all {@link Node#getFirst()}
     * keep equal to expectedFirstSum.
     * 
     * @param expectedFirstsSum
     * @param expectedSeconds
     * @param input
     * @return 
     */
    public List<Node> adjust(final BigDecimal expectedFirstsSum,
            final List<BigDecimal> expectedSeconds, final List<Node> input) {
        
        final List<Correction> corrections = IntStream.range(0, input.size())
                .mapToObj(index -> {
                    
                    final BigDecimal expectedSecond = expectedSeconds.get(index);
                    final Node node = input.get(index);
                    final Boolean isEqual = node.getSecond().equals(expectedSecond);
                    
                    if (isEqual) {
                        return null;
                    }
                    
                    return new Correction(node, expectedSecond, index);
                })
                .filter(Objects::nonNull)
                .collect(toList());
        
        BigDecimal lastCorrection = null;
        
        for (final Correction correction : corrections) {
            
            if (lastCorrection != null) {
                final BigDecimal inverseCorrection = lastCorrection.multiply(BigDecimal.valueOf(-1));
                correction.addToFirst(inverseCorrection);
                correction.addToSecond(inverseCorrection);
            }
            
            final BigDecimal secondDifference = correction.getSecondDifference();
            if (secondDifference.equals(BigDecimal.ZERO)) {
                continue;
            }
            
            correction.addToFirst(secondDifference);
            correction.addToSecond(secondDifference);
            
            lastCorrection = secondDifference;
        }
        
        return input;
    }
}
