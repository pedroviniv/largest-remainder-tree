/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.alfaconsultoria.sogo.commons.largest.remainder.custom;

import java.math.BigDecimal;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class Node {
    
    private BigDecimal first;
    private BigDecimal second;
    
    public static Node node(final BigDecimal first, final BigDecimal second) {
        return new Node(first, second);
    }
    
    public static Node node(final Integer first, final Integer second) {
        return new Node(BigDecimal.valueOf(first), BigDecimal.valueOf(second));
    }

    public Node(BigDecimal first, BigDecimal second) {
        this.first = first;
        this.second = second;
    }

    public BigDecimal getFirst() {
        return first;
    }

    public BigDecimal getSecond() {
        return second;
    }
    
    public void addToFirst(final BigDecimal value) {
        this.first = this.first.add(value);
    }
    
    public void addToSecond(final BigDecimal value) {
        this.second = this.second.add(value);
    }

    @Override
    public String toString() {
        return "{" + first + ", " + second + '}';
    }
}
