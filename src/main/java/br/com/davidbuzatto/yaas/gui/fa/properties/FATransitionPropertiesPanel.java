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
package br.com.davidbuzatto.yaas.gui.fa.properties;

import br.com.davidbuzatto.yaas.model.fa.FA;
import br.com.davidbuzatto.yaas.gui.fa.FAInternalFrame;
import br.com.davidbuzatto.yaas.model.fa.FATransition;
import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

/**
 * FATransition properties edit/visualization panel.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FATransitionPropertiesPanel extends javax.swing.JPanel {

    private FAInternalFrame faIFrame;
    
    private FA fa;
    private FATransition transition;
    
    
    /**
     * Creates new form FATransitionPropertiesPanel
     */
    public FATransitionPropertiesPanel( FAInternalFrame faIFrame ) {
        
        this.faIFrame = faIFrame;
        
        initComponents();
        customInit();
        
    }
    
    private void customInit() {
    }

    public void setFa( FA fa ) {
        this.fa = fa;
    }

    public void setTransition( FATransition transition ) {
        this.transition = transition;
    }
    
    public void readProperties() {
        
        if ( transition != null ) {
            
            txtOriginState.setText( transition.getOriginState().toString() );
            txtTargetState.setText( transition.getTargetState().toString() );
            txtSymbols.setText( transition.getSymbols().toString() );
            ccpColor.setColor( transition.getStrokeColor() );
            ccpColor.repaint();
            
        } else {
            
            txtOriginState.setText( "" );
            txtTargetState.setText( "" );
            txtSymbols.setText( "" );
            ccpColor.setColor( Color.BLACK );
            ccpColor.repaint();
            
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelProperties = new javax.swing.JPanel();
        lblOriginState = new javax.swing.JLabel();
        lblTargetState = new javax.swing.JLabel();
        lblSymbols = new javax.swing.JLabel();
        lblColor = new javax.swing.JLabel();
        txtOriginState = new javax.swing.JTextField();
        txtTargetState = new javax.swing.JTextField();
        txtSymbols = new javax.swing.JTextField();
        btnEditSymbols = new javax.swing.JButton();
        ccpColor = new br.com.davidbuzatto.yaas.gui.ColorChooserPanel();
        btnResetTransformations = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();

        lblOriginState.setText("Origin State:");

        lblTargetState.setText("Target State:");

        lblSymbols.setText("Symbol(s):");

        lblColor.setText("Color:");

        txtOriginState.setEnabled(false);

        txtTargetState.setEnabled(false);

        txtSymbols.setEnabled(false);

        btnEditSymbols.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pencil.png"))); // NOI18N
        btnEditSymbols.setToolTipText("Edit Symbol(s)");
        btnEditSymbols.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditSymbolsActionPerformed(evt);
            }
        });

        ccpColor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ccpColorMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout ccpColorLayout = new javax.swing.GroupLayout(ccpColor);
        ccpColor.setLayout(ccpColorLayout);
        ccpColorLayout.setHorizontalGroup(
            ccpColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );
        ccpColorLayout.setVerticalGroup(
            ccpColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelPropertiesLayout = new javax.swing.GroupLayout(panelProperties);
        panelProperties.setLayout(panelPropertiesLayout);
        panelPropertiesLayout.setHorizontalGroup(
            panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOriginState, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTargetState, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSymbols, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblColor, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPropertiesLayout.createSequentialGroup()
                        .addComponent(txtSymbols, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditSymbols))
                    .addComponent(txtTargetState)
                    .addComponent(txtOriginState)
                    .addGroup(panelPropertiesLayout.createSequentialGroup()
                        .addComponent(ccpColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelPropertiesLayout.setVerticalGroup(
            panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOriginState)
                    .addComponent(txtOriginState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTargetState)
                    .addComponent(txtTargetState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSymbols)
                    .addComponent(txtSymbols, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditSymbols))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(ccpColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblColor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnResetTransformations.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancel.png"))); // NOI18N
        btnResetTransformations.setText("Reset Transformations");
        btnResetTransformations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetTransformationsActionPerformed(evt);
            }
        });

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelProperties, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnResetTransformations)
                            .addComponent(btnRemove))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelProperties, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnResetTransformations)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemove)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditSymbolsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSymbolsActionPerformed
        
        if ( transition != null ) {
            
            String s = "";
            for ( Character c : transition.getSymbols() ) {
                s += c.toString() + " ";
            }
            s = s.trim();
            
            String input = Utils.showInputDialogEmptyString( faIFrame, 
                    "New transition symbol(s)", 
                    "Edit Transition Symbol(s)", s );
            List<Character> symbols = new ArrayList<>();

            if ( input != null ) {

                input = input.trim().replace( " ", "" );

                if ( !input.isEmpty() ) {
                    for ( char c : input.toCharArray() ) {
                        symbols.add( c );
                    }
                    transition.setSymbols( symbols );
                    fa.markAllCachesAsObsolete();
                    readProperties();
                    faIFrame.setCurrentFileSaved( false );
                    faIFrame.repaintDrawPanel();
                    faIFrame.updateAfterUpdate();
                }

            }
            
        } else {
            Utils.showErrorMessage( faIFrame, "There's no selected transition!" );
        }
        
    }//GEN-LAST:event_btnEditSymbolsActionPerformed

    private void btnResetTransformationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetTransformationsActionPerformed
        
        if ( transition != null ) {
            
            transition.resetTransformations();
            faIFrame.setCurrentFileSaved( false );
            faIFrame.repaintDrawPanel();
            
        } else {
            Utils.showErrorMessage( faIFrame, "There's no selected transition!" );
        }
        
    }//GEN-LAST:event_btnResetTransformationsActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        
        if ( transition != null ) {
        
            if ( Utils.showConfirmationMessageYesNo(
                    faIFrame, 
                    "Do you really want to remove the selected transition?" )
                    == JOptionPane.YES_OPTION ) {
                fa.removeTransition( transition );
                faIFrame.setCurrentFileSaved( false );
                faIFrame.repaintDrawPanel();
                faIFrame.updateAfterRemoval();
                transition = null;
                readProperties();
            }
        
        } else {
            Utils.showErrorMessage( faIFrame, "There's no selected transition!" );
        }
        
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void ccpColorMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ccpColorMouseReleased
        
        Color c = JColorChooser.showDialog( faIFrame, "Transition Color", 
                ccpColor.getForeground() );
        
        if ( c != null ) {
            ccpColor.setColor( c );
            ccpColor.repaint();
            transition.setStrokeColor( c );
            faIFrame.setCurrentFileSaved( false );
            faIFrame.repaintDrawPanel();
        }
        
    }//GEN-LAST:event_ccpColorMouseReleased

    public void disableGUI() {
        btnEditSymbols.setEnabled( false );
        btnRemove.setEnabled( false );
    }
    
    public void enableGUI() {
        btnEditSymbols.setEnabled( true );
        btnRemove.setEnabled( true );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditSymbols;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnResetTransformations;
    private br.com.davidbuzatto.yaas.gui.ColorChooserPanel ccpColor;
    private javax.swing.JLabel lblColor;
    private javax.swing.JLabel lblOriginState;
    private javax.swing.JLabel lblSymbols;
    private javax.swing.JLabel lblTargetState;
    private javax.swing.JPanel panelProperties;
    private javax.swing.JTextField txtOriginState;
    private javax.swing.JTextField txtSymbols;
    private javax.swing.JTextField txtTargetState;
    // End of variables declaration//GEN-END:variables
}
