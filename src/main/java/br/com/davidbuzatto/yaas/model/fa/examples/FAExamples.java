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
    
}
