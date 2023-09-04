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
package br.com.davidbuzatto.yaas.gui.model;

import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.CubicCurve2D;

/**
 * A generic automaton transition.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Transition extends AbstractForm {

    private State originState;
    private State targetState;
    private char symbol;
    
    private CubicCurve2D curve;
    private Arrow arrow;
    
    private int xPressed;
    private int yPressed;
    
    private int cx;
    private int cy;
    private int c1x;
    private int c1y;
    private int c2x;
    private int c2y;
    
    private int cxOri;
    private int cyOri;
    private int c1xOri;
    private int c1yOri;
    private int c2xOri;
    private int c2yOri;
    
    private boolean ptgDragg;
    private boolean cDragg;
    private boolean c1Dragg;
    private boolean c2Dragg;
    
    private boolean cpMoved;
    private boolean tpMoved;
    private double ptgAngle;
    
    private boolean controlPointsVisible;
    
    public Transition( State originState, State targetState, char symbol ) {
        
        this.originState = originState;
        this.targetState = targetState;
        this.symbol = symbol;
        
        updateStartAndEndPoints();
        
        arrow = new Arrow();
        arrow.setAngle( Math.atan2( y2-c2y, x2-c2x ) );
        arrow.setX1( x2 );
        arrow.setY1( y2 );
        
        curve = new CubicCurve2D.Double( x1, y1, c1x, c1y, c2x, c2y, x2, y2 );
        
    }
    
    public void mouseReleased( MouseEvent evt ) {
        ptgDragg = false;
        cDragg = false;
        c1Dragg = false;
        c2Dragg = false;
    }
    
    public void mouseDragged( MouseEvent evt ) {
        
        updateStartAndEndPoints();
        
        if ( controlPointsVisible ) {
            
            if ( ptgDragg ) {

                tpMoved = true;

                ptgAngle = Math.atan2( 
                        targetState.getY1() - evt.getY(), 
                        targetState.getX1() - evt.getX() );

                x2 = targetState.getX1() - (int) ( Math.cos( ptgAngle ) * Utils.STATE_RADIUS );
                y2 = targetState.getY1() - (int) ( Math.sin( ptgAngle ) * Utils.STATE_RADIUS );

            } else if ( cDragg ) {
                cpMoved = true;
                cx = evt.getX();
                cy = evt.getY();
                c1x = c1xOri + cx-cxOri;
                c1y = c1yOri + cy-cyOri;
                c2x = c2xOri + cx-cxOri;
                c2y = c2yOri + cy-cyOri;
            } else if ( c1Dragg ) {
                cpMoved = true;
                c1x = evt.getX();
                c1y = evt.getY();
            } else if ( c2Dragg ) {
                cpMoved = true;
                c2x = evt.getX();
                c2y = evt.getY();
            }
            
        }

        curve.setCurve( x1, y1, c1x, c1y, c2x, c2y, x2, y2 );

        arrow.setAngle( Math.atan2( y2-c2y, x2-c2x ) );
        arrow.setX1( x2 );
        arrow.setY1( y2 );

    }
    
    public void updateStartAndEndPoints() {
        
        double a = Math.atan2( 
                targetState.getY1() - originState.getY1(), 
                targetState.getX1() - originState.getX1() );
        
        x1 = originState.getX1();
        y1 = originState.getY1();
        
        x2 = targetState.getX1() - (int) ( Math.cos( a ) * Utils.STATE_RADIUS );
        y2 = targetState.getY1() - (int) ( Math.sin( a ) * Utils.STATE_RADIUS );
        
        if ( tpMoved ) {
            x2 = targetState.getX1() - (int) ( Math.cos( ptgAngle ) * Utils.STATE_RADIUS );
            y2 = targetState.getY1() - (int) ( Math.sin( ptgAngle ) * Utils.STATE_RADIUS );
        } 
        
        if ( !cpMoved ) {
            cx = x1 + (x2-x1)/2;
            cy = y1 + (y2-y1)/2;
            c1x = x1 + (x2-x1)/3;
            c1y = y1 + (y2-y1)/3;
            c2x = x2 - (x2-x1)/3;
            c2y = y2 - (y2-y1)/3;
        }
        
    }
    
    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        g2d.setStroke( Utils.TRANSITION_STROKE );
        g2d.setColor( Color.BLACK );
        g2d.draw( curve );
        arrow.draw( g2d );
        
        if ( controlPointsVisible ) {
            
            g2d.setStroke( Utils.TRANSITION_CP_STROKE );
            
            g2d.setColor( Utils.TRANSITION_CP_LEFT_COLOR );
            g2d.drawLine( cx, cy, c1x, c1y );
            
            g2d.setColor( Utils.TRANSITION_CP_RIGHT_COLOR );
            g2d.drawLine( cx, cy, c2x, c2y );
        
            g2d.setColor( Utils.TRANSITION_PTG_COLOR );
            g2d.fillOval( 
                    x2 - Utils.TRANSITION_CP_DIAMETER, 
                    y2 - Utils.TRANSITION_CP_DIAMETER, 
                    Utils.TRANSITION_CP_DIAMETER * 2, 
                    Utils.TRANSITION_CP_DIAMETER * 2 );
            
            g2d.setColor( Utils.TRANSITION_CP_COLOR );
            g2d.fillOval( 
                    cx - Utils.TRANSITION_CP_RADIUS, 
                    cy - Utils.TRANSITION_CP_RADIUS, 
                    Utils.TRANSITION_CP_DIAMETER, 
                    Utils.TRANSITION_CP_DIAMETER );
            
            g2d.setColor( Utils.TRANSITION_CP_LEFT_COLOR );
            g2d.fillOval( 
                    c1x - Utils.TRANSITION_CP_RADIUS, 
                    c1y - Utils.TRANSITION_CP_RADIUS, 
                    Utils.TRANSITION_CP_DIAMETER, 
                    Utils.TRANSITION_CP_DIAMETER );
            
            g2d.setColor( Utils.TRANSITION_CP_RIGHT_COLOR );
            g2d.fillOval( 
                    c2x - Utils.TRANSITION_CP_RADIUS, 
                    c2y - Utils.TRANSITION_CP_RADIUS, 
                    Utils.TRANSITION_CP_DIAMETER, 
                    Utils.TRANSITION_CP_DIAMETER );
            
        }
        
        g2d.dispose();
        
    }

    @Override
    public boolean intercepts( int x, int y ) {
        
        xPressed = x;
        yPressed = y;
        
        int ptgx = xPressed - x2;
        int ptgy = yPressed - y2;

        int cxc = xPressed - cx;
        int cyc = yPressed - cy;
        int c1xc = xPressed - c1x;
        int c1yc = yPressed - c1y;
        int c2xc = xPressed - c2x;
        int c2yc = yPressed - c2y;

        cxOri = cx;
        cyOri = cy;
        c1xOri = c1x;
        c1yOri = c1y;
        c2xOri = c2x;
        c2yOri = c2y;

        if ( ptgx * ptgx + ptgy * ptgy <= Utils.TRANSITION_CP_RADIUS_EQUARED * 4 ) {
            ptgDragg = true;
        } else if ( cxc * cxc + cyc * cyc <= Utils.TRANSITION_CP_RADIUS_EQUARED ) {
            cDragg = true;
        } else if ( c1xc * c1xc + c1yc * c1yc <= Utils.TRANSITION_CP_RADIUS_EQUARED ) {
            c1Dragg = true;
        } else if ( c2xc * c2xc + c2yc * c2yc <= Utils.TRANSITION_CP_RADIUS_EQUARED ) {
            c2Dragg = true;
        }
        
        return ptgDragg || cDragg || c1Dragg || c2Dragg;
        
    }

    public State getOriginState() {
        return originState;
    }

    public void setOriginState( State originState ) {
        this.originState = originState;
    }

    public State getTargetState() {
        return targetState;
    }

    public void setTargetState( State targetState ) {
        this.targetState = targetState;
    }
    
    public char getSymbol() {
        return symbol;
    }

    public void setSymbol( char symbol ) {
        this.symbol = symbol;
    }

    public boolean isControlPointsVisible() {
        return controlPointsVisible;
    }

    public void setControlPointsVisible( boolean controlPointsVisible ) {
        this.controlPointsVisible = controlPointsVisible;
    }
    
}
