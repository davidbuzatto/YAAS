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
package br.com.davidbuzatto.yaas.gui.fa.algorithms;

import br.com.davidbuzatto.yaas.gui.fa.FA;
import static br.com.davidbuzatto.yaas.gui.fa.algorithms.FACommon.newCustomLabel;
import br.com.davidbuzatto.yaas.gui.fa.FAState;
import br.com.davidbuzatto.yaas.gui.fa.FATransition;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * This class processes a Finite Automaton and generates a new one without any
 * non-determinisms.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FADeterminize {
    
    private final FA generatedDFA;
    
    public FADeterminize( FA fa ) {
        generatedDFA = processIt( fa );
    }

    public FA getGeneratedDFA() {
        return generatedDFA;
    }
    
    /**
     * Generates a new Finite Automaton without any non-determinisms.
     * 
     * @param fa The Finite Automaton to be processed.
     * @return An equivalent DFA (not minimum).
     */
    private static FA processIt( FA fa ) {
        
        FA dfa = new FA();
        int currentState = 0;
        
        // get data from the original automaton
        Set<Character> alphabet = fa.getAlphabet();
        FAState initialState = fa.getInitialState();
        List<FAState> acceptinStates = fa.getAcceptingStates();
        Map<FAState, Map<Character, List<FAState>>> delta = fa.getDelta();
        Map<FAState, Set<FAState>> ecloses = fa.getEcloses( delta );
        
        // start the process of generation
        
        // creates the new initial state
        FAState dfaInitial = new FAState();
        dfaInitial.setInitial( true );
        dfaInitial.setLabel( "q" + currentState );
        dfaInitial.setCustomLabel( newCustomLabel( currentState++ ) );
        StateHelper initialSH = new StateHelper( dfaInitial, ecloses.get( initialState ) );
        
        // data strutctures to store transitions and states that will be generated
        Set<TransitionHelper> generatedTransitions = new LinkedHashSet<>();
        Set<StateHelper> generatedStates = new LinkedHashSet<>();
        generatedStates.add( initialSH );
        
        // a queue to process the generated states
        Queue<StateHelper> queue = new ArrayDeque<>();
        queue.add( initialSH );
        
        // on demand process of new states
        while ( !queue.isEmpty() ) {
            
            StateHelper current = queue.poll();
            
            for ( Character a : alphabet ) {
                
                Set<FAState> targetEclose = new HashSet<>();
                
                for ( FAState s : current.states ) {
                    
                    List<FAState> t = delta.get( s ).get( a );
                    if ( t != null && !t.isEmpty() ) {
                        for ( FAState ns : t ) {
                            targetEclose.addAll( ecloses.get( ns ) );
                        }
                    }

                }
                
                if ( !targetEclose.isEmpty() ) {

                    StateHelper newSH = new StateHelper( targetEclose );
                    
                    if ( !generatedStates.contains( newSH ) ) {
                        
                        FAState newState = new FAState();
                        newState.setLabel( "q" + currentState );
                        newState.setCustomLabel( newCustomLabel( currentState++ ) );
                        newSH.state = newState;
                        generatedStates.add( newSH );
                        queue.add( newSH );
                        
                    }
                    
                    TransitionHelper newTH = new TransitionHelper( current, newSH, a );
                    if ( generatedTransitions.contains( newTH ) ) {
                        for ( TransitionHelper t : generatedTransitions ) {
                            if ( t.equals( newTH ) ) {
                                t.symbols.addAll( newTH.symbols );
                            }
                        }
                    } else {
                        generatedTransitions.add( newTH );
                    }

                }

            }
            
        }
        
        for ( StateHelper s : generatedStates ) {
            for ( FAState a : acceptinStates ) {
                if ( s.states.contains( a ) ) {
                    s.state.setAccepting( true );
                    break;
                }
            }
        }
        
        for ( StateHelper s : generatedStates ) {
            dfa.addState( s.state );
        }
        
        Map<Set<FAState>, FAState> m = new HashMap<>();
        for ( TransitionHelper t : generatedTransitions ) {
            m.put( t.originState.states, t.originState.state );
        }
        
        for ( TransitionHelper t : generatedTransitions ) {
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
    
}
