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

import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.Graphics2D;

/**
 * A generic automaton state.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class State extends AbstractForm {
    
    protected String label;
    protected String customLabel;
    
    protected boolean initial;
    protected boolean accepting;
    
    protected int radius;
    protected int radiusSquared;
    protected int diameter;
    
    private Arrow arrow;
    
    public State() {
        
        font = Utils.DEFAULT_FONT;
        stroke = Utils.STATE_STROKE;
        
        mouseHoverFillColor = Utils.STATE_MOUSE_HOVER_FILL_COLOR;
        mouseHoverStrokeColor = Utils.STATE_MOUSE_HOVER_STROKE_COLOR;
        
        radius = Utils.STATE_RADIUS;
        radiusSquared = Utils.STATE_RADIUS_SQUARED;
        diameter = Utils.STATE_DIAMETER;
        
        arrow = new Arrow();
        arrow.setMouseHoverStrokeColor( Utils.STATE_MOUSE_HOVER_STROKE_COLOR );
        
    }
    
    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        g2d.setFont( font );
        g2d.setStroke( stroke );
        arrow.setMouseHover( mouseHover );
        
        if ( mouseHover ) {
            g2d.setColor( mouseHoverStrokeColor );
        } else {
            g2d.setColor( strokeColor );
        }
        
        if ( initial ) {
            g2d.drawLine( x1 - diameter, y1, x1 - radius, y1 );
            arrow.setX1( x1 - radius );
            arrow.setY1( y1 );
            arrow.draw( g2d );
        }
        
        if ( mouseHover ) {
            g2d.setColor( mouseHoverFillColor );
        } else {
            g2d.setColor( fillColor );
        }
        
        g2d.fillOval( x1 - radius, y1 - radius, diameter, diameter );
        
        if ( mouseHover ) {
            g2d.setColor( mouseHoverStrokeColor );
        } else {
            g2d.setColor( strokeColor );
        }
        g2d.drawOval( x1 - radius, y1 - radius, diameter, diameter );
        
        if ( accepting ) {
            g2d.drawOval(
                x1 - radius + 5, 
                y1 - radius + 5, 
                diameter - 10,
                diameter - 10 );
        }
        
        if ( customLabel != null ) {
            g2d.drawString( 
                    customLabel, 
                    x1 - g2d.getFontMetrics().stringWidth( customLabel )/2, 
                    y1 + 5 );
        } else if ( label != null ) {
            g2d.drawString( 
                    label, 
                    x1 - g2d.getFontMetrics().stringWidth( label )/2, 
                    y1 + 5 );
        }
        
        g2d.dispose();
        
    }
    
    public void mouseHover( int x, int y ) {
        if ( intercepts( x, y ) ) {
            mouseHover = true;
            return;
        }
        mouseHover = false;
    }
    
    @Override
    public boolean intercepts( int x, int y ) {
        x = x1 - x;
        y = y1 - y;
        return x*x + y*y <= radiusSquared;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel( String label ) {
        this.label = label;
    }

    public String getCustomLabel() {
        return customLabel;
    }

    public void setCustomLabel( String customLabel ) {
        this.customLabel = customLabel;
    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial( boolean initial ) {
        this.initial = initial;
    }

    public boolean isAccepting() {
        return accepting;
    }

    public void setAccepting( boolean accepting ) {
        this.accepting = accepting;
    }

    public int getRadius() {
        return radius;
    }

    public int getDiameter() {
        return diameter;
    }
    
    public void setRadius( int radius ) {
        this.radius = radius;
        this.radiusSquared = radius * radius;
        this.diameter = radius * 2;
    }
    
}
