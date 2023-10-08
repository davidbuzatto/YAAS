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
package br.com.davidbuzatto.yaas.gui.fa;

import br.com.davidbuzatto.yaas.model.fa.FA;
import br.com.davidbuzatto.yaas.gui.fa.table.FATransitionFunctionTableCellRenderer;
import br.com.davidbuzatto.yaas.gui.fa.table.FATransitionFunctionTableModel;
import br.com.davidbuzatto.yaas.util.CharacterConstants;
import br.com.davidbuzatto.yaas.util.DrawingConstants;
import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.Frame;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;

/**
 * A dialog to show the formal definition of Finite Automata.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAFormalDefinitionDialog extends javax.swing.JDialog {

    private FAInternalFrame faIFrame;
    private FA fa;
    
    /**
     * Creates new form FAFormalDefinitionDialog
     */
    public FAFormalDefinitionDialog( Frame parent, FAInternalFrame faIFrame, FA fa, boolean modal ) {
        super( parent, modal );
        this.faIFrame = faIFrame;
        this.fa = fa;
        initComponents();
        customInit();
        setLocationRelativeTo( faIFrame );
    }
    
    private void customInit() {
        
        Utils.registerDefaultAndCancelButton( getRootPane(), btnClose, btnClose );
        FATransitionFunctionTableModel m = Utils.createFATransitionFunctionTableModel( fa );
        
        textAreaFD.setText( fa.getFormalDefinition() );
        tableDelta.getTableHeader().setFont( DrawingConstants.DEFAULT_TABLE_FONT );
        tableDelta.setDefaultRenderer( 
                String.class, new FATransitionFunctionTableCellRenderer() );
        tableDelta.setModel( m );
        
        TitledBorder b = (TitledBorder) panelDelta.getBorder();
        b.setTitle( b.getTitle() + ( m.isPartial() ? " (partial)" : " (total)" ) );
        
        Utils.resizeColumnWidth( tableDelta, 300 );
        setIconImage( new ImageIcon( getClass().getResource( "/delta.png" ) ).getImage() );
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFD = new javax.swing.JPanel();
        scrollFD = new javax.swing.JScrollPane();
        textAreaFD = new javax.swing.JTextArea();
        panelDelta = new javax.swing.JPanel();
        scrollDelta = new javax.swing.JScrollPane();
        tableDelta = new javax.swing.JTable();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Formal Definition");
        setResizable(false);

        panelFD.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Formal Definition", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 153, 102))); // NOI18N

        scrollFD.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        textAreaFD.setEditable(false);
        textAreaFD.setColumns(20);
        textAreaFD.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        textAreaFD.setRows(4);
        scrollFD.setViewportView(textAreaFD);

        javax.swing.GroupLayout panelFDLayout = new javax.swing.GroupLayout(panelFD);
        panelFD.setLayout(panelFDLayout);
        panelFDLayout.setHorizontalGroup(
            panelFDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollFD)
                .addContainerGap())
        );
        panelFDLayout.setVerticalGroup(
            panelFDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollFD, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelDelta.setBorder(javax.swing.BorderFactory.createTitledBorder(null, String.valueOf( CharacterConstants.SMALL_DELTA ), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 153, 204))); // NOI18N

        tableDelta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableDelta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        scrollDelta.setViewportView(tableDelta);

        javax.swing.GroupLayout panelDeltaLayout = new javax.swing.GroupLayout(panelDelta);
        panelDelta.setLayout(panelDeltaLayout);
        panelDeltaLayout.setHorizontalGroup(
            panelDeltaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDeltaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollDelta, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelDeltaLayout.setVerticalGroup(
            panelDeltaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDeltaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollDelta, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE))
        );

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/accept.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnClose))
                    .addComponent(panelDelta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDelta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JPanel panelDelta;
    private javax.swing.JPanel panelFD;
    private javax.swing.JScrollPane scrollDelta;
    private javax.swing.JScrollPane scrollFD;
    private javax.swing.JTable tableDelta;
    private javax.swing.JTextArea textAreaFD;
    // End of variables declaration//GEN-END:variables
}
