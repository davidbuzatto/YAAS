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
package br.com.davidbuzatto.yaas.gui.fa;

import br.com.davidbuzatto.yaas.util.CharacterConstants;

/**
 * A set of methods to construct example DFAs for testing purposes of 
 * the minimization algorithm.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAExamplesForDFAMinimizationTest {
    
    public static FA createDFAForMinimizationTest01() {
        
        FA dfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), true, false );
        q0.setX1( 100 );
        q0.setY1( 100 );
        
        FAState q1 = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        q1.setX1( 250 );
        q1.setY1( 100 );
        
        FAState q2 = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, true );
        q2.setX1( 400 );
        q2.setY1( 100 );
        
        FAState q3 = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        q3.setX1( 550 );
        q3.setY1( 100 );
        
        FAState q4 = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        q4.setX1( 100 );
        q4.setY1( 250 );
        
        FAState q5 = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        q5.setX1( 250 );
        q5.setY1( 250 );
        
        FAState q6 = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        q6.setX1( 400 );
        q6.setY1( 250 );
        
        FAState q7 = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
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
                FAAlgorithms.newCustomLabel( currentState++ )+ "(E)",
                false, false );
        q8.setX1( 100 );
        q8.setY1( 400 );
        
        FAState q9 = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ) + "(B)",
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
        
        FA dfa = new FA();
        int currentState = 0;
        
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
        
        FAState a = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), true, false );
        FAState b = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        FAState c = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        FAState d = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, true );
        FAState e = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        FAState f = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        FAState g = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        FAState h = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        
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
        
        FAAlgorithms.arrangeFARectangularly( dfa, 100, 100, 4, 150 );
        
        return dfa;
        
    }
    
    public static FA createDFAForMinimizationTest03() {
        
        FA dfa = new FA();
        int currentState = 0;
        
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
        
        FAState a = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), true, false );
        FAState b = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        FAState c = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, true );
        FAState d = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        FAState e = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        FAState f = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, true );
        FAState g = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        FAState h = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, false );
        FAState i = new FAState( currentState, 
                FAAlgorithms.newCustomLabel( currentState++ ), false, true );
        
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
        
        FAAlgorithms.arrangeFARectangularly( dfa, 100, 100, 4, 150 );
        
        return dfa;
        
    }
    
    public static FA createDFAForMinimizationTest04() {
        
        FA dfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        FAState q1 = new FAState( currentState++, false, false );
        FAState q2 = new FAState( currentState++, false, false );
        FAState q3 = new FAState( currentState++, false, true );
        
        dfa.addState( q0 );
        dfa.addState( q1 );
        dfa.addState( q2 );
        dfa.addState( q3 );
        
        FAAlgorithms.arrangeFARectangularly( dfa, 100, 100, 4, 150 );
        
        dfa.addTransition( new FATransition( q0, q0, '0', '1' ) );
        dfa.addTransition( new FATransition( q0, q1, '0' ) );
        dfa.addTransition( new FATransition( q1, q2, '0', '1' ) );
        dfa.addTransition( new FATransition( q2, q2, '0', '1' ) );
        dfa.addTransition( new FATransition( q2, q3, '0' ) );
        dfa.addTransition( new FATransition( q3, q3, '0', '1' ) );
        dfa.addTransition( new FATransition( q3, q0, '0' )
                .bendByCenterCPY( 60 ).rotateTargetCP( 210 ) );
        
        return dfa;
        
    }
    
    public static FA createDFAForMinimizationTest05() {
        
        FA dfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        FAState q1 = new FAState( currentState++, false, false );
        FAState q2 = new FAState( currentState++, false, false );
        FAState q3 = new FAState( currentState++, false, true );
        FAState q4 = new FAState( currentState++, false, false );
        FAState q5 = new FAState( currentState++, false, false );
        FAState q6 = new FAState( currentState++, false, true );
        
        dfa.addState( q0 );
        dfa.addState( q1 );
        dfa.addState( q2 );
        dfa.addState( q3 );
        dfa.addState( q4 );
        dfa.addState( q5 );
        dfa.addState( q6 );
        
        FAAlgorithms.arrangeFARectangularly( dfa, 100, 100, 4, 150 );
        q0.move( 0, 75 );
        q4.move( 150, 0 );
        q5.move( 150, 0 );
        q6.move( 150, 0 );
        
        dfa.addTransition( new FATransition( q0, q1, CharacterConstants.EMPTY_STRING ) );
        dfa.addTransition( new FATransition( q0, q4, CharacterConstants.EMPTY_STRING ) );
        dfa.addTransition( new FATransition( q1, q2, CharacterConstants.EMPTY_STRING ) );
        dfa.addTransition( new FATransition( q2, q3, CharacterConstants.EMPTY_STRING ) );
        dfa.addTransition( new FATransition( q4, q5, CharacterConstants.EMPTY_STRING ) );
        dfa.addTransition( new FATransition( q5, q6, CharacterConstants.EMPTY_STRING ) );
        dfa.addTransition( new FATransition( q1, q1, 'a' ) );
        dfa.addTransition( new FATransition( q2, q2, 'b' ) );
        dfa.addTransition( new FATransition( q3, q3, 'a' ) );
        dfa.addTransition( new FATransition( q4, q4, 'b' ) );
        dfa.addTransition( new FATransition( q5, q5, 'a' ) );
        dfa.addTransition( new FATransition( q6, q6, 'b' ) );
        
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
        
        FAAlgorithms.arrangeFARectangularly( dfa, 100, 100, 3, 150 );
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
        
        FAAlgorithms.arrangeFARectangularly( dfa, 100, 100, 3, 150 );
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
    
}
