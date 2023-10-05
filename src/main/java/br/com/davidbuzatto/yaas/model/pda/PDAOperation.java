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
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Models a transition operation.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAOperation extends AbstractGeometricForm implements Cloneable, Comparable<PDAOperation> {
    
    private char symbol;
    private char top;
    private PDAOperationType type;
    private List<Character> symbolsToPush;

    public PDAOperation( 
            char symbol, 
            char top, 
            PDAOperationType type, 
            List<Character> symbolsToPush ) {
        this.symbol = symbol;
        this.top = top;
        this.type = type;
        this.symbolsToPush = symbolsToPush;
    }
    
    public PDAOperation( 
            char symbol, 
            char top, 
            PDAOperationType type, 
            char symbolToPush ) {
        this( symbol, top, type, new ArrayList<Character>() );
        addSymbolToPush( symbolToPush );
    }
    
    public PDAOperation( 
            char symbol, 
            char top, 
            PDAOperationType type, 
            char... symbolsToPush ) {
        this( symbol, top, type, new ArrayList<Character>() );
        addSymbolsToPush( symbolsToPush );
    }
    
    public PDAOperation( 
            char symbol, 
            char top, 
            PDAOperationType type ) {
        this( symbol, top, type, new ArrayList<Character>() );
    }
    
    public static PDAOperation getDoNothingOperation( char symbol, char top ) {
        return new PDAOperation( symbol, top, PDAOperationType.DO_NOTHING );
    }
    
    public static PDAOperation getPopOperation( char symbol, char top ) {
        return new PDAOperation( symbol, top, PDAOperationType.POP );
    }

    @Override
    public int compareTo( PDAOperation o ) {
        
        if ( symbol != o.symbol ) {
            if ( symbol == CharacterConstants.EMPTY_STRING ) {
                return -1;
            } else if ( o.symbol == CharacterConstants.EMPTY_STRING ) {
                return 1;
            }
        }
        
        if ( symbol < o.symbol ) {
            return -1;
        } else if ( symbol > o.symbol ) {
            return 1;
        } else if ( top != o.top ) {
            
            if ( top == CharacterConstants.EMPTY_STRING ) {
                return -1;
            } else if ( o.top == CharacterConstants.EMPTY_STRING ) {
                return 1;
            } else {
                return top - o.top;
            }
            
        }
        
        String s1 = symbolsToPush.toString();
        String s2 = o.symbolsToPush.toString();

        return s1.compareTo( s2 );
        
    }
    
    @Override
    public void draw( Graphics2D g2d ) {
        throw new UnsupportedOperationException( "Not supported." );
    }

    @Override
    public boolean intersects( int x, int y ) {
        throw new UnsupportedOperationException( "Not supported." );
    }
    
    public void addSymbolToPush( char symbol ) {
        symbolsToPush.add( symbol );
    }
    
    public void addSymbolsToPush( char... symbols ) {
        for ( char s : symbols ) {
            symbolsToPush.add( s );
        }
    }
    
    public void addSymbolsToPush( List<Character> symbols ) {
        symbolsToPush.addAll( symbols );
    }
    
    public char getSymbol() {
        return symbol;
    }

    public void setSymbol( char symbol ) {
        this.symbol = symbol;
    }

    public char getTop() {
        return top;
    }

    public void setTop( char top ) {
        this.top = top;
    }

    public PDAOperationType getType() {
        return type;
    }

    public void setType( PDAOperationType type ) {
        this.type = type;
    }

    public List<Character> getSymbolsToPush() {
        return symbolsToPush;
    }

    public void setSymbolsToPush( List<Character> symbolsToPush ) {
        this.symbolsToPush = symbolsToPush;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.symbol;
        hash = 19 * hash + this.top;
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
        final PDAOperation other = (PDAOperation) obj;
        if ( this.symbol != other.symbol ) {
            return false;
        }
        return this.top == other.top;
    }
    
    @Override
    public String toString() {
        
        String op = "";
        
        switch ( type ) {
            case DO_NOTHING:
                op += top;
                break;
            case POP:
                op += CharacterConstants.EMPTY_STRING;
                break;
            case PUSH:
                for ( int i = symbolsToPush.size()-1; i >= 0; i-- ) {
                    op += symbolsToPush.get( i );
                }
                op += top;
                break;
            case REPLACE:
                for ( int i = symbolsToPush.size()-1; i >= 0; i-- ) {
                    op += symbolsToPush.get( i );
                }
                break;
            
        }
        
        return String.format( "%c,%c/%s", symbol, top, op );
        
    }
    
    public String generateCode( PDA pda, String modelName ) {
        
        String op = "            ";
        
        if ( type == PDAOperationType.DO_NOTHING ) {
            op += "PDAOperation.getDoNothingOperation( ";
        } else if ( type == PDAOperationType.POP ) {
            op += "PDAOperation.getPopOperation( ";
        } else {
            op += "new PDAOperation( ";
        }
        
        if ( symbol == CharacterConstants.EMPTY_STRING ) {
            op += "CharacterConstants.EMPTY_STRING, ";
        } else {
            op += String.format( "'%c', ", symbol );
        }
        
        if ( top == CharacterConstants.EMPTY_STRING ) {
            op += "CharacterConstants.EMPTY_STRING";
        } else if ( top == pda.getStackStartingSymbol() ) {
            op += modelName + ".getStackStartingSymbol()";
        } else {
            op += String.format( "'%c'", top );
        }
        
        if ( type == PDAOperationType.DO_NOTHING ) {
            op += " )";
        } else if ( type == PDAOperationType.POP ) {
            op += " )";
        } else {
            
            String sy = "";
            boolean fs = true;
            for ( char c : symbolsToPush ) {
                if ( !fs ) {
                    sy += ", ";
                }
                sy += String.format( "'%c'", c );
                fs = false;
            }
        
            op += String.format( ", %s, %s )", 
                    type == PDAOperationType.PUSH ? 
                    "PDAOperationType.PUSH" : "PDAOperationType.REPLACE",
                    sy );
            
        }
        
        return op;
        
    }
    
    @Override
    @SuppressWarnings( "unchecked" )
    public Object clone() throws CloneNotSupportedException {
        
        PDAOperation c = (PDAOperation) super.clone();
        
        c.symbol = symbol;
        c.top = top;
        c.type = type;
        
        c.symbolsToPush = new ArrayList<>();
        for ( Character s : symbolsToPush ) {
            c.symbolsToPush.add( s );
        }
        
        return c;
        
    }
    
}
