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
package br.com.davidbuzatto.yaas.model.pda;

import br.com.davidbuzatto.yaas.gui.DrawPanel;
import br.com.davidbuzatto.yaas.util.CharacterConstants;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

/**
 * Temporary tests with PDAs.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDATest {
    
    public static void main( String[] args ) {
        
        PDA pda = new PDA();
        
        PDAState q0 = new PDAState( 0, true, false );
        q0.setX1Y1( 150, 300 );
        
        PDAState q1 = new PDAState( 1, false, false );
        q1.setX1Y1( 300, 300 );
        
        PDAState q2 = new PDAState( 1, false, true );
        q2.setX1Y1( 450, 300 );
        
        pda.addState( q0 );
        pda.addState( q1 );
        pda.addState( q2 );
        
        pda.addTransition( new PDATransition( q0, q0, new PDAOperation( '0', '$', PDAOperationType.PUSH, '0' ) ) );
        pda.addTransition( new PDATransition( q0, q0, new PDAOperation( '0', '0', PDAOperationType.PUSH, '0' ) ) );
        pda.addTransition( new PDATransition( q0, q0, new PDAOperation( '0', '1', PDAOperationType.PUSH, '0' ) ) );
        pda.addTransition( new PDATransition( q0, q0, new PDAOperation( '1', '$', PDAOperationType.PUSH, '1' ) ) );
        pda.addTransition( new PDATransition( q0, q0, new PDAOperation( '1', '0', PDAOperationType.PUSH, '1' ) ) );
        pda.addTransition( new PDATransition( q0, q0, new PDAOperation( '1', '1', PDAOperationType.PUSH, '1' ) ) );
        
        // tests
        pda.addTransition( new PDATransition( q0, q0, new PDAOperation( '1', '1', PDAOperationType.PUSH, '1', '2', '3' ) ) );
        pda.addTransition( new PDATransition( q0, q0, new PDAOperation( '1', '1', PDAOperationType.REPLACE, '2', '3' ) ) );
        
        pda.addTransition( new PDATransition( q0, q1, PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, '$' ) ) );
        pda.addTransition( new PDATransition( q0, q1, PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, '0' ) ) );
        pda.addTransition( new PDATransition( q0, q1, PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, '1' ) ) );
        
        pda.addTransition( new PDATransition( q1, q1, PDAOperation.getPopOperation( '0', '0' ) ) );
        pda.addTransition( new PDATransition( q1, q1, PDAOperation.getPopOperation( '1', '1' ) ) );
        
        pda.addTransition( new PDATransition( q1, q2, PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, '$' ) ) );
        
        JFrame f = new JFrame( "test" );
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        DrawPanel drawPanel = new DrawPanel();
        drawPanel.setPda( pda );
        
        drawPanel.addMouseMotionListener( new MouseAdapter(){
            @Override
            public void mouseMoved( MouseEvent evt ) {
                
                pda.deselectAll();
                
                for ( PDAState s : pda.getStates() ) {
                    s.setMouseHover( false );
                }
                for ( PDATransition t : pda.getTransitions() ) {
                    t.setMouseHover( false );
                }
                
                PDAState s = pda.getStateAt( evt.getX(), evt.getY( ) );
                PDATransition t = pda.getTransitionAt( evt.getX(), evt.getY( ) );
                
                if ( s != null ) {
                    s.setMouseHover( true );
                }
                
                if ( t != null ) {
                    t.setMouseHover( true );
                }
                
                drawPanel.repaint();
                
            }
        });
        
        f.add( drawPanel, BorderLayout.CENTER );
        f.setSize( 800, 600 );
        f.setLocationRelativeTo( null );
        f.setVisible( true );
        
    }
    
}
