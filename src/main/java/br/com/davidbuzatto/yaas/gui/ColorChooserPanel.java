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
package br.com.davidbuzatto.yaas.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * A color chooser component.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ColorChooserPanel extends JPanel {
    
    private static final Color DEFAULT_COLOR_CHOOSER_COLOR = Color.BLACK;
    private static final String DEFAULT_COLOR_CHOOSER_TITLE = "Color Chooser";
    
    private Color color;
    private String colorChooserTitle;
    
    private boolean mouseHover;
    
    public ColorChooserPanel() {
        this( DEFAULT_COLOR_CHOOSER_COLOR, DEFAULT_COLOR_CHOOSER_TITLE );
    }
    
    public ColorChooserPanel( Color color ) {
        this( color, DEFAULT_COLOR_CHOOSER_TITLE );
    }
    
    public ColorChooserPanel( String title ) {
        this( DEFAULT_COLOR_CHOOSER_COLOR, title );
    }
    
    public ColorChooserPanel( Color color, String colorChooserTitle ) {
        
        this.color = color;
        this.colorChooserTitle = colorChooserTitle;
    
        setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
        setFocusable( true );
        Dimension d = new Dimension( 26, 26 );
        setMinimumSize( d );
        setMaximumSize( d );
        setPreferredSize( d );
        
        addMouseListener( new MouseAdapter() {
            
            @Override
            public void mouseClicked( MouseEvent e ) {
                requestFocus();
            }

            @Override
            public void mousePressed( MouseEvent e ) {
                requestFocus();
            }
            
            @Override
            public void mouseEntered( MouseEvent evt ) {
                mouseHover = true;
                repaint();
            }
            
            @Override
            public void mouseExited( MouseEvent evt ) {
                mouseHover = false;
                repaint();
            }
            
        });
        
        addFocusListener( new FocusAdapter() {
            @Override
            public void focusGained( FocusEvent e ) {
                repaint();
            }
            @Override
            public void focusLost( FocusEvent e ) {
                repaint();
            }
        });
        
    }

    @Override
    protected void paintComponent( Graphics g ) {
        
        super.paintComponent( g );
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint( 
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON );
        
        if ( hasFocus() ) {
            g2d.setColor( UIManager.getColor( "Button.default.focusColor" ) );
            g2d.fillRoundRect( 0, 0, getWidth()-1, getHeight()-1, 5, 5 );
        }
        
        if ( mouseHover ) {
            g2d.setColor( UIManager.getColor( "Button.hoverBorderColor" ) );
        } else if ( hasFocus() ) {
            g2d.setColor( UIManager.getColor( "Button.focusedBorderColor" ) );
        } else {
            g2d.setColor( UIManager.getColor( "Button.borderColor" ) );
        }
        g2d.fillRoundRect( 2, 2, getWidth()-5, getHeight()-5, 5, 5 );
        
        g2d.setColor( color );
        g2d.fillRoundRect( 3, 3, getWidth()-7, getHeight()-7, 5, 5 );
        
        g2d.dispose();
        
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor( Color color ) {
        this.color = color;
    }

    public String getColorChooserTitle() {
        return colorChooserTitle;
    }

    public void setColorChooserTitle( String colorChooserTitle ) {
        this.colorChooserTitle = colorChooserTitle;
    }
    
}
