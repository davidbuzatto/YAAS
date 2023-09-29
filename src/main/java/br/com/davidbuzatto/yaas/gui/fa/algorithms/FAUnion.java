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

/**
 * Performs the UNION operation between two Finite Automata generating a new
 * one as result.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAUnion {
    
    private final FA generatedFA;
    
    public FAUnion( FA fa1, FA fa2 ) throws IllegalArgumentException {
        generatedFA = processIt( fa1, fa2 );
    }

    public FA getGeneratedFA() {
        return generatedFA;
    }
    
    @SuppressWarnings( "unchecked" )
    private static FA processIt( FA fa1, FA fa2 )
            throws IllegalArgumentException {
        
        try {
            
            FACommon.validateInitialState( fa1, fa2 );
            FACommon.validateAcceptingStates( fa1, fa2 );

            fa1 = (FA) fa1.clone();
            fa2 = (FA) fa2.clone();
            
            fa1.deactivateAllStatesInSimulation();
            fa1.deselectAll();
            
            fa2.deactivateAllStatesInSimulation();
            fa2.deselectAll();
            
            FAState fa1Initial = fa1.getInitialState();
            FAState fa2Initial = fa2.getInitialState();
            
            fa1.setInitialState( null );
            fa2.setInitialState( null );
            fa1Initial.setInitial( false );
            fa2Initial.setInitial( false );
            
            FAState newInitial = new FAState( 0, true, false );
            
            fa1.merge( fa2 );
            fa1.addState( newInitial );
            
            fa1.addTransition( new FATransition( 
                    newInitial, fa1Initial, CharacterConstants.EMPTY_STRING ) );
            fa1.addTransition( new FATransition( 
                    newInitial, fa2Initial, CharacterConstants.EMPTY_STRING ) );
            
            FACommon.reenumerateStates( fa1 );
            fa1.resetTransitionsTransformations();
            
            return fa1;
            
        } catch ( CloneNotSupportedException exc ) {
            // should never be reached
            exc.printStackTrace();
        }
        
        return null;
        
    }
    
}
