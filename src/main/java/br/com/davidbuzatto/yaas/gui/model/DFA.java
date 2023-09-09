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
package br.com.davidbuzatto.yaas.gui.model;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Deterministic Finite Automata representation.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DFA extends AbstractForm {
    
    private List<State> states;
    private List<Transition> transitions;
    private State initialState;
    
    private boolean transitionControlPointsVisible;
    
    public DFA() {
        states = new ArrayList<>();
        transitions = new ArrayList<>();
    }
    
    public boolean accepts( String str ) {
        return false;
    }

    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        for ( Transition t : transitions ) {
            t.draw( g2d );
        }
        
        for ( State s : states ) {
            s.draw( g2d );
        }
        
        g2d.dispose();
        
    }

    @Override
    public boolean intercepts( int x, int y ) {
        return false;
    }
    
    public State getStateAt( int x, int y ) {
        
        for ( State s : states ) {
            if ( s.intercepts( x, y ) ) {
                s.setSelected( true );
                return s;
            }
        }
        
        return null;
        
    }
    
    public Transition getTransitionAt( int x, int y ) {
        
        for ( Transition t : transitions ) {
            if ( t.intercepts( x, y ) ) {
                t.setSelected( true );
                return t;
            }
        }
        
        return null;
        
    }
    
    public void deselectAll() {
        for ( Transition t : transitions ) {
            t.setSelected( false );
        }
        for ( State s : states ) {
            s.setSelected( false );
        }
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
    
}
