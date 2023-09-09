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
package br.com.davidbuzatto.yaas.gui.fa;

import br.com.davidbuzatto.yaas.gui.model.AbstractGeometricForm;
import br.com.davidbuzatto.yaas.gui.model.Arrow;
import br.com.davidbuzatto.yaas.gui.model.ControlPoint;
import br.com.davidbuzatto.yaas.util.DrawingConstants;
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
public class FATransition extends AbstractGeometricForm {

    private static final double RAD_60 = Math.toRadians( 60 );
    private static final double RAD_90 = Math.toRadians( 90 );
    private static final double RAD_95 = Math.toRadians( 95 );
    
    
    private FAState originState;
    private FAState targetState;
    private List<Character> symbols;
    
    
    private final FATransitionLabel label;
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
    
    public FATransition( FAState originState, FAState targetState, List<Character> symbols ) {
        
        this.originState = originState;
        this.targetState = targetState;
        this.symbols = new ArrayList<>();
        
        font = DrawingConstants.DEFAULT_FONT;
        mouseHoverStrokeColor = DrawingConstants.TRANSITION_MOUSE_HOVER_STROKE_COLOR;
        selectedStrokeColor = DrawingConstants.TRANSITION_SELECTED_STROKE_COLOR;
        stroke = DrawingConstants.TRANSITION_STROKE;
        cpStroke = DrawingConstants.TRANSITION_CP_STROKE;
        
        label = new FATransitionLabel();
        label.setStroke( stroke );
        label.setFont( font );
        label.setStrokeColor( strokeColor );
        label.setMouseHoverStrokeColor(DrawingConstants.TRANSITION_MOUSE_HOVER_STROKE_COLOR );
        label.setMouseHoverFillColor(DrawingConstants.TRANSITION_MOUSE_HOVER_FILL_COLOR );
        label.setSelectedStrokeColor(DrawingConstants.TRANSITION_SELECTED_STROKE_COLOR );
        label.setSelectedFillColor(DrawingConstants.TRANSITION_SELECTED_FILL_COLOR );
        updateLabel();
        
        targetCP = new ControlPoint();
        targetCP.setStroke( stroke );
        targetCP.setRadius(DrawingConstants.TRANSITION_CP_TARGET_RADIUS );
        targetCP.setFillColor(DrawingConstants.TRANSITION_CP_TARGET_COLOR );
        targetCP.setMouseHoverStrokeColor(DrawingConstants.TRANSITION_CP_MOUSE_HOVER_STROKE_COLOR );
        
        centralCP = new ControlPoint();
        centralCP.setStroke( stroke );
        centralCP.setRadius(DrawingConstants.TRANSITION_CP_RADIUS );
        centralCP.setFillColor(DrawingConstants.TRANSITION_CP_COLOR );
        centralCP.setMouseHoverStrokeColor(DrawingConstants.TRANSITION_CP_MOUSE_HOVER_STROKE_COLOR );
        
        leftCP = new ControlPoint();
        leftCP.setStroke( stroke );
        leftCP.setRadius(DrawingConstants.TRANSITION_CP_RADIUS );
        leftCP.setFillColor(DrawingConstants.TRANSITION_CP_LEFT_COLOR );
        leftCP.setMouseHoverStrokeColor(DrawingConstants.TRANSITION_CP_MOUSE_HOVER_STROKE_COLOR );
        
        rightCP = new ControlPoint();
        rightCP.setStroke( stroke );
        rightCP.setRadius(DrawingConstants.TRANSITION_CP_RADIUS );
        rightCP.setFillColor(DrawingConstants.TRANSITION_CP_RIGHT_COLOR );
        rightCP.setMouseHoverStrokeColor(DrawingConstants.TRANSITION_CP_MOUSE_HOVER_STROKE_COLOR );
        
        updateStartAndEndPoints();
        
        arrow = new Arrow();
        arrow.setMouseHoverStrokeColor(DrawingConstants.TRANSITION_MOUSE_HOVER_STROKE_COLOR );
        arrow.setSelectedStrokeColor(DrawingConstants.TRANSITION_SELECTED_STROKE_COLOR );
        arrow.setAngle( Math.atan2( 
                y2 - rightCP.getY1(), x2 - rightCP.getX1() ) );
        arrow.setX1( x2 );
        arrow.setY1( y2 );
        
        curve = new CubicCurve2D.Double( 
                x1, y1, 
                leftCP.getX1(), leftCP.getY1(), 
                rightCP.getX1(), rightCP.getY1(), 
                x2, y2 );
        
        addSymbols( symbols );
        
    }
    
