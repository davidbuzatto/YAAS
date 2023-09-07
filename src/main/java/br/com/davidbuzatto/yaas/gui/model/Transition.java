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
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * A generic automaton transition.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Transition extends AbstractForm {

    private State originState;
    private State targetState;
    private List<Character> symbols;
    
    
    private TransitionLabel label;
    private final CubicCurve2D curve;
    private final Arrow arrow;
    
    private final ControlPoint targetCP;
    private final ControlPoint centralCP;
    private final ControlPoint leftCP;
    private final ControlPoint rightCP;
    
    private final Stroke cpStroke;
    
    
    private int xOffset;
    private int yOffset;
    
    private boolean labelDragging;
    private boolean labelMoved;
    
    
    private int prevCentralCPX;
    private int prevCentralCPY;
    private int prevLeftCPX;
    private int prevLeftCPY;
    private int prevRightCPX;
    private int prevRightCPY;
    
    private boolean targetCPDragging;
    private boolean centralCPDragging;
    private boolean leftCPDragging;
    private boolean rightCPDragging;
    
    private boolean centralCPMoved;
    private boolean targetCPMoved;
    
    private double targetCPAngle;
    
    private boolean controlPointsVisible;
    
    
    public Transition( State originState, State targetState, char symbol ) {
        
        this.originState = originState;
        this.targetState = targetState;
        
        symbols = new ArrayList<>();
        symbols.add( symbol );
        
        label = new TransitionLabel();
        label.setFont( Utils.DEFAULT_FONT );
        label.setStrokeColor( strokeColor );
        updateLabel();
        
        font = Utils.DEFAULT_FONT;
        stroke = Utils.TRANSITION_STROKE;
        cpStroke = Utils.TRANSITION_CP_STROKE;
        
        targetCP = new ControlPoint();
        targetCP.setRadius( Utils.TRANSITION_PTG_RADIUS );
        targetCP.setFillColor( Utils.TRANSITION_PTG_COLOR );
        
        centralCP = new ControlPoint();
        centralCP.setRadius( Utils.TRANSITION_CP_RADIUS );
        centralCP.setFillColor( Utils.TRANSITION_CP_COLOR );
        
        leftCP = new ControlPoint();
        leftCP.setRadius( Utils.TRANSITION_CP_RADIUS );
        leftCP.setFillColor( Utils.TRANSITION_CP_LEFT_COLOR );
        
        rightCP = new ControlPoint();
        rightCP.setRadius( Utils.TRANSITION_CP_RADIUS );
        rightCP.setFillColor( Utils.TRANSITION_CP_RIGHT_COLOR );
        
        updateStartAndEndPoints();
        
        arrow = new Arrow();
        arrow.setAngle( Math.atan2( y2 - rightCP.getY1(), x2 - rightCP.getX1() ) );
        arrow.setX1( x2 );
        arrow.setY1( y2 );
        
        curve = new CubicCurve2D.Double( 
                x1, y1, 
                leftCP.getX1(), leftCP.getY1(), 
                rightCP.getX1(), rightCP.getY1(), 
                x2, y2 );
        
    }
    
    public void mouseReleased( MouseEvent evt ) {
        targetCPDragging = false;
        centralCPDragging = false;
        leftCPDragging = false;
        rightCPDragging = false;
        labelDragging = false;
    }
    
    public void mouseDragged( MouseEvent evt ) {
        
        updateStartAndEndPoints();
        
        if ( controlPointsVisible ) {
            
            if ( targetCPDragging ) {

                targetCPMoved = true;

                if ( originState == targetState ) {
                    
                    targetCPAngle = Math.atan2( 
                            targetState.getY1() - evt.getY(), 
                            targetState.getX1() - evt.getX() ) 
                            - Math.toRadians( 60 );
                    
                } else {
                    
                    targetCPAngle = Math.atan2( 
                            targetState.getY1() - evt.getY(), 
                            targetState.getX1() - evt.getX() );

                    x2 = targetState.getX1() - (int) ( Math.cos( targetCPAngle ) * 
                            targetState.getRadius() );
                    y2 = targetState.getY1() - (int) ( Math.sin( targetCPAngle ) * 
                            targetState.getRadius() );
                    
                }

            } else if ( centralCPDragging ) {
                centralCPMoved = true;
                centralCP.setX1( evt.getX() );
                centralCP.setY1( evt.getY() );
                leftCP.setX1( prevLeftCPX + centralCP.getX1() - prevCentralCPX );
                leftCP.setY1( prevLeftCPY + centralCP.getY1() - prevCentralCPY );
                rightCP.setX1( prevRightCPX + centralCP.getX1() - prevCentralCPX );
                rightCP.setY1( prevRightCPY + centralCP.getY1() - prevCentralCPY );
            } else if ( leftCPDragging ) {
                centralCPMoved = true;
                leftCP.setX1( evt.getX() );
                leftCP.setY1( evt.getY() );
            } else if ( rightCPDragging ) {
                centralCPMoved = true;
                rightCP.setX1( evt.getX() );
                rightCP.setY1( evt.getY() );
            }
            
        }

        if ( labelDragging ) {
            labelMoved = true;
            label.setX1( evt.getX() - xOffset );
            label.setY1( evt.getY() - yOffset );
        }
        
        curve.setCurve( 
                x1, y1, 
                leftCP.getX1(), leftCP.getY1(), 
                rightCP.getX1(), rightCP.getY1(), 
                x2, y2 );

        arrow.setAngle( Math.atan2( 
                y2 - rightCP.getY1(), 
                x2 - rightCP.getX1() ) );
        
        arrow.setX1( x2 );
        arrow.setY1( y2 );
        
        targetCP.setX1( x2 );
        targetCP.setY1( y2 );

    }
    
    public final void updateStartAndEndPoints() {
        
        double a = Math.atan2( 
                targetState.getY1() - originState.getY1(), 
                targetState.getX1() - originState.getX1() );
        
        x1 = originState.getX1();
        y1 = originState.getY1();
        
        if ( originState == targetState ) {
            x2 = originState.getX1() - (int) ( originState.getRadius() * 
                    Math.cos( targetCPAngle + Math.toRadians( 60 ) ) );
            y2 = originState.getY1() - (int) ( originState.getRadius() * 
                    Math.sin( targetCPAngle + Math.toRadians( 60 ) ) );
        } else {
            if ( targetCPMoved ) {
                x2 = targetState.getX1() - (int) ( Math.cos( targetCPAngle ) * 
                        targetState.getRadius() );
                y2 = targetState.getY1() - (int) ( Math.sin( targetCPAngle ) * 
                        targetState.getRadius() );
            } else {
                x2 = targetState.getX1() - (int) ( Math.cos( a ) * 
                        targetState.getRadius() );
                y2 = targetState.getY1() - (int) ( Math.sin( a ) * 
                        targetState.getRadius() );
            }
        }
        
        targetCP.setX1( x2 );
        targetCP.setY1( y2 );
        
        if ( !centralCPMoved ) {
            centralCP.setX1( x1 + (x2-x1)/2 );
            centralCP.setY1( y1 + (y2-y1)/2 );
            leftCP.setX1( x1 + (x2-x1)/3 );
            leftCP.setY1( y1 + (y2-y1)/3 );
            rightCP.setX1( x2 - (x2-x1)/3 );
            rightCP.setY1( y2 - (y2-y1)/3 );
        }
        
        if ( !labelMoved ) {
            if ( curve == null ) {
                label.setX1( x1 + (x2-x1)/2 );
                label.setY1( y1 + (y2-y1)/2 - (int) ( label.getTextHeight() * 1.5 ) );
            } else {
                Point2D p = Utils.cubicBezierPoint( curve, 0.5 );
                label.setX1( (int) p.getX() );
                label.setY1( (int) p.getY() - (int) ( label.getTextHeight() * 1.5 ) );
            }
        }
        
    }
    
    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        g2d.setFont( font );
        g2d.setStroke( stroke );
        g2d.setColor( Color.BLACK );
        
        if ( originState == targetState ) {
            
            Graphics2D g2dr = (Graphics2D) g2d.create();
            g2dr.rotate( targetCPAngle, x1, y1 );
            
            g2dr.drawOval( 
                    x1 - targetState.getRadius() / 2, 
                    y1 - targetState.getDiameter(), 
                    targetState.getRadius(), 
                    targetState.getDiameter() );
            
            arrow.setX1( x1 - targetState.getRadius() / 2 );
            arrow.setY1( y1 - targetState.getRadius() + 1 );
            arrow.setAngle( Math.toRadians( 95 ) );
            arrow.draw( g2dr );
            
            if ( controlPointsVisible ) {
                targetCP.draw( g2d );
            }
            
            g2dr.dispose();
            
        } else {
            
            g2d.draw( curve );
            arrow.draw( g2d );
            
            if ( controlPointsVisible ) {
            
                g2d.setStroke( cpStroke );

                Point2D p = Utils.cubicBezierPoint( curve, 0.5 );

                g2d.setColor( centralCP.getFillColor() );
                g2d.drawLine( centralCP.getX1(), centralCP.getY1(), 
                        (int) p.getX(), (int) p.getY() );

                g2d.setColor( leftCP.getFillColor() );
                g2d.drawLine( centralCP.getX1(), centralCP.getY1(), 
                        leftCP.getX1(), leftCP.getY1() );

                g2d.setColor( rightCP.getFillColor() );
                g2d.drawLine( centralCP.getX1(), centralCP.getY1(), 
                        rightCP.getX1(), rightCP.getY1() );

                targetCP.draw( g2d );
                centralCP.draw( g2d );
                leftCP.draw( g2d );
                rightCP.draw( g2d );

            }
        
        }
        
        label.draw( g2d );
        
        g2d.dispose();
        
    }

    @Override
    public boolean intercepts( int x, int y ) {
        
        prevCentralCPX = centralCP.getX1();
        prevCentralCPY = centralCP.getY1();
        prevLeftCPX = leftCP.getX1();
        prevLeftCPY = leftCP.getY1();
        prevRightCPX = rightCP.getX1();
        prevRightCPY = rightCP.getY1();

        if ( targetCP.intercepts( x, y ) ) {
            targetCPDragging = true;
        } else if ( centralCP.intercepts( x, y ) ) {
            centralCPDragging = true;
        } else if ( leftCP.intercepts( x, y ) ) {
            leftCPDragging = true;
        } else if ( rightCP.intercepts( x, y ) ) {
            rightCPDragging = true;
        } else if ( label.intercepts( x, y ) ) {
            labelDragging = true;
            xOffset = x - label.getX1();
            yOffset = y - label.getY1();
        }
        
        return 
                targetCPDragging || 
                centralCPDragging || 
                leftCPDragging || 
                rightCPDragging || 
                labelDragging;
        
    }

    private void updateLabel() {
        
        boolean first = true;
        String labelText = "";
        
        for ( Character s : symbols ) {
            if ( !first ) {
                labelText += ", ";
            }
            labelText += s.toString();
            first = false;
        }
        
        label.setText( labelText );
        
    }
    
    public void addSymbol( char symbol ) {
        symbols.add( symbol );
        updateLabel();
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

    public List<Character> getSymbols() {
        return symbols;
    }

    public void setSymbols( List<Character> symbols ) {
        this.symbols = symbols;
        updateLabel();
    }

    public boolean isControlPointsVisible() {
        return controlPointsVisible;
    }

    public void setControlPointsVisible( boolean controlPointsVisible ) {
        this.controlPointsVisible = controlPointsVisible;
    }
    
}
