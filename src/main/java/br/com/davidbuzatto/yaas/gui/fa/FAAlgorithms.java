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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.davidbuzatto.yaas.gui.fa;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

/**
 * A set of utilitary methods to perform algorithms related to Finite Automata.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAAlgorithms {
    
    /**
     * A helper class that encapsulates data from existing states while the
     * automaton is being processed.
     */
    private static class HelperS {
        
        public HelperS( Set<FAState> states ) {
            this( null, states );
        }
        
        public HelperS( FAState state, Set<FAState> states ) {
            this.state = state;
            this.states = states;
        }
        
        FAState state;
        Set<FAState> states;

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 61 * hash + Objects.hashCode( this.states );
            return hash;
        }

        @Override
        public boolean equals( Object obj ) {
            if ( this == obj ) {
                return true;
            }
            if ( obj == null ) {
                return false;
            }
            if ( getClass() != obj.getClass() ) {
                return false;
            }
            final HelperS other = (HelperS) obj;
            return Objects.equals( this.states, other.states );
        }

        @Override
        public String toString() {
            return "CState{" + "state=" + state + ", states=" + states + '}';
        }
        
    }
    
    /**
     * A helper class that encapsulates data from existing transitions while the
     * automaton is being processed.
     */
    private static class HelperT {
        
        HelperS originState;
        HelperS targetState;
        Set<Character> symbols;
        
        public HelperT( HelperS originState, HelperS targetState, Character symbol ) {
            this.originState = originState;
            this.targetState = targetState;
            this.symbols = new HashSet<>();
            this.symbols.add( symbol );
        }
        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode( this.originState );
            hash = 97 * hash + Objects.hashCode( this.targetState );
            return hash;
        }

        @Override
        public boolean equals( Object obj ) {
            if ( this == obj ) {
                return true;
            }
            if ( obj == null ) {
                return false;
            }
            if ( getClass() != obj.getClass() ) {
                return false;
            }
            final HelperT other = (HelperT) obj;
            if ( !Objects.equals( this.originState, other.originState ) ) {
                return false;
            }
            return Objects.equals( this.targetState, other.targetState );
        }

        @Override
        public String toString() {
            return String.format( "(%s) - %s -> (%s)",
                originState, symbols, targetState );
        }
        
    }
    
    /**
     * Generates a new Finite Automaton without any non-determinisms
     * 
     * @param nfa The Finite Automaton to be processed.
     * @return A new Finite Automaton without any non-determinisms.
     */
    public static FA removeNonDeterminisms( FA nfa ) {
        
        FA dfa = new FA();
        int currentState = 0;
        
        // get data from the original automaton
        Set<Character> alphabet = nfa.getAlphabet();
        FAState eInitialState = nfa.getInitialState();
        List<FAState> eAcceptinStates = nfa.getAcceptingStates();
        Map<FAState, Map<Character, List<FAState>>> eDelta = nfa.getDelta();
        Map<FAState, Set<FAState>> eEcloses = nfa.getEcloses( eDelta );
        
        // start the process of generation
        
        // creates the new initial state
        FAState initial = new FAState();
        initial.setInitial( true );
        initial.setLabel( "q" + currentState );
        initial.setCustomLabel( newCustomLabel( currentState++ ) );
        HelperS cInitial = new HelperS( initial, eEcloses.get( eInitialState ) );
        
        // data strutctures to store transitions and states that will be generated
        Set<HelperT> generatedTransitions = new LinkedHashSet<>();
        Set<HelperS> generatedStates = new LinkedHashSet<>();
        generatedStates.add( cInitial );
        
        // a queue to process the generated states
        Queue<HelperS> queue = new ArrayDeque<>();
        queue.add( cInitial );
        
        // on demand process of new states
        while ( !queue.isEmpty() ) {
            
            HelperS current = queue.poll();
            
            for ( Character a : alphabet ) {
                
                Set<FAState> targetEclose = new HashSet<>();
                
                for ( FAState s : current.states ) {
                    
                    List<FAState> t = eDelta.get( s ).get( a );
                    if ( t != null && !t.isEmpty() ) {
                        for ( FAState ns : t ) {
                            targetEclose.addAll( eEcloses.get( ns ) );
                        }
                    }

                }
                
                if ( !targetEclose.isEmpty() ) {

                    HelperS newCState = new HelperS( targetEclose );
                    
                    if ( !generatedStates.contains( newCState ) ) {
                        
                        FAState newState = new FAState();
                        newState.setLabel( "q" + currentState );
                        newState.setCustomLabel( newCustomLabel( currentState++ ) );
                        newCState.state = newState;
                        generatedStates.add( newCState );
                        queue.add( newCState );
                        
                    }
                    
                    HelperT newTransition = new HelperT( current, newCState, a );
                    if ( generatedTransitions.contains( newTransition ) ) {
                        for ( HelperT t : generatedTransitions ) {
                            if ( t.equals( newTransition ) ) {
                                t.symbols.addAll( newTransition.symbols );
                            }
                        }
                    } else {
                        generatedTransitions.add( newTransition );
                    }

                }

            }
            
        }
        
        for ( HelperS s : generatedStates ) {
            for ( FAState a : eAcceptinStates ) {
                if ( s.states.contains( a ) ) {
                    s.state.setAccepting( true );
                    break;
                }
            }
        }
        
        for ( HelperS s : generatedStates ) {
            dfa.addState( s.state );
        }
        
        Map<Set<FAState>, FAState> m = new HashMap<>();
        for ( HelperT t : generatedTransitions ) {
            m.put( t.originState.states, t.originState.state );
        }
        
        for ( HelperT t : generatedTransitions ) {
            t.targetState.state = m.get( t.targetState.states );
            List<Character> symbols = new ArrayList<>();
            symbols.addAll( t.symbols );
            dfa.addTransition( new FATransition( 
                    t.originState.state, 
                    t.targetState.state, symbols ) );
        }
        
        Collections.sort( dfa.getStates() );
        return dfa;
        
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
     * Reorganize Finite Automata states in a circle.
     * 
     * @param fa The Finite Automata to be processed.
     * @param xCenter The x center of the circle.
     * @param yCenter The y center of the circle.
     * @param radius The distance of each state to the center.
     */
    public static void arrangeFAInCircle( 
            FA fa, int xCenter, int yCenter, int radius ) {
        
        double inc = Math.PI * 2 / fa.getStates().size();
        double current = Math.PI;
        
        for ( FAState s : fa.getStates() ) {
            s.setX1( xCenter + (int) ( radius * Math.cos( current ) ) );
            s.setY1( yCenter + (int) ( radius * Math.sin( current ) ) );
            current += inc;
        }
        
    }
    
}
