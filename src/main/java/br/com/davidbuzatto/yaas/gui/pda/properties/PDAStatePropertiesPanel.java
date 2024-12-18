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
package br.com.davidbuzatto.yaas.gui.pda.properties;

import br.com.davidbuzatto.yaas.gui.pda.PDAInternalFrame;
import br.com.davidbuzatto.yaas.model.pda.PDA;
import br.com.davidbuzatto.yaas.model.pda.PDAState;
import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

/**
 * PDAState properties edit/visualization panel.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAStatePropertiesPanel extends javax.swing.JPanel {

    private PDAInternalFrame pdaIFrame;
    
    private PDA pda;
    private PDAState state;
    
    /**
     * Creates new form PDAStatePropertiesPanel
     */
    public PDAStatePropertiesPanel( PDAInternalFrame pdaIFrame ) {
        
        this.pdaIFrame = pdaIFrame;
        
        initComponents();
        customInit();
        
    }
    
    private void customInit() {
    }

    public void setPda( PDA pda ) {
        this.pda = pda;
    }

    public void setState( PDAState state ) {
        this.state = state;
    }
    
    public void readProperties() {
        
        if ( state != null ) {
            
            txtLabel.setText( state.getLabel() );
            txtCustomLabel.setText( state.getCustomLabel() );
            checkInitial.setSelected( state.isInitial() );
            checkFinal.setSelected( state.isFinal() );
            ccpColor.setColor( state.getStrokeColor() );
            ccpColor.repaint();
            
        } else {
            
            txtLabel.setText( "" );
            txtCustomLabel.setText( "" );
            checkInitial.setSelected( false );
            checkFinal.setSelected( false );
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
        lblLabel = new javax.swing.JLabel();
        lblCustomLabel = new javax.swing.JLabel();
        lblInitial = new javax.swing.JLabel();
        lblFinal = new javax.swing.JLabel();
        lblColor = new javax.swing.JLabel();
        txtLabel = new javax.swing.JTextField();
        txtCustomLabel = new javax.swing.JTextField();
        checkInitial = new javax.swing.JCheckBox();
        checkFinal = new javax.swing.JCheckBox();
        ccpColor = new br.com.davidbuzatto.yaas.gui.ColorChooserPanel();
        btnRemove = new javax.swing.JButton();

        lblLabel.setText("Label:");

        lblCustomLabel.setText("Custom Label:");

        lblInitial.setText("Initial:");

        lblFinal.setText("Final:");

        lblColor.setText("Color:");

        txtLabel.setText("a");
        txtLabel.setEnabled(false);

        txtCustomLabel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCustomLabelFocusLost(evt);
            }
        });
        txtCustomLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustomLabelActionPerformed(evt);
            }
        });
        txtCustomLabel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCustomLabelKeyReleased(evt);
            }
        });

        checkInitial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkInitialActionPerformed(evt);
            }
        });

        checkFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkFinalActionPerformed(evt);
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

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPropertiesLayout = new javax.swing.GroupLayout(panelProperties);
        panelProperties.setLayout(panelPropertiesLayout);
        panelPropertiesLayout.setHorizontalGroup(
            panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCustomLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblInitial, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblFinal, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblColor, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(txtCustomLabel)
                    .addGroup(panelPropertiesLayout.createSequentialGroup()
                        .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkInitial)
                            .addComponent(checkFinal)
                            .addComponent(ccpColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panelPropertiesLayout.createSequentialGroup()
                .addComponent(btnRemove)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelPropertiesLayout.setVerticalGroup(
            panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLabel)
                    .addComponent(txtLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomLabel)
                    .addComponent(txtCustomLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInitial)
                    .addComponent(checkInitial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFinal)
                    .addComponent(checkFinal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(ccpColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblColor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemove)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelProperties, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelProperties, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void checkInitialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkInitialActionPerformed
        
        if ( checkInitial.isSelected() ) {
            state.setInitial( true );
            pda.setInitialState( state );
        } else {
            if ( state.isInitial() ) {
                pda.setInitialState( null );
            }
            state.setInitial( false );
        }
        
        pdaIFrame.setCurrentFileSaved( false );
        pdaIFrame.repaintDrawPanel();
        
    }//GEN-LAST:event_checkInitialActionPerformed

    private void checkFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkFinalActionPerformed
        state.setFinal( checkFinal.isSelected() );
        pdaIFrame.setCurrentFileSaved( false );
        pdaIFrame.repaintDrawPanel();
    }//GEN-LAST:event_checkFinalActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        
        if ( state != null ) {
        
            if ( Utils.showConfirmationMessageYesNo(
                    pdaIFrame, 
                    "Do you really want to remove the selected state?" )
                    == JOptionPane.YES_OPTION ) {
                pda.removeState( state );
                pdaIFrame.setCurrentFileSaved( false );
                pdaIFrame.repaintDrawPanel();
                pdaIFrame.updateAfterRemoval();
                state = null;
                readProperties();
            }
        
        } else {
            Utils.showErrorMessage( pdaIFrame, "There's no selected state!" );
        }
        
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void txtCustomLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustomLabelActionPerformed
        updateStateCustomLabel();
    }//GEN-LAST:event_txtCustomLabelActionPerformed

    private void ccpColorMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ccpColorMouseReleased
        
        Color c = JColorChooser.showDialog( pdaIFrame, "State Color", 
                ccpColor.getForeground() );
        
        if ( c != null ) {
            ccpColor.setColor( c );
            ccpColor.repaint();
            state.setStrokeColor( c );
            pdaIFrame.setCurrentFileSaved( false );
            pdaIFrame.repaintDrawPanel();
        }
        
    }//GEN-LAST:event_ccpColorMouseReleased

    private void txtCustomLabelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCustomLabelFocusLost
        updateStateCustomLabel();
    }//GEN-LAST:event_txtCustomLabelFocusLost

    private void txtCustomLabelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustomLabelKeyReleased
        updateStateCustomLabel();
    }//GEN-LAST:event_txtCustomLabelKeyReleased

    public void disableGUI() {
        checkInitial.setEnabled( false );
        checkFinal.setEnabled( false );
        btnRemove.setEnabled( false );
    }
    
    public void enableGUI() {
        checkInitial.setEnabled( true );
        checkFinal.setEnabled( true );
        btnRemove.setEnabled( true );
    }
    
    private void updateStateCustomLabel() {
        String input = txtCustomLabel.getText().trim();
        if ( input.isEmpty() ) {
            state.setCustomLabel( null );
        } else {
            state.setCustomLabel( input );
        }
        pdaIFrame.setCurrentFileSaved( false );
        pdaIFrame.repaintDrawPanel();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRemove;
    private br.com.davidbuzatto.yaas.gui.ColorChooserPanel ccpColor;
    private javax.swing.JCheckBox checkFinal;
    private javax.swing.JCheckBox checkInitial;
    private javax.swing.JLabel lblColor;
    private javax.swing.JLabel lblCustomLabel;
    private javax.swing.JLabel lblFinal;
    private javax.swing.JLabel lblInitial;
    private javax.swing.JLabel lblLabel;
    private javax.swing.JPanel panelProperties;
    private javax.swing.JTextField txtCustomLabel;
    private javax.swing.JTextField txtLabel;
    // End of variables declaration//GEN-END:variables
}
