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
    
    public PDAID( PDAState state, String string, Deque<Character> stack, Color strokeColor ) {
        
        this.state = state;
        this.string = string;
        this.children = new ArrayList<>();
        this.stack = Utils.cloneCharacterStack( stack );
        
        this.font = DrawingConstants.DEFAULT_FONT;
        this.strokeColor = strokeColor;
        
    }
    
    public void addChild( PDAID child ) {
        children.add( child );
        child.parent = this;
    }

    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        g2d.setFont( font );
        g2d.setColor( Color.BLACK );
        
        String text = toString();
        LineMetrics lm = Utils.getLineMetrics( text, font );
        FontMetrics fm = Utils.getFontMetrics( font );
        int textWidth = fm.stringWidth( text );
        int textHeight = (int) ( lm.getHeight() / 2 );
        
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
