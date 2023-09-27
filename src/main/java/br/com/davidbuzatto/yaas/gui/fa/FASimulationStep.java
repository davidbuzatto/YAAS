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
package br.com.davidbuzatto.yaas.gui.fa;

import br.com.davidbuzatto.yaas.gui.fa.FA;
import br.com.davidbuzatto.yaas.gui.fa.FAState;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Encapsulates data related to a step in a pertinence simulation.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FASimulationStep {
    
    private List<FAState> activeStates;
    private Character processadSymbol;
    
    public FASimulationStep( Set<FAState> activeStates, Character processedSymbol ) {
        this.activeStates = new ArrayList<>();
        this.activeStates.addAll( activeStates );
        this.processadSymbol = processedSymbol;
    }
    
    public void activateInFA( FA fa ) {
        
        fa.deactivateAllStatesInSimulation();
        
        for ( FAState s : activeStates ) {
            s.setActiveInSimulation( true );
        }
        
    }
    
}
