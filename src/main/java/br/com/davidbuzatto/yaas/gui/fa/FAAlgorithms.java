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
import br.com.davidbuzatto.yaas.util.DrawingConstants;
import br.com.davidbuzatto.yaas.util.SigmaStarGeneratorStream;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * A set of utilitary methods to perform algorithms related to Finite Automata.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAAlgorithms {
    
    /**
     * A helper class that encapsulates data from existing states while the
     * automaton is being processed.
     */
    private static class StateHelper {
        
        public StateHelper( Set<FAState> states ) {
            this( null, states );
        }
        
        public StateHelper( FAState state, Set<FAState> states ) {
            this.state = state;
            this.states = states;
        }
        
        FAState state;
        Set<FAState> states;

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 61 * hash + Objects.hashCode( this.states );
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
            final StateHelper other = (StateHelper) obj;
            return Objects.equals( this.states, other.states );
        }

        @Override
        public String toString() {
            return "CState{" + "state=" + state + ", states=" + states + '}';
        }
        
    }
    
    /**
     * A helper class that encapsulates data from existing transitions while the
     * automaton is being processed.
     */
    private static class TransitionHelper {
        
        StateHelper originState;
        StateHelper targetState;
        Set<Character> symbols;
        
        public TransitionHelper( StateHelper originState, StateHelper targetState, Character symbol ) {
            this.originState = originState;
            this.targetState = targetState;
            this.symbols = new HashSet<>();
            this.symbols.add( symbol );
        }
        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode( this.originState );
            hash = 97 * hash + Objects.hashCode( this.targetState );
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
            final TransitionHelper other = (TransitionHelper) obj;
            if ( !Objects.equals( this.originState, other.originState ) ) {
                return false;
            }
            return Objects.equals( this.targetState, other.targetState );
        }

        @Override
        public String toString() {
            return String.format( "(%s) - %s -> (%s)",
                originState, symbols, targetState );
        }
        
    }
    
    /**
     * Generates a new Finite Automaton without any non-determinisms
     * 
     * @param fa The Finite Automaton to be processed.
     * @return A new Finite Automaton without any non-determinisms.
     */
    public static FA generateDFARemovingNondeterminisms( FA fa ) {
        
        FA dfa = new FA();
        int currentState = 0;
        
        // get data from the original automaton
        Set<Character> alphabet = fa.getAlphabet();
        FAState initialState = fa.getInitialState();
        List<FAState> acceptinStates = fa.getAcceptingStates();
        Map<FAState, Map<Character, List<FAState>>> delta = fa.getDelta();
        Map<FAState, Set<FAState>> ecloses = fa.getEcloses( delta );
        
        // start the process of generation
        
        // creates the new initial state
        FAState dfaInitial = new FAState();
        dfaInitial.setInitial( true );
        dfaInitial.setLabel( "q" + currentState );
        dfaInitial.setCustomLabel( newCustomLabel( currentState++ ) );
        StateHelper initialSH = new StateHelper( dfaInitial, ecloses.get( initialState ) );
        
        // data strutctures to store transitions and states that will be generated
        Set<TransitionHelper> generatedTransitions = new LinkedHashSet<>();
        Set<StateHelper> generatedStates = new LinkedHashSet<>();
        generatedStates.add( initialSH );
        
        // a queue to process the generated states
        Queue<StateHelper> queue = new ArrayDeque<>();
        queue.add( initialSH );
        
        // on demand process of new states
        while ( !queue.isEmpty() ) {
            
            StateHelper current = queue.poll();
            
            for ( Character a : alphabet ) {
                
                Set<FAState> targetEclose = new HashSet<>();
                
                for ( FAState s : current.states ) {
                    
                    List<FAState> t = delta.get( s ).get( a );
                    if ( t != null && !t.isEmpty() ) {
                        for ( FAState ns : t ) {
                            targetEclose.addAll( ecloses.get( ns ) );
                        }
                    }

                }
                
                if ( !targetEclose.isEmpty() ) {

                    StateHelper newSH = new StateHelper( targetEclose );
                    
                    if ( !generatedStates.contains( newSH ) ) {
                        
                        FAState newState = new FAState();
                        newState.setLabel( "q" + currentState );
                        newState.setCustomLabel( newCustomLabel( currentState++ ) );
                        newSH.state = newState;
                        generatedStates.add( newSH );
                        queue.add( newSH );
                        
                    }
                    
                    TransitionHelper newTH = new TransitionHelper( current, newSH, a );
                    if ( generatedTransitions.contains( newTH ) ) {
                        for ( TransitionHelper t : generatedTransitions ) {
                            if ( t.equals( newTH ) ) {
                                t.symbols.addAll( newTH.symbols );
                            }
                        }
                    } else {
                        generatedTransitions.add( newTH );
                    }

                }

            }
            
        }
        
        for ( StateHelper s : generatedStates ) {
            for ( FAState a : acceptinStates ) {
                if ( s.states.contains( a ) ) {
                    s.state.setAccepting( true );
                    break;
                }
            }
        }
        
        for ( StateHelper s : generatedStates ) {
            dfa.addState( s.state );
        }
        
        Map<Set<FAState>, FAState> m = new HashMap<>();
        for ( TransitionHelper t : generatedTransitions ) {
            m.put( t.originState.states, t.originState.state );
        }
        
        for ( TransitionHelper t : generatedTransitions ) {
            t.targetState.state = m.get( t.targetState.states );
            List<Character> symbols = new ArrayList<>();
            symbols.addAll( t.symbols );
            dfa.addTransition( new FATransition( 
                    t.originState.state, 
                    t.targetState.state, symbols ) );
        }
        
        Collections.sort( dfa.getStates() );
        return dfa;
        
    }
    
    /**
     * A helper class that encapsulates a pair os states while the automaton is
     * being processed.
     */
    private static class FAStatePair {
        
        FAState s1;
        FAState s2;
        Set<FAState> ss;

        public FAStatePair( FAState s1, FAState s2 ) {
            this.s1 = s1;
            this.s2 = s2;
            this.ss = new HashSet<>();
            this.ss.add( s1 );
            this.ss.add( s2 );
        }
        
        @Override
        public String toString() {
            return "FAStatePair{" + s1 + ", " + s2 + '}';
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 73 * hash + Objects.hashCode( this.ss );
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
            final FAStatePair other = (FAStatePair) obj;
            return Objects.equals( this.ss, other.ss );
        }
        
    }
    
    /**
     * Uses the Myhill-Nerode Theorem (Table filling method) to create a
     * minimized DFA.
     * 
     * @param fa The DFA to be processed.
     * @return An equivalent DFA, but minimized.
     */
    public static FA generateMinimizedDFA( FA fa ) {
        
        FA dfa = new FA();
        Set<Character> alphabet = fa.getAlphabet();
        Map<FAState, Map<Character, List<FAState>>> delta = fa.getDelta();
        
        // pre-processing step(s)
        // removing inaccessible states
        System.out.println( "Preprocessing step(s)" );
        List<FAState> states = new ArrayList<>( fa.getStates() );
        List<FAState> inaccessibleStates = new ArrayList<>();
        for ( FAState s : states ) {
            if ( isInaccessible( s, fa ) ) {
                System.out.println( "remove inaccesible state: " + s );
                inaccessibleStates.add( s );
            }
        }
        states.removeAll( inaccessibleStates );
        
        /* Myhill-Nerode Theorem:
         *
         * 1) Using all states, create all possible pairs;
         * 2) Remove all pairs such one element is an accepting state and the
         *    other is not;
         * 3) For each remaining pair, verify the transition function
         *    incrementing the input length. If the transition is not equal,
         *    remove the pair;
         * 4) The remaining pairs must be combined with theris equivalent
         *    states.
         */
        
        // 1) Using all states, create all possible pairs;
        System.out.println( "\nStep 01" );
        Set<FAStatePair> pairs = new LinkedHashSet<>();
        int sSize = states.size();
        for ( int i = 0; i < sSize; i++ ) {
            for ( int j = i+1; j < sSize; j++ ) {
                pairs.add( new FAStatePair( states.get( i ), states.get( j ) ) );
            }
        }
        
        for ( FAStatePair sp : pairs ) {
            System.out.println( sp );
        }
        
        // 2) Remove all pairs such one element is an accepting state and the
        //    other is not;
        System.out.println( "\nStep 02" );
        Set<FAStatePair> toRemove = new HashSet<>();
        for ( FAStatePair sp : pairs ) {
            if ( ( sp.s1.isAccepting() && !sp.s2.isAccepting() ) || 
                 ( !sp.s1.isAccepting() && sp.s2.isAccepting() ) ) {
                toRemove.add( sp );
            }
        }
        
        for ( FAStatePair sp : toRemove ) {
            System.out.println( "remove step 02: " + sp );
        }
        pairs.removeAll( toRemove );
        
        for ( FAStatePair sp : pairs ) {
            System.out.println( sp );
        }
        
        // 3) For each remaining pair, verify the transition function
        //    incrementing the input length. If the transition is not equal,
        //    remove the pair;
        System.out.println( "\nStep 03" );
        toRemove.clear();
        SigmaStarGeneratorStream ssgs = new SigmaStarGeneratorStream( alphabet );
        for ( FAStatePair sp : pairs ) {
            if ( !isDistinguishable( sp.s1, sp.s2, fa, ssgs ) ) {
                toRemove.add( sp );
            }
        }
        
        for ( FAStatePair sp : toRemove ) {
            System.out.println( "remove step 03: " + sp );
        }
        pairs.removeAll( toRemove );
        
        for ( FAStatePair sp : pairs ) {
            System.out.println( sp );
        }
        
        // 4) The remaining pairs must be combined with their equivalent
        //    states.
        System.out.println( "\nStep 04" );
                
        return dfa;
        
    }
    
    /**
     * Verifies if a state is inaccessible.
     * 
     * @param state The state to verify.
     * @param fa The automaton containing the state that will be verified.
     * @return true if the state is inaccessible, false otherwise.
     */
    public static boolean isInaccessible( FAState state, FA fa ) {
        
        if ( state.isInitial() ) {
            return false;
        }
        
        for ( FATransition t : fa.getTransitions() ) {
            if ( state.equals(  t.getTargetState() ) ) {
                return false;
            }
        }
        
        return true;
        
    }
    
    /**
     * Verifies if two states are distinguishable.
     * 
     * @param s1 One FA state.
     * @param s2 Another FA state.
     * @param fa The automaton containing the states that will be verified.
     * @param ssgs The Σ* generator
     * @return true if the states are distinguishable, false otherwise.
     */
    public static boolean isDistinguishable( 
            FAState s1, 
            FAState s2,
            FA fa,
            SigmaStarGeneratorStream ssgs ) {
        
        Set<Character> alphabet = fa.getAlphabet();
        Map<FAState, Map<Character, List<FAState>>> delta = fa.getDelta();
            
        return true;
        
    }
    
    public static void main( String[] args ) {
        
        FA dfa = FAExamples.createDFAForMinimization();
        FA min = generateMinimizedDFA( dfa );
        
        
    }
    
    /**
     * Add all missing transitions to a DFA.
     * 
     * @param fa The DFA to be processed.
     * @param currentState Whats the current state counter.
     */
    public static void addAllMissingTransitions( FA fa, int currentState ) {
        addAllMissingTransitions( 
                fa, 
                currentState, 
                true, 
                DrawingConstants.NULL_STATE_FILL_COLOR,
                DrawingConstants.NULL_STATE_STROKE_COLOR,
                DrawingConstants.NULL_TRANSITION_STROKE_COLOR );
    }
    
    /**
     * Add all missing transitions to a DFA.
     * 
     * @param fa The DFA to be processed.
     * @param currentState Whats the current state counter.
     * @param useNullCustomLabel If it will use the empty set symbol do set the
     * custom label of the generated state (null state).
     * @param nullStateFillColor The color to set the fill in the null state.
     * @param nullStateStrokeColor The color to set the stroke in the null state.
     * @param nullTransitionStrokeColor The color to set in thes troke the null transition.
     */
    public static void addAllMissingTransitions( 
            FA fa, int currentState, 
            boolean useNullCustomLabel,
            Color nullStateFillColor,
            Color nullStateStrokeColor,
            Color nullTransitionStrokeColor ) {
        
        Map<FAState, Set<Character>> allMissing = new HashMap<>();
        Map<FAState, Map<Character, List<FAState>>> delta = fa.getDelta();
        List<Character> alphabet = new ArrayList<>( fa.getAlphabet() );
        
        for ( FAState s : fa.getStates() ) {
            Set<Character> a = new TreeSet<>( alphabet );
            allMissing.put( s, a );
        }
        
        Set<FAState> toRemove = new HashSet<>();
        
        for ( Map.Entry<FAState, Map<Character, List<FAState>>> e : delta.entrySet() ) {
            for ( Map.Entry<Character, List<FAState>> t : e.getValue().entrySet() ) {
                Set<Character> cSet = allMissing.get( e.getKey() );
                if ( !cSet.isEmpty() ) {
                    cSet.remove( t.getKey() );
                    if ( cSet.isEmpty() ) {
                        toRemove.add( e.getKey() );
                    }
                }
            }
        }
        
        for ( FAState s : toRemove ) {
            allMissing.remove( s );
        }
        
        if ( !allMissing.isEmpty() ) {
            
            FAState nullState = new FAState();
            nullState.setLabel( "q" + currentState );
            if ( useNullCustomLabel ) {
                nullState.setCustomLabel( CharacterConstants.EMPTY_SET.toString() );
            }
            nullState.setFillColor( nullStateFillColor );
            nullState.setStrokeColor( nullStateStrokeColor );
            nullState.setX1( 200 );
            nullState.setY1( 200 );
            fa.addState( nullState );
            
            FATransition nullTransition = new FATransition( 
                    nullState, nullState, alphabet );
            nullTransition.setStrokeColor( nullStateStrokeColor );
            fa.addTransition( nullTransition );
            
            for ( Map.Entry<FAState, Set<Character>> e : allMissing.entrySet() ) {
                List<Character> s = new ArrayList<>( e.getValue() );
                FATransition t = new FATransition( e.getKey(), nullState, s );
                t.setStrokeColor( nullTransitionStrokeColor );
                fa.addTransition( t );
            }
            
        }
        
    }
    
    public static void complementDFA( FA fa, int currentState ) {
        
        addAllMissingTransitions( 
                fa, 
                currentState, 
                false, 
                DrawingConstants.STATE_FILL_COLOR,
                DrawingConstants.STATE_STROKE_COLOR,
                DrawingConstants.TRANSITION_STROKE_COLOR );
        
        for ( FAState s : fa.getStates() ) {
            s.setAccepting( !s.isAccepting() );
        }
        
    }
    
    /**
     * Generates 702 custom labels (A, B, C, ... , ZX, ZY and ZZ).
     * 
     * @param index The index of the custom label 0 through 701
     * @return The custom label.
     */
    public static String newCustomLabel( int index ) {
        
        if ( index < 0 || index > 701 ) {
            return "";
        }
        
        int d = index / 26;
        int u = index % 26;
        
        if ( d != 0 ) {
            return String.valueOf( (char) ('A' + (d-1) ) ) +
                   String.valueOf( (char) ('A' + u) );
        } else {
            return String.valueOf( (char) ('A' + u) );
        }
            
    }
    
    /**
     * Reorganize Finite Automata horizontally.
     * 
     * @param fa The Finite Automata to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param distance The horizontal distance between each state.
     */
    public static void arrangeFAHorizontally( 
            FA fa, int xCenter, int yCenter, int distance ) {
        arrangeFARectangularly( fa, xCenter, yCenter, fa.getStates().size(), distance );
    }
    
    /**
     * Reorganize Finite Automata vertically.
     * 
     * @param fa The Finite Automata to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param distance The vertical distance between each state.
     */
    public static void arrangeFAVertically( 
            FA fa, int xCenter, int yCenter, int distance ) {
        arrangeFARectangularly( fa, xCenter, yCenter, 1, distance );
    }
    
    /**
     * Reorganize Finite Automata diagonally.
     * 
     * @param fa The Finite Automata to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param distance The diagonal distance between each state.
     */
    public static void arrangeFADiagonally( 
            FA fa, int xCenter, int yCenter, int distance ) {
        
        FAState initialState = fa.getInitialState();
        List<FAState> states = new ArrayList<>();
        states.addAll( fa.getStates() );
        
        if ( initialState != null ) {
            states.remove( initialState );
            states.add( 0, initialState );
        }
        
        int distanceX = (int) ( distance * Math.cos( Math.PI/4 ) );
        int distanceY = (int) ( distance * Math.sin( Math.PI/4 ) );
        int currentX = xCenter;
        int currentY = yCenter;
        
        for ( FAState s : states ) {
            s.setX1( currentX );
            s.setY1( currentY );
            currentX += distanceX;
            currentY += distanceY;
        }
        
        fa.resetTransitionsTransformations();
        
    }
    
    /**
     * Reorganize Finite Automata rectangularly.
     * 
     * @param fa The Finite Automata to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param cols How many columns.
     * @param distance The horizontal and vertical distance between each state.
     */
    public static void arrangeFARectangularly( 
            FA fa, int xCenter, int yCenter, int cols, int distance ) {
        
        FAState initialState = fa.getInitialState();
        List<FAState> states = new ArrayList<>();
        states.addAll( fa.getStates() );
        
        if ( initialState != null ) {
            states.remove( initialState );
            states.add( 0, initialState );
        }
        
        int currentX = xCenter;
        int currentY = yCenter;
        int currentColumn = 1;
        
        for ( FAState s : states ) {
            
            s.setX1( currentX );
            s.setY1( currentY );
            
            currentColumn++;
            currentX += distance;

            if ( currentColumn > cols ) {
                currentColumn = 1;
                currentX = xCenter;
                currentY += distance;
            }
            
        }
        
        fa.resetTransitionsTransformations();
        
    }
    
    /**
     * Reorganize Finite Automata states in a circle.
     * 
     * @param fa The Finite Automata to be processed.
     * @param xCenter The x center of the circle.
     * @param yCenter The y center of the circle.
     * @param radius The distance of each state to the center.
     */
    public static void arrangeFAInCircle( 
            FA fa, int xCenter, int yCenter, int radius ) {
        
        FAState initialState = fa.getInitialState();
        List<FAState> states = new ArrayList<>();
        states.addAll( fa.getStates() );
        
        if ( initialState != null ) {
            states.remove( initialState );
            states.add( 0, initialState );
        }
        
        double inc = Math.PI * 2 / fa.getStates().size();
        double current = Math.PI;
        
        for ( FAState s : states ) {
            s.setX1( xCenter + (int) ( radius * Math.cos( current ) ) );
            s.setY1( yCenter + (int) ( radius * Math.sin( current ) ) );
            current += inc;
        }
        
        fa.resetTransitionsTransformations();
        
    }
    
    /**
     * Reorganize Finite Automata by levels starting from initial state.
     * 
     * @param fa The Finite Automata to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param distance The distance between levels and between states horizontally.
     */
    public static void arrangeFAByLevel( 
            FA fa, int xCenter, int yCenter, int distance, boolean vertical ) {
        
        int currentX;
        int currentY;
        
        FAState initialState = fa.getInitialState();
        Map<FAState, Map<Character, List<FAState>>> delta = fa.getDelta();
        
        Map<Integer, List<FAState>> leveledStates = new TreeMap<>();
        List<FAState> liInitial = new ArrayList<>();
        liInitial.add( initialState );
        leveledStates.put( 0, liInitial );
        
        Map<FAState, Integer> distanceTo = new HashMap<>();
        for ( FAState e : fa.getStates() ) {
            distanceTo.put( e, Integer.MAX_VALUE );
        }
        distanceTo.put( initialState, 0 );
        
        Set<FAState> visited = new HashSet<>();
        visited.add( initialState );
        
        Queue<FAState> queue = new ArrayDeque<>();
        queue.add( initialState );
        int maxDistance = 0;
        
        while ( !queue.isEmpty() ) {
            
            FAState current = queue.poll();
            
            Set<FAState> children = new HashSet<>();
            for ( Map.Entry<Character, List<FAState>> e : delta.get( current ).entrySet() ) {
                children.addAll( e.getValue() );
            }
            List<FAState> lChildren = new ArrayList<>( children );
            Collections.sort( lChildren );
            
            for ( FAState e : lChildren ) {
                if ( !visited.contains( e ) ) {
                    
                    int d = distanceTo.get( current ) + 1;
                    if ( maxDistance < d ) {
                        maxDistance = d;
                    }
                    
                    distanceTo.put( e, d );
                    visited.add( e );
                    queue.add( e );
                    
                    List<FAState> es = leveledStates.get( d );
                    if ( es == null ) {
                        es = new ArrayList<>();
                        leveledStates.put( d, es );
                    }
                    es.add( e );
                    
                }
            }
            
        }
        
        List<FAState> inaccessible = new ArrayList<>();
        for ( FAState e : fa.getStates() ) {
            if ( !visited.contains( e ) ) {
                inaccessible.add( e );
            }
        }
        if ( !inaccessible.isEmpty() ) {
            leveledStates.put( maxDistance + 1, inaccessible );
        }
        
        if ( vertical ) {
            
            for ( Map.Entry<Integer, List<FAState>> e : leveledStates.entrySet() ) {

                currentX = xCenter;
                currentY = yCenter + e.getKey() * distance;

                Collections.sort( e.getValue() );
                
                for ( FAState s : e.getValue() ) {
                    s.setX1( currentX );
                    s.setY1( currentY );
                    currentX += distance;
                }

            }
            
        } else {
            
            for ( Map.Entry<Integer, List<FAState>> e : leveledStates.entrySet() ) {

                currentX = xCenter + e.getKey() * distance;
                currentY = yCenter;
                
                Collections.sort( e.getValue() );

                for ( FAState s : e.getValue() ) {
                    s.setX1( currentX );
                    s.setY1( currentY );
                    currentY += distance;
                }

            }
            
        }
        
        fa.resetTransitionsTransformations();
        
    }
    
}
