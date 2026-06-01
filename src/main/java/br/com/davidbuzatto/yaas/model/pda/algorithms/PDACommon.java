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
package br.com.davidbuzatto.yaas.model.pda.algorithms;

import br.com.davidbuzatto.yaas.model.pda.PDA;
import br.com.davidbuzatto.yaas.model.pda.PDAState;
import br.com.davidbuzatto.yaas.util.Utils;
import java.util.Set;

/**
 * Common algorithms for reuse.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDACommon {
    
    private static final boolean DEBUG = Boolean.parseBoolean( 
            Utils.getMavenModel().getProperties().getProperty( "debugAlgorithms" ) );
    
    /**
     * Validades if a Pushdown Automaton have an initial state.
     * 
     * @param pda The Pushdown Automaton
     * @throws IllegalArgumentException If the Pushdown Automaton don't have an
     * initial state.
     */
    public static void validateInitialState( PDA pda ) throws IllegalArgumentException {
        
        if ( pda.getInitialState() == null ) {
            throw new IllegalArgumentException( 
                    "The Pushdown Automaton must have an initial state!" );
        }
        
    }
    
    /**
     * Validades if both Pushdown Automata have an initial state.
     * 
     * @param pda1 One Pushdown Automaton
     * @param pda2 Another Pushdown Automaton
     * @throws IllegalArgumentException If at least one of the Pushdown Automata
     * don't have an initial state.
     */
    public static void validateInitialState( PDA pda1, PDA pda2 ) throws IllegalArgumentException {
        
        if ( pda1.getInitialState() == null ) {
            throw new IllegalArgumentException( 
                    "The first Pushdown Automaton must have an initial state!" );
        }
        
        if ( pda2.getInitialState() == null ) {
            throw new IllegalArgumentException( 
                    "The second Pushdown Automaton must have an initial state!" );
        }
        
    }
    
    /**
     * Validades if a Pushdown Automaton have at least one final state.
     * 
     * @param pda The Pushdown Automaton
     * @throws IllegalArgumentException If the Pushdown Automata don't have at
     * least one final state.
     */
    public static void validateFinalStates( PDA pda ) throws IllegalArgumentException {
        
        if ( pda.getFinalStates().isEmpty() ) {
            throw new IllegalArgumentException( 
                    "The Pushdown Automaton must have at least one final state!" );
        }
        
    }
    
    /**
     * Validades if both Pushdown Automata have at least one final state.
     * 
     * @param pda1 One Pushdown Automaton
     * @param pda2 Another Pushdown Automaton
     * @throws IllegalArgumentException If at least one of the Pushdown Automata
     * don't have at least one final state.
     */
    public static void validateFinalStates( PDA pda1, PDA pda2 ) throws IllegalArgumentException {
        
        if ( pda1.getFinalStates().isEmpty() ) {
            throw new IllegalArgumentException( 
                    "The first Pushdown Automaton must have at least one final state!" );
        }
        
        if ( pda2.getFinalStates().isEmpty() ) {
            throw new IllegalArgumentException( 
                    "The second Pushdown Automaton must have at least one final state!" );
        }
        
    }
    
    /**
     * Chooses a fresh stack symbol to be used as the new bottom-of-stack marker
     * (X0 in Hopcroft's notation) when converting between acceptance criteria.
     * By definition X0 must not belong to the original stack alphabet. 'X' is
     * preferred to keep consistency with the textbook; if it is already in use,
     * the next free uppercase letter is returned.
     *
     * @param pda The Pushdown Automaton whose stack alphabet must be avoided.
     * @return A character that is not present in the stack alphabet.
     * @throws IllegalArgumentException If no free symbol could be found.
     */
    public static char chooseNewStackStartingSymbol( PDA pda )
            throws IllegalArgumentException {

        Set<Character> stackAlphabet = pda.getStackAlphabet();

        // 'X' (X0) is preferred to match the textbook notation
        if ( !stackAlphabet.contains( 'X' ) ) {
            return 'X';
        }

        // 'X' is already used as a working symbol, so the next free uppercase
        // letter is chosen as the fresh bottom-of-stack marker
        for ( char c = 'A'; c <= 'Z'; c++ ) {
            if ( !stackAlphabet.contains( c ) ) {
                return c;
            }
        }

        // extremely unlikely: the whole A-Z range is already in use
        throw new IllegalArgumentException(
                "Could not find a fresh stack symbol for the conversion!" );

    }

    /**
     * Set the states number in order. The initial state is the state with
     * number 0. The other will reenumerate in appearance order inside the state
     * list.
     *
     * @param pda The Pushdown Automaon with the states to be processed.
     */
    public static void reenumerateStates( PDA pda ) {
        
        int currentState = 1;
        for ( PDAState s : pda.getStates() ) {
            if ( s.isInitial() ) {
                s.setNumber( 0 );
            } else {
                s.setNumber( currentState++ );
            }
        }
        
    }
    
}
