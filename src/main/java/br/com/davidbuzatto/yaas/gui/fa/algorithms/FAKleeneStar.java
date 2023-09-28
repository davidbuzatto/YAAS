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
import br.com.davidbuzatto.yaas.util.CharacterConstants;
import java.util.List;

/**
 * Performs the KLEENE STAR operation in a Finite Automaton generating a new
 * one as result.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAKleeneStar {
    
    private final FA generatedFA;
    
    public FAKleeneStar( FA fa ) throws IllegalArgumentException {
        generatedFA = processIt( fa );
    }

    public FA getGeneratedFA() {
        return generatedFA;
    }
    
    @SuppressWarnings( "unchecked" )
    private static FA processIt( FA fa )
            throws IllegalArgumentException {
        
        try {
            
            int distance = 150;
            
            FACommon.validateInitialState( fa );
            FACommon.validateAcceptingStates( fa );

            fa = (FA) fa.clone();
            
            fa.deactivateAllStatesInSimulation();
            fa.deselectAll();
            
            FAState fa1Initial = fa.getInitialState();
            List<FAState> fa1Accepting = fa.getAcceptingStates();
            
            fa.setInitialState( null );
            fa1Initial.setInitial( false );
            
            FAState newInitial = new FAState( 0, true, false );
            
            fa.addState( newInitial );
            
            fa.addTransition( new FATransition( 
                    newInitial, fa1Initial, CharacterConstants.EMPTY_STRING ) );
            
            for ( FAState s : fa1Accepting ) {
                fa.addTransition( new FATransition( 
                        s, newInitial, CharacterConstants.EMPTY_STRING ) );
            }
            
            FACommon.reenumerateStates( fa );
            FAArrangement.arrangeByLevel( fa, 150, 150, distance, false );
            fa.resetTransitionsTransformations();
            
            return fa;
            
        } catch ( CloneNotSupportedException exc ) {
            // should never be reached
            exc.printStackTrace();
        }
        
        return null;
        
    }
    
}
