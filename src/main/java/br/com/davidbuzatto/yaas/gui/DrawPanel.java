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
package br.com.davidbuzatto.yaas.gui;

import br.com.davidbuzatto.yaas.gui.model.Arrow;
import br.com.davidbuzatto.yaas.gui.fa.FA;
import br.com.davidbuzatto.yaas.util.DrawingConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import javax.swing.JPanel;

/**
 * Drawing panel for automaton construction and test.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DrawPanel extends JPanel {

    private FA fa;
    
    private boolean showGrid;
    private boolean drawingTempTransition;
    
    private Arrow tempTransitionArrow;
    private int tempTransitionX1;
    private int tempTransitionY1;
    private int tempTransitionX2;
    private int tempTransitionY2;
    
    public DrawPanel() {
        tempTransitionArrow = new Arrow();
        tempTransitionArrow.setStroke(DrawingConstants.TRANSITION_STROKE );
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
        g2d.drawRect( 0, 0, getWidth(), getHeight() );
        
        if ( showGrid ) {
            g2d.setColor(DrawingConstants.GRID_COLOR );
            for ( int i = 0; i <= getHeight(); i += DrawingConstants.STATE_RADIUS ) {
                g2d.drawLine( 0, i, getWidth(), i );
            }
            for ( int i = 0; i <= getWidth(); i += DrawingConstants.STATE_RADIUS ) {
                g2d.drawLine( i, 0, i, getHeight() );
            }
        }
        
        if ( fa != null ) {
            
            fa.draw( g2d );
        
            if ( drawingTempTransition ) {

                Stroke s = g2d.getStroke();
                g2d.setStroke(DrawingConstants.TEMP_TRANSITION_STROKE );
                g2d.setColor(DrawingConstants.TEMP_TRANSITION_COLOR );

                g2d.drawLine( 
                        tempTransitionX1, tempTransitionY1, 
                        tempTransitionX2, tempTransitionY2 );

                tempTransitionArrow.setX1( tempTransitionX2 );
                tempTransitionArrow.setY1( tempTransitionY2 );
                tempTransitionArrow.setStrokeColor(DrawingConstants.TEMP_TRANSITION_COLOR );
                tempTransitionArrow.setAngle( Math.atan2( 
                        tempTransitionY2 - tempTransitionY1, 
                        tempTransitionX2 - tempTransitionX1 ) );
                tempTransitionArrow.draw( g2d );

            }
            
        }
        
        g2d.dispose();
        
    }

    public void setFa( FA fa ) {
        this.fa = fa;
    }

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid( boolean showGrid ) {
        this.showGrid = showGrid;
    }

    public void setDrawingTempTransition( boolean drawingTempTransition ) {
        this.drawingTempTransition = drawingTempTransition;
    }

    public void setTempTransitionX1( int tempTransitionX1 ) {
        this.tempTransitionX1 = tempTransitionX1;
    }

    public void setTempTransitionY1( int tempTransitionY1 ) {
        this.tempTransitionY1 = tempTransitionY1;
    }

    public void setTempTransitionX2( int tempTransitionX2 ) {
        this.tempTransitionX2 = tempTransitionX2;
    }

    public void setTempTransitionY2( int tempTransitionY2 ) {
        this.tempTransitionY2 = tempTransitionY2;
    }
    
}
