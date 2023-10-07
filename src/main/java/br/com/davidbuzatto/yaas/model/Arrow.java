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
package br.com.davidbuzatto.yaas.model;

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
    
    private int length;
    private int halfLength;
    private int oneThirdLength;
    
    private boolean activeInSimulation;
    private Color activeInSimulationStrokeColor;
    
    public Arrow() {
        stroke = DrawingConstants.DEFAULT_ARROW_STROKE;
        setLength( DrawingConstants.ARROW_LENGTH );
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
        p.lineTo( x1 - length, y1 - halfLength );
        p.quadTo( x1 - oneThirdLength, y1, x1 - length, y1 + halfLength );
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

    public void setLength( int length ) {
        this.length = length;
        this.halfLength = length/2;
        this.oneThirdLength = length/3;
    }
    
    @Override
    @SuppressWarnings( "unchecked" )
    public Object clone() throws CloneNotSupportedException {
        
        Arrow c = (Arrow) super.clone();
        
        c.angle = angle;
        c.length = length;
        c.halfLength = halfLength;
        c.oneThirdLength = oneThirdLength;
        c.activeInSimulation = false;
        c.activeInSimulationStrokeColor = activeInSimulationStrokeColor;
        
        return c;
        
    }
    
}
