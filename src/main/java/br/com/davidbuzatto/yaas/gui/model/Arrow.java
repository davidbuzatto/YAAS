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
package br.com.davidbuzatto.yaas.gui.model;

import br.com.davidbuzatto.yaas.util.DrawingConstants;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

/**
 * A graphical representation of a arrow.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Arrow extends AbstractGeometricForm {
    
    private double angle = 0;
    
    public Arrow() {
        stroke = DrawingConstants.DEFAULT_ARROW_STROKE;
    }
    
    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        g2d.rotate( angle, x1, y1 );
        g2d.setStroke( stroke );
        
        if ( mouseHover ) {
            g2d.setColor( mouseHoverStrokeColor );
        } else if ( selected ) {
            g2d.setColor( selectedStrokeColor );
        } else {
            g2d.setColor( strokeColor );
        }
        
        Path2D p = new Path2D.Double();
        p.moveTo( x1, y1 );
        p.lineTo( x1-15, y1-7 );
        p.quadTo( x1-5, y1, x1-15, y1+7 );
        p.closePath();
        g2d.fill( p );
        g2d.draw( p );
        
        g2d.dispose();
        
    }

    @Override
    public boolean intercepts( int x, int y ) {
        return false;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle( double angle ) {
        this.angle = angle;
    }
    
}
