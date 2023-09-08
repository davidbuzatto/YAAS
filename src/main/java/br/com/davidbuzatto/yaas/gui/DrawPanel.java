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
package br.com.davidbuzatto.yaas.gui;

import br.com.davidbuzatto.yaas.gui.model.Arrow;
import br.com.davidbuzatto.yaas.gui.model.State;
import br.com.davidbuzatto.yaas.gui.model.Transition;
import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Drawing panel for automaton construction and test.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DrawPanel extends JPanel {

    private List<State> states;
    private List<Transition> transitions;
    
    private boolean showGrid;
    private boolean transitionControlPointsVisible;
    
    private boolean drawingTempTransition;
    private Arrow tempTransitionArrow;
    private int tempTransitionX1;
    private int tempTransitionY1;
    private int tempTransitionX2;
    private int tempTransitionY2;
    
    public DrawPanel() {
        states = new ArrayList<>();
        transitions = new ArrayList<>();
        tempTransitionArrow = new Arrow();
        tempTransitionArrow.setStroke( Utils.TRANSITION_STROKE );
    }
    
    @Override
    protected void paintComponent( Graphics g ) {
        
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON );
        
        g2d.setColor( Color.WHITE );
        g2d.fillRect( 0, 0, getWidth(), getHeight() );
        
        if ( showGrid ) {
            g2d.setColor( Utils.GRID_COLOR );
            for ( int i = 0; i <= getHeight(); i += Utils.STATE_RADIUS ) {
                g2d.drawLine( 0, i, getWidth(), i );
            }
            for ( int i = 0; i <= getWidth(); i += Utils.STATE_RADIUS ) {
                g2d.drawLine( i, 0, i, getHeight() );
            }
        }
        
        for ( Transition t : transitions ) {
            t.draw( g2d );
        }
        
        for ( State s : states ) {
            s.draw( g2d );
        }
        
        if ( drawingTempTransition ) {
            
            Stroke s = g2d.getStroke();
            g2d.setStroke( Utils.TEMP_TRANSITION_STROKE );
            g2d.setColor( Utils.TEMP_TRANSITION_COLOR );
            
            g2d.drawLine( 
                    tempTransitionX1, tempTransitionY1, 
                    tempTransitionX2, tempTransitionY2 );
            
            tempTransitionArrow.setX1( tempTransitionX2 );
            tempTransitionArrow.setY1( tempTransitionY2 );
            tempTransitionArrow.setStrokeColor( Utils.TEMP_TRANSITION_COLOR );
            tempTransitionArrow.setAngle( Math.atan2( 
                    tempTransitionY2 - tempTransitionY1, 
                    tempTransitionX2 - tempTransitionX1 ) );
            tempTransitionArrow.draw( g2d );
            
        }
        
        g2d.dispose();
        
    }
    
    public State getStateAt( int x, int y ) {
        
        for ( State s : states ) {
            if ( s.intercepts( x, y ) ) {
                return s;
            }
        }
        
        return null;
        
    }
    
    public Transition getTransitionAt( int x, int y ) {
        
        for ( Transition t : transitions ) {
            if ( t.intercepts( x, y ) ) {
                return t;
            }
        }
        
        return null;
        
    }
    
    public void addState( State state ) {
        if ( state != null ) {
            states.add( state );
        }
    }
    
    public void addTransition( Transition transition ) {
        if ( transition != null ) {
            transition.setControlPointsVisible( transitionControlPointsVisible );
            transitions.add( transition );
        }
    }
    
    public void updateTransitions() {
        for ( Transition t : transitions ) {
            t.updateStartAndEndPoints();
        }
    }
    
    public void draggTransitions( MouseEvent e ) {
        for ( Transition t : transitions ) {
            t.mouseDragged( e );
        }
    }
    
    public void setTransitionsControlPointsVisible( boolean visible ) {
        transitionControlPointsVisible = visible;
        for ( Transition t : transitions ) {
            t.setControlPointsVisible( visible );
        }
    }
    
    public void mouseHoverStatesAndTransitions( int x, int y ) {
        for ( Transition t : transitions ) {
            t.mouseHover( x, y );
        }
        for ( State s : states ) {
            s.mouseHover( x, y );
        }
    }

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid( boolean showGrid ) {
        this.showGrid = showGrid;
    }

    public void setDrawingTempTransition( boolean drawingTempTransition ) {
        this.drawingTempTransition = drawingTempTransition;
    }

    public void setTempTransitionX1( int tempTransitionX1 ) {
        this.tempTransitionX1 = tempTransitionX1;
    }

    public void setTempTransitionY1( int tempTransitionY1 ) {
        this.tempTransitionY1 = tempTransitionY1;
    }

    public void setTempTransitionX2( int tempTransitionX2 ) {
        this.tempTransitionX2 = tempTransitionX2;
    }

    public void setTempTransitionY2( int tempTransitionY2 ) {
        this.tempTransitionY2 = tempTransitionY2;
    }
    
}
