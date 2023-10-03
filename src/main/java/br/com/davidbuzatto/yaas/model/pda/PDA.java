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

import br.com.davidbuzatto.yaas.gui.pda.PDASimulationStep;
import br.com.davidbuzatto.yaas.model.AbstractGeometricForm;
import br.com.davidbuzatto.yaas.util.CharacterConstants;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Pushdown Automaton representation and algorithms.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDA extends AbstractGeometricForm implements Cloneable {
    
    private List<PDAState> states;
    private List<PDATransition> transitions;
    private PDAState initialState;
    
    private List<Character> startingPushSymbols;
    
    // cache control
    private boolean alphabetUpToDate;
    private boolean stackAlphabetUpToDate;
    private boolean deltaUpToDate;
    
    private Set<Character> alphabet;
    private Set<Character> stackAlphabet;
    private Map<PDAState, List<PDATransition>> delta;
    
    private boolean transitionControlPointsVisible;
    
    public PDA() {
        states = new ArrayList<>();
        transitions = new ArrayList<>();
        startingPushSymbols = new ArrayList<>();
    }
    
    public boolean accepts( String str ) {
        return accepts( str, null );
    }
    
    // TODO implement accepts  (depends on delta update)
    public boolean accepts( String str, List<PDASimulationStep> simulationSteps ) {
        
        if ( canExecute() ) {
            
            return false;
            
        }
        
        return false;
        
    }

    public boolean canExecute() {
        return initialState != null;
    }
    
    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        int maxX = 0;
        int maxY = 0;
        
        for ( PDATransition t : transitions ) {
            t.draw( g2d );
        }
        
        for ( PDAState s : states ) {
            s.draw( g2d );
            if ( maxX < s.getX1() + s.getRadius() ) {
                maxX = s.getX1() + s.getRadius();
            }
            if ( maxY < s.getY1() + s.getRadius() ) {
                maxY = s.getY1() + s.getRadius();
            }
        }
        
        width = maxX + 100;
        height = maxY + 100;
        
        for ( PDATransition t : transitions ) {
            t.drawLabel( g2d );
        }
        
        g2d.dispose();
        
    }

    @Override
    public boolean intersects( int x, int y ) {
        return false;
    }

    @Override
    public void move( int xAmount, int yAmount ) {
        for ( PDATransition t : transitions ) {
            t.move( xAmount, yAmount );
        }
        for ( PDAState s : states ) {
            s.move( xAmount, yAmount );
        }
    }
    
    public PDAState getStateAt( int x, int y ) {
        
        for ( int i = states.size()-1; i >= 0; i-- ) {
            PDAState s = states.get( i );
            if ( s.intersects( x, y ) ) {
                s.setSelected( true );
                return s;
            }
        }
        
        return null;
        
    }
    
    public PDATransition getTransitionAt( int x, int y ) {
        
        for ( int i = transitions.size()-1; i >= 0; i-- ) {
            PDATransition t = transitions.get( i );
            if ( t.intersects( x, y ) ) {
                t.setSelected( true );
                return t;
            }
        }
        
        return null;
        
    }
    
    public void deselectAll() {
        for ( PDATransition t : transitions ) {
            t.setSelected( false );
        }
        for ( PDAState s : states ) {
            s.setSelected( false );
        }
    }
    
    public void addState( PDAState state ) {
        
        if ( state != null ) {
            
            states.add( state );
            
            if ( state.isInitial() ) {
                initialState = state;
            }
            
        }
        
        markAllCachesAsObsolete();
        
    }
    
    public void addTransition( PDATransition transition ) {
        
        if ( transition != null ) {
            
            PDATransition tf = null;
            
            for ( PDATransition t : transitions ) {
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
                tf.addOperations( transition.getOperations() );
            }
            
        }
        
        updateTransitions();
        markAllCachesAsObsolete();
        
    }
    
    public void updateTransitions() {
        for ( PDATransition t : transitions ) {
            t.updateStartAndEndPoints();
        }
    }
    
    public void resetTransitionsTransformations() {
        for ( PDATransition t : transitions ) {
            t.resetTransformations();
        }
    }
    
    public void draggTransitions( MouseEvent evt ) {
        for ( PDATransition t : transitions ) {
            t.mouseDragged( evt );
        }
    }
    
    public void setTransitionsControlPointsVisible( boolean visible ) {
        transitionControlPointsVisible = visible;
        for ( PDATransition t : transitions ) {
            t.setControlPointsVisible( visible );
        }
    }
    
    public void mouseHoverStatesAndTransitions( int x, int y ) {
        for ( PDATransition t : transitions ) {
            t.mouseHover( x, y );
        }
        for ( PDAState s : states ) {
            s.mouseHover( x, y );
        }
    }

    public void setInitialState( PDAState initialState ) {
        
        if ( this.initialState != null ) {
            this.initialState.setInitial( false );
        }
        
        this.initialState = initialState;
        
        markAllCachesAsObsolete();
        
    }

    public PDAState getInitialState() {
        return initialState;
    }
    
    public void removeState( PDAState state ) {
        
        if ( initialState == state ) {
            initialState = null;
        }
        
        states.remove( state );
        
        List<PDATransition> ts = new ArrayList<>();
        for ( PDATransition t : transitions ) {
            if ( t.getOriginState() == state || t.getTargetState() == state ) {
                ts.add( t );
            }
        }
        
        for ( PDATransition t : ts ) {
            transitions.remove( t );
        }
        
        markAllCachesAsObsolete();
        
    }
    
    public void removeTransition( PDATransition transition ) {
        transitions.remove( transition );
        markAllCachesAsObsolete();
    }
    
    public String getFormalDefinition() {
        
        String def = String.format( "A = { Q, %c, %c, %c, %s, F }\n",
                CharacterConstants.CAPITAL_SIGMA,
                CharacterConstants.CAPITAL_GAMMA,
                CharacterConstants.SMALL_DELTA, 
                initialState.toString() );
        def += getStatesString() + "\n";
        def += getAlphabetString() + "\n";
        def += getStackAlphabetString() + "\n";
        def += getAcceptingStatesString();
        
        return def;
        
    }
    
    public Set<Character> getAlphabet() {
        
        if ( alphabet == null || !alphabetUpToDate ) {
            
            alphabetUpToDate = true;
            alphabet = new TreeSet<>();
        
            for ( PDATransition t : transitions ) {
                for ( PDAOperation o : t.getOperations() ) {
                    if ( o.getSymbol() != CharacterConstants.EMPTY_STRING ) {
                        alphabet.add( o.getSymbol() );
                    }
                }
            }
        
        }
        
        return alphabet;
        
    }
    
    public Set<Character> getStackAlphabet() {
        
        if ( stackAlphabet == null || !stackAlphabetUpToDate ) {
            
            stackAlphabetUpToDate = true;
            stackAlphabet = new TreeSet<>();
            
            stackAlphabet.add( CharacterConstants.STACK_STARTING_SYMBOL );
            stackAlphabet.addAll( startingPushSymbols );
        
            for ( PDATransition t : transitions ) {
                for ( PDAOperation o : t.getOperations() ) {
                    stackAlphabet.add( o.getTop() );
                    stackAlphabet.addAll( o.getSymbolsToPush() );
                }
            }
        
        }
        
        return stackAlphabet;
        
    }
    
    public Map<PDAState, List<PDATransition>> getDelta() {
        
        if ( delta == null || !deltaUpToDate ) {
            
            deltaUpToDate = true;
            
            delta = new TreeMap<>();
            for ( PDAState s : states ) {
                delta.put( s, new ArrayList<>() );
            }

            for ( PDATransition t : transitions ) {
                List<PDATransition> ts = delta.get( t.getOriginState() );
                ts.add( t );
            }
            
        }
        
        return delta;
        
    }
    
    private String getStatesString() {
        
        String str = "";
        
        List<String> ss = new ArrayList<>();
        for ( PDAState s : states ) {
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
        for ( PDAState s : states ) {
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
    
    public List<PDAState> getAcceptingStates() {
        
        List<PDAState> acStates = new ArrayList<>();
        
        for ( PDAState s : states ) {
            if ( s.isAccepting() ) {
                acStates.add( s );
            }
        }
        
        return acStates;
        
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
    
    private String getStackAlphabetString() {
        
        String str = "";
        
        Set<Character> alf = getStackAlphabet();
        
        boolean first = true;
        
        str += CharacterConstants.CAPITAL_GAMMA + " = { ";
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

    public List<PDAState> getStates() {
        return states;
    }

    public List<PDATransition> getTransitions() {
        return transitions;
    }

    public List<Character> getStartingPushSymbols() {
        return startingPushSymbols;
    }

    public void setStartingPushSymbols( List<Character> startingPushSymbols ) {
        this.startingPushSymbols = startingPushSymbols;
        markAllCachesAsObsolete();
    }
    
    public void deactivateAllStatesInSimulation() {
        for ( PDAState s : states ) {
            s.setActiveInSimulation( false );
        }
    }
    
    public void markAllCachesAsObsolete() {
        alphabetUpToDate = false;
        stackAlphabetUpToDate = false;
        deltaUpToDate = false;
    }
    
    public void merge( PDA fa ) {
        
        for ( PDAState s : fa.getStates() ) {
            addState( s );
        }
        
        for ( PDATransition t : fa.getTransitions() ) {
            addTransition( t );
        }
        
    }
    
    public String generateCode() {
        
        String className = getClass().getSimpleName();
        String modelName = "pda";
        
        String template =
                """
                public static %s create%s() {
                
                    %s %s = new %s();
                
                    // states
                %s
                
                    // transitions
                %s
                
                    return %s;
                
                }""";
        
        StringBuilder stBuilderInst = new StringBuilder();
        boolean first = true;
        for ( PDAState s : this.states ) {
            if ( !first ) {
                stBuilderInst.append( "\n\n" );
            }
            stBuilderInst.append( s.generateCode( modelName ) );
            first = false;
        }
        
        StringBuilder tBuilder = new StringBuilder();
        first = true;
        for ( PDATransition t : this.transitions ) {
            if ( !first ) {
                tBuilder.append( "\n\n" );
            }
            tBuilder.append( t.generateCode( modelName ) );
            first = false;
        }
        
        return String.format( template, 
                className, modelName.toUpperCase(), 
                className, modelName, className, 
                stBuilderInst.toString(), 
                tBuilder.toString(),
                modelName );
        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode( this.states );
        hash = 23 * hash + Objects.hashCode( this.transitions );
        hash = 23 * hash + Objects.hashCode( this.initialState );
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final PDA other = (PDA) obj;
        if ( !Objects.equals( this.states, other.states ) ) {
            return false;
        }
        if ( !Objects.equals( this.transitions, other.transitions ) ) {
            return false;
        }
        return Objects.equals( this.initialState, other.initialState );
    }
    
    @Override
    @SuppressWarnings( "unchecked" )
    public Object clone() throws CloneNotSupportedException {
        
        PDA c = (PDA) super.clone();
        Map<PDAState, PDAState> ref = new HashMap<>();
    
        c.states = new ArrayList<>();
        for ( PDAState s : states ) {
            PDAState n = (PDAState) s.clone();
            c.addState( n );
            ref.put( s, n );
        }
        
        c.transitions = new ArrayList<>();
        for ( PDATransition t : transitions ) {
            PDATransition n = (PDATransition) t.clone();
            n.setOriginState( ref.get( t.getOriginState() ) );
            n.setTargetState( ref.get( t.getTargetState() ) );
            c.addTransition( n );
        }
        
        // c.initialState = null;  <- c.addState() resolves it accordingly
        
        c.startingPushSymbols = new ArrayList<>();
        for ( char ch : startingPushSymbols ) {
            c.startingPushSymbols.add( ch );
        }
        
        c.alphabetUpToDate = false;
        c.stackAlphabetUpToDate = false;
        c.deltaUpToDate = false;

        c.alphabet = null;
        c.stackAlphabet = null;
        c.delta = null;

        c.transitionControlPointsVisible = false;
        
        return c;
        
    }
    
}