    public FATransition( FAState originState, FAState targetState, char symbol ) {
        this( originState, targetState, new ArrayList<Character>() );
        addSymbol( symbol );
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
                            - RAD_60;
                    
                } else {
                    
                    targetCPAngle = Math.atan2( 
                            targetState.getY1() - evt.getY(), 
                            targetState.getX1() - evt.getX() );

                    x2 = targetState.getX1() - 
                            (int) ( Math.cos( targetCPAngle ) * 
                            targetState.getRadius() );
                    y2 = targetState.getY1() - 
                            (int) ( Math.sin( targetCPAngle ) * 
                            targetState.getRadius() );
                    
                }

            } else if ( centralCPDragging ) {
                centralCPMoved = true;
                centralCP.setX1( evt.getX() );
                centralCP.setY1( evt.getY() );
                leftCP.setX1( prevLeftCPX + 
                        centralCP.getX1() - prevCentralCPX );
                leftCP.setY1( prevLeftCPY + 
                        centralCP.getY1() - prevCentralCPY );
                rightCP.setX1( prevRightCPX + 
                        centralCP.getX1() - prevCentralCPX );
                rightCP.setY1( prevRightCPY + 
                        centralCP.getY1() - prevCentralCPY );
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
    
    public void mouseHover( int x, int y ) {

        if ( controlPointsVisible ) {
            if ( targetCP.intercepts( x, y ) ) {
                mouseHover = true;
                targetCP.setMouseHover( true );
                return;
            } else if ( centralCP.intercepts( x, y ) ) {
                mouseHover = true;
                centralCP.setMouseHover( true );
                return;
            } else if ( leftCP.intercepts( x, y ) ) {
                mouseHover = true;
                leftCP.setMouseHover( true );
                return;
            } else if ( rightCP.intercepts( x, y ) ) {
                mouseHover = true;
                rightCP.setMouseHover( true );
                return;
            }
        }
        
        if ( label.intercepts( x, y ) ) {
            mouseHover = true;
            return;
        }
        
        mouseHover = false;
        
    }
    
    public final void updateStartAndEndPoints() {
        
        double a = Math.atan2( 
                targetState.getY1() - originState.getY1(), 
                targetState.getX1() - originState.getX1() );
        
        x1 = originState.getX1();
        y1 = originState.getY1();
        
        if ( originState == targetState ) {
            x2 = originState.getX1() - (int) ( originState.getRadius() * 
                    Math.cos( targetCPAngle + RAD_60 ) );
            y2 = originState.getY1() - (int) ( originState.getRadius() * 
                    Math.sin( targetCPAngle + RAD_60 ) );
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
        
        if ( originState == targetState ) {
            if ( !labelMoved ) {
                label.setX1( x1 + (int) ( Math.cos( targetCPAngle - RAD_90 ) * 
                        ( targetState.getDiameter() + label.getTextHeight() )));
                label.setY1( y1 + (int) ( Math.sin( targetCPAngle - RAD_90 ) * 
                        ( targetState.getDiameter() + label.getTextHeight() )));
            }
        } else {
            if ( !labelMoved ) {
                if ( curve == null ) {
                    label.setX1( x1 + (x2-x1)/2 );
                    label.setY1( y1 + (y2-y1)/2 - 
                            (int) ( label.getTextHeight() * 1.5 ) );
                } else {
                    Point2D p = Utils.cubicBezierPoint( curve, 0.5 );
                    label.setX1( (int) p.getX() );
                    label.setY1( (int) p.getY() - 
                            (int) ( label.getTextHeight() * 1.5 ) );
                }
            }
        }
        
    }
    
    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        g2d.setFont( font );
        g2d.setStroke( stroke );
        arrow.setMouseHover( mouseHover );
        arrow.setSelected( selected );
        
        label.setMouseHover( mouseHover );
        label.setSelected( selected );
        
        targetCP.setMouseHover( mouseHover );
        centralCP.setMouseHover( mouseHover );
        leftCP.setMouseHover( mouseHover );
        rightCP.setMouseHover( mouseHover );
        
        if ( mouseHover ) {
            g2d.setColor( mouseHoverStrokeColor );
        } else if ( selected ) {
            g2d.setColor( selectedStrokeColor );
        } else {
            g2d.setColor( strokeColor );
        }
        
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
            arrow.setAngle( RAD_95 );
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

        if ( label.intercepts( x, y ) ) {
            labelDragging = true;
            xOffset = x - label.getX1();
            yOffset = y - label.getY1();
        }
        
        if ( !labelDragging && controlPointsVisible ) {
            if ( targetCP.intercepts( x, y ) ) {
                targetCPDragging = true;
            } else if ( centralCP.intercepts( x, y ) ) {
                centralCPDragging = true;
            } else if ( leftCP.intercepts( x, y ) ) {
                leftCPDragging = true;
            } else if ( rightCP.intercepts( x, y ) ) {
                rightCPDragging = true;
            }
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
        
        if ( !symbols.contains( symbol ) ) {
            symbols.add( symbol );
            Utils.customSymbolsSort( this.symbols );
            updateLabel();
        }
        
    }
    
    public void addSymbols( List<Character> symbols ) {
        
        if ( !symbols.isEmpty() ) {
            
            for ( Character s : symbols ) {
                if ( !this.symbols.contains( s ) ) {
                    this.symbols.add( s );
                }
            }

            Utils.customSymbolsSort( this.symbols );
            updateLabel();
            
        }
        
    }
    
    public FAState getOriginState() {
        return originState;
    }

    public void setOriginState( FAState originState ) {
        this.originState = originState;
    }

    public FAState getTargetState() {
        return targetState;
    }

    public void setTargetState( FAState targetState ) {
        this.targetState = targetState;
    }

    public List<Character> getSymbols() {
        return symbols;
    }

    public void setSymbols( List<Character> symbols ) {
        
        this.symbols = new ArrayList<>();
        
        if ( !symbols.isEmpty() ) {
            
            for ( Character s : symbols ) {
                if ( !this.symbols.contains( s ) ) {
                    this.symbols.add( s );
                }
            }

            Utils.customSymbolsSort( this.symbols );
            updateLabel();
            
        }
        
    }

    public boolean isControlPointsVisible() {
        return controlPointsVisible;
    }

    public void setControlPointsVisible( boolean controlPointsVisible ) {
        this.controlPointsVisible = controlPointsVisible;
    }
    
    public void resetTransformations() {
        
        centralCPMoved = false;
        targetCPMoved = false;
        labelMoved = false;
        targetCPAngle = 0;
        
        updateStartAndEndPoints();
        
        arrow.setAngle( Math.atan2( 
                y2 - rightCP.getY1(), x2 - rightCP.getX1() ) );
        arrow.setX1( x2 );
        arrow.setY1( y2 );
        
        curve.setCurve( 
                x1, y1, 
                leftCP.getX1(), leftCP.getY1(), 
                rightCP.getX1(), rightCP.getY1(), 
                x2, y2 );
        
        updateStartAndEndPoints();
        
    }
    
    @Override
    public void setStrokeColor( Color strokeColor ) {
        this.strokeColor = strokeColor;
        arrow.setStrokeColor( strokeColor );
        label.setStrokeColor( strokeColor );
    }
    
    @Override
    public String toString() {
        return String.format( "(%s) - %s -> (%s)",
                originState, label.getText(), targetState );
    }
    
}
