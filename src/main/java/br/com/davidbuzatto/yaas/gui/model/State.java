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
    
    protected int radius;
    
    protected String label;
    protected String customLabel;
    
    protected boolean isInitial;
    protected boolean isFinal;
    
    public State() {
        radius = 25;
    }
    
    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        g2d.setFont( Utils.DEFAULT_FONT );
        g2d.setStroke( Utils.DEFAULT_STROKE );
        
        g2d.setColor( fillColor );
        g2d.fillOval( x1-radius, y1-radius, radius*2, radius*2 );
        
        g2d.setColor( strokeColor );
        g2d.drawOval( x1-radius, y1-radius, radius*2, radius*2 );
        
        if ( label != null ) {
            g2d.drawString( label, x1 - g2d.getFontMetrics().stringWidth( label )/2, y1 + 5 );
        }
        
        g2d.dispose();
        
    }

    @Override
    public boolean intercepts( int x, int y ) {
        
        int dx = x1-x;
        int dy = y1-y;
        
        return dx*dx + dy*dy <= radius*radius;
        
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius( int radius ) {
        this.radius = radius;
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

    public boolean isIsInitial() {
        return isInitial;
    }

    public void setIsInitial( boolean isInitial ) {
        this.isInitial = isInitial;
    }

    public boolean isIsFinal() {
        return isFinal;
    }

    public void setIsFinal( boolean isFinal ) {
        this.isFinal = isFinal;
    }
    
}
