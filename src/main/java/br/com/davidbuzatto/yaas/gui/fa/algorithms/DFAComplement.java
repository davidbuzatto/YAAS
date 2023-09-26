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

import br.com.davidbuzatto.yaas.gui.fa.FA;
import br.com.davidbuzatto.yaas.gui.fa.FAState;
import br.com.davidbuzatto.yaas.util.DrawingConstants;

/**
 * Performs the COMPLEMENT operation in a Deterministic Finite Automaton
 * generating a new one as result.
 * 
 * This class processes a DFA modifying it, making it the complement of what
 * it was.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DFAComplement {
    
    private final FA generatedFA;
    
    public DFAComplement( FA fa ) throws IllegalArgumentException {
        generatedFA = processIt( fa );
    }

    public FA getGeneratedFA() {
        return generatedFA;
    }
    
    private static FA processIt( FA fa ) {
        return null;
    }
    
    /**
     * Complement a DFA.
     * 
     * @param dfa The dfa to be complemented.
     * @param currentState for state label generation
     */
    public static void processIt( FA dfa, int currentState )
            throws IllegalArgumentException {
        
        // clone first, than totalize
        
        DFATotalTransitionFunction.processIt(
                dfa, 
                currentState, 
                false,
                DrawingConstants.STATE_STROKE_COLOR,
                DrawingConstants.TRANSITION_STROKE_COLOR );
        
        for ( FAState s : dfa.getStates() ) {
            s.setAccepting( !s.isAccepting() );
        }
        
    }
    
}
