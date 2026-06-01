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
package br.com.davidbuzatto.yaas.model.fa.examples;

import br.com.davidbuzatto.yaas.model.fa.FA;
import br.com.davidbuzatto.yaas.model.fa.FAState;
import br.com.davidbuzatto.yaas.model.fa.FATransition;
import br.com.davidbuzatto.yaas.util.CharacterConstants;

/**
 * A set of methods to construct example FAs for testing purposes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAExamples {
    
    public static final String REGULAR_LANGUAGE_0_EVEN_1_ODD = 
            String.format( "L = { 0%c %c 1%c | i > 0 and even, and j > 0 and odd }", 
            CharacterConstants.SUPER_I, 
            CharacterConstants.OR, 
            CharacterConstants.SUPER_J );
    
    public static FA createDFASubstring01() {
        
        FA dfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        q0.setX1( 100 );
        q0.setY1( 200 );
        
        FAState q1 = new FAState( currentState++, false, false );
        q1.setX1( 250 );
        q1.setY1( 200 );
        
        FAState q2 = new FAState( currentState++, false, true );
        q2.setX1( 400 );
        q2.setY1( 200 );
        
        dfa.addState( q0 );
        dfa.addState( q1 );
        dfa.addState( q2 );
        
        dfa.addTransition( new FATransition( q0, q0, '1' ) );
        dfa.addTransition( new FATransition( q0, q1, '0' ) );
        dfa.addTransition( new FATransition( q1, q1, '0' ) );
        dfa.addTransition( new FATransition( q1, q2, '1' ) );
        dfa.addTransition( new FATransition( q2, q2, '0', '1' ) );
        
        return dfa;
        
    }
    
    public static FA createDFAEndsWith00() {
        
        FA dfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        q0.setX1( 100 );
        q0.setY1( 200 );
        
        FAState q1 = new FAState( currentState++, false, false );
        q1.setX1( 250 );
        q1.setY1( 200 );
        
        FAState q2 = new FAState( currentState++, false, true );
        q2.setX1( 400 );
        q2.setY1( 200 );
        
        dfa.addState( q0 );
        dfa.addState( q1 );
        dfa.addState( q2 );
        
        dfa.addTransition( new FATransition( q0, q0, '1' ) );
        dfa.addTransition( new FATransition( q0, q1, '0' )
                .bendY( -30, -30, -30, 30 ).moveLabelX( 12 ) );
        dfa.addTransition( new FATransition( q1, q2, '0' ) );
        dfa.addTransition( new FATransition( q1, q0, '1' )
                .bendY( 30, 30, 30, 210 ).moveLabelX( -12 ) );
        dfa.addTransition( new FATransition( q2, q2, '0' ) );
        dfa.addTransition( new FATransition( q2, q0, '1' )
                .bendY( 80, 80, 80, 210 ).moveLabelX( -12 )
                .rotateTargetCP( 230 ) );
        
        return dfa;
        
    }
    
    public static FA createDFAEndsWith01() {
        
        FA dfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        q0.setX1( 100 );
        q0.setY1( 200 );
        
        FAState q1 = new FAState( currentState++, false, false );
        q1.setX1( 250 );
        q1.setY1( 200 );
        
        FAState q2 = new FAState( currentState++, false, true );
        q2.setX1( 400 );
        q2.setY1( 200 );
        
        dfa.addState( q0 );
        dfa.addState( q1 );
        dfa.addState( q2 );
        
        dfa.addTransition( new FATransition( q0, q0, '1' ) );
        dfa.addTransition( new FATransition( q0, q1, '0' ) );
        dfa.addTransition( new FATransition( q1, q1, '0' ) );
        dfa.addTransition( new FATransition( q1, q2, '1' )
                .bendY( -30, -30, -30, 30 ).moveLabelX( 12 ) );
        dfa.addTransition( new FATransition( q2, q1, '0' )
                .bendY( 30, 30, 30, 210 ).moveLabelX( -12  ) );
        dfa.addTransition( new FATransition( q2, q0, '1' )
                .bendY( 80, 80, 80, 210 ).moveLabelX( -12 ) );
        
        return dfa;
        
    }
    
    public static FA createDFAEndsWith10() {
        
        FA dfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        q0.setX1( 100 );
        q0.setY1( 200 );
        
        FAState q1 = new FAState( currentState++, false, false );
        q1.setX1( 250 );
        q1.setY1( 200 );
        
        FAState q2 = new FAState( currentState++, false, true );
        q2.setX1( 400 );
        q2.setY1( 200 );
        
        dfa.addState( q0 );
        dfa.addState( q1 );
        dfa.addState( q2 );
        
        dfa.addTransition( new FATransition( q0, q0, '0' ) );
        dfa.addTransition( new FATransition( q0, q1, '1' ) );
        dfa.addTransition( new FATransition( q1, q1, '1' ) );
        dfa.addTransition( new FATransition( q1, q2, '0' )
                .bendY( -30, -30, -30, 30 ).moveLabelX( 12 ) );
        dfa.addTransition( new FATransition( q2, q1, '1' )
                .bendY( 30, 30, 30, 210 ).moveLabelX( -12 ) );
        dfa.addTransition( new FATransition( q2, q0, '0' )
                .bendY( 80, 80, 80, 210 ).moveLabelX( -12 ) );
        
        return dfa;
        
    }
    
    public static FA createDFAEndsWith11() {
        
        FA dfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        q0.setX1( 100 );
        q0.setY1( 200 );
        
        FAState q1 = new FAState( currentState++, false, false );
        q1.setX1( 250 );
        q1.setY1( 200 );
        
        FAState q2 = new FAState( currentState++, false, true );
        q2.setX1( 400 );
        q2.setY1( 200 );
        
        dfa.addState( q0 );
        dfa.addState( q1 );
        dfa.addState( q2 );
        
        dfa.addTransition( new FATransition( q0, q0, '0' ) );
        dfa.addTransition( new FATransition( q0, q1, '1' )
                .bendY( -30, -30, -30, 30 ).moveLabelX( 12 ) );
        dfa.addTransition( new FATransition( q1, q2, '1' ) );
        dfa.addTransition( new FATransition( q1, q0, '0' )
                .bendY( 30, 30, 30, 210 ).moveLabelX( -12 ) );
        dfa.addTransition( new FATransition( q2, q2, '1' ) );
        dfa.addTransition( new FATransition( q2, q0, '0' )
                .bendY( 80, 80, 80, 210 ).moveLabelX( -12 )
                .rotateTargetCP( 230 ) );
        
        return dfa;
        
    }
    
    public static FA createDFA0Even1Odd() {
        
        FA dfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        q0.setX1( 100 );
        q0.setY1( 200 );
        
        FAState q1 = new FAState( currentState++, false, false );
        q1.setX1( 250 );
        q1.setY1( 100 );
        
        FAState q2 = new FAState( currentState++, false, true );
        q2.setX1( 400 );
        q2.setY1( 100 );
        
        FAState q3 = new FAState( currentState++, false, true );
        q3.setX1( 250 );
        q3.setY1( 300 );
        
        FAState q4 = new FAState( currentState++, false, false );
        q4.setX1( 400 );
        q4.setY1( 300 );
        
        dfa.addState( q0 );
        dfa.addState( q1 );
        dfa.addState( q2 );
        dfa.addState( q3 );
        dfa.addState( q4 );
        
        dfa.addTransition( new FATransition( q0, q1, '0' )
                .bendByCenterCPY( -30 ).rotateTargetCP( 0 ) );
        dfa.addTransition( new FATransition( q0, q3, '1' )
                .bendByCenterCPY( 30 ).rotateTargetCP( 0 ) );
        dfa.addTransition( new FATransition( q1, q2, '0' )
                .bendByCenterCPY( -30 ).rotateTargetCP( 30 ).moveLabelX( 12 ) );
        dfa.addTransition( new FATransition( q2, q1, '0' )
                .bendByCenterCPY( 30 ).rotateTargetCP( 210 ).moveLabelX( -12 ) );
        dfa.addTransition( new FATransition( q3, q4, '1' )
                .bendByCenterCPY( -30 ).rotateTargetCP( 30 ).moveLabelX( 12 ) );
        dfa.addTransition( new FATransition( q4, q3, '1' )
                .bendByCenterCPY( 30 ).rotateTargetCP( 210 ).moveLabelX( -12 ) );
        
        return dfa;
        
    }
    
    public static FA createNFAEndsWith00() {
        
        FA nfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        q0.setX1( 100 );
        q0.setY1( 200 );
        
        FAState q1 = new FAState( currentState++, false, false );
        q1.setX1( 250 );
        q1.setY1( 200 );
        
        FAState q2 = new FAState( currentState++, false, true );
        q2.setX1( 400 );
        q2.setY1( 200 );
        
        nfa.addState( q0 );
        nfa.addState( q1 );
        nfa.addState( q2 );
        
        nfa.addTransition( new FATransition( q0, q0, '0', '1' ) );
        nfa.addTransition( new FATransition( q0, q1, '0' ) );
        nfa.addTransition( new FATransition( q1, q2, '0' ) );
        
        return nfa;
        
    }
    
    public static FA createNFAEndsWith01() {
        
        FA nfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        q0.setX1( 100 );
        q0.setY1( 200 );
        
        FAState q1 = new FAState( currentState++, false, false );
        q1.setX1( 250 );
        q1.setY1( 200 );
        
        FAState q2 = new FAState( currentState++, false, true );
        q2.setX1( 400 );
        q2.setY1( 200 );
        
        nfa.addState( q0 );
        nfa.addState( q1 );
        nfa.addState( q2 );
        
        nfa.addTransition( new FATransition( q0, q0, '0', '1' ) );
        nfa.addTransition( new FATransition( q0, q1, '0' ) );
        nfa.addTransition( new FATransition( q1, q2, '1' ) );
        
        return nfa;
        
    }
    
    public static FA createNFAEndsWith10() {
        
        FA nfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        q0.setX1( 100 );
        q0.setY1( 200 );
        
        FAState q1 = new FAState( currentState++, false, false );
        q1.setX1( 250 );
        q1.setY1( 200 );
        
        FAState q2 = new FAState( currentState++, false, true );
        q2.setX1( 400 );
        q2.setY1( 200 );
        
        nfa.addState( q0 );
        nfa.addState( q1 );
        nfa.addState( q2 );
        
        nfa.addTransition( new FATransition( q0, q0, '0', '1' ) );
        nfa.addTransition( new FATransition( q0, q1, '1' ) );
        nfa.addTransition( new FATransition( q1, q2, '0' ) );
        
        return nfa;
        
    }
    
    public static FA createNFAEndsWith11() {
        
        FA nfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        q0.setX1( 100 );
        q0.setY1( 200 );
        
        FAState q1 = new FAState( currentState++, false, false );
        q1.setX1( 250 );
        q1.setY1( 200 );
        
        FAState q2 = new FAState( currentState++, false, true );
        q2.setX1( 400 );
        q2.setY1( 200 );
        
        nfa.addState( q0 );
        nfa.addState( q1 );
        nfa.addState( q2 );
        
        nfa.addTransition( new FATransition( q0, q0, '0', '1' ) );
        nfa.addTransition( new FATransition( q0, q1, '1' ) );
        nfa.addTransition( new FATransition( q1, q2, '1' ) );
        
        return nfa;
        
    }
    
    public static FA createENFADecimalNumber() {
        
        FA enfa = new FA();
        int currentState = 0;
        
        FAState q0 = new FAState( currentState++, true, false );
        q0.setX1( 75 );
        q0.setY1( 150 );
        
        FAState q1 = new FAState( currentState++, false, false );
        q1.setX1( 250 );
        q1.setY1( 150 );
        
        FAState q2 = new FAState( currentState++, false, false );
        q2.setX1( 350 );
        q2.setY1( 150 );
        
        FAState q3 = new FAState( currentState++, false, false );
        q3.setX1( 575 );
        q3.setY1( 150 );
        
        FAState q4 = new FAState( currentState++, false, false );
        q4.setX1( 350 );
        q4.setY1( 300 );
        
        FAState q5 = new FAState( currentState++, false, true );
        q5.setX1( 700 );
        q5.setY1( 150 );
        
        char[] n = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        
        enfa.addState( q0 );
        enfa.addState( q1 );
        enfa.addState( q2 );
        enfa.addState( q3 );
        enfa.addState( q4 );
        enfa.addState( q5 );
        
        enfa.addTransition( new FATransition( q0, q1, '+', '-',
                CharacterConstants.EMPTY_STRING ) );
        enfa.addTransition( new FATransition( q1, q1, n ) );
        enfa.addTransition( new FATransition( q1, q2, '.' ) );
        enfa.addTransition( new FATransition( q1, q4, n ).moveLabel( -70, 25 ) );
        enfa.addTransition( new FATransition( q2, q3, n ) );
        enfa.addTransition( new FATransition( q3, q3, n ) );
        enfa.addTransition( new FATransition( q3, q5,
                CharacterConstants.EMPTY_STRING ) );
        enfa.addTransition( new FATransition( q4, q3, '.' ) );

        return enfa;

    }

    /**
     * Review scenario - bug 1 (FA.accepts simulation step).
     *
     * A partial DFA that accepts only the string "01". When a string is
     * rejected in the middle (e.g. "00"), the simulation must show a final
     * step with no active states, pointing the symbol that caused the
     * rejection.
     *
     * Suggested tests: "01" (accepted), "00", "0", "1" and "010" (rejected,
     * the last one rejecting on the third symbol).
     */
    public static FA createDFAForRejectionStep() {

        FA dfa = new FA();
        int currentState = 0;

        FAState q0 = new FAState( currentState++, true, false );
        q0.setX1( 100 );
        q0.setY1( 200 );

        FAState q1 = new FAState( currentState++, false, false );
        q1.setX1( 250 );
        q1.setY1( 200 );

        FAState q2 = new FAState( currentState++, false, true );
        q2.setX1( 400 );
        q2.setY1( 200 );

        dfa.addState( q0 );
        dfa.addState( q1 );
        dfa.addState( q2 );

        dfa.addTransition( new FATransition( q0, q1, '0' ) );
        dfa.addTransition( new FATransition( q1, q2, '1' ) );

        return dfa;

    }

    /**
     * Review scenario - bug 2 (DFAMinimize transitive merge).
     *
     * A DFA for the language of strings over {0,1} that contain the substring
     * "01". Each non-initial equivalence class is duplicated into two
     * reachable and equivalent states, so the minimization must combine them.
     *
     * The expected minimum DFA has 3 states:
     *     {S} (initial), {T0, T1} and {U0, U1} (final).
     *
     * This stresses the step that combines equivalent states into more than
     * one group (the transitive closure fix).
     *
     * Suggested tests: "01", "001", "1101" (accepted), "0", "1", "11", "000"
     * (rejected).
     */
    public static FA createDFAForMinimizationTwoGroups() {

        FA dfa = new FA();
        int currentState = 0;

        // S: no progress towards "01" yet (initial)
        FAState s = new FAState( currentState++, true, false );
        s.setX1( 100 );
        s.setY1( 225 );

        // T0 and T1: a 0 was read and a 1 would complete "01"
        FAState t0 = new FAState( currentState++, false, false );
        t0.setX1( 275 );
        t0.setY1( 125 );

        FAState t1 = new FAState( currentState++, false, false );
        t1.setX1( 275 );
        t1.setY1( 325 );

        // U0 and U1: "01" was already seen (accepting sink)
        FAState u0 = new FAState( currentState++, false, true );
        u0.setX1( 475 );
        u0.setY1( 125 );

        FAState u1 = new FAState( currentState++, false, true );
        u1.setX1( 475 );
        u1.setY1( 325 );

        dfa.addState( s );
        dfa.addState( t0 );
        dfa.addState( t1 );
        dfa.addState( u0 );
        dfa.addState( u1 );

        // S stays in its class on 1 and moves to the T class on 0
        dfa.addTransition( new FATransition( s, s, '1' ) );
        dfa.addTransition( new FATransition( s, t0, '0' ) );

        // T0 and T1 stay in the T class on 0 and move to the U class on 1
        dfa.addTransition( new FATransition( t0, t1, '0' ) );
        dfa.addTransition( new FATransition( t0, u0, '1' ) );
        dfa.addTransition( new FATransition( t1, t0, '0' )
                .bendByCenterCPY( 30 ) );
        dfa.addTransition( new FATransition( t1, u1, '1' ) );

        // U0 and U1 are equivalent accepting sinks
        dfa.addTransition( new FATransition( u0, u1, '0' ) );
        dfa.addTransition( new FATransition( u0, u0, '1' ) );
        dfa.addTransition( new FATransition( u1, u0, '0' )
                .bendByCenterCPY( 30 ) );
        dfa.addTransition( new FATransition( u1, u1, '1' ) );

        return dfa;

    }

    /**
     * Review scenario - bugs A and B (minimization of partial DFAs).
     *
     * A partial DFA (not every state defines every symbol) for the finite
     * language { "aab", "baa" }. The states q1 and q2 share the same local
     * alphabet ({a}) and the same one-symbol behaviour, but are distinguished
     * only by strings that continue with symbols not defined on them
     * ("ab" tells them apart). Before the fix the distinguishability test used
     * the local alphabet and wrongly merged q1 and q2, producing a minimized
     * DFA with the wrong language. After the fix they are kept separate.
     *
     * Suggested tests: "aab", "baa" (accepted), "aa", "ab", "ba", "" (rejected).
     */
    public static FA createDFAForMinimizationPartial() {

        FA dfa = new FA();
        int currentState = 0;

        FAState q0 = new FAState( currentState++, true, false );
        q0.setX1( 100 );
        q0.setY1( 225 );

        FAState q1 = new FAState( currentState++, false, false );
        q1.setX1( 250 );
        q1.setY1( 125 );

        FAState q2 = new FAState( currentState++, false, false );
        q2.setX1( 250 );
        q2.setY1( 325 );

        FAState q3 = new FAState( currentState++, false, false );
        q3.setX1( 400 );
        q3.setY1( 125 );

        FAState q4 = new FAState( currentState++, false, false );
        q4.setX1( 400 );
        q4.setY1( 325 );

        FAState qf = new FAState( currentState++, false, true );
        qf.setX1( 550 );
        qf.setY1( 225 );

        dfa.addState( q0 );
        dfa.addState( q1 );
        dfa.addState( q2 );
        dfa.addState( q3 );
        dfa.addState( q4 );
        dfa.addState( qf );

        dfa.addTransition( new FATransition( q0, q1, 'a' ) );
        dfa.addTransition( new FATransition( q0, q2, 'b' ) );
        dfa.addTransition( new FATransition( q1, q3, 'a' ) );
        dfa.addTransition( new FATransition( q2, q4, 'a' ) );
        dfa.addTransition( new FATransition( q3, qf, 'b' ) );
        dfa.addTransition( new FATransition( q4, qf, 'a' ) );

        return dfa;

    }

}
