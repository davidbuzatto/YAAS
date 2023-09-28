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
import br.com.davidbuzatto.yaas.gui.fa.FAState;
import static br.com.davidbuzatto.yaas.gui.fa.algorithms.FAAlgorithmsConstants.DEBUG;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a new FA without useless and inaccessible states.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FARemoveUselessAndInaccessibleStates {
    
    private final FA generatedFA;
    
    public FARemoveUselessAndInaccessibleStates( FA fa ) throws IllegalArgumentException {
        generatedFA = processIt( fa );
    }

    public FA getGeneratedFA() {
        return generatedFA;
    }
    
    @SuppressWarnings( "unchecked" )
    private static FA processIt( FA fa )
            throws IllegalArgumentException {
        
        try {
            
            /*FACommon.validateInitialState( fa );
            FACommon.validateAcceptingStates( fa );*/

            fa = (FA) fa.clone();
            
            fa.deactivateAllStatesInSimulation();
            fa.deselectAll();
            
            List<FAState> uselessStates = new ArrayList<>();
            List<FAState> inaccessibleStates = new ArrayList<>();
            
            while ( true ) {
                
                int statesQuantity = fa.getStates().size();
                
                for ( FAState s : fa.getStates() ) {
                    if ( FACommon.isUseless( s, fa ) ) {
                        uselessStates.add( s );
                    }
                    if ( FACommon.isInaccessible( s, fa ) ) {
                        inaccessibleStates.add( s );
                    }
                }

                if ( DEBUG ) {
                    System.out.println( "    Useless states: " + uselessStates );
                    System.out.println( "    Inacessible states: " + inaccessibleStates );
                }
                
                for ( FAState s : uselessStates ) {
                    fa.removeState( s );
                }

                for ( FAState s : inaccessibleStates ) {
                    fa.removeState( s );
                }
                
                uselessStates.clear();
                inaccessibleStates.clear();
                
                if ( statesQuantity == fa.getStates().size() ) {
                    break;
                }
                
            }
            
            FACommon.reenumerateStates( fa );
            fa.resetTransitionsTransformations();
            
            return fa;
            
        } catch ( CloneNotSupportedException exc ) {
            // should never be reached
            exc.printStackTrace();
        }
        
        return null;
        
    }
    
}
