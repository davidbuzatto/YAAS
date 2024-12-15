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
package br.com.davidbuzatto.yaas.model.tm;


import br.com.davidbuzatto.yaas.model.AbstractGeometricForm;
import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.util.ArrayList;
import java.util.List;

/**
 * A graphical representation of a Turing Machine transition label.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TMTransitionLabel extends AbstractGeometricForm implements Cloneable {
    
    private static final long serialVersionUID = 1L;
    
    private List<String> texts;
    private List<Integer> textsWidth;
    private List<Integer> textsHeight;
    
    private int totalTextWidth;
    private int totalTextHeight;
    private int maxTextWidth;
    private int maxTextHeight;
    
    private boolean activeInSimulation;
    private Color activeInSimulationStrokeColor;
    private Color activeInSimulationFillColor;
    
    public TMTransitionLabel() {
        texts = new ArrayList<>();
        textsWidth = new ArrayList<>();
        textsHeight = new ArrayList<>();
    }
    
    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        g2d.setStroke( stroke.getBasicStroke() );
        g2d.setFont( font );
        
        boolean drawBackground = false;
        Color localFillColor = null;
        Color localStrokeColor = null;
        
        if ( mouseHover ) {
            localFillColor = mouseHoverFillColor;
            localStrokeColor = mouseHoverStrokeColor;
            drawBackground = true;
        } else if ( activeInSimulation ) {
            localFillColor = activeInSimulationFillColor;
            localStrokeColor = activeInSimulationStrokeColor;
            drawBackground = true;
        } else if ( selected ) {
            localFillColor = selectedFillColor;
            localStrokeColor = selectedStrokeColor;
            drawBackground = true;
        } else {
            g2d.setColor( strokeColor );
        }
        
        if ( drawBackground ) {
            g2d.setColor( localFillColor );
            g2d.fillRoundRect(
                    x1 - totalTextWidth/2 - 4, y1 - totalTextHeight - 1, 
                    totalTextWidth + 8, totalTextHeight + 8,
                    10, 10 );
            g2d.setColor( localStrokeColor );
            g2d.drawRoundRect(
                    x1 - totalTextWidth/2 - 4, y1 - totalTextHeight - 1, 
                    totalTextWidth + 8, totalTextHeight + 8,
                    10, 10 );
        }
        
        for ( int i = 0; i < texts.size(); i++ ) {
            String text = texts.get( i );
            int textWidth = textsWidth.get( i );
            int textHeight = textsHeight.get( i );
            g2d.drawString( text, x1 - textWidth/2, 
                    y1 + textHeight / 2 - ( ( maxTextHeight + 5 ) * ( texts.size() - i ) ) + 11 );
        }
        
        g2d.dispose();
        
    }

    @Override
    public boolean intersects( int x, int y ) {
        return 
                x >= x1 - totalTextWidth/2 - 4 && 
                x <= x1 + totalTextWidth/2 + 8 &&
                y >= y1 - totalTextHeight - 1 && 
                y <= y1 + 7;
    }
    
    public void addText( String text ) {
        texts.add( text );
        LineMetrics lm = Utils.getLineMetrics( text, font );
        FontMetrics fm = Utils.getFontMetrics( font );
        textsWidth.add( fm.stringWidth( text ) );
        textsHeight.add( (int) ( lm.getHeight() / 2 ) );
        updateTotals();
    }

    public void clear() {
        texts.clear();
        textsWidth.clear();
        textsHeight.clear();
        totalTextWidth = 0;
        totalTextHeight = 0;
        maxTextHeight = 0;
    }
    
    private void updateTotals() {
        
        int maxWidth = Integer.MIN_VALUE;
        int maxHeight = Integer.MIN_VALUE;
        
        for ( int v : textsWidth ) {
            if ( maxWidth < v ) {
                maxWidth = v;
            }
        }
        totalTextWidth = maxWidth;
        maxTextWidth = maxWidth;
        
        totalTextHeight = 0;
        for ( int v : textsHeight ) {
            totalTextHeight += v + 5;
            if ( maxHeight < v ) {
                maxHeight = v;
            }
        }
        maxTextHeight = maxHeight;
        
    }
    
    public int getTotalTextWidth() {
        return totalTextWidth;
    }

    public int getTotalTextHeight() {
        return totalTextHeight;
    }

    public int getMaxTextWidth() {
        return maxTextWidth;
    }

    public int getMaxTextHeight() {
        return maxTextHeight;
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

    @Override
    public String toString() {
        String str = "";
        boolean f = true;
        for ( String s : texts ) {
            if ( !f ) {
                str += ", ";
            }
            str += s;
            f = false;
        }
        return str;
    }
    
    @Override
    @SuppressWarnings( "unchecked" )
    public TMTransitionLabel clone() throws CloneNotSupportedException {
        
        TMTransitionLabel c = (TMTransitionLabel) super.clone();
        
        c.texts = new ArrayList<>();
        for ( String t : texts ) {
            c.texts.add( t );
        }
        
        c.textsWidth = new ArrayList<>();
        for ( Integer v : textsWidth ) {
            c.textsWidth.add( v );
        }
        
        c.textsHeight = new ArrayList<>();
        for ( Integer v : textsHeight ) {
            c.textsHeight.add( v );
        }
        
        c.totalTextWidth = totalTextWidth;
        c.totalTextHeight = totalTextHeight;
        c.maxTextWidth = maxTextWidth;
        c.maxTextHeight = maxTextHeight;
    
        c.activeInSimulation = false;
        c.activeInSimulationStrokeColor = activeInSimulationStrokeColor;
        c.activeInSimulationFillColor = activeInSimulationFillColor;
        
        return c;
        
    }
    
}
