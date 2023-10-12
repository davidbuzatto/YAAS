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
import br.com.davidbuzatto.yaas.model.tm.TMMovementType;
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
    
    public static TM createTMEvenPalindromeFinalState() {

        TM tm = new TM();

        // states
        TMState q0 = new TMState( 0, null, true, false );
        q0.setX1Y1( 125, 125 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        tm.addState( q0 );

        TMState q1 = new TMState( 1, null, false, false );
        q1.setX1Y1( 275, 125 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        tm.addState( q1 );

        TMState q2 = new TMState( 2, null, false, false );
        q2.setX1Y1( 425, 125 );
        q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        tm.addState( q2 );

        TMState q3 = new TMState( 3, null, false, false );
        q3.setX1Y1( 125, 275 );
        q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        tm.addState( q3 );

        TMState q4 = new TMState( 4, null, false, true );
        q4.setX1Y1( 275, 275 );
        q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        tm.addState( q4 );

        // transitions
        TMTransition q0q1 = new TMTransition( q0, q1,
                TMOperation.getMoveRightOperation( '0', 'X' ) );
        q0q1.moveLabelTo( 196, 99 );
        q0q1.rotateTargetCP( 20 );
        q0q1.moveCPsTo( 187, 105, 166, 105, 209, 105 );
        q0q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        tm.addTransition( q0q1 );

        TMTransition q1q1 = new TMTransition( q1, q1,
                TMOperation.getMoveRightOperation( '0', '0' ),
                TMOperation.getMoveRightOperation( 'Y', 'Y' ) );
        q1q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        tm.addTransition( q1q1 );

        TMTransition q1q2 = new TMTransition( q1, q2,
                TMOperation.getMoveLeftOperation( '1', 'Y' ) );
        q1q2.moveLabelTo( 347, 101 );
        q1q2.rotateTargetCP( 15 );
        q1q2.moveCPsTo( 338, 108, 317, 108, 360, 108 );
        q1q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        tm.addTransition( q1q2 );

        TMTransition q2q2 = new TMTransition( q2, q2,
                TMOperation.getMoveLeftOperation( '0', '0' ),
                TMOperation.getMoveLeftOperation( 'Y', 'Y' ) );
        q2q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        tm.addTransition( q2q2 );

        TMTransition q2q0 = new TMTransition( q2, q0,
                TMOperation.getMoveRightOperation( 'X', 'X' ) );
        q2q0.moveLabelTo( 276, 190 );
        q2q0.rotateTargetCP( -145 );
        q2q0.moveCPsTo( 277, 183, 323, 183, 230, 183 );
        q2q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        tm.addTransition( q2q0 );

        TMTransition q0q3 = new TMTransition( q0, q3,
                TMOperation.getMoveRightOperation( 'Y', 'Y' ) );
        q0q3.moveLabelTo( 144, 202 );
        q0q3.rotateTargetCP( 75 );
        q0q3.moveCPsTo( 107, 200, 107, 179, 107, 222 );
        q0q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        tm.addTransition( q0q3 );

        TMTransition q3q3 = new TMTransition( q3, q3,
                TMOperation.getMoveRightOperation( 'Y', 'Y' ) );
        q3q3.moveLabelTo( 127, 342 );
        q3q3.rotateTargetCP( -180 );
        q3q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        tm.addTransition( q3q3 );

        TMTransition q3q4 = new TMTransition( q3, q4,
                TMOperation.getMoveRightOperation( 'B', 'B' ) );
        q3q4.moveLabelTo( 196, 250 );
        q3q4.rotateTargetCP( 15 );
        q3q4.moveCPsTo( 195, 256, 174, 256, 217, 256 );
        q3q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        tm.addTransition( q3q4 );

        return tm;

    }
    
}
