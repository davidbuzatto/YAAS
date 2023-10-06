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
package br.com.davidbuzatto.yaas.model.pda.examples;

import br.com.davidbuzatto.yaas.model.pda.PDA;
import br.com.davidbuzatto.yaas.model.pda.PDAOperation;
import br.com.davidbuzatto.yaas.model.pda.PDAOperationType;
import br.com.davidbuzatto.yaas.model.pda.PDAState;
import br.com.davidbuzatto.yaas.model.pda.PDATransition;
import br.com.davidbuzatto.yaas.util.CharacterConstants;
import java.awt.Color;

/**
 * A set of methods to construct example FAs for testing purposes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAExamples {
    
    public static PDA createPDAEvenPalindromeAS() {
        
        PDA pda = new PDA();

        // states
        PDAState q0 = new PDAState( 0, null, true, false );
        q0.setX1Y1( 100, 200 );
        pda.addState( q0 );

        PDAState q1 = new PDAState( 1, null, false, false );
        q1.setX1Y1( 250, 200 );
        pda.addState( q1 );

        PDAState q2 = new PDAState( 2, null, false, true );
        q2.setX1Y1( 400, 200 );
        pda.addState( q2 );

        // transitions
        PDATransition q0q0 = new PDATransition( q0, q0,
                new PDAOperation( '0', '0', PDAOperationType.PUSH, '0' ),
                new PDAOperation( '0', '1', PDAOperationType.PUSH, '0' ),
                new PDAOperation( '0', pda.getStackStartingSymbol(),
                        PDAOperationType.PUSH, '0' ),
                new PDAOperation( '1', '0', PDAOperationType.PUSH, '1' ),
                new PDAOperation( '1', '1', PDAOperationType.PUSH, '1' ),
                new PDAOperation( '1', pda.getStackStartingSymbol(),
                        PDAOperationType.PUSH, '1' ) );
        pda.addTransition( q0q0 );

        PDATransition q0q1 = new PDATransition( q0, q1,
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, '0' ),
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, '1' ),
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING,
                        pda.getStackStartingSymbol() ) );
        pda.addTransition( q0q1 );

        PDATransition q1q1 = new PDATransition( q1, q1,
                PDAOperation.getPopOperation( '0', '0' ),
                PDAOperation.getPopOperation( '1', '1' ) );
        pda.addTransition( q1q1 );

        PDATransition q1q2 = new PDATransition( q1, q2,
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING,
                        pda.getStackStartingSymbol() ) );
        pda.addTransition( q1q2 );

        q0q0.setStrokeColor( new Color( 112, 48, 160 ) );
        q0q1.setStrokeColor( new Color( 0, 176, 80 ) );
        q1q1.setStrokeColor( new Color( 255, 153, 51 ) );
        q1q2.setStrokeColor( new Color( 0, 112, 192 ) );
        
        return pda;
        
    }
    
    public static PDA createPDAEvenPalindromeES() {

        PDA pda = new PDA();

        // states
        PDAState q0 = new PDAState( 0, null, true, false );
        q0.setX1Y1( 100, 200 );
        pda.addState( q0 );

        PDAState q1 = new PDAState( 1, null, false, false );
        q1.setX1Y1( 250, 200 );
        pda.addState( q1 );

        PDAState q2 = new PDAState( 2, null, false, false );
        q2.setX1Y1( 400, 200 );
        pda.addState( q2 );

        // transitions
        PDATransition q0q0 = new PDATransition( q0, q0,
                new PDAOperation( '0', '0', PDAOperationType.PUSH, '0' ),
                new PDAOperation( '0', '1', PDAOperationType.PUSH, '0' ),
                new PDAOperation( '0', pda.getStackStartingSymbol(),
                        PDAOperationType.PUSH, '0' ),
                new PDAOperation( '1', '0', PDAOperationType.PUSH, '1' ),
                new PDAOperation( '1', '1', PDAOperationType.PUSH, '1' ),
                new PDAOperation( '1', pda.getStackStartingSymbol(),
                        PDAOperationType.PUSH, '1' ) );
        pda.addTransition( q0q0 );

        PDATransition q0q1 = new PDATransition( q0, q1,
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, '0' ),
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, '1' ),
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING,
                        pda.getStackStartingSymbol() ) );
        pda.addTransition( q0q1 );

        PDATransition q1q1 = new PDATransition( q1, q1,
                PDAOperation.getPopOperation( '0', '0' ),
                PDAOperation.getPopOperation( '1', '1' ) );
        pda.addTransition( q1q1 );

        PDATransition q1q2 = new PDATransition( q1, q2,
                PDAOperation.getPopOperation( CharacterConstants.EMPTY_STRING, pda.getStackStartingSymbol() ) );
        pda.addTransition( q1q2 );

        return pda;

    }
    
    public static PDA createDPDAEvenPalindromeCM() {
        
        PDA dpda = new PDA();

        // states
        PDAState q0 = new PDAState( 0, null, true, false );
        q0.setX1Y1( 100, 200 );
        dpda.addState( q0 );

        PDAState q1 = new PDAState( 1, null, false, false );
        q1.setX1Y1( 250, 200 );
        dpda.addState( q1 );

        PDAState q2 = new PDAState( 2, null, false, true );
        q2.setX1Y1( 400, 200 );
        dpda.addState( q2 );

        // transitions
        PDATransition q0q0 = new PDATransition( q0, q0,
                new PDAOperation( '0', '0', PDAOperationType.PUSH, '0' ),
                new PDAOperation( '0', '1', PDAOperationType.PUSH, '0' ),
                new PDAOperation( '0', dpda.getStackStartingSymbol(), PDAOperationType.PUSH, '0' ),
                new PDAOperation( '1', '0', PDAOperationType.PUSH, '1' ),
                new PDAOperation( '1', '1', PDAOperationType.PUSH, '1' ),
                new PDAOperation( '1', dpda.getStackStartingSymbol(), PDAOperationType.PUSH, '1' ) );
        dpda.addTransition( q0q0 );

        PDATransition q0q1 = new PDATransition( q0, q1,
                PDAOperation.getDoNothingOperation( 'c', '0' ),
                PDAOperation.getDoNothingOperation( 'c', '1' ),
                PDAOperation.getDoNothingOperation( 'c', dpda.getStackStartingSymbol() ) );
        dpda.addTransition( q0q1 );

        PDATransition q1q1 = new PDATransition( q1, q1,
                PDAOperation.getPopOperation( '0', '0' ),
                PDAOperation.getPopOperation( '1', '1' ) );
        dpda.addTransition( q1q1 );

        PDATransition q1q2 = new PDATransition( q1, q2,
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, dpda.getStackStartingSymbol() ) );
        dpda.addTransition( q1q2 );

        return dpda;
        
    }
    
}
