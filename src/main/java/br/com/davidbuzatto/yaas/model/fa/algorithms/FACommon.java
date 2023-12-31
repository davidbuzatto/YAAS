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
package br.com.davidbuzatto.yaas.model.fa.algorithms;

import br.com.davidbuzatto.yaas.model.fa.FA;
import br.com.davidbuzatto.yaas.model.fa.FAState;
import br.com.davidbuzatto.yaas.model.fa.FAType;
import br.com.davidbuzatto.yaas.util.SigmaStarGeneratorStream;
import br.com.davidbuzatto.yaas.util.Utils;
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
    
    private static final boolean DEBUG = Boolean.parseBoolean( 
            Utils.getMavenModel().getProperties().getProperty( "debugAlgorithms" ) );
    
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
        
        return current.isFinal();
        
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
            t1.put( e.getKey(), e.getValue().get( 0 ).isFinal() );
        }
        for ( Map.Entry<Character, List<FAState>> e : delta.get( s2 ).entrySet() ) {
            t2.put( e.getKey(), e.getValue().get( 0 ).isFinal() );
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
                    
                    boolean accS1 = acceptsDFA( str, dfa, s1 );
                    boolean accS2 = acceptsDFA( str, dfa, s2 );
                    
                    if ( DEBUG ) {
                        System.out.println( "        " + s1 + " " + str + ": " + accS1 );
                        System.out.println( "        " + s2 + " " + str + ": " + accS2 );
                        System.out.println( "        " + ( accS1 != accS2 ? "don't match" : "match" ) + "\n" );
                    }
                    
                    if ( accS1 != accS2 ) {
                        result = true;
                        break;
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
     * Validades if a Finite Automaton have an initial state.
     * 
     * @param fa The Finite Automaton
     * @throws IllegalArgumentException If the Finite Automaton don't have an
     * initial state.
     */
    public static void validateInitialState( FA fa ) throws IllegalArgumentException {
        
        if ( fa.getInitialState() == null ) {
            throw new IllegalArgumentException( 
                    "The Finite Automaton must have an initial state!" );
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
                    "The first Finite Automaton must have an initial state!" );
        }
        
        if ( fa2.getInitialState() == null ) {
            throw new IllegalArgumentException( 
                    "The second Finite Automaton must have an initial state!" );
        }
        
    }
    
    /**
     * Validades if a Finite Automaton have at least one final state.
     * 
     * @param fa The Finite Automaton
     * @throws IllegalArgumentException If the Finite Automata don't have at
     * least one final state.
     */
    public static void validateFinalStates( FA fa ) throws IllegalArgumentException {
        
        if ( fa.getFinalStates().isEmpty() ) {
            throw new IllegalArgumentException( 
                    "The Finite Automaton must have at least one final state!" );
        }
        
    }
    
    /**
     * Validades if both Finite Automata have at least one final state.
     * 
     * @param fa1 One Finite Automaton
     * @param fa2 Another Finite Automaton
     * @throws IllegalArgumentException If at least one of the Finite Automata
     * don't have at least one final state.
     */
    public static void validateFinalStates( FA fa1, FA fa2 ) throws IllegalArgumentException {
        
        if ( fa1.getFinalStates().isEmpty() ) {
            throw new IllegalArgumentException( 
                    "The first Finite Automaton must have at least one final state!" );
        }
        
        if ( fa2.getFinalStates().isEmpty() ) {
            throw new IllegalArgumentException( 
                    "The second Finite Automaton must have at least one final state!" );
        }
        
    }
    
    /**
     * Validades if a Finite Automaton is a DFA.
     * 
     * @param fa The Finite Automaton
     * @throws IllegalArgumentException If the Finite Automata is not a DFA.
     */
    public static void validateDFA( FA fa ) throws IllegalArgumentException {
        
        fa.updateType();
        
        if ( fa.getType() != FAType.DFA ) {
            throw new IllegalArgumentException( 
                    "The Finite Automaton must be a DFA!" );
        }
        
    }
    
    /**
     * Validades if both Finite Automata area a DFA.
     * 
     * @param fa1 One Finite Automaton
     * @param fa2 Another Finite Automaton
     * @throws IllegalArgumentException If at least one of the Finite Automata
     * is not a DFA.
     */
    public static void validateDFA( FA fa1, FA fa2 ) throws IllegalArgumentException {
        
        fa1.updateType();
        fa2.updateType();
        
        if ( fa1.getType() != FAType.DFA ) {
            throw new IllegalArgumentException( 
                    "The first Finite Automaton must be a DFA!" );
        }
        
        if ( fa2.getType() != FAType.DFA ) {
            throw new IllegalArgumentException( 
                    "The second Finite Automaton must be a DFA!" );
        }
        
    }
    
    /**
     * Set the states number in order. The initial state is the state with
     * number 0. The other will reenumerate in appearance order inside the state
     * list.
     * 
     * @param fa The Finite Automaon with the states to be processed.
     */
    public static void reenumerateStates( FA fa ) {
        
        int currentState = 1;
        for ( FAState s : fa.getStates() ) {
            if ( s.isInitial() ) {
                s.setNumber( 0 );
            } else {
                s.setNumber( currentState++ );
            }
        }
        
    }
    
}
