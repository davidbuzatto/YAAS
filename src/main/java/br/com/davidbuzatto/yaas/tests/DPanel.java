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
package br.com.davidbuzatto.yaas.tests;

import br.com.davidbuzatto.yaas.gui.model.Arrow;
import br.com.davidbuzatto.yaas.util.DrawingConstants;
import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import javax.swing.JPanel;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class DPanel extends JPanel {
    
    private CubicCurve2D curve;
    private Arrow originArrow;
    private Arrow targetArrow;
    
    private int xPressed;
    private int yPressed;
    
    // start and end
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    
    // control points coordinates
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
    
    private boolean p1Dragg;
    private boolean p2Dragg;
    private boolean cDragg;
    private boolean c1Dragg;
    private boolean c2Dragg;
    
    public DPanel() {
        
        x1 = 100;
        y1 = 200;
        x2 = 400;
        y2 = 100;
        
        cx = x1 + (x2-x1)/2;
        cy = y1 + (y2-y1)/2;
        
        c1x = x1 + (x2-x1)/3;
        c1y = y1 + (y2-y1)/3;
        
        c2x = x2 - (x2-x1)/3;
        c2y = y2 - (y2-y1)/3;
        
        originArrow = new Arrow();
        originArrow.setAngle( Math.atan2( y1-c1y, x1-c1x ) );
        originArrow.setX1( x1 );
        originArrow.setY1( y1 );
        
        targetArrow = new Arrow();
        targetArrow.setAngle( Math.atan2( y2-c2y, x2-c2x ) );
        targetArrow.setX1( x2 );
        targetArrow.setY1( y2 );
        
        curve = new CubicCurve2D.Double( x1, y1, c1x, c1y, c2x, c2y, x2, y2 );
        
        addMouseListener(new MouseAdapter(){
            
            @Override
            public void mousePressed( MouseEvent e ) {
                
                xPressed = e.getX();
                yPressed = e.getY();
                
                int p1x = xPressed - x1;
                int p1y = yPressed - y1;
                int p2x = xPressed - x2;
                int p2y = yPressed - y2;
                
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
                    
                if ( p1x * p1x + p1y * p1y <= DrawingConstants.TRANSITION_CP_RADIUS_EQUARED ) {
                    p1Dragg = true;
                } else if ( p2x * p2x + p2y * p2y <= DrawingConstants.TRANSITION_CP_RADIUS_EQUARED ) {
                    p2Dragg = true;
                } else if ( cxc * cxc + cyc * cyc <= DrawingConstants.TRANSITION_CP_RADIUS_EQUARED ) {
                    cDragg = true;
                } else if ( c1xc * c1xc + c1yc * c1yc <= DrawingConstants.TRANSITION_CP_RADIUS_EQUARED ) {
                    c1Dragg = true;
                } else if ( c2xc * c2xc + c2yc * c2yc <= DrawingConstants.TRANSITION_CP_RADIUS_EQUARED ) {
                    c2Dragg = true;
                }
                
            }

            @Override
            public void mouseReleased( MouseEvent e ) {
                p1Dragg = false;
                p2Dragg = false;
                cDragg = false;
                c1Dragg = false;
                c2Dragg = false;
            }
            
        });
        
        addMouseMotionListener( new MouseAdapter() {
            
            @Override
            public void mouseDragged( MouseEvent e ) {
                
                if ( p1Dragg ) {
                    x1 = e.getX();
                    y1 = e.getY();
                    /*cx = x1 + (x2-x1)/2;
                    cy = y1 + (y2-y1)/2;
                    c1x = x1 + (x2-x1)/3;
                    c1y = y1 + (y2-y1)/3;
                    c2x = x2 - (x2-x1)/3;
                    c2y = y2 - (y2-y1)/3;*/
                } else if ( p2Dragg ) {
                    x2 = e.getX();
                    y2 = e.getY();
                    /*cx = x1 + (x2-x1)/2;
                    cy = y1 + (y2-y1)/2;
                    c1x = x1 + (x2-x1)/3;
                    c1y = y1 + (y2-y1)/3;
                    c2x = x2 - (x2-x1)/3;
                    c2y = y2 - (y2-y1)/3;*/
                } else if ( cDragg ) {
                    cx = e.getX();
                    cy = e.getY();
                    c1x = c1xOri + cx-cxOri;
                    c1y = c1yOri + cy-cyOri;
                    c2x = c2xOri + cx-cxOri;
                    c2y = c2yOri + cy-cyOri;
                } else if ( c1Dragg ) {
                    c1x = e.getX();
                    c1y = e.getY();
                } else if ( c2Dragg ) {
                    c2x = e.getX();
                    c2y = e.getY();
                }
                
                curve.setCurve( x1, y1, c1x, c1y, c2x, c2y, x2, y2 );
                
                originArrow.setAngle( Math.atan2( y1-c1y, x1-c1x ) );
                originArrow.setX1( x1 );
                originArrow.setY1( y1 );
                
                targetArrow.setAngle( Math.atan2( y2-c2y, x2-c2x ) );
                targetArrow.setX1( x2 );
                targetArrow.setY1( y2 );
                
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
        
        g2d.setColor( Color.WHITE );
        g2d.fillRect( 0, 0, getWidth(), getHeight() );
        
        g2d.setStroke(DrawingConstants.TRANSITION_STROKE );
        g2d.setColor( Color.BLACK );
        g2d.draw( curve );
        
        originArrow.draw( g2d );
        targetArrow.draw( g2d );
        
        g2d.setStroke(DrawingConstants.TRANSITION_CP_STROKE );
        g2d.setColor(DrawingConstants.TRANSITION_CP_COLOR );
        g2d.drawLine( cx, cy, c1x, c1y );
        g2d.drawLine( cx, cy, c2x, c2y );
        g2d.fillOval(x1 - DrawingConstants.TRANSITION_CP_RADIUS, 
                y1 - DrawingConstants.TRANSITION_CP_RADIUS, 
                DrawingConstants.TRANSITION_CP_DIAMETER, 
                DrawingConstants.TRANSITION_CP_DIAMETER );
        g2d.fillOval(x2 - DrawingConstants.TRANSITION_CP_RADIUS, 
                y2 - DrawingConstants.TRANSITION_CP_RADIUS, 
                DrawingConstants.TRANSITION_CP_DIAMETER, 
                DrawingConstants.TRANSITION_CP_DIAMETER );
        g2d.fillOval(cx - DrawingConstants.TRANSITION_CP_RADIUS, 
                cy - DrawingConstants.TRANSITION_CP_RADIUS, 
                DrawingConstants.TRANSITION_CP_DIAMETER, 
                DrawingConstants.TRANSITION_CP_DIAMETER );
        g2d.fillOval(c1x - DrawingConstants.TRANSITION_CP_RADIUS, 
                c1y - DrawingConstants.TRANSITION_CP_RADIUS, 
                DrawingConstants.TRANSITION_CP_DIAMETER, 
                DrawingConstants.TRANSITION_CP_DIAMETER );
        g2d.fillOval(c2x - DrawingConstants.TRANSITION_CP_RADIUS, 
                c2y - DrawingConstants.TRANSITION_CP_RADIUS, 
                DrawingConstants.TRANSITION_CP_DIAMETER, 
                DrawingConstants.TRANSITION_CP_DIAMETER );
        
        g2d.setColor( Color.BLUE );
        for ( double t = 0.0; t <= 1.0; t += 0.1 ) {
            
            Point2D p = Utils.cubicBezierPoint( 
                    curve.getX1(), 
                    curve.getY1(), 
                    curve.getCtrlX1(), 
                    curve.getCtrlY1(), 
                    curve.getCtrlX2(), 
                    curve.getCtrlY2(), 
                    curve.getX2(), 
                    curve.getY2(),
                    t );
            
            g2d.fillOval( (int) p.getX() - 5, (int) p.getY() - 5, 10, 10 );
            
        }
        
        QuadCurve2D c2 = new QuadCurve2D.Double( 100, 400, 250, 300, 400, 400 );
        g2d.draw( c2 );
        
        for ( double t = 0.0; t <= 1.0; t += 0.1 ) {
            
            Point2D p = Utils.quadraticBezierPoint( 
                    c2.getX1(), 
                    c2.getY1(), 
                    c2.getCtrlX(), 
                    c2.getCtrlY(),
                    c2.getX2(), 
                    c2.getY2(),
                    t );
            
            g2d.fillOval( (int) p.getX() - 5, (int) p.getY() - 5, 10, 10 );
            
        }
        
        g2d.dispose();
        
    }
    
}
