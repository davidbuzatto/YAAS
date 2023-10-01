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
package br.com.davidbuzatto.yaas.gui.pda;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Encapsulates data related to a step in a pertinence simulation of a Pushdown
 * Automaton.
 * TODO needs to be refactored with FASimulationStep
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDASimulationStep {
    
    private List<PDAState> activeStates;
    private Character processadSymbol;
    
    public PDASimulationStep( Set<PDAState> activeStates, Character processedSymbol ) {
        this.activeStates = new ArrayList<>();
        this.activeStates.addAll( activeStates );
        this.processadSymbol = processedSymbol;
    }
    
    public void activateInFA( PDA pda ) {
        
        pda.deactivateAllStatesInSimulation();
        
        for ( PDAState s : activeStates ) {
            s.setActiveInSimulation( true );
        }
        
    }
    
}
