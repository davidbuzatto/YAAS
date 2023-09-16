/*
 * Copyright (C) 2022 Prof. Dr. David Buzatto
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

import br.com.davidbuzatto.yaas.gui.fa.FA;
import br.com.davidbuzatto.yaas.gui.fa.FAExamples;
import br.com.davidbuzatto.yaas.gui.fa.FAInternalFrame;
import br.com.davidbuzatto.yaas.util.ApplicationPreferences;
import br.com.davidbuzatto.yaas.util.CharacterConstants;
import br.com.davidbuzatto.yaas.util.Utils;
import com.formdev.flatlaf.FlatDarculaLaf;
import java.beans.PropertyVetoException;

/**
 * Main GUI class.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class MainWindow extends javax.swing.JFrame {
    
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        ApplicationPreferences.preparePreferences( false );
        initComponents();
        customInit();
    }
    
    private void customInit() {
        
        setTitle( 
                Utils.getMavenModel().getArtifactId() + 
                " - v" + 
                Utils.getMavenModel().getVersion() );
        
        //setExtendedState( MAXIMIZED_BOTH );
        
        //createFAInternalFrame( FAExamples.createENFADecimalNumber(), true );
        createFAInternalFrame( FAExamples.createDFA0Even1Odd(), true );
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        toolBar = new javax.swing.JToolBar();
        btnFA = new javax.swing.JButton();
        btnPDA = new javax.swing.JButton();
        btnTM = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuItemExit = new javax.swing.JMenuItem();
        menuExamples = new javax.swing.JMenu();
        menuDFA = new javax.swing.JMenu();
        miDFASubstring01 = new javax.swing.JMenuItem();
        miDFAEndsWith00 = new javax.swing.JMenuItem();
        miDFAEndsWith01 = new javax.swing.JMenuItem();
        miDFAEndsWith10 = new javax.swing.JMenuItem();
        miDFAEndsWith11 = new javax.swing.JMenuItem();
        miDFA0Even1Odd = new javax.swing.JMenuItem();
        menuNFA = new javax.swing.JMenu();
        miNFAEndsWith00 = new javax.swing.JMenuItem();
        miNFAEndsWith01 = new javax.swing.JMenuItem();
        miNFAEndsWith10 = new javax.swing.JMenuItem();
        miNFAEndsWith11 = new javax.swing.JMenuItem();
        menuENFA = new javax.swing.JMenu();
        miENFADecimalNumber = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("YAAS");
        setIconImage(new javax.swing.ImageIcon( getClass().getResource( "/yaas.png" ) ).getImage());

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );

        toolBar.setRollover(true);

        btnFA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/faBig.png"))); // NOI18N
        btnFA.setText("Finite Automata");
        btnFA.setToolTipText("");
        btnFA.setFocusable(false);
        btnFA.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFA.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFAActionPerformed(evt);
            }
        });
        toolBar.add(btnFA);

        btnPDA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pdaBig.png"))); // NOI18N
        btnPDA.setText("Pushdown Automata");
        btnPDA.setToolTipText("");
        btnPDA.setEnabled(false);
        btnPDA.setFocusable(false);
        btnPDA.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPDA.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(btnPDA);

        btnTM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/turingBig.png"))); // NOI18N
        btnTM.setText("Turing Machines");
        btnTM.setEnabled(false);
        btnTM.setFocusable(false);
        btnTM.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTM.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(btnTM);

        menuFile.setText("File");

        menuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuItemExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/door_out.png"))); // NOI18N
        menuItemExit.setText("Exit");
        menuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExitActionPerformed(evt);
            }
        });
        menuFile.add(menuItemExit);

        menuBar.add(menuFile);

        menuExamples.setText("Examples");

        menuDFA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dfa.png"))); // NOI18N
        menuDFA.setText("DFA");

        miDFASubstring01.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dfa.png"))); // NOI18N
        miDFASubstring01.setText("L = { x01y | x and y are any strings of 0's and 1's }");
        miDFASubstring01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDFASubstring01ActionPerformed(evt);
            }
        });
        menuDFA.add(miDFASubstring01);

        miDFAEndsWith00.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dfa.png"))); // NOI18N
        miDFAEndsWith00.setText("L = { w | w ends with 00 }");
        miDFAEndsWith00.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDFAEndsWith00ActionPerformed(evt);
            }
        });
        menuDFA.add(miDFAEndsWith00);

        miDFAEndsWith01.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dfa.png"))); // NOI18N
        miDFAEndsWith01.setText("L = { w | w ends with 01 }");
        miDFAEndsWith01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDFAEndsWith01ActionPerformed(evt);
            }
        });
        menuDFA.add(miDFAEndsWith01);

        miDFAEndsWith10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dfa.png"))); // NOI18N
        miDFAEndsWith10.setText("L = { w | w ends with 10 }");
        miDFAEndsWith10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDFAEndsWith10ActionPerformed(evt);
            }
        });
        menuDFA.add(miDFAEndsWith10);

        miDFAEndsWith11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dfa.png"))); // NOI18N
        miDFAEndsWith11.setText("L = { w | w ends with 11 }");
        miDFAEndsWith11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDFAEndsWith11ActionPerformed(evt);
            }
        });
        menuDFA.add(miDFAEndsWith11);

        miDFA0Even1Odd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dfa.png"))); // NOI18N
        miDFA0Even1Odd.setText(FAExamples.REG_LANG_0_EVEN_1_ODD);
        miDFA0Even1Odd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDFA0Even1OddActionPerformed(evt);
            }
        });
        menuDFA.add(miDFA0Even1Odd);

        menuExamples.add(menuDFA);

        menuNFA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nfa.png"))); // NOI18N
        menuNFA.setText("NFA");

        miNFAEndsWith00.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nfa.png"))); // NOI18N
        miNFAEndsWith00.setText("L = { w | w ends with 00 }");
        miNFAEndsWith00.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNFAEndsWith00ActionPerformed(evt);
            }
        });
        menuNFA.add(miNFAEndsWith00);

        miNFAEndsWith01.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nfa.png"))); // NOI18N
        miNFAEndsWith01.setText("L = { w | w ends with 01 }");
        miNFAEndsWith01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNFAEndsWith01ActionPerformed(evt);
            }
        });
        menuNFA.add(miNFAEndsWith01);

        miNFAEndsWith10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nfa.png"))); // NOI18N
        miNFAEndsWith10.setText("L = { w | w ends with 10 }");
        miNFAEndsWith10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNFAEndsWith10ActionPerformed(evt);
            }
        });
        menuNFA.add(miNFAEndsWith10);

        miNFAEndsWith11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nfa.png"))); // NOI18N
        miNFAEndsWith11.setText("L = { w | w ends with 11 }");
        miNFAEndsWith11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNFAEndsWith11ActionPerformed(evt);
            }
        });
        menuNFA.add(miNFAEndsWith11);

        menuExamples.add(menuNFA);

        menuENFA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/enfa.png"))); // NOI18N
        menuENFA.setText(CharacterConstants.EMPTY_STRING.toString() + "-NFA");

        miENFADecimalNumber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/enfa.png"))); // NOI18N
        miENFADecimalNumber.setText("L = { w | w is a valid decimal number }");
        miENFADecimalNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miENFADecimalNumberActionPerformed(evt);
            }
        });
        menuENFA.add(miENFADecimalNumber);

        menuExamples.add(menuENFA);

        menuBar.add(menuExamples);

        menuHelp.setText("Help");

        menuItemAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        menuItemAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/help.png"))); // NOI18N
        menuItemAbout.setText("About...");
        menuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAboutActionPerformed(evt);
            }
        });
        menuHelp.add(menuItemAbout);

        menuBar.add(menuHelp);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane)
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desktopPane))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFAActionPerformed
        createFAInternalFrame( null, false );
    }//GEN-LAST:event_btnFAActionPerformed

    private void miDFASubstring01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDFASubstring01ActionPerformed
        createFAInternalFrame( FAExamples.createDFASubstring01(), false );
    }//GEN-LAST:event_miDFASubstring01ActionPerformed

    private void miDFAEndsWith01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDFAEndsWith01ActionPerformed
        createFAInternalFrame( FAExamples.createDFAEndsWith01(), false );
    }//GEN-LAST:event_miDFAEndsWith01ActionPerformed

    private void miDFA0Even1OddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDFA0Even1OddActionPerformed
        createFAInternalFrame( FAExamples.createDFA0Even1Odd(), false );
    }//GEN-LAST:event_miDFA0Even1OddActionPerformed

    private void miNFAEndsWith01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNFAEndsWith01ActionPerformed
        createFAInternalFrame( FAExamples.createNFAEndsWith01(), false );
    }//GEN-LAST:event_miNFAEndsWith01ActionPerformed

    private void miENFADecimalNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miENFADecimalNumberActionPerformed
        createFAInternalFrame( FAExamples.createENFADecimalNumber(), false );
    }//GEN-LAST:event_miENFADecimalNumberActionPerformed

    private void menuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExitActionPerformed
        Utils.showNotImplementedYetMessage();
    }//GEN-LAST:event_menuItemExitActionPerformed

    private void menuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAboutActionPerformed
        Utils.showNotImplementedYetMessage();
    }//GEN-LAST:event_menuItemAboutActionPerformed

    private void miNFAEndsWith00ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNFAEndsWith00ActionPerformed
        createFAInternalFrame( FAExamples.createNFAEndsWith00(), false );
    }//GEN-LAST:event_miNFAEndsWith00ActionPerformed

    private void miNFAEndsWith10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNFAEndsWith10ActionPerformed
        createFAInternalFrame( FAExamples.createNFAEndsWith10(), false );
    }//GEN-LAST:event_miNFAEndsWith10ActionPerformed

    private void miNFAEndsWith11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNFAEndsWith11ActionPerformed
        createFAInternalFrame( FAExamples.createNFAEndsWith11(), false );
    }//GEN-LAST:event_miNFAEndsWith11ActionPerformed

    private void miDFAEndsWith00ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDFAEndsWith00ActionPerformed
        createFAInternalFrame( FAExamples.createDFAEndsWith00(), false );
    }//GEN-LAST:event_miDFAEndsWith00ActionPerformed

    private void miDFAEndsWith11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDFAEndsWith11ActionPerformed
        createFAInternalFrame( FAExamples.createDFAEndsWith11(), false );
    }//GEN-LAST:event_miDFAEndsWith11ActionPerformed

    private void miDFAEndsWith10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDFAEndsWith10ActionPerformed
        createFAInternalFrame( FAExamples.createDFAEndsWith10(), false );
    }//GEN-LAST:event_miDFAEndsWith10ActionPerformed

    public void createFAInternalFrame( FA fa, boolean maximized ) {
        
        FAInternalFrame iFrame = new FAInternalFrame( this, fa );
        iFrame.setCurrentState( fa.getStates().size() );
        
        desktopPane.add( iFrame );
        iFrame.setVisible( true );
        
        try {
            iFrame.setMaximum( maximized );
        } catch ( PropertyVetoException exc ) {
            Utils.showException( exc );
        }
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main( String args[] ) {
        java.awt.EventQueue.invokeLater( new Runnable() {
            public void run() {
                FlatDarculaLaf.setup();
                new MainWindow().setVisible( true );
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFA;
    private javax.swing.JButton btnPDA;
    private javax.swing.JButton btnTM;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuDFA;
    private javax.swing.JMenu menuENFA;
    private javax.swing.JMenu menuExamples;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuItemAbout;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenu menuNFA;
    private javax.swing.JMenuItem miDFA0Even1Odd;
    private javax.swing.JMenuItem miDFAEndsWith00;
    private javax.swing.JMenuItem miDFAEndsWith01;
    private javax.swing.JMenuItem miDFAEndsWith10;
    private javax.swing.JMenuItem miDFAEndsWith11;
    private javax.swing.JMenuItem miDFASubstring01;
    private javax.swing.JMenuItem miENFADecimalNumber;
    private javax.swing.JMenuItem miNFAEndsWith00;
    private javax.swing.JMenuItem miNFAEndsWith01;
    private javax.swing.JMenuItem miNFAEndsWith10;
    private javax.swing.JMenuItem miNFAEndsWith11;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables
}
