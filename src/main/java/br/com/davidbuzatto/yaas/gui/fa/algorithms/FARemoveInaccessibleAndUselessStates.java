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
import static br.com.davidbuzatto.yaas.gui.fa.algorithms.FAAlgorithmsConstants.DEBUG;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Creates a new FA without inaccessible and useless states.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FARemoveInaccessibleAndUselessStates {
    
    private final FA generatedFA;
    
    public FARemoveInaccessibleAndUselessStates( FA fa, boolean createNewFA ) throws IllegalArgumentException {
        generatedFA = processIt( fa,createNewFA );
    }

    public FA getGeneratedFA() {
        return generatedFA;
    }
    
    @SuppressWarnings( "unchecked" )
    private static FA processIt( FA fa, boolean createNewFA )
            throws IllegalArgumentException {
        
        try {

            if ( createNewFA ) {
                fa = (FA) fa.clone();
            }
            
            fa.deactivateAllStatesInSimulation();
            fa.deselectAll();
            
            Set<FAState> inaccessibleStates = new HashSet<>( fa.getStates() );
            List<FAState> uselessStates = new ArrayList<>();
            
            Set<FAState> visitedI = new HashSet<>();
            dfs( fa.getInitialState(), fa.getTransitions(), visitedI );
            inaccessibleStates.removeAll( visitedI );
            for ( FAState s : inaccessibleStates ) {
                fa.removeState( s );
                if ( DEBUG ) {
                    System.out.println( "    Inacessible state: " + s );
                }
            }
            
            Set<FAState> acceptingStates = new HashSet<>( fa.getAcceptingStates() );
            Set<FAState> visitedU = new HashSet<>();
            for ( FAState s : fa.getStates() ) {
                visitedU.clear();
                dfs( s, fa.getTransitions(), visitedU );
                if ( !containsAtLeastOne( acceptingStates, visitedU ) ) {
                    uselessStates.add( s );
                }
            }
            for ( FAState s : uselessStates ) {
                fa.removeState( s );
                if ( DEBUG ) {
                    System.out.println( "    Useless state: " + s );
                }
            }
            
            FACommon.reenumerateStates( fa );
            fa.resetTransitionsTransformations();
            
            return fa;
            
        } catch ( CloneNotSupportedException exc ) {
            // should never be reached
            exc.printStackTrace();
        }
        
        return null;
        
    }
    
    private static void dfs( FAState s, List<FATransition> transitions, Set<FAState> visited ) {
        if ( !visited.contains( s ) ) {
            visited.add( s );
            for ( FATransition t : transitions ) {
                if ( t.getOriginState().equals( s ) ) {
                    dfs( t.getTargetState(), transitions, visited );
                }
            }
        }
    }
    
    private static boolean containsAtLeastOne( Set<FAState> states, Set<FAState> testSet ) {
        for ( FAState s : testSet ) {
            if ( states.contains( s ) ) {
                return true;
            }
        }
        return false;
    }
    
}
