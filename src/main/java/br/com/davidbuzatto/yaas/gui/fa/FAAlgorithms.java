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
     * @return An equivalent DFA (not minimum).
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
     * @param dfa The DFA to be processed.
     * @return An equivalent DFA, but minimized.
     */
    public static FA generateMinimizedDFA( FA dfa ) {
        
        if ( dfa.getType() != FAType.DFA ) {
            throw new IllegalArgumentException( "You must use an DFA!" );
        }
        
        FA minDfa = new FA();
        Map<FAState, Map<Character, List<FAState>>> delta = dfa.getDelta();
        
        // pre-processing step(s)
        // removing inaccessible states
        List<FAState> states = new ArrayList<>( dfa.getStates() );
        List<FAState> inaccessibleStates = new ArrayList<>();
        for ( FAState s : states ) {
            if ( isInaccessible( s, dfa ) ) {
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
         * 4) The remaining pairs must be combined with their equivalent
         *    states.
         */
        
        // 1) Using all states, create all possible pairs;
        //System.out.println( "\nStep 1" );
        Set<FAStatePair> pairs = new LinkedHashSet<>();
        int sSize = states.size();
        for ( int i = 0; i < sSize; i++ ) {
            for ( int j = i+1; j < sSize; j++ ) {
                pairs.add( new FAStatePair( states.get( i ), states.get( j ) ) );
            }
        }
        
        // 2) Remove all pairs such one element is an accepting state and the
        //    other is not;
        Set<FAStatePair> toRemove = new HashSet<>();
        for ( FAStatePair sp : pairs ) {
            if ( ( sp.s1.isAccepting() && !sp.s2.isAccepting() ) || 
                 ( !sp.s1.isAccepting() && sp.s2.isAccepting() ) ) {
                toRemove.add( sp );
            }
        }
        pairs.removeAll( toRemove );
        
        // 2.5) Remove all pairs such the transition function with just one
        // symbol is not the same;
        toRemove.clear();
        for ( FAStatePair sp : pairs ) {
            if ( !haveSameDeltaWithOneSymbol( sp.s1, sp.s2 , dfa ) ) {
                toRemove.add( sp );
            }
        }
        pairs.removeAll( toRemove );
        
        // 3) For each remaining pair, verify the transition function
        //    incrementing the input length. If the transition is not equal,
        //    remove the pair, i.e, if the pair is distinguishable.
        toRemove.clear();
        for ( FAStatePair sp : pairs ) {
            if ( isDistinguishable( sp.s1, sp.s2, dfa ) ) {
                toRemove.add( sp );
            }
        }
        pairs.removeAll( toRemove );
        
        // 4) The remaining pairs must be combined with their equivalent
        //    states. Applying transitivity.
        List<Set<FAState>> newStateSets = new ArrayList<>();
        while ( !pairs.isEmpty() ) {
            
            FAStatePair cPair = pairs.iterator().next();
            Set<FAState> newStateSet = new TreeSet<>();
            newStateSet.add( cPair.s1 );
            newStateSet.add( cPair.s2 );
            
            if ( newStateSets.isEmpty() ) {
                newStateSets.add( newStateSet );
            } else {
                
                boolean addNewStateSet = true;
                
                for ( Set<FAState> s : newStateSets ) {
                    if ( s.contains( cPair.s1 ) ) {
                        s.add( cPair.s2 );
                        addNewStateSet = false;
                    }
                    if ( s.contains( cPair.s2 ) ) {
                        s.add( cPair.s1 );
                        addNewStateSet = false;
                    }
                }
                
                if ( addNewStateSet ) {
                    newStateSets.add( newStateSet );
                }
                
            }
            
            pairs.remove( cPair );
            
        }
        
        // finally, deribing the minized automaton :D
        // the transition generation needs more testing!!!
        int currentState = 0;
        for ( Set<FAState> s : newStateSets ) {
            states.removeAll( s );
        }
        
        // new initial state
        FAState dfaIS = new FAState();
        dfaIS.setInitial( true );
        
        FAState iS = dfa.getInitialState();
        Map<FAState, Set<FAState>> newToSet = new HashMap<>();
        Map<FAState, FAState> originalToNew = new HashMap<>();
        
        for ( FAState s : states ) {
            
            if ( s.isInitial() ) {
                dfaIS.setAccepting( s.isAccepting() );
                dfaIS.setLabel( "q" + currentState++ );
                dfaIS.setCustomLabel( s.getCustomLabel() );
                minDfa.addState( dfaIS );
                originalToNew.put( s, dfaIS );
            } else {
                FAState newState = new FAState();
                newState.setAccepting( s.isAccepting() );
                newState.setLabel( "q" + currentState++ );
                newState.setCustomLabel( s.getCustomLabel() );
                minDfa.addState( newState );
                originalToNew.put( s, newState );
            }
            
        }
        
        for ( Set<FAState> s : newStateSets ) {
            
            String label = "";
            boolean isAccepting = false;
            
            for ( FAState ss : s ) {
                if ( ss.isAccepting() ) {
                    isAccepting = true;
                }
                /*if ( !label.isEmpty() ) {
                    label += ", ";
                }*/
                label += ss.getCustomLabel();
            }
            
            if ( s.contains( iS ) ) {
                dfaIS.setAccepting( isAccepting );
                dfaIS.setLabel( "q" + currentState++ );
                dfaIS.setCustomLabel( label );
                newToSet.put( dfaIS, s );
                minDfa.addState( dfaIS );
            } else {
                FAState newState = new FAState();
                newState.setAccepting( isAccepting );
                newState.setLabel( "q" + currentState++ );
                newState.setCustomLabel( label );
                newToSet.put( newState, s );
                minDfa.addState( newState );
            }
            
        }
        
        for ( Map.Entry<FAState, Set<FAState>> e : newToSet.entrySet() ) {
            for ( FAState s : e.getValue() ) {
                originalToNew.put( s, e.getKey() );
            }
        }
        
        // creating the transitions
        for ( Map.Entry<FAState, FAState> e : originalToNew.entrySet() ) {
            for ( Map.Entry<Character, List<FAState>> tr : delta.get( e.getKey() ).entrySet() ) {
                for ( FAState target : tr.getValue() ) {
                    minDfa.addTransition( new FATransition( e.getValue(), originalToNew.get( target ), tr.getKey() ) );
                }
            }
        }
        
        return minDfa;
        
    }
    
    /**
     * Acceptance algorithm for a DFA, using a custom initialState.
     * 
     * @param str The string to be tested.
     * @param dfa The DFA to be processed.
     * @param initialState The initial state to use (regardless the current
     * DFA initial state)
     * @return true if the string is accepted, false otherwise.
     */
    public static boolean acceptsDFA( String str, FA dfa, FAState initialState )
            throws IllegalArgumentException {
        
        if ( dfa.getType() != FAType.DFA ) {
            throw new IllegalArgumentException( "You must use an DFA!" );
        }
        
        FAState current = initialState;
        Map<FAState, Map<Character, List<FAState>>> delta = dfa.getDelta();
        
        for ( char c : str.toCharArray() ) {
            List<FAState> target = delta.get( current ).get( c );
            if ( target != null ) {
                current = target.get( 0 );
            } else {
                return false;
            }
        }
        
        return current.isAccepting();
        
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
     * Verifies if two have the same transition function to a next state
     * with a input of one symbol. It will consider if the states have 
     * different symbols to in their transitions.
     * 
     * @param s1 One FA state.
     * @param s2 Another FA state.
     * @param dfa The automaton containing the states that will be verified.
     * @return true if the states have the same transition function,
     * false otherwise.
     */
    public static boolean haveSameDeltaWithOneSymbol( 
            FAState s1, 
            FAState s2,
            FA dfa ) throws IllegalArgumentException {
        
        if ( dfa.getType() != FAType.DFA ) {
            throw new IllegalArgumentException( "You must use an DFA!" );
        }
        
        Map<FAState, Map<Character, List<FAState>>> delta = dfa.getDelta();
        Map<Character, Boolean> t1 = new HashMap<>();
        Map<Character, Boolean> t2 = new HashMap<>();
        
        // assumes DFA
        for ( Map.Entry<Character, List<FAState>> e : delta.get( s1 ).entrySet() ) {
            t1.put( e.getKey(), e.getValue().get( 0 ).isAccepting() );
        }
        for ( Map.Entry<Character, List<FAState>> e : delta.get( s2 ).entrySet() ) {
            t2.put( e.getKey(), e.getValue().get( 0 ).isAccepting() );
        }
        
        return t1.equals( t2 );
        
    }
    
    /**
     * Verifies if two states are distinguishable.
     * 
     * @param s1 One FA state.
     * @param s2 Another FA state.
     * @param dfa The automaton containing the states that will be verified.
     * @return true if the states are distinguishable, false otherwise.
     */
    public static boolean isDistinguishable( 
            FAState s1, 
            FAState s2,
            FA dfa ) throws IllegalArgumentException {
        
        if ( dfa.getType() != FAType.DFA ) {
            throw new IllegalArgumentException( "You must use an DFA!" );
        }
        
        Map<FAState, Map<Character, List<FAState>>> delta = dfa.getDelta();
        
        // assumes s1 and s2 have the same transition function with one symbol
        Set<Character> alphabet = delta.get( s1 ).keySet();
        
        // generate strings starting with length = 2
        SigmaStarGeneratorStream ssgs = new SigmaStarGeneratorStream( alphabet, 2 );
        
        // max length => n-2, n being the number os states of the DFA
        int maxLength = dfa.getStates().size() - 2;
        
        for ( int i = 2; i <= maxLength; i++ ) {
            String str = ssgs.next();
            if ( str.length() > i ) {
                break;
            }
            if ( acceptsDFA( str, dfa, s1 ) != acceptsDFA( str, dfa, s2 ) ) {
                return true;
            }
        }
            
        return false;
        
    }
    
    /**
     * Add all missing transitions to a DFA.
     * 
     * @param dfa The DFA to be processed.
     * @param currentState Whats the current state counter.
     */
    public static void addAllMissingTransitions( FA dfa, int currentState )
            throws IllegalArgumentException {
        addAllMissingTransitions( 
                dfa, 
                currentState, 
                true, 
                DrawingConstants.NULL_STATE_FILL_COLOR,
                DrawingConstants.NULL_STATE_STROKE_COLOR,
                DrawingConstants.NULL_TRANSITION_STROKE_COLOR );
    }
    
    /**
     * Add all missing transitions to a DFA.
     * 
     * @param dfa The DFA to be processed.
     * @param currentState Whats the current state counter.
     * @param useNullCustomLabel If it will use the empty set symbol do set the
     * custom label of the generated state (null state).
     * @param nullStateFillColor The color to set the fill in the null state.
     * @param nullStateStrokeColor The color to set the stroke in the null state.
     * @param nullTransitionStrokeColor The color to set in thes troke the null transition.
     */
    public static void addAllMissingTransitions( 
            FA dfa,
            int currentState, 
            boolean useNullCustomLabel,
            Color nullStateFillColor,
            Color nullStateStrokeColor,
            Color nullTransitionStrokeColor ) throws IllegalArgumentException {
        
        if ( dfa.getType() != FAType.DFA ) {
            throw new IllegalArgumentException( "You must use an DFA!" );
        }
        
        Map<FAState, Set<Character>> allMissing = new HashMap<>();
        Map<FAState, Map<Character, List<FAState>>> delta = dfa.getDelta();
        List<Character> alphabet = new ArrayList<>( dfa.getAlphabet() );
        
        for ( FAState s : dfa.getStates() ) {
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
            dfa.addState( nullState );
            
            FATransition nullTransition = new FATransition( 
                    nullState, nullState, alphabet );
            nullTransition.setStrokeColor( nullStateStrokeColor );
            dfa.addTransition( nullTransition );
            
            for ( Map.Entry<FAState, Set<Character>> e : allMissing.entrySet() ) {
                List<Character> s = new ArrayList<>( e.getValue() );
                FATransition t = new FATransition( e.getKey(), nullState, s );
                t.setStrokeColor( nullTransitionStrokeColor );
                dfa.addTransition( t );
            }
            
        }
        
    }
    
    /**
     * Complement a DFA.
     * 
     * @param dfa The dfa to be complemented.
     * @param currentState for state label generation
     */
    public static void complementDFA( FA dfa, int currentState )
            throws IllegalArgumentException {
        
        addAllMissingTransitions( 
                dfa, 
                currentState, 
                false, 
                DrawingConstants.STATE_FILL_COLOR,
                DrawingConstants.STATE_STROKE_COLOR,
                DrawingConstants.TRANSITION_STROKE_COLOR );
        
        for ( FAState s : dfa.getStates() ) {
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
