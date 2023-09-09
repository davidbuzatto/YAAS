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

import java.awt.Graphics2D;

/**
 * A graphical representation of a control point.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ControlPoint extends AbstractGeometricForm {
    
    private int radius;
    private int radiusSquared;
    private int diameter;
    
    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        g2d.setStroke( stroke );
        g2d.setColor( fillColor );
        g2d.fillOval( x1 - radius, y1 - radius, diameter, diameter );
        
        if ( mouseHover ) {
            g2d.setColor( mouseHoverStrokeColor );
            g2d.drawOval( x1 - radius, y1 - radius, diameter, diameter );
        }
        
        g2d.dispose();
        
    }

    @Override
    public boolean intercepts( int x, int y ) {
        x = x1 - x;
        y = y1 - y;        
        return x*x + y*y <= radiusSquared;
    }

    public void setRadius( int radius ) {
        this.radius = radius;
        this.radiusSquared = radius * radius;
        this.diameter = radius * 2;
    }
    
}
