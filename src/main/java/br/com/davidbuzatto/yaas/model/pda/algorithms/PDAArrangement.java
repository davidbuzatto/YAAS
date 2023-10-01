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
package br.com.davidbuzatto.yaas.model.pda.algorithms;

import br.com.davidbuzatto.yaas.model.pda.PDA;
import br.com.davidbuzatto.yaas.model.pda.PDAState;
import java.awt.Point;
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
 * Pushdown Automata arrangement algorithms.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAArrangement {
    
    /**
     * Reorganize Pushdown Automata horizontally.
     * 
     * @param pda The Pushdown Automata to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param distance The horizontal distance between each state.
     */
    public static void arrangeHorizontally( 
            PDA pda, int xCenter, int yCenter, int distance ) {
        arrangeRectangularly( pda, xCenter, yCenter, pda.getStates().size(), distance );
    }
    
    /**
     * Reorganize Pushdown Automata vertically.
     * 
     * @param pda The Pushdown Automata to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param distance The vertical distance between each state.
     */
    public static void arrangeVertically( 
            PDA pda, int xCenter, int yCenter, int distance ) {
        arrangeRectangularly( pda, xCenter, yCenter, 1, distance );
    }
    
    /**
     * Reorganize Pushdown Automata diagonally.
     * 
     * @param pda The Pushdown Automata to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param distance The diagonal distance between each state.
     */
    public static void arrangeDiagonally( 
            PDA pda, int xCenter, int yCenter, int distance ) {
        
        PDAState initialState = pda.getInitialState();
        List<PDAState> states = new ArrayList<>();
        states.addAll( pda.getStates() );
        
        if ( initialState != null ) {
            states.remove( initialState );
            states.add( 0, initialState );
        }
        
        int distanceX = (int) ( distance * Math.cos( Math.PI/4 ) );
        int distanceY = (int) ( distance * Math.sin( Math.PI/4 ) );
        int currentX = xCenter;
        int currentY = yCenter;
        
        for ( PDAState s : states ) {
            s.setX1( currentX );
            s.setY1( currentY );
            currentX += distanceX;
            currentY += distanceY;
        }
        
        pda.resetTransitionsTransformations();
        
    }
    
    /**
     * Reorganize Pushdown Automata rectangularly.
     * 
     * @param pda The Pushdown Automata to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param cols How many columns.
     * @param distance The horizontal and vertical distance between each state.
     */
    public static void arrangeRectangularly( 
            PDA pda, int xCenter, int yCenter, int cols, int distance ) {
        
        PDAState initialState = pda.getInitialState();
        List<PDAState> states = new ArrayList<>();
        states.addAll( pda.getStates() );
        
        if ( initialState != null ) {
            states.remove( initialState );
            states.add( 0, initialState );
        }
        
        int currentX = xCenter;
        int currentY = yCenter;
        int currentColumn = 1;
        
        for ( PDAState s : states ) {
            
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
        
        pda.resetTransitionsTransformations();
        
    }
    
    /**
     * Reorganize Pushdown Automata states in a circle.
     * 
     * @param pda The Pushdown Automata to be processed.
     * @param xCenter The x center of the circle.
     * @param yCenter The y center of the circle.
     * @param radius The distance of each state to the center.
     */
    public static void arrangeInCircle( 
            PDA pda, int xCenter, int yCenter, int radius ) {
        
        PDAState initialState = pda.getInitialState();
        List<PDAState> states = new ArrayList<>();
        states.addAll( pda.getStates() );
        
        if ( initialState != null ) {
            states.remove( initialState );
            states.add( 0, initialState );
        }
        
        double inc = Math.PI * 2 / pda.getStates().size();
        double current = Math.PI;
        
        for ( PDAState s : states ) {
            s.setX1( xCenter + (int) ( radius * Math.cos( current ) ) );
            s.setY1( yCenter + (int) ( radius * Math.sin( current ) ) );
            current += inc;
        }
        
        pda.resetTransitionsTransformations();
        
    }
    
    /**
     * Reorganize Pushdown Automata by levels starting from initial state.
     * 
     * @param pda The Pushdown Automata to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param distance The distance between levels and between states horizontally.
     */
    public static void arrangeByLevel( 
            PDA pda, int xCenter, int yCenter, int distance, boolean vertical ) {
        
        int currentX;
        int currentY;
        
        PDAState initialState = pda.getInitialState();
        Map<PDAState, Map<Character, List<PDAState>>> delta = pda.getDelta();
        
        Map<Integer, List<PDAState>> leveledStates = new TreeMap<>();
        List<PDAState> liInitial = new ArrayList<>();
        liInitial.add( initialState );
        leveledStates.put( 0, liInitial );
        
        Map<PDAState, Integer> distanceTo = new HashMap<>();
        for ( PDAState e : pda.getStates() ) {
            distanceTo.put( e, Integer.MAX_VALUE );
        }
        distanceTo.put( initialState, 0 );
        
        Set<PDAState> visited = new HashSet<>();
        visited.add( initialState );
        
        Queue<PDAState> queue = new ArrayDeque<>();
        queue.add( initialState );
        int maxDistance = 0;
        
        while ( !queue.isEmpty() ) {
            
            PDAState current = queue.poll();
            
            Set<PDAState> children = new HashSet<>();
            for ( Map.Entry<Character, List<PDAState>> e : delta.get( current ).entrySet() ) {
                children.addAll( e.getValue() );
            }
            List<PDAState> lChildren = new ArrayList<>( children );
            Collections.sort( lChildren );
            
            for ( PDAState e : lChildren ) {
                if ( !visited.contains( e ) ) {
                    
                    int d = distanceTo.get( current ) + 1;
                    if ( maxDistance < d ) {
                        maxDistance = d;
                    }
                    
                    distanceTo.put( e, d );
                    visited.add( e );
                    queue.add( e );
                    
                    List<PDAState> es = leveledStates.get( d );
                    if ( es == null ) {
                        es = new ArrayList<>();
                        leveledStates.put( d, es );
                    }
                    es.add( e );
                    
                }
            }
            
        }
        
        List<PDAState> inaccessible = new ArrayList<>();
        for ( PDAState e : pda.getStates() ) {
            if ( !visited.contains( e ) ) {
                inaccessible.add( e );
            }
        }
        if ( !inaccessible.isEmpty() ) {
            leveledStates.put( maxDistance + 1, inaccessible );
        }
        
        if ( vertical ) {
            
            for ( Map.Entry<Integer, List<PDAState>> e : leveledStates.entrySet() ) {

                currentX = xCenter;
                currentY = yCenter + e.getKey() * distance;

                Collections.sort( e.getValue() );
                
                for ( PDAState s : e.getValue() ) {
                    s.setX1( currentX );
                    s.setY1( currentY );
                    currentX += distance;
                }

            }
            
        } else {
            
            for ( Map.Entry<Integer, List<PDAState>> e : leveledStates.entrySet() ) {

                currentX = xCenter + e.getKey() * distance;
                currentY = yCenter;
                
                Collections.sort( e.getValue() );

                for ( PDAState s : e.getValue() ) {
                    s.setX1( currentX );
                    s.setY1( currentY );
                    currentY += distance;
                }

            }
            
        }
        
        pda.resetTransitionsTransformations();
        
    }
    
    /**
     * Get the boundaries of a Pushdown Automaton.
     * 
     * @param fa The Pushdown Automaton to proccess.
     * @return An array containing two Points. The first contains the cordinates
     * of the superior left vertex of the boundary rectangle and the second
     * contains the cordinates of the inferior right vertex of the boundary
     * rectangle.
     */
    public static Point[] getPDABoundaries( PDA fa ) {
        
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for ( PDAState s : fa.getStates() ) {
            if ( minX > s.getX1() ) {
                minX = s.getX1();
            }
            if ( minY > s.getY1() ) {
                minY = s.getY1();
            }
            if ( maxX < s.getX1() ) {
                maxX = s.getX1();
            }
            if ( maxY < s.getY1() ) {
                maxY = s.getY1();
            }
        }
        
        return new Point[]{ new Point( minX, minY ), new Point( maxX, maxY ) };
        
    }
    
}
