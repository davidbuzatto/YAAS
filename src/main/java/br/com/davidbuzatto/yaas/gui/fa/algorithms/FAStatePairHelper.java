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

import br.com.davidbuzatto.yaas.gui.fa.FAState;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A helper class that encapsulates a pair os states while the automaton is
 * being processed.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAStatePairHelper {
    
    FAState s1;
    FAState s2;
    Set<FAState> ss;

    public FAStatePairHelper( FAState s1, FAState s2 ) {
        this.s1 = s1;
        this.s2 = s2;
        this.ss = new HashSet<>();
        this.ss.add( s1 );
        this.ss.add( s2 );
    }

    @Override
    public String toString() {
        return "FAStatePair{" + s1 + ", " + s2 + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode( this.ss );
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
        final FAStatePairHelper other = (FAStatePairHelper) obj;
        return Objects.equals( this.ss, other.ss );
    }
        
}
