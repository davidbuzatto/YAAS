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

import br.com.davidbuzatto.yaas.model.fa.FA;
import br.com.davidbuzatto.yaas.model.fa.FAState;
import br.com.davidbuzatto.yaas.model.fa.FAType;
import br.com.davidbuzatto.yaas.gui.fa.table.FATransitionFunctionTableModel;
import br.com.davidbuzatto.yaas.gui.pda.table.PDATransitionFunctionTableModel;
import br.com.davidbuzatto.yaas.gui.tm.table.TMTransitionFunctionTableModel;
import br.com.davidbuzatto.yaas.model.pda.PDA;
import br.com.davidbuzatto.yaas.model.pda.PDAOperation;
import br.com.davidbuzatto.yaas.model.pda.PDAOperationType;
import br.com.davidbuzatto.yaas.model.pda.PDAState;
import br.com.davidbuzatto.yaas.model.pda.PDATransition;
import br.com.davidbuzatto.yaas.model.pda.PDAType;
import br.com.davidbuzatto.yaas.model.tm.TM;
import br.com.davidbuzatto.yaas.model.tm.TMOperation;
import br.com.davidbuzatto.yaas.model.tm.TMMovementType;
import br.com.davidbuzatto.yaas.model.tm.TMState;
import br.com.davidbuzatto.yaas.model.tm.TMTransition;
import br.com.davidbuzatto.yaas.model.tm.TMType;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.SplashScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
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
        
        FATransitionFunctionTableModel tModel = new FATransitionFunctionTableModel();
        List<Character> symbols = new ArrayList<>();
        
        if ( fa.getType() == FAType.ENFA ) {
            symbols.add( CharacterConstants.EMPTY_STRING );
        }
        symbols.addAll( fa.getAlphabet() );
        
        for ( char s : symbols ) {
            tModel.getSymbols().add( String.valueOf( s ) );
        }
        
        Map<FAState, Map<Character, List<FAState>>> delta = fa.getDelta();
        
        for ( Map.Entry<FAState, Map<Character, List<FAState>>> entry : delta.entrySet() ) {
            
            FAState state = entry.getKey();
            
            String eStr = "";
            if ( state.isFinal()) {
                eStr += "*";
            }
            if ( state.isInitial() ) {
                eStr += CharacterConstants.ARROW_RIGHT;
            }
            eStr += state;
            
            tModel.getStates().add( eStr );
            
            List<String> data = new ArrayList<>();
            data.add( eStr );
            
            for ( char symbol : symbols ) {
                
                List<FAState> targetStates = entry.getValue().get( symbol );
                
                if ( targetStates == null || targetStates.isEmpty() ) {
                    data.add( CharacterConstants.EMPTY_SET.toString() );
                    tModel.setPartial( true );
                } else {
                    
                    String targetStr;
                    
                    if ( fa.getType() == FAType.DFA ) {
                        targetStr = targetStates.get( 0 ).toString();
                    } else {
                        targetStr = "{";
                        boolean first = true;
                        for ( FAState ee : targetStates ) {
                            if ( !first ) {
                                targetStr += ", ";
                            }
                            targetStr += ee.toString();
                            first = false;
                        }
                        targetStr += "}";
                    }
                    
                    data.add( targetStr );
                    
                }
                
            }
            
            tModel.getData().add( data );
            
        }
        
        return tModel;
        
    }
    
    /**
     * Creates the table model for the transition function representation of
     * pushdown automata.
     * 
     * @param pda The pushdown automaton to be used.
     * @return The table model.
     */
    public static PDATransitionFunctionTableModel createPDATransitionFunctionTableModel( PDA pda ) {
        
        PDATransitionFunctionTableModel tModel = new PDATransitionFunctionTableModel();
        List<Character> symbols = new ArrayList<>();
        Map<String, Integer> operationIndexes = new HashMap<>();
        String eSet = CharacterConstants.EMPTY_SET.toString();
        
        symbols.add( CharacterConstants.EMPTY_STRING );
        symbols.addAll( pda.getAlphabet() );
        
        int index = 1;
        for ( char s : symbols ) {
            for ( char ss : pda.getStackAlphabet() ) {
                String op = s + "," + ss;
                tModel.getOperations().add( op );
                operationIndexes.put( op, index++ );
            }
        }
        
        Map<PDAState, List<PDATransition>> delta = pda.getDelta();
        
        for ( Map.Entry<PDAState, List<PDATransition>> entry : delta.entrySet() ) {
            
            PDAState state = entry.getKey();
            
            String eStr = "";
            if ( state.isFinal()) {
                eStr += "*";
            }
            if ( state.isInitial() ) {
                eStr += CharacterConstants.ARROW_RIGHT;
            }
            eStr += state;
            
            tModel.getStates().add( eStr );
            
            List<String> data = new ArrayList<>();
            data.add( eStr );
            
            for ( int i = 0; i < tModel.getOperations().size(); i++ ) {
                data.add( eSet );
            }
            
            for ( PDATransition t : entry.getValue() ) {
                
                for ( PDAOperation o : t.getOperations() ) {
                    
                    if ( o.getTop() == CharacterConstants.EMPTY_STRING ) {
                        break;
                    }
                    
                    String op = o.getSymbol() + "," + o.getTop();
                    int ind = operationIndexes.get( op );
                    
                    String value = "(";
                    
                    value += t.getTargetState().toString() + ",";
                    
                    String stackData = "";
                    if ( o.getType() == PDAOperationType.PUSH || o.getType() == PDAOperationType.REPLACE ) {
                        for ( int i = o.getSymbolsToPush().size()-1; i >= 0; i-- ) {
                            stackData += o.getSymbolsToPush().get( i );
                        }
                        stackData = stackData.trim();
                    }
                    
                    switch ( o.getType() ) {
                        case DO_NOTHING:
                            value += o.getTop();
                            break;
                        case POP:
                            value += CharacterConstants.EMPTY_STRING.toString();
                            break;
                        case PUSH:
                            value += stackData + o.getTop();
                            break;
                        case REPLACE:
                            value += stackData;
                            break;
                    }
                    
                    value += ")";
                    
                    String cData = data.get( ind );
                    if ( cData.equals( eSet ) ) {
                        data.set( ind, value );
                    } else {
                        data.set( ind, cData + ", " + value );
                    }
                    
                    
                }
                
            }
            
            for ( int i = 1; i < data.size(); i++ ) {
                if ( !data.get( i ).equals( eSet ) ) {
                    if ( pda.getType() == PDAType.DPDA ) {
                        data.set( i, data.get( i ) );
                    } else {
                        data.set( i, "{" + data.get( i ) + "}" );
                    }
                } else {
                    tModel.setPartial( true );
                }
            }
            
            tModel.getData().add( data );
            
        }
        
        return tModel;
        
    }
    
    /**
     * Creates the table model for the transition function representation of
     * Turing Machines.
     * 
     * @param tm The turing machine to be used.
     * @return The table model.
     */
    public static TMTransitionFunctionTableModel createTMTransitionFunctionTableModel( TM tm ) {
        
        TMTransitionFunctionTableModel tModel = new TMTransitionFunctionTableModel();
        List<Character> symbols = new ArrayList<>();
        Map<Character, Integer> operationIndexes = new HashMap<>();
        String eSet = CharacterConstants.EMPTY_SET.toString();
        
        boolean alpContainsB = false;
        for ( char c : tm.getAlphabet() ) {
            if ( c != CharacterConstants.BLANK_TAPE_SYMBOL ) {
                symbols.add( c );
            } else {
                alpContainsB = true;
            }
        }
        if ( alpContainsB ) {
            symbols.add( CharacterConstants.BLANK_TAPE_SYMBOL );
        }
        
        int index = 1;
        for ( char s : symbols ) {
            tModel.getOperations().add( String.valueOf( s ) );
            operationIndexes.put( s, index++ );
        }
        
        Map<TMState, List<TMTransition>> delta = tm.getDelta();
        
        for ( Map.Entry<TMState, List<TMTransition>> entry : delta.entrySet() ) {
            
            TMState state = entry.getKey();
            
            String eStr = "";
            if ( state.isFinal()) {
                eStr += "*";
            }
            if ( state.isInitial() ) {
                eStr += CharacterConstants.ARROW_RIGHT;
            }
            eStr += state;
            
            tModel.getStates().add( eStr );
            
            List<String> data = new ArrayList<>();
            data.add( eStr );
            
            for ( int i = 0; i < tModel.getOperations().size(); i++ ) {
                data.add( eSet );
            }
            
            for ( TMTransition t : entry.getValue() ) {
                
                for ( TMOperation o : t.getOperations() ) {
                    
                    int ind = operationIndexes.get( o.getReadSymbol() );
                    String value = String.format("(%s,%c,%c)", 
                            t.getTargetState(), 
                            o.getWriteSymbol(), 
                            o.getType() == TMMovementType.MOVE_RIGHT ? 'R' : 'L' );
                    
                    String cData = data.get( ind );
                    if ( cData.equals( eSet ) ) {
                        data.set( ind, value );
                    } else {
                        data.set( ind, cData + ", " + value );
                    }
                    
                }
                
            }
            
            for ( int i = 1; i < data.size(); i++ ) {
                if ( !data.get( i ).equals( eSet ) ) {
                    if ( tm.getType() == TMType.DTM ) {
                        data.set( i, data.get( i ) );
                    } else {
                        data.set( i, "{" + data.get( i ) + "}" );
                    }
                } else {
                    tModel.setPartial( true );
                }
            }
            
            tModel.getData().add( data );
            
        }
        
        return tModel;
        
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
     * Show a custom input dialog for pda operations.
     * 
     * @param parentComponent The parent component.
     * @param title The title of the dialog.
     * @param startingSymbol The stack starting symbol of the PDA.
     * @param op
     * 
     * @return The op PDAOperation with it was updated, a new PDAOperation if
     * a new one was created or null if the dialog was canceled.
     */
    public static PDAOperation showInputDialogNewPDAOperation( 
            Component parentComponent, 
            String title,
            char startingSymbol,
            PDAOperation op ) {
        
        JPanel panel = new JPanel();
        panel.setLayout( new GridBagLayout() );
        
        JLabel lblTS = new JLabel( "Transition symbol:" );
        lblTS.setHorizontalAlignment( SwingConstants.RIGHT );
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets( 5, 5, 5, 5 );
        panel.add( lblTS, gridBagConstraints );
        
        JLabel lblSTT = new JLabel( "Stack top:" );
        lblSTT.setHorizontalAlignment( SwingConstants.RIGHT );
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets( 5, 5, 5, 5 );
        panel.add( lblSTT, gridBagConstraints );

        JLabel lblST = new JLabel( "Stack operation:" );
        lblST.setHorizontalAlignment( SwingConstants.RIGHT );
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets( 5, 5, 5, 5 );
        panel.add( lblST, gridBagConstraints );

        JLabel lblSP = new JLabel( "Symbol(s) to push/replace:" );
        lblSP.setHorizontalAlignment( SwingConstants.RIGHT );
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets( 5, 5, 5, 5 );
        panel.add( lblSP, gridBagConstraints);

        JPanel tsPanel = new JPanel();
        tsPanel.setLayout( new BoxLayout( tsPanel, BoxLayout.X_AXIS ) );
        JTextField txtTS = new JTextField( 5 );
        txtTS.setAlignmentX( Component.LEFT_ALIGNMENT );
        JCheckBox checkE = new JCheckBox( CharacterConstants.EMPTY_STRING.toString() );
        checkE.setAlignmentX( Component.LEFT_ALIGNMENT );
        checkE.setToolTipText( CharacterConstants.EMPTY_STRING + "-transition" );
        tsPanel.add( txtTS );
        tsPanel.add( checkE );
        
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets( 5, 5, 5, 5 );
        panel.add( tsPanel, gridBagConstraints );
        
        JPanel stPanel = new JPanel();
        stPanel.setAlignmentX( JPanel.LEFT_ALIGNMENT );
        stPanel.setLayout( new BoxLayout( stPanel, BoxLayout.X_AXIS ) );
        JTextField txtST = new JTextField( 5 );
        JCheckBox checkEmptyST = new JCheckBox( CharacterConstants.EMPTY_STRING.toString() );
        checkEmptyST.setToolTipText( "Empty stack" );
        JCheckBox checkStartingST = new JCheckBox( String.valueOf( startingSymbol ) );
        checkStartingST.setToolTipText( "Starting symbol" );
        stPanel.add( txtST );
        stPanel.add( checkEmptyST );
        stPanel.add( checkStartingST );
        
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.insets = new Insets( 5, 5, 5, 5 );
        panel.add( stPanel, gridBagConstraints );
        
        JTextField txtSP = new JTextField();
        txtSP.setEnabled( false );
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets( 5, 5, 5, 5 );
        panel.add( txtSP, gridBagConstraints );

        JRadioButton rNothing = new JRadioButton( "Do nothing" );
        rNothing.setSelected( true );
        JRadioButton rPop = new JRadioButton( "Pop" );
        JRadioButton rPush = new JRadioButton( "Push" );
        JRadioButton rReplace = new JRadioButton( "Replace" );
        
        ButtonGroup bg = new ButtonGroup();
        bg.add( rNothing );
        bg.add( rPop );
        bg.add( rPush );
        bg.add( rReplace );
        
        JPanel stOpPanel = new JPanel();
        stOpPanel.setLayout( new BoxLayout( stOpPanel, BoxLayout.PAGE_AXIS ) );
        stOpPanel.add( rNothing );
        stOpPanel.add( rPop );
        stOpPanel.add( rPush );
        stOpPanel.add( rReplace );

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add( stOpPanel, gridBagConstraints );
        
        Component[] components = new Component[]{ panel };
        
        txtTS.addAncestorListener( new AncestorListener(){
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
        
        ActionListener ac = new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if ( rNothing.isSelected() || rPop.isSelected() ) {
                    txtSP.setEnabled( false );
                    txtSP.setText( "" );
                } else {
                    txtSP.setEnabled( true );
                    txtSP.requestFocus();
                }
            }
        };
                
        rNothing.addActionListener( ac );
        rPop.addActionListener( ac );
        rPush.addActionListener( ac );
        rReplace.addActionListener( ac );
        
        checkE.addActionListener( new ActionListener(){
            @Override
            public void actionPerformed( ActionEvent e ) {
                if ( checkE.isSelected() ) {
                    txtTS.setEnabled( false );
                    txtTS.setText( "" );
                } else {
                    txtTS.setEnabled( true );
                    txtTS.requestFocus();
                }
            }
        });
        
        checkEmptyST.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if ( checkEmptyST.isSelected() ) {
                    txtST.setEnabled( false );
                    txtST.setText( "" );
                    checkStartingST.setSelected( false );
                } else {
                    txtST.setEnabled( true );
                    txtST.requestFocus();
                }
            }
        });
        
        checkStartingST.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if ( checkStartingST.isSelected() ) {
                    txtST.setEnabled( false );
                    txtST.setText( "" );
                    checkEmptyST.setSelected( false );
                } else {
                    txtST.setEnabled( true );
                    txtST.requestFocus();
                }
            }
        });
        
        if ( op != null ) {
            
            if ( op.getSymbol() == CharacterConstants.EMPTY_STRING ) {
                checkE.doClick();
            } else {
                txtTS.setText( String.valueOf( op.getSymbol() ) );
            }
            
            if ( op.getTop() == CharacterConstants.EMPTY_STRING ) {
                checkEmptyST.doClick();
            } else if ( op.getTop()== startingSymbol ) {
                checkStartingST.doClick();
            } else {
                txtST.setText( String.valueOf( op.getTop() ) );
            }
            
            switch ( op.getType() ) {
                case DO_NOTHING:
                    rNothing.doClick();
                    break;
                case POP:
                    rPop.doClick();
                    break;
                case PUSH:
                    rPush.doClick();
                    break;
                case REPLACE:
                    rReplace.doClick();
                    break;
            }
            
            if ( op.getType() == PDAOperationType.PUSH || op.getType() == PDAOperationType.REPLACE ) {
                String s = "";
                for ( char c : op.getSymbolsToPush() ) {
                    s += c + " ";
                }
                txtSP.setText( s.trim() );
            }
            
        }
        
        while ( true ) {
            
            if ( JOptionPane.showOptionDialog( 
                    parentComponent, 
                    components, 
                    title, 
                    JOptionPane.OK_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    new String[]{ "OK", "Cancel" }, 
                    "OK" ) == JOptionPane.OK_OPTION ) {

                boolean errorTS = false;
                boolean errorST = false;
                boolean errorSP = false;

                char symbol = CharacterConstants.EMPTY_STRING;
                if ( !checkE.isSelected() ) {
                    String inputTS = txtTS.getText().trim();
                    if ( !inputTS.isEmpty() ) {
                        symbol = txtTS.getText().trim().charAt( 0 );
                    } else {
                        errorTS = true;
                    }
                }

                char stackTop = 'v';
                if ( checkEmptyST.isSelected() ) {
                    stackTop = CharacterConstants.EMPTY_STRING;
                } else if ( checkStartingST.isSelected() ) {
                    stackTop = startingSymbol;
                } else {
                    String inputST = txtST.getText().trim();
                    if ( !inputST.isEmpty() ) {
                        stackTop = inputST.charAt( 0 );
                    } else {
                        errorST = true;
                    }
                }

                String pushSymbols = txtSP.getText().replace( " ", "" ).trim();
                PDAOperationType type;

                if ( rNothing.isSelected() ) {
                    type = PDAOperationType.DO_NOTHING;
                } else if ( rPop.isSelected() ) {
                    type = PDAOperationType.POP;
                } else if ( rPush.isSelected() ) {
                    type = PDAOperationType.PUSH;
                } else {
                    type = PDAOperationType.REPLACE;
                }

                if ( pushSymbols.isEmpty() && ( type == PDAOperationType.PUSH || type == PDAOperationType.REPLACE ) ) {
                    errorSP = true;
                }

                String error = "";
                if ( errorTS ) {
                    error += "You must set a transition symbol!";
                }
                if ( errorST ) {
                    error += "\nYou must set a stack top!";
                }
                if ( errorSP ) {
                    error += String.format( 
                            "\nYou must add at least one symbol to %s!", 
                            type == PDAOperationType.PUSH ? "push" : "replace" );
                }

                if ( errorTS || errorST || errorSP ) {
                    showErrorMessage( parentComponent, error.trim() );
                } else {
                    if ( op != null ) {
                        op.setSymbol( symbol );
                        op.setTop( stackTop );
                        op.setType( type );
                        List<Character> sp = new ArrayList<>();
                        for ( char c : pushSymbols.toCharArray() ) {
                            sp.add( c );
                        }
                        op.setSymbolsToPush( sp );
                        return op;
                    } else {
                        return new PDAOperation( symbol, stackTop, type, pushSymbols.toCharArray() );
                    }
                }

            } else {
                break;
            }
        
        }
        
        return null;
        
    }
    
    /**
     * Show a custom input dialog for tm operations.
     * 
     * @param parentComponent The parent component.
     * @param title The title of the dialog.
     * @param op
     * 
     * @return The op TMOperation with it was updated, a new TMOperation if
     * a new one was created or null if the dialog was canceled.
     */
    public static TMOperation showInputDialogNewTMOperation( 
            Component parentComponent, 
            String title,
            TMOperation op ) {
        
        JPanel panel = new JPanel();
        panel.setLayout( new GridBagLayout() );
        
        JLabel lblReadSymbol = new JLabel( "Read symbol:" );
        lblReadSymbol.setHorizontalAlignment( SwingConstants.RIGHT );
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets( 5, 5, 5, 5 );
        panel.add( lblReadSymbol, gridBagConstraints );
        
        JLabel lblWriteSymbol = new JLabel( "Write symbol:" );
        lblWriteSymbol.setHorizontalAlignment( SwingConstants.RIGHT );
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets( 5, 5, 5, 5 );
        panel.add( lblWriteSymbol, gridBagConstraints );

        JLabel lblMove = new JLabel( "Move to:" );
        lblMove.setHorizontalAlignment( SwingConstants.RIGHT );
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets( 5, 5, 5, 5 );
        panel.add( lblMove, gridBagConstraints );

        JPanel readSymbolPanel = new JPanel();
        readSymbolPanel.setAlignmentX( JPanel.LEFT_ALIGNMENT );
        readSymbolPanel.setLayout( new BoxLayout( readSymbolPanel, BoxLayout.X_AXIS ) );
        JTextField txtReadSymbol = new JTextField( 5 );
        JCheckBox checkReadBlankSymbol = new JCheckBox( CharacterConstants.BLANK_TAPE_SYMBOL.toString() );
        checkReadBlankSymbol.setToolTipText( "Blank tape symbol" );
        readSymbolPanel.add( txtReadSymbol );
        readSymbolPanel.add( checkReadBlankSymbol );
        
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.insets = new Insets( 5, 5, 5, 5 );
        panel.add( readSymbolPanel, gridBagConstraints );
        
        JPanel writeSymbolPanel = new JPanel();
        writeSymbolPanel.setAlignmentX( JPanel.LEFT_ALIGNMENT );
        writeSymbolPanel.setLayout( new BoxLayout( writeSymbolPanel, BoxLayout.X_AXIS ) );
        JTextField txtWriteSymbol = new JTextField( 5 );
        JCheckBox checkWriteBlankSymbol = new JCheckBox( CharacterConstants.BLANK_TAPE_SYMBOL.toString() );
        checkWriteBlankSymbol.setToolTipText( "Blank tape symbol" );
        writeSymbolPanel.add( txtWriteSymbol );
        writeSymbolPanel.add( checkWriteBlankSymbol );
        
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.insets = new Insets( 5, 5, 5, 5 );
        panel.add( writeSymbolPanel, gridBagConstraints );

        JRadioButton rMoveRight = new JRadioButton();
        JLabel lblMoveRight = new JLabel( new ImageIcon( 
                new Utils().getClass().getResource("/arrow_right.png" ) ) );
        rMoveRight.setToolTipText( "Right" );
        lblMoveRight.setToolTipText( "Right" );
        rMoveRight.setSelected( true );
        JRadioButton rMoveLeft = new JRadioButton();
        JLabel lblMoveLeft = new JLabel( new ImageIcon( 
                new Utils().getClass().getResource("/arrow_left.png" ) ) );
        rMoveLeft.setToolTipText( "Left" );
        lblMoveLeft.setToolTipText( "Left" );
        
        ButtonGroup bg = new ButtonGroup();
        bg.add( rMoveRight );
        bg.add( rMoveLeft );
        
        JPanel movePanel = new JPanel();
        movePanel.setLayout( new BoxLayout( movePanel, BoxLayout.LINE_AXIS ) );
        movePanel.add( rMoveRight );
        movePanel.add( lblMoveRight );
        movePanel.add( rMoveLeft );
        movePanel.add( lblMoveLeft );

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add( movePanel, gridBagConstraints );
        
        Component[] components = new Component[]{ panel };
        
        txtReadSymbol.addAncestorListener( new AncestorListener(){
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
        
        checkReadBlankSymbol.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if ( checkReadBlankSymbol.isSelected() ) {
                    txtReadSymbol.setEnabled( false );
                    txtReadSymbol.setText( "" );
                } else {
                    txtReadSymbol.setEnabled( true );
                    txtReadSymbol.requestFocus();
                }
            }
        });
        
        checkWriteBlankSymbol.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if ( checkWriteBlankSymbol.isSelected() ) {
                    txtWriteSymbol.setEnabled( false );
                    txtWriteSymbol.setText( "" );
                } else {
                    txtWriteSymbol.setEnabled( true );
                    txtWriteSymbol.requestFocus();
                }
            }
        });
        
        if ( op != null ) {
            
            txtReadSymbol.setText( String.valueOf( op.getReadSymbol() ) );
            
            if ( op.getReadSymbol() == CharacterConstants.BLANK_TAPE_SYMBOL ) {
                checkReadBlankSymbol.doClick();
            } else {
                txtReadSymbol.setText( String.valueOf( op.getReadSymbol() ) );
            }
            
            if ( op.getWriteSymbol() == CharacterConstants.BLANK_TAPE_SYMBOL ) {
                checkWriteBlankSymbol.doClick();
            } else {
                txtWriteSymbol.setText( String.valueOf( op.getWriteSymbol() ) );
            }
            
            switch ( op.getType() ) {
                case MOVE_RIGHT:
                    rMoveRight.doClick();
                    break;
                case MOVE_LEFT:
                    rMoveLeft.doClick();
                    break;
            }
            
        }
        
        while ( true ) {
            
            if ( JOptionPane.showOptionDialog( 
                    parentComponent, 
                    components, 
                    title, 
                    JOptionPane.OK_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    new String[]{ "OK", "Cancel" }, 
                    "OK" ) == JOptionPane.OK_OPTION ) {

                boolean errorRead = false;
                boolean errorWrite = false;

                char readSymbol = ' ';
                if ( checkReadBlankSymbol.isSelected() ) {
                    readSymbol = CharacterConstants.BLANK_TAPE_SYMBOL;
                } else {
                    String inputRead = txtReadSymbol.getText().trim();
                    if ( !inputRead.isEmpty() ) {
                        readSymbol = inputRead.charAt( 0 );
                    } else {
                        errorRead = true;
                    }
                }

                char writeSymbol = ' ';
                if ( checkWriteBlankSymbol.isSelected() ) {
                    writeSymbol = CharacterConstants.BLANK_TAPE_SYMBOL;
                } else {
                    String inputWrite = txtWriteSymbol.getText().trim();
                    if ( !inputWrite.isEmpty() ) {
                        writeSymbol = inputWrite.charAt( 0 );
                    } else {
                        errorWrite = true;
                    }
                }

                TMMovementType type = null;

                if ( rMoveRight.isSelected() ) {
                    type = TMMovementType.MOVE_RIGHT;
                } else if ( rMoveLeft.isSelected() ) {
                    type = TMMovementType.MOVE_LEFT;
                }

                String error = "";
                if ( errorRead ) {
                    error += "You must set a read symbol!";
                }
                if ( errorWrite ) {
                    error += "\nYou must set a write symbol!";
                }

                if ( errorRead || errorWrite ) {
                    showErrorMessage( parentComponent, error.trim() );
                } else {
                    if ( op != null ) {
                        op.setReadSymbol( readSymbol );
                        op.setWriteSymbol( writeSymbol );
                        op.setType( type );
                        return op;
                    } else {
                        return new TMOperation( readSymbol, writeSymbol, type );
                    }
                }

            } else {
                break;
            }
        
        }
        
        return null;
        
    }
    
    /**
     * Clones a character stack.
     * 
     * @param stack The stack to be cloned.
     * @return The cloned stack.
     */
    public static Deque<Character> cloneCharacterStack( Deque<Character> stack ) {
        Deque<Character> newStack = new ArrayDeque<>();
        Iterator<Character> it = stack.descendingIterator();
        while ( it.hasNext() ) {
            newStack.push( it.next() );
        }
        return newStack;
    }
    
    /**
     * Round to the nearest integer by a multple.
     * 
     * @param value The value to be rounded.
     * @param multiple The multiple to use.
     * @return The rounded value.
     */
    public static int roundToNearest( int value, int multiple ) {
        return (int) ( Math.round( value / (double) multiple ) * multiple );
    }
    
    /**
     * Shows a message to warn about the no implementation of a functinality.
     */
    public static void showNotImplementedYetMessage() {
        JOptionPane.showMessageDialog( null, "Not implemented yet!" );
    }
    
    /**
     * Update the Splash Screen with version number.
     * 
     * @param millisecondsToWait How many milliseconds to wait with the splash
     * screen active.
     */
    public static void updateSplashScreen( int millisecondsToWait ) {
        
        SplashScreen sp = SplashScreen.getSplashScreen();
        
        if ( sp != null ) {
            
            Graphics2D g2d = (Graphics2D) sp.createGraphics();
            g2d.setRenderingHint( 
                    RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON );
            g2d.setColor( new Color( 51, 0, 102 ) );
            
            Font f = new Font( "Century Gothic", Font.BOLD, 30 ) ;
            if ( f.getFamily().equals( Font.DIALOG ) ) {
                f = new Font( Font.MONOSPACED, Font.BOLD, 30 ) ;
            }
            g2d.setFont( f );
            
            FontMetrics fm = g2d.getFontMetrics();
            String v = "v" + ApplicationConstants.APP_VERSION;
            int vWidth = fm.stringWidth( v );
            
            g2d.drawString( v, 280 - vWidth, 160 );
            g2d.dispose();
            
            sp.update();
            
            try {
                Thread.sleep( millisecondsToWait );
            } catch ( InterruptedException exc ) {
            }
            
        }
        
    }
    
    /**
     * Generates a random BigInteger based on a random UUID.
     * 
     * @return The random BigInteger.
     */
    public static BigInteger generateUUID() {
        
        UUID uuid = UUID.randomUUID();
        
        long hi = uuid.getMostSignificantBits();
        long lo = uuid.getLeastSignificantBits();
        byte[] bytes = ByteBuffer.allocate( 16 ).putLong( hi ).putLong( lo ).array();
        //String numericUuid = big.toString().replace( '-', '1' ); // just in case
        
        return new BigInteger( bytes );
        
    }
    
    /**
     * Shows a error message.
     * 
     * @param parent Parent component.
     * @param message The message to show.
     */
    public static void showErrorMessage( Component parent, String message ) {
        JOptionPane.showMessageDialog( 
                    parent, 
                    message, 
                    "ERROR", 
                    JOptionPane.ERROR_MESSAGE );
    }
    
    /**
     * Shows a warning message.
     * 
     * @param parent Parent component.
     * @param message The message to show.
     */
    public static void showWarningMessage( Component parent, String message ) {
        JOptionPane.showMessageDialog( 
                    parent, 
                    message, 
                    "Warning", 
                    JOptionPane.WARNING_MESSAGE );
    }
    
    /**
     * Shows a information message.
     * 
     * @param parent Parent component.
     * @param message The message to show.
     */
    public static void showInformationMessage( Component parent, String message ) {
        JOptionPane.showMessageDialog( 
                    parent, 
                    message, 
                    "Information", 
                    JOptionPane.INFORMATION_MESSAGE );
    }
    
    /**
     * Shows a confirmation message.
     * 
     * @param parent Parent component.
     * @param message The message to show.
     * @return The selectec option.
     */
    public static int showConfirmationMessageYesNo( 
            Component parent, String message ) {
        return JOptionPane.showConfirmDialog( 
                    parent, 
                    message , 
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION );
    }
    
    /**
     * Shows a confirmation message.
     * 
     * @param parent Parent component.
     * @param message The message to show.
     * @return The selectec option.
     */
    public static int showConfirmationMessageYesNoCancel( 
            Component parent, String message ) {
        return JOptionPane.showConfirmDialog( 
                    parent, 
                    message , 
                    "Confirmation",
                    JOptionPane.YES_NO_CANCEL_OPTION );
    }
    
    /**
     * Creates a new color that is lighter than the color used as parameter.
     * 
     * @param color The color used to create a lighter color.
     * @return A lighter color.
     */
    public static Color lighterColor( Color color ) {
        
        float[] hsb = Color.RGBtoHSB( color.getRed(), color.getGreen(), color.getBlue(), null );
        
        // grayscale
        if ( color.getRed() == color.getGreen() && color.getGreen() == color.getBlue() ) {
            hsb[2] = 0.95f;
        } else {
            hsb[1] = 0.1f;
            hsb[2] = 1;
        }
        
        int rbg = Color.HSBtoRGB( hsb[0], hsb[1], hsb[2] );
        Color rgbColor = new Color( rbg );
        
        return new Color( 
                rgbColor.getRed(), 
                rgbColor.getGreen(), 
                rgbColor.getBlue(), 
                color.getAlpha() );
        
    }
    
    /**
     * Register the default and cancel buttons of a dialog.
     * 
     * @param rootPane The root pane.
     * @param defaultButton The button that will be the default button.
     * @param cancelButton The button that will be triggered when the ESC
     * key is pressed.
     */
    public static void registerDefaultAndCancelButton( 
            JRootPane rootPane, 
            JButton defaultButton,
            JButton cancelButton ) {
        rootPane.setDefaultButton( defaultButton );
        rootPane.getInputMap( JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT ).put( 
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0 ), "UTIL_CANCEL" );
        rootPane.getActionMap().put( "UTIL_CANCEL", new AbstractAction(){
            @Override
            public void actionPerformed( ActionEvent e ) {
                cancelButton.doClick();
            }
        });
    }
    
    /**
     * Trims a String using a specific character.
     * 
     * @param str The String to be trimed.
     * @param c The character to remove.
     * @return A new trimed String.
     */
    public static String trim( String str, char c ) {
        
        if ( str == null ) {
            return null;
        }
        
        if ( str.isEmpty() ) {
            return str;
        }
        
        String trimed = str;
            
        while ( trimed.length() > 0 && 
                trimed.charAt( 0 ) == CharacterConstants.BLANK_TAPE_SYMBOL ) {
            trimed = trimed.substring( 1 );
        }

        while ( trimed.length() > 0 && 
                trimed.charAt( trimed.length()-1 ) == CharacterConstants.BLANK_TAPE_SYMBOL ) {
            trimed = trimed.substring( 0, trimed.length()-1 );
        }
        
        return trimed;
        
    }
    
}
