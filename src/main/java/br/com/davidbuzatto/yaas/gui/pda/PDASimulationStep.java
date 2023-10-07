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

import br.com.davidbuzatto.yaas.model.pda.PDA;
import br.com.davidbuzatto.yaas.model.pda.PDAID;

/**
 * Encapsulates data related to a step in a pertinence simulation of a Pushdown
 * Automaton.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDASimulationStep {
    
    private PDAID id;
    
    public PDASimulationStep( PDAID id ) {
        this.id = id;
    }
    
    public void activateInPDA( PDA pda ) {
        pda.deactivateAllStatesInSimulation();
        id.getState().setActiveInSimulation( true );
        id.setActiveInSimulation( true );
    }

    public PDAID getId() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
}
