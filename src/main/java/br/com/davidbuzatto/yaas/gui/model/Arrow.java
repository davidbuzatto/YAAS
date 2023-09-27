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
package br.com.davidbuzatto.yaas.gui.model;

import br.com.davidbuzatto.yaas.util.DrawingConstants;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

/**
 * A graphical representation of a arrow.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Arrow extends AbstractGeometricForm implements Cloneable {
    
    private double angle = 0;
    
    private int arrowLength;
    private int halfArrowLength;
    private int oneThirdArrowLength;
    
    private boolean activeInSimulation;
    private Color activeInSimulationStrokeColor;
    
    public Arrow() {
        stroke = DrawingConstants.DEFAULT_ARROW_STROKE;
        arrowLength = DrawingConstants.ARROW_LENGTH;
        halfArrowLength = arrowLength/2;
        oneThirdArrowLength = arrowLength/3;
    }
    
    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        g2d.rotate( angle, x1, y1 );
        g2d.setStroke( stroke.getBasicStroke() );
        
        if ( mouseHover ) {
            g2d.setColor( mouseHoverStrokeColor );
        } else if ( activeInSimulation ) {
            g2d.setColor( activeInSimulationStrokeColor );
        } else if ( selected ) {
            g2d.setColor( selectedStrokeColor );
        } else {
            g2d.setColor( strokeColor );
        }
        
        Path2D p = new Path2D.Double();
        p.moveTo( x1, y1 );
        p.lineTo( x1 - arrowLength, y1 - halfArrowLength );
        p.quadTo( x1 - oneThirdArrowLength, y1, x1 - arrowLength, y1 + halfArrowLength );
        p.closePath();
        g2d.fill( p );
        g2d.draw( p );
        
        g2d.dispose();
        
    }

    @Override
    public boolean intersects( int x, int y ) {
        return false;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle( double angle ) {
        this.angle = angle;
    }

    public boolean isActiveInSimulation() {
        return activeInSimulation;
    }

    public void setActiveInSimulation( boolean activeInSimulation ) {
        this.activeInSimulation = activeInSimulation;
    }

    public Color getActiveInSimulationStrokeColor() {
        return activeInSimulationStrokeColor;
    }

    public void setActiveInSimulationStrokeColor( Color activeInSimulationStrokeColor ) {
        this.activeInSimulationStrokeColor = activeInSimulationStrokeColor;
    }
    
    @Override
    @SuppressWarnings( "unchecked" )
    public Object clone() throws CloneNotSupportedException {
        
        Arrow c = (Arrow) super.clone();
        
        c.angle = angle;
        c.arrowLength = arrowLength;
        c.halfArrowLength = halfArrowLength;
        c.oneThirdArrowLength = oneThirdArrowLength;
        c.activeInSimulation = false;
        c.activeInSimulationStrokeColor = activeInSimulationStrokeColor;
        
        return c;
        
    }
    
}
