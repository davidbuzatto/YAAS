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
package br.com.davidbuzatto.yaas.gui.pda;

import br.com.davidbuzatto.yaas.model.pda.PDA;
import java.awt.Frame;

/**
 * A viewer of a ID tree.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PDAIDViewerDialog extends javax.swing.JDialog {

    private PDAInternalFrame pdaIFrame;
    private PDA pda;
    
    /**
     * Creates new form PDAIDViewer
     */
    public PDAIDViewerDialog( Frame parent, PDAInternalFrame pdaIFrame, PDA pda, boolean modal ) {
        
        super( parent, modal );
        
        this.pdaIFrame = pdaIFrame;
        this.pda = pda;
        
        initComponents();
        setLocationRelativeTo( pdaIFrame );
        
        drawPanel.setPda( pda );
        drawPanel.arrangeAndProccessIds();
        drawPanel.repaint();
        drawPanel.revalidate();
        
        customInit();
        
    }

    private void customInit() {
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollDrawPanel = new javax.swing.JScrollPane();
        drawPanel = new br.com.davidbuzatto.yaas.gui.pda.PDAIDViewerDrawPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Instantaneous Description Viewer");

        javax.swing.GroupLayout drawPanelLayout = new javax.swing.GroupLayout(drawPanel);
        drawPanel.setLayout(drawPanelLayout);
        drawPanelLayout.setHorizontalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 798, Short.MAX_VALUE)
        );
        drawPanelLayout.setVerticalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 598, Short.MAX_VALUE)
        );

        scrollDrawPanel.setViewportView(drawPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollDrawPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollDrawPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.davidbuzatto.yaas.gui.pda.PDAIDViewerDrawPanel drawPanel;
    private javax.swing.JScrollPane scrollDrawPanel;
    // End of variables declaration//GEN-END:variables
}
