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
package br.com.davidbuzatto.yaas.model.pda;

import br.com.davidbuzatto.yaas.model.AbstractGeometricForm;
import br.com.davidbuzatto.yaas.util.CharacterConstants;
import br.com.davidbuzatto.yaas.util.DrawingConstants;
import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * This class models Pushdown Automata Instantaneous Descriptios (IDs).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAID extends AbstractGeometricForm implements Comparable<PDAID> {
    
    private PDAState state;
    private String string;
    private Deque<Character> stack;
    
    private PDAID parent;
    private List<PDAID> children;
    
    private boolean acceptedByFinalState;
    private boolean acceptedByEmptyStack;
    private Color acceptedStrokeColor;
    private Color acceptedFillColor;
    
    private String text;
    private int textWidth;
    private int textHeight;
    private Color textColor;
    
    public PDAID( PDAState state, String string, Deque<Character> stack, Color strokeColor ) {
        
        this.state = state;
        this.string = string;
        this.children = new ArrayList<>();
        this.stack = Utils.cloneCharacterStack( stack );
        
        this.font = DrawingConstants.DEFAULT_FONT;
        setStrokeColor( strokeColor );
        setAcceptedStrokeColor( DrawingConstants.PDAID_DEFAULT_ACCEPTED_COLOR );
        this.textColor = DrawingConstants.PDAID_DEFAULT_TEXT_COLOR;
        
        setText( toString() );
        
    }
    
    public void addChild( PDAID child ) {
        children.add( child );
        child.parent = this;
    }

    public void setText( String text ) {
        this.text = text;
        LineMetrics lm = Utils.getLineMetrics( text, font );
        FontMetrics fm = Utils.getFontMetrics( font );
        textWidth = fm.stringWidth( text );
        textHeight = (int) ( lm.getHeight() / 2 );
    }
    
    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        g2d.setFont( font );
        
        if ( acceptedByFinalState || acceptedByEmptyStack ) {
            g2d.setColor( acceptedFillColor );
            g2d.fillRoundRect( 
                    x1 - textWidth / 2 - 5,
                    y1 - textHeight / 2 - 8,
                    textWidth + 10,
                    textHeight + 15, 5, 5 );
            g2d.setColor( acceptedStrokeColor );
            g2d.drawRoundRect( 
                    x1 - textWidth / 2 - 5,
                    y1 - textHeight / 2 - 8,
                    textWidth + 10,
                    textHeight + 15, 5, 5 );
        }
        
        g2d.setColor( textColor );
        g2d.drawString( text, x1 - textWidth / 2, y1 + textHeight / 2 );
        
        g2d.dispose();
        
    }

    @Override
    public boolean intersects( int x, int y ) {
        return false;
    }
    
    public PDAState getState() {
        return state;
    }

    public String getString() {
        return string;
    }

    public Deque<Character> getStack() {
        return stack;
    }

    public PDAID getParent() {
        return parent;
    }

    public List<PDAID> getChildren() {
        return children;
    }

    public boolean isAcceptedByFinalState() {
        return acceptedByFinalState;
    }

    public void setAcceptedByFinalState( boolean acceptedByFinalState ) {
        this.acceptedByFinalState = acceptedByFinalState;
    }

    public boolean isAcceptedByEmptyStack() {
        return acceptedByEmptyStack;
    }

    public void setAcceptedByEmptyStack( boolean acceptedByEmptyStack ) {
        this.acceptedByEmptyStack = acceptedByEmptyStack;
    }

    @Override
    public void setStrokeColor( Color strokeColor ) {
        this.strokeColor = strokeColor;
        this.fillColor = Utils.lighterColor( strokeColor );
    }
    
    public void setAcceptedStrokeColor( Color acceptedStrokeColor ) {
        this.acceptedStrokeColor = acceptedStrokeColor;
        this.acceptedFillColor = Utils.lighterColor( acceptedStrokeColor );
    }

    public int getTextWidth() {
        return textWidth;
    }

    public int getTextHeight() {
        return textHeight;
    }
    
    @Override
    public int compareTo( PDAID o ) {
        return state.compareTo( o.state );
    }
    
    @Override
    public String toString() {
        String sStr = "";
        for ( char c : stack ) {
            sStr += c;
        }
        return String.format( "(%s, %s, %s)", state, 
                string.isEmpty() ? CharacterConstants.EMPTY_STRING.toString() : string , 
                sStr );
    }
    
}
