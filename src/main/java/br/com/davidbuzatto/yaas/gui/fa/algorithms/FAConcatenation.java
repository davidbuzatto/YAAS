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
package br.com.davidbuzatto.yaas.gui.fa.algorithms;

import br.com.davidbuzatto.yaas.gui.fa.FA;

/**
 * Performs the CONCATENATION operation between two Finite Automata generating
 * a new one as result.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAConcatenation {
    
    private final FA generatedFA;
    
    public FAConcatenation( FA fa1, FA fa2 ) throws IllegalArgumentException {
        generatedFA = processIt( fa1, fa2 );
    }

    public FA getGeneratedFA() {
        return generatedFA;
    }
    
    private static FA processIt( FA fa1, FA fa2 ) {
        return null;
    }
    
}
