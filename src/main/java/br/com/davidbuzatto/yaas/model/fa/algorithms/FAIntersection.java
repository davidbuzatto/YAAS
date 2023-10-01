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

/**
 * Performs the INTERSECTION operation between two Finite Automata generating
 * a new one as result.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAIntersection {
    
    private final FA generatedFA;
    
    public FAIntersection( FA fa1, FA fa2 ) throws IllegalArgumentException {
        generatedFA = processIt( fa1, fa2 );
    }

    public FA getGeneratedFA() {
        return generatedFA;
    }
    
    @SuppressWarnings( "unchecked" )
    private static FA processIt( FA fa1, FA fa2 )
            throws IllegalArgumentException {
        
        try {
            
            FACommon.validateDFA( fa1, fa2 );
            FACommon.validateInitialState( fa1, fa2 );
            FACommon.validateAcceptingStates( fa1, fa2 );

            fa1 = (FA) fa1.clone();
            fa2 = (FA) fa2.clone();
            
            fa1 = new DFAComplement( fa1 ).getGeneratedDFA();
            fa2 = new DFAComplement( fa2 ).getGeneratedDFA();
            
            fa1 = new FAUnion( fa1, fa2 ).getGeneratedFA();
            fa1 = new FADeterminize( fa1 ).getGeneratedDFA();
            fa1 = new DFAComplement( fa1 ).getGeneratedDFA();
            fa1 = new FARemoveInaccessibleAndUselessStates( fa1, true ).getGeneratedFA();
            //fa1 = new DFAMinimize( fa1 ).getGeneratedDFA();
            
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
