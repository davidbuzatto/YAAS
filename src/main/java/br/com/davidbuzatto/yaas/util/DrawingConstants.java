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

/**
 * Drawing constants.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface DrawingConstants {
    
    public static final Font DEFAULT_FONT = new Font( Font.MONOSPACED, Font.BOLD, 18 );
    public static final Font DEFAULT_TABLE_FONT = new Font( Font.MONOSPACED, Font.BOLD, 16 );
    
    public static final BasicStroke DRAW_PANEL_STROKE = new BasicStroke( 2 );
    
    public static final int STATE_RADIUS = 25;
    public static final int STATE_RADIUS_SQUARED = STATE_RADIUS * STATE_RADIUS;
    public static final int STATE_DIAMETER = STATE_RADIUS * 2;
    public static final BasicStroke STATE_STROKE = new BasicStroke( 2 );
    
    public static final Color STATE_MOUSE_HOVER_STROKE_COLOR = 
            new Color( 0, 102, 153 );
    public static final Color STATE_MOUSE_HOVER_FILL_COLOR = 
            new Color( 157, 222, 255 );
    public static final Color STATE_SELECTED_STROKE_COLOR = 
            new Color( 0, 102, 153 );
    public static final Color STATE_SELECTED_FILL_COLOR = 
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
    public static final Color TRANSITION_SELECTED_STROKE_COLOR = 
            new Color( 0, 102, 153, 220 );
    public static final Color TRANSITION_SELECTED_FILL_COLOR = 
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
    
    public static final BasicStroke DEFAULT_ARROW_STROKE = new BasicStroke( 
            2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND );
    
}
