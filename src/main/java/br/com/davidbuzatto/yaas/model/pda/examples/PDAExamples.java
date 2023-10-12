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
 * A set of methods to construct example PDAs for testing purposes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAExamples {
    
    public static final String CONTEXT_FREE_LANGUAGE_0N_1N = 
            String.format( "L = { 0%c1%c | n %c 1 }", 
            CharacterConstants.SUPER_N, 
            CharacterConstants.SUPER_N,
            CharacterConstants.GREATER_THAN_OR_EQUAL_TO );
    
    public static final String CONTEXT_FREE_LANGUAGE_AI_BJ_CK = 
            String.format( "L = { a%cb%cc%c | i = j %c j = k }", 
            CharacterConstants.SUPER_I, 
            CharacterConstants.SUPER_J,
            CharacterConstants.SUPER_K,
            CharacterConstants.OR );
    
    public static PDA createPDAEvenPalindromeFinalState() {
        
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
    
    public static PDA createPDA01FinalState() {

        PDA pda = new PDA();

        // states
        PDAState q0 = new PDAState( 0, null, true, false );
        q0.setX1Y1( 100, 200 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q0 );

        PDAState q1 = new PDAState( 1, null, false, true );
        q1.setX1Y1( 250, 200 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q1 );

        // transitions
        PDATransition q0q0 = new PDATransition( q0, q0,
                new PDAOperation( '0', 'X', PDAOperationType.PUSH, 'X' ),
                new PDAOperation( '0', pda.getStackStartingSymbol(), PDAOperationType.PUSH, 'X' ),
                PDAOperation.getDoNothingOperation( '1', 'X' ) );
        q0q0.setStrokeColor( new Color( 153, 0, 153, 255 ) );
        pda.addTransition( q0q0 );

        PDATransition q0q1 = new PDATransition( q0, q1,
                PDAOperation.getPopOperation( CharacterConstants.EMPTY_STRING, 'X' ) );
        q0q1.setStrokeColor( new Color( 0, 153, 0, 255 ) );
        pda.addTransition( q0q1 );

        PDATransition q1q1 = new PDATransition( q1, q1,
                PDAOperation.getPopOperation( CharacterConstants.EMPTY_STRING, 'X' ),
                new PDAOperation( '1', 'X', PDAOperationType.PUSH, 'X' ),
                PDAOperation.getPopOperation( '1', pda.getStackStartingSymbol() ) );
        q1q1.setStrokeColor( new Color( 0, 102, 204, 255 ) );
        pda.addTransition( q1q1 );

        return pda;

    }
    
    public static PDA createPDA0n1nFinalState() {

        PDA pda = new PDA();

        // states
        PDAState q0 = new PDAState( 0, null, true, false );
        q0.setX1Y1( 100, 200 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q0 );

        PDAState q1 = new PDAState( 1, null, false, false );
        q1.setX1Y1( 250, 200 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q1 );

        PDAState q2 = new PDAState( 2, null, false, true );
        q2.setX1Y1( 400, 200 );
        q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q2 );

        // transitions
        PDATransition q0q0 = new PDATransition( q0, q0,
                new PDAOperation( '0', 'X', PDAOperationType.PUSH, 'X' ),
                new PDAOperation( '0', pda.getStackStartingSymbol(), PDAOperationType.PUSH, 'X' ) );
        q0q0.setStrokeColor( new Color( 204, 0, 204, 255 ) );
        pda.addTransition( q0q0 );

        PDATransition q0q1 = new PDATransition( q0, q1,
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, 'X' ) );
        q0q1.setStrokeColor( new Color( 51, 153, 0, 255 ) );
        pda.addTransition( q0q1 );

        PDATransition q1q1 = new PDATransition( q1, q1,
                PDAOperation.getPopOperation( '1', 'X' ) );
        q1q1.setStrokeColor( new Color( 0, 0, 255, 255 ) );
        pda.addTransition( q1q1 );

        PDATransition q1q2 = new PDATransition( q1, q2,
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, pda.getStackStartingSymbol() ) );
        q1q2.setStrokeColor( new Color( 255, 153, 0, 255 ) );
        pda.addTransition( q1q2 );

        return pda;

    }
    
    public static PDA createPDA01SameFinalState() {

        PDA pda = new PDA();

        // states
        PDAState q0 = new PDAState( 0, null, true, false );
        q0.setX1Y1( 175, 225 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q0 );

        PDAState q1 = new PDAState( 1, null, false, true );
        q1.setX1Y1( 325, 225 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q1 );

        // transitions
        PDATransition q0q0 = new PDATransition( q0, q0,
                new PDAOperation( '0', 'X', PDAOperationType.PUSH, 'X' ),
                PDAOperation.getPopOperation( '0', 'Y' ),
                new PDAOperation( '0', pda.getStackStartingSymbol(), PDAOperationType.PUSH, 'X' ),
                PDAOperation.getPopOperation( '1', 'X' ),
                new PDAOperation( '1', 'Y', PDAOperationType.PUSH, 'Y' ),
                new PDAOperation( '1', pda.getStackStartingSymbol(), PDAOperationType.PUSH, 'Y' ) );
        q0q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q0q0 );

        PDATransition q0q1 = new PDATransition( q0, q1,
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, pda.getStackStartingSymbol() ) );
        q0q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q0q1 );

        return pda;

    }
    
    public static PDA createPDAAiBjCkFinalState() {

        PDA pda = new PDA();

        // states
        PDAState q0 = new PDAState( 0, null, true, false );
        q0.setX1Y1( 100, 225 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q0 );

        PDAState q1 = new PDAState( 1, null, false, true );
        q1.setX1Y1( 275, 100 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q1 );

        PDAState q2 = new PDAState( 2, null, false, false );
        q2.setX1Y1( 275, 225 );
        q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q2 );

        PDAState q3 = new PDAState( 3, null, false, false );
        q3.setX1Y1( 425, 225 );
        q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q3 );

        PDAState q4 = new PDAState( 4, null, false, true );
        q4.setX1Y1( 275, 350 );
        q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q4 );

        PDAState q5 = new PDAState( 5, null, false, false );
        q5.setX1Y1( 425, 350 );
        q5.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q5 );

        PDAState q6 = new PDAState( 6, null, false, false );
        q6.setX1Y1( 575, 350 );
        q6.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q6 );

        // transitions
        PDATransition q0q1 = new PDATransition( q0, q1,
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, pda.getStackStartingSymbol() ) );
        q0q1.rotateTargetCP( -15 );
        q0q1.moveCPsTo( 165, 146, 139, 164, 192, 127 );
        q0q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q0q1 );

        PDATransition q0q2 = new PDATransition( q0, q2,
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, pda.getStackStartingSymbol() ) );
        q0q2.moveLabelTo( 180, 214 );
        q0q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q0q2 );

        PDATransition q0q4 = new PDATransition( q0, q4,
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, pda.getStackStartingSymbol() ) );
        q0q4.moveLabelTo( 168, 333 );
        q0q4.rotateTargetCP( 15 );
        q0q4.moveCPsTo( 165, 307, 139, 289, 192, 326 );
        q0q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q0q4 );

        PDATransition q1q1 = new PDATransition( q1, q1,
                PDAOperation.getDoNothingOperation( 'c', pda.getStackStartingSymbol() ) );
        q1q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q1q1 );

        PDATransition q2q2 = new PDATransition( q2, q2,
                new PDAOperation( 'a', 'X', PDAOperationType.PUSH, 'X' ),
                new PDAOperation( 'a', pda.getStackStartingSymbol(), PDAOperationType.PUSH, 'X' ) );
        q2q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q2q2 );

        PDATransition q4q4 = new PDATransition( q4, q4,
                PDAOperation.getDoNothingOperation( 'a', pda.getStackStartingSymbol() ) );
        q4q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q4q4 );

        PDATransition q2q3 = new PDATransition( q2, q3,
                PDAOperation.getPopOperation( 'b', 'X' ) );
        q2q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q2q3 );

        PDATransition q3q3 = new PDATransition( q3, q3,
                PDAOperation.getPopOperation( 'b', 'X' ) );
        q3q3.rotateTargetCP( 25 );
        q3q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q3q3 );

        PDATransition q3q1 = new PDATransition( q3, q1,
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, pda.getStackStartingSymbol() ) );
        q3q1.moveLabelTo( 375, 129 );
        q3q1.rotateTargetCP( -160 );
        q3q1.moveCPsTo( 370, 144, 392, 162, 347, 125 );
        q3q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q3q1 );

        PDATransition q4q5 = new PDATransition( q4, q5,
                new PDAOperation( 'b', pda.getStackStartingSymbol(), PDAOperationType.PUSH, 'X' ) );
        q4q5.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q4q5 );

        PDATransition q5q5 = new PDATransition( q5, q5,
                new PDAOperation( 'b', 'X', PDAOperationType.PUSH, 'X' ) );
        q5q5.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q5q5 );

        PDATransition q5q6 = new PDATransition( q5, q6,
                PDAOperation.getPopOperation( 'c', 'X' ) );
        q5q6.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q5q6 );

        PDATransition q6q6 = new PDATransition( q6, q6,
                PDAOperation.getPopOperation( 'c', 'X' ) );
        q6q6.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q6q6 );

        PDATransition q6q4 = new PDATransition( q6, q4,
                PDAOperation.getPopOperation( CharacterConstants.EMPTY_STRING, pda.getStackStartingSymbol() ) );
        q6q4.moveLabelTo( 425, 418 );
        q6q4.rotateTargetCP( -150 );
        q6q4.moveCPsTo( 425, 410, 448, 410, 400, 410 );
        q6q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q6q4 );

        return pda;

    }
    
    public static PDA createPDATest0Double1FinalState() {

        PDA pda = new PDA();

        // states
        PDAState q0 = new PDAState( 0, null, true, false );
        q0.setX1Y1( 100, 200 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q0 );

        PDAState q1 = new PDAState( 1, null, false, false );
        q1.setX1Y1( 225, 100 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q1 );

        PDAState q3 = new PDAState( 3, null, false, false );
        q3.setX1Y1( 225, 300 );
        q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q3 );

        PDAState q4 = new PDAState( 4, null, false, true );
        q4.setX1Y1( 425, 200 );
        q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q4 );

        // transitions
        PDATransition q0q0 = new PDATransition( q0, q0,
                new PDAOperation( '0', 'X', PDAOperationType.PUSH, 'X', 'X' ),
                new PDAOperation( '0', pda.getStackStartingSymbol(), PDAOperationType.PUSH, 'X', 'X' ),
                new PDAOperation( '1', 'Y', PDAOperationType.PUSH, 'Y' ),
                new PDAOperation( '1', pda.getStackStartingSymbol(), PDAOperationType.PUSH, 'Y' ) );
        q0q0.moveLabelTo( 61, 144 );
        q0q0.rotateTargetCP( -25 );
        q0q0.setStrokeColor( new Color( 0, 51, 255, 255 ) );
        pda.addTransition( q0q0 );

        PDATransition q0q1 = new PDATransition( q0, q1,
                PDAOperation.getPopOperation( '1', 'X' ) );
        q0q1.moveLabelTo( 145, 102 );
        q0q1.rotateTargetCP( -5 );
        q0q1.moveCPsTo( 139, 125, 111, 146, 162, 105 );
        q0q1.setStrokeColor( new Color( 0, 204, 51, 255 ) );
        pda.addTransition( q0q1 );

        PDATransition q1q0 = new PDATransition( q1, q0,
                PDAOperation.getPopOperation( CharacterConstants.EMPTY_STRING, 'X' ),
                new PDAOperation( CharacterConstants.EMPTY_STRING, pda.getStackStartingSymbol(), PDAOperationType.PUSH, 'Y' ) );
        q1q0.moveLabelTo( 228, 172 );
        q1q0.rotateTargetCP( 160 );
        q1q0.moveCPsTo( 178, 167, 196, 153, 160, 182 );
        q1q0.setStrokeColor( new Color( 0, 102, 102, 255 ) );
        pda.addTransition( q1q0 );

        PDATransition q0q3 = new PDATransition( q0, q3,
                PDAOperation.getPopOperation( '0', 'Y' ) );
        q0q3.moveLabelTo( 213, 230 );
        q0q3.rotateTargetCP( 65 );
        q0q3.moveCPsTo( 172, 222, 142, 207, 202, 238 );
        q0q3.setStrokeColor( new Color( 204, 0, 204, 255 ) );
        pda.addTransition( q0q3 );

        PDATransition q3q0 = new PDATransition( q3, q0,
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, pda.getStackStartingSymbol() ),
                PDAOperation.getPopOperation( '0', 'Y' ) );
        q3q0.moveLabelTo( 114, 302 );
        q3q0.rotateTargetCP( -110 );
        q3q0.moveCPsTo( 145, 279, 175, 294, 115, 263 );
        q3q0.setStrokeColor( new Color( 255, 102, 0, 255 ) );
        pda.addTransition( q3q0 );

        PDATransition q0q4 = new PDATransition( q0, q4,
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, pda.getStackStartingSymbol() ) );
        q0q4.moveLabelTo( 326, 189 );
        q0q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q0q4 );

        return pda;

    }
    
    public static PDA createPDAEvenPalindromeEmptyStack() {

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
    
    public static PDA createPDA0n1nEmptyStack() {

        PDA pda = new PDA();

        // states
        PDAState q0 = new PDAState( 0, null, true, false );
        q0.setX1Y1( 100, 200 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q0 );

        PDAState q1 = new PDAState( 1, null, false, false );
        q1.setX1Y1( 250, 200 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q1 );

        PDAState q2 = new PDAState( 2, null, false, false );
        q2.setX1Y1( 400, 200 );
        q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q2 );

        // transitions
        PDATransition q0q0 = new PDATransition( q0, q0,
                new PDAOperation( '0', 'X', PDAOperationType.PUSH, 'X' ),
                new PDAOperation( '0', pda.getStackStartingSymbol(), PDAOperationType.PUSH, 'X' ) );
        q0q0.setStrokeColor( new Color( 204, 0, 204, 255 ) );
        pda.addTransition( q0q0 );

        PDATransition q0q1 = new PDATransition( q0, q1,
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, 'X' ) );
        q0q1.setStrokeColor( new Color( 51, 153, 0, 255 ) );
        pda.addTransition( q0q1 );

        PDATransition q1q1 = new PDATransition( q1, q1,
                PDAOperation.getPopOperation( '1', 'X' ) );
        q1q1.setStrokeColor( new Color( 0, 0, 255, 255 ) );
        pda.addTransition( q1q1 );

        PDATransition q1q2 = new PDATransition( q1, q2,
                PDAOperation.getPopOperation( CharacterConstants.EMPTY_STRING, pda.getStackStartingSymbol() ) );
        q1q2.setStrokeColor( new Color( 255, 153, 0, 255 ) );
        pda.addTransition( q1q2 );

        return pda;

    }
    
    public static PDA createPDA01SameEmptyStack() {

        PDA pda = new PDA();

        // states
        PDAState q0 = new PDAState( 0, null, true, false );
        q0.setX1Y1( 175, 225 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q0 );

        PDAState q1 = new PDAState( 1, null, false, false );
        q1.setX1Y1( 325, 225 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addState( q1 );

        // transitions
        PDATransition q0q0 = new PDATransition( q0, q0,
                new PDAOperation( '0', 'X', PDAOperationType.PUSH, 'X' ),
                PDAOperation.getPopOperation( '0', 'Y' ),
                new PDAOperation( '0', pda.getStackStartingSymbol(), PDAOperationType.PUSH, 'X' ),
                PDAOperation.getPopOperation( '1', 'X' ),
                new PDAOperation( '1', 'Y', PDAOperationType.PUSH, 'Y' ),
                new PDAOperation( '1', pda.getStackStartingSymbol(), PDAOperationType.PUSH, 'Y' ) );
        q0q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q0q0 );

        PDATransition q0q1 = new PDATransition( q0, q1,
                PDAOperation.getPopOperation( CharacterConstants.EMPTY_STRING, pda.getStackStartingSymbol() ) );
        q0q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        pda.addTransition( q0q1 );

        return pda;

    }
    
    public static PDA createDPDAEvenPalindromeCenterMark() {
        
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
    
    public static PDA createDPDA0n1n() {

        PDA dpda = new PDA();

        // states
        PDAState q0 = new PDAState( 0, null, true, false );
        q0.setX1Y1( 100, 200 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dpda.addState( q0 );

        PDAState q1 = new PDAState( 1, null, false, false );
        q1.setX1Y1( 250, 200 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dpda.addState( q1 );

        PDAState q2 = new PDAState( 2, null, false, true );
        q2.setX1Y1( 400, 200 );
        q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dpda.addState( q2 );

        // transitions
        PDATransition q0q0 = new PDATransition( q0, q0,
                new PDAOperation( '0', 'X', PDAOperationType.PUSH, 'X' ),
                new PDAOperation( '0', dpda.getStackStartingSymbol(), PDAOperationType.PUSH, 'X' ) );
        q0q0.setStrokeColor( new Color( 204, 0, 204, 255 ) );
        dpda.addTransition( q0q0 );

        PDATransition q0q1 = new PDATransition( q0, q1,
                PDAOperation.getPopOperation( '1', 'X' ) );
        q0q1.setStrokeColor( new Color( 51, 153, 0, 255 ) );
        dpda.addTransition( q0q1 );

        PDATransition q1q1 = new PDATransition( q1, q1,
                PDAOperation.getPopOperation( '1', 'X' ) );
        q1q1.setStrokeColor( new Color( 0, 0, 255, 255 ) );
        dpda.addTransition( q1q1 );

        PDATransition q1q2 = new PDATransition( q1, q2,
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, dpda.getStackStartingSymbol() ) );
        q1q2.setStrokeColor( new Color( 255, 153, 0, 255 ) );
        dpda.addTransition( q1q2 );

        return dpda;

    }
    
    public static PDA createDPDA01Same() {

        PDA dpda = new PDA();

        // states
        PDAState q0 = new PDAState( 0, null, true, true );
        q0.setX1Y1( 175, 225 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dpda.addState( q0 );

        // transitions
        PDATransition q0q0 = new PDATransition( q0, q0,
                new PDAOperation( '0', 'X', PDAOperationType.PUSH, 'X' ),
                PDAOperation.getPopOperation( '0', 'Y' ),
                new PDAOperation( '0', dpda.getStackStartingSymbol(), PDAOperationType.PUSH, 'X' ),
                PDAOperation.getPopOperation( '1', 'X' ),
                new PDAOperation( '1', 'Y', PDAOperationType.PUSH, 'Y' ),
                new PDAOperation( '1', dpda.getStackStartingSymbol(), PDAOperationType.PUSH, 'Y' ) );
        q0q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dpda.addTransition( q0q0 );

        return dpda;

    }
    
}
