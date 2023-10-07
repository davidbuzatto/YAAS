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
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The line of a PDAID tree.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAIDLine extends AbstractGeometricForm {

    private Arrow arrow;

    public PDAIDLine( int x1, int y1, int x2, int y2, Color strokeColor ) {

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.strokeColor = strokeColor;

        arrow = new Arrow();
        arrow.setX1( x2 );
        arrow.setY1( y2 );
        arrow.setStrokeColor( strokeColor );
        arrow.setAngle( Math.atan2( y2 - y1, x2 - x1 ) );

    }

    @Override
    public void move( int xAmount, int yAmount ) {
        super.move( xAmount, yAmount );
        arrow.move( xAmount, yAmount );
    }

    @Override
    public void draw( Graphics2D g2d ) {
        g2d = (Graphics2D) g2d.create();
        g2d.setColor( strokeColor );
        g2d.drawLine( x1, y1, x2, y2 );
        arrow.draw( g2d );
        g2d.dispose();
    }

    @Override
    public boolean intersects( int x, int y ) {
        return false;
    }

}
