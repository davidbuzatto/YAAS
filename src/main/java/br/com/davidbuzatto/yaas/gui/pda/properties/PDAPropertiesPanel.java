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
import javax.swing.JOptionPane;

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
        /*if ( pda.getType() == FAType.EMPTY ) {
            txtFAType.setText( "" );
            txtFAType.setToolTipText( "" );
        } else {
            txtFAType.setText(pda.getType().getAcronym() );
            txtFAType.setToolTipText(pda.getType().getDescription() );
        }*/
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnFormalDefinition = new javax.swing.JButton();

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
                .addComponent(btnFormalDefinition)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnFormalDefinition)
                .addContainerGap(46, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnFormalDefinitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFormalDefinitionActionPerformed
        
        if ( pda.getStates().isEmpty() ) {
            JOptionPane.showMessageDialog( 
                    pdaIFrame, 
                    "First add at least one state!", 
                    "Warning", 
                    JOptionPane.WARNING_MESSAGE );
        } else if ( pda.getInitialState() == null ) {
            JOptionPane.showMessageDialog( 
                    pdaIFrame, 
                    "Set the initial state!", 
                    "Warning", JOptionPane.WARNING_MESSAGE );
        } else {
            PDAFormalDefinitionDialog d = new PDAFormalDefinitionDialog( null, true, pda );
            d.setVisible( true );
        }
        
    }//GEN-LAST:event_btnFormalDefinitionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFormalDefinition;
    // End of variables declaration//GEN-END:variables
}
