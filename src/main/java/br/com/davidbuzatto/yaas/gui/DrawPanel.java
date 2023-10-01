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

import br.com.davidbuzatto.yaas.gui.model.Arrow;
import br.com.davidbuzatto.yaas.gui.fa.FA;
import br.com.davidbuzatto.yaas.gui.fa.FASimulationStep;
import br.com.davidbuzatto.yaas.gui.pda.PDA;
import br.com.davidbuzatto.yaas.util.DrawingConstants;
import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.LineMetrics;
import java.util.List;
import javax.swing.JPanel;

/**
 * Drawing panel for automaton construction and test.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DrawPanel extends JPanel {

    private FA fa;
    private PDA pda;
    
    private boolean showGrid;
    private boolean drawingTempTransition;
    
    private final Arrow tempTransitionArrow;
    private int tempTransitionX1;
    private int tempTransitionY1;
    private int tempTransitionX2;
    private int tempTransitionY2;
    
    private Rectangle selectionRectangle;
    
    private String simulationString;
    private List<FASimulationStep> simulationSteps;
    private int currentSimulationStep;
    private boolean simulationAccepted;
    
    public DrawPanel() {
        tempTransitionArrow = new Arrow();
        tempTransitionArrow.setStroke( DrawingConstants.TRANSITION_STROKE );
    }
    
    @Override
    protected void paintComponent( Graphics g ) {
        
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON );
        
        g2d.setColor( Color.WHITE );
        g2d.fillRect( 0, 0, getWidth(), getHeight() );
        
        if ( showGrid ) {
            g2d.setColor( DrawingConstants.GRID_COLOR );
            for ( int i = 0; i <= getHeight(); i += DrawingConstants.STATE_RADIUS ) {
                g2d.drawLine( 0, i, getWidth(), i );
            }
            for ( int i = 0; i <= getWidth(); i += DrawingConstants.STATE_RADIUS ) {
                g2d.drawLine( i, 0, i, getHeight() );
            }
        }
        
        if ( fa != null ) {
            
            fa.draw( g2d );
        
            if ( drawingTempTransition ) {

                g2d.setStroke( DrawingConstants.TEMP_TRANSITION_STROKE.getBasicStroke() );
                g2d.setColor( DrawingConstants.TEMP_TRANSITION_COLOR );

                g2d.drawLine( 
                        tempTransitionX1, tempTransitionY1, 
                        tempTransitionX2, tempTransitionY2 );

                tempTransitionArrow.setX1( tempTransitionX2 );
                tempTransitionArrow.setY1( tempTransitionY2 );
                tempTransitionArrow.setStrokeColor( DrawingConstants.TEMP_TRANSITION_COLOR );
                tempTransitionArrow.setAngle( Math.atan2( 
                        tempTransitionY2 - tempTransitionY1, 
                        tempTransitionX2 - tempTransitionX1 ) );
                tempTransitionArrow.draw( g2d );

            }
            
        }
        
        // TODO update
        if ( pda != null ) {
            pda.draw( g2d );
        }
        
        if ( simulationString != null && simulationSteps != null ) {
            
            Rectangle r = getVisibleRect();
            int x1R = (int) r.getX();
            int y1R = (int) r.getY();
            int x2R = (int) ( r.getX() + r.getWidth() );
            int y2R = (int) ( r.getY() + r.getHeight() );
            int half = x1R + ( x2R - x1R ) / 2;
        
            g2d.setFont( DrawingConstants.SIMULATION_STRING_FONT );
            
            FontMetrics fm = g2d.getFontMetrics();
            LineMetrics lm = Utils.getLineMetrics( simulationString, g2d.getFont() );
            
            int start = half - fm.stringWidth( simulationString ) / 2;
            int inc = fm.stringWidth( "0" );
            int height = (int) lm.getHeight();
            int ySimulationString = y2R - height / 2 - 10;
            char[] cs = simulationString.toCharArray();
            int i;
            
            for ( i = 0; i < cs.length; i++ ) {
            
                if ( i == currentSimulationStep ) {
                    g2d.setColor(DrawingConstants.SYMBOL_ACTIVE_IN_SIMULATION_BACKGROUND_COLOR );
                    g2d.fillRoundRect( start + inc * i - 2, 
                            ySimulationString - height / 2 - 4, 
                            inc + 3, 
                            height / 2 + 8, 10, 10 );
                    g2d.setColor(DrawingConstants.SYMBOL_ACTIVE_IN_SIMULATION_COLOR );
                    g2d.drawRoundRect( start + inc * i - 2, 
                            ySimulationString - height / 2 - 4, 
                            inc + 3, 
                            height / 2 + 8, 10, 10 );
                } else if ( i < currentSimulationStep ) {
                    g2d.setColor( DrawingConstants.SIMULATION_STRING_PROCESSED_COLOR );
                } else {
                    g2d.setColor( DrawingConstants.SIMULATION_STRING_NON_PROCESSED_COLOR );
                }
                
                g2d.drawString( String.valueOf( cs[i] ), 
                        start + inc * i, 
                        ySimulationString );
                
            }
            
            if ( currentSimulationStep == simulationSteps.size() - 1 ) {
                if ( simulationAccepted ) {
                    g2d.setColor( DrawingConstants.ACCEPTED_SIMULATION_RESULT_COLOR );
                    g2d.drawString( " ACCEPTED", start + inc * i, ySimulationString );
                } else {
                    g2d.setColor( DrawingConstants.REJECTED_SIMULATION_RESULT_COLOR );
                    g2d.drawString( " REJECTED", start + inc * i, ySimulationString );
                }
            }
            
        }
        
        if ( selectionRectangle != null ) {
            
            g2d.setColor( DrawingConstants.SELECTION_RETANGLE_FILL_COLOR );
            g2d.fillRect( 
                    selectionRectangle.x, selectionRectangle.y, 
                    selectionRectangle.width, selectionRectangle.height );
            
            g2d.setStroke( DrawingConstants.SELECTION_RETANGLE_STROKE.getBasicStroke() );
            g2d.setColor( DrawingConstants.SELECTION_RETANGLE_STROKE_COLOR );
            g2d.drawRect( 
                    selectionRectangle.x, selectionRectangle.y, 
                    selectionRectangle.width, selectionRectangle.height );

        }
        
        g2d.setStroke( DrawingConstants.DRAW_PANEL_STROKE.getBasicStroke() );
        g2d.setColor( Color.BLACK );
        g2d.drawRect( 0, 0, getWidth(), getHeight() );
        
        g2d.dispose();
        
    }

    public void setFa( FA fa ) {
        this.fa = fa;
    }

    public void setPda( PDA pda ) {
        this.pda = pda;
    }

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid( boolean showGrid ) {
        this.showGrid = showGrid;
    }

    public void setDrawingTempTransition( boolean drawingTempTransition ) {
        this.drawingTempTransition = drawingTempTransition;
    }

    public void setTempTransitionX1( int tempTransitionX1 ) {
        this.tempTransitionX1 = tempTransitionX1;
    }

    public void setTempTransitionY1( int tempTransitionY1 ) {
        this.tempTransitionY1 = tempTransitionY1;
    }

    public void setTempTransitionX2( int tempTransitionX2 ) {
        this.tempTransitionX2 = tempTransitionX2;
    }

    public void setTempTransitionY2( int tempTransitionY2 ) {
        this.tempTransitionY2 = tempTransitionY2;
    }

    public void setSelectionRectangle( Rectangle selectionRectangle ) {
        this.selectionRectangle = selectionRectangle;
    }

    public void setSimulationString( String simulationString ) {
        this.simulationString = simulationString;
    }

    public void setSimulationSteps( List<FASimulationStep> simulationSteps ) {
        this.simulationSteps = simulationSteps;
    }

    public void setCurrentSimulationStep( int currentSimulationStep ) {
        this.currentSimulationStep = currentSimulationStep;
    }

    public void setSimulationAccepted( boolean simulationAccepted ) {
        this.simulationAccepted = simulationAccepted;
    }

    public boolean isSimulationAccepted() {
        return simulationAccepted;
    }
    
}
