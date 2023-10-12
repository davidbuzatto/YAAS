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
import java.awt.Graphics2D;

/**
 * Models a Turing Machine transition operation.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TMOperation extends AbstractGeometricForm implements Cloneable, Comparable<TMOperation> {
    
    private char readSymbol;
    private char writeSymbol;
    private TMMovementType type;

    public TMOperation( 
            char readSymbol, 
            char writeSymbol, 
            TMMovementType type ) {
        this.readSymbol = readSymbol;
        this.writeSymbol = writeSymbol;
        this.type = type;
    }
    
    public static TMOperation getMoveRightOperation( char symbol, char top ) {
        return new TMOperation( symbol, top, TMMovementType.MOVE_RIGHT );
    }
    
    public static TMOperation getMoveLeftOperation( char symbol, char top ) {
        return new TMOperation( symbol, top, TMMovementType.MOVE_LEFT );
    }

    @Override
    public int compareTo( TMOperation o ) {
        
        if ( readSymbol != o.readSymbol ) {
            if ( readSymbol == CharacterConstants.EMPTY_STRING ) {
                return -1;
            } else if ( o.readSymbol == CharacterConstants.EMPTY_STRING ) {
                return 1;
            }
        }
        
        if ( readSymbol < o.readSymbol ) {
            return -1;
        } else if ( readSymbol > o.readSymbol ) {
            return 1;
        } else if ( writeSymbol != o.writeSymbol ) {
            
            if ( writeSymbol == CharacterConstants.EMPTY_STRING ) {
                return -1;
            } else if ( o.writeSymbol == CharacterConstants.EMPTY_STRING ) {
                return 1;
            } else {
                return writeSymbol - o.writeSymbol;
            }
            
        }
        
        return 0;
        
    }
    
    @Override
    public void draw( Graphics2D g2d ) {
        throw new UnsupportedOperationException( "Not supported." );
    }

    @Override
    public boolean intersects( int x, int y ) {
        throw new UnsupportedOperationException( "Not supported." );
    }
    
    public char getReadSymbol() {
        return readSymbol;
    }

    public void setReadSymbol( char readSymbol ) {
        this.readSymbol = readSymbol;
    }

    public char getWriteSymbol() {
        return writeSymbol;
    }

    public void setWriteSymbol( char writeSymbol ) {
        this.writeSymbol = writeSymbol;
    }

    public TMMovementType getType() {
        return type;
    }

    public void setType( TMMovementType type ) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.readSymbol;
        hash = 19 * hash + this.writeSymbol;
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
        final TMOperation other = (TMOperation) obj;
        if ( this.readSymbol != other.readSymbol ) {
            return false;
        }
        return this.writeSymbol == other.writeSymbol;
    }
    
    @Override
    public String toString() {
        return String.format("%c/%c%s", readSymbol, writeSymbol, 
                type == TMMovementType.MOVE_RIGHT ? 
                        CharacterConstants.ARROW_RIGHT : 
                        CharacterConstants.ARROW_LEFT );
    }
    
    public String generateCode( TM pda, String modelName ) {
        
        String op = "            ";
        
        if ( type == TMMovementType.MOVE_RIGHT ) {
            op += "TMOperation.getMoveRightOperation( ";
        } else if ( type == TMMovementType.MOVE_LEFT ) {
            op += "TMOperation.getMoveLeftOperation( ";
        }
        
        op += String.format( "'%c', '%c' )", readSymbol, writeSymbol );
        
        return op;
        
    }
    
    @Override
    @SuppressWarnings( "unchecked" )
    public Object clone() throws CloneNotSupportedException {
        
        TMOperation c = (TMOperation) super.clone();
        
        c.readSymbol = readSymbol;
        c.writeSymbol = writeSymbol;
        c.type = type;
        
        return c;
        
    }
    
}
