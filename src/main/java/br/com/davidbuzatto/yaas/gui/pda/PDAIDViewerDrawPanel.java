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

import br.com.davidbuzatto.yaas.model.pda.PDA;
import br.com.davidbuzatto.yaas.model.pda.PDAID;
import br.com.davidbuzatto.yaas.model.pda.PDAIDLine;
import br.com.davidbuzatto.yaas.model.pda.algorithms.PDAArrangement;
import br.com.davidbuzatto.yaas.util.DrawingConstants;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Drawing panel for IDs of PDAs view.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAIDViewerDrawPanel extends JPanel {

    private PDA pda;
    private PDAID root;
    private List<PDAID> ids;
    private List<PDAIDLine> lines;
    
    private int xPrev;
    private int yPrev;
    
    private int xAmount;
    private int yAmount;
    private int totalXAmount;
    private int totalYAmount;
    private PDAIDLine mouseOverLine;
    
    private Dimension size;
    
    public PDAIDViewerDrawPanel() {
        
        setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ));
        
        addMouseListener( new MouseAdapter() {
            
            @Override
            public void mousePressed( MouseEvent evt ) {
                xPrev = evt.getX();
                yPrev = evt.getY();
            }

            @Override
            public void mouseReleased( MouseEvent e ) {
                
                totalXAmount += xAmount;
                totalYAmount += yAmount;
                
                Dimension s = getPreferredSize();
                size = new Dimension( s.width + totalXAmount, s.height + totalYAmount );
                setPreferredSize( size );
                repaint();
                revalidate();
                
            }
            
        });
        
        addMouseMotionListener( new MouseAdapter() {
            
            @Override
            public void mouseMoved( MouseEvent evt ) {
                
                mouseOverLine = null;
                
                for ( PDAIDLine line : lines ) {
                    if ( line.intersects( evt.getX(), evt.getY() ) ) {
                        line.setMouseHover( true );
                        line.setMouseXY( evt.getX(), evt.getY() );
                        mouseOverLine = line;
                    } else {
                        line.setMouseHover( false );
                    }
                }
                
                repaint();
                
            }
            
            @Override
            public void mouseDragged( MouseEvent evt ) {
                
                xAmount = evt.getX() - xPrev;
                yAmount = evt.getY() - yPrev;
                
                xPrev += xAmount;
                yPrev += yAmount;
                
                for ( PDAIDLine line : lines ) {
                    line.move( xAmount, yAmount );
                }
                for ( PDAID id : ids ) {
                    id.move( xAmount, yAmount );
                }
                
                Dimension s = getPreferredSize();
                setPreferredSize( new Dimension( s.width + xAmount, s.height + yAmount ) );
                repaint();
                revalidate();
                
            }
            
        });
        
    }
    
    @Override
    protected void paintComponent( Graphics g ) {
        
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON );
        
        g2d.setColor( Color.WHITE );
        g2d.fillRect( 0, 0, getWidth(), getHeight() );
        
        if ( ids != null ) {
            for ( PDAID id : ids ) {
                id.draw( g2d );
            }
        }
        
        g2d.setColor( Color.BLACK );
        if ( lines != null ) {
            for ( PDAIDLine line : lines ) {
                if ( line != mouseOverLine ) {
                    line.draw( g2d );
                }
            }
        }
        
        if ( mouseOverLine != null ) {
            mouseOverLine.draw( g2d );
        }
        
        g2d.setStroke( DrawingConstants.DRAW_PANEL_STROKE.getBasicStroke() );
        g2d.setColor( Color.BLACK );
        g2d.drawRect( 0, 0, getWidth(), getHeight() );
        
        g2d.dispose();
        
    }

    public void setPda( PDA pda ) {
        this.pda = pda;
    }
    
    public void arrangeAndProccessIds() {
        
        root = pda.getRootId();
        ids = pda.getIds();
        lines = new ArrayList<>();
        
        size = PDAArrangement.arrangeIDsInTreeFormat( 
                root, ids, lines, 30, 40, 60, 20 );
        setPreferredSize( new Dimension( size.width + 60, size.height + 70 ) );
        
    }
    
    public void arrangeAndProccessIdsForSimulation( List<PDASimulationStep> simulationSteps ) {
        
        ids = new ArrayList<>();
        for ( PDASimulationStep step : simulationSteps ) {
            ids.add( step.getId() );
        }
        
        root = ids.get( 0 );
        lines = new ArrayList<>();
        
        size = PDAArrangement.arrangeIDsInTreeFormatForSimulation( 
                root, ids, lines, 30, 40, 60, 20 );
        setPreferredSize( new Dimension( size.width + 60, size.height + 70 ) );
        
    }
    
}
