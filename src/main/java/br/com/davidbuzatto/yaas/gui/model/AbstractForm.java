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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 * An abstract class with the basic behavior of drawable forms.
 * 
 * @author Prof. Dr. David Buzatto
 */
public abstract class AbstractForm {
    
    private static int idGenerator;
    protected int id;
    
    protected int x1;
    protected int y1;
    protected int x2;
    protected int y2;
    protected boolean mouseHover;
    
    protected Color strokeColor = Color.BLACK;
    protected Color fillColor = Color.WHITE;
    
    protected Color mouseHoverStrokeColor = Color.BLUE;
    protected Color mouseHoverFillColor = Color.GREEN;
    
    protected Font font;
    protected Stroke stroke;
    
    public AbstractForm() {
        id = idGenerator++;
    }
    
    public abstract void draw( Graphics2D g2d );
    public abstract boolean intercepts( int x, int y );

    public static int getIdGenerator() {
        return idGenerator;
    }

    public static void setIdGenerator( int idGenerator ) {
        AbstractForm.idGenerator = idGenerator;
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

    public Font getFont() {
        return font;
    }

    public void setFont( Font font ) {
        this.font = font;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke( Stroke stroke ) {
        this.stroke = stroke;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.id;
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
        final AbstractForm other = (AbstractForm) obj;
        return this.id == other.id;
    }
    
}
