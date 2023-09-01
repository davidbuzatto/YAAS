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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.davidbuzatto.yaas.gui;

import br.com.davidbuzatto.yaas.gui.model.AbstractForm;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Drawing panel for automaton construction and test.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DrawPanel extends JPanel {

    private List<AbstractForm> forms;
    
    public DrawPanel() {
        forms = new ArrayList<>();
    }
    
    @Override
    protected void paintComponent( Graphics g ) {
        
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON );
        
        g2d.setColor( Color.WHITE );
        g2d.fillRect( 0, 0, getWidth(), getHeight() );
        
        for ( AbstractForm f : forms ) {
            f.draw( g2d );
        }
        
        g2d.dispose();
        
    }
    
    public void moveForms( int xOffset, int yOffset ) {
        
        for ( AbstractForm form : forms ) {
            form.setX1( form.getX1() + xOffset );
            form.setY1( form.getY1() + yOffset );
        }
        
    }
    
    public AbstractForm getFormAt( int x, int y ) {
        
        for ( AbstractForm form : forms ) {
            if ( form.intercepts( x, y ) ) {
                return form;
            }
        }
        
        return null;
        
    }
    
    public void addForm( AbstractForm form ) {
        if ( form != null ) {
            forms.add( form );
        }
    }
    
}
