/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.alfaconsultoria.sogo.commons.largest.remainder.custom;
import static br.com.alfaconsultoria.sogo.commons.largest.remainder.custom.Node.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class Loader {
    
    public static void main(String[] args) {
        
        final Adjuster adjuster = new Adjuster();
        final List<Node> adjusted = adjuster.adjust(
                55, 
                Arrays.asList(
                        30,
                        69
                ),
                Arrays.asList(
                        node(16, 29),
                        node(39, 70)
                )
        );
        
        System.out.println("adjusted: " + adjusted);
    }
}
