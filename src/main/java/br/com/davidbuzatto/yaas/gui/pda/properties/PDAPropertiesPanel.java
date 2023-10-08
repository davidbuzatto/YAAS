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

import br.com.davidbuzatto.yaas.gui.pda.PDAFormalDefinitionDialog;
import br.com.davidbuzatto.yaas.gui.pda.PDAInternalFrame;
import br.com.davidbuzatto.yaas.model.pda.PDA;
import br.com.davidbuzatto.yaas.model.pda.PDAType;
import br.com.davidbuzatto.yaas.util.Utils;

/**
 * Pushdown Automaton properties edit/visualization panel.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAPropertiesPanel extends javax.swing.JPanel {

    private PDAInternalFrame pdaIFrame;
    
    private PDA pda;
    
    /**
     * Creates new form PDAPropertiesPanel
     */
    public PDAPropertiesPanel( PDAInternalFrame pdaIFrame ) {
        
        this.pdaIFrame = pdaIFrame;
        
        initComponents();
        customInit();
        
    }
    
    private void customInit() {
    }

    public void setPda( PDA pda ) {
        this.pda = pda;
    }

    public void readProperties() {
        if ( pda.getType() == PDAType.EMPTY ) {
            txtPDAType.setText( "" );
            txtPDAType.setToolTipText( "" );
        } else {
            txtPDAType.setText( pda.getType().getAcronym() );
            txtPDAType.setToolTipText( pda.getType().getDescription() );
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
        lblPDAType = new javax.swing.JLabel();
        txtPDAType = new javax.swing.JTextField();
        btnFormalDefinition = new javax.swing.JButton();
        btnResetStatesColor = new javax.swing.JButton();
        btnResetTransitionsColor = new javax.swing.JButton();

        lblPDAType.setText("PDA Type:");

        txtPDAType.setEnabled(false);
        txtPDAType.setFocusable(false);

        javax.swing.GroupLayout panelPropertiesLayout = new javax.swing.GroupLayout(panelProperties);
        panelProperties.setLayout(panelPropertiesLayout);
        panelPropertiesLayout.setHorizontalGroup(
            panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropertiesLayout.createSequentialGroup()
                .addComponent(lblPDAType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPDAType)
                .addContainerGap())
        );
        panelPropertiesLayout.setVerticalGroup(
            panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPDAType)
                    .addComponent(txtPDAType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnFormalDefinition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delta.png"))); // NOI18N
        btnFormalDefinition.setText("Formal Definition");
        btnFormalDefinition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFormalDefinitionActionPerformed(evt);
            }
        });

        btnResetStatesColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancel.png"))); // NOI18N
        btnResetStatesColor.setText("Reset States Color");
        btnResetStatesColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetStatesColorActionPerformed(evt);
            }
        });

        btnResetTransitionsColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancel.png"))); // NOI18N
        btnResetTransitionsColor.setText("Reset Transitions Color");
        btnResetTransitionsColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetTransitionsColorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnFormalDefinition)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelProperties, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnResetStatesColor)
                            .addComponent(btnResetTransitionsColor))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelProperties, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFormalDefinition)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnResetStatesColor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnResetTransitionsColor)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnFormalDefinitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFormalDefinitionActionPerformed
        
        if ( pda.getStates().isEmpty() ) {
            Utils.showWarningMessage( pdaIFrame, "First add at least one state!" );
        } else if ( pda.getInitialState() == null ) {
            Utils.showWarningMessage( pdaIFrame, "Set the initial state!" );
        } else {
            PDAFormalDefinitionDialog d = new PDAFormalDefinitionDialog( 
                    null, pdaIFrame, pda, false );
            d.setVisible( true );
        }
        
    }//GEN-LAST:event_btnFormalDefinitionActionPerformed

    private void btnResetStatesColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetStatesColorActionPerformed
        pda.resetStatesColor();
        pdaIFrame.repaintDrawPanel();
    }//GEN-LAST:event_btnResetStatesColorActionPerformed

    private void btnResetTransitionsColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetTransitionsColorActionPerformed
        pda.resetTransitionsColor();
        pdaIFrame.repaintDrawPanel();
    }//GEN-LAST:event_btnResetTransitionsColorActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFormalDefinition;
    private javax.swing.JButton btnResetStatesColor;
    private javax.swing.JButton btnResetTransitionsColor;
    private javax.swing.JLabel lblPDAType;
    private javax.swing.JPanel panelProperties;
    private javax.swing.JTextField txtPDAType;
    // End of variables declaration//GEN-END:variables
}
