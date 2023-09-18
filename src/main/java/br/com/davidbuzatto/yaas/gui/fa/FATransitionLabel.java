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
package br.com.davidbuzatto.yaas.gui.fa;

import br.com.davidbuzatto.yaas.gui.model.AbstractGeometricForm;
import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;

/**
 * A graphical representation of a transition label.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FATransitionLabel extends AbstractGeometricForm {
    
    private String text;
    private int textWidth;
    private int textHeight;
    
    private boolean activeInSimulation;
    private Color activeInSimulationStrokeColor;
    private Color activeInSimulationFillColor;
    
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
                    x1 - textWidth/2 - 4, y1 - textHeight / 2 - 6, 
                    textWidth + 8, textHeight + 8,
                    10, 10 );
            g2d.setColor( localStrokeColor );
            g2d.drawRoundRect( 
                    x1 - textWidth/2 - 4, y1 - textHeight / 2 - 6, 
                    textWidth + 8, textHeight + 8,
                    10, 10 );
        }
        
        if ( text != null ) {
            g2d.drawString( text, x1 - textWidth/2, y1 + textHeight / 2 );
        }
        
        g2d.dispose();
        
    }

    @Override
    public boolean intersects( int x, int y ) {
        return 
                x >= x1 - textWidth/2 - 4 && 
                x <= x1 + textWidth/2 + 8 &&
                y >= y1 - textHeight/2 - 4 && 
                y <= y1 + textHeight/2 + 8;
    }
    
    public void setText( String text ) {
        this.text = text;
        LineMetrics lm = Utils.getLineMetrics( text, font );
        FontMetrics fm = Utils.getFontMetrics( font );
        textWidth = fm.stringWidth( text );
        textHeight = (int) ( lm.getHeight() / 2 );
    }

    public String getText() {
        return text;
    }

    public int getTextWidth() {
        return textWidth;
    }

    public int getTextHeight() {
        return textHeight;
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
    
}
