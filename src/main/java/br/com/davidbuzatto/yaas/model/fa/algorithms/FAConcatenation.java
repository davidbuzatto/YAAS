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
import java.util.List;

/**
 * Performs the CONCATENATION operation between two Finite Automata generating
 * a new one as result.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAConcatenation {
    
    private final FA generatedFA;
    
    public FAConcatenation( FA fa1, FA fa2 ) throws IllegalArgumentException {
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
            FACommon.validateFinalStates( fa1, fa2 );

            fa1 = (FA) fa1.clone();
            fa2 = (FA) fa2.clone();
            
            fa1.deactivateAllStatesInSimulation();
            fa1.deselectAll();
            
            fa2.deactivateAllStatesInSimulation();
            fa2.deselectAll();
            
            FAState fa2Initial = fa2.getInitialState();
            List<FAState> fa1Final = fa1.getFinalStates();
            
            fa2.setInitialState( null );
            fa2Initial.setInitial( false );
            
            for ( FAState s : fa1Final ) {
                s.setFinal( false );
            }
            
            fa1.merge( fa2 );
            
            for ( FAState s : fa1Final ) {
                fa1.addTransition( new FATransition( 
                        s, fa2Initial, CharacterConstants.EMPTY_STRING ) );
            }
            
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
