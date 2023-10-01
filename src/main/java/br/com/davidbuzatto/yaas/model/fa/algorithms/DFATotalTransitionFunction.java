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
import br.com.davidbuzatto.yaas.model.fa.FATransition;
import br.com.davidbuzatto.yaas.util.CharacterConstants;
import br.com.davidbuzatto.yaas.util.DrawingConstants;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class has methods that process a DFA modifying it, making its
 * transition function total.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DFATotalTransitionFunction {
    
    /**
     * Add all missing transitions to a DFA.
     * 
     * @param dfa The DFA to be processed.
     * @param currentState Whats the current state counter.
     */
    public static void processIt( FA dfa, int currentState )
            throws IllegalArgumentException {
        processIt( 
                dfa, 
                currentState, 
                true,
                DrawingConstants.NULL_STATE_STROKE_COLOR,
                DrawingConstants.NULL_TRANSITION_STROKE_COLOR );
    }
    
    /**
     * Add all missing transitions to a DFA.
     * 
     * @param dfa The DFA to be processed.
     * @param currentState Whats the current state counter.
     * @param useNullCustomLabel If it will use the empty set symbol do set the
     * custom label of the generated state (null state).
     * @param nullStateStrokeColor The color to set the stroke in the null state.
     * @param nullTransitionStrokeColor The color to set in thes troke the null transition.
     */
    public static void processIt( 
            FA dfa,
            int currentState, 
            boolean useNullCustomLabel,
            Color nullStateStrokeColor,
            Color nullTransitionStrokeColor ) throws IllegalArgumentException {
        
        FACommon.validateDFA( dfa );
        FACommon.validateInitialState( dfa );
        FACommon.validateAcceptingStates( dfa );
        
        Map<FAState, Set<Character>> allMissing = new HashMap<>();
        Map<FAState, Map<Character, List<FAState>>> delta = dfa.getDelta();
        List<Character> alphabet = new ArrayList<>( dfa.getAlphabet() );
        int maxX = Integer.MIN_VALUE;
        
        for ( FAState s : dfa.getStates() ) {
            
            Set<Character> a = new TreeSet<>( alphabet );
            allMissing.put( s, a );
            
            if ( maxX < s.getX1() ) {
                maxX = s.getX1();
            }
            
        }
        
        Set<FAState> toRemove = new HashSet<>();
        
        for ( Map.Entry<FAState, Map<Character, List<FAState>>> e : delta.entrySet() ) {
            for ( Map.Entry<Character, List<FAState>> t : e.getValue().entrySet() ) {
                Set<Character> cSet = allMissing.get( e.getKey() );
                if ( !cSet.isEmpty() ) {
                    cSet.remove( t.getKey() );
                    if ( cSet.isEmpty() ) {
                        toRemove.add( e.getKey() );
                    }
                }
            }
        }
        
        for ( FAState s : toRemove ) {
            allMissing.remove( s );
        }
        
        if ( !allMissing.isEmpty() ) {
            
            FAState nullState = new FAState( dfa.getStates().size() );
            if ( useNullCustomLabel ) {
                nullState.setCustomLabel( CharacterConstants.EMPTY_SET.toString() );
            }
            nullState.setStrokeColor( nullStateStrokeColor );
            nullState.setX1( maxX + 150 );
            nullState.setY1( dfa.getInitialState().getY1() );
            dfa.addState( nullState );
            
            FATransition nullTransition = new FATransition( 
                    nullState, nullState, alphabet );
            nullTransition.setStrokeColor( nullStateStrokeColor );
            dfa.addTransition( nullTransition );
            
            for ( Map.Entry<FAState, Set<Character>> e : allMissing.entrySet() ) {
                List<Character> s = new ArrayList<>( e.getValue() );
                FATransition t = new FATransition( e.getKey(), nullState, s );
                t.setStrokeColor( nullTransitionStrokeColor );
                dfa.addTransition( t );
            }
            
        }
        
    }
    
}
