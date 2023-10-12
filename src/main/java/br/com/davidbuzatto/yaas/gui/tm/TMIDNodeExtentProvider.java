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
package br.com.davidbuzatto.yaas.gui.tm;

import br.com.davidbuzatto.yaas.model.tm.TMID;
import org.abego.treelayout.NodeExtentProvider;

/**
 * Node extent provider for TMAIDs.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TMIDNodeExtentProvider implements NodeExtentProvider<TMID>{

    @Override
    public double getWidth( TMID tn ) {
        return tn.getTextWidth();
    }

    @Override
    public double getHeight( TMID tn ) {
        return tn.getTextHeight();
    }
    
}
