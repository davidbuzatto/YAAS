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
package br.com.davidbuzatto.yaas.gui;

/**
 * Encapsulates zoom related data.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ZoomFacility {
    
    private static double[] zoomFactors = { 
        0.1, 0.2, 0.3, 0.4, 0.5, 
        0.6, 0.7, 0.8, 0.9, 1.0, 
        1.5, 2.0, 2.5, 3.0, 3.5, 
        4.0, 4.5, 5.0 };
    
    private int currentZoomFactor;
    
    public ZoomFacility() {
        this.currentZoomFactor = 9; // map to the default zoom factor of 1.0
    }
    
    public void zoomIn() {
        if ( currentZoomFactor < zoomFactors.length - 1 ) {
            currentZoomFactor++;
        }
    }
    
    public void zoomOut() {
        if ( currentZoomFactor > 0 ) {
            currentZoomFactor--;
        }
    }
    
    public boolean canZoomIn() {
        return currentZoomFactor < zoomFactors.length - 1;
    }
    
    public boolean canZoomOut() {
        return currentZoomFactor > 0;
    }
    
    public int screenToWorld( int value ) {
        return (int) ( value / getZoomFactor() );
    }
    
    public int screenToWorld( double value ) {
        return (int) ( value / getZoomFactor() );
    }
    
    public int worldToScreen( int value ) {
        return (int) ( value * getZoomFactor() );
    }
    
    public int worldToScreen( double value ) {
        return (int) ( value * getZoomFactor() );
    }
            
    public double getZoomFactor() {
        return zoomFactors[currentZoomFactor];
    }

    @Override
    public String toString() {
        return "ZoomFacility{" + "currentZoomFactor=" + currentZoomFactor + '}';
    }
    
}
