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
package br.com.davidbuzatto.yaas.gui;

import br.com.davidbuzatto.yaas.gui.model.State;
import br.com.davidbuzatto.yaas.gui.model.Transition;
import javax.swing.JOptionPane;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class DFAInternalFrame extends javax.swing.JInternalFrame {

    private int xPressed;
    private int yPressed;
    
    private int xOffset;
    private int yOffset;
    
    private int currentState;
    
    private State selectedState;
    private Transition selectedTransition;
    
    private State originState;
    private State targetState;
    
    /**
     * Creates new form DFAInternalFrame
     */
    public DFAInternalFrame() {
        initComponents();
        customInit();
    }

    private void customInit() {
        
        State s0 = new State();
        s0.setX1( 100 );
        s0.setY1( 200 );
        s0.setLabel( "q" + currentState++ );
        s0.setInitial( true );
        
        State s1 = new State();
        s1.setX1( 300 );
        s1.setY1( 100 );
        s1.setLabel( "q" + currentState++ );
        
        State s2 = new State();
        s2.setX1( 500 );
        s2.setY1( 100 );
        s2.setLabel( "q" + currentState++ );
        
        State s3 = new State();
        s3.setX1( 300 );
        s3.setY1( 300 );
        s3.setAccepting( true );
        s3.setLabel( "q" + currentState++ );
        
        Transition t1 = new Transition( s0, s1, 'a' );
        t1.addSymbol( 'b' );
        t1.addSymbol( 'c' );
        Transition t2 = new Transition( s0, s3, 'a' );
        Transition t3 = new Transition( s1, s2, 'a' );
        Transition t4 = new Transition( s2, s3, 'a' );
        Transition t5 = new Transition( s0, s0, 'a' );
        
        drawPanel.addState( s0 );
        drawPanel.addState( s1 );
        drawPanel.addState( s2 );
        drawPanel.addState( s3 );
        drawPanel.addTransition( t1 );
        drawPanel.addTransition( t2 );
        drawPanel.addTransition( t3 );
        drawPanel.addTransition( t4 );
        drawPanel.addTransition( t5 );
        
        //drawPanel.setTransitionsControlPointsVisible( true );
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroup = new javax.swing.ButtonGroup();
        toolBar = new javax.swing.JToolBar();
        btnNew = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        sep01 = new javax.swing.JToolBar.Separator();
        btnMove = new javax.swing.JToggleButton();
        btnAddState = new javax.swing.JToggleButton();
        btnAddTransition = new javax.swing.JToggleButton();
        horizontalFiller = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        btnShowObjectProperties = new javax.swing.JToggleButton();
        btnShowTransitionControls = new javax.swing.JToggleButton();
        seo03 = new javax.swing.JToolBar.Separator();
        btnShowGrid = new javax.swing.JToggleButton();
        btnSnapToGrid = new javax.swing.JToggleButton();
        sep04 = new javax.swing.JToolBar.Separator();
        btnZoomIn = new javax.swing.JButton();
        btnZoomOut = new javax.swing.JButton();
        drawPanel = new br.com.davidbuzatto.yaas.gui.DrawPanel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("DFA");

        toolBar.setRollover(true);

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/page_white_add.png"))); // NOI18N
        btnNew.setToolTipText("New");
        btnNew.setFocusable(false);
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        toolBar.add(btnNew);

        btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/folder.png"))); // NOI18N
        btnOpen.setToolTipText("Open");
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        toolBar.add(btnOpen);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/disk.png"))); // NOI18N
        btnSave.setToolTipText("Save");
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        toolBar.add(btnSave);
        toolBar.add(sep01);

        btnGroup.add(btnMove);
        btnMove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cursor_openhand.png"))); // NOI18N
        btnMove.setSelected(true);
        btnMove.setToolTipText("Move");
        btnMove.setFocusable(false);
        btnMove.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveActionPerformed(evt);
            }
        });
        toolBar.add(btnMove);

        btnGroup.add(btnAddState);
        btnAddState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/state.png"))); // NOI18N
        btnAddState.setToolTipText("Add State");
        btnAddState.setFocusable(false);
        btnAddState.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddState.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddStateActionPerformed(evt);
            }
        });
        toolBar.add(btnAddState);

        btnGroup.add(btnAddTransition);
        btnAddTransition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transition.png"))); // NOI18N
        btnAddTransition.setToolTipText("Add Transition");
        btnAddTransition.setFocusable(false);
        btnAddTransition.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddTransition.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddTransition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTransitionActionPerformed(evt);
            }
        });
        toolBar.add(btnAddTransition);
        toolBar.add(horizontalFiller);

        btnShowObjectProperties.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wrench.png"))); // NOI18N
        btnShowObjectProperties.setToolTipText("Show/Hide Object Properties");
        btnShowObjectProperties.setFocusable(false);
        btnShowObjectProperties.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnShowObjectProperties.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnShowObjectProperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowObjectPropertiesActionPerformed(evt);
            }
        });
        toolBar.add(btnShowObjectProperties);

        btnShowTransitionControls.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shape_square_edit.png"))); // NOI18N
        btnShowTransitionControls.setToolTipText("Show/Hide Transition Control Points");
        btnShowTransitionControls.setFocusable(false);
        btnShowTransitionControls.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnShowTransitionControls.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnShowTransitionControls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowTransitionControlsActionPerformed(evt);
            }
        });
        toolBar.add(btnShowTransitionControls);
        toolBar.add(seo03);

        btnShowGrid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/note.png"))); // NOI18N
        btnShowGrid.setToolTipText("Show/Hide Grid");
        btnShowGrid.setFocusable(false);
        btnShowGrid.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnShowGrid.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnShowGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowGridActionPerformed(evt);
            }
        });
        toolBar.add(btnShowGrid);

        btnSnapToGrid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/magnet.png"))); // NOI18N
        btnSnapToGrid.setToolTipText("Snap to Grid");
        btnSnapToGrid.setFocusable(false);
        btnSnapToGrid.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSnapToGrid.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSnapToGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSnapToGridActionPerformed(evt);
            }
        });
        toolBar.add(btnSnapToGrid);
        toolBar.add(sep04);

        btnZoomIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zoom_in.png"))); // NOI18N
        btnZoomIn.setToolTipText("Zoom In");
        btnZoomIn.setFocusable(false);
        btnZoomIn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnZoomIn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomInActionPerformed(evt);
            }
        });
        toolBar.add(btnZoomIn);

        btnZoomOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zoom_out.png"))); // NOI18N
        btnZoomOut.setToolTipText("Zoom Out");
        btnZoomOut.setFocusable(false);
        btnZoomOut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnZoomOut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomOutActionPerformed(evt);
            }
        });
        toolBar.add(btnZoomOut);

        drawPanel.setTransitionsControlPointsVisible(false);
        drawPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                drawPanelMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                drawPanelMouseMoved(evt);
            }
        });
        drawPanel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                drawPanelMouseWheelMoved(evt);
            }
        });
        drawPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                drawPanelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                drawPanelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout drawPanelLayout = new javax.swing.GroupLayout(drawPanel);
        drawPanel.setLayout(drawPanelLayout);
        drawPanelLayout.setHorizontalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        drawPanelLayout.setVerticalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 533, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
            .addComponent(drawPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(drawPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void drawPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawPanelMousePressed
        
        xPressed = evt.getX();
        yPressed = evt.getY();
        
        if ( btnMove.isSelected() ) {
            
            selectedState = drawPanel.getStateAt( xPressed, yPressed );
            
            if ( selectedState != null ) {
                xOffset = xPressed - selectedState.getX1();
                yOffset = yPressed - selectedState.getY1();
                drawPanel.updateTransitions();
            } else {
                selectedTransition = drawPanel.getTransitionAt( xPressed, yPressed );
            }
            
        } else if ( btnAddState.isSelected() ) {
            
            State newState = new State();
            newState.setX1( xPressed );
            newState.setY1( yPressed );
            newState.setLabel( "q" + currentState++ );
            
            drawPanel.addState( newState );
            
        } else if ( btnAddTransition.isSelected() ) {
            
            if ( originState == null ) {
                originState = drawPanel.getStateAt( xPressed, yPressed );
                if ( originState != null ) {
                    drawPanel.setDrawingTempTransition( true );
                    drawPanel.setTempTransitionX1( originState.getX1() );
                    drawPanel.setTempTransitionY1( originState.getY1() );
                    drawPanel.setTempTransitionX2( originState.getX1() );
                    drawPanel.setTempTransitionY2( originState.getY1() );
                }
            }
            
        }
        
        drawPanel.repaint();
        
    }//GEN-LAST:event_drawPanelMousePressed

    private void drawPanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawPanelMouseReleased
        
        if ( btnMove.isSelected() ) {
            
            selectedState = null;

            if ( selectedTransition != null ) {
                selectedTransition.mouseReleased( evt );
                selectedTransition = null;
            }
            
        } else if ( btnAddTransition.isSelected() ) {
            
            if ( originState != null ) {
                
                if ( targetState == null ) {
                    targetState = drawPanel.getStateAt( evt.getX(), evt.getY() );
                } 
            
                if ( targetState != null ) {
                    
                    String s = JOptionPane.showInputDialog( this, "Transition symbol" );
                    char symbol;
                    
                    if ( s == null || s.isEmpty() ) {
                        symbol = '\u03b5';
                    } else {
                        symbol = s.charAt( 0 );
                    }
                        
                    Transition t = new Transition( originState, targetState, symbol );
                    drawPanel.addTransition( t );
                    
                }
                
                originState = null;
                targetState = null;
                drawPanel.setDrawingTempTransition( false );
                
            }
            
        }
        
        drawPanel.repaint();
        
    }//GEN-LAST:event_drawPanelMouseReleased

    private void drawPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawPanelMouseDragged
        
        if ( btnMove.isSelected() ) {
            
            if ( selectedState != null ) {
                selectedState.setX1( evt.getX() - xOffset );
                selectedState.setY1( evt.getY() - yOffset );
                drawPanel.updateTransitions();
                drawPanel.draggTransitions( evt );
            } else if ( selectedTransition != null ) {
                selectedTransition.mouseDragged( evt );
            }
            
        } else if ( btnAddTransition.isSelected() ) {
            drawPanel.setTempTransitionX2( evt.getX() );
            drawPanel.setTempTransitionY2( evt.getY() );
        }
        
        drawPanel.repaint();
        
    }//GEN-LAST:event_drawPanelMouseDragged

    private void drawPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawPanelMouseMoved
        
        if ( btnMove.isSelected() || btnAddTransition.isSelected() ) {
            drawPanel.mouseHoverStatesAndTransitions( evt.getX(), evt.getY() );
            drawPanel.repaint();
        }
        
    }//GEN-LAST:event_drawPanelMouseMoved

    private void drawPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_drawPanelMouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_drawPanelMouseWheelMoved

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMoveActionPerformed

    private void btnAddStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStateActionPerformed
        // nothing here
    }//GEN-LAST:event_btnAddStateActionPerformed

    private void btnAddTransitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTransitionActionPerformed
        // nothing here
    }//GEN-LAST:event_btnAddTransitionActionPerformed

    private void btnShowTransitionControlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowTransitionControlsActionPerformed
        drawPanel.setTransitionsControlPointsVisible( btnShowTransitionControls.isSelected() );
        drawPanel.repaint();
    }//GEN-LAST:event_btnShowTransitionControlsActionPerformed

    private void btnShowGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowGridActionPerformed
        drawPanel.setShowGrid( btnShowGrid.isSelected() );
        drawPanel.repaint();
    }//GEN-LAST:event_btnShowGridActionPerformed

    private void btnSnapToGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSnapToGridActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSnapToGridActionPerformed

    private void btnZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomInActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnZoomInActionPerformed

    private void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomOutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnZoomOutActionPerformed

    private void btnShowObjectPropertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowObjectPropertiesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShowObjectPropertiesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAddState;
    private javax.swing.JToggleButton btnAddTransition;
    private javax.swing.ButtonGroup btnGroup;
    private javax.swing.JToggleButton btnMove;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnSave;
    private javax.swing.JToggleButton btnShowGrid;
    private javax.swing.JToggleButton btnShowObjectProperties;
    private javax.swing.JToggleButton btnShowTransitionControls;
    private javax.swing.JToggleButton btnSnapToGrid;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    private br.com.davidbuzatto.yaas.gui.DrawPanel drawPanel;
    private javax.swing.Box.Filler horizontalFiller;
    private javax.swing.JToolBar.Separator seo03;
    private javax.swing.JToolBar.Separator sep01;
    private javax.swing.JToolBar.Separator sep04;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables
}
