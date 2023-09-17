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
 * A set of methods to construct example FAs for testing purposes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAExamples {
    
    public static final String REG_LANG_0_EVEN_1_ODD = 
            String.format( "L = { 0%c %c 1%c | i > 0 and even, and j > 0 and odd }", 
            CharacterConstants.SUPER_I, 
            CharacterConstants.OR, 
            CharacterConstants.SUPER_J );
    
    public static FA createDFASubstring01() {
        
        FA fa = new FA();
        int currentState = 0;
        
        FAState s0 = new FAState();
        s0.setX1( 100 );
        s0.setY1( 200 );
        s0.setInitial( true );
        s0.setLabel( "q" + currentState++ );
        
        FAState s1 = new FAState();
        s1.setX1( 250 );
        s1.setY1( 200 );
        s1.setLabel( "q" + currentState++ );
        
        FAState s2 = new FAState();
        s2.setX1( 400 );
        s2.setY1( 200 );
        s2.setAccepting( true );
        s2.setLabel( "q" + currentState++ );
        
        FATransition t1 = new FATransition( s0, s0, '1' );
        FATransition t2 = new FATransition( s0, s1, '0' );
        FATransition t3 = new FATransition( s1, s1, '0' );
        FATransition t4 = new FATransition( s1, s2, '1' );
        FATransition t5 = new FATransition( s2, s2, '0', '1' );
        
        fa.addState( s0 );
        fa.addState( s1 );
        fa.addState( s2 );
        fa.addTransition( t1 );
        fa.addTransition( t2 );
        fa.addTransition( t3 );
        fa.addTransition( t4 );
        fa.addTransition( t5 );
        
        return fa;
        
    }
    
    public static FA createDFAEndsWith00() {
        
        FA fa = new FA();
        int currentState = 0;
        
        FAState s0 = new FAState();
        s0.setX1( 100 );
        s0.setY1( 200 );
        s0.setInitial( true );
        s0.setLabel( "q" + currentState++ );
        
        FAState s1 = new FAState();
        s1.setX1( 250 );
        s1.setY1( 200 );
        s1.setLabel( "q" + currentState++ );
        
        FAState s2 = new FAState();
        s2.setX1( 400 );
        s2.setY1( 200 );
        s2.setAccepting( true );
        s2.setLabel( "q" + currentState++ );
        
        FATransition t1 = new FATransition( s0, s0, '1' );
        FATransition t2 = new FATransition( s0, s1, '0' );
        FATransition t3 = new FATransition( s1, s2, '0' );
        FATransition t4 = new FATransition( s1, s0, '1' );
        FATransition t5 = new FATransition( s2, s2, '0' );
        FATransition t6 = new FATransition( s2, s0, '1' );
        
        t2.bendY( -30, -30, -30, 30 );
        t2.moveLabelX( 12 );
        t4.bendY( 30, 30, 30, 210 );
        t4.moveLabelX( -12 );
        t6.bendY( 80, 80, 80, 210 );
        t6.moveLabelX( -12 );
        t6.rotateTargetCP( 230 );
        
        fa.addState( s0 );
        fa.addState( s1 );
        fa.addState( s2 );
        fa.addTransition( t1 );
        fa.addTransition( t2 );
        fa.addTransition( t3 );
        fa.addTransition( t4 );
        fa.addTransition( t5 );
        fa.addTransition( t6 );
        
        return fa;
        
    }
    
    public static FA createDFAEndsWith01() {
        
        FA fa = new FA();
        int currentState = 0;
        
        FAState s0 = new FAState();
        s0.setX1( 100 );
        s0.setY1( 200 );
        s0.setInitial( true );
        s0.setLabel( "q" + currentState++ );
        
        FAState s1 = new FAState();
        s1.setX1( 250 );
        s1.setY1( 200 );
        s1.setLabel( "q" + currentState++ );
        
        FAState s2 = new FAState();
        s2.setX1( 400 );
        s2.setY1( 200 );
        s2.setAccepting( true );
        s2.setLabel( "q" + currentState++ );
        
        FATransition t1 = new FATransition( s0, s0, '1' );
        FATransition t2 = new FATransition( s0, s1, '0' );
        FATransition t3 = new FATransition( s1, s1, '0' );
        FATransition t4 = new FATransition( s1, s2, '1' );
        t4.bendY( -30, -30, -30, 30 );
        t4.moveLabelX( 12 );
        FATransition t5 = new FATransition( s2, s1, '0' );
        t5.bendY( 30, 30, 30, 210 );
        t5.moveLabelX( -12 );
        FATransition t6 = new FATransition( s2, s0, '1' );
        t6.bendY( 80, 80, 80, 210 );
        t6.moveLabelX( -12 );
        
        fa.addState( s0 );
        fa.addState( s1 );
        fa.addState( s2 );
        fa.addTransition( t1 );
        fa.addTransition( t2 );
        fa.addTransition( t3 );
        fa.addTransition( t4 );
        fa.addTransition( t5 );
        fa.addTransition( t6 );
        
        return fa;
        
    }
    
    public static FA createDFAEndsWith10() {
        
        FA fa = new FA();
        int currentState = 0;
        
        FAState s0 = new FAState();
        s0.setX1( 100 );
        s0.setY1( 200 );
        s0.setInitial( true );
        s0.setLabel( "q" + currentState++ );
        
        FAState s1 = new FAState();
        s1.setX1( 250 );
        s1.setY1( 200 );
        s1.setLabel( "q" + currentState++ );
        
        FAState s2 = new FAState();
        s2.setX1( 400 );
        s2.setY1( 200 );
        s2.setAccepting( true );
        s2.setLabel( "q" + currentState++ );
        
        FATransition t1 = new FATransition( s0, s0, '0' );
        FATransition t2 = new FATransition( s0, s1, '1' );
        FATransition t3 = new FATransition( s1, s1, '1' );
        FATransition t4 = new FATransition( s1, s2, '0' );
        t4.bendY( -30, -30, -30, 30 );
        t4.moveLabelX( 12 );
        FATransition t5 = new FATransition( s2, s1, '1' );
        t5.bendY( 30, 30, 30, 210 );
        t5.moveLabelX( -12 );
        FATransition t6 = new FATransition( s2, s0, '0' );
        t6.bendY( 80, 80, 80, 210 );
        t6.moveLabelX( -12 );
        
        fa.addState( s0 );
        fa.addState( s1 );
        fa.addState( s2 );
        fa.addTransition( t1 );
        fa.addTransition( t2 );
        fa.addTransition( t3 );
        fa.addTransition( t4 );
        fa.addTransition( t5 );
        fa.addTransition( t6 );
        
        return fa;
        
    }
    
    public static FA createDFAEndsWith11() {
        
        FA fa = new FA();
        int currentState = 0;
        
        FAState s0 = new FAState();
        s0.setX1( 100 );
        s0.setY1( 200 );
        s0.setInitial( true );
        s0.setLabel( "q" + currentState++ );
        
        FAState s1 = new FAState();
        s1.setX1( 250 );
        s1.setY1( 200 );
        s1.setLabel( "q" + currentState++ );
        
        FAState s2 = new FAState();
        s2.setX1( 400 );
        s2.setY1( 200 );
        s2.setAccepting( true );
        s2.setLabel( "q" + currentState++ );
        
        FATransition t1 = new FATransition( s0, s0, '0' );
        FATransition t2 = new FATransition( s0, s1, '1' );
        FATransition t3 = new FATransition( s1, s2, '1' );
        FATransition t4 = new FATransition( s1, s0, '0' );
        FATransition t5 = new FATransition( s2, s2, '1' );
        FATransition t6 = new FATransition( s2, s0, '0' );
        
        t2.bendY( -30, -30, -30, 30 );
        t2.moveLabelX( 12 );
        t4.bendY( 30, 30, 30, 210 );
        t4.moveLabelX( -12 );
        t6.bendY( 80, 80, 80, 210 );
        t6.moveLabelX( -12 );
        t6.rotateTargetCP( 230 );
        
        fa.addState( s0 );
        fa.addState( s1 );
        fa.addState( s2 );
        fa.addTransition( t1 );
        fa.addTransition( t2 );
        fa.addTransition( t3 );
        fa.addTransition( t4 );
        fa.addTransition( t5 );
        fa.addTransition( t6 );
        
        return fa;
        
    }
    
    public static FA createDFA0Even1Odd() {
        
        FA fa = new FA();
        int currentState = 0;
        
        FAState s0 = new FAState();
        s0.setX1( 100 );
        s0.setY1( 200 );
        s0.setInitial( true );
        s0.setLabel( "q" + currentState++ );
        
        FAState s1 = new FAState();
        s1.setX1( 250 );
        s1.setY1( 100 );
        s1.setLabel( "q" + currentState++ );
        
        FAState s2 = new FAState();
        s2.setX1( 400 );
        s2.setY1( 100 );
        s2.setAccepting( true );
        s2.setLabel( "q" + currentState++ );
        
        FAState s3 = new FAState();
        s3.setX1( 250 );
        s3.setY1( 300 );
        s3.setAccepting( true );
        s3.setLabel( "q" + currentState++ );
        
        FAState s4 = new FAState();
        s4.setX1( 400 );
        s4.setY1( 300 );
        s4.setLabel( "q" + currentState++ );
        
        FATransition t1 = new FATransition( s0, s1, '0' );
        t1.bendByCenterCPY( -30 );
        t1.rotateTargetCP( 0 );
        FATransition t2 = new FATransition( s0, s3, '1' );
        t2.bendByCenterCPY( 30 );
        t2.rotateTargetCP( 0 );
        FATransition t3 = new FATransition( s1, s2, '0' );
        t3.bendByCenterCPY( -30 );
        t3.rotateTargetCP( 30 );
        t3.moveLabelX( 12 );
        FATransition t4 = new FATransition( s2, s1, '0' );
        t4.bendByCenterCPY( 30 );
        t4.rotateTargetCP( 210 );
        t4.moveLabelX( -12 );
        FATransition t5 = new FATransition( s3, s4, '1' );
        t5.bendByCenterCPY( -30 );
        t5.rotateTargetCP( 30 );
        t5.moveLabelX( 12 );
        FATransition t6 = new FATransition( s4, s3, '1' );
        t6.bendByCenterCPY( 30 );
        t6.rotateTargetCP( 210 );
        t6.moveLabelX( -12 );
        
        fa.addState( s0 );
        fa.addState( s1 );
        fa.addState( s2 );
        fa.addState( s3 );
        fa.addState( s4 );
        fa.addTransition( t1 );
        fa.addTransition( t2 );
        fa.addTransition( t3 );
        fa.addTransition( t4 );
        fa.addTransition( t5 );
        fa.addTransition( t6 );
        
        return fa;
        
    }
    
    public static FA createDFAForMinimization() {
        
        FA fa = new FA();
        int currentState = 0;
        
        FAState s0 = new FAState();
        s0.setX1( 100 );
        s0.setY1( 100 );
        s0.setInitial( true );
        s0.setCustomLabel( FAAlgorithms.newCustomLabel( currentState ) );
        s0.setLabel( "q" + currentState++ );
        
        FAState s1 = new FAState();
        s1.setX1( 250 );
        s1.setY1( 100 );
        s1.setCustomLabel( FAAlgorithms.newCustomLabel( currentState ) );
        s1.setLabel( "q" + currentState++ );
        
        FAState s2 = new FAState();
        s2.setX1( 400 );
        s2.setY1( 100 );
        s2.setAccepting( true );
        s2.setCustomLabel( FAAlgorithms.newCustomLabel( currentState ) );
        s2.setLabel( "q" + currentState++ );
        
        FAState s3 = new FAState();
        s3.setX1( 550 );
        s3.setY1( 100 );
        s3.setCustomLabel( FAAlgorithms.newCustomLabel( currentState ) );
        s3.setLabel( "q" + currentState++ );
        
        FAState s4 = new FAState();
        s4.setX1( 100 );
        s4.setY1( 250 );
        s4.setCustomLabel( FAAlgorithms.newCustomLabel( currentState ) );
        s4.setLabel( "q" + currentState++ );
        
        FAState s5 = new FAState();
        s5.setX1( 250 );
        s5.setY1( 250 );
        s5.setCustomLabel( FAAlgorithms.newCustomLabel( currentState ) );
        s5.setLabel( "q" + currentState++ );
        
        FAState s6 = new FAState();
        s6.setX1( 400 );
        s6.setY1( 250 );
        s6.setCustomLabel( FAAlgorithms.newCustomLabel( currentState ) );
        s6.setLabel( "q" + currentState++ );
        
        FAState s7 = new FAState();
        s7.setX1( 550 );
        s7.setY1( 250 );
        s7.setCustomLabel( FAAlgorithms.newCustomLabel( currentState ) );
        s7.setLabel( "q" + currentState++ );
        
        
        FATransition t1 = new FATransition( s0, s1, '0' );
        FATransition t2 = new FATransition( s0, s5, '1' );
        FATransition t3 = new FATransition( s1, s2, '1' );
        FATransition t4 = new FATransition( s1, s6, '0' );
        t4.moveLabelX( -40 );
        FATransition t5 = new FATransition( s2, s2, '1' );
        FATransition t6 = new FATransition( s2, s0, '0' );
        t6.bendByCenterCPY( -70 );
        t6.rotateTargetCP( 160 );
        FATransition t7 = new FATransition( s3, s2, '0' );
        FATransition t8 = new FATransition( s3, s6, '1' );
        t8.moveLabelX( 40 );
        FATransition t9 = new FATransition( s4, s5, '1' );
        FATransition t10 = new FATransition( s4, s7, '0' );
        t10.bendByCenterCPY( 120 );
        t10.rotateTargetCP( -45 );
        FATransition t11 = new FATransition( s5, s2, '0' );
        t11.moveLabel( -40, 35 );
        FATransition t12 = new FATransition( s5, s6, '1' );
        FATransition t13 = new FATransition( s6, s6, '0' );
        t13.rotateTargetCP( 180 );
        FATransition t14 = new FATransition( s6, s4, '1' );
        t14.bendByCenterCPY( 70 );
        t14.rotateTargetCP( -160 );
        FATransition t15 = new FATransition( s7, s2, '1' );
        t15.moveLabel( 40, 35 );
        FATransition t16 = new FATransition( s7, s6, '0' );
        
        fa.addState( s0 );
        fa.addState( s1 );
        fa.addState( s2 );
        fa.addState( s3 );
        fa.addState( s4 );
        fa.addState( s5 );
        fa.addState( s6 );
        fa.addState( s7 );
        
        fa.addTransition( t1 );
        fa.addTransition( t2 );
        fa.addTransition( t3 );
        fa.addTransition( t4 );
        fa.addTransition( t5 );
        fa.addTransition( t6 );
        fa.addTransition( t7 );
        fa.addTransition( t8 );
        fa.addTransition( t9 );
        fa.addTransition( t10 );
        fa.addTransition( t11 );
        fa.addTransition( t12 );
        fa.addTransition( t13 );
        fa.addTransition( t14 );
        fa.addTransition( t15 );
        fa.addTransition( t16 );
        
        //fa.addTransition( new FATransition( s5, s1, '2' ) );
        
        // transitivity
        /*FAState s8 = new FAState();
        s8.setX1( 100 );
        s8.setY1( 400 );
        s8.setCustomLabel( FAAlgorithms.newCustomLabel( currentState ) + "(E)" );
        s8.setLabel( "q" + currentState++ );
        
        FAState s9 = new FAState();
        s9.setX1( 250 );
        s9.setY1( 400 );
        s9.setCustomLabel( FAAlgorithms.newCustomLabel( currentState ) + "(B)" );
        s9.setLabel( "q" + currentState++ );
        
        FATransition tt1 = new FATransition( s6, s8, '1' );
        FATransition tt2 = new FATransition( s8, s5, '1' );
        FATransition tt3 = new FATransition( s8, s7, '0' );
        
        FATransition tt4 = new FATransition( s0, s9, '0' );
        FATransition tt5 = new FATransition( s9, s6, '0' );
        FATransition tt6 = new FATransition( s9, s2, '1' );
        
        fa.addState( s8 );
        fa.addState( s9 );
        
        fa.addTransition( tt1 );
        fa.addTransition( tt2 );
        fa.addTransition( tt3 );
        fa.addTransition( tt4 );
        fa.addTransition( tt5 );
        fa.addTransition( tt6 );*/
        
        return fa;
        
    }
    
    public static FA createNFAEndsWith00() {
        
        FA fa = new FA();
        int currentState = 0;
        
        FAState s0 = new FAState();
        s0.setX1( 100 );
        s0.setY1( 200 );
        s0.setInitial( true );
        s0.setLabel( "q" + currentState++ );
        
        FAState s1 = new FAState();
        s1.setX1( 250 );
        s1.setY1( 200 );
        s1.setLabel( "q" + currentState++ );
        
        FAState s2 = new FAState();
        s2.setX1( 400 );
        s2.setY1( 200 );
        s2.setAccepting( true );
        s2.setLabel( "q" + currentState++ );
        
        FATransition t1 = new FATransition( s0, s0, '0', '1' );
        FATransition t2 = new FATransition( s0, s1, '0' );
        FATransition t3 = new FATransition( s1, s2, '0' );
        
        fa.addState( s0 );
        fa.addState( s1 );
        fa.addState( s2 );
        fa.addTransition( t1 );
        fa.addTransition( t2 );
        fa.addTransition( t3 );
        
        return fa;
        
    }
    
    public static FA createNFAEndsWith01() {
        
        FA fa = new FA();
        int currentState = 0;
        
        FAState s0 = new FAState();
        s0.setX1( 100 );
        s0.setY1( 200 );
        s0.setInitial( true );
        s0.setLabel( "q" + currentState++ );
        
        FAState s1 = new FAState();
        s1.setX1( 250 );
        s1.setY1( 200 );
        s1.setLabel( "q" + currentState++ );
        
        FAState s2 = new FAState();
        s2.setX1( 400 );
        s2.setY1( 200 );
        s2.setAccepting( true );
        s2.setLabel( "q" + currentState++ );
        
        FATransition t1 = new FATransition( s0, s0, '0', '1' );
        FATransition t2 = new FATransition( s0, s1, '0' );
        FATransition t3 = new FATransition( s1, s2, '1' );
        
        fa.addState( s0 );
        fa.addState( s1 );
        fa.addState( s2 );
        fa.addTransition( t1 );
        fa.addTransition( t2 );
        fa.addTransition( t3 );
        
        return fa;
        
    }
    
    public static FA createNFAEndsWith10() {
        
        FA fa = new FA();
        int currentState = 0;
        
        FAState s0 = new FAState();
        s0.setX1( 100 );
        s0.setY1( 200 );
        s0.setInitial( true );
        s0.setLabel( "q" + currentState++ );
        
        FAState s1 = new FAState();
        s1.setX1( 250 );
        s1.setY1( 200 );
        s1.setLabel( "q" + currentState++ );
        
        FAState s2 = new FAState();
        s2.setX1( 400 );
        s2.setY1( 200 );
        s2.setAccepting( true );
        s2.setLabel( "q" + currentState++ );
        
        FATransition t1 = new FATransition( s0, s0, '0', '1' );
        FATransition t2 = new FATransition( s0, s1, '1' );
        FATransition t3 = new FATransition( s1, s2, '0' );
        
        fa.addState( s0 );
        fa.addState( s1 );
        fa.addState( s2 );
        fa.addTransition( t1 );
        fa.addTransition( t2 );
        fa.addTransition( t3 );
        
        return fa;
        
    }
    
    public static FA createNFAEndsWith11() {
        
        FA fa = new FA();
        int currentState = 0;
        
        FAState s0 = new FAState();
        s0.setX1( 100 );
        s0.setY1( 200 );
        s0.setInitial( true );
        s0.setLabel( "q" + currentState++ );
        
        FAState s1 = new FAState();
        s1.setX1( 250 );
        s1.setY1( 200 );
        s1.setLabel( "q" + currentState++ );
        
        FAState s2 = new FAState();
        s2.setX1( 400 );
        s2.setY1( 200 );
        s2.setAccepting( true );
        s2.setLabel( "q" + currentState++ );
        
        FATransition t1 = new FATransition( s0, s0, '0', '1' );
        FATransition t2 = new FATransition( s0, s1, '1' );
        FATransition t3 = new FATransition( s1, s2, '1' );
        
        fa.addState( s0 );
        fa.addState( s1 );
        fa.addState( s2 );
        fa.addTransition( t1 );
        fa.addTransition( t2 );
        fa.addTransition( t3 );
        
        return fa;
        
    }
    
    public static FA createENFADecimalNumber() {
        
        FA fa = new FA();
        int currentState = 0;
        
        FAState s0 = new FAState();
        s0.setX1( 75 );
        s0.setY1( 150 );
        s0.setInitial( true );
        s0.setLabel( "q" + currentState++ );
        
        /*FAState s1 = new FAState();
        s1.setX1( 180 );
        s1.setY1( 150 );
        s1.setLabel( "q" + currentState++ );
        
        FAState s2 = new FAState();
        s2.setX1( 280 );
        s2.setY1( 150 );
        s2.setLabel( "q" + currentState++ );
        
        FAState s3 = new FAState();
        s3.setX1( 380 );
        s3.setY1( 150 );
        s3.setLabel( "q" + currentState++ );
        
        FAState s4 = new FAState();
        s4.setX1( 280 );
        s4.setY1( 250 );
        s4.setLabel( "q" + currentState++ );
        
        FAState s5 = new FAState();
        s5.setX1( 480 );
        s5.setY1( 150 );
        s5.setAccepting( true );
        s5.setLabel( "q" + currentState++ );*/
        
        FAState s1 = new FAState();
        s1.setX1( 250 );
        s1.setY1( 150 );
        s1.setLabel( "q" + currentState++ );
        
        FAState s2 = new FAState();
        s2.setX1( 350 );
        s2.setY1( 150 );
        s2.setLabel( "q" + currentState++ );
        
        FAState s3 = new FAState();
        s3.setX1( 575 );
        s3.setY1( 150 );
        s3.setLabel( "q" + currentState++ );
        
        FAState s4 = new FAState();
        s4.setX1( 350 );
        s4.setY1( 300 );
        s4.setLabel( "q" + currentState++ );
        
        FAState s5 = new FAState();
        s5.setX1( 700 );
        s5.setY1( 150 );
        s5.setAccepting( true );
        s5.setLabel( "q" + currentState++ );
        
        char[] n = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        FATransition t1 = new FATransition( s0, s1, '+', '-', CharacterConstants.EMPTY_STRING );
        FATransition t2 = new FATransition( s1, s1, n );
        FATransition t3 = new FATransition( s1, s2, '.' );
        FATransition t4 = new FATransition( s1, s4, n );
        t4.moveLabel( -70, 25 );
        FATransition t5 = new FATransition( s2, s3, n );
        FATransition t6 = new FATransition( s3, s3, n );
        FATransition t7 = new FATransition( s3, s5, CharacterConstants.EMPTY_STRING );
        FATransition t8 = new FATransition( s4, s3, '.' );
        
        fa.addState( s0 );
        fa.addState( s1 );
        fa.addState( s2 );
        fa.addState( s3 );
        fa.addState( s4 );
        fa.addState( s5 );
        fa.addTransition( t1 );
        fa.addTransition( t2 );
        fa.addTransition( t3 );
        fa.addTransition( t4 );
        fa.addTransition( t5 );
        fa.addTransition( t6 );
        fa.addTransition( t7 );
        fa.addTransition( t8 );
        
        return fa;
        
    }
    
}
