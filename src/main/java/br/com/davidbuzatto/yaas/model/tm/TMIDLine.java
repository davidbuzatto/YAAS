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
import br.com.davidbuzatto.yaas.model.Arrow;
import br.com.davidbuzatto.yaas.util.DrawingConstants;
import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;

/**
 * The line of a TMID tree.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TMIDLine extends AbstractGeometricForm {

    private static final long serialVersionUID = 1L;
    
    private TMID origin;
    private TMID target;
    private TMOperation operation;
    private Arrow arrow;
    
    private int mouseX;
    private int mouseY;
    
    private String operationText;
    private int operationTextWidth;
    private int operationTextHeight;

    public TMIDLine( 
            TMID origin, TMID target, 
            int x1, int y1, 
            int x2, int y2, 
            TMOperation operation, Color strokeColor ) {
        
        this.origin = origin;
        this.target = target;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.operation = operation;
        this.font = DrawingConstants.DEFAULT_FONT;
        this.setStroke( DrawingConstants.PDAIDLINE_DEFAULT_STROKE );
        setStrokeColor( strokeColor );
        
        arrow = new Arrow();
        arrow.setX1( x2 );
        arrow.setY1( y2 );
        arrow.setStrokeColor( strokeColor );
        arrow.setAngle( Math.atan2( y2 - y1, x2 - x1 ) );

    }

    public TMOperation getOperation() {
        return operation;
    }

    public void setMouseXY( int x, int y ) {
        mouseX = x;
        mouseY = y;
    }
    
    @Override
    public void setStrokeColor( Color strokeColor ) {
        this.strokeColor = strokeColor;
        this.fillColor = Utils.lighterColor( strokeColor );
    }
    
    @Override
    public void move( int xAmount, int yAmount ) {
        super.move( xAmount, yAmount );
        arrow.move( xAmount, yAmount );
    }

    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        boolean showOp = false;
        
        if ( mouseHover ) {
            setStroke( DrawingConstants.PDAIDLINE_MOUSE_OVER_STROKE );
            arrow.setLength( DrawingConstants.ARROW_MOUSE_OVER_LENGTH );
            showOp = true;
        } else {
            setStroke( DrawingConstants.PDAIDLINE_DEFAULT_STROKE );
            arrow.setLength( DrawingConstants.ARROW_LENGTH );
        }
        
        g2d.setStroke( stroke.getBasicStroke() );
        g2d.setColor( strokeColor );
        
        g2d.drawLine( x1, y1, x2, y2 );
        arrow.draw( g2d );
        
        if ( showOp && operation != null ) {
            
            setStroke( DrawingConstants.PDAIDLINE_DEFAULT_STROKE );
            g2d.setStroke( stroke.getBasicStroke() );
            g2d.setFont( font );
            
            if ( operationText == null ) {
                operationText = operation.toString();
                LineMetrics lm = Utils.getLineMetrics( operationText, font );
                FontMetrics fm = g2d.getFontMetrics();
                operationTextWidth = fm.stringWidth( operationText );
                operationTextHeight = (int) ( lm.getHeight() / 2 );
            }
            
            int textX = mouseX + 10;
            int textY = mouseY - 10;
            
            g2d.setColor( fillColor );
            g2d.fillRoundRect( 
                    textX - 5, 
                    textY - operationTextHeight - 5, 
                    operationTextWidth + 10, 
                    operationTextHeight + 10, 
                    5, 5 );
            
            g2d.setColor( strokeColor );
            g2d.drawRoundRect( 
                    textX - 5, 
                    textY - operationTextHeight - 5, 
                    operationTextWidth + 10, 
                    operationTextHeight + 10, 
                    5, 5 );
            
            g2d.drawString( operationText, textX, textY );
            
        }
        
        g2d.dispose();
        
    }

    @Override
    public boolean intersects( int x, int y ) {
        
        int x1 = this.x1 <= this.x2 ? this.x1 : this.x2;
        int y1 = this.y1 <= this.y2 ? this.y1 : this.y2;
        int x2 = this.x1 <= this.x2 ? this.x2 : this.x1;
        int y2 = this.y1 <= this.y2 ? this.y2 : this.y1;
        
        if ( x1 == x2 ) {
            return x >= x1-10 && x <= x2+10 && y >= y1 && y <= y2;
        } else if ( y1 == y2 ) {
            return x >= x1 && x <= x2 && y >= y1-10 && y <= y2+10;
        }
        
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
        
    }

}
