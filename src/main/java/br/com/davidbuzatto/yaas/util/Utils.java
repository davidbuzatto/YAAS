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
package br.com.davidbuzatto.yaas.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;

/**
 * Utilitary methods and application constants.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Utils {
    
    public static final Font DEFAULT_FONT = new Font( "monospaced", Font.BOLD, 18 );
    
    public static final int STATE_RADIUS = 25;
    public static final int STATE_RADIUS_SQUARED = STATE_RADIUS * STATE_RADIUS;
    public static final int STATE_DIAMETER = STATE_RADIUS * 2;
    public static final BasicStroke STATE_STROKE = new BasicStroke( 2 );
    
    public static final Color STATE_MOUSE_HOVER_STROKE_COLOR = 
            new Color( 0, 102, 153 );
    public static final Color STATE_MOUSE_HOVER_FILL_COLOR = 
            new Color( 157, 222, 255 );
    
    public static final int TRANSITION_CP_RADIUS = 5;
    public static final int TRANSITION_CP_RADIUS_EQUARED = 
            TRANSITION_CP_RADIUS * TRANSITION_CP_RADIUS;
    public static final int TRANSITION_CP_DIAMETER = TRANSITION_CP_RADIUS * 2;
    
    public static final int TRANSITION_CP_TARGET_RADIUS = TRANSITION_CP_DIAMETER;
    public static final int TRANSITION_CP_TARGET_RADIUS_SQUARED = 
            TRANSITION_CP_TARGET_RADIUS * TRANSITION_CP_TARGET_RADIUS;
    public static final int TRANSITION_CP_TARGET_DIAMETER = TRANSITION_CP_TARGET_RADIUS * 2;
    
    public static final Color TRANSITION_CP_COLOR = 
            new Color( 150, 150, 150, 220 );
    public static final Color TRANSITION_CP_LEFT_COLOR = 
            new Color( 255, 78, 8, 220 );
    public static final Color TRANSITION_CP_RIGHT_COLOR = 
            new Color( 187, 16, 242, 220 );
    public static final Color TRANSITION_CP_TARGET_COLOR = 
            new Color( 150, 150, 150, 220 );
    public static final Color TRANSITION_CP_MOUSE_HOVER_STROKE_COLOR = 
            new Color( 0, 102, 153, 220 );
    
    public static final Color TRANSITION_MOUSE_HOVER_STROKE_COLOR = 
            new Color( 0, 102, 153, 220 );
    public static final Color TRANSITION_MOUSE_HOVER_FILL_COLOR = 
            new Color( 157, 222, 255, 220 );
    
    public static final BasicStroke TRANSITION_STROKE = 
            new BasicStroke( 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND );
    public static final BasicStroke TRANSITION_CP_STROKE = 
            new BasicStroke( 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1
                    , new float[]{ 5, 5 }, 0 );
    
    public static final Color GRID_COLOR = new Color( 200, 200, 200, 200 );
    public static final Color TEMP_TRANSITION_COLOR = new Color( 150, 150, 150, 200 );
    public static final BasicStroke TEMP_TRANSITION_STROKE = 
            new BasicStroke( 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1
                    , new float[]{ 5, 5 }, 0 );
    
    public static final BasicStroke DEFAULT_ARROW_STROKE = new BasicStroke( 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND );
    
    /**
     * De Casteljau's algorithm to calculate points in a cubic Bézier curve.
     * 
     * @param x1 x of the first anchor point.
     * @param y1 y of the first anchor point.
     * @param ctrlx1 x of the first control point.
     * @param ctrly1 y of the first control point.
     * @param ctrlx2 x of the second control point.
     * @param ctrly2 y of the second control point.
     * @param x2 x of the second anchor point.
     * @param y2 y of the second anchor point.
     * @param t linear position of the desired point. Varies from 0 to 1.
     * 
     * @return A point inside the cubic Bézier curve.
     */
    public static Point2D cubicBezierPoint( 
            double x1, double y1, 
            double ctrlx1, double ctrly1, 
            double ctrlx2, double ctrly2, 
            double x2, double y2,
            double t ) {
        
        // x1 and y1 => p0
        // ctrlx1 and ctrly1 => p1
        // ctrlx2 and ctrly2 => p2
        // x2 and y2 => p3
        
        // p4 = lerp(p0, p1, t)
        double p4x = lerp( x1, ctrlx1, t );
        double p4y = lerp( y1, ctrly1, t );
        
        // p5 = lerp(p1, p2, t)
        double p5x = lerp( ctrlx1, ctrlx2, t );
        double p5y = lerp( ctrly1, ctrly2, t );
                
        // p6 = lerp(p2, p3, t)
        double p6x = lerp( ctrlx2, x2, t );
        double p6y = lerp( ctrly2, y2, t );
        
        //-------------------------------------------
        
        // p7 = lerp(p4, p5, t)
        double p7x = lerp( p4x, p5x, t );
        double p7y = lerp( p4y, p5y, t );
        
        // p8 = lerp(p5, p6, t)
        double p8x = lerp( p5x, p6x, t );
        double p8y = lerp( p5y, p6y, t );
        
        //-------------------------------------------
        
        // bt = lerp(p7, p8, t)
        double btx = lerp( p7x, p8x, t );
        double bty = lerp( p7y, p8y, t );
        
        return new Point2D.Double( btx, bty );
        
    }
    
    /**
     * Uses a CubicCurve2d to calculate points in it.
     * 
     * @param curve The curve.
     * @param t linear position of the desired point. Varies from 0 to 1.
     * @return A point inside the curve.
     */
    public static Point2D cubicBezierPoint( CubicCurve2D curve, double t ) {
        return cubicBezierPoint( curve.getX1(), 
                        curve.getY1(), 
                        curve.getCtrlX1(), 
                        curve.getCtrlY1(), 
                        curve.getCtrlX2(), 
                        curve.getCtrlY2(), 
                        curve.getX2(), 
                        curve.getY2(), 
                        t );
    }
    
    /**
     * De Casteljau's algorithm to calculate points in a quadratic Bézier curve.
     * 
     * @param x1 x of the first anchor point.
     * @param y1 y of the first anchor point.
     * @param ctrlx x of the control point.
     * @param ctrly y of the control point.
     * @param x2 x of the second anchor point.
     * @param y2 y of the second anchor point.
     * @param t linear position of the desired point. Varies from 0 to 1.
     * 
     * @return A point inside the quadratic Bézier curve.
     */
    public static Point2D quadraticBezierPoint( 
            double x1, double y1, 
            double ctrlx, double ctrly, 
            double x2, double y2,
            double t ) {
        
        // x1 and y1 => p0
        // ctrlx and ctrly => p1
        // x2 and y2 => p2
        
        // p3 = lerp(p0, p1, t)
        double p3x = lerp( x1, ctrlx, t );
        double p3y = lerp( y1, ctrly, t );
        
        // p4 = lerp(p1, p2, t)
        double p4x = lerp( ctrlx, x2, t );
        double p4y = lerp( ctrly, y2, t );
        
        //-------------------------------------------
        
        // bt = lerp(p3, p4, t)
        double btx = lerp( p3x, p4x, t );
        double bty = lerp( p3y, p4y, t );
        
        return new Point2D.Double( btx, bty );
        
    }
    
    /**
     * Uses a QuadCurve2d to calculate points in it.
     * 
     * @param curve The curve.
     * @param t linear position of the desired point. Varies from 0 to 1.
     * @return A point inside the curve.
     */
    public static Point2D quadraticBezierPoint( QuadCurve2D curve, double t ) {
        return quadraticBezierPoint( curve.getX1(), 
                        curve.getY1(), 
                        curve.getCtrlX(), 
                        curve.getCtrlY(), 
                        curve.getX2(), 
                        curve.getY2(), 
                        t );
    }
    
    /**
     * Linear interpolation between two values.
     * 
     * @param start starting value.
     * @param end ending value.
     * @param t linear position of the desired value. Varies from 0 to 1.
     * 
     * @return The linear interpolation between start and end.
     */
    public static double lerp( double start, double end, double t ) {
        return start + (end - start) * t;
    }
    
    /**
     * Linear interpolation between two points.
     * 
     * @param x1 starting x.
     * @param y1 starting y.
     * @param x2 ending x.
     * @param y2 ending y.
     * @param t linear position of the desired point. Varies from 0 to 1.
     * 
     * @return The linear interpolation between two points.
     */
    public static Point2D lerp( double x1, double y1, double x2, double y2, double t ) {
        return new Point2D.Double( lerp( x1, x2, t ), lerp( y1, y2, t ) );
    }
    
    /**
     * Get a font metrics of a font.
     * 
     * @param font Font to be used
     * @return The font FontMetrics
     */
    public static FontMetrics getFontMetrics( Font font ) {
        return new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ).getGraphics().getFontMetrics( font );
    }
    
    /**
     * Get a line metrics of text using a font.
     * 
     * @param text text used to perform calculations
     * @param font Font to be used
     * @return The text LineMetrics
     */
    public static LineMetrics getLineMetrics( String text, Font font ) {
        Graphics2D g2d = (Graphics2D) new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ).getGraphics();
        return font.getLineMetrics( text, g2d.getFontRenderContext() );
    }
    
}
