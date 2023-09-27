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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.davidbuzatto.yaas.gui.fa.examples;

import br.com.davidbuzatto.yaas.gui.fa.FA;
import br.com.davidbuzatto.yaas.gui.fa.FAState;
import br.com.davidbuzatto.yaas.gui.fa.FATransition;
import br.com.davidbuzatto.yaas.gui.fa.algorithms.FAArrangement;
import br.com.davidbuzatto.yaas.gui.fa.algorithms.FACommon;

/**
 * A set of methods to construct example DFAs for testing purposes of 
 * the minimization algorithm.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAExamplesForMinimizationTest {
    
    public static FA createDFAForMinimizationTest01() {
        
        FA dfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), true, false );
        q0.setX1( 100 );
        q0.setY1( 100 );
        
        FAState q1 = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        q1.setX1( 250 );
        q1.setY1( 100 );
        
        FAState q2 = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, true );
        q2.setX1( 400 );
        q2.setY1( 100 );
        
        FAState q3 = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        q3.setX1( 550 );
        q3.setY1( 100 );
        
        FAState q4 = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        q4.setX1( 100 );
        q4.setY1( 250 );
        
        FAState q5 = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        q5.setX1( 250 );
        q5.setY1( 250 );
        
        FAState q6 = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        q6.setX1( 400 );
        q6.setY1( 250 );
        
        FAState q7 = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        q7.setX1( 550 );
        q7.setY1( 250 );
        
        dfa.addState( q0 );
        dfa.addState( q1 );
        dfa.addState( q2 );
        dfa.addState( q3 );
        dfa.addState( q4 );
        dfa.addState( q5 );
        dfa.addState( q6 );
        dfa.addState( q7 );
        
        dfa.addTransition( new FATransition( q0, q1, '0' ) );
        dfa.addTransition( new FATransition( q0, q5, '1' ) );
        dfa.addTransition( new FATransition( q1, q2, '1' ) );
        dfa.addTransition( new FATransition( q1, q6, '0' ).moveLabelX( -40 ) );
        dfa.addTransition( new FATransition( q2, q2, '1' ) );
        dfa.addTransition( new FATransition( q2, q0, '0' )
                .bendByCenterCPY( -70 ).rotateTargetCP( 160 ) );
        dfa.addTransition( new FATransition( q3, q2, '0' ) );
        dfa.addTransition( new FATransition( q3, q6, '1' ).moveLabelX( 40 ) );
        dfa.addTransition( new FATransition( q4, q5, '1' ) );
        dfa.addTransition( new FATransition( q4, q7, '0' )
                .bendByCenterCPY( 120 ).rotateTargetCP( -45 ) );
        dfa.addTransition( new FATransition( q5, q2, '0' ).moveLabel( -40, 35 ) );
        dfa.addTransition( new FATransition( q5, q6, '1' ) );
        dfa.addTransition( new FATransition( q6, q6, '0' ).rotateTargetCP( 180 ) );
        dfa.addTransition( new FATransition( q6, q4, '1' )
                .bendByCenterCPY( 70 ).rotateTargetCP( -160 ) );
        dfa.addTransition( new FATransition( q7, q2, '1' ).moveLabel( 40, 35 ) );
        dfa.addTransition( new FATransition( q7, q6, '0' ) );
        
        //fa.addTransition( new FATransition( s5, s1, '2' ) );
        
        // transitivity
        /*FAState q8 = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ )+ "(E)",
                false, false );
        q8.setX1( 100 );
        q8.setY1( 400 );
        
        FAState q9 = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ) + "(B)",
                false, false );
        q9.setX1( 250 );
        q9.setY1( 400 );
        
        dfa.addState( q8 );
        dfa.addState( q9 );
        dfa.addTransition( new FATransition( q6, q8, '1' ) );
        dfa.addTransition( new FATransition( q8, q5, '1' ) );
        dfa.addTransition( new FATransition( q8, q7, '0' ) );
        dfa.addTransition( new FATransition( q0, q9, '0' ) );
        dfa.addTransition( new FATransition( q9, q6, '0' ) );
        dfa.addTransition( new FATransition( q9, q2, '1' ) );*/
        
        return dfa;
        
    }
    
    public static FA createDFAForMinimizationTest02() {
        
        /*************************
                    0    1
             ->A    B    A
               B    A    C
               C    D    B
              *D    D    A
               E    D    F
               F    G    E
               G    F    G
               H    G    D
        *************************/
        
        /*FA dfa = new FA();
        int currentState = 0;
        
        FAState a = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), true, false );
        FAState b = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        FAState c = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        FAState d = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, true );
        FAState e = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        FAState f = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        FAState g = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        FAState h = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        
        dfa.addState( a );
        dfa.addState( b );
        dfa.addState( c );
        dfa.addState( d );
        dfa.addState( e );
        dfa.addState( f );
        dfa.addState( g );
        dfa.addState( h );
        
        dfa.addTransition( new FATransition( a, b, '0' ) );
        dfa.addTransition( new FATransition( a, a, '1' ) );
        dfa.addTransition( new FATransition( b, a, '0' ) );
        dfa.addTransition( new FATransition( b, c, '1' ) );
        dfa.addTransition( new FATransition( c, d, '0' ) );
        dfa.addTransition( new FATransition( c, b, '1' ) );
        dfa.addTransition( new FATransition( d, d, '0' ) );
        dfa.addTransition( new FATransition( d, a, '1' ) );
        dfa.addTransition( new FATransition( e, d, '0' ) );
        dfa.addTransition( new FATransition( e, f, '1' ) );
        dfa.addTransition( new FATransition( f, g, '0' ) );
        dfa.addTransition( new FATransition( f, e, '1' ) );
        dfa.addTransition( new FATransition( g, f, '0' ) );
        dfa.addTransition( new FATransition( g, g, '1' ) );
        dfa.addTransition( new FATransition( h, g, '0' ) );
        dfa.addTransition( new FATransition( h, d, '1' ) );
        
        FACommon.arrangeRectangularly( dfa, 100, 100, 4, 150 );
        
        return dfa;*/
        
        FA dfa = new FA();

        FAState q0 = new FAState( "q0", "A", true, false );
        q0.setX1Y1( 100, 100 );
        dfa.addState( q0 );

        FAState q1 = new FAState( "q1", "B", false, false );
        q1.setX1Y1( 250, 100 );
        dfa.addState( q1 );

        FAState q2 = new FAState( "q2", "C", false, false );
        q2.setX1Y1( 400, 100 );
        dfa.addState( q2 );

        FAState q3 = new FAState( "q3", "D", false, true );
        q3.setX1Y1( 550, 100 );
        dfa.addState( q3 );

        FAState q4 = new FAState( "q4", "E", false, false );
        q4.setX1Y1( 100, 250 );
        dfa.addState( q4 );

        FAState q5 = new FAState( "q5", "F", false, false );
        q5.setX1Y1( 250, 250 );
        dfa.addState( q5 );

        FAState q6 = new FAState( "q6", "G", false, false );
        q6.setX1Y1( 400, 250 );
        dfa.addState( q6 );

        FAState q7 = new FAState( "q7", "H", false, false );
        q7.setX1Y1( 550, 250 );
        dfa.addState( q7 );


        FATransition q0q1 = new FATransition( q0, q1, '0' );
        q0q1.moveLabelTo( 215, 67 );
        q0q1.rotateTargetCP( 30 );
        q0q1.moveCPsTo( 178, 65, 157, 65, 200, 65 );
        dfa.addTransition( q0q1 );

        FATransition q0q0 = new FATransition( q0, q0, '1' );
        dfa.addTransition( q0q0 );

        FATransition q1q0 = new FATransition( q1, q0, '0' );
        q1q0.rotateTargetCP( -155 );
        q1q0.moveCPsTo( 176, 132, 197, 132, 154, 132 );
        dfa.addTransition( q1q0 );

        FATransition q1q2 = new FATransition( q1, q2, '1' );
        q1q2.rotateTargetCP( 20 );
        q1q2.moveCPsTo( 329, 69, 308, 69, 351, 69 );
        dfa.addTransition( q1q2 );

        FATransition q2q3 = new FATransition( q2, q3, '0' );
        dfa.addTransition( q2q3 );

        FATransition q2q1 = new FATransition( q2, q1, '1' );
        q2q1.rotateTargetCP( -155 );
        q2q1.moveCPsTo( 326, 132, 347, 132, 304, 132 );
        dfa.addTransition( q2q1 );

        FATransition q3q3 = new FATransition( q3, q3, '0' );
        dfa.addTransition( q3q3 );

        FATransition q3q0 = new FATransition( q3, q0, '1' );
        q3q0.moveLabelTo( 324, 28 );
        q3q0.rotateTargetCP( 130 );
        q3q0.moveCPsTo( 337, 25, 408, 25, 265, 25 );
        dfa.addTransition( q3q0 );

        FATransition q4q3 = new FATransition( q4, q3, '0' );
        q4q3.moveCPsTo( 218, 166, 147, 190, 290, 141 );
        dfa.addTransition( q4q3 );

        FATransition q4q5 = new FATransition( q4, q5, '1' );
        q4q5.moveLabelTo( 209, 219 );
        q4q5.rotateTargetCP( 25 );
        q4q5.moveCPsTo( 171, 217, 150, 217, 193, 217 );
        dfa.addTransition( q4q5 );

        FATransition q5q6 = new FATransition( q5, q6, '0' );
        q5q6.moveLabelTo( 327, 210 );
        q5q6.rotateTargetCP( 20 );
        q5q6.moveCPsTo( 328, 219, 307, 219, 350, 219 );
        dfa.addTransition( q5q6 );

        FATransition q5q4 = new FATransition( q5, q4, '1' );
        q5q4.rotateTargetCP( -150 );
        q5q4.moveCPsTo( 171, 283, 192, 283, 149, 283 );
        dfa.addTransition( q5q4 );

        FATransition q6q5 = new FATransition( q6, q5, '0' );
        q6q5.rotateTargetCP( -150 );
        q6q5.moveCPsTo( 328, 281, 349, 281, 306, 281 );
        dfa.addTransition( q6q5 );

        FATransition q6q6 = new FATransition( q6, q6, '1' );
        q6q6.rotateTargetCP( -185 );
        dfa.addTransition( q6q6 );

        FATransition q7q6 = new FATransition( q7, q6, '0' );
        dfa.addTransition( q7q6 );

        FATransition q7q3 = new FATransition( q7, q3, '1' );
        q7q3.moveLabelTo( 534, 175 );
        dfa.addTransition( q7q3 );


        return dfa;
        
    }
    
    public static FA createDFAForMinimizationTest03() {
        
        /*************************
                    0    1
             ->A    B    E
               B    C    F
              *C    D    H
               D    E    H
               E    F    I
              *F    G    B
               G    H    B
               H    I    C
              *I    A    E
        *************************/
        
        /*FA dfa = new FA();
        int currentState = 0;
        
        FAState a = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), true, false );
        FAState b = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        FAState c = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, true );
        FAState d = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        FAState e = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        FAState f = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, true );
        FAState g = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        FAState h = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        FAState i = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, true );
        
        dfa.addState( a );
        dfa.addState( b );
        dfa.addState( c );
        dfa.addState( d );
        dfa.addState( e );
        dfa.addState( f );
        dfa.addState( g );
        dfa.addState( h );
        dfa.addState( i );
        
        dfa.addTransition( new FATransition( a, b, '0' ) );
        dfa.addTransition( new FATransition( a, e, '1' ) );
        dfa.addTransition( new FATransition( b, c, '0' ) );
        dfa.addTransition( new FATransition( b, f, '1' ) );
        dfa.addTransition( new FATransition( c, d, '0' ) );
        dfa.addTransition( new FATransition( c, h, '1' ) );
        dfa.addTransition( new FATransition( d, e, '0' ) );
        dfa.addTransition( new FATransition( d, h, '1' ) );
        dfa.addTransition( new FATransition( e, f, '0' ) );
        dfa.addTransition( new FATransition( e, i, '1' ) );
        dfa.addTransition( new FATransition( f, g, '0' ) );
        dfa.addTransition( new FATransition( f, b, '1' ) );
        dfa.addTransition( new FATransition( g, h, '0' ) );
        dfa.addTransition( new FATransition( g, b, '1' ) );
        dfa.addTransition( new FATransition( h, i, '0' ) );
        dfa.addTransition( new FATransition( h, c, '1' ) );
        dfa.addTransition( new FATransition( i, a, '0' ) );
        dfa.addTransition( new FATransition( i, e, '1' ) );
        
        FACommon.arrangeRectangularly( dfa, 100, 100, 4, 150 );
        
        return dfa;*/

        FA dfa = new FA();

        // states
        FAState q0 = new FAState( "q0", "A", true, false );
        q0.setX1Y1( 101, 100 );
        dfa.addState( q0 );

        FAState q1 = new FAState( "q1", "B", false, false );
        q1.setX1Y1( 251, 100 );
        dfa.addState( q1 );

        FAState q2 = new FAState( "q2", "C", false, true );
        q2.setX1Y1( 401, 100 );
        dfa.addState( q2 );

        FAState q3 = new FAState( "q3", "D", false, false );
        q3.setX1Y1( 551, 100 );
        dfa.addState( q3 );

        FAState q4 = new FAState( "q4", "E", false, false );
        q4.setX1Y1( 101, 250 );
        dfa.addState( q4 );

        FAState q5 = new FAState( "q5", "F", false, true );
        q5.setX1Y1( 251, 250 );
        dfa.addState( q5 );

        FAState q6 = new FAState( "q6", "G", false, false );
        q6.setX1Y1( 401, 250 );
        dfa.addState( q6 );

        FAState q7 = new FAState( "q7", "H", false, false );
        q7.setX1Y1( 551, 250 );
        dfa.addState( q7 );

        FAState q8 = new FAState( "q8", "I", false, true );
        q8.setX1Y1( 101, 400 );
        dfa.addState( q8 );

        // transitions
        FATransition q0q1 = new FATransition( q0, q1, '0' );
        dfa.addTransition( q0q1 );

        FATransition q0q4 = new FATransition( q0, q4, '1' );
        q0q4.moveLabelTo( 63, 179 );
        q0q4.rotateTargetCP( 65 );
        q0q4.moveCPsTo( 70, 169, 70, 148, 70, 191 );
        dfa.addTransition( q0q4 );

        FATransition q1q2 = new FATransition( q1, q2, '0' );
        dfa.addTransition( q1q2 );

        FATransition q1q5 = new FATransition( q1, q5, '1' );
        q1q5.moveLabelTo( 283, 171 );
        q1q5.rotateTargetCP( 110 );
        q1q5.moveCPsTo( 269, 177, 269, 156, 269, 199 );
        dfa.addTransition( q1q5 );

        FATransition q2q3 = new FATransition( q2, q3, '0' );
        dfa.addTransition( q2q3 );

        FATransition q2q7 = new FATransition( q2, q7, '1' );
        q2q7.moveLabelTo( 488, 141 );
        q2q7.rotateTargetCP( 55 );
        q2q7.moveCPsTo( 495, 149, 473, 127, 518, 172 );
        dfa.addTransition( q2q7 );

        FATransition q3q4 = new FATransition( q3, q4, '0' );
        q3q4.moveLabelTo( 453, 344 );
        q3q4.rotateTargetCP( -135 );
        q3q4.moveCPsTo( 433, 402, 757, 370, 252, 457 );
        dfa.addTransition( q3q4 );

        FATransition q3q7 = new FATransition( q3, q7, '1' );
        q3q7.moveLabelTo( 536, 169 );
        dfa.addTransition( q3q7 );

        FATransition q4q5 = new FATransition( q4, q5, '0' );
        q4q5.rotateTargetCP( -20 );
        q4q5.moveCPsTo( 164, 276, 143, 276, 186, 276 );
        dfa.addTransition( q4q5 );

        FATransition q4q8 = new FATransition( q4, q8, '1' );
        q4q8.moveLabelTo( 71, 322 );
        q4q8.rotateTargetCP( 75 );
        q4q8.moveCPsTo( 78, 319, 78, 298, 77, 344 );
        dfa.addTransition( q4q8 );

        FATransition q5q6 = new FATransition( q5, q6, '0' );
        dfa.addTransition( q5q6 );

        FATransition q5q1 = new FATransition( q5, q1, '1' );
        q5q1.moveLabelTo( 215, 173 );
        q5q1.rotateTargetCP( -65 );
        q5q1.moveCPsTo( 230, 180, 230, 201, 230, 158 );
        dfa.addTransition( q5q1 );

        FATransition q6q7 = new FATransition( q6, q7, '0' );
        dfa.addTransition( q6q7 );

        FATransition q6q1 = new FATransition( q6, q1, '1' );
        q6q1.moveLabelTo( 300, 131 );
        dfa.addTransition( q6q1 );

        FATransition q7q8 = new FATransition( q7, q8, '0' );
        dfa.addTransition( q7q8 );

        FATransition q7q2 = new FATransition( q7, q2, '1' );
        q7q2.moveLabelTo( 451, 167 );
        q7q2.rotateTargetCP( -120 );
        q7q2.moveCPsTo( 453, 196, 475, 218, 430, 173 );
        dfa.addTransition( q7q2 );

        FATransition q8q0 = new FATransition( q8, q0, '0' );
        q8q0.moveLabelTo( 28, 238 );
        q8q0.rotateTargetCP( -30 );
        q8q0.moveCPsTo( 23, 251, 23, 297, 23, 204 );
        dfa.addTransition( q8q0 );

        FATransition q8q4 = new FATransition( q8, q4, '1' );
        q8q4.moveLabelTo( 129, 323 );
        q8q4.rotateTargetCP( -110 );
        q8q4.moveCPsTo( 120, 325, 120, 346, 120, 303 );
        dfa.addTransition( q8q4 );

        return dfa;
        
    }
    
    public static FA createDFAForMinimizationTest04() {
        
        /*FA nfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        FAState q1 = new FAState( currentState++, false, false );
        FAState q2 = new FAState( currentState++, false, false );
        FAState q3 = new FAState( currentState++, false, true );
        
        nfa.addState( q0 );
        nfa.addState( q1 );
        nfa.addState( q2 );
        nfa.addState( q3 );
        
        FACommon.arrangeRectangularly( nfa, 100, 100, 4, 150 );
        
        nfa.addTransition( new FATransition( q0, q0, '0', '1' ) );
        nfa.addTransition( new FATransition( q0, q1, '0' ) );
        nfa.addTransition( new FATransition( q1, q2, '0', '1' ) );
        nfa.addTransition( new FATransition( q2, q2, '0', '1' ) );
        nfa.addTransition( new FATransition( q2, q3, '0' ) );
        nfa.addTransition( new FATransition( q3, q3, '0', '1' ) );
        nfa.addTransition( new FATransition( q3, q0, '0' )
                .bendByCenterCPY( 60 ).rotateTargetCP( 210 ) );
        
        return nfa;*/

        FA dfa = new FA();

        // states
        FAState q0 = new FAState( "q0", "A", true, false );
        q0.setX1Y1( 75, 150 );
        dfa.addState( q0 );

        FAState q1 = new FAState( "q1", "B", false, false );
        q1.setX1Y1( 175, 150 );
        dfa.addState( q1 );

        FAState q2 = new FAState( "q2", "C", false, false );
        q2.setX1Y1( 275, 50 );
        dfa.addState( q2 );

        FAState q3 = new FAState( "q3", "D", false, false );
        q3.setX1Y1( 275, 250 );
        dfa.addState( q3 );

        FAState q4 = new FAState( "q4", "E", false, true );
        q4.setX1Y1( 375, 150 );
        dfa.addState( q4 );

        FAState q5 = new FAState( "q5", "F", false, true );
        q5.setX1Y1( 525, 150 );
        dfa.addState( q5 );

        // transitions
        FATransition q0q1 = new FATransition( q0, q1, '0' );
        dfa.addTransition( q0q1 );

        FATransition q0q0 = new FATransition( q0, q0, '1' );
        dfa.addTransition( q0q0 );

        FATransition q1q2 = new FATransition( q1, q2, '0' );
        dfa.addTransition( q1q2 );

        FATransition q1q3 = new FATransition( q1, q3, '1' );
        dfa.addTransition( q1q3 );

        FATransition q2q4 = new FATransition( q2, q4, '0' );
        dfa.addTransition( q2q4 );

        FATransition q2q3 = new FATransition( q2, q3, '1' );
        q2q3.moveLabelTo( 262, 149 );
        dfa.addTransition( q2q3 );

        FATransition q3q4 = new FATransition( q3, q4, '0' );
        dfa.addTransition( q3q4 );

        FATransition q3q3 = new FATransition( q3, q3, '1' );
        q3q3.rotateTargetCP( -180 );
        dfa.addTransition( q3q3 );

        FATransition q4q4 = new FATransition( q4, q4, '0' );
        dfa.addTransition( q4q4 );

        FATransition q4q5 = new FATransition( q4, q5, '1' );
        q4q5.rotateTargetCP( 20 );
        q4q5.moveCPsTo( 451, 118, 430, 118, 473, 118 );
        dfa.addTransition( q4q5 );

        FATransition q5q4 = new FATransition( q5, q4, '0' );
        q5q4.rotateTargetCP( -150 );
        q5q4.moveCPsTo( 450, 184, 471, 184, 428, 184 );
        dfa.addTransition( q5q4 );

        FATransition q5q5 = new FATransition( q5, q5, '1' );
        dfa.addTransition( q5q5 );

        return dfa;
        
    }
    
    public static FA createDFAForMinimizationTest05() {
        
        /*FA enfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        FAState q1 = new FAState( currentState++, false, false );
        FAState q2 = new FAState( currentState++, false, false );
        FAState q3 = new FAState( currentState++, false, true );
        FAState q4 = new FAState( currentState++, false, false );
        FAState q5 = new FAState( currentState++, false, false );
        FAState q6 = new FAState( currentState++, false, true );
        
        enfa.addState( q0 );
        enfa.addState( q1 );
        enfa.addState( q2 );
        enfa.addState( q3 );
        enfa.addState( q4 );
        enfa.addState( q5 );
        enfa.addState( q6 );
        
        FACommon.arrangeRectangularly( enfa, 100, 100, 4, 150 );
        q0.move( 0, 75 );
        q4.move( 150, 0 );
        q5.move( 150, 0 );
        q6.move( 150, 0 );
        
        enfa.addTransition( new FATransition( q0, q1, CharacterConstants.EMPTY_STRING ) );
        enfa.addTransition( new FATransition( q0, q4, CharacterConstants.EMPTY_STRING ) );
        enfa.addTransition( new FATransition( q1, q2, CharacterConstants.EMPTY_STRING ) );
        enfa.addTransition( new FATransition( q2, q3, CharacterConstants.EMPTY_STRING ) );
        enfa.addTransition( new FATransition( q4, q5, CharacterConstants.EMPTY_STRING ) );
        enfa.addTransition( new FATransition( q5, q6, CharacterConstants.EMPTY_STRING ) );
        enfa.addTransition( new FATransition( q1, q1, 'a' ) );
        enfa.addTransition( new FATransition( q2, q2, 'b' ) );
        enfa.addTransition( new FATransition( q3, q3, 'a' ) );
        enfa.addTransition( new FATransition( q4, q4, 'b' ) );
        enfa.addTransition( new FATransition( q5, q5, 'a' ) );
        enfa.addTransition( new FATransition( q6, q6, 'b' ) );
        
        return enfa;*/
        
        FA dfa = new FA();

        // states
        FAState q0 = new FAState( "q0", "A", true, true );
        q0.setX1Y1( 100, 175 );
        dfa.addState( q0 );

        FAState q1 = new FAState( "q1", "B", false, true );
        q1.setX1Y1( 250, 100 );
        dfa.addState( q1 );

        FAState q2 = new FAState( "q2", "C", false, true );
        q2.setX1Y1( 250, 250 );
        dfa.addState( q2 );

        FAState q3 = new FAState( "q3", "D", false, true );
        q3.setX1Y1( 400, 100 );
        dfa.addState( q3 );

        FAState q4 = new FAState( "q4", "E", false, true );
        q4.setX1Y1( 400, 250 );
        dfa.addState( q4 );

        FAState q5 = new FAState( "q5", "F", false, true );
        q5.setX1Y1( 550, 100 );
        dfa.addState( q5 );

        FAState q6 = new FAState( "q6", "G", false, true );
        q6.setX1Y1( 550, 250 );
        dfa.addState( q6 );

        // transitions
        FATransition q0q1 = new FATransition( q0, q1, 'a' );
        dfa.addTransition( q0q1 );

        FATransition q0q2 = new FATransition( q0, q2, 'b' );
        dfa.addTransition( q0q2 );

        FATransition q1q1 = new FATransition( q1, q1, 'a' );
        dfa.addTransition( q1q1 );

        FATransition q1q3 = new FATransition( q1, q3, 'b' );
        dfa.addTransition( q1q3 );

        FATransition q2q4 = new FATransition( q2, q4, 'a' );
        dfa.addTransition( q2q4 );

        FATransition q2q2 = new FATransition( q2, q2, 'b' );
        dfa.addTransition( q2q2 );

        FATransition q3q5 = new FATransition( q3, q5, 'a' );
        dfa.addTransition( q3q5 );

        FATransition q3q3 = new FATransition( q3, q3, 'b' );
        dfa.addTransition( q3q3 );

        FATransition q4q4 = new FATransition( q4, q4, 'a' );
        dfa.addTransition( q4q4 );

        FATransition q4q6 = new FATransition( q4, q6, 'b' );
        dfa.addTransition( q4q6 );

        FATransition q5q5 = new FATransition( q5, q5, 'a' );
        dfa.addTransition( q5q5 );

        FATransition q6q6 = new FATransition( q6, q6, 'b' );
        dfa.addTransition( q6q6 );

        return dfa;
        
    }
    
    public static FA createDFAForMinimizationTest06() {
        
        FA dfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        FAState q1 = new FAState( currentState++, false, false );
        FAState q2 = new FAState( currentState++, false, true );
        FAState q3 = new FAState( currentState++, false, false );
        FAState q4 = new FAState( currentState++, false, true );
        
        dfa.addState( q0 );
        dfa.addState( q1 );
        dfa.addState( q2 );
        dfa.addState( q3 );
        dfa.addState( q4 );
        
        FAArrangement.arrangeRectangularly( dfa, 100, 100, 3, 150 );
        q0.move( 0, 75 );
        q3.move( 150, 0 );
        q4.move( 150, 0 );
        
        dfa.addTransition( new FATransition( q0, q1, 'a' ) );
        dfa.addTransition( new FATransition( q0, q3, 'b' ) );
        dfa.addTransition( new FATransition( q1, q1, 'd' ) );
        dfa.addTransition( new FATransition( q1, q2, 'e' ) );
        dfa.addTransition( new FATransition( q1, q3, 'c' )
                .bendByCenterCPX( 30 ).moveLabel( 15, 25 )
                .rotateTargetCP( 120 ) );
        dfa.addTransition( new FATransition( q3, q3, 'd' )
                .rotateTargetCP( 180 ) );
        dfa.addTransition( new FATransition( q3, q4, 'e' ) );
        dfa.addTransition( new FATransition( q3, q1, 'c' )
                .bendByCenterCPX( -30 ).moveLabelX( -15 )
                .rotateTargetCP( -60 ) );
        
        return dfa;
        
    }
    
    public static FA createDFAForMinimizationTest07() {
        
        FA dfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        FAState q1 = new FAState( currentState++, false, false );
        FAState q2 = new FAState( currentState++, false, true );
        FAState q3 = new FAState( currentState++, false, false );
        FAState q4 = new FAState( currentState++, false, true );
        
        dfa.addState( q0 );
        dfa.addState( q1 );
        dfa.addState( q2 );
        dfa.addState( q3 );
        dfa.addState( q4 );
        
        FAArrangement.arrangeRectangularly( dfa, 100, 100, 3, 150 );
        q0.move( 0, 75 );
        q3.move( 150, 0 );
        q4.move( 150, 0 );
        
        dfa.addTransition( new FATransition( q0, q1, 'a' ) );
        dfa.addTransition( new FATransition( q0, q3, 'b' ) );
        dfa.addTransition( new FATransition( q1, q1, 'c' ) );
        dfa.addTransition( new FATransition( q1, q2, 'b' ) );
        dfa.addTransition( new FATransition( q1, q4, 'a' ) );
        dfa.addTransition( new FATransition( q3, q3, 'c' ).rotateTargetCP( 180 ) );
        dfa.addTransition( new FATransition( q3, q4, 'b' ) );
        dfa.addTransition( new FATransition( q3, q2, 'a' ) );
        
        return dfa;
        
    }
    
    public static FA createDFAForMinimizationTest08() {
        
        FA dfa = new FA();
        int currentState = 0;
        
        FAState a = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), true, false );
        FAState b = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        FAState c = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        FAState d = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, false );
        FAState e = new FAState( currentState, 
                FACommon.newCustomLabel( currentState++ ), false, true );
        
        dfa.addState( a );
        dfa.addState( b );
        dfa.addState( c );
        dfa.addState( d );
        dfa.addState( e );
        
        FAArrangement.arrangeRectangularly( dfa, 100, 100, 3, 150 );
        a.move( 0, 75 );
        d.move( 150, 0 );
        e.move( 150, 0 );
        
        dfa.addTransition( new FATransition( a, b, '0' ) );
        dfa.addTransition( new FATransition( a, d, '1' ) );
        dfa.addTransition( new FATransition( b, b, '0' ) );
        dfa.addTransition( new FATransition( b, c, '1' ).bendByCenterCPY( -30 ).rotateTargetCP( 30 ) );
        dfa.addTransition( new FATransition( d, d, '1' ).rotateTargetCP( 180 ) );
        dfa.addTransition( new FATransition( d, b, '0' ).moveLabelX( -15 ) );
        dfa.addTransition( new FATransition( c, b, '0' ).bendByCenterCPY( 30 ).rotateTargetCP( 210 ) );
        dfa.addTransition( new FATransition( c, e, '1' ).moveLabelX( -15 ) );
        dfa.addTransition( new FATransition( e, b, '0' ) );
        dfa.addTransition( new FATransition( e, d, '1' ) );
        
        return dfa;
        
    }
    
    public static FA createDFAForMinimizationTest09() {
        
        FA dfa = new FA();

        // states
        FAState q0 = new FAState( "q0", "A", true, false );
        q0.setX1Y1( 100, 250 );
        dfa.addState( q0 );

        FAState q1 = new FAState( "q1", "B", false, false );
        q1.setX1Y1( 100, 100 );
        dfa.addState( q1 );

        FAState q2 = new FAState( "q2", "C", false, true );
        q2.setX1Y1( 250, 250 );
        dfa.addState( q2 );

        FAState q3 = new FAState( "q3", "D", false, true );
        q3.setX1Y1( 250, 100 );
        dfa.addState( q3 );

        FAState q4 = new FAState( "q4", "E", false, true );
        q4.setX1Y1( 400, 250 );
        dfa.addState( q4 );

        FAState q5 = new FAState( "q5", "F", false, false );
        q5.setX1Y1( 400, 100 );
        dfa.addState( q5 );

        // transitions
        FATransition q0q1 = new FATransition( q0, q1, '0' );
        q0q1.moveLabelTo( 63, 173 );
        q0q1.rotateTargetCP( -70 );
        q0q1.moveCPsTo( 73, 175, 73, 196, 73, 153 );
        dfa.addTransition( q0q1 );

        FATransition q0q2 = new FATransition( q0, q2, '1' );
        dfa.addTransition( q0q2 );

        FATransition q1q0 = new FATransition( q1, q0, '0' );
        q1q0.moveLabelTo( 136, 174 );
        q1q0.rotateTargetCP( 105 );
        q1q0.moveCPsTo( 124, 174, 124, 153, 124, 196 );
        dfa.addTransition( q1q0 );

        FATransition q1q3 = new FATransition( q1, q3, '1' );
        dfa.addTransition( q1q3 );

        FATransition q2q4 = new FATransition( q2, q4, '0' );
        dfa.addTransition( q2q4 );

        FATransition q2q5 = new FATransition( q2, q5, '1' );
        q2q5.moveLabelTo( 289, 194 );
        dfa.addTransition( q2q5 );

        FATransition q3q4 = new FATransition( q3, q4, '0' );
        q3q4.moveLabelTo( 288, 153 );
        dfa.addTransition( q3q4 );

        FATransition q3q5 = new FATransition( q3, q5, '1' );
        dfa.addTransition( q3q5 );

        FATransition q4q4 = new FATransition( q4, q4, '0' );
        q4q4.rotateTargetCP( -180 );
        dfa.addTransition( q4q4 );

        FATransition q4q5 = new FATransition( q4, q5, '1' );
        q4q5.moveLabelTo( 384, 175 );
        dfa.addTransition( q4q5 );

        FATransition q5q5 = new FATransition( q5, q5, '0', '1' );
        dfa.addTransition( q5q5 );

        return dfa;
        
    }
    
}
