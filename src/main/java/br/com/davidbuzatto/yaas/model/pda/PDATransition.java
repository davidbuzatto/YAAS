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

import br.com.davidbuzatto.yaas.model.AbstractGeometricForm;
import br.com.davidbuzatto.yaas.model.Arrow;
import br.com.davidbuzatto.yaas.model.ControlPoint;
import br.com.davidbuzatto.yaas.model.SerializableBasicStroke;
import br.com.davidbuzatto.yaas.util.DrawingConstants;
import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * A Pushdown Automaton transition.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDATransition extends AbstractGeometricForm implements Cloneable {

    private static final double RAD_60 = Math.toRadians( 60 );
    private static final double RAD_90 = Math.toRadians( 90 );
    private static final double RAD_95 = Math.toRadians( 95 );
    
    private PDAState originState;
    private PDAState targetState;
    private List<PDAOperation> operations;
    
    private PDATransitionLabel label;
    private CubicCurve2D curve;
    private Arrow arrow;
    
    private ControlPoint targetCP;
    private ControlPoint centralCP;
    private ControlPoint leftCP;
    private ControlPoint rightCP;
    
    private SerializableBasicStroke cpStroke;
    
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
    
    private Color activeInSimulationStrokeColor;
    
    public PDATransition( PDAState originState, PDAState targetState, List<PDAOperation> operations ) {
        
        this.originState = originState;
        this.targetState = targetState;
        this.operations = new ArrayList<>();
        
        font = DrawingConstants.DEFAULT_FONT;
        strokeColor = DrawingConstants.TRANSITION_STROKE_COLOR;
        mouseHoverStrokeColor = DrawingConstants.TRANSITION_MOUSE_HOVER_STROKE_COLOR;
        selectedStrokeColor = DrawingConstants.TRANSITION_SELECTED_STROKE_COLOR;
        activeInSimulationStrokeColor = DrawingConstants.TRANSITION_ACTIVE_IN_SIMULATION_STROKE_COLOR;
        stroke = DrawingConstants.TRANSITION_STROKE;
        cpStroke = DrawingConstants.TRANSITION_CP_STROKE;
        
        label = new PDATransitionLabel();
        label.setStroke( stroke );
        label.setFont( font );
        label.setStrokeColor( strokeColor );
        label.setMouseHoverStrokeColor( DrawingConstants.TRANSITION_MOUSE_HOVER_STROKE_COLOR );
        label.setMouseHoverFillColor( DrawingConstants.TRANSITION_MOUSE_HOVER_FILL_COLOR );
        label.setSelectedStrokeColor( DrawingConstants.TRANSITION_SELECTED_STROKE_COLOR );
        label.setSelectedFillColor( DrawingConstants.TRANSITION_SELECTED_FILL_COLOR );
        label.setActiveInSimulationStrokeColor(DrawingConstants.LABEL_ACTIVE_IN_SIMULATION_STROKE_COLOR );
        label.setActiveInSimulationFillColor(DrawingConstants.LABEL_ACTIVE_IN_SIMULATION_FILL_COLOR );
        updateLabel();
        
        targetCP = new ControlPoint();
        targetCP.setStroke( stroke );
        targetCP.setRadius( DrawingConstants.TRANSITION_CP_TARGET_RADIUS );
        targetCP.setFillColor( DrawingConstants.TRANSITION_CP_TARGET_COLOR );
        targetCP.setMouseHoverStrokeColor( DrawingConstants.TRANSITION_CP_MOUSE_HOVER_STROKE_COLOR );
        
        centralCP = new ControlPoint();
        centralCP.setStroke( stroke );
        centralCP.setRadius( DrawingConstants.TRANSITION_CP_RADIUS );
        centralCP.setFillColor( DrawingConstants.TRANSITION_CP_COLOR );
        centralCP.setMouseHoverStrokeColor( DrawingConstants.TRANSITION_CP_MOUSE_HOVER_STROKE_COLOR );
        
        leftCP = new ControlPoint();
        leftCP.setStroke( stroke );
        leftCP.setRadius( DrawingConstants.TRANSITION_CP_RADIUS );
        leftCP.setFillColor( DrawingConstants.TRANSITION_CP_LEFT_COLOR );
        leftCP.setMouseHoverStrokeColor( DrawingConstants.TRANSITION_CP_MOUSE_HOVER_STROKE_COLOR );
        
        rightCP = new ControlPoint();
        rightCP.setStroke( stroke );
        rightCP.setRadius( DrawingConstants.TRANSITION_CP_RADIUS );
        rightCP.setFillColor( DrawingConstants.TRANSITION_CP_RIGHT_COLOR );
        rightCP.setMouseHoverStrokeColor( DrawingConstants.TRANSITION_CP_MOUSE_HOVER_STROKE_COLOR );
        
        updateStartAndEndPoints();
        
        arrow = new Arrow();
        arrow.setMouseHoverStrokeColor( DrawingConstants.TRANSITION_MOUSE_HOVER_STROKE_COLOR );
        arrow.setSelectedStrokeColor( DrawingConstants.TRANSITION_SELECTED_STROKE_COLOR );
        arrow.setAngle( Math.atan2( 
                y2 - rightCP.getY1(), x2 - rightCP.getX1() ) );
        arrow.setX1( x2 );
        arrow.setY1( y2 );
        
        curve = new CubicCurve2D.Double( 
                x1, y1, 
                leftCP.getX1(), leftCP.getY1(), 
                rightCP.getX1(), rightCP.getY1(), 
                x2, y2 );
        
        addOperations( operations );
        
    }
    
    public PDATransition( PDAState originState, PDAState targetState, PDAOperation operation ) {
        this( originState, targetState, new ArrayList<PDAOperation>() );
        addOperation( operation );
    }
    
    public PDATransition( PDAState originState, PDAState targetState, PDAOperation... operations ) {
        this( originState, targetState, new ArrayList<PDAOperation>() );
        for ( PDAOperation o : operations ) {
            addOperation( o );
        }
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
            if ( targetCP.intersects( x, y ) ) {
                mouseHover = true;
                targetCP.setMouseHover( true );
                return;
            } else if ( centralCP.intersects( x, y ) ) {
                mouseHover = true;
                centralCP.setMouseHover( true );
                return;
            } else if ( leftCP.intersects( x, y ) ) {
                mouseHover = true;
                leftCP.setMouseHover( true );
                return;
            } else if ( rightCP.intersects( x, y ) ) {
                mouseHover = true;
                rightCP.setMouseHover( true );
                return;
            }
        }
        
        if ( label.intersects( x, y ) ) {
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
                        ( targetState.getDiameter() + label.getMaxTextHeight() ) ) );
                label.setY1( y1 + (int) ( Math.sin( targetCPAngle - RAD_90 ) * 
                        ( targetState.getDiameter() + label.getMaxTextHeight() ) ) );
            }
        } else {
            if ( !labelMoved ) {
                if ( curve == null ) {
                    label.setX1( x1 + (x2-x1)/2 );
                    label.setY1( y1 + (y2-y1)/2 - 
                            (int) ( label.getMaxTextHeight() * 1.5 ) );
                } else {
                    Point2D p = Utils.cubicBezierPoint( curve, 0.5 );
                    label.setX1( (int) p.getX() );
                    label.setY1( (int) p.getY() - 
                            (int) ( label.getMaxTextHeight() * 1.5 ) );
                }
            }
        }
        
    }
    
    @Override
    public void draw( Graphics2D g2d ) {
        
        g2d = (Graphics2D) g2d.create();
        
        g2d.setFont( font );
        g2d.setStroke( stroke.getBasicStroke() );
        arrow.setMouseHover( mouseHover || originState.isMouseHover() );
        arrow.setSelected( selected || originState.isSelected() );
        arrow.setActiveInSimulation( originState.isActiveInSimulation() );
        
        label.setMouseHover( mouseHover || originState.isMouseHover() );
        label.setSelected( selected || originState.isSelected() );
        label.setActiveInSimulation( originState.isActiveInSimulation() );
        
        targetCP.setMouseHover( mouseHover );
        centralCP.setMouseHover( mouseHover );
        leftCP.setMouseHover( mouseHover );
        rightCP.setMouseHover( mouseHover );
        
        if ( mouseHover || originState.isMouseHover() ) {
            g2d.setColor( mouseHoverStrokeColor );
        } else if ( originState.isActiveInSimulation() ) {
            g2d.setColor( activeInSimulationStrokeColor );
        } else if ( selected || originState.isSelected() ) {
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
            
                g2d.setStroke( cpStroke.getBasicStroke() );

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
    
    public void drawLabel( Graphics2D g2d ) {
        g2d = (Graphics2D) g2d.create();
        label.draw( g2d );
        g2d.dispose();
    }

    @Override
    public boolean intersects( int x, int y ) {
        
        labelDragging = false;
        targetCPDragging = false;
        centralCPDragging = false;
        leftCPDragging = false;
        rightCPDragging = false;
            
        prevCentralCPX = centralCP.getX1();
        prevCentralCPY = centralCP.getY1();
        prevLeftCPX = leftCP.getX1();
        prevLeftCPY = leftCP.getY1();
        prevRightCPX = rightCP.getX1();
        prevRightCPY = rightCP.getY1();

        if ( label.intersects( x, y ) ) {
            labelDragging = true;
            xOffset = x - label.getX1();
            yOffset = y - label.getY1();
        }
        
        if ( !labelDragging && controlPointsVisible ) {
            if ( targetCP.intersects( x, y ) ) {
                targetCPDragging = true;
            } else if ( centralCP.intersects( x, y ) ) {
                centralCPDragging = true;
            } else if ( leftCP.intersects( x, y ) ) {
                leftCPDragging = true;
            } else if ( rightCP.intersects( x, y ) ) {
                rightCPDragging = true;
            }
        }
        
        return 
                labelDragging ||
                targetCPDragging || 
                centralCPDragging || 
                leftCPDragging || 
                rightCPDragging;
        
    }

    @Override
    public void move( int xAmount, int yAmount ) {
        x1 += xAmount;
        y1 += yAmount;
        x2 += xAmount;
        y2 += yAmount;
        targetCP.move( xAmount, yAmount );
        centralCP.move( xAmount, yAmount );
        leftCP.move( xAmount, yAmount );
        rightCP.move( xAmount, yAmount );
        label.move( xAmount, yAmount );
        arrow.move( xAmount, yAmount );
        curve.setCurve( 
                x1, y1, 
                leftCP.getX1(), leftCP.getY1(), 
                rightCP.getX1(), rightCP.getY1(), 
                x2, y2 );
    }
    
    private void updateLabel() {
        
        label.clear();
        
        for ( PDAOperation o : operations ) {
            label.addText( o.toString() );
        }
        
    }
    
    public void addOperation( PDAOperation operation ) {
        
        if ( !operations.contains( operation ) ) {
            operations.add( operation );
            Collections.sort( this.operations );
            updateLabel();
        }
        
    }
    
    public void addOperations( List<PDAOperation> operations ) {
        
        if ( !operations.isEmpty() ) {
            
            for ( PDAOperation s : operations ) {
                if ( !this.operations.contains( s ) ) {
                    this.operations.add( s );
                }
            }

            Collections.sort( this.operations );
            updateLabel();
            
        }
        
    }
    
    public PDAState getOriginState() {
        return originState;
    }

    public void setOriginState( PDAState originState ) {
        this.originState = originState;
    }

    public PDAState getTargetState() {
        return targetState;
    }

    public void setTargetState( PDAState targetState ) {
        this.targetState = targetState;
    }

    public List<PDAOperation> getOperations() {
        return operations;
    }

    public void setOperations( List<PDAOperation> operations ) {
        
        this.operations = new ArrayList<>();
        
        if ( !operations.isEmpty() ) {
            
            for ( PDAOperation o : operations ) {
                if ( !this.operations.contains( o ) ) {
                    this.operations.add( o );
                }
            }

            Collections.sort( this.operations );
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
    
    public PDATransition moveCPsTo( 
            int centralCPX1, 
            int centralCPY1, 
            int leftCPX1, 
            int leftCPY1, 
            int rightCPX1,
            int rightCPY1 ) {
        
        this.centralCPMoved = true;
        
        centralCP.setX1( centralCPX1 );
        centralCP.setY1( centralCPY1 );
        leftCP.setX1( leftCPX1 );
        leftCP.setY1( leftCPY1 );
        rightCP.setX1( rightCPX1 );
        rightCP.setY1( rightCPY1 );
        
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
        
        return this;
        
    }
    
    private PDATransition bend( 
            int centralCPAmountX, 
            int centralCPAmountY, 
            boolean centralCPMoved,
            int leftCPAmountX, 
            int leftCPAmountY, 
            int rightCPAmountX,
            int rightCPAmountY, 
            int targetCPAmount,
            boolean targetCPMoved ) {
        
        this.centralCPMoved = centralCPMoved;
        this.targetCPMoved = targetCPMoved;
        targetCPAngle = Math.toRadians( targetCPAmount );
        
        centralCP.setX1( centralCP.getX1() + centralCPAmountX );
        centralCP.setY1( centralCP.getY1() + centralCPAmountY );
        leftCP.setX1( leftCP.getX1() + leftCPAmountX );
        leftCP.setY1( leftCP.getY1() + leftCPAmountY );
        rightCP.setX1( rightCP.getX1() + rightCPAmountX );
        rightCP.setY1( rightCP.getY1() + rightCPAmountY );
        
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
        
        return this;
        
    }
    
    public PDATransition bend( 
            int centralCPAmountX, 
            int centralCPAmountY, 
            int leftCPAmountX, 
            int leftCPAmountY, 
            int rightCPAmountX,
            int rightCPAmountY, 
            int targetCPAmount ) {
        return bend( centralCPAmountX, centralCPAmountY, true,
              leftCPAmountX, leftCPAmountY, 
              rightCPAmountX, rightCPAmountY, 
              targetCPAmount, true );
    }
    
    public PDATransition bendX( 
            int centralCPAmount, 
            int leftCPAmount, 
            int rightCPAmount, 
            int targetCPAmount ) {
        return bend( centralCPAmount, 0,
              leftCPAmount, 0, 
              rightCPAmount, 0,
              targetCPAmount );
    }
    
    public PDATransition bendY( 
            int centralCPAmount, 
            int leftCPAmount, 
            int rightCPAmount, 
            int targetCPAmount ) {
        return bend( 0, centralCPAmount, 
              0, leftCPAmount, 
              0, rightCPAmount, 
              targetCPAmount );
    }
    
    public PDATransition bendByCenterCP( int xAmount, int yAmount ) {
        return bend( xAmount, yAmount, true, 
              xAmount, yAmount,
              xAmount, yAmount,
              0, false );
    }
    
    public PDATransition bendByCenterCPX( int amount ) {
        return bendByCenterCP( amount, 0 );
    }
    
    public PDATransition bendByCenterCPY( int amount ) {
        return bendByCenterCP( 0, amount );
    }
    
    public PDATransition bendByLeftCP( int xAmount, int yAmount ) {
        return bend( 0, 0, false, xAmount, yAmount, 0, 0, 0, false );
    }
    
    public PDATransition bendByLeftCPX( int amount ) {
        return bendByLeftCP( amount, 0 );
    }
    
    public PDATransition bendByLeftCPY( int amount ) {
        return bendByLeftCP( 0, amount );
    }
    
    public PDATransition bendByRightCP( int xAmount, int yAmount ) {
        return bend( 0, 0, false, 0, 0, xAmount, yAmount, 0, false );
    }
    
    public PDATransition bendByRightCPX( int amount ) {
        return bendByRightCP( amount, 0 );
    }
    
    public PDATransition bendByRightCPY( int amount ) {
        return bendByRightCP( 0, amount );
    }
    
    public PDATransition rotateTargetCP( int angle ) {
        return bend( 0, 0, true, 0, 0, 0, 0, angle, true );
    }
    
    public PDATransition moveLabelTo( int x1, int y1 ) {
        labelMoved = true;
        label.setX1( x1 );
        label.setY1( y1 );
        return this;
    }
    
    public PDATransition moveLabel( int xAmount, int yAmount ) {
        labelMoved = true;
        label.setX1( label.getX1() + xAmount );
        label.setY1( label.getY1() + yAmount );
        return this;
    }
    
    public PDATransition moveLabelX( int amount ) {
        return moveLabel( amount, 0 );
    }
    
    public PDATransition moveLabelY( int amount ) {
        return moveLabel( 0, amount );
    }
    
    public void replaceAllOperations( Set<PDAOperation> newOperations ) {
        replaceAllOperations( new ArrayList<>( newOperations ) );
    }
    
    public void replaceAllOperations( List<PDAOperation> newOperations ) {
        Collections.sort( newOperations );
        operations.clear();
        operations.addAll( newOperations );
        updateLabel();
    }
    
    public boolean constainsOperation( PDAOperation operation ) {
        return operations.contains( operation );
    }
    
    @Override
    public String toString() {
        return String.format( "(%s) - %s -> (%s)",
                originState, label, targetState );
    }
    
    public String generateCode( String modelName ) {
        
        String className = getClass().getSimpleName();
        String tName = originState.getLabel() + targetState.getLabel();
        
        String pdaOp = "";
        boolean fs = true;
        
        for ( PDAOperation o : operations ) {
            if ( !fs ) {
                pdaOp += ",\n";
            }
            pdaOp += o.generateCode();
            fs = false;
        }
        
        String def = String.format( "    %s %s = new %s( %s, %s,\n%s );", 
                className, 
                tName, 
                className, 
                originState.getLabel(), 
                targetState.getLabel(), 
                pdaOp );
        
        String pos = "";
        
        if ( labelMoved ) {
            pos += String.format( "\n    %s.moveLabelTo( %d, %d );", 
                    tName, 
                    label.getX1(), label.getY1() );
        }
        
        if ( targetCPMoved ) {
            pos += String.format( "\n    %s.rotateTargetCP( %d );", tName,
                    Utils.roundToNearest( (int) Math.toDegrees( targetCPAngle ), 5 ) );
        }
        
        if ( originState != targetState && centralCPMoved ) {
            pos += String.format( "\n    %s.moveCPsTo( %d, %d, %d, %d, %d, %d );", 
                    tName, 
                    centralCP.getX1(), centralCP.getY1(),
                    leftCP.getX1(), leftCP.getY1(),
                    rightCP.getX1(), rightCP.getY1() );
        }
        
        String add = String.format( "\n    %s.addTransition( %s );", modelName, tName );
        
        return def + pos + add;
        
    }
    
    @Override
    @SuppressWarnings( "unchecked" )
    public Object clone() throws CloneNotSupportedException {
        
        PDATransition c = (PDATransition) super.clone();
        
        // will be reconstructed in PDA clone.
        /*c.originState = (PDAState) originState.clone();
        c.targetState = (PDAState) targetState.clone();*/
        
        c.operations = new ArrayList<>();
        for ( PDAOperation o : operations ) {
            c.operations.add( o );
        }

        c.label = (PDATransitionLabel) label.clone();
        c.curve = (CubicCurve2D) curve.clone();
        c.arrow = (Arrow) arrow.clone();

        c.targetCP = (ControlPoint) targetCP.clone();
        c.centralCP = (ControlPoint) centralCP.clone();
        c.leftCP = (ControlPoint) leftCP.clone();
        c.rightCP = (ControlPoint) rightCP.clone();

        c.cpStroke = (SerializableBasicStroke) cpStroke.clone();

        c.xOffset = xOffset;
        c.yOffset = yOffset;

        c.labelDragging = false;
        c.labelMoved = labelMoved;

        c.prevCentralCPX = prevCentralCPX;
        c.prevCentralCPY = prevCentralCPY;
        c.prevLeftCPX = prevLeftCPX;
        c.prevLeftCPY = prevLeftCPY;
        c.prevRightCPX = prevRightCPX;
        c.prevRightCPY = prevRightCPY;

        c.targetCPDragging = false;
        c.centralCPDragging = false;
        c.leftCPDragging = false;
        c.rightCPDragging = false;

        c.centralCPMoved = centralCPMoved;
        c.targetCPMoved = targetCPMoved;

        c.targetCPAngle = targetCPAngle;

        c.controlPointsVisible = false;

        c.activeInSimulationStrokeColor = activeInSimulationStrokeColor;
        
        return c;
        
    }
    
}
