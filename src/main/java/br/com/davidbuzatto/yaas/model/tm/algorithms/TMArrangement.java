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
package br.com.davidbuzatto.yaas.model.tm.algorithms;

import br.com.davidbuzatto.yaas.gui.tm.TMIDNodeExtentProvider;
import br.com.davidbuzatto.yaas.model.tm.TM;
import br.com.davidbuzatto.yaas.model.tm.TMID;
import br.com.davidbuzatto.yaas.model.tm.TMIDLine;
import br.com.davidbuzatto.yaas.model.tm.TMState;
import br.com.davidbuzatto.yaas.model.tm.TMTransition;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
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
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

/**
 * Turing Machine arrangement algorithms.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TMArrangement {
    
    /**
     * Reorganize Turing Machines horizontally.
     * 
     * @param tm The Turing Machine to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param distance The horizontal distance between each state.
     */
    public static void arrangeHorizontally( 
            TM tm, int xCenter, int yCenter, int distance ) {
        arrangeRectangularly( tm, xCenter, yCenter, tm.getStates().size(), distance );
    }
    
    /**
     * Reorganize Turing Machines vertically.
     * 
     * @param tm The Turing Machine to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param distance The vertical distance between each state.
     */
    public static void arrangeVertically( 
            TM tm, int xCenter, int yCenter, int distance ) {
        arrangeRectangularly( tm, xCenter, yCenter, 1, distance );
    }
    
    /**
     * Reorganize Turing Machines diagonally.
     * 
     * @param tm The Turing Machine to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param distance The diagonal distance between each state.
     */
    public static void arrangeDiagonally( 
            TM tm, int xCenter, int yCenter, int distance ) {
        
        TMState initialState = tm.getInitialState();
        List<TMState> states = new ArrayList<>();
        states.addAll( tm.getStates() );
        
        if ( initialState != null ) {
            states.remove( initialState );
            states.add( 0, initialState );
        }
        
        int distanceX = (int) ( distance * Math.cos( Math.PI/4 ) );
        int distanceY = (int) ( distance * Math.sin( Math.PI/4 ) );
        int currentX = xCenter;
        int currentY = yCenter;
        
        for ( TMState s : states ) {
            s.setX1( currentX );
            s.setY1( currentY );
            currentX += distanceX;
            currentY += distanceY;
        }
        
        tm.resetTransitionsTransformations();
        
    }
    
    /**
     * Reorganize Turing Machines rectangularly.
     * 
     * @param tm The Turing Machine to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param cols How many columns.
     * @param distance The horizontal and vertical distance between each state.
     */
    public static void arrangeRectangularly( 
            TM tm, int xCenter, int yCenter, int cols, int distance ) {
        
        TMState initialState = tm.getInitialState();
        List<TMState> states = new ArrayList<>();
        states.addAll( tm.getStates() );
        
        if ( initialState != null ) {
            states.remove( initialState );
            states.add( 0, initialState );
        }
        
        int currentX = xCenter;
        int currentY = yCenter;
        int currentColumn = 1;
        
        for ( TMState s : states ) {
            
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
        
        tm.resetTransitionsTransformations();
        
    }
    
    /**
     * Reorganize Turing Machines states in a circle.
     * 
     * @param tm The Turing Machine to be processed.
     * @param xCenter The x center of the circle.
     * @param yCenter The y center of the circle.
     * @param radius The distance of each state to the center.
     */
    public static void arrangeInCircle( 
            TM tm, int xCenter, int yCenter, int radius ) {
        
        TMState initialState = tm.getInitialState();
        List<TMState> states = new ArrayList<>();
        states.addAll( tm.getStates() );
        
        if ( initialState != null ) {
            states.remove( initialState );
            states.add( 0, initialState );
        }
        
        double inc = Math.PI * 2 / tm.getStates().size();
        double current = Math.PI;
        
        for ( TMState s : states ) {
            s.setX1( xCenter + (int) ( radius * Math.cos( current ) ) );
            s.setY1( yCenter + (int) ( radius * Math.sin( current ) ) );
            current += inc;
        }
        
        tm.resetTransitionsTransformations();
        
    }
    
    /**
     * Reorganize Turing Machines by levels starting from initial state.
     * 
     * @param tm The Turing Machine to be processed.
     * @param xCenter The x of the first state.
     * @param yCenter The y of the first state.
     * @param distance The distance between levels and between states horizontally.
     */
    public static void arrangeByLevel( 
            TM tm, int xCenter, int yCenter, int distance, boolean vertical ) {
        
        int currentX;
        int currentY;
        
        TMState initialState = tm.getInitialState();
        Map<TMState, List<TMTransition>> delta = tm.getDelta();
        
        Map<Integer, List<TMState>> leveledStates = new TreeMap<>();
        List<TMState> liInitial = new ArrayList<>();
        liInitial.add( initialState );
        leveledStates.put( 0, liInitial );
        
        Map<TMState, Integer> distanceTo = new HashMap<>();
        for ( TMState e : tm.getStates() ) {
            distanceTo.put( e, Integer.MAX_VALUE );
        }
        distanceTo.put( initialState, 0 );
        
        Set<TMState> visited = new HashSet<>();
        visited.add( initialState );
        
        Queue<TMState> queue = new ArrayDeque<>();
        queue.add( initialState );
        int maxDistance = 0;
        
        while ( !queue.isEmpty() ) {
            
            TMState current = queue.poll();
            
            Set<TMState> children = new HashSet<>();
            for ( TMTransition t : delta.get( current ) ) {
                children.add( t.getTargetState() );
            }
            List<TMState> lChildren = new ArrayList<>( children );
            Collections.sort( lChildren );
            
            for ( TMState e : lChildren ) {
                if ( !visited.contains( e ) ) {
                    
                    int d = distanceTo.get( current ) + 1;
                    if ( maxDistance < d ) {
                        maxDistance = d;
                    }
                    
                    distanceTo.put( e, d );
                    visited.add( e );
                    queue.add( e );
                    
                    List<TMState> es = leveledStates.get( d );
                    if ( es == null ) {
                        es = new ArrayList<>();
                        leveledStates.put( d, es );
                    }
                    es.add( e );
                    
                }
            }
            
        }
        
        List<TMState> inaccessible = new ArrayList<>();
        for ( TMState e : tm.getStates() ) {
            if ( !visited.contains( e ) ) {
                inaccessible.add( e );
            }
        }
        if ( !inaccessible.isEmpty() ) {
            leveledStates.put( maxDistance + 1, inaccessible );
        }
        
        if ( vertical ) {
            
            for ( Map.Entry<Integer, List<TMState>> e : leveledStates.entrySet() ) {

                currentX = xCenter;
                currentY = yCenter + e.getKey() * distance;

                Collections.sort( e.getValue() );
                
                for ( TMState s : e.getValue() ) {
                    s.setX1( currentX );
                    s.setY1( currentY );
                    currentX += distance;
                }

            }
            
        } else {
            
            for ( Map.Entry<Integer, List<TMState>> e : leveledStates.entrySet() ) {

                currentX = xCenter + e.getKey() * distance;
                currentY = yCenter;
                
                Collections.sort( e.getValue() );

                for ( TMState s : e.getValue() ) {
                    s.setX1( currentX );
                    s.setY1( currentY );
                    currentY += distance;
                }

            }
            
        }
        
        tm.resetTransitionsTransformations();
        
    }
    
    /**
     * Get the boundaries of a Turing Machine.
     * 
     * @param fa The Turing Machine to proccess.
     * @return An array containing two Points. The first contains the cordinates
     * of the superior left vertex of the boundary rectangle and the second
     * contains the cordinates of the inferior right vertex of the boundary
     * rectangle.
     */
    public static Point[] getTMBoundaries( TM fa ) {
        
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for ( TMState s : fa.getStates() ) {
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
    
    /**
     * Process a TMID tree and organize it.
     * 
     * @param root The root node
     * @param ids All the ids
     * @param lines The lines that will connect the ids
     * @param marginX Margin x
     * @param marginY Margin y
     * @param levelGap Level gap
     * @param nodeGap Node gap
     * @return The dimension of the tree.
     */
    public static Dimension arrangeIDsInTreeFormat( 
            TMID root, 
            List<TMID> ids,
            List<TMIDLine> lines, 
            int marginX, int marginY,
            int levelGap, int nodeGap ) {
        
        DefaultTreeForTreeLayout<TMID> tree = new DefaultTreeForTreeLayout<>(root);
        for ( TMID id : ids ) {
            for ( TMID cId : id.getChildren() ) {
                tree.addChild( id, cId );
            }
        }
        
        DefaultConfiguration<TMID> configuration = new DefaultConfiguration<>( levelGap, nodeGap );
        TMIDNodeExtentProvider nodeExtentProvider = new TMIDNodeExtentProvider();
        TreeLayout<TMID> treeLayout = new TreeLayout<>( tree, nodeExtentProvider, configuration );
        
        for ( TMID id : ids ) {
            Rectangle2D.Double box = treeLayout.getNodeBounds().get( id );
            id.setX1( (int) ( box.x + box.width/2 ) + marginX );
            id.setY1( (int) ( box.y + box.height/2 ) + marginY );
        }
        
        for ( TMID id : ids ) {
            for ( TMID cId : id.getChildren() ) {
                lines.add( new TMIDLine( 
                        id,
                        cId,
                        id.getX1(), 
                        id.getY1() + 20, 
                        cId.getX1(),
                        cId.getY1() - 20, 
                        cId.getOperation(),
                        cId.getStrokeColor() ) );
            }
            if ( id.isAcceptedByFinalState() || id.isAcceptedByHalt() ) {
                TMID current = id.getParent();
                while ( current != null ) {
                    current.setAcceptedByFinalState( id.isAcceptedByFinalState() );
                    current.setAcceptedByHalt( id.isAcceptedByHalt() );
                    current = current.getParent();
                }
            }
        }
        
        return treeLayout.getBounds().getBounds().getSize();
        
    }
    
    /**
     * Process a TMID tree and organize it.
     * 
     * @param root The root node
     * @param ids All the ids
     * @param lines The lines that will connect the ids
     * @param marginX Margin x
     * @param marginY Margin y
     * @param levelGap Level gap
     * @param nodeGap Node gap
     * @return The dimension of the tree.
     */
    public static Dimension arrangeIDsInTreeFormatForSimulation( 
            TMID root, 
            List<TMID> ids,
            List<TMIDLine> lines, 
            int marginX, int marginY,
            int levelGap, int nodeGap ) {
        
        DefaultTreeForTreeLayout<TMID> tree = new DefaultTreeForTreeLayout<>(root);
        for ( int i = 0; i < ids.size() - 1; i++ ) {
            tree.addChild( ids.get( i ), ids.get( i+1 ) );
        }
        
        boolean acceptedByFinalState = ids.get( ids.size()-1 ).isAcceptedByFinalState();
        boolean acceptedByEmptyStack = ids.get( ids.size()-1 ).isAcceptedByHalt();
        
        DefaultConfiguration<TMID> configuration = new DefaultConfiguration<>( levelGap, nodeGap );
        TMIDNodeExtentProvider nodeExtentProvider = new TMIDNodeExtentProvider();
        TreeLayout<TMID> treeLayout = new TreeLayout<>( tree, nodeExtentProvider, configuration );
        
        for ( TMID id : ids ) {
            Rectangle2D.Double box = treeLayout.getNodeBounds().get( id );
            id.setX1( (int) ( box.x + box.width/2 ) + marginX );
            id.setY1( (int) ( box.y + box.height/2 ) + marginY );
        }
        
        for ( int i = 0; i < ids.size() - 1; i++ ) {
            TMID id = ids.get( i );
            TMID cId = ids.get( i+1 );
            lines.add( new TMIDLine( 
                    id,
                    cId,
                    id.getX1(), 
                    id.getY1() + 20, 
                    cId.getX1(),
                    cId.getY1() - 20, 
                    cId.getOperation(),
                    cId.getStrokeColor() ) );
            id.setAcceptedByFinalState( acceptedByFinalState );
            id.setAcceptedByHalt( acceptedByEmptyStack );
        }
        
        return treeLayout.getBounds().getBounds().getSize();
        
    }
    
}
