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
import static br.com.davidbuzatto.yaas.model.fa.algorithms.FACommon.haveSameDeltaWithOneSymbol;
import static br.com.davidbuzatto.yaas.model.fa.algorithms.FACommon.isDistinguishable;
import br.com.davidbuzatto.yaas.model.fa.FAState;
import br.com.davidbuzatto.yaas.model.fa.FATransition;
import br.com.davidbuzatto.yaas.util.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implements the Myhill-Nerode Theorem (Table filling method) to create a
 * minimized DFA.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DFAMinimize {
    
    private static final boolean DEBUG = Boolean.parseBoolean( 
            Utils.getMavenModel().getProperties().getProperty( "debugAlgorithms" ) );
    
    private final FA generatedDFA;
    
    public DFAMinimize( FA fa, 
            boolean removeUselessAndInaccessibleStates ) 
            throws IllegalArgumentException {
        generatedDFA = processIt( fa, removeUselessAndInaccessibleStates );
    }

    public FA getGeneratedDFA() {
        return generatedDFA;
    }
    
    /**
     * Uses the Myhill-Nerode Theorem (Table filling method) to create a
     * minimized DFA.
     * 
     * @param dfa The DFA to be processed.
     * @return An equivalent DFA, but minimized. If the processed DFA is already
     * mimimum, it will be returned instead of a new DFA.
     */
    private static FA processIt( FA dfa, boolean removeUselessAndInaccessibleStates ) throws IllegalArgumentException {
        
        FACommon.validateDFA( dfa );
        FACommon.validateInitialState( dfa );
        FACommon.validateFinalStates( dfa );
        
        // pre-processing step
        // removing inaccessible states
        if ( removeUselessAndInaccessibleStates ) {
            
            if ( DEBUG ) {
                System.out.println( "Pre-processing - removing inaccessible and useless states:" );
            }
            
            FA originalDFA = dfa;
            int originalStateQuantity = originalDFA.getStates().size();
            
            dfa = new FARemoveInaccessibleAndUselessStates( dfa, true ).getGeneratedFA();
            
            // no states removed
            if ( dfa.getStates().size() == originalStateQuantity ) {
                dfa = originalDFA;
            }
            
            if ( DEBUG ) {
                System.out.println( "    Remaining states:" );
                for ( FAState s : dfa.getStates() ) {
                    System.out.println( "        " + s );
                }
            }
        
        }
        
        FA minDfa = new FA();
        List<FAState> states = new ArrayList<>( dfa.getStates() );
        Map<FAState, Map<Character, List<FAState>>> delta = dfa.getDelta();
        
        /* Myhill-Nerode Theorem:
         *
         * 1) Using all states, create all possible pairs;
         * 2) Remove all pairs such one element is a final state and the
         *    other is not;
         * 3) For each remaining pair, verify the transition function
         *    incrementing the input length. If the transition is not equal,
         *    remove the pair;
         * 4) The remaining pairs must be combined with their equivalent
         *    states.
         */
        
        // 1) Using all states, create all possible pairs;
        if ( DEBUG ) {
            System.out.println( "\nStep 01 - creating all possible pairs:" );
        }
        Set<FAStatePairHelper> pairs = new LinkedHashSet<>();
        int sSize = states.size();
        for ( int i = 0; i < sSize; i++ ) {
            for ( int j = i+1; j < sSize; j++ ) {
                FAStatePairHelper pair = new FAStatePairHelper( states.get( i ), states.get( j ) );
                pairs.add( pair );
                if ( DEBUG ) {
                    System.out.println( "    Created pair: " + pair );
                }
            }
        }
        
        // 2) Remove all pairs such one element is a final state and the
        //    other is not;
        if ( DEBUG ) {
            System.out.println( """

                    Step 02 - first equivalente class, removing
                    all pairs such one element is a final
                    state and the other is not.""" );
        }
        Set<FAStatePairHelper> toRemove = new HashSet<>();
        for ( FAStatePairHelper sp : pairs ) {
            if ( ( sp.s1.isFinal() && !sp.s2.isFinal() ) || 
                 ( !sp.s1.isFinal() && sp.s2.isFinal() ) ) {
                toRemove.add( sp );
                if ( DEBUG ) {
                    System.out.println( "    Removing: " + sp );
                }
            }
        }
        pairs.removeAll( toRemove );
        
        if ( DEBUG ) {
            System.out.println( "    Remaining pairs:" );
            for ( FAStatePairHelper p : pairs ) {
                System.out.println( "        " + p );
            }
        }
        
        // 2.5) Remove all pairs such the transition function with just one
        // symbol is not the same;
        if ( DEBUG ) {
            System.out.println( """

                    Step 02.5 - second equivalence class,
                    removing all pairs such the transition
                    function with just one symbol is not
                    the same.""" );
        }
        toRemove.clear();
        for ( FAStatePairHelper sp : pairs ) {
            if ( !haveSameDeltaWithOneSymbol( sp.s1, sp.s2 , dfa ) ) {
                toRemove.add( sp );
                if ( DEBUG ) {
                    System.out.println( "    Removing: " + sp );
                }
            }
        }
        pairs.removeAll( toRemove );
        
        if ( DEBUG ) {
            System.out.println( "    Remaining pairs:" );
            for ( FAStatePairHelper p : pairs ) {
                System.out.println( "        " + p );
            }
        }
        
        // 3) For each remaining pair, verify the transition function
        //    incrementing the input length. If the transition is not equal,
        //    remove the pair, i.e, if the pair is distinguishable.
        if ( DEBUG ) {
            System.out.println( """

                    Step 03 - remaining equivalence classes,
                    removing all pairs such the acceptance when
                    incrementing the input string doesn't match.""" );
        }
        toRemove.clear();
        for ( FAStatePairHelper sp : pairs ) {
            if ( isDistinguishable( sp.s1, sp.s2, dfa ) ) {
                toRemove.add( sp );
                if ( DEBUG ) {
                    System.out.println( "        Removing: " + sp );
                }
            }
        }
        pairs.removeAll( toRemove );
        
        if ( DEBUG ) {
            System.out.println( "    Remaining pairs:" );
            for ( FAStatePairHelper p : pairs ) {
                System.out.println( "        " + p );
            }
        }
        
        /* If all pairs are removed, all of them are distinguishable, so the
         * DFA is already minimum and it is returned.
         */
        if ( pairs.isEmpty() ) {
            return dfa;
        }
        
        // 4) The remaining pairs must be combined with their equivalent
        //    states. Applying transitivity.
        if ( DEBUG ) {
            System.out.println( "\nStep 04 - combining states:" );
        }
        List<Set<FAState>> newStateSets = new ArrayList<>();
        while ( !pairs.isEmpty() ) {
            
            FAStatePairHelper cPair = pairs.iterator().next();
            Set<FAState> newStateSet = new TreeSet<>();
            newStateSet.add( cPair.s1 );
            newStateSet.add( cPair.s2 );
            
            if ( newStateSets.isEmpty() ) {
                newStateSets.add( newStateSet );
                if ( DEBUG ) {
                    System.out.println( "    Combining: " + newStateSet );
                }
            } else {
                
                boolean addNewStateSet = true;
                
                for ( Set<FAState> s : newStateSets ) {
                    if ( s.contains( cPair.s1 ) ) {
                        s.add( cPair.s2 );
                        addNewStateSet = false;
                    }
                    if ( s.contains( cPair.s2 ) ) {
                        s.add( cPair.s1 );
                        addNewStateSet = false;
                    }
                }
                
                if ( addNewStateSet ) {
                    newStateSets.add( newStateSet );
                    if ( DEBUG ) {
                        System.out.println( "    Combining: " + newStateSet );
                    }
                }
                
            }
            
            pairs.remove( cPair );
            
        }
        
        if ( DEBUG ) {
            System.out.println( "    Remaining pairs:" );
            for ( FAStatePairHelper p : pairs ) {
                System.out.println( "        " + p );
            }
        }
        
        // finally, deriving the minized automaton :D
        // the transition generation needs more testing!!!
        for ( Set<FAState> s : newStateSets ) {
            states.removeAll( s );
        }
            
        // new initial state
        FAState dfaIS = new FAState( 0 );
        dfaIS.setInitial( true );

        FAState iS = dfa.getInitialState();
        Map<FAState, Set<FAState>> newToSet = new HashMap<>();
        Map<FAState, FAState> originalToNew = new HashMap<>();

        for ( FAState s : states ) {

            if ( s.isInitial() ) {
                dfaIS.setFinal( s.isFinal() );
                dfaIS.setNumber( s.getNumber() );
                dfaIS.setCustomLabel( s.getCustomLabel() );
                minDfa.addState( dfaIS );
                originalToNew.put( s, dfaIS );
            } else {
                FAState newState = new FAState();
                newState.setFinal( s.isFinal() );
                newState.setNumber( s.getNumber() );
                newState.setCustomLabel( s.getCustomLabel() );
                minDfa.addState( newState );
                originalToNew.put( s, newState );
            }

        }

        for ( Set<FAState> s : newStateSets ) {

            String label = "";
            boolean isFinal = false;

            for ( FAState ss : s ) {
                if ( ss.isFinal() ) {
                    isFinal = true;
                }
                if ( ss.getCustomLabel() != null ) {
                    label += ss.getCustomLabel();
                } else {
                    label += ss.getLabel();
                }
            }

            if ( s.contains( iS ) ) {
                dfaIS.setFinal( isFinal );
                dfaIS.setCustomLabel( label );
                newToSet.put( dfaIS, s );
                minDfa.addState( dfaIS );
            } else {
                FAState newState = new FAState( 0 );
                newState.setFinal( isFinal );
                newState.setCustomLabel( label );
                newToSet.put( newState, s );
                minDfa.addState( newState );
            }

        }

        for ( Map.Entry<FAState, Set<FAState>> e : newToSet.entrySet() ) {
            for ( FAState s : e.getValue() ) {
                originalToNew.put( s, e.getKey() );
            }
        }

        // creating the transitions
        for ( Map.Entry<FAState, FAState> e : originalToNew.entrySet() ) {
            for ( Map.Entry<Character, List<FAState>> tr : delta.get( e.getKey() ).entrySet() ) {
                for ( FAState target : tr.getValue() ) {
                    FAState newOrigin = e.getValue();
                    FAState newTarget = originalToNew.get( target );
                    if ( newOrigin != null && newTarget != null ) {
                        minDfa.addTransition( new FATransition( newOrigin, newTarget, tr.getKey() ) );
                    }
                }
            }
        }
        
        FACommon.reenumerateStates( minDfa );
        return minDfa;
        
    }
    
}
