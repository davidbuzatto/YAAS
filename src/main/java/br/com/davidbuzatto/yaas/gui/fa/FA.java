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

import br.com.davidbuzatto.yaas.gui.model.AbstractGeometricForm;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Finite Automaton representation.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FA extends AbstractGeometricForm {
    
    private List<FAState> states;
    private List<FATransition> transitions;
    private FAState initialState;
    
    private boolean transitionControlPointsVisible;
    
    public FA() {
        states = new ArrayList<>();
        transitions = new ArrayList<>();
    }
    
    public boolean accepts( String str ) {
        return false;
    }

    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        for ( FATransition t : transitions ) {
            t.draw( g2d );
        }
        
        for ( FAState s : states ) {
            s.draw( g2d );
        }
        
        g2d.dispose();
        
    }

    @Override
    public boolean intercepts( int x, int y ) {
        return false;
    }
    
    public FAState getStateAt( int x, int y ) {
        
        for ( FAState s : states ) {
            if ( s.intercepts( x, y ) ) {
                s.setSelected( true );
                return s;
            }
        }
        
        return null;
        
    }
    
    public FATransition getTransitionAt( int x, int y ) {
        
        for ( FATransition t : transitions ) {
            if ( t.intercepts( x, y ) ) {
                t.setSelected( true );
                return t;
            }
        }
        
        return null;
        
    }
    
    public void deselectAll() {
        for ( FATransition t : transitions ) {
            t.setSelected( false );
        }
        for ( FAState s : states ) {
            s.setSelected( false );
        }
    }
    
    public void addState( FAState state ) {
        
        if ( state != null ) {
            
            states.add( state );
            
            if ( state.isInitial() ) {
                initialState = state;
            }
            
        }
    }
    
    public void addTransition( FATransition transition ) {
        
        if ( transition != null ) {
            
            FATransition tf = null;
            
            for ( FATransition t : transitions ) {
                if ( t.getOriginState() == transition.getOriginState() && 
                        t.getTargetState() == transition.getTargetState() ) {
                    tf = t;
                    break;
                }
            }
            
            if ( tf == null ) {
                transition.setControlPointsVisible( transitionControlPointsVisible );
                transitions.add( transition );
            } else {
                tf.addSymbols( transition.getSymbols() );
            }
            
        }
        
    }
    
    public void updateTransitions() {
        for ( FATransition t : transitions ) {
            t.updateStartAndEndPoints();
        }
    }
    
    public void draggTransitions( MouseEvent e ) {
        for ( FATransition t : transitions ) {
            t.mouseDragged( e );
        }
    }
    
    public void setTransitionsControlPointsVisible( boolean visible ) {
        transitionControlPointsVisible = visible;
        for ( FATransition t : transitions ) {
            t.setControlPointsVisible( visible );
        }
    }
    
    public void mouseHoverStatesAndTransitions( int x, int y ) {
        for ( FATransition t : transitions ) {
            t.mouseHover( x, y );
        }
        for ( FAState s : states ) {
            s.mouseHover( x, y );
        }
    }

    public void setInitialState( FAState initialState ) {
        
        if ( this.initialState != null ) {
            this.initialState.setInitial( false );
        }
        
        this.initialState = initialState;
        
    }

    public FAState getInitialState() {
        return initialState;
    }
    
    public void removeState( FAState state ) {
        
        if ( initialState == state ) {
            initialState = null;
        }
        
        states.remove( state );
        
        List<FATransition> ts = new ArrayList<>();
        for ( FATransition t : transitions ) {
            if ( t.getOriginState() == state || t.getTargetState() == state ) {
                ts.add( t );
            }
        }
        
        for ( FATransition t : ts ) {
            transitions.remove( t );
        }
        
    }
    
    public void removeTransition( FATransition transition ) {
        transitions.remove( transition );
    }
    
}
