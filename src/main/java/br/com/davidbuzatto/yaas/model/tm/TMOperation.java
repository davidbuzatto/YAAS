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
import java.util.ArrayList;
import java.util.List;

/**
 * Models a Turing Machine transition operation.
 * TODO update
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TMOperation extends AbstractGeometricForm implements Cloneable, Comparable<TMOperation> {
    
    private char symbol;
    private char top;
    private TMOperationType type;
    private List<Character> symbolsToPush;

    public TMOperation( 
            char symbol, 
            char top, 
            TMOperationType type, 
            List<Character> symbolsToPush ) {
        this.symbol = symbol;
        this.top = top;
        this.type = type;
        this.symbolsToPush = symbolsToPush;
    }
    
    public TMOperation( 
            char symbol, 
            char top, 
            TMOperationType type, 
            char symbolToPush ) {
        this( symbol, top, type, new ArrayList<Character>() );
        addSymbolToPush( symbolToPush );
    }
    
    public TMOperation( 
            char symbol, 
            char top, 
            TMOperationType type, 
            char... symbolsToPush ) {
        this( symbol, top, type, new ArrayList<Character>() );
        addSymbolsToPush( symbolsToPush );
    }
    
    public TMOperation( 
            char symbol, 
            char top, 
            TMOperationType type ) {
        this( symbol, top, type, new ArrayList<Character>() );
    }
    
    public static TMOperation getDoNothingOperation( char symbol, char top ) {
        return new TMOperation( symbol, top, TMOperationType.DO_NOTHING );
    }
    
    public static TMOperation getPopOperation( char symbol, char top ) {
        return new TMOperation( symbol, top, TMOperationType.POP );
    }

    @Override
    public int compareTo( TMOperation o ) {
        
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

    public TMOperationType getType() {
        return type;
    }

    public void setType( TMOperationType type ) {
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
        final TMOperation other = (TMOperation) obj;
        if ( this.symbol != other.symbol ) {
            return false;
        }
        return this.top == other.top;
    }
    
    @Override // TODO update
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
    
    public String generateCode( TM pda, String modelName ) {
        
        String op = "            ";
        
        if ( type == TMOperationType.DO_NOTHING ) {
            op += "TMOperationType.getDoNothingOperation( ";
        } else if ( type == TMOperationType.POP ) {
            op += "TMOperationType.getPopOperation( ";
        } else {
            op += "new TMOperationType( ";
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
        
        if ( type == TMOperationType.DO_NOTHING ) {
            op += " )";
        } else if ( type == TMOperationType.POP ) {
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
                    type == TMOperationType.PUSH ? 
                    "TMOperationType.PUSH" : "TMOperationType.REPLACE",
                    sy );
            
        }
        
        return op;
        
    }
    
    @Override
    @SuppressWarnings( "unchecked" )
    public Object clone() throws CloneNotSupportedException {
        
        TMOperation c = (TMOperation) super.clone();
        
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
