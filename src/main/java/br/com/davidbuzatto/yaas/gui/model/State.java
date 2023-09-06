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
    
    private Arrow arrow;
    
    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        g2d.setFont( Utils.DEFAULT_FONT );
        g2d.setStroke( Utils.STATE_STROKE );
        
        g2d.setColor( fillColor );
        g2d.fillOval( 
                x1 - Utils.STATE_RADIUS, 
                y1 - Utils.STATE_RADIUS, 
                Utils.STATE_DIAMETER, 
                Utils.STATE_DIAMETER );
        
        g2d.setColor( strokeColor );
        
        if ( initial ) {
            g2d.drawLine( 
                    x1 - Utils.STATE_DIAMETER, 
                    y1, 
                    x1 - Utils.STATE_RADIUS, 
                    y1 );
            if ( arrow == null ) {
                arrow = new Arrow();
            }
            arrow.setX1( x1 - Utils.STATE_RADIUS );
            arrow.setY1( y1 );
            arrow.draw( g2d );
        }
        
        g2d.drawOval(
                x1 - Utils.STATE_RADIUS, 
                y1 - Utils.STATE_RADIUS, 
                Utils.STATE_DIAMETER,
                Utils.STATE_DIAMETER );
        
        if ( accepting ) {
            g2d.drawOval(
                x1 - Utils.STATE_RADIUS + 5, 
                y1 - Utils.STATE_RADIUS + 5, 
                Utils.STATE_DIAMETER - 10,
                Utils.STATE_DIAMETER - 10 );
        }
        
        if ( label != null ) {
            g2d.drawString( 
                    label, 
                    x1 - g2d.getFontMetrics().stringWidth( label )/2, 
                    y1 + 5 );
        }
        
        g2d.dispose();
        
    }

    @Override
    public boolean intercepts( int x, int y ) {
        
        int dx = x1-x;
        int dy = y1-y;
        
        return dx*dx + dy*dy <= Utils.STATE_RADIUS_SQUARED;
        
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
    
}
