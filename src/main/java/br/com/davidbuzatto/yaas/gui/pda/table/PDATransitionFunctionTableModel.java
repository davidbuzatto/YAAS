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
package br.com.davidbuzatto.yaas.gui.pda.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * A table model for the transition function table of Pushdown Automata.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDATransitionFunctionTableModel implements TableModel {

    private List<String> symbols;
    private List<String> states;
    private List<List<String>> data;
    private boolean partial;
    
    public PDATransitionFunctionTableModel() {
        symbols = new ArrayList<>();
        states = new ArrayList<>();
        data = new ArrayList<>();
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public void setSymbols( List<String> symbols ) {
        this.symbols = symbols;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates( List<String> states ) {
        this.states = states;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData( List<List<String>> data ) {
        this.data = data;
    }
    
    @Override
    public int getRowCount() {
        return states.size();
    }

    @Override
    public int getColumnCount() {
        return symbols.size() + 1;
    }

    @Override
    public Object getValueAt( int rowIndex, int columnIndex ) {
        if ( data.isEmpty() ) {
            return "null";
        }
        return data.get( rowIndex ).get( columnIndex );
    }

    @Override
    public String getColumnName( int columnIndex ) {
        if ( columnIndex == 0 ) {
            return "";
        }
        return symbols.get( columnIndex - 1 );
    }

    @Override
    public Class<?> getColumnClass( int columnIndex ) {
        return String.class;
    }

    @Override
    public boolean isCellEditable( int rowIndex, int columnIndex ) {
        return false;
    }

    @Override
    public void setValueAt( Object aValue, int rowIndex, int columnIndex ) {
        data.get( rowIndex ).set( columnIndex, aValue.toString() );
    }

    @Override
    public void addTableModelListener( TableModelListener l ) {
    }

    @Override
    public void removeTableModelListener( TableModelListener l ) {
    }

    public boolean isPartial() {
        return partial;
    }

    public void setPartial( boolean partial ) {
        this.partial = partial;
    }
    
}
