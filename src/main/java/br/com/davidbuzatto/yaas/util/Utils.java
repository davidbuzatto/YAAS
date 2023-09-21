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

import br.com.davidbuzatto.yaas.gui.fa.FA;
import br.com.davidbuzatto.yaas.gui.fa.FAState;
import br.com.davidbuzatto.yaas.gui.fa.FAType;
import br.com.davidbuzatto.yaas.gui.fa.table.FATransitionFunctionTableModel;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 * Utilitary methods.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Utils {
    
    private static Model mavenModel;
    
    /**
     * Return the maven model to information extraction.
     * 
     * @return  The maven model.
     */
    public static Model getMavenModel() {
        
        MavenXpp3Reader reader = new MavenXpp3Reader();
        
        if ( mavenModel == null ) {
            try {
                if ( ( new File( "pom.xml" )).exists() ) {
                    mavenModel = reader.read( new FileReader( "pom.xml" ) );
                } else {
                    mavenModel = reader.read(
                        new InputStreamReader(
                            Utils.class.getResourceAsStream(
                                "/META-INF/maven/br.com.davidbuzatto/YAAS/pom.xml"
                            )
                        )
                    );
                }
            } catch ( IOException | XmlPullParserException exc ) {
                showException( exc );
            }
        }
        
        return mavenModel;
      
    }
    
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
    
    /**
     * Sorts a list of symbols taking into consideration the empty string
     * symbol (small epsilon).
     * 
     * @param symbols A list of symbols to be sorted.
     */
    public static void customSymbolsSort( List<Character> symbols ) {
        
        boolean hasEpsilon = false;
        
        if ( symbols.contains( CharacterConstants.SMALL_EPSILON ) ) {
            hasEpsilon = true;
            symbols.remove( CharacterConstants.SMALL_EPSILON );
        }
        
        Collections.sort( symbols );
        
        if ( hasEpsilon ) {
            symbols.add( 0, CharacterConstants.SMALL_EPSILON );
        }
        
    }
    
    /**
     * Show exception details using a JOptionPane.
     * 
     * @param exc The exception to be processed.
     */
    public static void showException( Exception exc ) {
        
        try ( StringWriter sw = new StringWriter();
              PrintWriter pw = new PrintWriter( sw ) ) {
            
            exc.printStackTrace( pw );
            
            JOptionPane.showMessageDialog( 
                    null, 
                    new JScrollPane( new JTextArea( sw.toString(), 15, 40 ) ), 
                    "ERROR", 
                    JOptionPane.ERROR_MESSAGE );
            
        } catch ( IOException iexc ) {
            iexc.printStackTrace();
        }
        
    }
    
    /**
     * Resizes columns width based in their contents.
     * Based in: https://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths
     * 
     * @param table The table that contains the columns to be resized.
     * @param maxWidth The max width to use.
     */
    public static void resizeColumnWidth( JTable table, int maxWidth ) {
        
        final TableColumnModel columnModel = table.getColumnModel();
        
        for ( int column = 0; column < table.getColumnCount(); column++ ) {
            
            double width = table.getTableHeader().getHeaderRect( column ).getWidth();
            
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer( row, column );
                Component comp = table.prepareRenderer( renderer, row, column );
                width = Math.max( comp.getPreferredSize().width + 1, width );
            }
            
            if ( width > maxWidth ) {
                width = maxWidth;
            }
            
            columnModel.getColumn( column ).setPreferredWidth( (int) width );
            
        }
        
    }
    
    /**
     * Creates the table model for the transition function representation of
     * finite automata.
     * 
     * @param fa The finite automata to be used.
     * @return The table model.
     */
    public static FATransitionFunctionTableModel createFATransitionFunctionTableModel( FA fa ) {
        
        FATransitionFunctionTableModel tm = new FATransitionFunctionTableModel();
        List<Character> symbols = new ArrayList<>();
        
        if ( fa.getType() == FAType.ENFA ) {
            symbols.add( CharacterConstants.EMPTY_STRING );
        }
        symbols.addAll( fa.getAlphabet() );
        
        for ( char s : symbols ) {
            tm.getSymbols().add( String.valueOf( s ) );
        }
        
        Map<FAState, Map<Character, List<FAState>>> delta = fa.getDelta();
        
        for ( Map.Entry<FAState, Map<Character, List<FAState>>> entry : delta.entrySet() ) {
            
            FAState state = entry.getKey();
            
            String eStr = "";
            if ( state.isAccepting()) {
                eStr += "*";
            }
            if ( state.isInitial() ) {
                eStr += CharacterConstants.ARROW_RIGHT;
            }
            eStr += state;
            
            tm.getStates().add( eStr );
            
            List<String> data = new ArrayList<>();
            data.add( eStr );
            
            for ( char symbol : symbols ) {
                
                List<FAState> targetStates = entry.getValue().get( symbol );
                
                if ( targetStates == null || targetStates.isEmpty() ) {
                    data.add( CharacterConstants.EMPTY_SET.toString() );
                    tm.setPartial( true );
                } else {
                    
                    String targetStr;
                    
                    if ( fa.getType() == FAType.DFA ) {
                        targetStr = targetStates.get( 0 ).toString();
                    } else {
                        targetStr = "{ ";
                        boolean first = true;
                        for ( FAState ee : targetStates ) {
                            if ( !first ) {
                                targetStr += ", ";
                            }
                            targetStr += ee.toString();
                            first = false;
                        }
                        targetStr += " }";
                    }
                    
                    data.add( targetStr );
                    
                }
                
            }
            
            tm.getData().add( data );
            
        }
        
        return tm;
        
    }
    
    /**
     * Show a custom input dialog for transition symbols.
     * 
     * @param parentComponent The parent component.
     * @param message The message above the input text.
     * @param title The title of the dialog.
     * @param initialValue The initial value
     * 
     * @return The input.
     */
    public static String showInputDialogEmptyString( 
            Component parentComponent, 
            String message, 
            String title, 
            String initialValue ) {
        
        JLabel lbl = new JLabel( message + ":" );
        JTextField txtField = new JTextField( 20 );
        JCheckBox chkBox = new JCheckBox( "Add " + CharacterConstants.EMPTY_STRING );
        Component[] components = new Component[]{ lbl, txtField, chkBox };
        
        txtField.addAncestorListener( new AncestorListener(){
            @Override
            public void ancestorAdded( AncestorEvent evt ) {
                evt.getComponent().requestFocusInWindow();
            }
            @Override
            public void ancestorRemoved( AncestorEvent evt ) {
            }
            @Override
            public void ancestorMoved( AncestorEvent evt ) {
            }
        });
        
        if ( initialValue != null ) {
            if ( initialValue.contains( CharacterConstants.EMPTY_STRING.toString() ) ) {
                initialValue = initialValue.replace( 
                        CharacterConstants.EMPTY_STRING.toString(), "" );
                chkBox.setSelected( true );
            }
            txtField.setText( initialValue.trim() );
        }
        
        if ( JOptionPane.showOptionDialog( 
                parentComponent, 
                components, 
                title, 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                new String[]{ "OK", "Cancel" }, 
                "OK" ) == JOptionPane.OK_OPTION ) {
            
            String input = "";
            
            if ( chkBox.isSelected() ) {
                input += CharacterConstants.EMPTY_STRING;
            }
            
            input += txtField.getText();
            
            return input.trim();
            
        }
        
        return null;
        
    }
    
    /**
     * Shows a message to warn about the no implementation of a functinality.
     */
    public static void showNotImplementedYetMessage() {
        JOptionPane.showMessageDialog( null, "Not implemented yet!" );
    }
    
}
