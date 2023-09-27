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
package br.com.davidbuzatto.yaas.gui.fa.algorithms;

import br.com.davidbuzatto.yaas.gui.fa.FA;
import br.com.davidbuzatto.yaas.gui.fa.FAState;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

/**
 * Finite Automata arrangement algorithms.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAArrangement {
    
    /**
     * Reorganize Finite Automata horizontally.
     * 
     * @param fa The Finite Automata to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param distance The horizontal distance between each state.
     */
    public static void arrangeHorizontally( 
            FA fa, int xCenter, int yCenter, int distance ) {
        arrangeRectangularly( fa, xCenter, yCenter, fa.getStates().size(), distance );
    }
    
    /**
     * Reorganize Finite Automata vertically.
     * 
     * @param fa The Finite Automata to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param distance The vertical distance between each state.
     */
    public static void arrangeVertically( 
            FA fa, int xCenter, int yCenter, int distance ) {
        arrangeRectangularly( fa, xCenter, yCenter, 1, distance );
    }
    
    /**
     * Reorganize Finite Automata diagonally.
     * 
     * @param fa The Finite Automata to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param distance The diagonal distance between each state.
     */
    public static void arrangeDiagonally( 
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
    public static void arrangeRectangularly( 
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
    public static void arrangeInCircle( 
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
    public static void arrangeByLevel( 
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
