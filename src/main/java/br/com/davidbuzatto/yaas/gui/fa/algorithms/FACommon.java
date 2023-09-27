/*
 * Copyright (C) 2023 Prof. Dr. David Buzatto
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.davidbuzatto.yaas.gui.fa.algorithms;

import br.com.davidbuzatto.yaas.gui.fa.FA;
import br.com.davidbuzatto.yaas.gui.fa.FAState;
import br.com.davidbuzatto.yaas.gui.fa.FATransition;
import br.com.davidbuzatto.yaas.gui.fa.FAType;
import static br.com.davidbuzatto.yaas.gui.fa.algorithms.FAAlgorithmsConstants.DEBUG;
import br.com.davidbuzatto.yaas.util.SigmaStarGeneratorStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Common algorithms for reuse.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FACommon {
    
    /**
     * Acceptance algorithm for a DFA, using a custom initialState.
     * 
     * @param str The string to be tested.
     * @param dfa The DFA to be processed.
     * @param initialState The initial state to use (regardless the current
     * DFA initial state)
     * @return true if the string is accepted, false otherwise.
     */
    public static boolean acceptsDFA( String str, FA dfa, FAState initialState )
            throws IllegalArgumentException {
        
        if ( dfa.getType() != FAType.DFA ) {
            throw new IllegalArgumentException( "You must use an DFA!" );
        }
        
        FAState current = initialState;
        Map<FAState, Map<Character, List<FAState>>> delta = dfa.getDelta();
        
        for ( char c : str.toCharArray() ) {
            List<FAState> target = delta.get( current ).get( c );
            if ( target != null ) {
                current = target.get( 0 );
            } else {
                return false;
            }
        }
        
        return current.isAccepting();
        
    }
    
    /**
     * Verifies if a state is inaccessible.
     * 
     * @param state The state to verify.
     * @param fa The automaton containing the state that will be verified.
     * @return true if the state is inaccessible, false otherwise.
     */
    public static boolean isInaccessible( FAState state, FA fa ) {
        
        if ( state.isInitial() ) {
            return false;
        }
        
        for ( FATransition t : fa.getTransitions() ) {
            if ( state.equals(  t.getTargetState() ) ) {
                return false;
            }
        }
        
        return true;
        
    }
    
    /**
     * Verifies if two have the same transition function to a next state
     * with a input of one symbol. It will consider if the states have 
     * different symbols to in their transitions.
     * 
     * @param s1 One FA state.
     * @param s2 Another FA state.
     * @param dfa The automaton containing the states that will be verified.
     * @return true if the states have the same transition function,
     * false otherwise.
     */
    public static boolean haveSameDeltaWithOneSymbol( 
            FAState s1, 
            FAState s2,
            FA dfa ) throws IllegalArgumentException {
        
        if ( dfa.getType() != FAType.DFA ) {
            throw new IllegalArgumentException( "You must use an DFA!" );
        }
        
        Map<FAState, Map<Character, List<FAState>>> delta = dfa.getDelta();
        Map<Character, Boolean> t1 = new HashMap<>();
        Map<Character, Boolean> t2 = new HashMap<>();
        
        // assumes DFA
        for ( Map.Entry<Character, List<FAState>> e : delta.get( s1 ).entrySet() ) {
            t1.put( e.getKey(), e.getValue().get( 0 ).isAccepting() );
        }
        for ( Map.Entry<Character, List<FAState>> e : delta.get( s2 ).entrySet() ) {
            t2.put( e.getKey(), e.getValue().get( 0 ).isAccepting() );
        }
        
        return t1.equals( t2 );
        
    }
    
    /**
     * Verifies if two states are distinguishable.
     * 
     * @param s1 One FA state.
     * @param s2 Another FA state.
     * @param dfa The automaton containing the states that will be verified.
     * @return true if the states are distinguishable, false otherwise.
     */
    public static boolean isDistinguishable( 
            FAState s1, 
            FAState s2,
            FA dfa ) throws IllegalArgumentException {
        
        boolean result = false;
        
        if ( dfa.getType() != FAType.DFA ) {
            throw new IllegalArgumentException( "You must use an DFA!" );
        }
        
        Map<FAState, Map<Character, List<FAState>>> delta = dfa.getDelta();
        
        // assumes s1 and s2 have the same transition function with one symbol
        Set<Character> alphabet = new TreeSet<>( delta.get( s1 ).keySet() );
        alphabet.addAll( delta.get( s2 ).keySet() ); // should not add any symbol
        
        if ( !alphabet.isEmpty() ) {
            
            // generate strings starting with length = 2
            SigmaStarGeneratorStream ssgs = new SigmaStarGeneratorStream( alphabet, 2 );

            // max length => n-2, n being the number os states of the DFA
            int maxLength = dfa.getStates().size() - 2;
            
            if ( DEBUG ) {
                System.out.println( "    Testing " + s1 + " and " + s2 + ":" );
            }
            
            while ( true ) {
                String str = ssgs.next();
                if ( str.length() > maxLength ) {
                    result = false;
                    break;
                } else {
                    if ( DEBUG ) {
                        System.out.print( "        " + str + ": " );
                    }
                    if ( acceptsDFA( str, dfa, s1 ) != acceptsDFA( str, dfa, s2 ) ) {
                        result = true;
                        if ( DEBUG ) {
                            System.out.println( "don't match." );
                        }
                        break;
                    } else if ( DEBUG ) {
                        System.out.println( "match." );
                    }
                }
            }
            
        }
        
        return result;
        
    }
    
    /**
     * Generates 702 custom labels (A, B, C, ... , ZX, ZY and ZZ).
     * 
     * @param index The index of the custom label 0 through 701
     * @return The custom label.
     */
    public static String newCustomLabel( int index ) {
        
        if ( index < 0 || index > 701 ) {
            return "";
        }
        
        int d = index / 26;
        int u = index % 26;
        
        if ( d != 0 ) {
            return String.valueOf( (char) ('A' + (d-1) ) ) +
                   String.valueOf( (char) ('A' + u) );
        } else {
            return String.valueOf( (char) ('A' + u) );
        }
            
    }
    
    /**
     * Validades if both Finite Automata have an initial state.
     * 
     * @param fa1 One Finite Automaton
     * @param fa2 Another Finite Automaton
     * @throws IllegalArgumentException If at least one of the Finite Automata
     * don't have an initial state.
     */
    public static void validateInitialState( FA fa1, FA fa2 ) throws IllegalArgumentException {
        
        if ( fa1.getInitialState() == null ) {
            throw new IllegalArgumentException( 
                    "The first Finite Automata must have an initial state!" );
        }
        
        if ( fa2.getInitialState() == null ) {
            throw new IllegalArgumentException( 
                    "The second Finite Automata must have an initial state!" );
        }
        
    }
    
    /**
     * Validades if both Finite Automata have at least one accepting state.
     * 
     * @param fa1 One Finite Automaton
     * @param fa2 Another Finite Automaton
     * @throws IllegalArgumentException If at least one of the Finite Automata
     * don't have at least one accepting state.
     */
    public static void validateAcceptingStates( FA fa1, FA fa2 ) throws IllegalArgumentException {
        
        if ( fa1.getAcceptingStates().isEmpty() ) {
            throw new IllegalArgumentException( 
                    "The first Finite Automata must have at least one accepting state!" );
        }
        
        if ( fa2.getAcceptingStates().isEmpty() ) {
            throw new IllegalArgumentException( 
                    "The second Finite Automata must have at least one accepting state!" );
        }
        
    }
    
}
