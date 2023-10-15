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
    
    public static final String RECURSIVELY_ENUMERABLE_LANGUAGE_0N_1N = 
            String.format( "L = { 0%c1%c | n %c 1 }", 
            CharacterConstants.SUPER_N, 
            CharacterConstants.SUPER_N,
            CharacterConstants.GREATER_THAN_OR_EQUAL_TO );
    
    public static final String RECURSIVELY_ENUMERABLE_LANGUAGE_AN_BN_CN = 
            String.format( "L = { a%cb%cc%c | n %c 1 }", 
            CharacterConstants.SUPER_N, 
            CharacterConstants.SUPER_N,
            CharacterConstants.SUPER_N,
            CharacterConstants.GREATER_THAN_OR_EQUAL_TO );
    
    public static TM createDTM0n1nFinalState() {

        TM dtm = new TM();

        // states
        TMState q0 = new TMState( 0, null, true, false );
        q0.setX1Y1( 125, 125 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q0 );

        TMState q1 = new TMState( 1, null, false, false );
        q1.setX1Y1( 275, 125 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q1 );

        TMState q2 = new TMState( 2, null, false, false );
        q2.setX1Y1( 425, 125 );
        q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q2 );

        TMState q3 = new TMState( 3, null, false, false );
        q3.setX1Y1( 125, 275 );
        q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q3 );

        TMState q4 = new TMState( 4, null, false, true );
        q4.setX1Y1( 275, 275 );
        q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q4 );

        // transitions
        TMTransition q0q1 = new TMTransition( q0, q1,
                TMOperation.getMoveRightOperation( '0', 'X' ) );
        q0q1.moveLabelTo( 196, 99 );
        q0q1.rotateTargetCP( 20 );
        q0q1.moveCPsTo( 187, 105, 166, 105, 209, 105 );
        q0q1.setStrokeColor( new Color( 51, 153, 0, 255 ) );
        dtm.addTransition( q0q1 );

        TMTransition q1q1 = new TMTransition( q1, q1,
                TMOperation.getMoveRightOperation( '0', '0' ),
                TMOperation.getMoveRightOperation( 'Y', 'Y' ) );
        q1q1.setStrokeColor( new Color( 153, 0, 153, 255 ) );
        dtm.addTransition( q1q1 );

        TMTransition q1q2 = new TMTransition( q1, q2,
                TMOperation.getMoveLeftOperation( '1', 'Y' ) );
        q1q2.moveLabelTo( 347, 101 );
        q1q2.rotateTargetCP( 15 );
        q1q2.moveCPsTo( 338, 108, 317, 108, 360, 108 );
        q1q2.setStrokeColor( new Color( 0, 51, 153, 255 ) );
        dtm.addTransition( q1q2 );

        TMTransition q2q2 = new TMTransition( q2, q2,
                TMOperation.getMoveLeftOperation( '0', '0' ),
                TMOperation.getMoveLeftOperation( 'Y', 'Y' ) );
        q2q2.setStrokeColor( new Color( 0, 153, 153, 255 ) );
        dtm.addTransition( q2q2 );

        TMTransition q2q0 = new TMTransition( q2, q0,
                TMOperation.getMoveRightOperation( 'X', 'X' ) );
        q2q0.moveLabelTo( 276, 190 );
        q2q0.rotateTargetCP( -145 );
        q2q0.moveCPsTo( 277, 183, 323, 183, 230, 183 );
        q2q0.setStrokeColor( new Color( 255, 0, 102, 255 ) );
        dtm.addTransition( q2q0 );

        TMTransition q0q3 = new TMTransition( q0, q3,
                TMOperation.getMoveRightOperation( 'Y', 'Y' ) );
        q0q3.moveLabelTo( 144, 202 );
        q0q3.rotateTargetCP( 75 );
        q0q3.moveCPsTo( 107, 200, 107, 179, 107, 222 );
        q0q3.setStrokeColor( new Color( 255, 102, 0, 255 ) );
        dtm.addTransition( q0q3 );

        TMTransition q3q3 = new TMTransition( q3, q3,
                TMOperation.getMoveRightOperation( 'Y', 'Y' ) );
        q3q3.moveLabelTo( 127, 342 );
        q3q3.rotateTargetCP( -180 );
        q3q3.setStrokeColor( new Color( 204, 0, 51, 255 ) );
        dtm.addTransition( q3q3 );

        TMTransition q3q4 = new TMTransition( q3, q4,
                TMOperation.getMoveRightOperation( 'B', 'B' ) );
        q3q4.moveLabelTo( 196, 250 );
        q3q4.rotateTargetCP( 15 );
        q3q4.moveCPsTo( 195, 256, 174, 256, 217, 256 );
        q3q4.setStrokeColor( new Color( 102, 102, 255, 255 ) );
        dtm.addTransition( q3q4 );

        return dtm;

    }
    
    public static TM createDTMMonusHalt() {

        TM dtm = new TM();

        // states
        TMState q0 = new TMState( 0, null, true, false );
        q0.setX1Y1( 123, 149 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q0 );

        TMState q1 = new TMState( 1, null, false, false );
        q1.setX1Y1( 273, 149 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q1 );

        TMState q2 = new TMState( 2, null, false, false );
        q2.setX1Y1( 423, 149 );
        q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q2 );

        TMState q3 = new TMState( 3, null, false, false );
        q3.setX1Y1( 573, 149 );
        q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q3 );

        TMState q4 = new TMState( 4, null, false, false );
        q4.setX1Y1( 423, 299 );
        q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q4 );

        TMState q5 = new TMState( 5, null, false, false );
        q5.setX1Y1( 123, 299 );
        q5.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q5 );

        TMState q6 = new TMState( 6, null, false, false );
        q6.setX1Y1( 273, 299 );
        q6.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q6 );

        // transitions
        TMTransition q0q1 = new TMTransition( q0, q1,
                TMOperation.getMoveRightOperation( '0', 'B' ) );
        q0q1.moveCPsTo( 189, 137, 168, 137, 211, 137 );
        q0q1.setStrokeColor( new Color( 51, 153, 0, 255 ) );
        dtm.addTransition( q0q1 );

        TMTransition q1q2 = new TMTransition( q1, q2,
                TMOperation.getMoveRightOperation( '1', '1' ) );
        q1q2.moveCPsTo( 342, 138, 321, 138, 364, 138 );
        q1q2.setStrokeColor( new Color( 0, 0, 255, 255 ) );
        dtm.addTransition( q1q2 );

        TMTransition q1q1 = new TMTransition( q1, q1,
                TMOperation.getMoveRightOperation( '0', '0' ) );
        q1q1.setStrokeColor( new Color( 0, 102, 51, 255 ) );
        dtm.addTransition( q1q1 );

        TMTransition q0q5 = new TMTransition( q0, q5,
                TMOperation.getMoveRightOperation( '1', 'B' ) );
        q0q5.moveLabelTo( 87, 219 );
        q0q5.rotateTargetCP( 80 );
        q0q5.moveCPsTo( 110, 220, 110, 199, 110, 242 );
        q0q5.setStrokeColor( new Color( 0, 102, 102, 255 ) );
        dtm.addTransition( q0q5 );

        TMTransition q5q5 = new TMTransition( q5, q5,
                TMOperation.getMoveRightOperation( '0', 'B' ),
                TMOperation.getMoveRightOperation( '1', 'B' ) );
        q5q5.moveLabelTo( 121, 383 );
        q5q5.rotateTargetCP( -180 );
        q5q5.setStrokeColor( new Color( 0, 204, 204, 255 ) );
        dtm.addTransition( q5q5 );

        TMTransition q2q2 = new TMTransition( q2, q2,
                TMOperation.getMoveRightOperation( '1', '1' ) );
        q2q2.setStrokeColor( new Color( 0, 51, 153, 255 ) );
        dtm.addTransition( q2q2 );

        TMTransition q2q3 = new TMTransition( q2, q3,
                TMOperation.getMoveLeftOperation( '0', '1' ) );
        q2q3.moveCPsTo( 488, 136, 467, 136, 510, 136 );
        q2q3.setStrokeColor( new Color( 153, 0, 255, 255 ) );
        dtm.addTransition( q2q3 );

        TMTransition q2q4 = new TMTransition( q2, q4,
                TMOperation.getMoveLeftOperation( 'B', 'B' ) );
        q2q4.moveLabelTo( 386, 219 );
        q2q4.rotateTargetCP( 80 );
        q2q4.moveCPsTo( 410, 221, 410, 200, 410, 243 );
        q2q4.setStrokeColor( new Color( 204, 102, 0, 255 ) );
        dtm.addTransition( q2q4 );

        TMTransition q3q3 = new TMTransition( q3, q3,
                TMOperation.getMoveLeftOperation( '0', '0' ),
                TMOperation.getMoveLeftOperation( '1', '1' ) );
        q3q3.setStrokeColor( new Color( 51, 0, 102, 255 ) );
        dtm.addTransition( q3q3 );

        TMTransition q4q4 = new TMTransition( q4, q4,
                TMOperation.getMoveLeftOperation( '0', '0' ),
                TMOperation.getMoveLeftOperation( '1', 'B' ) );
        q4q4.moveLabelTo( 422, 383 );
        q4q4.rotateTargetCP( -180 );
        q4q4.setStrokeColor( new Color( 255, 153, 0, 255 ) );
        dtm.addTransition( q4q4 );

        TMTransition q5q6 = new TMTransition( q5, q6,
                TMOperation.getMoveRightOperation( 'B', 'B' ) );
        q5q6.rotateTargetCP( 5 );
        q5q6.moveCPsTo( 185, 288, 164, 288, 207, 288 );
        q5q6.setStrokeColor( new Color( 255, 102, 102, 255 ) );
        dtm.addTransition( q5q6 );

        TMTransition q4q6 = new TMTransition( q4, q6,
                TMOperation.getMoveRightOperation( 'B', '0' ) );
        q4q6.rotateTargetCP( 170 );
        q4q6.moveCPsTo( 354, 288, 375, 288, 332, 288 );
        q4q6.setStrokeColor( new Color( 255, 204, 0, 255 ) );
        dtm.addTransition( q4q6 );

        TMTransition q3q0 = new TMTransition( q3, q0,
                TMOperation.getMoveRightOperation( 'B', 'B' ) );
        q3q0.moveLabelTo( 347, 46 );
        q3q0.rotateTargetCP( 125 );
        q3q0.moveCPsTo( 358, 40, 488, 30, 239, 30 );
        q3q0.setStrokeColor( new Color( 153, 0, 102, 255 ) );
        dtm.addTransition( q3q0 );

        return dtm;

    }
    
    public static TM createDTMInfiniteLoopFinalState() {

        TM dtm = new TM();

        // states
        TMState q0 = new TMState( 0, null, true, false );
        q0.setX1Y1( 121, 136 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q0 );

        TMState q1 = new TMState( 1, null, false, true );
        q1.setX1Y1( 271, 136 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q1 );

        // transitions
        TMTransition q0q0 = new TMTransition( q0, q0,
                TMOperation.getMoveRightOperation( '0', '0' ) );
        q0q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q0q0 );

        TMTransition q0q1 = new TMTransition( q0, q1,
                TMOperation.getMoveLeftOperation( '1', '1' ) );
        q0q1.moveLabelTo( 192, 100 );
        q0q1.rotateTargetCP( 10 );
        q0q1.moveCPsTo( 194, 104, 173, 104, 216, 104 );
        q0q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q0q1 );

        TMTransition q1q1 = new TMTransition( q1, q1,
                TMOperation.getMoveRightOperation( '0', '0' ) );
        q1q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q1q1 );

        TMTransition q1q0 = new TMTransition( q1, q0,
                TMOperation.getMoveLeftOperation( '1', '1' ) );
        q1q0.moveLabelTo( 192, 183 );
        q1q0.rotateTargetCP( -145 );
        q1q0.moveCPsTo( 196, 169, 217, 169, 174, 169 );
        q1q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q1q0 );

        return dtm;

    }
    
    public static TM createDTMExample() {

        TM dtm = new TM();

        // states
        TMState q0 = new TMState( 0, null, true, false );
        q0.setX1Y1( 125, 125 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q0 );

        TMState q1 = new TMState( 1, null, false, false );
        q1.setX1Y1( 275, 125 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q1 );

        TMState q2 = new TMState( 2, null, false, false );
        q2.setX1Y1( 425, 125 );
        q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q2 );

        TMState q3 = new TMState( 3, null, false, false );
        q3.setX1Y1( 275, 275 );
        q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q3 );

        TMState q4 = new TMState( 4, "qs", false, true );
        q4.setX1Y1( 575, 125 );
        q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q4 );

        TMState q5 = new TMState( 5, "qn", false, true );
        q5.setX1Y1( 425, 275 );
        q5.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q5 );

        // transitions
        TMTransition q0q1 = new TMTransition( q0, q1,
                TMOperation.getMoveLeftOperation( 'B', 'B' ) );
        q0q1.rotateTargetCP( 10 );
        q0q1.moveCPsTo( 198, 108, 177, 108, 220, 108 );
        q0q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q0q1 );

        TMTransition q1q2 = new TMTransition( q1, q2,
                TMOperation.getMoveLeftOperation( '0', 'B' ) );
        q1q2.rotateTargetCP( 5 );
        q1q2.moveCPsTo( 344, 109, 323, 109, 366, 109 );
        q1q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q1q2 );

        TMTransition q2q4 = new TMTransition( q2, q4,
                TMOperation.getMoveLeftOperation( '0', 'B' ) );
        q2q4.rotateTargetCP( 5 );
        q2q4.moveCPsTo( 494, 111, 473, 111, 516, 111 );
        q2q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q2q4 );

        TMTransition q2q5 = new TMTransition( q2, q5,
                TMOperation.getMoveLeftOperation( '1', 'B' ),
                TMOperation.getMoveLeftOperation( 'B', 'B' ) );
        q2q5.moveLabelTo( 463, 209 );
        q2q5.rotateTargetCP( 95 );
        q2q5.moveCPsTo( 436, 195, 436, 174, 436, 217 );
        q2q5.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q2q5 );

        TMTransition q1q3 = new TMTransition( q1, q3,
                TMOperation.getMoveLeftOperation( '1', 'B' ) );
        q1q3.moveLabelTo( 235, 203 );
        q1q3.rotateTargetCP( 80 );
        q1q3.moveCPsTo( 263, 196, 263, 175, 263, 218 );
        q1q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q1q3 );

        TMTransition q1q5 = new TMTransition( q1, q5,
                TMOperation.getMoveLeftOperation( 'B', 'B' ) );
        q1q5.moveLabelTo( 363, 200 );
        q1q5.moveCPsTo( 332, 206, 310, 184, 355, 229 );
        q1q5.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q1q5 );

        TMTransition q3q5 = new TMTransition( q3, q5,
                TMOperation.getMoveLeftOperation( '0', 'B' ),
                TMOperation.getMoveLeftOperation( '1', 'B' ),
                TMOperation.getMoveLeftOperation( 'B', 'B' ) );
        q3q5.moveLabelTo( 344, 339 );
        q3q5.rotateTargetCP( -10 );
        q3q5.moveCPsTo( 346, 291, 325, 291, 368, 291 );
        q3q5.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q3q5 );

        TMTransition q0q0 = new TMTransition( q0, q0,
                TMOperation.getMoveRightOperation( '0', '0' ),
                TMOperation.getMoveRightOperation( '1', '1' ) );
        q0q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q0q0 );

        return dtm;

    }
    
    public static TM createDTM01SameFinalState() {

        TM dtm = new TM();

        // states
        TMState q0 = new TMState( 0, null, true, false );
        q0.setX1Y1( 100, 200 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q0 );

        TMState q1 = new TMState( 1, null, false, false );
        q1.setX1Y1( 250, 100 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q1 );

        TMState q2 = new TMState( 2, null, false, false );
        q2.setX1Y1( 400, 100 );
        q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q2 );

        TMState q3 = new TMState( 3, null, false, false );
        q3.setX1Y1( 250, 300 );
        q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q3 );

        TMState q4 = new TMState( 4, null, false, false );
        q4.setX1Y1( 400, 300 );
        q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q4 );

        TMState q5 = new TMState( 5, null, false, true );
        q5.setX1Y1( 450, 200 );
        q5.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q5 );

        // transitions
        TMTransition q0q0 = new TMTransition( q0, q0,
                TMOperation.getMoveRightOperation( 'X', 'B' ),
                TMOperation.getMoveRightOperation( 'Y', 'B' ) );
        q0q0.rotateTargetCP( -25 );
        q0q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q0q0 );

        TMTransition q0q1 = new TMTransition( q0, q1,
                TMOperation.getMoveRightOperation( '0', 'B' ) );
        q0q1.moveLabelTo( 164, 134 );
        q0q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q0q1 );

        TMTransition q0q3 = new TMTransition( q0, q3,
                TMOperation.getMoveRightOperation( '1', 'B' ) );
        q0q3.moveLabelTo( 164, 275 );
        q0q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q0q3 );

        TMTransition q1q2 = new TMTransition( q1, q2,
                TMOperation.getMoveLeftOperation( '1', 'Y' ) );
        q1q2.moveLabelTo( 319, 90 );
        q1q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q1q2 );

        TMTransition q1q1 = new TMTransition( q1, q1,
                TMOperation.getMoveRightOperation( '0', '0' ),
                TMOperation.getMoveRightOperation( 'Y', 'Y' ) );
        q1q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q1q1 );

        TMTransition q2q2 = new TMTransition( q2, q2,
                TMOperation.getMoveLeftOperation( '0', '0' ),
                TMOperation.getMoveLeftOperation( 'Y', 'Y' ) );
        q2q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q2q2 );

        TMTransition q2q0 = new TMTransition( q2, q0,
                TMOperation.getMoveRightOperation( 'B', 'B' ) );
        q2q0.moveLabelTo( 251, 174 );
        q2q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q2q0 );

        TMTransition q3q3 = new TMTransition( q3, q3,
                TMOperation.getMoveRightOperation( '1', '1' ),
                TMOperation.getMoveRightOperation( 'X', 'X' ) );
        q3q3.moveLabelTo( 250, 383 );
        q3q3.rotateTargetCP( -180 );
        q3q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q3q3 );

        TMTransition q4q4 = new TMTransition( q4, q4,
                TMOperation.getMoveLeftOperation( '1', '1' ),
                TMOperation.getMoveLeftOperation( 'X', 'X' ) );
        q4q4.moveLabelTo( 375, 383 );
        q4q4.rotateTargetCP( -180 );
        q4q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q4q4 );

        TMTransition q3q4 = new TMTransition( q3, q4,
                TMOperation.getMoveLeftOperation( '0', 'X' ) );
        q3q4.moveLabelTo( 324, 318 );
        q3q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q3q4 );

        TMTransition q4q0 = new TMTransition( q4, q0,
                TMOperation.getMoveRightOperation( 'B', 'B' ) );
        q4q0.moveLabelTo( 246, 233 );
        q4q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q4q0 );

        TMTransition q0q5 = new TMTransition( q0, q5,
                TMOperation.getMoveRightOperation( 'B', 'B' ) );
        q0q5.moveLabelTo( 346, 190 );
        q0q5.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q0q5 );

        return dtm;

    }
    
    public static TM createDTMAnBnCnFinalState() {

        TM dtm = new TM();

        // states
        TMState q0 = new TMState( 0, null, true, false );
        q0.setX1Y1( 100, 225 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q0 );

        TMState q1 = new TMState( 1, null, false, false );
        q1.setX1Y1( 225, 125 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q1 );

        TMState q2 = new TMState( 2, null, false, false );
        q2.setX1Y1( 375, 125 );
        q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q2 );

        TMState q3 = new TMState( 3, null, false, false );
        q3.setX1Y1( 525, 125 );
        q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q3 );

        TMState q4 = new TMState( 4, null, false, false );
        q4.setX1Y1( 225, 325 );
        q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q4 );

        TMState q5 = new TMState( 5, null, false, true );
        q5.setX1Y1( 375, 325 );
        q5.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q5 );

        // transitions
        TMTransition q0q1 = new TMTransition( q0, q1,
                TMOperation.getMoveRightOperation( 'a', 'B' ) );
        q0q1.moveLabelTo( 141, 166 );
        q0q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q0q1 );

        TMTransition q1q1 = new TMTransition( q1, q1,
                TMOperation.getMoveRightOperation( 'X', 'X' ),
                TMOperation.getMoveRightOperation( 'a', 'a' ) );
        q1q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q1q1 );

        TMTransition q1q2 = new TMTransition( q1, q2,
                TMOperation.getMoveRightOperation( 'b', 'X' ) );
        q1q2.moveLabelTo( 299, 116 );
        q1q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q1q2 );

        TMTransition q2q2 = new TMTransition( q2, q2,
                TMOperation.getMoveRightOperation( 'Y', 'Y' ),
                TMOperation.getMoveRightOperation( 'b', 'b' ) );
        q2q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q2q2 );

        TMTransition q2q3 = new TMTransition( q2, q3,
                TMOperation.getMoveLeftOperation( 'c', 'Y' ) );
        q2q3.moveLabelTo( 450, 116 );
        q2q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q2q3 );

        TMTransition q3q3 = new TMTransition( q3, q3,
                TMOperation.getMoveLeftOperation( 'X', 'X' ),
                TMOperation.getMoveLeftOperation( 'Y', 'Y' ),
                TMOperation.getMoveLeftOperation( 'a', 'a' ),
                TMOperation.getMoveLeftOperation( 'b', 'b' ) );
        q3q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q3q3 );

        TMTransition q3q0 = new TMTransition( q3, q0,
                TMOperation.getMoveRightOperation( 'B', 'B' ) );
        q3q0.moveLabelTo( 273, 176 );
        q3q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q3q0 );

        TMTransition q0q4 = new TMTransition( q0, q4,
                TMOperation.getMoveRightOperation( 'X', 'B' ) );
        q0q4.moveLabelTo( 140, 291 );
        q0q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q0q4 );

        TMTransition q4q4 = new TMTransition( q4, q4,
                TMOperation.getMoveRightOperation( 'X', 'X' ),
                TMOperation.getMoveRightOperation( 'Y', 'Y' ) );
        q4q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q4q4 );

        TMTransition q4q5 = new TMTransition( q4, q5,
                TMOperation.getMoveRightOperation( 'B', 'B' ) );
        q4q5.moveLabelTo( 300, 316 );
        q4q5.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q4q5 );

        return dtm;

    }
    
    public static TM createDTMDivideHalt() {

        TM dtm = new TM();

        // states
        TMState q0 = new TMState( 0, null, true, false );
        q0.setX1Y1( 125, 275 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q0 );

        TMState q1 = new TMState( 1, null, false, false );
        q1.setX1Y1( 275, 275 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q1 );

        TMState q2 = new TMState( 2, null, false, false );
        q2.setX1Y1( 425, 275 );
        q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q2 );

        TMState q3 = new TMState( 3, null, false, false );
        q3.setX1Y1( 575, 275 );
        q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q3 );

        TMState q4 = new TMState( 4, null, false, false );
        q4.setX1Y1( 725, 275 );
        q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q4 );

        TMState q5 = new TMState( 5, null, false, false );
        q5.setX1Y1( 425, 425 );
        q5.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q5 );

        TMState q6 = new TMState( 6, null, false, false );
        q6.setX1Y1( 575, 425 );
        q6.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q6 );

        TMState q7 = new TMState( 7, null, false, false );
        q7.setX1Y1( 725, 425 );
        q7.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q7 );

        TMState q8 = new TMState( 8, null, false, false );
        q8.setX1Y1( 575, 125 );
        q8.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q8 );

        TMState q9 = new TMState( 9, null, false, false );
        q9.setX1Y1( 725, 125 );
        q9.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q9 );

        TMState q10 = new TMState( 10, null, false, false );
        q10.setX1Y1( 875, 125 );
        q10.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q10 );

        TMState q11 = new TMState( 11, null, false, false );
        q11.setX1Y1( 1025, 125 );
        q11.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addState( q11 );

        // transitions
        TMTransition q0q0 = new TMTransition( q0, q0,
                TMOperation.getMoveRightOperation( '0', '0' ) );
        q0q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q0q0 );

        TMTransition q0q1 = new TMTransition( q0, q1,
                TMOperation.getMoveRightOperation( '1', '1' ) );
        q0q1.moveLabelTo( 201, 265 );
        q0q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q0q1 );

        TMTransition q1q1 = new TMTransition( q1, q1,
                TMOperation.getMoveRightOperation( 'X', 'X' ) );
        q1q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q1q1 );

        TMTransition q1q2 = new TMTransition( q1, q2,
                TMOperation.getMoveLeftOperation( '0', 'X' ) );
        q1q2.moveLabelTo( 350, 266 );
        q1q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q1q2 );

        TMTransition q2q2 = new TMTransition( q2, q2,
                TMOperation.getMoveLeftOperation( '0', '0' ),
                TMOperation.getMoveLeftOperation( '1', '1' ),
                TMOperation.getMoveLeftOperation( 'X', 'X' ) );
        q2q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q2q2 );

        TMTransition q2q3 = new TMTransition( q2, q3,
                TMOperation.getMoveRightOperation( 'B', 'B' ),
                TMOperation.getMoveRightOperation( 'Y', 'Y' ) );
        q2q3.moveLabelTo( 499, 265 );
        q2q3.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q2q3 );

        TMTransition q3q4 = new TMTransition( q3, q4,
                TMOperation.getMoveRightOperation( '0', 'Y' ) );
        q3q4.moveLabelTo( 649, 265 );
        q3q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q3q4 );

        TMTransition q4q4 = new TMTransition( q4, q4,
                TMOperation.getMoveRightOperation( '0', '0' ),
                TMOperation.getMoveRightOperation( '1', '1' ) );
        q4q4.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q4q4 );

        TMTransition q1q5 = new TMTransition( q1, q5,
                TMOperation.getMoveRightOperation( '1', '1' ) );
        q1q5.moveLabelTo( 337, 375 );
        q1q5.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q1q5 );

        TMTransition q5q6 = new TMTransition( q5, q6,
                TMOperation.getMoveLeftOperation( 'B', '0' ) );
        q5q6.moveLabelTo( 501, 416 );
        q5q6.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q5q6 );

        TMTransition q5q5 = new TMTransition( q5, q5,
                TMOperation.getMoveRightOperation( '0', '0' ) );
        q5q5.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q5q5 );

        TMTransition q6q6 = new TMTransition( q6, q6,
                TMOperation.getMoveLeftOperation( '0', '0' ) );
        q6q6.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q6q6 );

        TMTransition q6q7 = new TMTransition( q6, q7,
                TMOperation.getMoveLeftOperation( '1', '1' ) );
        q6q7.moveLabelTo( 653, 415 );
        q6q7.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q6q7 );

        TMTransition q7q7 = new TMTransition( q7, q7,
                TMOperation.getMoveLeftOperation( 'X', '0' ) );
        q7q7.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q7q7 );

        TMTransition q4q1 = new TMTransition( q4, q1,
                TMOperation.getMoveRightOperation( 'X', 'X' ) );
        q4q1.moveLabelTo( 501, 314 );
        q4q1.rotateTargetCP( -160 );
        q4q1.moveCPsTo( 501, 340, 572, 340, 429, 340 );
        q4q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q4q1 );

        TMTransition q7q1 = new TMTransition( q7, q1,
                TMOperation.getMoveRightOperation( '1', '1' ) );
        q7q1.moveLabelTo( 298, 422 );
        q7q1.rotateTargetCP( -95 );
        q7q1.moveCPsTo( 388, 507, 459, 531, 316, 482 );
        q7q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q7q1 );

        TMTransition q3q8 = new TMTransition( q3, q8,
                TMOperation.getMoveRightOperation( '1', '1' ) );
        q3q8.moveLabelTo( 546, 203 );
        q3q8.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q3q8 );

        TMTransition q8q8 = new TMTransition( q8, q8,
                TMOperation.getMoveRightOperation( '0', '0' ),
                TMOperation.getMoveRightOperation( 'X', 'X' ) );
        q8q8.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q8q8 );

        TMTransition q8q9 = new TMTransition( q8, q9,
                TMOperation.getMoveLeftOperation( '1', 'B' ) );
        q8q9.moveLabelTo( 651, 116 );
        q8q9.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q8q9 );

        TMTransition q9q10 = new TMTransition( q9, q10,
                TMOperation.getMoveRightOperation( 'B', 'B' ) );
        q9q10.moveLabelTo( 801, 115 );
        q9q10.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q9q10 );

        TMTransition q10q10 = new TMTransition( q10, q10,
                TMOperation.getMoveRightOperation( 'B', 'B' ) );
        q10q10.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q10q10 );

        TMTransition q10q11 = new TMTransition( q10, q11,
                TMOperation.getMoveRightOperation( '0', '0' ) );
        q10q11.moveLabelTo( 951, 116 );
        q10q11.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q10q11 );

        TMTransition q9q9 = new TMTransition( q9, q9,
                TMOperation.getMoveLeftOperation( '0', 'B' ),
                TMOperation.getMoveLeftOperation( '1', 'B' ),
                TMOperation.getMoveLeftOperation( 'X', 'B' ),
                TMOperation.getMoveLeftOperation( 'Y', 'B' ) );
        q9q9.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        dtm.addTransition( q9q9 );

        return dtm;

    }
    
    public static TM createNTM() {

        TM ntm = new TM();

        // states
        TMState q0 = new TMState( 0, null, true, false );
        q0.setX1Y1( 100, 150 );
        q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        ntm.addState( q0 );

        TMState q1 = new TMState( 1, null, false, false );
        q1.setX1Y1( 250, 150 );
        q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        ntm.addState( q1 );

        TMState q2 = new TMState( 2, null, false, true );
        q2.setX1Y1( 400, 150 );
        q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        ntm.addState( q2 );

        // transitions
        TMTransition q0q0 = new TMTransition( q0, q0,
                TMOperation.getMoveRightOperation( '0', '0' ),
                TMOperation.getMoveRightOperation( '1', '1' ) );
        q0q0.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        ntm.addTransition( q0q0 );

        TMTransition q0q1 = new TMTransition( q0, q1,
                TMOperation.getMoveRightOperation( '0', '0' ) );
        q0q1.moveLabelTo( 176, 141 );
        q0q1.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        ntm.addTransition( q0q1 );

        TMTransition q1q2 = new TMTransition( q1, q2,
                TMOperation.getMoveRightOperation( '0', '0' ) );
        q1q2.moveLabelTo( 327, 141 );
        q1q2.setStrokeColor( new Color( 0, 0, 0, 255 ) );
        ntm.addTransition( q1q2 );

        return ntm;

    }
    
}
