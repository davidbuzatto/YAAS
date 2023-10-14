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

/**
 * The type of a Turing Machne.
 * 
 * @author Prof. Dr. David Buzatto
 */
public enum TMType {
    
    EMPTY( "Empty", "Empty" ),
    TM( "TM", "Turing Machine" ),
    DTM( "DTM", "Deterministic Turing Machine" ),
    NTM( "NTM", "Nondeterministic Turing Machine" );
    
    private final String acronym;
    private final String description;
    
    TMType( String acronym, String description ) {
        this.acronym = acronym;
        this.description = description;
    }

    public String getAcronym() {
        return acronym;
    }
    
    public String getDescription() {
        return description;
    }
    
}
