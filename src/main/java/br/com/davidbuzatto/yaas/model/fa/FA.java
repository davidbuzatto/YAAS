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
package br.com.davidbuzatto.yaas.model.fa;

import br.com.davidbuzatto.yaas.gui.fa.FASimulationStep;
import br.com.davidbuzatto.yaas.model.AbstractGeometricForm;
import br.com.davidbuzatto.yaas.util.CharacterConstants;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Finite Automaton representation and algorithms.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FA extends AbstractGeometricForm implements Cloneable {
    
    private List<FAState> states;
    private List<FATransition> transitions;
    private FAState initialState;
    private FAType type;
    
    // cache control
    private boolean alphabetUpToDate;
    private boolean deltaUpToDate;
    private boolean eclosesUpToDate;
    
    private Set<Character> alphabet;
    private Map<FAState, Map<Character, List<FAState>>> delta;
    private Map<FAState, Set<FAState>> ecloses;
    
    private boolean transitionControlPointsVisible;
    
    public FA() {
        states = new ArrayList<>();
        transitions = new ArrayList<>();
        type = FAType.EMPTY;
    }
    
    public boolean accepts( String str ) {
        return accepts( str, null );
    }
    
    public boolean accepts( String str, List<FASimulationStep> simulationSteps ) {
        
        if ( canExecute() ) {
            
            Map<FAState, Map<Character, List<FAState>>> delta = getDelta();
            Map<FAState, Set<FAState>> ecloses = getEcloses( delta );
            
            Set<FAState> currentStates = new HashSet<>();
            currentStates.addAll( ecloses.get( initialState ) );
            
            if ( simulationSteps != null ) {
                simulationSteps.add( new FASimulationStep( currentStates, Character.MIN_SURROGATE ) );
            }
            
            for ( char c : str.toCharArray() ) {
                
                Set<FAState> targetStates = new HashSet<>();
                
                for ( FAState s : currentStates ) {
                    
                    Map<Character, List<FAState>> t = delta.get( s );
                    
                    if ( t.containsKey( c ) ) {
                        targetStates.addAll( t.get( c ) );
                    }
                    
                }
                
                if ( targetStates.isEmpty() ) {
                    return false;
                }
                
                Set<FAState> targetEclose = new HashSet<>();
                for ( FAState s : targetStates ) {
                    targetEclose.addAll( ecloses.get( s ) );
                }
                
                /*System.out.println( currentStates + " " + c + " " + targetEclose );
                System.out.println();*/
                
                currentStates = targetEclose;
                
                if ( simulationSteps != null ) {
                    simulationSteps.add( new FASimulationStep( currentStates, c ) );
                }
                
            }
            
            for ( FAState s : currentStates ) {
                if ( s.isAccepting() ) {
                    return true;
                }
            }
            
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
        
        width = maxX + 100;
        height = maxY + 100;
        
        for ( FATransition t : transitions ) {
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
        for ( FATransition t : transitions ) {
            t.move( xAmount, yAmount );
        }
        for ( FAState s : states ) {
            s.move( xAmount, yAmount );
        }
    }
    
    public FAState getStateAt( int x, int y ) {
        
        for ( int i = states.size()-1; i >= 0; i-- ) {
            FAState s = states.get( i );
            if ( s.intersects( x, y ) ) {
                s.setSelected( true );
                return s;
            }
        }
        
        return null;
        
    }
    
    public FATransition getTransitionAt( int x, int y ) {
        
        for ( int i = transitions.size()-1; i >= 0; i-- ) {
            FATransition t = transitions.get( i );
            if ( t.intersects( x, y ) ) {
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
        
        markAllCachesAsObsolete();
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
        
        markAllCachesAsObsolete();
        updateType();
        
    }
    
    public void updateTransitions() {
        for ( FATransition t : transitions ) {
            t.updateStartAndEndPoints();
        }
    }
    
    public void resetTransitionsTransformations() {
        for ( FATransition t : transitions ) {
            t.resetTransformations();
        }
    }
    
    public void draggTransitions( MouseEvent evt ) {
        for ( FATransition t : transitions ) {
            t.mouseDragged( evt );
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
        
        markAllCachesAsObsolete();
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
        
        markAllCachesAsObsolete();
        updateType();
        
    }
    
    public void removeTransition( FATransition transition ) {
        transitions.remove( transition );
        markAllCachesAsObsolete();
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
    
    public Set<Character> getAlphabet() {
        
        if ( alphabet == null || !alphabetUpToDate ) {
            
            alphabetUpToDate = true;
            alphabet = new TreeSet<>();
        
            for ( FATransition t : transitions ) {
                for ( char c : t.getSymbols() ) {
                    if ( c != CharacterConstants.EMPTY_STRING ) {
                        alphabet.add( c );
                    }
                }
            }
        
        }
        
        return alphabet;
        
    }
    
    public Map<FAState, Map<Character, List<FAState>>> getDelta() {
        
        if ( delta == null || !deltaUpToDate ) {
            
            deltaUpToDate = true;
            
            delta = new TreeMap<>();
            for ( FAState s : states ) {
                delta.put( s, new TreeMap<>() );
            }

            for ( FATransition t : transitions ) {
                Map<Character, List<FAState>> mq = delta.get( t.getOriginState() );
                for ( Character sy : t.getSymbols() ) {
                    List<FAState> li = mq.get( sy );
                    if ( li == null ) {
                        li = new ArrayList<>();
                        mq.put( sy, li );
                    }
                    li.add( t.getTargetState() );
                }
            }
            
        }
        
        return delta;
        
    }
    
    public Map<FAState, Set<FAState>> getEcloses( 
            Map<FAState, Map<Character, List<FAState>>> delta ) {
        
        if ( ecloses == null || !eclosesUpToDate ) {
            
            eclosesUpToDate = true;
            ecloses = new HashMap<>();
            
            for ( FAState e : delta.keySet() ) {
                ecloses.put( e, discoverEclose( e, delta ) );
            }
            
        }
        
        return ecloses;
        
    }
    
    private Set<FAState> discoverEclose( 
            FAState s, 
            Map<FAState, Map<Character, List<FAState>>> delta ) {
        
        Set<FAState> eclose = new TreeSet<>();
        Set<FAState> visited = new HashSet<>();
        
        discoverEcloseR( s, eclose, visited, delta );
        
        return eclose;
        
    }
    
    private void discoverEcloseR( 
            FAState s, 
            Set<FAState> eclose, 
            Set<FAState> visited, 
            Map<FAState, Map<Character, List<FAState>>> delta ) {
        
        if ( !visited.contains( s ) ) {
            
            eclose.add( s );
            visited.add( s );
            
            Map<Character, List<FAState>> transitions = delta.get( s );
            
            if ( transitions.containsKey( CharacterConstants.EMPTY_STRING ) ) {
                for ( FAState target : transitions.get( CharacterConstants.EMPTY_STRING ) ) {
                    discoverEcloseR( target, eclose, visited, delta );
                }
            }
            
        }
        
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
    
    public List<FAState> getAcceptingStates() {
        
        List<FAState> acStates = new ArrayList<>();
        
        for ( FAState s : states ) {
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

    public List<FAState> getStates() {
        return states;
    }

    public List<FATransition> getTransitions() {
        return transitions;
    }
    
    public void deactivateAllStatesInSimulation() {
        for ( FAState s : states ) {
            s.setActiveInSimulation( false );
        }
    }
    
    public void markAllCachesAsObsolete() {
        alphabetUpToDate = false;
        deltaUpToDate = false;
        eclosesUpToDate = false;
    }
    
    public void merge( FA fa ) {
        
        for ( FAState s : fa.getStates() ) {
            addState( s );
        }
        
        for ( FATransition t : fa.getTransitions() ) {
            addTransition( t );
        }
        
    }
    
    public String generateCode() {
        
        updateType();
        String className = getClass().getSimpleName();
        String modelName = "";
        
        switch ( type ) {
            case EMPTY:
                modelName = "fa";
                break;
            case DFA:
                modelName = "dfa";
                break;
            case NFA:
                modelName = "nfa";
                break;
            case ENFA:
                modelName = "enfa";
                break;
        }
        
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
        for ( FAState s : this.states ) {
            if ( !first ) {
                stBuilderInst.append( "\n\n" );
            }
            stBuilderInst.append( s.generateCode( modelName ) );
            first = false;
        }
        
        StringBuilder tBuilder = new StringBuilder();
        first = true;
        for ( FATransition t : this.transitions ) {
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
        final FA other = (FA) obj;
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
        
        FA c = (FA) super.clone();
        Map<FAState, FAState> ref = new HashMap<>();
    
        c.states = new ArrayList<>();
        for ( FAState s : states ) {
            FAState n = (FAState) s.clone();
            c.addState( n );
            ref.put( s, n );
        }
        
        c.transitions = new ArrayList<>();
        for ( FATransition t : transitions ) {
            FATransition n = (FATransition) t.clone();
            n.setOriginState( ref.get( t.getOriginState() ) );
            n.setTargetState( ref.get( t.getTargetState() ) );
            c.addTransition( n );
        }
        
        // c.initialState = null;  <- c.addState() resolves it accordingly
        // c.type = null;          <- c.updateType() resolves it accordingly
        
        c.alphabetUpToDate = false;
        c.deltaUpToDate = false;
        c.eclosesUpToDate = false;

        c.alphabet = null;
        c.delta = null;
        c.ecloses = null;

        c.transitionControlPointsVisible = false;
        
        c.updateType();
        return c;
        
    }
    
}
