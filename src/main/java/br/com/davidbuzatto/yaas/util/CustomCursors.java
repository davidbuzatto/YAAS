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
package br.com.davidbuzatto.yaas.util;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Custom cursors.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class CustomCursors {
    
    public static final Cursor MOVE_CURSOR;
    
    static {
        
        BufferedImage imgMove = new BufferedImage( 
                32, 32, BufferedImage.TYPE_INT_ARGB );
        
        try {
            imgMove.getGraphics().drawImage( 
                    ImageIO.read( new CustomCursors().getClass().getResource( 
                            "/cursor_openhand.png" ) ), 0, 0, null );
        } catch ( IOException exc ) {
            Utils.showException( exc );
        }
        
        MOVE_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor( 
                imgMove, new Point( 7, 7 ), "move" );
        
    }
            
}
