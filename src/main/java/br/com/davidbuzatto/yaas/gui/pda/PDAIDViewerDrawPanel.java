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
package br.com.davidbuzatto.yaas.gui.pda;

import br.com.davidbuzatto.yaas.model.AbstractGeometricForm;
import br.com.davidbuzatto.yaas.model.Arrow;
import br.com.davidbuzatto.yaas.model.pda.PDA;
import br.com.davidbuzatto.yaas.model.pda.PDAID;
import br.com.davidbuzatto.yaas.util.DrawingConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JPanel;

/**
 * Drawing panel for IDs of PDAs view.
 * 
 * TODO highligh acceptance path!
 * TODO recalculate panel size based on contents.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAIDViewerDrawPanel extends JPanel {

    private PDA pda;
    private PDAID root;
    private List<PDAID> ids;
    private List<Line> lines;
    
    private class Line extends AbstractGeometricForm {
        
        Arrow arrow;
        
        Line( int x1, int y1, int x2, int y2, Color strokeColor ) {
            
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.strokeColor = strokeColor;
            
            arrow = new Arrow();
            arrow.setX1( x2 );
            arrow.setY1( y2 );
            arrow.setStrokeColor( strokeColor );
            arrow.setAngle( Math.atan2( y2 - y1, x2 - x1 ) );
            
        }
        
        @Override
        public void draw( Graphics2D g2d ) {
            g2d = (Graphics2D) g2d.create();
            g2d.setColor( strokeColor );
            g2d.drawLine( x1, y1, x2, y2 );
            arrow.draw( g2d );
            g2d.dispose();
        }

        @Override
        public boolean intersects( int x, int y ) {
            return false;
        }
        
    }
    
    @Override
    protected void paintComponent( Graphics g ) {
        
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON );
        
        g2d.setColor( Color.WHITE );
        g2d.fillRect( 0, 0, getWidth(), getHeight() );
        
        g2d.setColor( Color.BLACK );
        if ( lines != null ) {
            for ( Line line : lines ) {
                line.draw( g2d );
            }
        }
        
        if ( ids != null ) {
            for ( PDAID id : ids ) {
                id.draw( g2d );
            }
        }
        
        g2d.setStroke( DrawingConstants.DRAW_PANEL_STROKE.getBasicStroke() );
        g2d.setColor( Color.BLACK );
        g2d.drawRect( 0, 0, getWidth(), getHeight() );
        
        g2d.dispose();
        
    }

    public void setPda( PDA pda ) {
        this.pda = pda;
    }
    
    public void organizeAndCollect() {
        
        // TODO improve starting coordinate and distance
        int xCenter = 100;
        int yCenter = 50;
        int distanceX = 200;
        int distanceY = 70;
        int currentX;
        int currentY;
                
        root = pda.getRootId();
        ids = pda.getIds();
        lines = new ArrayList<>();
        
        Map<Integer, List<PDAID>> leveledIds = new TreeMap<>();
        List<PDAID> liRoot = new ArrayList<>();
        liRoot.add( root );
        leveledIds.put( 0, liRoot );
        
        Map<PDAID, Integer> distanceTo = new HashMap<>();
        for ( PDAID id : ids ) {
            distanceTo.put( id, Integer.MAX_VALUE );
        }
        distanceTo.put( root, 0 );
        
        Set<PDAID> visited = new HashSet<>();
        visited.add( root );
        
        Deque<PDAID> queue = new ArrayDeque<>();
        queue.add( root );
        int maxDistance = 0;
        
        while ( !queue.isEmpty() ) {
            
            PDAID id = queue.pop();
            
            for ( PDAID cId : id.getChildren() ) {
                if ( !visited.contains( cId ) ) {
                    
                    int d = distanceTo.get( id ) + 1;
                    if ( maxDistance < d ) {
                        maxDistance = d;
                    }
                    
                    distanceTo.put( cId, d );
                    visited.add( cId );
                    queue.add( cId );
                    
                    List<PDAID> lIds = leveledIds.get( d );
                    if ( lIds == null ) {
                        lIds = new ArrayList<>();
                        leveledIds.put( d, lIds );
                    }
                    lIds.add( cId );
                    
                }
            }
            
        }
        
        // improve positioning algorithm (use tree with rank!)
        for ( Map.Entry<Integer, List<PDAID>> e : leveledIds.entrySet() ) {

            currentX = xCenter;
            currentY = yCenter + e.getKey() * distanceY;

            Collections.sort( e.getValue() );

            for ( PDAID id : e.getValue() ) {
                id.setX1( currentX );
                id.setY1( currentY );
                currentX += distanceX;
            }

        }
        
        for ( PDAID id : ids ) {
            for ( PDAID cId : id.getChildren() ) {
                lines.add( new Line( 
                        id.getX1(), 
                        id.getY1() + 20, 
                        cId.getX1(),
                        cId.getY1() - 20, 
                        cId.getStrokeColor() ) );
            }
        }
        
    }
    
}
