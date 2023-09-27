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

import java.awt.BasicStroke;
import java.io.Serializable;

/**
 * A serializable representation of a java.awt.BasicStroke.
 *
 * @author Prof. Dr. David Buzatto
 */
public class SerializableBasicStroke implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    public static final int CAP_BUTT = BasicStroke.CAP_BUTT;
    public static final int CAP_ROUND = BasicStroke.CAP_ROUND;
    public static final int CAP_SQUARE = BasicStroke.CAP_SQUARE;
    public static final int JOIN_BEVEL = BasicStroke.JOIN_BEVEL;
    public static final int JOIN_MITER = BasicStroke.JOIN_MITER;
    public static final int JOIN_ROUND = BasicStroke.JOIN_ROUND;
    
    private float width;
    private int cap;
    private int join;
    private float miterLimit;
    private float[] dash;
    private float dashPhase;

    private transient boolean updated;
    private transient BasicStroke basicStroke;

    public SerializableBasicStroke() {
        this( 1.0f, CAP_SQUARE, JOIN_MITER, 10.0f, null, 0.0f );
    }

    public SerializableBasicStroke( float width ) {
        this( width, CAP_SQUARE, JOIN_MITER, 10.0f, null, 0.0f );
    }

    public SerializableBasicStroke(
            float width,
            int cap,
            int join ) {
        this( width, cap, join, 10.0f, null, 0.0f );
    }

    public SerializableBasicStroke(
            float width,
            int cap,
            int join,
            float miterLimit ) {
        this( width, cap, join, miterLimit, null, 0.0f );
    }

    public SerializableBasicStroke(
            float width,
            int cap,
            int join,
            float miterLimit,
            float[] dash,
            float dashPhase ) {

        if ( width < 0.0f ) {
            throw new IllegalArgumentException( "negative width" );
        }
        if ( cap != CAP_BUTT && cap != CAP_ROUND && cap != CAP_SQUARE ) {
            throw new IllegalArgumentException( "illegal end cap value" );
        }
        if ( join == JOIN_MITER ) {
            if ( miterLimit < 1.0f ) {
                throw new IllegalArgumentException( "miter limit < 1" );
            }
        } else if ( join != JOIN_ROUND && join != JOIN_BEVEL ) {
            throw new IllegalArgumentException( "illegal line join value" );
        }
        if ( dash != null ) {
            if ( dashPhase < 0.0f ) {
                throw new IllegalArgumentException( "negative dash phase" );
            }
            boolean allzero = true;
            for ( int i = 0; i < dash.length; i++ ) {
                float d = dash[i];
                if ( d > 0.0 ) {
                    allzero = false;
                } else if ( d < 0.0 ) {
                    throw new IllegalArgumentException( "negative dash length" );
                }
            }
            if ( allzero ) {
                throw new IllegalArgumentException( "dash lengths all zero" );
            }
        }

        this.width = width;
        this.cap = cap;
        this.join = join;
        this.miterLimit = miterLimit;
        if ( dash != null ) {
            this.dash = dash.clone();
        }
        this.dashPhase = dashPhase;

        createBasicStroke();

    }

    public float getWidth() {
        return width;
    }

    public void setWidth( float width ) {
        this.width = width;
        updated = false;
    }

    public int getCap() {
        return cap;
    }

    public void setCap( int cap ) {
        this.cap = cap;
        updated = false;
    }

    public int getJoin() {
        return join;
    }

    public void setJoin( int join ) {
        this.join = join;
        updated = false;
    }

    public float getMiterLimit() {
        return miterLimit;
    }

    public void setMiterLimit( float miterLimit ) {
        this.miterLimit = miterLimit;
        updated = false;
    }

    public float[] getDash() {
        return dash;
    }

    public void setDash( float[] dash ) {
        this.dash = dash;
        updated = false;
    }

    public float getDashPhase() {
        return dashPhase;
    }

    public void setDashPhase( float dashPhase ) {
        this.dashPhase = dashPhase;
        updated = false;
    }

    private void createBasicStroke() {
        basicStroke = new BasicStroke(
                width, cap, join, miterLimit, dash, dashPhase );
    }

    public BasicStroke getBasicStroke() {

        if ( basicStroke == null || !updated ) {
            createBasicStroke();
            updated = true;
        }

        return basicStroke;

    }
    
    @Override
    @SuppressWarnings( "unchecked" )
    public Object clone() throws CloneNotSupportedException {
        
        SerializableBasicStroke c = (SerializableBasicStroke) super.clone();
        
        c.width = width;
        c.cap = cap;
        c.join = join;
        c.miterLimit = miterLimit;
        
        if ( dash != null ) {
            c.dash = dash.clone();
        }
        
        c.dashPhase = dashPhase;
        
        return c;
        
    }

}
