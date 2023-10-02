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
package br.com.davidbuzatto.yaas.model.pda.examples;

import br.com.davidbuzatto.yaas.model.pda.PDA;
import br.com.davidbuzatto.yaas.model.pda.PDAOperation;
import br.com.davidbuzatto.yaas.model.pda.PDAOperationType;
import br.com.davidbuzatto.yaas.model.pda.PDAState;
import br.com.davidbuzatto.yaas.model.pda.PDATransition;
import br.com.davidbuzatto.yaas.util.CharacterConstants;

/**
 * A set of methods to construct example FAs for testing purposes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAExamples {
    
    public static PDA createPDAPalindrome() {
        
        PDA pda = new PDA();
        
        PDAState q0 = new PDAState( 0, true, false );
        q0.setX1Y1( 150, 300 );
        
        PDAState q1 = new PDAState( 1, false, false );
        q1.setX1Y1( 300, 300 );
        
        PDAState q2 = new PDAState( 2, false, true );
        q2.setX1Y1( 450, 300 );
        
        pda.addState( q0 );
        pda.addState( q1 );
        pda.addState( q2 );
        
        pda.addTransition( new PDATransition( q0, q0, 
                new PDAOperation( '0', '$', PDAOperationType.PUSH, '0' ) ) );
        pda.addTransition( new PDATransition( q0, q0, 
                new PDAOperation( '0', '0', PDAOperationType.PUSH, '0' ) ) );
        pda.addTransition( new PDATransition( q0, q0, 
                new PDAOperation( '0', '1', PDAOperationType.PUSH, '0' ) ) );
        pda.addTransition( new PDATransition( q0, q0, 
                new PDAOperation( '1', '$', PDAOperationType.PUSH, '1' ) ) );
        pda.addTransition( new PDATransition( q0, q0, 
                new PDAOperation( '1', '0', PDAOperationType.PUSH, '1' ) ) );
        pda.addTransition( new PDATransition( q0, q0, 
                new PDAOperation( '1', '1', PDAOperationType.PUSH, '1' ) ) );
        
        // tests
        pda.addTransition( new PDATransition( q0, q0, 
                new PDAOperation( '1', '1', PDAOperationType.PUSH, '1', '2', '3' ) ) );
        pda.addTransition( new PDATransition( q0, q0, 
                new PDAOperation( '1', '1', PDAOperationType.REPLACE, '2', '3' ) ) );
        
        pda.addTransition( new PDATransition( q0, q1, 
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, '$' ) ) );
        pda.addTransition( new PDATransition( q0, q1, 
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, '0' ) ) );
        pda.addTransition( new PDATransition( q0, q1, 
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, '1' ) ) );
        
        pda.addTransition( new PDATransition( q1, q1, 
                PDAOperation.getPopOperation( '0', '0' ) ) );
        pda.addTransition( new PDATransition( q1, q1, 
                PDAOperation.getPopOperation( '1', '1' ) ) );
        
        pda.addTransition( new PDATransition( q1, q2, 
                PDAOperation.getDoNothingOperation( CharacterConstants.EMPTY_STRING, '$' ) ) );
        
        return pda;
        
    }
    
}
