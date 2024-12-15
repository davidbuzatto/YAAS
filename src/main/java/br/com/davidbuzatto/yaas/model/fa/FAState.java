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
package br.com.davidbuzatto.yaas.model.fa;

import br.com.davidbuzatto.yaas.model.AbstractGeometricForm;
import br.com.davidbuzatto.yaas.model.Arrow;
import br.com.davidbuzatto.yaas.util.DrawingConstants;
import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * A Finite Automaton state.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAState extends AbstractGeometricForm implements Comparable<FAState>, Cloneable {
    
    private static final long serialVersionUID = 1L;
    
    private int number;
    private String label;
    private String customLabel;
    
    private boolean initial;
    private boolean _final;
    
    private int radius;
    private int radiusSquared;
    private int diameter;
    
    private Arrow arrow;
    
    private boolean activeInSimulation;
    private Color activeInSimulationStrokeColor;
    private Color activeInSimulationFillColor;
    
    public FAState() {
        this( 0, null, false, false );
    }
    
    public FAState( int number ) {
        this( number, null, false, false );
    }
    
    public FAState( int number, boolean initial, boolean _final ) {
        this( number, null, initial, _final );
    }
    
    public FAState( int number, String customLabel, boolean initial, boolean _final ) {
        
        this.number = number;
        this.label = "q" + number;
        this.customLabel = customLabel;
        this.initial = initial;
        this._final = _final;
        
        font = DrawingConstants.DEFAULT_FONT;
        stroke = DrawingConstants.STATE_STROKE;
        
        setStrokeColor( DrawingConstants.STATE_STROKE_COLOR );
        
        mouseHoverFillColor = DrawingConstants.STATE_MOUSE_HOVER_FILL_COLOR;
        mouseHoverStrokeColor = DrawingConstants.STATE_MOUSE_HOVER_STROKE_COLOR;
        
        selectedFillColor = DrawingConstants.STATE_SELECTED_FILL_COLOR;
        selectedStrokeColor = DrawingConstants.STATE_SELECTED_STROKE_COLOR;
        
        activeInSimulationFillColor = DrawingConstants.STATE_ACTIVE_IN_SIMULATION_FILL_COLOR;
        activeInSimulationStrokeColor = DrawingConstants.STATE_ACTIVE_IN_SIMULATION_STROKE_COLOR;
        
        radius = DrawingConstants.STATE_RADIUS;
        radiusSquared = DrawingConstants.STATE_RADIUS_SQUARED;
        diameter = DrawingConstants.STATE_DIAMETER;
        
        arrow = new Arrow();
        arrow.setMouseHoverStrokeColor( DrawingConstants.STATE_MOUSE_HOVER_STROKE_COLOR );
        arrow.setSelectedStrokeColor( DrawingConstants.STATE_SELECTED_STROKE_COLOR );
        arrow.setActiveInSimulationStrokeColor( DrawingConstants.STATE_ACTIVE_IN_SIMULATION_STROKE_COLOR );
        
    }
    
    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        g2d.setFont( font );
        g2d.setStroke( stroke.getBasicStroke() );
        arrow.setMouseHover( mouseHover );
        arrow.setSelected( selected );
        arrow.setStrokeColor( strokeColor );
        arrow.setActiveInSimulation( activeInSimulation );
        
        if ( mouseHover ) {
            g2d.setColor( mouseHoverStrokeColor );
        } else if ( activeInSimulation ) {
            g2d.setColor( activeInSimulationStrokeColor );
        } else if ( selected ) {
            g2d.setColor( selectedStrokeColor );
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
        } else if ( activeInSimulation ) {
            g2d.setColor( activeInSimulationFillColor );
        } else if ( selected ) {
            g2d.setColor( selectedFillColor );
        } else {
            g2d.setColor( fillColor );
        }
        
        g2d.fillOval( x1 - radius, y1 - radius, diameter, diameter );
        
        if ( mouseHover ) {
            g2d.setColor( mouseHoverStrokeColor );
        } else if ( activeInSimulation ) {
            g2d.setColor( activeInSimulationStrokeColor );
        } else if ( selected ) {
            g2d.setColor( selectedStrokeColor );
        } else {
            g2d.setColor( strokeColor );
        }
        
        g2d.drawOval( x1 - radius, y1 - radius, diameter, diameter );
        
        if ( _final ) {
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
        if ( FAState.this.intersects( x, y ) ) {
            mouseHover = true;
            return;
        }
        mouseHover = false;
    }
    
    @Override
    public boolean intersects( int x, int y ) {
        x = x1 - x;
        y = y1 - y;
        return x*x + y*y <= radiusSquared;
    }
    
    public boolean intersects( Rectangle rectangle ) {
        return new Rectangle( 
                x1 - radius, 
                y1 - radius, 
                diameter, 
                diameter ).intersects( rectangle );
    }

    public int getNumber() {
        return number;
    }

    public void setNumber( int number ) {
        this.number = number;
        this.label = "q" + number;
    }
    
    public String getLabel() {
        return label;
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

    public boolean isFinal() {
        return _final;
    }

    public void setFinal( boolean _final ) {
        this._final = _final;
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

    public Color getActiveInSimulationFillColor() {
        return activeInSimulationFillColor;
    }

    public void setActiveInSimulationFillColor( Color activeInSimulationFillColor ) {
        this.activeInSimulationFillColor = activeInSimulationFillColor;
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

    public void resetStrokeColor() {
        setStrokeColor( DrawingConstants.TRANSITION_STROKE_COLOR );
    }
    
    @Override
    public void setStrokeColor( Color strokeColor ) {
        this.strokeColor = strokeColor;
        this.fillColor = Utils.lighterColor( strokeColor );
    }
    
    @Override
    public String toString() {
        return customLabel != null ? customLabel : label;
    }

    @Override
    public int compareTo( FAState o ) {
        return number - o.number;
    }
    
    public String generateCode( String modelName ) {
        
        String className = getClass().getSimpleName();
        
        String def = String.format("    %s %s = new %s( %d, %s, %b, %b );", 
                className, 
                label, 
                className, 
                number, 
                customLabel == null ? null : String.format( "\"%s\"", customLabel ), 
                initial, 
                _final );
        String pos = String.format( "\n    %s.setX1Y1( %d, %d );", label, x1, y1 );
        String color = String.format( "\n    %s.setStrokeColor( new Color( %d, %d, %d, %d ) );", label, 
                getStrokeColor().getRed(), 
                getStrokeColor().getGreen(), 
                getStrokeColor().getBlue(),
                getStrokeColor().getAlpha() );
        String add = String.format( "\n    %s.addState( %s );", modelName, label );
        
        return def + pos + color + add;
        
    }
    
    @Override
    @SuppressWarnings( "unchecked" )
    public FAState clone() throws CloneNotSupportedException {
        
        FAState c = (FAState) super.clone();
        
        c.number = number;
        c.label = label;
        c.customLabel = customLabel;

        c.initial = initial;
        c._final = _final;

        c.radius = radius;
        c.radiusSquared = radiusSquared;
        c.diameter = diameter;

        c.arrow = arrow.clone();

        c.activeInSimulation = false;
        c.activeInSimulationStrokeColor = activeInSimulationStrokeColor;
        c.activeInSimulationFillColor = activeInSimulationFillColor;
        
        return c;
        
    }
    
}
