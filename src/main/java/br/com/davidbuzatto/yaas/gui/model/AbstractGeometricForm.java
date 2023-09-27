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
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

/**
 * An abstract class with the basic behavior of drawable forms.
 * 
 * @author Prof. Dr. David Buzatto
 */
public abstract class AbstractGeometricForm implements Serializable, Cloneable {
    
    private static final long serialVersionUID = 1L;
    
    protected BigInteger id;
    
    protected int x1;
    protected int y1;
    protected int x2;
    protected int y2;
    protected int width;
    protected int height;
    protected boolean mouseHover;
    protected boolean selected;
    
    protected Color strokeColor = Color.BLACK;
    protected Color fillColor = Color.WHITE;
    
    protected Color mouseHoverStrokeColor = Color.BLACK;
    protected Color mouseHoverFillColor = Color.WHITE;
    
    protected Color selectedStrokeColor = Color.BLUE;
    protected Color selectedFillColor = Color.WHITE;
    
    protected Font font;
    protected SerializableBasicStroke stroke;
    
    public AbstractGeometricForm() {
        id = Utils.generateUUID();
    }
    
    public abstract void draw( Graphics2D g2d );
    public abstract boolean intersects( int x, int y );

    public void move( int xAmount, int yAmount ) {
        x1 += xAmount;
        y1 += yAmount;
        x2 += xAmount;
        y2 += yAmount;
    }

    public int getX1() {
        return x1;
    }

    public void setX1( int x1 ) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1( int y1 ) {
        this.y1 = y1;
    }
    
    public void setX1Y1( int x1, int y1 ) {
        this.x1 = x1;
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2( int x2 ) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2( int y2 ) {
        this.y2 = y2;
    }

    public void setX2Y2( int x2, int y2 ) {
        this.x2 = x2;
        this.y2 = y2;
    }
    
    public boolean isMouseHover() {
        return mouseHover;
    }

    public void setMouseHover( boolean mouseHover ) {
        this.mouseHover = mouseHover;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor( Color strokeColor ) {
        this.strokeColor = strokeColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor( Color fillColor ) {
        this.fillColor = fillColor;
    }

    public Color getMouseHoverStrokeColor() {
        return mouseHoverStrokeColor;
    }

    public void setMouseHoverStrokeColor( Color mouseHoverStrokeColor ) {
        this.mouseHoverStrokeColor = mouseHoverStrokeColor;
    }

    public Color getMouseHoverFillColor() {
        return mouseHoverFillColor;
    }

    public void setMouseHoverFillColor( Color mouseHoverFillColor ) {
        this.mouseHoverFillColor = mouseHoverFillColor;
    }

    public Color getSelectedStrokeColor() {
        return selectedStrokeColor;
    }

    public void setSelectedStrokeColor( Color selectedStrokeColor ) {
        this.selectedStrokeColor = selectedStrokeColor;
    }

    public Color getSelectedFillColor() {
        return selectedFillColor;
    }

    public void setSelectedFillColor( Color selectedFillColor ) {
        this.selectedFillColor = selectedFillColor;
    }

    public Font getFont() {
        return font;
    }

    public void setFont( Font font ) {
        this.font = font;
    }

    public SerializableBasicStroke getStroke() {
        return stroke;
    }

    public void setStroke( SerializableBasicStroke stroke ) {
        this.stroke = stroke;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected( boolean selected ) {
        this.selected = selected;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth( int width ) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight( int height ) {
        this.height = height;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode( this.id );
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final AbstractGeometricForm other = (AbstractGeometricForm) obj;
        return Objects.equals( this.id, other.id );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public Object clone() throws CloneNotSupportedException {
        
        AbstractGeometricForm c = (AbstractGeometricForm) super.clone();
        
        c.id = Utils.generateUUID();
        
        c.x1 = x1;
        c.y1 = y1;
        c.x2 = x2;
        c.y2 = y2;
        c.width = width;
        c.height = height;
        c.mouseHover = false;
        c.selected = false;
        
        c.strokeColor = strokeColor;
        c.fillColor = fillColor;
        
        c.mouseHoverStrokeColor = mouseHoverStrokeColor;
        c.mouseHoverFillColor = mouseHoverFillColor;
        
        c.selectedStrokeColor = selectedStrokeColor;
        c.selectedFillColor = selectedFillColor;
        
        c.font = font;
        
        if ( stroke != null ) {
            c.stroke = (SerializableBasicStroke) stroke.clone();
        }
        
        return c;
        
    }
    
}
