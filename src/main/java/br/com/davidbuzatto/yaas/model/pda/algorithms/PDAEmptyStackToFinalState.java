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
package br.com.davidbuzatto.yaas.model.pda.algorithms;

import br.com.davidbuzatto.yaas.model.pda.PDA;
import br.com.davidbuzatto.yaas.model.pda.PDAOperation;
import br.com.davidbuzatto.yaas.model.pda.PDAOperationType;
import br.com.davidbuzatto.yaas.model.pda.PDAState;
import br.com.davidbuzatto.yaas.model.pda.PDATransition;
import br.com.davidbuzatto.yaas.util.CharacterConstants;

/**
 * Creates a new Pushdown Automaton that accepts by final state using a Pushdown
 * Automaton that accepts by empty stack.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAEmptyStackToFinalState {
    
    private final PDA generatedPDA;
    
    public PDAEmptyStackToFinalState( PDA pda ) throws IllegalArgumentException {
        generatedPDA = processIt( pda );
    }

    public PDA getGeneratedPDA() {
        return generatedPDA;
    }
    
    @SuppressWarnings( "unchecked" )
    private static PDA processIt( PDA pda )
            throws IllegalArgumentException {
        
        try {
            
            PDACommon.validateInitialState( pda );

            pda = (PDA) pda.clone();
            pda.setStackStartingSymbol( 'X' );
            
            pda.deactivateAllStatesInSimulation();
            pda.deselectAll();
            
            PDAState pdaInitial = pda.getInitialState();
            
            pda.setInitialState( null );
            pdaInitial.setInitial( false );
            
            PDAState newInitial = new PDAState( 0, true, false );
            PDAState newFinal = new PDAState( 0, false, true );
            
            pda.addState( newInitial );
            pda.addState( newFinal );
            
            pda.addTransition( new PDATransition( newInitial, pdaInitial, 
                    new PDAOperation( 
                            CharacterConstants.EMPTY_STRING, 
                            pda.getStackStartingSymbol(), 
                            PDAOperationType.PUSH, 
                            CharacterConstants.STACK_STARTING_SYMBOL ) ) );
            
            for ( PDAState s : pda.getStates() ) {
                if ( s != newInitial && s != newFinal ) {
                    s.setFinal( false );
                    pda.addTransition( new PDATransition( s, newFinal, 
                            PDAOperation.getPopOperation( 
                                    CharacterConstants.EMPTY_STRING,
                                    pda.getStackStartingSymbol() ) ) );
                }
            }
            
            PDACommon.reenumerateStates( pda );
            pda.resetTransitionsTransformations();
            
            return pda;
            
        } catch ( CloneNotSupportedException exc ) {
            // should never be reached
            exc.printStackTrace();
        }
        
        return null;
        
    }
    
}
