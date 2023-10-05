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
import java.util.ArrayList;
import java.util.List;

/**
 * Pushdown Automaton properties edit/visualization panel.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAPropertiesPanel extends javax.swing.JPanel {

    private PDAInternalFrame pdaIFrame;
    
    private PDA pda;
    
    /**
     * Creates new form FAPropertiesPanel
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
        
        String s = "";
        List<Character> sps = pda.getStartingPushSymbols();
        
        // ignores the first
        for ( int i = 1; i < sps.size(); i++ ) {
            s += sps.get( i ) + " ";
        }
        
        txtStartingPush.setText( s.trim() );
        
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
        lblStartingPush = new javax.swing.JLabel();
        txtPDAType = new javax.swing.JTextField();
        txtStartingPush = new javax.swing.JTextField();
        btnFormalDefinition = new javax.swing.JButton();

        lblPDAType.setText("PDA Type:");

        lblStartingPush.setText("Starting Push:");

        txtPDAType.setEnabled(false);
        txtPDAType.setFocusable(false);

        txtStartingPush.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStartingPushFocusLost(evt);
            }
        });
        txtStartingPush.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStartingPushActionPerformed(evt);
            }
        });
        txtStartingPush.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStartingPushKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelPropertiesLayout = new javax.swing.GroupLayout(panelProperties);
        panelProperties.setLayout(panelPropertiesLayout);
        panelPropertiesLayout.setHorizontalGroup(
            panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPDAType, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStartingPush, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtStartingPush)
                    .addComponent(txtPDAType))
                .addContainerGap())
        );
        panelPropertiesLayout.setVerticalGroup(
            panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPDAType)
                    .addComponent(txtPDAType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStartingPush)
                    .addComponent(txtStartingPush, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnFormalDefinition.setText("Formal Definition");
        btnFormalDefinition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFormalDefinitionActionPerformed(evt);
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
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelProperties, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFormalDefinition)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnFormalDefinitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFormalDefinitionActionPerformed
        
        if ( pda.getStates().isEmpty() ) {
            Utils.showWarningMessage( pdaIFrame, "First add at least one state!" );
        } else if ( pda.getInitialState() == null ) {
            Utils.showWarningMessage( pdaIFrame, "Set the initial state!" );
        } else {
            PDAFormalDefinitionDialog d = new PDAFormalDefinitionDialog( null, true, pda );
            d.setVisible( true );
        }
        
    }//GEN-LAST:event_btnFormalDefinitionActionPerformed

    private void txtStartingPushActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStartingPushActionPerformed
        updateStartingPushSymbols();
        readProperties();
    }//GEN-LAST:event_txtStartingPushActionPerformed

    private void txtStartingPushFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStartingPushFocusLost
        updateStartingPushSymbols();
        readProperties();
    }//GEN-LAST:event_txtStartingPushFocusLost

    private void txtStartingPushKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStartingPushKeyReleased
        updateStartingPushSymbols();
        readProperties();
    }//GEN-LAST:event_txtStartingPushKeyReleased

    private void updateStartingPushSymbols() {
        
        String input = txtStartingPush.getText().trim().replaceAll( " ", "" );
        
        if ( !input.isEmpty() ) {
            List<Character> s = new ArrayList<>();
            for ( char c : input.toCharArray() ) {
                s.add( c );
            }
            pda.setStartingPushSymbols( s );
        } else {
            pda.setStartingPushSymbols( new ArrayList<>() );
        }
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFormalDefinition;
    private javax.swing.JLabel lblPDAType;
    private javax.swing.JLabel lblStartingPush;
    private javax.swing.JPanel panelProperties;
    private javax.swing.JTextField txtPDAType;
    private javax.swing.JTextField txtStartingPush;
    // End of variables declaration//GEN-END:variables
}
