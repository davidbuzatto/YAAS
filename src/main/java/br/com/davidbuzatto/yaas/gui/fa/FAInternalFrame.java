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
package br.com.davidbuzatto.yaas.gui.fa;

import br.com.davidbuzatto.yaas.gui.MainWindow;
import br.com.davidbuzatto.yaas.gui.ZoomFacility;
import br.com.davidbuzatto.yaas.gui.fa.properties.FAPropertiesPanel;
import br.com.davidbuzatto.yaas.gui.fa.properties.FAStatePropertiesPanel;
import br.com.davidbuzatto.yaas.gui.fa.properties.FATransitionPropertiesPanel;
import br.com.davidbuzatto.yaas.util.ApplicationPreferences;
import br.com.davidbuzatto.yaas.util.DrawingConstants;
import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Finite Automaton modelation and simulation.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FAInternalFrame extends javax.swing.JInternalFrame {

    private static final String MODEL_PROPERTIES_CARD = "model";
    private static final String STATE_PROPERTIES_CARD = "state";
    private static final String TRANSITION_PROPERTIES_CARD = "transition";
    
    private FA fa;
    private MainWindow mainWindow;
    
    private int xPressed;
    private int yPressed;
    
    private int xOffset;
    private int yOffset;
    
    private int xPrev;
    private int yPrev;
    
    private int xSnap;
    private int ySnap;
    
    private int currentState;
    
    private FAState selectedState;
    private FATransition selectedTransition;
    
    private FAState originState;
    private FAState targetState;
    
    private FAPropertiesPanel faPPanel;
    private FAStatePropertiesPanel statePPanel;
    private FATransitionPropertiesPanel transitionPPanel;
    private CardLayout cardLayout;
    
    private List<FASimulationStep> simulationSteps;
    private int currentSimulationStep;
    
    private FABatchTest faBatchTestDialog;
    private Color txtTestStringDefaultBC;
    private Color txtTestStringDefaultFC;
    private Color lblTestResultDefaultFC;
    
    private ZoomFacility zoomFacility;
    
    /**
     * Creates new form FAInternalFrame
     */
    public FAInternalFrame( MainWindow mainWindow ) {
        this( mainWindow, null );
    }
    
    public FAInternalFrame( MainWindow mainWindow, FA fa ) {
        
        this.mainWindow = mainWindow;
        this.simulationSteps = new ArrayList<>();
       
        if ( fa == null ) {
            this.fa = new FA();
        } else {
            this.fa = fa;
        }
       
        this.zoomFacility = new ZoomFacility();
        
        initComponents();
        customInit();
        
    }

    private void customInit() {
        
        faPPanel = new FAPropertiesPanel( this );
        statePPanel = new FAStatePropertiesPanel( this );
        transitionPPanel = new FATransitionPropertiesPanel( this );
        
        cardLayout = (CardLayout) panelProperties.getLayout();
        panelProperties.add( faPPanel, MODEL_PROPERTIES_CARD );
        panelProperties.add( statePPanel, STATE_PROPERTIES_CARD );
        panelProperties.add( transitionPPanel, TRANSITION_PROPERTIES_CARD );
        
        drawPanel.setFa( fa );
        
        faPPanel.setFa( fa );
        faPPanel.readProperties();
        cardLayout.show( panelProperties, MODEL_PROPERTIES_CARD );
        
        drawPanel.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
        repaintDrawPanel();
        
        txtTestStringDefaultBC = txtTestString.getBackground();
        txtTestStringDefaultFC = txtTestString.getForeground();
        lblTestResultDefaultFC = lblTestResult.getForeground();
        
        scrollPaneModel.getHorizontalScrollBar().addAdjustmentListener( 
                new AdjustmentListener(){
            @Override
            public void adjustmentValueChanged( AdjustmentEvent e ) {
                repaintDrawPanel();
            }
        });
        
        scrollPaneModel.getVerticalScrollBar().addAdjustmentListener( 
                new AdjustmentListener(){
            @Override
            public void adjustmentValueChanged( AdjustmentEvent e ) {
                repaintDrawPanel();
            }
        });
        
        sep04.setVisible( false );
        btnZoomIn.setVisible( false );
        btnZoomOut.setVisible( false );
        
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
        btnSaveAs = new javax.swing.JButton();
        btnSaveModelAsImage = new javax.swing.JButton();
        sep01 = new javax.swing.JToolBar.Separator();
        btnMove = new javax.swing.JToggleButton();
        btnAddState = new javax.swing.JToggleButton();
        btnAddTransition = new javax.swing.JToggleButton();
        sep02 = new javax.swing.JToolBar.Separator();
        btnGenerateEquivalentDFA = new javax.swing.JButton();
        btnGenerateMinimizedDFA = new javax.swing.JButton();
        btnGenerateComplementDFA = new javax.swing.JButton();
        hFiller = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        btnShowTransitionControls = new javax.swing.JToggleButton();
        sep03 = new javax.swing.JToolBar.Separator();
        btnShowGrid = new javax.swing.JToggleButton();
        btnSnapToGrid = new javax.swing.JToggleButton();
        sep04 = new javax.swing.JToolBar.Separator();
        btnZoomIn = new javax.swing.JButton();
        btnZoomOut = new javax.swing.JButton();
        panelModel = new javax.swing.JPanel();
        panelTestsAndSimulation = new javax.swing.JPanel();
        lblTestString = new javax.swing.JLabel();
        txtTestString = new javax.swing.JTextField();
        lblTestResult = new javax.swing.JLabel();
        toolBarTestsAndSimulation = new javax.swing.JToolBar();
        btnTest = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        sepTS01 = new javax.swing.JToolBar.Separator();
        btnBatchTest = new javax.swing.JButton();
        sepTS02 = new javax.swing.JToolBar.Separator();
        btnStart = new javax.swing.JButton();
        btnFirstStep = new javax.swing.JButton();
        btnPreviousStep = new javax.swing.JButton();
        btnNextStep = new javax.swing.JButton();
        btnLastStep = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        scrollPaneModel = new javax.swing.JScrollPane();
        drawPanel = new br.com.davidbuzatto.yaas.gui.DrawPanel();
        panelProperties = new javax.swing.JPanel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Finite Automata");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/fa.png"))); // NOI18N

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

        btnSaveAs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/disk_multiple.png"))); // NOI18N
        btnSaveAs.setToolTipText("Save as...");
        btnSaveAs.setFocusable(false);
        btnSaveAs.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSaveAs.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAsActionPerformed(evt);
            }
        });
        toolBar.add(btnSaveAs);

        btnSaveModelAsImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture.png"))); // NOI18N
        btnSaveModelAsImage.setToolTipText("Save Model as Image");
        btnSaveModelAsImage.setFocusable(false);
        btnSaveModelAsImage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSaveModelAsImage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSaveModelAsImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveModelAsImageActionPerformed(evt);
            }
        });
        toolBar.add(btnSaveModelAsImage);
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
        toolBar.add(sep02);

        btnGenerateEquivalentDFA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dfa.png"))); // NOI18N
        btnGenerateEquivalentDFA.setToolTipText("Generate Equivalent DFA");
        btnGenerateEquivalentDFA.setFocusable(false);
        btnGenerateEquivalentDFA.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGenerateEquivalentDFA.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGenerateEquivalentDFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateEquivalentDFAActionPerformed(evt);
            }
        });
        toolBar.add(btnGenerateEquivalentDFA);

        btnGenerateMinimizedDFA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arrow_in.png"))); // NOI18N
        btnGenerateMinimizedDFA.setToolTipText("Generate Minimized DFA");
        btnGenerateMinimizedDFA.setFocusable(false);
        btnGenerateMinimizedDFA.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGenerateMinimizedDFA.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGenerateMinimizedDFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateMinimizedDFAActionPerformed(evt);
            }
        });
        toolBar.add(btnGenerateMinimizedDFA);

        btnGenerateComplementDFA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shape_flip_horizontal.png"))); // NOI18N
        btnGenerateComplementDFA.setToolTipText("Generate Complement DFA");
        btnGenerateComplementDFA.setFocusable(false);
        btnGenerateComplementDFA.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGenerateComplementDFA.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGenerateComplementDFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateComplementDFAActionPerformed(evt);
            }
        });
        toolBar.add(btnGenerateComplementDFA);
        toolBar.add(hFiller);

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
        toolBar.add(sep03);

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

        panelModel.setBorder(javax.swing.BorderFactory.createTitledBorder("Model"));

        panelTestsAndSimulation.setBorder(javax.swing.BorderFactory.createTitledBorder("Tests and Simulation"));

        lblTestString.setText("String:");

        txtTestString.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTestStringActionPerformed(evt);
            }
        });

        lblTestResult.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTestResult.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTestResult.setText("TEST RESULT");

        toolBarTestsAndSimulation.setRollover(true);

        btnTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/accept.png"))); // NOI18N
        btnTest.setToolTipText("Execute Test");
        btnTest.setFocusable(false);
        btnTest.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTest.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestActionPerformed(evt);
            }
        });
        toolBarTestsAndSimulation.add(btnTest);

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
        btnReset.setToolTipText("Reset Test");
        btnReset.setFocusable(false);
        btnReset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReset.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        toolBarTestsAndSimulation.add(btnReset);
        toolBarTestsAndSimulation.add(sepTS01);

        btnBatchTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/book.png"))); // NOI18N
        btnBatchTest.setToolTipText("Batch Test");
        btnBatchTest.setFocusable(false);
        btnBatchTest.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBatchTest.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBatchTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatchTestActionPerformed(evt);
            }
        });
        toolBarTestsAndSimulation.add(btnBatchTest);
        toolBarTestsAndSimulation.add(sepTS02);

        btnStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control_play_blue.png"))); // NOI18N
        btnStart.setToolTipText("Start Simulation");
        btnStart.setFocusable(false);
        btnStart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
        toolBarTestsAndSimulation.add(btnStart);

        btnFirstStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control_start_blue.png"))); // NOI18N
        btnFirstStep.setToolTipText("First Step");
        btnFirstStep.setEnabled(false);
        btnFirstStep.setFocusable(false);
        btnFirstStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFirstStep.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFirstStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstStepActionPerformed(evt);
            }
        });
        toolBarTestsAndSimulation.add(btnFirstStep);

        btnPreviousStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control_rewind_blue.png"))); // NOI18N
        btnPreviousStep.setToolTipText("Previous Step");
        btnPreviousStep.setEnabled(false);
        btnPreviousStep.setFocusable(false);
        btnPreviousStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPreviousStep.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPreviousStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousStepActionPerformed(evt);
            }
        });
        toolBarTestsAndSimulation.add(btnPreviousStep);

        btnNextStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control_fastforward_blue.png"))); // NOI18N
        btnNextStep.setToolTipText("Next Step");
        btnNextStep.setEnabled(false);
        btnNextStep.setFocusable(false);
        btnNextStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNextStep.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNextStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextStepActionPerformed(evt);
            }
        });
        toolBarTestsAndSimulation.add(btnNextStep);

        btnLastStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control_end_blue.png"))); // NOI18N
        btnLastStep.setToolTipText("Last Step");
        btnLastStep.setEnabled(false);
        btnLastStep.setFocusable(false);
        btnLastStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLastStep.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLastStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastStepActionPerformed(evt);
            }
        });
        toolBarTestsAndSimulation.add(btnLastStep);

        btnStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control_stop_blue.png"))); // NOI18N
        btnStop.setToolTipText("Stop Simulation");
        btnStop.setEnabled(false);
        btnStop.setFocusable(false);
        btnStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });
        toolBarTestsAndSimulation.add(btnStop);

        javax.swing.GroupLayout panelTestsAndSimulationLayout = new javax.swing.GroupLayout(panelTestsAndSimulation);
        panelTestsAndSimulation.setLayout(panelTestsAndSimulationLayout);
        panelTestsAndSimulationLayout.setHorizontalGroup(
            panelTestsAndSimulationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTestsAndSimulationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTestString)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTestsAndSimulationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toolBarTestsAndSimulation, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(txtTestString))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTestResult, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelTestsAndSimulationLayout.setVerticalGroup(
            panelTestsAndSimulationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTestsAndSimulationLayout.createSequentialGroup()
                .addGroup(panelTestsAndSimulationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblTestResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelTestsAndSimulationLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelTestsAndSimulationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTestString)
                            .addComponent(txtTestString, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(toolBarTestsAndSimulation, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
            .addGap(0, 1148, Short.MAX_VALUE)
        );
        drawPanelLayout.setVerticalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 467, Short.MAX_VALUE)
        );

        scrollPaneModel.setViewportView(drawPanel);

        javax.swing.GroupLayout panelModelLayout = new javax.swing.GroupLayout(panelModel);
        panelModel.setLayout(panelModelLayout);
        panelModelLayout.setHorizontalGroup(
            panelModelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelModelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelModelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTestsAndSimulation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPaneModel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelModelLayout.setVerticalGroup(
            panelModelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelModelLayout.createSequentialGroup()
                .addComponent(scrollPaneModel, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTestsAndSimulation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelProperties.setBorder(javax.swing.BorderFactory.createTitledBorder("Properties"));
        panelProperties.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelModel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelProperties, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelProperties, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelModel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        
        if ( JOptionPane.showConfirmDialog( 
                mainWindow, 
                "Do you really want to create a new model?\n" +
                "All non saved modifications will be lost!", 
                "New model", 
                JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION ) {
            
            fa = new FA();
            
            drawPanel.setFa( fa );
            faPPanel.setFa( fa );
            statePPanel.setFa( fa );
            transitionPPanel.setFa( fa );
            
            selectedState = null;
            selectedTransition = null;
            
            currentState = 0;
            
            faPPanel.readProperties();
            statePPanel.readProperties();
            transitionPPanel.readProperties();
            
            repaintDrawPanel();
            
        }
        
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        Utils.showNotImplementedYetMessage();
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        Utils.showNotImplementedYetMessage();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveActionPerformed
        btnMoveAction();
    }//GEN-LAST:event_btnMoveActionPerformed

    private void btnAddStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStateActionPerformed
        fa.deselectAll();
        cardLayout.show( panelProperties, MODEL_PROPERTIES_CARD );
        repaintDrawPanel();
        drawPanel.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
    }//GEN-LAST:event_btnAddStateActionPerformed

    private void btnAddTransitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTransitionActionPerformed
        fa.deselectAll();
        cardLayout.show( panelProperties, MODEL_PROPERTIES_CARD );
        repaintDrawPanel();
        drawPanel.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
    }//GEN-LAST:event_btnAddTransitionActionPerformed

    private void btnShowTransitionControlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowTransitionControlsActionPerformed
        fa.setTransitionsControlPointsVisible( btnShowTransitionControls.isSelected() );
        repaintDrawPanel();
    }//GEN-LAST:event_btnShowTransitionControlsActionPerformed

    private void btnShowGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowGridActionPerformed
        drawPanel.setShowGrid( btnShowGrid.isSelected() );
        repaintDrawPanel();
    }//GEN-LAST:event_btnShowGridActionPerformed

    private void btnZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomInActionPerformed
        zoomIn();
    }//GEN-LAST:event_btnZoomInActionPerformed

    private void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomOutActionPerformed
        zoomOut();
    }//GEN-LAST:event_btnZoomOutActionPerformed

    private void drawPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawPanelMousePressed
        
        xPressed = evt.getX();
        yPressed = evt.getY();
        
        xPrev = xPressed;
        yPrev = yPressed;
        
        if ( btnMove.isSelected() ) {
            
            fa.deselectAll();
            selectedState = fa.getStateAt( xPressed, yPressed );
            
            if ( selectedState != null ) {
                
                xOffset = xPressed - selectedState.getX1();
                yOffset = yPressed - selectedState.getY1();
                fa.updateTransitions();
                
                statePPanel.setFa( fa );
                statePPanel.setState( selectedState );
                statePPanel.readProperties();
                cardLayout.show( panelProperties, STATE_PROPERTIES_CARD );
                
            } else {
                
                selectedTransition = fa.getTransitionAt( xPressed, yPressed );
                
                if ( selectedTransition != null ) {
                    
                    transitionPPanel.setFa( fa );
                    transitionPPanel.setTransition( selectedTransition );
                    transitionPPanel.readProperties();
                    cardLayout.show( panelProperties, TRANSITION_PROPERTIES_CARD );
                    
                } else {
                    
                    faPPanel.setFa( fa );
                    faPPanel.readProperties();
                    cardLayout.show( panelProperties, MODEL_PROPERTIES_CARD );
                    
                }
                
            }
            
        } else if ( btnAddState.isSelected() ) {
            
            FAState newState = new FAState();
            newState.setX1( xPressed );
            newState.setY1( yPressed );
            newState.setLabel( "q" + currentState++ );
            
            fa.addState( newState );
            
            if ( btnSnapToGrid.isSelected() ) {
                updateSnapPoint( evt );
                newState.setX1( xSnap );
                newState.setY1( ySnap );
            }
            
        } else if ( btnAddTransition.isSelected() ) {
            
            if ( originState == null ) {
                originState = fa.getStateAt( xPressed, yPressed );
                if ( originState != null ) {
                    drawPanel.setDrawingTempTransition( true );
                    drawPanel.setTempTransitionX1( originState.getX1() );
                    drawPanel.setTempTransitionY1( originState.getY1() );
                    drawPanel.setTempTransitionX2( originState.getX1() );
                    drawPanel.setTempTransitionY2( originState.getY1() );
                }
            }
            
        }
        
        faPPanel.setFa( fa );
        faPPanel.readProperties();
        repaintDrawPanel();
        
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
                    targetState = fa.getStateAt( evt.getX(), evt.getY() );
                } 
            
                if ( targetState != null ) {
                    
                    String input = Utils.showInputDialogEmptyString( mainWindow, 
                            "Transition symbol(s)", 
                            "Add Transition Symbol(s)", null );
                    List<Character> symbols = new ArrayList<>();
                    
                    if ( input != null ) {
                        
                        input = input.trim().replace( " ", "" );
                        
                        if ( !input.isEmpty() ) {
                            for ( char c : input.toCharArray() ) {
                                symbols.add( c );
                            }
                            FATransition t = new FATransition( 
                                    originState, targetState, symbols );
                            fa.addTransition( t );
                        }
                        
                    }
                    
                }
                
                originState = null;
                targetState = null;
                drawPanel.setDrawingTempTransition( false );
                
            }
            
            fa.deselectAll();
            
        }
        
        faPPanel.setFa( fa );
        faPPanel.readProperties();
        repaintDrawPanel();
        
    }//GEN-LAST:event_drawPanelMouseReleased
    
    private void drawPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawPanelMouseDragged
        
        if ( btnMove.isSelected() ) {
            
            if ( selectedState != null ) {
                if ( btnSnapToGrid.isSelected() ) {
                    updateSnapPoint( evt );
                    selectedState.setX1( xSnap );
                    selectedState.setY1( ySnap );
                } else {
                    selectedState.setX1( evt.getX() - xOffset );
                    selectedState.setY1( evt.getY() - yOffset );
                }
                fa.updateTransitions();
                fa.draggTransitions( evt );
            } else if ( selectedTransition != null ) {
                selectedTransition.mouseDragged( evt );
            } else {
                int xAmount = evt.getX() - xPrev;
                int yAmount = evt.getY() - yPrev;
                xPrev += xAmount;
                yPrev += yAmount;
                fa.move( xAmount, yAmount );
            }
            
        } else if ( btnAddTransition.isSelected() ) {
            drawPanel.setTempTransitionX2( evt.getX() );
            drawPanel.setTempTransitionY2( evt.getY() );
        }
        
        repaintDrawPanel();
        
    }//GEN-LAST:event_drawPanelMouseDragged

    private void drawPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawPanelMouseMoved
        
        if ( btnMove.isSelected() || btnAddTransition.isSelected() ) {
            fa.mouseHoverStatesAndTransitions( evt.getX(), evt.getY() );
            repaintDrawPanel();
        }
        
    }//GEN-LAST:event_drawPanelMouseMoved

    private void drawPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_drawPanelMouseWheelMoved
        
        if ( evt.isAltDown() ) {
            
            if ( evt.getWheelRotation() > 0 ) {
                zoomOut();
            } else {
                zoomIn();
            }
            
        } else {
            
            JScrollBar sb;

            if ( evt.isShiftDown() ) {
                sb = scrollPaneModel.getHorizontalScrollBar();
            } else {
                sb = scrollPaneModel.getVerticalScrollBar();
            }

            if ( evt.getWheelRotation() > 0 ) {
                sb.setValue( sb.getValue() + sb.getBlockIncrement() );
            } else {
                sb.setValue( sb.getValue() - sb.getBlockIncrement() );
            }
            
        }
        
    }//GEN-LAST:event_drawPanelMouseWheelMoved

    private void btnSaveModelAsImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveModelAsImageActionPerformed
        
        JFileChooser jfc = new JFileChooser( new File( ApplicationPreferences.getPref( ApplicationPreferences.PREF_DEFAULT_FOLDER_PATH ) ) );
        jfc.setDialogTitle( "Save Model as Image" );
        jfc.setMultiSelectionEnabled( false );
        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        jfc.removeChoosableFileFilter( jfc.getFileFilter() );
        jfc.setFileFilter( new FileNameExtensionFilter( "PNG Image File", "png" ) );

        if ( jfc.showSaveDialog( mainWindow ) == JFileChooser.APPROVE_OPTION ) {

            File f = jfc.getSelectedFile();
            
            if ( !f.getName().endsWith( ".png" ) ) {
                f = new File( f.getAbsolutePath() + ".png" );
            }
            
            boolean save = true;

            if ( f.exists() ) {
                if ( JOptionPane.showConfirmDialog( 
                        mainWindow, 
                        "The file already exists. Do you want to overwrite it?", 
                        "Confirmation", 
                        JOptionPane.YES_NO_OPTION ) == JOptionPane.NO_OPTION ) {
                    save = false;
                }
            }

            if ( save ) {
                
                ApplicationPreferences.setPref( 
                        ApplicationPreferences.PREF_DEFAULT_FOLDER_PATH, 
                        f.getParentFile().getAbsolutePath() );
                
                int r = JOptionPane.showConfirmDialog( 
                        mainWindow, 
                        "Do you want transparent background?", 
                        "Transparent background", 
                        JOptionPane.YES_NO_OPTION );

                BufferedImage img = new BufferedImage( 
                        drawPanel.getWidth(), 
                        drawPanel.getHeight(), 
                        BufferedImage.TYPE_INT_ARGB );

                Graphics2D g2d = img.createGraphics();
                g2d.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON );

                if ( r != JOptionPane.YES_OPTION ) {
                    g2d.setColor( Color.WHITE );
                    g2d.fillRect( 0, 0, img.getWidth(), img.getHeight() );
                }

                fa.draw( g2d );

                try {
                    ImageIO.write( img, "png", f );
                } catch ( IOException exc ) {
                    Utils.showException( exc );
                }
                
            }
            
        }
        
    }//GEN-LAST:event_btnSaveModelAsImageActionPerformed

    private void btnTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestActionPerformed
        runSingleTest();
    }//GEN-LAST:event_btnTestActionPerformed
    
    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        
        if ( fa.canExecute() ) {
            
            txtTestString.setEditable( false );
            resetTestInGUI();
            disableGUI();
            simulationSteps.clear();
            currentSimulationStep = 0;
            
            boolean accepted = fa.accepts( txtTestString.getText(), simulationSteps );
            
            btnStart.setEnabled( false );
            btnStop.setEnabled( true );
            
            drawPanel.setSimulationString( txtTestString.getText() );
            drawPanel.setSimulationSteps( simulationSteps );
            drawPanel.setCurrentSimulationStep( currentSimulationStep );
            drawPanel.setSimulationAccepted( accepted );
            drawPanel.repaint();
            
            updateSimulationButtons( currentSimulationStep );
            activateSimulationStep( currentSimulationStep );
            
        } else {
            
            JOptionPane.showMessageDialog( 
                    mainWindow, 
                    "You must set an initial state!", 
                    "ERROR", 
                    JOptionPane.ERROR_MESSAGE );
            
        }
        
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnFirstStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstStepActionPerformed
        currentSimulationStep = 0;
        resetTestInGUI();
        updateSimulationButtons( currentSimulationStep );
        activateSimulationStep( currentSimulationStep );
    }//GEN-LAST:event_btnFirstStepActionPerformed

    private void btnPreviousStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousStepActionPerformed
        currentSimulationStep--;
        if ( currentSimulationStep < 0 ) {
            currentSimulationStep = 0;
        }
        resetTestInGUI();
        updateSimulationButtons( currentSimulationStep );
        activateSimulationStep( currentSimulationStep );
    }//GEN-LAST:event_btnPreviousStepActionPerformed

    private void btnNextStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextStepActionPerformed
        currentSimulationStep++;
        if ( currentSimulationStep >= simulationSteps.size() ) {
            currentSimulationStep--;
        }
        resetTestInGUI();
        updateSimulationButtons( currentSimulationStep );
        activateSimulationStep( currentSimulationStep );
    }//GEN-LAST:event_btnNextStepActionPerformed

    private void btnLastStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastStepActionPerformed
        currentSimulationStep = simulationSteps.size()-1;
        resetTestInGUI();
        updateSimulationButtons( currentSimulationStep );
        activateSimulationStep( currentSimulationStep );
    }//GEN-LAST:event_btnLastStepActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        
        simulationSteps.clear();
        fa.deactivateAllStatesInSimulation();
        txtTestString.setEditable( true );
        resetTestInGUI();
        enableGUI();
        
        btnStart.setEnabled( true );
        btnFirstStep.setEnabled( false );
        btnPreviousStep.setEnabled( false );
        btnNextStep.setEnabled( false );
        btnLastStep.setEnabled( false );
        btnStop.setEnabled( false );
            
        drawPanel.setSimulationString( null );
        drawPanel.setSimulationSteps( null );
        drawPanel.setCurrentSimulationStep( 0 );
        drawPanel.setSimulationAccepted( false );
        drawPanel.repaint();
        
    }//GEN-LAST:event_btnStopActionPerformed

    private void btnBatchTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatchTestActionPerformed
        if ( faBatchTestDialog == null ) {
            faBatchTestDialog = new FABatchTest( mainWindow, true );
        }
        faBatchTestDialog.setFa( fa );
        faBatchTestDialog.setVisible( true );
    }//GEN-LAST:event_btnBatchTestActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        resetTestInGUI();
        txtTestString.setText( "" );
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnGenerateEquivalentDFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateEquivalentDFAActionPerformed
        
        FA dfa = FAAlgorithms.removeNonDeterminisms( fa );
        FAAlgorithms.arrangeFAInCircle( dfa, 250, 200, 150 );
        dfa.resetTransitionsTransformations();
        mainWindow.createFAInternalFrame( dfa, false );
        
    }//GEN-LAST:event_btnGenerateEquivalentDFAActionPerformed

    private void btnGenerateMinimizedDFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateMinimizedDFAActionPerformed
        Utils.showNotImplementedYetMessage();
    }//GEN-LAST:event_btnGenerateMinimizedDFAActionPerformed

    private void btnGenerateComplementDFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateComplementDFAActionPerformed
        Utils.showNotImplementedYetMessage();
    }//GEN-LAST:event_btnGenerateComplementDFAActionPerformed

    private void btnSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAsActionPerformed
        Utils.showNotImplementedYetMessage();
    }//GEN-LAST:event_btnSaveAsActionPerformed

    private void txtTestStringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTestStringActionPerformed
        runSingleTest();
    }//GEN-LAST:event_txtTestStringActionPerformed

    public void repaintDrawPanel() {
        drawPanel.repaint();
        drawPanel.setPreferredSize( new Dimension( fa.getWidth(), fa.getHeight() ) );
        drawPanel.revalidate();
    }
    
    public void updateAfterRemotion() {
        faPPanel.setFa( fa );
        faPPanel.readProperties();
        cardLayout.show( panelProperties, MODEL_PROPERTIES_CARD );
        repaintDrawPanel();
    }
    
    public void updateAfterUpdate() {
        fa.updateType();
        faPPanel.setFa( fa );
        faPPanel.readProperties();
    }
    
    private void zoomIn() {
        
        zoomFacility.zoomIn();
        
        btnZoomOut.setEnabled( true );
        if ( !zoomFacility.canZoomIn() ) {
            btnZoomIn.setEnabled( false );
        }
        
        repaintDrawPanel();
        
    }
    
    private void zoomOut() {
        
        zoomFacility.zoomOut();
        
        btnZoomIn.setEnabled( true );
        if ( !zoomFacility.canZoomOut() ) {
            btnZoomOut.setEnabled( false );
        }
        
        repaintDrawPanel();
        
    }
    
    private void runSingleTest() throws HeadlessException {
        
        if ( fa.canExecute() ) {
            
            if ( fa.accepts( txtTestString.getText() ) ) {
                setTestToAcceptedInGUI( );
            } else {
                setTestToRejectedInGUI( );
            }
            
        } else {
            
            JOptionPane.showMessageDialog(
                    mainWindow,
                    "You must set an initial state!",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE );
            
        }
        
    }
    
    private void updateSimulationButtons( int step ) {
        
        if ( step == 0 && simulationSteps.size() == 1 ) {
            btnFirstStep.setEnabled( false );
            btnPreviousStep.setEnabled( false );
            btnNextStep.setEnabled( false );
            btnLastStep.setEnabled( false );
        } else if ( step == 0 ) {
            btnFirstStep.setEnabled( false );
            btnPreviousStep.setEnabled( false );
            btnNextStep.setEnabled( true );
            btnLastStep.setEnabled( true );
        } else if ( step == simulationSteps.size() - 1 ) {
            btnFirstStep.setEnabled( true );
            btnPreviousStep.setEnabled( true );
            btnNextStep.setEnabled( false );
            btnLastStep.setEnabled( false );
        } else {
            btnFirstStep.setEnabled( true );
            btnPreviousStep.setEnabled( true );
            btnNextStep.setEnabled( true );
            btnLastStep.setEnabled( true );
        }
        
    }
    
    private void setTestToAcceptedInGUI() {
        lblTestResult.setForeground(
                DrawingConstants.ACCEPTED_LABEL_FOREGROUND_COLOR );
        txtTestString.setForeground(
                DrawingConstants.ACCEPTED_TEXTFIELD_FOREGROUND_COLOR );
        txtTestString.setBackground(
                DrawingConstants.ACCEPTED_TEXTFIELD_BACKGROUND_COLOR );
        lblTestResult.setText( "ACCEPTED" );
    }
    
    private void setTestToRejectedInGUI() {
        lblTestResult.setForeground(
                DrawingConstants.REJECTED_LABEL_FOREGROUND_COLOR );
        txtTestString.setForeground(
                DrawingConstants.REJECTED_TEXTFIELD_FOREGROUND_COLOR );
        txtTestString.setBackground(
                DrawingConstants.REJECTED_TEXTFIELD_BACKGROUND_COLOR );
        lblTestResult.setText( "REJECTED" );
    }
    
    private void resetTestInGUI() {
        txtTestString.setBackground( txtTestStringDefaultBC );
        txtTestString.setForeground( txtTestStringDefaultFC );
        lblTestResult.setForeground( lblTestResultDefaultFC );
        lblTestResult.setText( "TEST RESULT" );
    }
    
    private void btnMoveAction() {
        fa.deselectAll();
        cardLayout.show( panelProperties, MODEL_PROPERTIES_CARD );
        repaintDrawPanel();
        drawPanel.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
    }
    
    private void disableGUI() {
        
        btnNew.setEnabled( false );
        btnOpen.setEnabled( false );
        btnSave.setEnabled( false );
        btnSaveAs.setEnabled( false );
        btnSaveModelAsImage.setEnabled( false );
        
        btnMove.setSelected( true );
        btnMoveAction();
        btnMove.setEnabled( false );
        btnAddState.setEnabled( false );
        btnAddTransition.setEnabled( false );
        
        btnGenerateEquivalentDFA.setEnabled( false );
        btnGenerateMinimizedDFA.setEnabled( false );
        btnGenerateComplementDFA.setEnabled( false );
        
        btnTest.setEnabled( false );
        btnReset.setEnabled( false );
        btnBatchTest.setEnabled( false );
        
        statePPanel.disableGUI();
        transitionPPanel.disableGUI();
        
    }
    
    private void enableGUI() {
        
        btnNew.setEnabled( true );
        btnOpen.setEnabled( true );
        btnSave.setEnabled( true );
        btnSaveAs.setEnabled( true );
        btnSaveModelAsImage.setEnabled( true );
        
        btnMove.setSelected( true );
        btnMove.setEnabled( true );
        btnAddState.setEnabled( true );
        btnAddTransition.setEnabled( true );
        
        btnGenerateEquivalentDFA.setEnabled( true );
        btnGenerateMinimizedDFA.setEnabled( true );
        btnGenerateComplementDFA.setEnabled( true );
        
        btnTest.setEnabled( true );
        btnReset.setEnabled( true );
        btnBatchTest.setEnabled( true );
        
        statePPanel.enableGUI();
        transitionPPanel.enableGUI();
        
    }
    
    private void activateSimulationStep( int step ) {
        
        if ( step >= 0 && step < simulationSteps.size() ) {
            
            FASimulationStep current = simulationSteps.get( step );
            current.activateInFA( fa );
            drawPanel.setCurrentSimulationStep( step );
            
            if ( step == simulationSteps.size() - 1 ) {
                if ( drawPanel.isSimulationAccepted() ) {
                    setTestToAcceptedInGUI();
                } else {
                    setTestToRejectedInGUI();
                }
            }
            
            drawPanel.repaint();
            
        }
        
    }
    
    private void updateSnapPoint( MouseEvent evt ) {
        
        xSnap = ( evt.getX() + DrawingConstants.STATE_RADIUS / 2 ) / 
                DrawingConstants.STATE_RADIUS * 
                DrawingConstants.STATE_RADIUS;
        ySnap = ( evt.getY() + DrawingConstants.STATE_RADIUS / 2 ) / 
                DrawingConstants.STATE_RADIUS * 
                DrawingConstants.STATE_RADIUS;
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAddState;
    private javax.swing.JToggleButton btnAddTransition;
    private javax.swing.JButton btnBatchTest;
    private javax.swing.JButton btnFirstStep;
    private javax.swing.JButton btnGenerateComplementDFA;
    private javax.swing.JButton btnGenerateEquivalentDFA;
    private javax.swing.JButton btnGenerateMinimizedDFA;
    private javax.swing.ButtonGroup btnGroup;
    private javax.swing.JButton btnLastStep;
    private javax.swing.JToggleButton btnMove;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNextStep;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnPreviousStep;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSaveAs;
    private javax.swing.JButton btnSaveModelAsImage;
    private javax.swing.JToggleButton btnShowGrid;
    private javax.swing.JToggleButton btnShowTransitionControls;
    private javax.swing.JToggleButton btnSnapToGrid;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JButton btnTest;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    private br.com.davidbuzatto.yaas.gui.DrawPanel drawPanel;
    private javax.swing.Box.Filler hFiller;
    private javax.swing.JLabel lblTestResult;
    private javax.swing.JLabel lblTestString;
    private javax.swing.JPanel panelModel;
    private javax.swing.JPanel panelProperties;
    private javax.swing.JPanel panelTestsAndSimulation;
    private javax.swing.JScrollPane scrollPaneModel;
    private javax.swing.JToolBar.Separator sep01;
    private javax.swing.JToolBar.Separator sep02;
    private javax.swing.JToolBar.Separator sep03;
    private javax.swing.JToolBar.Separator sep04;
    private javax.swing.JToolBar.Separator sepTS01;
    private javax.swing.JToolBar.Separator sepTS02;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JToolBar toolBarTestsAndSimulation;
    private javax.swing.JTextField txtTestString;
    // End of variables declaration//GEN-END:variables
}
