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
    
    public static final int TRANSITION_CP_RADIUS = 5;
    public static final int TRANSITION_CP_RADIUS_EQUARED = 
            TRANSITION_CP_RADIUS * TRANSITION_CP_RADIUS;
    public static final int TRANSITION_CP_DIAMETER = TRANSITION_CP_RADIUS * 2;
    
    public static final Color TRANSITION_PTG_COLOR = 
            new Color( 0, 102, 153, 220 );
    public static final Color TRANSITION_CP_COLOR = 
            new Color( 150, 150, 150, 220 );
    public static final Color TRANSITION_CP_LEFT_COLOR = 
            new Color( 94, 2, 153, 220 );
    public static final Color TRANSITION_CP_RIGHT_COLOR = 
            new Color( 4, 153, 64, 220 );
    
    public static final BasicStroke TRANSITION_STROKE = 
            new BasicStroke( 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND );
    public static final BasicStroke TRANSITION_CP_STROKE = 
            new BasicStroke( 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1
                    , new float[]{ 5, 5 }, 0 );
    
    
}
