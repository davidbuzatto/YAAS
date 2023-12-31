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

import br.com.davidbuzatto.yaas.util.DrawingConstants;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * A table cell renderer for the transition function table of Pushdown Automata.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDATransitionFunctionTableCellRenderer implements TableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent( 
            JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column ) {
        
        JLabel lbl = new JLabel( value.toString() );
        lbl.setFont( DrawingConstants.DEFAULT_TABLE_FONT );
        lbl.setHorizontalAlignment( JLabel.CENTER );
        
        return lbl;
        
    }
    
}
