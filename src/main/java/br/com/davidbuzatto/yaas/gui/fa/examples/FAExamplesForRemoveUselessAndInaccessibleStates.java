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
package br.com.davidbuzatto.yaas.gui.fa.examples;

import br.com.davidbuzatto.yaas.gui.fa.FA;
import br.com.davidbuzatto.yaas.gui.fa.FAState;
import br.com.davidbuzatto.yaas.gui.fa.FATransition;

/**
 * A set of methods to construct example DFAs for testing purposes of 
 * the minimization algorithm.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAExamplesForRemoveUselessAndInaccessibleStates {
    
    public static FA createNFA() {

        FA nfa = new FA();

        // states
        FAState q0 = new FAState( 0, null, true, false );
        q0.setX1Y1( 50, 125 );
        nfa.addState( q0 );

        FAState q1 = new FAState( 1, null, false, true );
        q1.setX1Y1( 350, 125 );
        nfa.addState( q1 );

        FAState q2 = new FAState( 2, null, false, false );
        q2.setX1Y1( 150, 75 );
        nfa.addState( q2 );

        FAState q3 = new FAState( 3, null, false, false );
        q3.setX1Y1( 250, 75 );
        nfa.addState( q3 );

        FAState q4 = new FAState( 4, null, false, false );
        q4.setX1Y1( 150, 175 );
        nfa.addState( q4 );

        FAState q5 = new FAState( 5, null, false, false );
        q5.setX1Y1( 250, 175 );
        nfa.addState( q5 );

        FAState q6 = new FAState( 6, null, false, false );
        q6.setX1Y1( 150, 300 );
        nfa.addState( q6 );

        FAState q7 = new FAState( 7, null, false, false );
        q7.setX1Y1( 250, 300 );
        nfa.addState( q7 );

        FAState q8 = new FAState( 8, null, false, false );
        q8.setX1Y1( 350, 300 );
        nfa.addState( q8 );

        FAState q9 = new FAState( 9, null, false, false );
        q9.setX1Y1( 450, 300 );
        nfa.addState( q9 );

        FAState q10 = new FAState( 10, null, false, false );
        q10.setX1Y1( 550, 300 );
        nfa.addState( q10 );

        FAState q11 = new FAState( 11, null, false, false );
        q11.setX1Y1( 675, 300 );
        nfa.addState( q11 );

        FAState q12 = new FAState( 12, null, false, false );
        q12.setX1Y1( 800, 300 );
        nfa.addState( q12 );

        FAState q13 = new FAState( 13, null, false, false );
        q13.setX1Y1( 450, 50 );
        nfa.addState( q13 );

        FAState q14 = new FAState( 14, null, false, false );
        q14.setX1Y1( 550, 50 );
        nfa.addState( q14 );

        FAState q15 = new FAState( 15, null, false, false );
        q15.setX1Y1( 475, 125 );
        nfa.addState( q15 );

        FAState q16 = new FAState( 16, null, false, false );
        q16.setX1Y1( 575, 125 );
        nfa.addState( q16 );

        FAState q17 = new FAState( 17, null, false, false );
        q17.setX1Y1( 675, 125 );
        nfa.addState( q17 );

        // transitions
        FATransition q0q2 = new FATransition( q0, q2, 'a' );
        nfa.addTransition( q0q2 );

        FATransition q0q4 = new FATransition( q0, q4, 'a' );
        nfa.addTransition( q0q4 );

        FATransition q2q3 = new FATransition( q2, q3, 'a' );
        nfa.addTransition( q2q3 );

        FATransition q4q5 = new FATransition( q4, q5, 'a' );
        nfa.addTransition( q4q5 );

        FATransition q5q1 = new FATransition( q5, q1, 'a' );
        nfa.addTransition( q5q1 );

        FATransition q3q1 = new FATransition( q3, q1, 'a' );
        nfa.addTransition( q3q1 );

        FATransition q6q4 = new FATransition( q6, q4, 'a' );
        nfa.addTransition( q6q4 );

        FATransition q7q5 = new FATransition( q7, q5, 'a' );
        nfa.addTransition( q7q5 );

        FATransition q7q7 = new FATransition( q7, q7, 'a' );
        q7q7.rotateTargetCP( -140 );
        nfa.addTransition( q7q7 );

        FATransition q8q5 = new FATransition( q8, q5, 'a' );
        nfa.addTransition( q8q5 );

        FATransition q9q8 = new FATransition( q9, q8, 'a' );
        nfa.addTransition( q9q8 );

        FATransition q9q9 = new FATransition( q9, q9, 'a' );
        nfa.addTransition( q9q9 );

        FATransition q10q11 = new FATransition( q10, q11, 'a' );
        nfa.addTransition( q10q11 );

        FATransition q11q10 = new FATransition( q11, q10, 'a' );
        nfa.addTransition( q11q10 );

        FATransition q12q11 = new FATransition( q12, q11, 'a' );
        nfa.addTransition( q12q11 );

        FATransition q11q12 = new FATransition( q11, q12, 'a' );
        nfa.addTransition( q11q12 );

        FATransition q12q12 = new FATransition( q12, q12, 'a' );
        nfa.addTransition( q12q12 );

        FATransition q10q1 = new FATransition( q10, q1, 'a' );
        nfa.addTransition( q10q1 );

        FATransition q1q15 = new FATransition( q1, q15, 'a' );
        nfa.addTransition( q1q15 );

        FATransition q15q16 = new FATransition( q15, q16, 'a' );
        nfa.addTransition( q15q16 );

        FATransition q16q15 = new FATransition( q16, q15, 'a' );
        nfa.addTransition( q16q15 );

        FATransition q16q17 = new FATransition( q16, q17, 'a' );
        nfa.addTransition( q16q17 );

        FATransition q17q16 = new FATransition( q17, q16, 'a' );
        nfa.addTransition( q17q16 );

        FATransition q17q17 = new FATransition( q17, q17, 'a' );
        q17q17.rotateTargetCP( 55 );
        nfa.addTransition( q17q17 );

        FATransition q3q13 = new FATransition( q3, q13, 'a' );
        nfa.addTransition( q3q13 );

        FATransition q13q14 = new FATransition( q13, q14, 'a' );
        nfa.addTransition( q13q14 );

        FATransition q14q13 = new FATransition( q14, q13, 'a' );
        nfa.addTransition( q14q13 );

        FATransition q14q14 = new FATransition( q14, q14, 'a' );
        q14q14.rotateTargetCP( 80 );
        nfa.addTransition( q14q14 );

        return nfa;

    }
    
}
