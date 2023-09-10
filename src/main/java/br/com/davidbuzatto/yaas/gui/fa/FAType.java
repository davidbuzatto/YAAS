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
package br.com.davidbuzatto.yaas.gui.fa;

import br.com.davidbuzatto.yaas.util.CharacterConstants;

/**
 * The type a Finite Automaton
 * 
 * @author Prof. Dr. David Buzatto
 */
public enum FAType {
    
    EMPTY( "Empty" ),
    DFA( "Deterministic Finite Automaton" ),
    NFA( "Nondeterministic Finite Automaton" ),
    ENFA( "Finite Automaton with " + CharacterConstants.SMALL_EPSILON + "-transitions" ),
    GNFA( "Generalized Nondeterministic Finite Automaton" );
    
    private final String description;
    
    FAType( String description ) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
}
