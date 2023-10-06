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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.davidbuzatto.yaas.gui;

import br.com.davidbuzatto.yaas.model.fa.FA;
import br.com.davidbuzatto.yaas.model.fa.examples.FAExamples;
import br.com.davidbuzatto.yaas.model.fa.examples.FAExamplesForMinimizationTest;
import br.com.davidbuzatto.yaas.gui.fa.FAInternalFrame;
import br.com.davidbuzatto.yaas.gui.pda.PDAInternalFrame;
import br.com.davidbuzatto.yaas.model.pda.PDA;
import br.com.davidbuzatto.yaas.model.pda.examples.PDAExamples;
import br.com.davidbuzatto.yaas.util.ApplicationConstants;
import br.com.davidbuzatto.yaas.util.ApplicationPreferences;
import br.com.davidbuzatto.yaas.util.CharacterConstants;
import br.com.davidbuzatto.yaas.util.Utils;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.beans.PropertyVetoException;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
                ApplicationConstants.APP_NAME + 
                " - v" + 
                ApplicationConstants.APP_VERSION );
        
        if ( ApplicationConstants.IN_DEVELOPMENT ) {
            //createFAInternalFrame( FAExamples.createDFAEndsWith00(), true, true );
            createPDAInternalFrame( PDAExamples.createPDAEvenPalindromeAS(), true, true );
            //createPDAInternalFrame( PDAExamples.createDPDAEvenPalindromeCM(), true, true );
        } else {
            menuMinimizationTest.setVisible( false );
            setExtendedState( MAXIMIZED_BOTH );
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

        bgTheme = new javax.swing.ButtonGroup();
        desktopPane = new javax.swing.JDesktopPane();
        toolBar = new javax.swing.JToolBar();
        btnFA = new javax.swing.JButton();
        btnPDA = new javax.swing.JButton();
        btnTM = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuItemExit = new javax.swing.JMenuItem();
        menuExamples = new javax.swing.JMenu();
        menuFA = new javax.swing.JMenu();
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
        menuPDA = new javax.swing.JMenu();
        menuPDAFinalState = new javax.swing.JMenu();
        miPDAEvenPalindromeAS = new javax.swing.JMenuItem();
        menuPDAEmptyStack = new javax.swing.JMenu();
        miPDAEvenPalindromeES = new javax.swing.JMenuItem();
        menuDPDA = new javax.swing.JMenu();
        miDPDAEvenPalindromeCM = new javax.swing.JMenuItem();
        menuTM = new javax.swing.JMenu();
        menuMinimizationTest = new javax.swing.JMenu();
        miMiniT01 = new javax.swing.JMenuItem();
        miMiniT02 = new javax.swing.JMenuItem();
        miMiniT03 = new javax.swing.JMenuItem();
        miMiniT04 = new javax.swing.JMenuItem();
        miMiniT05 = new javax.swing.JMenuItem();
        miMiniT06 = new javax.swing.JMenuItem();
        miMiniT07 = new javax.swing.JMenuItem();
        miMiniT08 = new javax.swing.JMenuItem();
        miMiniT09 = new javax.swing.JMenuItem();
        menuThemes = new javax.swing.JMenu();
        riLightTheme = new javax.swing.JRadioButtonMenuItem();
        riDarkTheme = new javax.swing.JRadioButtonMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("YAAS");
        setIconImage(new javax.swing.ImageIcon( getClass().getResource( "/yaas.png" ) ).getImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

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
        btnPDA.setFocusable(false);
        btnPDA.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPDA.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDAActionPerformed(evt);
            }
        });
        toolBar.add(btnPDA);

        btnTM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/turingBig.png"))); // NOI18N
        btnTM.setText("Turing Machines");
        btnTM.setFocusable(false);
        btnTM.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTM.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTMActionPerformed(evt);
            }
        });
        toolBar.add(btnTM);

        menuFile.setMnemonic('F');
        menuFile.setText("File");

        menuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuItemExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/door_out.png"))); // NOI18N
        menuItemExit.setMnemonic('x');
        menuItemExit.setText("Exit");
        menuItemExit.setToolTipText("");
        menuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExitActionPerformed(evt);
            }
        });
        menuFile.add(menuItemExit);

        menuBar.add(menuFile);

        menuExamples.setMnemonic('x');
        menuExamples.setText("Examples");
        menuExamples.setToolTipText("");

        menuFA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fa.png"))); // NOI18N
        menuFA.setMnemonic('F');
        menuFA.setText("Finite Automata");

        menuDFA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dfa.png"))); // NOI18N
        menuDFA.setMnemonic('D');
        menuDFA.setText("DFA");
        menuDFA.setToolTipText("Deterministic Finite Automaton");

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

        menuFA.add(menuDFA);

        menuNFA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nfa.png"))); // NOI18N
        menuNFA.setMnemonic('N');
        menuNFA.setText("NFA");
        menuNFA.setToolTipText("Nondeterministic Finite Automaton");

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

        menuFA.add(menuNFA);

        menuENFA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/enfa.png"))); // NOI18N
        menuENFA.setMnemonic('F');
        menuENFA.setText(CharacterConstants.EMPTY_STRING.toString() + "-NFA");
        menuENFA.setToolTipText("Finite Automaton with " + CharacterConstants.SMALL_EPSILON + "-transitions");

        miENFADecimalNumber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/enfa.png"))); // NOI18N
        miENFADecimalNumber.setText("L = { w | w is a valid decimal number }");
        miENFADecimalNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miENFADecimalNumberActionPerformed(evt);
            }
        });
        menuENFA.add(miENFADecimalNumber);

        menuFA.add(menuENFA);

        menuExamples.add(menuFA);

        menuPDA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pda.png"))); // NOI18N
        menuPDA.setMnemonic('P');
        menuPDA.setText("Pushdown Automata");

        menuPDAFinalState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pda.png"))); // NOI18N
        menuPDAFinalState.setMnemonic('A');
        menuPDAFinalState.setText("Accept by Final State");

        miPDAEvenPalindromeAS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pda.png"))); // NOI18N
        miPDAEvenPalindromeAS.setText("Even Palindrome");
        miPDAEvenPalindromeAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPDAEvenPalindromeASActionPerformed(evt);
            }
        });
        menuPDAFinalState.add(miPDAEvenPalindromeAS);

        menuPDA.add(menuPDAFinalState);

        menuPDAEmptyStack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pda.png"))); // NOI18N
        menuPDAEmptyStack.setMnemonic('E');
        menuPDAEmptyStack.setText("Accept by Empty Stack");

        miPDAEvenPalindromeES.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pda.png"))); // NOI18N
        miPDAEvenPalindromeES.setText("Even Palindrome");
        miPDAEvenPalindromeES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPDAEvenPalindromeESActionPerformed(evt);
            }
        });
        menuPDAEmptyStack.add(miPDAEvenPalindromeES);

        menuPDA.add(menuPDAEmptyStack);

        menuDPDA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pda.png"))); // NOI18N
        menuDPDA.setMnemonic('D');
        menuDPDA.setText("DPDA");
        menuDPDA.setToolTipText("");

        miDPDAEvenPalindromeCM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pda.png"))); // NOI18N
        miDPDAEvenPalindromeCM.setText("Even Palindrome with Center Mark");
        miDPDAEvenPalindromeCM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDPDAEvenPalindromeCMActionPerformed(evt);
            }
        });
        menuDPDA.add(miDPDAEvenPalindromeCM);

        menuPDA.add(menuDPDA);

        menuExamples.add(menuPDA);

        menuTM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/turing.png"))); // NOI18N
        menuTM.setMnemonic('T');
        menuTM.setText("Turing Machines");
        menuTM.setToolTipText("");
        menuExamples.add(menuTM);

        menuBar.add(menuExamples);

        menuMinimizationTest.setText("Minimization Tests");

        miMiniT01.setText("Test 01");
        miMiniT01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miMiniT01ActionPerformed(evt);
            }
        });
        menuMinimizationTest.add(miMiniT01);

        miMiniT02.setText("Test 02");
        miMiniT02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miMiniT02ActionPerformed(evt);
            }
        });
        menuMinimizationTest.add(miMiniT02);

        miMiniT03.setText("Test 03");
        miMiniT03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miMiniT03ActionPerformed(evt);
            }
        });
        menuMinimizationTest.add(miMiniT03);

        miMiniT04.setText("Test 04");
        miMiniT04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miMiniT04ActionPerformed(evt);
            }
        });
        menuMinimizationTest.add(miMiniT04);

        miMiniT05.setText("Test 05");
        miMiniT05.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miMiniT05ActionPerformed(evt);
            }
        });
        menuMinimizationTest.add(miMiniT05);

        miMiniT06.setText("Test 06");
        miMiniT06.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miMiniT06ActionPerformed(evt);
            }
        });
        menuMinimizationTest.add(miMiniT06);

        miMiniT07.setText("Test 07");
        miMiniT07.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miMiniT07ActionPerformed(evt);
            }
        });
        menuMinimizationTest.add(miMiniT07);

        miMiniT08.setText("Test 08");
        miMiniT08.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miMiniT08ActionPerformed(evt);
            }
        });
        menuMinimizationTest.add(miMiniT08);

        miMiniT09.setText("Test 09");
        miMiniT09.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miMiniT09ActionPerformed(evt);
            }
        });
        menuMinimizationTest.add(miMiniT09);

        menuBar.add(menuMinimizationTest);

        menuThemes.setMnemonic('T');
        menuThemes.setText("Themes");

        bgTheme.add(riLightTheme);
        riLightTheme.setMnemonic('L');
        riLightTheme.setText("Light");
        riLightTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                riLightThemeActionPerformed(evt);
            }
        });
        menuThemes.add(riLightTheme);

        bgTheme.add(riDarkTheme);
        riDarkTheme.setMnemonic('D');
        riDarkTheme.setText("Dark");
        riDarkTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                riDarkThemeActionPerformed(evt);
            }
        });
        menuThemes.add(riDarkTheme);

        menuBar.add(menuThemes);

        menuHelp.setMnemonic('H');
        menuHelp.setText("Help");

        menuItemAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        menuItemAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/help.png"))); // NOI18N
        menuItemAbout.setMnemonic('A');
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
        createFAInternalFrame( null, false, true );
    }//GEN-LAST:event_btnFAActionPerformed

    private void miDFASubstring01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDFASubstring01ActionPerformed
        createFAInternalFrame( FAExamples.createDFASubstring01(), false, false );
    }//GEN-LAST:event_miDFASubstring01ActionPerformed

    private void miDFAEndsWith01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDFAEndsWith01ActionPerformed
        createFAInternalFrame( FAExamples.createDFAEndsWith01(), false, false );
    }//GEN-LAST:event_miDFAEndsWith01ActionPerformed

    private void miDFA0Even1OddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDFA0Even1OddActionPerformed
        createFAInternalFrame( FAExamples.createDFA0Even1Odd(), false, false );
    }//GEN-LAST:event_miDFA0Even1OddActionPerformed

    private void miNFAEndsWith01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNFAEndsWith01ActionPerformed
        createFAInternalFrame( FAExamples.createNFAEndsWith01(), false, false );
    }//GEN-LAST:event_miNFAEndsWith01ActionPerformed

    private void miENFADecimalNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miENFADecimalNumberActionPerformed
        createFAInternalFrame( FAExamples.createENFADecimalNumber(), false, false );
    }//GEN-LAST:event_miENFADecimalNumberActionPerformed

    private void menuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExitActionPerformed
        close();
    }//GEN-LAST:event_menuItemExitActionPerformed

    private void menuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAboutActionPerformed
        
        JOptionPane.showMessageDialog( this, 
                String.format( """
                Yet Another Automata Simulator (YAAS) is a prototype tool for
                simulating the execution of Finite Automata, Pushdown Automata
                and Turing Machines.
                               
                This tool is developed by Prof. Dr. David Buzatto.
                
                Current version: %s""", Utils.getMavenModel().getVersion() ),
                "About...", 
                JOptionPane.INFORMATION_MESSAGE );
        
    }//GEN-LAST:event_menuItemAboutActionPerformed

    private void miNFAEndsWith00ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNFAEndsWith00ActionPerformed
        createFAInternalFrame( FAExamples.createNFAEndsWith00(), false, false );
    }//GEN-LAST:event_miNFAEndsWith00ActionPerformed

    private void miNFAEndsWith10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNFAEndsWith10ActionPerformed
        createFAInternalFrame( FAExamples.createNFAEndsWith10(), false, false );
    }//GEN-LAST:event_miNFAEndsWith10ActionPerformed

    private void miNFAEndsWith11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNFAEndsWith11ActionPerformed
        createFAInternalFrame( FAExamples.createNFAEndsWith11(), false, false );
    }//GEN-LAST:event_miNFAEndsWith11ActionPerformed

    private void miDFAEndsWith00ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDFAEndsWith00ActionPerformed
        createFAInternalFrame( FAExamples.createDFAEndsWith00(), false, false );
    }//GEN-LAST:event_miDFAEndsWith00ActionPerformed

    private void miDFAEndsWith11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDFAEndsWith11ActionPerformed
        createFAInternalFrame( FAExamples.createDFAEndsWith11(), false, false );
    }//GEN-LAST:event_miDFAEndsWith11ActionPerformed

    private void miDFAEndsWith10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDFAEndsWith10ActionPerformed
        createFAInternalFrame( FAExamples.createDFAEndsWith10(), false, false );
    }//GEN-LAST:event_miDFAEndsWith10ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        close();
    }//GEN-LAST:event_formWindowClosing

    private void btnPDAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDAActionPerformed
        createPDAInternalFrame( null, false, true );
    }//GEN-LAST:event_btnPDAActionPerformed

    private void btnTMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTMActionPerformed
        Utils.showNotImplementedYetMessage();
    }//GEN-LAST:event_btnTMActionPerformed

    private void miMiniT01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miMiniT01ActionPerformed
        createFAInternalFrame(FAExamplesForMinimizationTest.createDFAForMinimizationTest01(), 
                false, false );
    }//GEN-LAST:event_miMiniT01ActionPerformed

    private void miMiniT02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miMiniT02ActionPerformed
        createFAInternalFrame(FAExamplesForMinimizationTest.createDFAForMinimizationTest02(), 
                false, false );
    }//GEN-LAST:event_miMiniT02ActionPerformed

    private void miMiniT03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miMiniT03ActionPerformed
        createFAInternalFrame(FAExamplesForMinimizationTest.createDFAForMinimizationTest03(), 
                false, false );
    }//GEN-LAST:event_miMiniT03ActionPerformed

    private void miMiniT04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miMiniT04ActionPerformed
        createFAInternalFrame(FAExamplesForMinimizationTest.createDFAForMinimizationTest04(), 
                false, false );
    }//GEN-LAST:event_miMiniT04ActionPerformed

    private void miMiniT05ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miMiniT05ActionPerformed
        createFAInternalFrame(FAExamplesForMinimizationTest.createDFAForMinimizationTest05(), 
                false, false );
    }//GEN-LAST:event_miMiniT05ActionPerformed

    private void miMiniT06ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miMiniT06ActionPerformed
        createFAInternalFrame(FAExamplesForMinimizationTest.createDFAForMinimizationTest06(), 
                false, false );
    }//GEN-LAST:event_miMiniT06ActionPerformed

    private void miMiniT07ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miMiniT07ActionPerformed
        createFAInternalFrame(FAExamplesForMinimizationTest.createDFAForMinimizationTest07(), 
                false, false );
    }//GEN-LAST:event_miMiniT07ActionPerformed

    private void miMiniT08ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miMiniT08ActionPerformed
        createFAInternalFrame(FAExamplesForMinimizationTest.createDFAForMinimizationTest08(), 
                false, false );
    }//GEN-LAST:event_miMiniT08ActionPerformed

    private void miMiniT09ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miMiniT09ActionPerformed
        createFAInternalFrame(FAExamplesForMinimizationTest.createDFAForMinimizationTest09(), 
                false, false );
    }//GEN-LAST:event_miMiniT09ActionPerformed

    private void miPDAEvenPalindromeASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPDAEvenPalindromeASActionPerformed
        createPDAInternalFrame( PDAExamples.createPDAEvenPalindromeAS(), 
                false, false );
    }//GEN-LAST:event_miPDAEvenPalindromeASActionPerformed

    private void miPDAEvenPalindromeESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPDAEvenPalindromeESActionPerformed
        createPDAInternalFrame( PDAExamples.createPDAEvenPalindromeES(), 
                false, false );
    }//GEN-LAST:event_miPDAEvenPalindromeESActionPerformed

    private void miDPDAEvenPalindromeCMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDPDAEvenPalindromeCMActionPerformed
        createPDAInternalFrame( PDAExamples.createDPDAEvenPalindromeCM(), 
                false, false );
    }//GEN-LAST:event_miDPDAEvenPalindromeCMActionPerformed

    private void riLightThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riLightThemeActionPerformed
        changeThemeToLight();
    }//GEN-LAST:event_riLightThemeActionPerformed

    private void riDarkThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riDarkThemeActionPerformed
        changeThemeToDark();
    }//GEN-LAST:event_riDarkThemeActionPerformed

    public void createFAInternalFrame( FA fa, boolean maximized, boolean currentFileSaved ) {
        
        if ( fa == null ) {
            fa = new FA();
        }
        
        FAInternalFrame iFrame = new FAInternalFrame( this, fa );
        iFrame.setCurrentState( fa.getStates().size() );
        iFrame.setCurrentFileSaved( currentFileSaved );
        
        desktopPane.add( iFrame );
        iFrame.setVisible( true );
        
        try {
            iFrame.setMaximum( maximized );
        } catch ( PropertyVetoException exc ) {
            Utils.showException( exc );
        }
        
    }
    
    public void createPDAInternalFrame( PDA pda, boolean maximized, boolean currentFileSaved ) {
        
        if ( pda == null ) {
            pda = new PDA();
        }
        
        PDAInternalFrame iFrame = new PDAInternalFrame( this, pda );
        iFrame.setCurrentState( pda.getStates().size() );
        iFrame.setCurrentFileSaved( currentFileSaved );
        
        desktopPane.add( iFrame );
        iFrame.setVisible( true );
        
        try {
            iFrame.setMaximum( maximized );
        } catch ( PropertyVetoException exc ) {
            Utils.showException( exc );
        }
        
    }
    
    @SuppressWarnings( "unchecked" )
    private void close() {
        
        if ( ApplicationConstants.IN_DEVELOPMENT ) {
            System.exit( 0 );
        } else if ( Utils.showConfirmationMessageYesNo( this,
                """
                Do you really want to quit?
                All unsaved data will be lost!""" ) == JOptionPane.OK_OPTION ) {
            System.exit( 0 );
        }
        
    }
    
    private void updateJifsUI() {
        
        SwingUtilities.updateComponentTreeUI( this );
        
        for ( JInternalFrame jif : desktopPane.getAllFrames() ) {
            if ( jif instanceof FAInternalFrame faJif ) {
                faJif.updateUIFlatLaf();
                faJif.updateUI();
            }
            if ( jif instanceof PDAInternalFrame pdaJif ) {
                pdaJif.updateUIFlatLaf();
                pdaJif.updateUI();
            }
        }
        
    }
    
    public void changeThemeToLight() {
        FlatLightLaf.setup();
        updateJifsUI();
        riLightTheme.setSelected( true );
        ApplicationPreferences.setPref( ApplicationPreferences.PREF_THEME, 
                ApplicationConstants.LIGHT_THEME );
    }
    
    public void changeThemeToDark() {
        FlatDarculaLaf.setup();
        updateJifsUI();
        riDarkTheme.setSelected( true );
        ApplicationPreferences.setPref( ApplicationPreferences.PREF_THEME, 
                ApplicationConstants.DARK_THEME );
    }
    
    public static void main( String args[] ) {
        
        java.awt.EventQueue.invokeLater( new Runnable() {
            
            public void run() {
                
                MainWindow mainWindow = new MainWindow();
                
                switch ( ApplicationPreferences.getPref( ApplicationPreferences.PREF_THEME ) ) {
                    case ApplicationConstants.LIGHT_THEME:
                        mainWindow.changeThemeToLight();
                        break;
                    case ApplicationConstants.DARK_THEME:
                        mainWindow.changeThemeToDark();
                        break;
                }
                
                Utils.updateSplashScreen( 6000 );
                mainWindow.setVisible( true );
                
            }
            
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgTheme;
    private javax.swing.JButton btnFA;
    private javax.swing.JButton btnPDA;
    private javax.swing.JButton btnTM;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuDFA;
    private javax.swing.JMenu menuDPDA;
    private javax.swing.JMenu menuENFA;
    private javax.swing.JMenu menuExamples;
    private javax.swing.JMenu menuFA;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuItemAbout;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenu menuMinimizationTest;
    private javax.swing.JMenu menuNFA;
    private javax.swing.JMenu menuPDA;
    private javax.swing.JMenu menuPDAEmptyStack;
    private javax.swing.JMenu menuPDAFinalState;
    private javax.swing.JMenu menuTM;
    private javax.swing.JMenu menuThemes;
    private javax.swing.JMenuItem miDFA0Even1Odd;
    private javax.swing.JMenuItem miDFAEndsWith00;
    private javax.swing.JMenuItem miDFAEndsWith01;
    private javax.swing.JMenuItem miDFAEndsWith10;
    private javax.swing.JMenuItem miDFAEndsWith11;
    private javax.swing.JMenuItem miDFASubstring01;
    private javax.swing.JMenuItem miDPDAEvenPalindromeCM;
    private javax.swing.JMenuItem miENFADecimalNumber;
    private javax.swing.JMenuItem miMiniT01;
    private javax.swing.JMenuItem miMiniT02;
    private javax.swing.JMenuItem miMiniT03;
    private javax.swing.JMenuItem miMiniT04;
    private javax.swing.JMenuItem miMiniT05;
    private javax.swing.JMenuItem miMiniT06;
    private javax.swing.JMenuItem miMiniT07;
    private javax.swing.JMenuItem miMiniT08;
    private javax.swing.JMenuItem miMiniT09;
    private javax.swing.JMenuItem miNFAEndsWith00;
    private javax.swing.JMenuItem miNFAEndsWith01;
    private javax.swing.JMenuItem miNFAEndsWith10;
    private javax.swing.JMenuItem miNFAEndsWith11;
    private javax.swing.JMenuItem miPDAEvenPalindromeAS;
    private javax.swing.JMenuItem miPDAEvenPalindromeES;
    private javax.swing.JRadioButtonMenuItem riDarkTheme;
    private javax.swing.JRadioButtonMenuItem riLightTheme;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables
}
