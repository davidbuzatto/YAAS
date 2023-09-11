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
import br.com.davidbuzatto.yaas.util.CharacterConstants;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Finite Automaton representation.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FA extends AbstractGeometricForm {
    
    private final List<FAState> states;
    private final List<FATransition> transitions;
    private FAState initialState;
    private FAType type;
    
    private boolean transitionControlPointsVisible;
    
    public FA() {
        states = new ArrayList<>();
        transitions = new ArrayList<>();
        updateType();
    }
    
    public boolean accepts( String str ) {
        return false;
    }

    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        g2d.translate( x1, y1 );
        
        int maxX = 0;
        int maxY = 0;
        
        for ( FATransition t : transitions ) {
            t.draw( g2d );
        }
        
        for ( FAState s : states ) {
            s.draw( g2d );
            if ( maxX < s.getX1() + s.getRadius() ) {
                maxX = s.getX1() + s.getRadius();
            }
            if ( maxY < s.getY1() + s.getRadius() ) {
                maxY = s.getY1() + s.getRadius();
            }
        }
        
        width = x1 + maxX + 100;
        height = y1 + maxY + 100;
        
        for ( FATransition t : transitions ) {
            t.drawLabel( g2d );
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
            if ( t.intercepts( x - x1, y - y1 ) ) {
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
        
        updateType();
        
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
        
        updateType();
        
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
        
        updateType();
        
    }

    public FAState getInitialState() {
        return initialState;
    }

    public FAType getType() {
        return type;
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
        
        updateType();
        
    }
    
    public void removeTransition( FATransition transition ) {
        transitions.remove( transition );
        updateType();
    }
    
    public final void updateType() {
        
        if ( states.isEmpty() ) {
            type = FAType.EMPTY;
            return;
        }
        
        Map<FAState, Map<Character, Integer>> counts = new HashMap<>();
        boolean epsilon = false;
        boolean nondeterminism = false;
        
        for ( FATransition t : transitions ) {
            
            Map<Character, Integer> count = counts.get( t.getOriginState() );
            if ( count == null ) {
                count = new HashMap<>();
                counts.put( t.getOriginState(), count );
            }
            
            for ( char c : t.getSymbols() ) {
                Integer v = count.get( c );
                count.put( c, v == null ? 1 : v+1 );
            }
            
        }
        
        for ( Map.Entry<FAState, Map<Character, Integer>> ec : counts.entrySet() ) {
            for ( Map.Entry<Character, Integer> e : ec.getValue().entrySet() ) {
                if ( e.getKey().equals( CharacterConstants.EMPTY_STRING ) ) {
                    epsilon = true;
                } else if ( e.getValue().compareTo( 1 ) > 0 ) {
                    nondeterminism = true;
                }
            }
            if ( epsilon && nondeterminism ) {
                break;
            }
        }
        
        if ( epsilon ) {
            type = FAType.ENFA;
        } else if ( nondeterminism ) {
            type = FAType.NFA;
        } else {
            type = FAType.DFA;
        }
        
    }
    
    public String getFormalDefinition() {
        
        String def = String.format( "A = { Q, %c, %c, %s, F }\n",
                CharacterConstants.CAPITAL_SIGMA,
                CharacterConstants.SMALL_DELTA, 
                initialState.toString() );
        def += getStatesString() + "\n";
        def += getAlphabetString() + "\n";
        def += getAcceptingStatesString();
        
        return def;
        
    }
    
    public Map<FAState, Map<Character, List<FAState>>> getDelta() {
        
        Map<FAState, Map<Character, List<FAState>>> m = new TreeMap<>();
        for ( FAState s : states ) {
            m.put( s, new TreeMap<>() );
        }
        
        for ( FATransition t : transitions ) {
            Map<Character, List<FAState>> mq = m.get( t.getOriginState() );
            for ( Character sy : t.getSymbols() ) {
                List<FAState> li = mq.get( sy );
                if ( li == null ) {
                    li = new ArrayList<>();
                    mq.put( sy, li );
                }
                li.add( t.getTargetState() );
            }
            
        }
        
        return m;
        
    }
    
    private String getStatesString() {
        
        String str = "";
        
        List<String> ss = new ArrayList<>();
        for ( FAState s : states ) {
            ss.add( s.toString() );
        }
        
        Collections.sort( ss );
        boolean first = true;
        
        str += "Q = { ";
        for ( String s : ss ) {
            if ( !first ) {
                str += ", ";
            }
            str += s;
            first = false;
        }
        str += " }";
        
        return str;
        
    }
    
    private String getAcceptingStatesString() {
        
        String str = "";
        
        List<String> ss = new ArrayList<>();
        for ( FAState s : states ) {
            if ( s.accepting ) {
                ss.add( s.toString() );
            }
        }
        
        Collections.sort( ss );
        boolean first = true;
        
        str += "F = { ";
        for ( String s : ss ) {
            if ( !first ) {
                str += ", ";
            }
            str += s;
            first = false;
        }
        str += " }";
        
        return str;
        
    }
    
    private String getAlphabetString() {
        
        String str = "";
        
        Set<Character> alf = getAlphabet();
        
        boolean first = true;
        
        str += CharacterConstants.CAPITAL_SIGMA + " = { ";
        for ( Character c : alf ) {
            if ( !first ) {
                str += ", ";
            }
            str += c;
            first = false;
        }
        str += " }";
        
        return str;
        
    }
    
    public Set<Character> getAlphabet() {
        
        Set<Character> alf = new TreeSet<>();
        for ( FATransition t : transitions ) {
            for ( char c : t.getSymbols() ) {
                if ( c != CharacterConstants.EMPTY_STRING ) {
                    alf.add( c );
                }
            }
        }
        
        return alf;
        
    }

    public List<FAState> getStates() {
        return states;
    }

    public List<FATransition> getTransitions() {
        return transitions;
    }
    
}
