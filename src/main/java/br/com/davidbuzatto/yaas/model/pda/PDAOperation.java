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

import static br.com.davidbuzatto.yaas.model.pda.PDAOperationType.POP;
import br.com.davidbuzatto.yaas.util.CharacterConstants;
import java.util.ArrayList;
import java.util.List;

/**
 * Models a transition operation.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAOperation {
    
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
            case SUBSTITUTE:
                for ( int i = symbolsToPush.size()-1; i >= 0; i-- ) {
                    op += symbolsToPush.get( i );
                }
                break;
            
        }
        
        return String.format( "%c,%c/%s", symbol, top, op );
        
    }
    
    // TODO update (create clone)
    
}
