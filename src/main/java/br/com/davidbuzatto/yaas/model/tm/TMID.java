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
import br.com.davidbuzatto.yaas.util.CharacterConstants;
import br.com.davidbuzatto.yaas.util.DrawingConstants;
import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class models Turing Machine Instantaneous Descriptions (IDs).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TMID extends AbstractGeometricForm implements Comparable<TMID> {
    
    private TMState state;
    private String string;
    private TMOperation operation;
    private int position;
    
    private TMID parent;
    private List<TMID> children;
    
    private boolean acceptedByFinalState;
    private boolean acceptedByHalt;
    private Color acceptedStrokeColor;
    private Color acceptedFillColor;
    
    private String text;
    private int textWidth;
    private int textHeight;
    private Color textColor;
    
    private boolean activeInSimulation;
    private Color activeInSimulationStrokeColor;
    private Color activeInSimulationFillColor;
    
    public TMID( TMState state, String string, int position, TMOperation operation, Color strokeColor ) {
        
        this.state = state;
        this.string = string;
        this.children = new ArrayList<>();
        this.position = position;
        this.operation = operation;
        
        this.font = DrawingConstants.DEFAULT_FONT;
        this.textColor = DrawingConstants.TMID_DEFAULT_TEXT_COLOR;
        
        activeInSimulationFillColor = DrawingConstants.TMID_ACTIVE_IN_SIMULATION_FILL_COLOR;
        activeInSimulationStrokeColor = DrawingConstants.TMID_ACTIVE_IN_SIMULATION_STROKE_COLOR;
        
        setStrokeColor( strokeColor );
        setAcceptedStrokeColor( DrawingConstants.TMID_DEFAULT_ACCEPTED_COLOR );
        setText( toString() );
        
    }
    
    public void addChild( TMID child ) {
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

    public TMOperation getOperation() {
        return operation;
    }

    public void setActiveInSimulation( boolean activeInSimulation ) {
        this.activeInSimulation = activeInSimulation;
    }
    
    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        g2d.setFont( font );
        
        Color hilightStrokeColor = null;
        Color hilightFillColor = null;
        
        if ( acceptedByFinalState || acceptedByHalt ) {
            hilightStrokeColor = acceptedStrokeColor;
            hilightFillColor = acceptedFillColor;
        }
        
        if ( activeInSimulation ) {
            hilightStrokeColor = activeInSimulationStrokeColor;
            hilightFillColor = activeInSimulationFillColor;
        }
        
        if ( hilightStrokeColor != null ) {
            g2d.setColor( hilightFillColor );
            g2d.fillRoundRect( 
                    x1 - textWidth / 2 - 5,
                    y1 - textHeight / 2 - 8,
                    textWidth + 10,
                    textHeight + 15, 5, 5 );
            g2d.setColor( hilightStrokeColor );
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
    
    public TMID derive( TMTransition transition, TMOperation operation ) {
        
        String pre = string.substring( 0, position );
        String pos = string.substring( position+1 );
        String newString = pre + operation.getWriteSymbol() + pos;
        
        int newPosition = position + 
                (operation.getType() == TMMovementType.MOVE_RIGHT ? 1 : -1 );
        
        if ( newPosition == newString.length() ) {
            newString += CharacterConstants.BLANK_TAPE_SYMBOL;
        } else if ( newPosition == -1 ) {
            newString = CharacterConstants.BLANK_TAPE_SYMBOL + newString;
        }
        
        return new TMID( 
                transition.getTargetState(), 
                newString, 
                newPosition, 
                operation, 
                transition.getStrokeColor() );
        
    }
    
    public static void main( String[] args ) {
    }
    
    public TMState getState() {
        return state;
    }

    public String getString() {
        return string;
    }

    public TMID getParent() {
        return parent;
    }

    public List<TMID> getChildren() {
        return children;
    }

    public int getPosition() {
        return position;
    }

    public boolean isAcceptedByFinalState() {
        return acceptedByFinalState;
    }

    public void setAcceptedByFinalState( boolean acceptedByFinalState ) {
        this.acceptedByFinalState = acceptedByFinalState;
    }

    public boolean isAcceptedByHalt() {
        return acceptedByHalt;
    }

    public void setAcceptedByHalt( boolean acceptedByHalt ) {
        this.acceptedByHalt = acceptedByHalt;
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
    public int compareTo( TMID o ) {
        return state.compareTo( o.state );
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode( this.state );
        hash = 29 * hash + Objects.hashCode( this.string );
        hash = 29 * hash + Objects.hashCode( this.operation );
        hash = 29 * hash + this.position;
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
        final TMID other = (TMID) obj;
        if ( this.position != other.position ) {
            return false;
        }
        if ( !Objects.equals( this.string, other.string ) ) {
            return false;
        }
        if ( !Objects.equals( this.state, other.state ) ) {
            return false;
        }
        return Objects.equals( this.operation, other.operation );
    }
    
    @Override
    public String toString() {
        return String.format( "%s%s%s", 
                string.substring( 0, position ), 
                state, 
                string.substring( position ) );
    }
    
}
