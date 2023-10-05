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
package br.com.davidbuzatto.yaas.model.pda;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * This class models Pushdown Automata Instantaneous Descriptios (IDs).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAID {
    
    private PDAState state;
    private String string;
    private Deque<Character> stack;
    
    private PDAID parent;
    private List<PDAID> children;
    
    public PDAID( PDAState state, String string, Deque<Character> stack, PDAID parent ) {
        this.state = state;
        this.string = string;
        this.stack = stack;
        this.parent = parent;
        this.children = new ArrayList<>();
    }
    
    public void addChild( PDAID child ) {
        children.add( child );
        child.parent = this;
    }

    @Override
    public String toString() {
        String sStr = "";
        for ( char c : stack ) {
            sStr += c;
        }
        return String.format( "(%s, %s, %s)", state, string, sStr );
    }
    
}
