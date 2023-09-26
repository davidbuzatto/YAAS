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
package br.com.davidbuzatto.yaas.gui.fa.algorithms;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A helper class that encapsulates data from existing transitions while the
 * automaton is being processed.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TransitionHelper {
    
    StateHelper originState;
    StateHelper targetState;
    Set<Character> symbols;

    public TransitionHelper( StateHelper originState, StateHelper targetState, Character symbol ) {
        this.originState = originState;
        this.targetState = targetState;
        this.symbols = new HashSet<>();
        this.symbols.add( symbol );
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode( this.originState );
        hash = 97 * hash + Objects.hashCode( this.targetState );
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
        final TransitionHelper other = (TransitionHelper) obj;
        if ( !Objects.equals( this.originState, other.originState ) ) {
            return false;
        }
        return Objects.equals( this.targetState, other.targetState );
    }

    @Override
    public String toString() {
        return String.format( "(%s) - %s -> (%s)",
            originState, symbols, targetState );
    }
        
}
