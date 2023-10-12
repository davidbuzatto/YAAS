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
package br.com.davidbuzatto.yaas.model.tm.examples;

import br.com.davidbuzatto.yaas.model.tm.TM;
import br.com.davidbuzatto.yaas.model.tm.TMOperation;
import br.com.davidbuzatto.yaas.model.tm.TMOperationType;
import br.com.davidbuzatto.yaas.model.tm.TMState;
import br.com.davidbuzatto.yaas.model.tm.TMTransition;
import br.com.davidbuzatto.yaas.util.CharacterConstants;
import java.awt.Color;

/**
 * A set of methods to construct example TMs for testing purposes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TMExamples {
    
    public static final String EX1 = 
            String.format( "L = { 0%c1%c | n %c 1 }", 
            CharacterConstants.SUPER_N, 
            CharacterConstants.SUPER_N,
            CharacterConstants.GREATER_THAN_OR_EQUAL_TO );
    
    // TODO create first example
    public static TM createTMTest() {
        
        TM tm = new TM();

        // states
        TMState q0 = new TMState( 0, null, true, false );
        q0.setX1Y1( 100, 200 );
        tm.addState( q0 );

        TMState q1 = new TMState( 1, null, false, false );
        q1.setX1Y1( 250, 200 );
        tm.addState( q1 );

        TMState q2 = new TMState( 2, null, false, true );
        q2.setX1Y1( 400, 200 );
        tm.addState( q2 );

        // transitions
        TMTransition q0q0 = new TMTransition( q0, q0,
                new TMOperation( '0', '0', TMOperationType.PUSH, '0' ),
                new TMOperation( '0', '1', TMOperationType.PUSH, '0' ),
                new TMOperation( '0', tm.getStackStartingSymbol(),
                        TMOperationType.PUSH, '0' ),
                new TMOperation( '1', '0', TMOperationType.PUSH, '1' ),
                new TMOperation( '1', '1', TMOperationType.PUSH, '1' ),
                new TMOperation( '1', tm.getStackStartingSymbol(),
                        TMOperationType.PUSH, '1' ) );
        tm.addTransition( q0q0 );

        TMTransition q0q1 = new TMTransition( q0, q1,
                TMOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, '0' ),
                TMOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, '1' ),
                TMOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING,
                        tm.getStackStartingSymbol() ) );
        tm.addTransition( q0q1 );

        TMTransition q1q1 = new TMTransition( q1, q1,
                TMOperation.getPopOperation( '0', '0' ),
                TMOperation.getPopOperation( '1', '1' ) );
        tm.addTransition( q1q1 );

        TMTransition q1q2 = new TMTransition( q1, q2,
                TMOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING,
                        tm.getStackStartingSymbol() ) );
        tm.addTransition( q1q2 );

        q0q0.setStrokeColor( new Color( 112, 48, 160 ) );
        q0q1.setStrokeColor( new Color( 0, 176, 80 ) );
        q1q1.setStrokeColor( new Color( 255, 153, 51 ) );
        q1q2.setStrokeColor( new Color( 0, 112, 192 ) );
        
        return tm;
        
    }
    
}
