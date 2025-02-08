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
package br.com.davidbuzatto.yaas.model.tm.algorithms;

import br.com.davidbuzatto.yaas.model.tm.TM;
import br.com.davidbuzatto.yaas.model.tm.TMState;
import br.com.davidbuzatto.yaas.util.Utils;

/**
 * Common algorithms for reuse.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TMCommon {
    
    private static final boolean DEBUG = Boolean.parseBoolean( 
            Utils.getMavenModel().getProperties().getProperty( "debugAlgorithms" ) );
    
    /**
     * Validades if a Turing Machine have an initial state.
     * 
     * @param tm The Turing Machine
     * @throws IllegalArgumentException If the Turing Machine don't have an
     * initial state.
     */
    public static void validateInitialState( TM tm ) throws IllegalArgumentException {
        
        if ( tm.getInitialState() == null ) {
            throw new IllegalArgumentException( 
                    "The Turing Machine must have an initial state!" );
        }
        
    }
    
    /**
     * Validades if both Turing Machines have an initial state.
     * 
     * @param tm1 One Turing Machine
     * @param tm2 Another Turing Machine
     * @throws IllegalArgumentException If at least one of the Turing Machine
     * don't have an initial state.
     */
    public static void validateInitialState( TM tm1, TM tm2 ) throws IllegalArgumentException {
        
        if ( tm1.getInitialState() == null ) {
            throw new IllegalArgumentException( 
                    "The first Turing Machine must have an initial state!" );
        }
        
        if ( tm2.getInitialState() == null ) {
            throw new IllegalArgumentException( 
                    "The second Turing Machine must have an initial state!" );
        }
        
    }
    
    /**
     * Validades if a Turing Machine have at least one final state.
     * 
     * @param tm The Turing Machine
     * @throws IllegalArgumentException If the Turing Machine don't have at
     * least one final state.
     */
    public static void validateFinalStates( TM tm ) throws IllegalArgumentException {
        
        if ( tm.getFinalStates().isEmpty() ) {
            throw new IllegalArgumentException( 
                    "The Turing Machine must have at least one final state!" );
        }
        
    }
    
    /**
     * Validades if both Turing Machines have at least one final state.
     * 
     * @param tm1 One Turing Machine
     * @param tm2 Another Turing Machine
     * @throws IllegalArgumentException If at least one of the Turing Machine
     * don't have at least one final state.
     */
    public static void validateFinalStates( TM tm1, TM tm2 ) throws IllegalArgumentException {
        
        if ( tm1.getFinalStates().isEmpty() ) {
            throw new IllegalArgumentException( 
                    "The first Turing Machine must have at least one final state!" );
        }
        
        if ( tm2.getFinalStates().isEmpty() ) {
            throw new IllegalArgumentException( 
                    "The second Turing Machine must have at least one final state!" );
        }
        
    }
    
    /**
     * Set the states number in order. The initial state is the state with
     * number 0. The other will reenumerate in appearance order inside the state
     * list.
     * 
     * @param tm The Pushdown Automaon with the states to be processed.
     */
    public static void reenumerateStates( TM tm ) {
        
        int currentState = 1;
        for ( TMState s : tm.getStates() ) {
            if ( s.isInitial() ) {
                s.setNumber( 0 );
            } else {
                s.setNumber( currentState++ );
            }
        }
        
    }
    
}
