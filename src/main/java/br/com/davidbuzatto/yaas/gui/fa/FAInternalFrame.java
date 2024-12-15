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

import br.com.davidbuzatto.yaas.model.fa.FATransition;
import br.com.davidbuzatto.yaas.model.fa.FA;
import br.com.davidbuzatto.yaas.model.fa.FAType;
import br.com.davidbuzatto.yaas.model.fa.FAState;
import br.com.davidbuzatto.yaas.gui.MainWindow;
import br.com.davidbuzatto.yaas.model.fa.algorithms.FAArrangement;
import br.com.davidbuzatto.yaas.model.fa.algorithms.DFAComplement;
import br.com.davidbuzatto.yaas.model.fa.algorithms.DFAMinimize;
import br.com.davidbuzatto.yaas.model.fa.algorithms.FADeterminize;
import br.com.davidbuzatto.yaas.model.fa.algorithms.DFATotalTransitionFunction;
import br.com.davidbuzatto.yaas.model.fa.algorithms.FAConcatenation;
import br.com.davidbuzatto.yaas.model.fa.algorithms.FAIntersection;
import br.com.davidbuzatto.yaas.model.fa.algorithms.FAKleeneStar;
import br.com.davidbuzatto.yaas.model.fa.algorithms.FARemoveInaccessibleAndUselessStates;
import br.com.davidbuzatto.yaas.model.fa.algorithms.FAUnion;
import br.com.davidbuzatto.yaas.gui.fa.properties.FAPropertiesPanel;
import br.com.davidbuzatto.yaas.gui.fa.properties.FAStatePropertiesPanel;
import br.com.davidbuzatto.yaas.gui.fa.properties.FATransitionPropertiesPanel;
import br.com.davidbuzatto.yaas.util.ApplicationConstants;
import br.com.davidbuzatto.yaas.util.ApplicationPreferences;
import br.com.davidbuzatto.yaas.util.DrawingConstants;
import br.com.davidbuzatto.yaas.util.Utils;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
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
    
    private boolean canDrag;
    
    private int xPressed;
    private int yPressed;
    
    private int xPrev;
    private int yPrev;
    
    private int xSnap;
    private int ySnap;
    
    private int currentState;
    
    private FAState selectedState;
    private FATransition selectedTransition;
    private Set<FAState> selectedStates;
    
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
    private Color txtTestStringDefaultCaretColor;
    private Color lblTestResultDefaultFC;
    
    private boolean currentFileSaved;
    private File currentFile;
    private String baseTitle;
    
    private Deque<FA> undoStack;
    private Deque<FA> redoStack;
    
    /**
     * Creates new form FAInternalFrame
     */
    public FAInternalFrame( MainWindow mainWindow ) {
        this( mainWindow, new FA() );
    }
    
    public FAInternalFrame( MainWindow mainWindow, FA fa ) {
        
        this.mainWindow = mainWindow;
        this.simulationSteps = new ArrayList<>();
       
        if ( fa == null ) {
            this.fa = new FA();
        } else {
            this.fa = fa;
        }
        
        this.selectedStates = new HashSet<>();
        
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
        
        saveDefaultColors();
        
        scrollPaneModel.getHorizontalScrollBar().addAdjustmentListener( 
                new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged( AdjustmentEvent e ) {
                repaintDrawPanel();
            }
        });
        
        scrollPaneModel.getVerticalScrollBar().addAdjustmentListener( 
                new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged( AdjustmentEvent e ) {
                repaintDrawPanel();
            }
        });
        
        if ( !ApplicationConstants.IN_DEVELOPMENT ) {
            btnCodeGen.setVisible( false );
            btnClone.setVisible( false );
        }
        
        baseTitle = getTitle();
        setCurrentFileSaved( true );
        
        registerActions();
        repaintDrawPanel();
        
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
        popupMenuRegularOperations = new javax.swing.JPopupMenu();
        popItemUnion = new javax.swing.JMenuItem();
        popItemConcatenation = new javax.swing.JMenuItem();
        popItemKleeneStar = new javax.swing.JMenuItem();
        popItemComplement = new javax.swing.JMenuItem();
        popItemIntersection = new javax.swing.JMenuItem();
        popupMenuReorganizeStates = new javax.swing.JPopupMenu();
        popItemHorizontal = new javax.swing.JMenuItem();
        popItemVertical = new javax.swing.JMenuItem();
        popItemDiagonal = new javax.swing.JMenuItem();
        popItemRectangular = new javax.swing.JMenuItem();
        popItemCircular = new javax.swing.JMenuItem();
        popItemByLevel = new javax.swing.JMenuItem();
        popupMenuStateProperties = new javax.swing.JPopupMenu();
        popItemStateCustomLabel = new javax.swing.JMenuItem();
        spPopupState01 = new javax.swing.JPopupMenu.Separator();
        checkInitialState = new javax.swing.JCheckBoxMenuItem();
        checkFinalState = new javax.swing.JCheckBoxMenuItem();
        spPopupState = new javax.swing.JPopupMenu.Separator();
        popItemStateColor = new javax.swing.JMenuItem();
        popItemRemoveState = new javax.swing.JMenuItem();
        popupMenuTransitionProperties = new javax.swing.JPopupMenu();
        popItemTransitionSymbols = new javax.swing.JMenuItem();
        spPopupTransition01 = new javax.swing.JPopupMenu.Separator();
        popItemTransitionColor = new javax.swing.JMenuItem();
        spPopupTransition02 = new javax.swing.JPopupMenu.Separator();
        popItemResetTransitionTransformations = new javax.swing.JMenuItem();
        popItemRemoveTransition = new javax.swing.JMenuItem();
        toolBar = new javax.swing.JToolBar();
        btnNew = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnSaveAs = new javax.swing.JButton();
        btnSaveAsImage = new javax.swing.JButton();
        btnCodeGen = new javax.swing.JButton();
        btnClone = new javax.swing.JButton();
        sep01 = new javax.swing.JToolBar.Separator();
        btnSelectMultipleStates = new javax.swing.JToggleButton();
        btnMove = new javax.swing.JToggleButton();
        btnAddState = new javax.swing.JToggleButton();
        btnAddTransition = new javax.swing.JToggleButton();
        sep02 = new javax.swing.JToolBar.Separator();
        btnAddAllMissingTransitionsDFA = new javax.swing.JButton();
        btnRemoveInaccessibleAndUselessStates = new javax.swing.JButton();
        sep03 = new javax.swing.JToolBar.Separator();
        btnGenerateEquivalentDFA = new javax.swing.JButton();
        btnGenerateMinimizedDFA = new javax.swing.JButton();
        btnRegularOperations = new javax.swing.JButton();
        hFiller = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        btnShowTransitionControls = new javax.swing.JToggleButton();
        sep04 = new javax.swing.JToolBar.Separator();
        btnShowGrid = new javax.swing.JToggleButton();
        btnSnapToGrid = new javax.swing.JToggleButton();
        sep05 = new javax.swing.JToolBar.Separator();
        btnRearrangeStates = new javax.swing.JButton();
        sep06 = new javax.swing.JToolBar.Separator();
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

        popItemUnion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/union.png"))); // NOI18N
        popItemUnion.setText("Union");
        popItemUnion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemUnionActionPerformed(evt);
            }
        });
        popupMenuRegularOperations.add(popItemUnion);

        popItemConcatenation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/concatenation.png"))); // NOI18N
        popItemConcatenation.setText("Concatenation");
        popItemConcatenation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemConcatenationActionPerformed(evt);
            }
        });
        popupMenuRegularOperations.add(popItemConcatenation);

        popItemKleeneStar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kleeneStar.png"))); // NOI18N
        popItemKleeneStar.setText("Kleene Star");
        popItemKleeneStar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemKleeneStarActionPerformed(evt);
            }
        });
        popupMenuRegularOperations.add(popItemKleeneStar);

        popItemComplement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/complement.png"))); // NOI18N
        popItemComplement.setText("Complement");
        popItemComplement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemComplementActionPerformed(evt);
            }
        });
        popupMenuRegularOperations.add(popItemComplement);

        popItemIntersection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/intersection.png"))); // NOI18N
        popItemIntersection.setText("Intersection");
        popItemIntersection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemIntersectionActionPerformed(evt);
            }
        });
        popupMenuRegularOperations.add(popItemIntersection);

        popItemHorizontal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rearrangeHorizontal.png"))); // NOI18N
        popItemHorizontal.setText("Horizontal");
        popItemHorizontal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemHorizontalActionPerformed(evt);
            }
        });
        popupMenuReorganizeStates.add(popItemHorizontal);

        popItemVertical.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rearrangeVertical.png"))); // NOI18N
        popItemVertical.setText("Vertical");
        popItemVertical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemVerticalActionPerformed(evt);
            }
        });
        popupMenuReorganizeStates.add(popItemVertical);

        popItemDiagonal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rearrangeDiagonal.png"))); // NOI18N
        popItemDiagonal.setText("Diagonal");
        popItemDiagonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemDiagonalActionPerformed(evt);
            }
        });
        popupMenuReorganizeStates.add(popItemDiagonal);

        popItemRectangular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rearrangeRectangular.png"))); // NOI18N
        popItemRectangular.setText("Rectangular");
        popItemRectangular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemRectangularActionPerformed(evt);
            }
        });
        popupMenuReorganizeStates.add(popItemRectangular);

        popItemCircular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rearrangeCircular.png"))); // NOI18N
        popItemCircular.setText("Circular");
        popItemCircular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemCircularActionPerformed(evt);
            }
        });
        popupMenuReorganizeStates.add(popItemCircular);

        popItemByLevel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rearrangeLevel.png"))); // NOI18N
        popItemByLevel.setText("By Level");
        popItemByLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemByLevelActionPerformed(evt);
            }
        });
        popupMenuReorganizeStates.add(popItemByLevel);

        popupMenuStateProperties.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
                popupMenuStatePropertiesPopupMenuCanceled(evt);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        popItemStateCustomLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pencil.png"))); // NOI18N
        popItemStateCustomLabel.setText("Custom Label");
        popItemStateCustomLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemStateCustomLabelActionPerformed(evt);
            }
        });
        popupMenuStateProperties.add(popItemStateCustomLabel);
        popupMenuStateProperties.add(spPopupState01);

        checkInitialState.setText("Initial");
        checkInitialState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkInitialStateActionPerformed(evt);
            }
        });
        popupMenuStateProperties.add(checkInitialState);

        checkFinalState.setText("Final");
        checkFinalState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkFinalStateActionPerformed(evt);
            }
        });
        popupMenuStateProperties.add(checkFinalState);
        popupMenuStateProperties.add(spPopupState);

        popItemStateColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/palette.png"))); // NOI18N
        popItemStateColor.setText("Color");
        popItemStateColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemStateColorActionPerformed(evt);
            }
        });
        popupMenuStateProperties.add(popItemStateColor);

        popItemRemoveState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
        popItemRemoveState.setText("Remove");
        popItemRemoveState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemRemoveStateActionPerformed(evt);
            }
        });
        popupMenuStateProperties.add(popItemRemoveState);

        popupMenuTransitionProperties.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
                popupMenuTransitionPropertiesPopupMenuCanceled(evt);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        popItemTransitionSymbols.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pencil.png"))); // NOI18N
        popItemTransitionSymbols.setText("Symbol(s)");
        popItemTransitionSymbols.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemTransitionSymbolsActionPerformed(evt);
            }
        });
        popupMenuTransitionProperties.add(popItemTransitionSymbols);
        popupMenuTransitionProperties.add(spPopupTransition01);

        popItemTransitionColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/palette.png"))); // NOI18N
        popItemTransitionColor.setText("Color");
        popItemTransitionColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemTransitionColorActionPerformed(evt);
            }
        });
        popupMenuTransitionProperties.add(popItemTransitionColor);
        popupMenuTransitionProperties.add(spPopupTransition02);

        popItemResetTransitionTransformations.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancel.png"))); // NOI18N
        popItemResetTransitionTransformations.setText("Reset Transformations");
        popItemResetTransitionTransformations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemResetTransitionTransformationsActionPerformed(evt);
            }
        });
        popupMenuTransitionProperties.add(popItemResetTransitionTransformations);

        popItemRemoveTransition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
        popItemRemoveTransition.setText("Remove");
        popItemRemoveTransition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popItemRemoveTransitionActionPerformed(evt);
            }
        });
        popupMenuTransitionProperties.add(popItemRemoveTransition);

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Finite Automata");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/fa.png"))); // NOI18N
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        toolBar.setRollover(true);

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/page_white_add.png"))); // NOI18N
        btnNew.setToolTipText("New (Ctrl+N)");
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
        btnOpen.setToolTipText("Open (Ctrl+O)");
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
        btnSave.setToolTipText("Save (Ctrl+S)");
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
        btnSaveAs.setToolTipText("Save as... (Ctrl+Shift+S)");
        btnSaveAs.setFocusable(false);
        btnSaveAs.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSaveAs.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAsActionPerformed(evt);
            }
        });
        toolBar.add(btnSaveAs);

        btnSaveAsImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture.png"))); // NOI18N
        btnSaveAsImage.setToolTipText("Save as Image (Ctrl+Shift+I)");
        btnSaveAsImage.setFocusable(false);
        btnSaveAsImage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSaveAsImage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSaveAsImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAsImageActionPerformed(evt);
            }
        });
        toolBar.add(btnSaveAsImage);

        btnCodeGen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/script_gear.png"))); // NOI18N
        btnCodeGen.setToolTipText("Generate Code and Copy to Clipboard");
        btnCodeGen.setFocusable(false);
        btnCodeGen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCodeGen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCodeGen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodeGenActionPerformed(evt);
            }
        });
        toolBar.add(btnCodeGen);

        btnClone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/script_go.png"))); // NOI18N
        btnClone.setToolTipText("Clone Current Machine");
        btnClone.setFocusable(false);
        btnClone.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClone.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloneActionPerformed(evt);
            }
        });
        toolBar.add(btnClone);
        toolBar.add(sep01);

        btnGroup.add(btnSelectMultipleStates);
        btnSelectMultipleStates.setIcon(new javax.swing.ImageIcon(getClass().getResource("/selection.png"))); // NOI18N
        btnSelectMultipleStates.setToolTipText("Select Multiple States (Shift+U)");
        btnSelectMultipleStates.setFocusable(false);
        btnSelectMultipleStates.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSelectMultipleStates.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSelectMultipleStates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectMultipleStatesActionPerformed(evt);
            }
        });
        toolBar.add(btnSelectMultipleStates);

        btnGroup.add(btnMove);
        btnMove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cursor_openhand.png"))); // NOI18N
        btnMove.setSelected(true);
        btnMove.setToolTipText("Move (Shift+M)");
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
        btnAddState.setToolTipText("Add State (Shift+S)");
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
        btnAddTransition.setToolTipText("Add Transition (Shift+T)");
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

        btnAddAllMissingTransitionsDFA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transitionAddAll.png"))); // NOI18N
        btnAddAllMissingTransitionsDFA.setToolTipText("Add All Missing Transitions (Alt+Shift+T)");
        btnAddAllMissingTransitionsDFA.setFocusable(false);
        btnAddAllMissingTransitionsDFA.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddAllMissingTransitionsDFA.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddAllMissingTransitionsDFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAllMissingTransitionsDFAActionPerformed(evt);
            }
        });
        toolBar.add(btnAddAllMissingTransitionsDFA);

        btnRemoveInaccessibleAndUselessStates.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stateRemoveUI.png"))); // NOI18N
        btnRemoveInaccessibleAndUselessStates.setToolTipText("Remove Inaccessible and Useless States (Alt+Shift+DELETE)");
        btnRemoveInaccessibleAndUselessStates.setFocusable(false);
        btnRemoveInaccessibleAndUselessStates.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRemoveInaccessibleAndUselessStates.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemoveInaccessibleAndUselessStates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveInaccessibleAndUselessStatesActionPerformed(evt);
            }
        });
        toolBar.add(btnRemoveInaccessibleAndUselessStates);
        toolBar.add(sep03);

        btnGenerateEquivalentDFA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dfa.png"))); // NOI18N
        btnGenerateEquivalentDFA.setToolTipText("Generate Equivalent DFA (Alt+Shift+D)");
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
        btnGenerateMinimizedDFA.setToolTipText("Generate Minimized DFA (Alt+Shift+M)");
        btnGenerateMinimizedDFA.setFocusable(false);
        btnGenerateMinimizedDFA.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGenerateMinimizedDFA.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGenerateMinimizedDFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateMinimizedDFAActionPerformed(evt);
            }
        });
        toolBar.add(btnGenerateMinimizedDFA);

        btnRegularOperations.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lightning.png"))); // NOI18N
        btnRegularOperations.setToolTipText("Regular Operations");
        btnRegularOperations.setFocusable(false);
        btnRegularOperations.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRegularOperations.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRegularOperations.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnRegularOperationsMouseReleased(evt);
            }
        });
        toolBar.add(btnRegularOperations);
        toolBar.add(hFiller);

        btnShowTransitionControls.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shape_square_edit.png"))); // NOI18N
        btnShowTransitionControls.setToolTipText("Show/Hide Transition Control Points (Ctrl+Shift+C)");
        btnShowTransitionControls.setFocusable(false);
        btnShowTransitionControls.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnShowTransitionControls.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnShowTransitionControls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowTransitionControlsActionPerformed(evt);
            }
        });
        toolBar.add(btnShowTransitionControls);
        toolBar.add(sep04);

        btnShowGrid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/note.png"))); // NOI18N
        btnShowGrid.setToolTipText("Show/Hide Grid (Ctrl+Shift+G)");
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
        btnSnapToGrid.setToolTipText("Snap to Grid (Ctrl+Shift+N)");
        btnSnapToGrid.setFocusable(false);
        btnSnapToGrid.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSnapToGrid.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(btnSnapToGrid);
        toolBar.add(sep05);

        btnRearrangeStates.setIcon(new javax.swing.ImageIcon(getClass().getResource("/layers.png"))); // NOI18N
        btnRearrangeStates.setToolTipText("Rearrange States");
        btnRearrangeStates.setFocusable(false);
        btnRearrangeStates.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRearrangeStates.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRearrangeStates.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnRearrangeStatesMouseReleased(evt);
            }
        });
        toolBar.add(btnRearrangeStates);
        toolBar.add(sep06);

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
        txtTestString.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTestStringKeyReleased(evt);
            }
        });

        lblTestResult.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTestResult.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTestResult.setText("TEST RESULT");

        toolBarTestsAndSimulation.setRollover(true);

        btnTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/accept.png"))); // NOI18N
        btnTest.setToolTipText("Execute Test (Ctrl+Alt+T)");
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
        btnBatchTest.setToolTipText("Batch Test (Ctrl+Alt+B)");
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
        btnStart.setToolTipText("Start Simulation (Ctrl+Alt+S)");
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
        btnFirstStep.setToolTipText("First Step (Ctrl+Alt+Left)");
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
        btnPreviousStep.setToolTipText("Previous Step (Ctrl+Left)");
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
        btnNextStep.setToolTipText("Next Step (Ctrl+Right)");
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
        btnLastStep.setToolTipText("Last Step (Ctrl+Alt+Right)");
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
        btnStop.setToolTipText("Stop Simulation (Esc)");
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
                this,
                """
                Do you really want to create a new model?
                All non saved modifications will be lost!
                """,
                "New model",
                JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION ) {
            
            fa = new FA();
            setCurrentFileSaved( false );
            
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
    
    private void btnMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveActionPerformed
        btnMoveAction();
    }//GEN-LAST:event_btnMoveActionPerformed

    private void btnAddStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStateActionPerformed
        resetGUIToAddStatesAndTransitions();
    }//GEN-LAST:event_btnAddStateActionPerformed

    private void btnAddTransitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTransitionActionPerformed
        resetGUIToAddStatesAndTransitions();
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

        drawPanel.requestFocus();
        
        xPressed = drawPanel.getZoomFacility().screenToWorld( evt.getX() );
        yPressed = drawPanel.getZoomFacility().screenToWorld( evt.getY() );
        
        xPrev = xPressed;
        yPrev = yPressed;
        
        if ( evt.getButton() == MouseEvent.BUTTON1 ) {
            
            canDrag = true;
            
            if ( btnSelectMultipleStates.isSelected() ) {

                if ( !evt.isShiftDown() ) {
                    selectedStates.clear();
                }

                for ( FAState s : fa.getStates() ) {
                    if ( !selectedStates.contains( s ) ) {
                        s.setSelected( false );
                    }
                    s.setMouseHover( false );
                }

            } else if ( btnMove.isSelected() ) {

                boolean defaultMove = false;

                if ( !selectedStates.isEmpty() ) {

                    selectedState = fa.getStateAt( xPressed, yPressed );

                    if ( selectedStates.contains( selectedState ) ) {
                        fa.updateTransitions();
                    } else {
                        selectedStates.clear();
                        defaultMove = true;
                    }

                } else {
                    defaultMove = true;
                }

                if ( defaultMove ) {

                    fa.deselectAll();
                    selectedState = fa.getStateAt( xPressed, yPressed );

                    if ( selectedState != null ) {
                        
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

                }

            } else if ( btnAddState.isSelected() ) {

                FAState newState = new FAState( currentState++ );
                newState.setX1Y1( xPressed, yPressed );

                fa.addState( newState );

                if ( btnSnapToGrid.isSelected() ) {
                    updateSnapPoint( evt );
                    newState.setX1Y1( xSnap, ySnap );
                }

                setCurrentFileSaved( false );

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
            
        } else if ( evt.getButton() == MouseEvent.BUTTON3 ) {
        }
        
        faPPanel.setFa( fa );
        faPPanel.readProperties();
        repaintDrawPanel();
        
    }//GEN-LAST:event_drawPanelMousePressed

    private void drawPanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawPanelMouseReleased
        
        canDrag = false;
        
        int xEvt = drawPanel.getZoomFacility().screenToWorld( evt.getX() );
        int yEvt = drawPanel.getZoomFacility().screenToWorld( evt.getY() );
        
        if ( evt.getButton() == MouseEvent.BUTTON1 ) {
            
            if ( btnSelectMultipleStates.isSelected() ) {

                Rectangle rectangle = new Rectangle( 
                        xPressed <= xEvt ? xPressed : xEvt, 
                        yPressed <= yEvt ? yPressed : yEvt, 
                        xPressed <= xEvt ? xEvt - xPressed : xPressed - xEvt, 
                        yPressed <= yEvt ? yEvt - yPressed : yPressed - yEvt );

                for ( FAState s : fa.getStates() ) {
                    if ( s.intersects( rectangle ) ) {
                        s.setSelected( true );
                        selectedStates.add( s );
                    }
                    s.setMouseHover( false );
                }

                drawPanel.setSelectionRectangle( null );

            } else if ( btnMove.isSelected() ) {

                selectedState = null;

                if ( selectedTransition != null ) {
                    selectedTransition.mouseReleased( evt );
                    selectedTransition = null;
                }

            } else if ( btnAddTransition.isSelected() ) {

                if ( originState != null ) {

                    if ( targetState == null ) {
                        targetState = fa.getStateAt( xEvt, yEvt );
                    } 

                    if ( targetState != null ) {

                        String input = Utils.showInputDialogEmptyString( this, 
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
                                setCurrentFileSaved( false );
                            }

                        }

                    }

                    originState = null;
                    targetState = null;
                    drawPanel.setDrawingTempTransition( false );

                }

                fa.deselectAll();

            }
            
        } else if ( evt.getButton() == MouseEvent.BUTTON3 ) {
            
            if ( btnMove.isSelected() ) {
                
                fa.deselectAll();
                selectedStates.clear();
                selectedState = fa.getStateAt( xEvt, yEvt );
                selectedTransition = fa.getTransitionAt( xEvt, yEvt );
                
                if ( selectedState != null ) {

                    statePPanel.setFa( fa );
                    statePPanel.setState( selectedState );
                    statePPanel.readProperties();
                    cardLayout.show( panelProperties, STATE_PROPERTIES_CARD );
                    
                    checkInitialState.setSelected( selectedState.isInitial() );
                    checkFinalState.setSelected( selectedState.isFinal() );
                    
                    popupMenuStateProperties.show( drawPanel, xEvt, yEvt );

                } else if ( selectedTransition != null ) {

                    transitionPPanel.setFa( fa );
                    transitionPPanel.setTransition( selectedTransition );
                    transitionPPanel.readProperties();
                    cardLayout.show( panelProperties, TRANSITION_PROPERTIES_CARD );

                    popupMenuTransitionProperties.show( drawPanel, xEvt, yEvt );

                } else {

                    faPPanel.setFa( fa );
                    faPPanel.readProperties();
                    cardLayout.show( panelProperties, MODEL_PROPERTIES_CARD );

                }
                
            }
            
        }
        
        faPPanel.setFa( fa );
        faPPanel.readProperties();
        repaintDrawPanel();
        
    }//GEN-LAST:event_drawPanelMouseReleased
    
    private void drawPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawPanelMouseDragged
        
        int xEvt = drawPanel.getZoomFacility().screenToWorld( evt.getX() );
        int yEvt = drawPanel.getZoomFacility().screenToWorld( evt.getY() );
        
        if ( canDrag ) {
            
            if ( btnSelectMultipleStates.isSelected() ) {

                Rectangle rectangle = new Rectangle( 
                        xPressed <= xEvt ? xPressed : xEvt, 
                        yPressed <= yEvt ? yPressed : yEvt, 
                        xPressed <= xEvt ? xEvt - xPressed : xPressed - xEvt, 
                        yPressed <= yEvt ? yEvt - yPressed : yPressed - yEvt );

                for ( FAState s : fa.getStates() ) {
                    if ( s.intersects( rectangle ) ) {
                        s.setMouseHover( true );
                    } else {
                        s.setMouseHover( false );
                    }
                }

                drawPanel.setSelectionRectangle( rectangle );
                repaintDrawPanel();

            } else if ( btnMove.isSelected() ) {

                int xAmount = xEvt - xPrev;
                int yAmount = yEvt - yPrev;
                xPrev += xAmount;
                yPrev += yAmount;
                    
                if ( !selectedStates.isEmpty() ) {
                    for ( FAState s : selectedStates ) {
                        s.move( xAmount, yAmount );
                    }
                    fa.updateTransitions();
                    fa.draggTransitions( evt, drawPanel.getZoomFacility() );
                } else if ( selectedState != null ) {
                    if ( btnSnapToGrid.isSelected() ) {
                        updateSnapPoint( evt );
                        selectedState.setX1Y1( xSnap, ySnap );
                    } else {
                        selectedState.move( xAmount, yAmount );
                    }
                    fa.updateTransitions();
                    if ( selectedTransition != null ) {
                        selectedTransition.cancelDragging();
                        selectedTransition = null;
                    }
                    fa.draggTransitions( evt, drawPanel.getZoomFacility() );
                } else if ( selectedTransition != null ) {
                    selectedTransition.mouseDragged( evt, drawPanel.getZoomFacility() );
                } else {
                    fa.move( xAmount, yAmount );
                }

                setCurrentFileSaved( false );

            } else if ( btnAddTransition.isSelected() ) {
                drawPanel.setTempTransitionX2( xEvt );
                drawPanel.setTempTransitionY2( yEvt );
            }

            repaintDrawPanel();
        
        }
        
    }//GEN-LAST:event_drawPanelMouseDragged

    private void drawPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawPanelMouseMoved
        
        int xEvt = drawPanel.getZoomFacility().screenToWorld( evt.getX() );
        int yEvt = drawPanel.getZoomFacility().screenToWorld( evt.getY() );
        
        if ( btnMove.isSelected() || btnAddTransition.isSelected() ) {
            fa.mouseHoverStatesAndTransitions( xEvt, yEvt );
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

    private void btnSaveAsImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAsImageActionPerformed
        
        JFileChooser jfc = new JFileChooser( new File( ApplicationPreferences.getPref( ApplicationPreferences.PREF_DEFAULT_FOLDER_PATH ) ) );
        jfc.setDialogTitle( "Save Finite Automaton as Image" );
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
                if ( Utils.showConfirmationMessageYesNo(
                        this, 
                        "The file already exists.\nDo you want to overwrite it?" )
                        == JOptionPane.NO_OPTION ) {
                    save = false;
                }
            }

            if ( save ) {
                
                ApplicationPreferences.setPref( 
                        ApplicationPreferences.PREF_DEFAULT_FOLDER_PATH, 
                        f.getParentFile().getAbsolutePath() );
                
                int r = JOptionPane.showConfirmDialog( 
                        this, 
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
                g2d.scale( drawPanel.getZoomFacility().getZoomFactor(), drawPanel.getZoomFacility().getZoomFactor() );

                if ( r != JOptionPane.YES_OPTION ) {
                    g2d.setColor( Color.WHITE );
                    g2d.fillRect( 0, 0, img.getWidth(), img.getHeight() );
                }

                fa.draw( g2d );
                g2d.dispose();

                try {
                    ImageIO.write( img, "png", f );
                } catch ( IOException exc ) {
                    Utils.showException( exc );
                }
                
            }
            
        }
        
    }//GEN-LAST:event_btnSaveAsImageActionPerformed

    private void btnTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestActionPerformed
        runSingleTest();
    }//GEN-LAST:event_btnTestActionPerformed
    
    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        
        if ( fa.canExecute() ) {
            
            txtTestString.setEditable( false );
            resetTestInGUI();
            disableGUI();
            fa.deselectAll();
            selectedStates.clear();
            simulationSteps.clear();
            currentSimulationStep = 0;
            
            boolean accepted = fa.accepts( txtTestString.getText(), simulationSteps );
            
            btnStart.setEnabled( false );
            btnStop.setEnabled( true );
            
            drawPanel.setSimulationString( txtTestString.getText() );
            drawPanel.setFASimulationSteps( simulationSteps );
            drawPanel.setCurrentSimulationStep( currentSimulationStep );
            drawPanel.setSimulationAccepted( accepted );
            drawPanel.repaint();
            
            updateSimulationButtons( currentSimulationStep );
            activateSimulationStep( currentSimulationStep );
            
            drawPanel.requestFocus();
            
        } else {
            Utils.showErrorMessage( this, "You must set an initial state!" );
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
        drawPanel.setFASimulationSteps( null );
        drawPanel.setCurrentSimulationStep( 0 );
        drawPanel.setSimulationAccepted( false );
        drawPanel.repaint();
        
    }//GEN-LAST:event_btnStopActionPerformed

    private void btnBatchTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatchTestActionPerformed
        if ( faBatchTestDialog == null ) {
            faBatchTestDialog = new FABatchTest( mainWindow, this, false );
        }
        faBatchTestDialog.setFa( fa );
        faBatchTestDialog.setVisible( true );
    }//GEN-LAST:event_btnBatchTestActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        resetTestInGUI();
        txtTestString.setText( "" );
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnGenerateEquivalentDFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateEquivalentDFAActionPerformed
        
        fa.updateType();
        
        if ( fa.getType() == FAType.EMPTY ) {
            Utils.showInformationMessage( this, 
                    "You must define a Finite Automaton First!" );
        } else if ( fa.getType() == FAType.DFA ) {
            Utils.showInformationMessage( this, 
                    "You already have a DFA!" );
        } else {
            
            try {
                
                FA dfa = new FADeterminize( fa ).getGeneratedDFA();
                FAArrangement.arrangeByLevel( dfa, 100, 100, 150, false );
                mainWindow.createFAInternalFrame( dfa, false, false, null, null );
                
            } catch ( IllegalArgumentException exc ) {
                Utils.showErrorMessage( this, exc.getMessage() );
            }
            
        }
        
    }//GEN-LAST:event_btnGenerateEquivalentDFAActionPerformed

    private void btnGenerateMinimizedDFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateMinimizedDFAActionPerformed
        
        try {
            
            fa.updateType();
            
            int r = JOptionPane.showConfirmDialog( this, 
                    "Remove inaccessible and useless states before processing?", 
                    "Remove?", JOptionPane.YES_NO_CANCEL_OPTION );
            
            if ( r != JOptionPane.CANCEL_OPTION ) {
                
                FA minDFA = new DFAMinimize( fa, r == JOptionPane.YES_OPTION ).getGeneratedDFA();

                if ( minDFA.equals( fa ) ) {
                    Utils.showInformationMessage( this, 
                            "Your DFA is already mimimum!" );
                } else {
                    FAArrangement.arrangeByLevel( minDFA, 100, 100, 150, false );
                    mainWindow.createFAInternalFrame( minDFA, false, false, null, null );
                }
                
            }
            
        } catch ( IllegalArgumentException exc ) {
            Utils.showErrorMessage( this, exc.getMessage() );
        }
        
    }//GEN-LAST:event_btnGenerateMinimizedDFAActionPerformed

    private void txtTestStringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTestStringActionPerformed
        runSingleTest();
    }//GEN-LAST:event_txtTestStringActionPerformed

    private void btnAddAllMissingTransitionsDFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAllMissingTransitionsDFAActionPerformed
        
        fa.updateType();
        
        if ( fa.getType() == FAType.DFA ) {
            
            int currentSize = fa.getStates().size();
            DFATotalTransitionFunction.processIt( fa, currentState );
            int newSize = fa.getStates().size();
            
            if ( currentSize != newSize ) {
                currentState++;
            }
            
            setCurrentFileSaved( false );
            repaintDrawPanel();
            
        } else {
            Utils.showErrorMessage( this, "To perform this you must have a DFA!" );
        }
        
    }//GEN-LAST:event_btnAddAllMissingTransitionsDFAActionPerformed

    private void popItemHorizontalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemHorizontalActionPerformed
        
        try {
            
            String value = JOptionPane.showInputDialog( this, "Distance between states:" );
            
            if ( value != null ) {
                
                int distance = Integer.parseInt( value );
                if ( distance < 0 ) {
                    throw new NumberFormatException();
                }
                
                FAArrangement.arrangeHorizontally( fa, 100, 100, distance );
                
                setCurrentFileSaved( false );
                repaintDrawPanel();
                
            }
            
        } catch ( NumberFormatException exc ) {
            Utils.showErrorMessage( this, "Distance must be greater than zero!" );
        }
        
    }//GEN-LAST:event_popItemHorizontalActionPerformed

    private void popItemVerticalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemVerticalActionPerformed
        
        try {
            
            String value = JOptionPane.showInputDialog( this, "Distance between states:" );
            
            if ( value != null ) {
                
                int distance = Integer.parseInt( value );
                if ( distance < 0 ) {
                    throw new NumberFormatException();
                }
                
                FAArrangement.arrangeVertically( fa, 100, 100, distance );
                
                setCurrentFileSaved( false );
                repaintDrawPanel();
                
            }
            
        } catch ( NumberFormatException exc ) {
            Utils.showErrorMessage( this, "Distance must be greater than zero!" );
        }
        
    }//GEN-LAST:event_popItemVerticalActionPerformed

    private void popItemRectangularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemRectangularActionPerformed
        
        int size = fa.getStates().size();
        SpinnerNumberModel spinModel = new SpinnerNumberModel( 1, 1, size == 0 ? 1 : size, 1 );
        
        JLabel lblColumns = new JLabel( "How many columns:" );
        JSpinner spinColumns = new JSpinner( spinModel );
        JLabel lblDistance = new JLabel( "Distance between states:" );
        JTextField txtDistance = new JTextField( 20 );
        
        JPanel spinPanel = new JPanel();
        spinPanel.setLayout( new FlowLayout( FlowLayout.LEFT, 0, 0 ) );
        spinPanel.add( lblColumns );
        Dimension d = new Dimension( 5, 0 );
        spinPanel.add( new Box.Filler( d, d, d ) );
        spinPanel.add( spinColumns );
        
        Component[] components = new Component[]{ spinPanel, lblDistance, txtDistance };
        
        spinColumns.addAncestorListener( new AncestorListener(){
            @Override
            public void ancestorAdded( AncestorEvent evt ) {
                evt.getComponent().requestFocusInWindow();
            }
            @Override
            public void ancestorRemoved( AncestorEvent evt ) {
            }

            @Override
            public void ancestorMoved( AncestorEvent evt ) {
            }
        });
        
        if ( JOptionPane.showOptionDialog( 
                this, 
                components, 
                "Input", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                new String[]{ "OK", "Cancel" }, 
                "OK" ) == JOptionPane.OK_OPTION ) {
            
            try {
                
                int distance = Integer.parseInt( txtDistance.getText() );
                if ( distance < 0 ) {
                    throw new NumberFormatException();
                }

                FAArrangement.arrangeRectangularly( fa, 100, 100, 
                        Integer.parseInt( spinColumns.getValue().toString() ), 
                        distance );

                setCurrentFileSaved( false );
                repaintDrawPanel();
                
            } catch ( NumberFormatException exc ) {
                Utils.showErrorMessage( this, "Distance must be greater than zero!" );
            }
            
        }
        
    }//GEN-LAST:event_popItemRectangularActionPerformed

    private void popItemCircularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemCircularActionPerformed
        
        try {
            
            String value = JOptionPane.showInputDialog( this, "Circle radius:" );
            
            if ( value != null ) {
                
                int radius = Integer.parseInt( value );
                if ( radius < 0 ) {
                    throw new NumberFormatException();
                }
                
                FAArrangement.arrangeInCircle( 
                        fa, radius + 100, radius + 100, radius );
                
                setCurrentFileSaved( false );
                repaintDrawPanel();
                
            }
            
        } catch ( NumberFormatException exc ) {
            Utils.showErrorMessage( this, "Radius must be greater than zero!" );
        }
        
    }//GEN-LAST:event_popItemCircularActionPerformed

    private void popItemByLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemByLevelActionPerformed
                
        if ( fa.getInitialState() != null ) {
        
            JLabel lblAlign = new JLabel( "Align:" );
            JRadioButton rbHorizontal = new JRadioButton( "Horizontal" );
            rbHorizontal.setSelected( true );
            ButtonGroup bg = new ButtonGroup();
            JRadioButton rbVertical = new JRadioButton( "Vertical" );
            bg.add( rbVertical );
            bg.add( rbHorizontal );

            JLabel lblDistance = new JLabel( "Distance between levels and states:" );
            JTextField txtDistance = new JTextField( 20 );

            JPanel spinPanel = new JPanel();
            spinPanel.setLayout( new FlowLayout( FlowLayout.LEFT, 0, 0 ) );
            spinPanel.add( lblAlign );
            Dimension d = new Dimension( 5, 0 );
            spinPanel.add( new Box.Filler( d, d, d ) );
            spinPanel.add( rbHorizontal );
            spinPanel.add( new Box.Filler( d, d, d ) );
            spinPanel.add( rbVertical );

            Component[] components = new Component[]{ spinPanel, lblDistance, txtDistance };

            txtDistance.addAncestorListener( new AncestorListener(){
                @Override
                public void ancestorAdded( AncestorEvent evt ) {
                    evt.getComponent().requestFocusInWindow();
                }
                @Override
                public void ancestorRemoved( AncestorEvent evt ) {
                }

                @Override
                public void ancestorMoved( AncestorEvent evt ) {
                }
            });

            if ( JOptionPane.showOptionDialog( 
                    this, 
                    components, 
                    "Input", 
                    JOptionPane.OK_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    new String[]{ "OK", "Cancel" }, 
                    "OK" ) == JOptionPane.OK_OPTION ) {

                try {

                    int distance = Integer.parseInt( txtDistance.getText() );
                    if ( distance < 0 ) {
                        throw new NumberFormatException();
                    }

                    FAArrangement.arrangeByLevel( 
                            fa, 100, 100, distance, rbVertical.isSelected() );

                    setCurrentFileSaved( false );
                    repaintDrawPanel();

                } catch ( NumberFormatException exc ) {
                    Utils.showErrorMessage( this, "Distance must be greater than zero!" );
                }

            }
        
        } else {
            Utils.showErrorMessage( this, "Your Finite Automata must have an initial state!" );
        }
        
    }//GEN-LAST:event_popItemByLevelActionPerformed

    private void popItemDiagonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemDiagonalActionPerformed
        
        try {
            
            String value = JOptionPane.showInputDialog( this, "Distance between states:" );
            
            if ( value != null ) {
                
                int distance = Integer.parseInt( value );
                if ( distance < 0 ) {
                    throw new NumberFormatException();
                }
                
                FAArrangement.arrangeDiagonally( fa, 100, 100, distance );
                
                setCurrentFileSaved( false );
                repaintDrawPanel();
                
            }
            
        } catch ( NumberFormatException exc ) {
            Utils.showErrorMessage( this, "Distance must be greater than zero!" );
        }
        
    }//GEN-LAST:event_popItemDiagonalActionPerformed

    private void btnRearrangeStatesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRearrangeStatesMouseReleased
        popupMenuReorganizeStates.show( evt.getComponent(), 0, 0 );
    }//GEN-LAST:event_btnRearrangeStatesMouseReleased

    private void btnSelectMultipleStatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectMultipleStatesActionPerformed
        resetGUIToAddStatesAndTransitions();
        drawPanel.setCursor( Cursor.getPredefinedCursor( 
                Cursor.CROSSHAIR_CURSOR ) );
    }//GEN-LAST:event_btnSelectMultipleStatesActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        
        if ( !currentFileSaved ) {
            
            int r = Utils.showConfirmationMessageYesNoCancel( this, 
                    "Save modifications before oppening?" );
            
            if ( r == JOptionPane.YES_OPTION ) {
                if ( saveFA() ) {
                    openFA();
                }
            } else if ( r == JOptionPane.NO_OPTION ) {
                openFA();
            }
            
        } else {
            openFA();
        }
        
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveFA();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAsActionPerformed
        saveFAAs( "Save Finite Automaton As..." );
    }//GEN-LAST:event_btnSaveAsActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        
        if ( ApplicationConstants.IN_DEVELOPMENT ) {
            dispose();
        } else {
        
            if ( !currentFileSaved ) {

                int r = Utils.showConfirmationMessageYesNoCancel( this, 
                        "Save modifications before close?" );

                if ( r == JOptionPane.YES_OPTION ) {
                    if ( saveFA() ) {
                        dispose();
                    }
                } else if ( r == JOptionPane.NO_OPTION ) {
                    dispose();
                }

            } else {
                dispose();
            }
        
        }
        
    }//GEN-LAST:event_formInternalFrameClosing

    private void btnCodeGenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodeGenActionPerformed
        
        String code = fa.generateCode();
        StringSelection codeSelection = new StringSelection( code );
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents( codeSelection, null );
        
        if ( JOptionPane.showConfirmDialog( this,
                """
                The generated code has been copied to the clipboard!
                Do you want to see it?""",
                "Generated Code", JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION ) {
            
            JTextArea textArea = new JTextArea( 20, 60 );
            textArea.setFont( DrawingConstants.DEFAULT_FONT );
            textArea.setEditable( false );
            textArea.setText( code );
            textArea.setCaretPosition( 0 );
            
            JOptionPane.showMessageDialog( this, new JScrollPane( textArea ), 
                    "Generated Code", JOptionPane.INFORMATION_MESSAGE );
        
        }
        
    }//GEN-LAST:event_btnCodeGenActionPerformed

    private void popupMenuStatePropertiesPopupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_popupMenuStatePropertiesPopupMenuCanceled
        faPPanel.setFa( fa );
        faPPanel.readProperties();
        cardLayout.show( panelProperties, MODEL_PROPERTIES_CARD );
    }//GEN-LAST:event_popupMenuStatePropertiesPopupMenuCanceled

    private void popupMenuTransitionPropertiesPopupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_popupMenuTransitionPropertiesPopupMenuCanceled
        faPPanel.setFa( fa );
        faPPanel.readProperties();
        cardLayout.show( panelProperties, MODEL_PROPERTIES_CARD );
    }//GEN-LAST:event_popupMenuTransitionPropertiesPopupMenuCanceled

    private void checkInitialStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkInitialStateActionPerformed
        
        if ( selectedState != null ) {
            
            if ( checkInitialState.isSelected() ) {
                selectedState.setInitial( true );
                fa.setInitialState( selectedState );
            } else {
                if ( selectedState.isInitial() ) {
                    fa.setInitialState( null );
                }
                selectedState.setInitial( false );
            }

            statePPanel.setFa( fa );
            statePPanel.setState( selectedState );
            statePPanel.readProperties();
            cardLayout.show( panelProperties, STATE_PROPERTIES_CARD );

            setCurrentFileSaved( false );
            repaintDrawPanel();
        
        }
        
    }//GEN-LAST:event_checkInitialStateActionPerformed

    private void checkFinalStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkFinalStateActionPerformed
        
        if ( selectedState != null ) {
            
            selectedState.setFinal( checkFinalState.isSelected() );

            statePPanel.setFa( fa );
            statePPanel.setState( selectedState );
            statePPanel.readProperties();
            cardLayout.show( panelProperties, STATE_PROPERTIES_CARD );

            setCurrentFileSaved( false );
            repaintDrawPanel();
        
        }
        
    }//GEN-LAST:event_checkFinalStateActionPerformed

    private void popItemStateColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemStateColorActionPerformed
        
        if ( selectedState != null ) {
            
            Color c = JColorChooser.showDialog( this, "State Color", 
                    selectedState.getStrokeColor() );

            if ( c != null ) {
                
                selectedState.setStrokeColor( c );
                
                statePPanel.setFa( fa );
                statePPanel.setState( selectedState );
                statePPanel.readProperties();
                cardLayout.show( panelProperties, STATE_PROPERTIES_CARD );
        
                setCurrentFileSaved( false );
                repaintDrawPanel();
                
            }
        
        }
        
    }//GEN-LAST:event_popItemStateColorActionPerformed

    private void popItemRemoveStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemRemoveStateActionPerformed
        
        if ( selectedState != null ) {
        
            if ( Utils.showConfirmationMessageYesNo( 
                    this, 
                    "Do you really want to remove the selected state?" ) 
                    == JOptionPane.YES_OPTION ) {
                fa.removeState( selectedState );
                setCurrentFileSaved( false );
                repaintDrawPanel();
                updateAfterRemotion();
                selectedState = null;
                if ( fa.getStates().isEmpty() ) {
                    currentState = 0;
                }
            }
        
        } else {
            Utils.showErrorMessage( this, "There's no selected state!" );
        }
        
    }//GEN-LAST:event_popItemRemoveStateActionPerformed

    private void popItemTransitionSymbolsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemTransitionSymbolsActionPerformed
        
        if ( selectedTransition != null ) {
            
            String s = "";
            for ( Character c : selectedTransition.getSymbols() ) {
                s += c.toString() + " ";
            }
            s = s.trim();
            
            String input = Utils.showInputDialogEmptyString( this, 
                    "New transition symbol(s)", 
                    "Edit Transition Symbol(s)", s );
            List<Character> symbols = new ArrayList<>();

            if ( input != null ) {

                input = input.trim().replace( " ", "" );

                if ( !input.isEmpty() ) {
                    for ( char c : input.toCharArray() ) {
                        symbols.add( c );
                    }
                    selectedTransition.setSymbols( symbols );
                    fa.markAllCachesAsObsolete();
                    transitionPPanel.setFa( fa );
                    transitionPPanel.setTransition( selectedTransition );
                    transitionPPanel.readProperties();
                    cardLayout.show( panelProperties, TRANSITION_PROPERTIES_CARD );
                    setCurrentFileSaved( false );
                    repaintDrawPanel();
                    updateAfterUpdate();
                }

            }
            
        } else {
            Utils.showErrorMessage( this, "There's no selected transition!" );
        }
        
    }//GEN-LAST:event_popItemTransitionSymbolsActionPerformed

    private void popItemTransitionColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemTransitionColorActionPerformed
        
        if ( selectedTransition != null ) {
            
            Color c = JColorChooser.showDialog( this, "Transition Color", 
                    selectedTransition.getStrokeColor() );

            if ( c != null ) {
                
                selectedTransition.setStrokeColor( c );
                
                transitionPPanel.setFa( fa );
                transitionPPanel.setTransition( selectedTransition );
                transitionPPanel.readProperties();
                cardLayout.show( panelProperties, TRANSITION_PROPERTIES_CARD );
        
                setCurrentFileSaved( false );
                repaintDrawPanel();
                
            }
        
        }
        
    }//GEN-LAST:event_popItemTransitionColorActionPerformed

    private void popItemResetTransitionTransformationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemResetTransitionTransformationsActionPerformed
        
        if ( selectedTransition != null ) {
            
            selectedTransition.resetTransformations();
            
            transitionPPanel.setFa( fa );
            transitionPPanel.setTransition( selectedTransition );
            transitionPPanel.readProperties();
            cardLayout.show( panelProperties, TRANSITION_PROPERTIES_CARD );
                    
            setCurrentFileSaved( false );
            repaintDrawPanel();
            
        } else {
            Utils.showErrorMessage( this, "There's no selected transition!" );
        }
        
    }//GEN-LAST:event_popItemResetTransitionTransformationsActionPerformed

    private void popItemRemoveTransitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemRemoveTransitionActionPerformed
        
        if ( selectedTransition != null ) {
        
            if ( Utils.showConfirmationMessageYesNo( 
                    this, 
                    "Do you really want to remove the selected transition?" )
                    == JOptionPane.YES_OPTION ) {
                fa.removeTransition( selectedTransition );
                setCurrentFileSaved( false );
                repaintDrawPanel();
                updateAfterRemotion();
                selectedTransition = null;
            }
        
        } else {
            Utils.showErrorMessage( this, "There's no selected transition!" );
        }
        
    }//GEN-LAST:event_popItemRemoveTransitionActionPerformed

    private void popItemStateCustomLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemStateCustomLabelActionPerformed
        
        if ( selectedState != null ) {
            
            String initialValue = selectedState.getCustomLabel();
            if ( initialValue == null ) {
                initialValue = "";
            }
            
            String input = JOptionPane.showInputDialog( this, "Custom Label:", initialValue );
            
            if ( input != null ) {
                
                selectedState.setCustomLabel( input );

                statePPanel.setFa( fa );
                statePPanel.setState( selectedState );
                statePPanel.readProperties();
                cardLayout.show( panelProperties, STATE_PROPERTIES_CARD );

                setCurrentFileSaved( false );
                repaintDrawPanel();
                
            }
        
        }
        
    }//GEN-LAST:event_popItemStateCustomLabelActionPerformed

    private void btnCloneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloneActionPerformed
        
        try {
            FA clone = (FA) fa.clone();
            mainWindow.createFAInternalFrame( clone, false, false, null, null );
        } catch ( CloneNotSupportedException exc ) {
            // should never be reached
            exc.printStackTrace();
        }
        
    }//GEN-LAST:event_btnCloneActionPerformed

    private void popItemUnionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemUnionActionPerformed
        
        try {
            FA fa2 = loadFA( "Choose the second Finite Automaton to perform Union" );
            if ( fa2 != null ) {
                FA faUnion = new FAUnion( fa, fa2 ).getGeneratedFA();
                FAArrangement.arrangeByLevel( faUnion, 100, 100, 150, false );
                mainWindow.createFAInternalFrame( faUnion, false, false, null, null );
            }
        } catch ( IllegalArgumentException exc ) {
            Utils.showErrorMessage( this, exc.getMessage() );
        }
        
    }//GEN-LAST:event_popItemUnionActionPerformed

    private void popItemConcatenationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemConcatenationActionPerformed
        
        try {
            FA fa2 = loadFA( "Choose the second Finite Automaton to perform Concatenation" );
            if ( fa2 != null ) {
                FA faConcatenation = new FAConcatenation( fa, fa2 ).getGeneratedFA();
                FAArrangement.arrangeByLevel( faConcatenation, 100, 100, 150, false );
                mainWindow.createFAInternalFrame( faConcatenation, false, false, null, null );
            }
        } catch ( IllegalArgumentException exc ) {
            Utils.showErrorMessage( this, exc.getMessage() );
        }
        
    }//GEN-LAST:event_popItemConcatenationActionPerformed

    private void popItemKleeneStarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemKleeneStarActionPerformed
        
        try {
            FA faStar = new FAKleeneStar( fa ).getGeneratedFA();
            FAArrangement.arrangeByLevel( faStar, 100, 100, 150, false );
            mainWindow.createFAInternalFrame( faStar, false, false, null, null );
        } catch ( IllegalArgumentException exc ) {
            Utils.showErrorMessage( this, exc.getMessage() );
        }
        
    }//GEN-LAST:event_popItemKleeneStarActionPerformed

    private void popItemComplementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemComplementActionPerformed
        
        try {
            FA complementedDFA = new DFAComplement( fa ).getGeneratedDFA();
            mainWindow.createFAInternalFrame( complementedDFA, false, false, null, null );
        } catch ( IllegalArgumentException exc ) {
            Utils.showErrorMessage( this, exc.getMessage() );
        }
        
    }//GEN-LAST:event_popItemComplementActionPerformed

    private void popItemIntersectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popItemIntersectionActionPerformed
        
        try {
            FA fa2 = loadFA( "Choose the second Finite Automaton to perform Intersection" );
            if ( fa2 != null ) {
                FA faIntersection = new FAIntersection( fa, fa2 ).getGeneratedFA();
                FAArrangement.arrangeByLevel( faIntersection, 100, 100, 150, false );
                mainWindow.createFAInternalFrame( faIntersection, false, false, null, null );
            }
        } catch ( IllegalArgumentException exc ) {
            Utils.showErrorMessage( this, exc.getMessage() );
        }
        
    }//GEN-LAST:event_popItemIntersectionActionPerformed

    private void btnRegularOperationsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegularOperationsMouseReleased
        popupMenuRegularOperations.show( evt.getComponent(), 0, 0 );
    }//GEN-LAST:event_btnRegularOperationsMouseReleased

    private void btnRemoveInaccessibleAndUselessStatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveInaccessibleAndUselessStatesActionPerformed
        
        try {
            FA faCleaned = new FARemoveInaccessibleAndUselessStates( fa, true ).getGeneratedFA();
            mainWindow.createFAInternalFrame( faCleaned, false, false, null, null );
        } catch ( IllegalArgumentException exc ) {
            Utils.showErrorMessage( this, exc.getMessage() );
        }
        
    }//GEN-LAST:event_btnRemoveInaccessibleAndUselessStatesActionPerformed

    private void txtTestStringKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTestStringKeyReleased
        resetTestInGUI();
    }//GEN-LAST:event_txtTestStringKeyReleased

    public void repaintDrawPanel() {
        drawPanel.repaint();
        drawPanel.setPreferredSize( 
            new Dimension( 
                drawPanel.getZoomFacility().worldToScreen( fa.getWidth() ), 
                drawPanel.getZoomFacility().worldToScreen( fa.getHeight() )
            )
        );
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
        
        drawPanel.getZoomFacility().zoomIn();
        
        btnZoomOut.setEnabled( true );
        if ( !drawPanel.getZoomFacility().canZoomIn() ) {
            btnZoomIn.setEnabled( false );
        }
        
        setCurrentFileSaved( false );
        repaintDrawPanel();
        
    }
    
    private void zoomOut() {
        
        drawPanel.getZoomFacility().zoomOut();
        
        btnZoomIn.setEnabled( true );
        if ( !drawPanel.getZoomFacility().canZoomOut() ) {
            btnZoomOut.setEnabled( false );
        }
        
        setCurrentFileSaved( false );
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
            Utils.showErrorMessage( this, "You must set an initial state!" );
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
        txtTestString.setCaretColor( txtTestString.getForeground() );
        lblTestResult.setText( "ACCEPTED" );
    }
    
    private void setTestToRejectedInGUI() {
        lblTestResult.setForeground(
                DrawingConstants.REJECTED_LABEL_FOREGROUND_COLOR );
        txtTestString.setForeground(
                DrawingConstants.REJECTED_TEXTFIELD_FOREGROUND_COLOR );
        txtTestString.setBackground(
                DrawingConstants.REJECTED_TEXTFIELD_BACKGROUND_COLOR );
        txtTestString.setCaretColor( txtTestString.getForeground() );
        lblTestResult.setText( "REJECTED" );
    }
    
    private void resetTestInGUI() {
        txtTestString.setBackground( txtTestStringDefaultBC );
        txtTestString.setForeground( txtTestStringDefaultFC );
        txtTestString.setCaretColor( txtTestStringDefaultCaretColor );
        lblTestResult.setForeground( lblTestResultDefaultFC );
        lblTestResult.setText( "TEST RESULT" );
    }
    
    private void btnMoveAction() {
        
        if ( selectedStates.isEmpty() ) {
            fa.deselectAll();
        }
        
        cardLayout.show( panelProperties, MODEL_PROPERTIES_CARD );
        repaintDrawPanel();
        drawPanel.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
        
    }
    
    private void disableGUI() {
        
        btnNew.setEnabled( false );
        btnOpen.setEnabled( false );
        btnSave.setEnabled( false );
        btnSaveAs.setEnabled( false );
        btnSaveAsImage.setEnabled( false );
        btnCodeGen.setEnabled( false );
        btnClone.setEnabled( false );
        
        if ( btnAddState.isSelected() || btnAddTransition.isSelected() ) {
            btnMove.setSelected( true );
        }
        btnMoveAction();
        btnAddState.setEnabled( false );
        btnAddTransition.setEnabled( false );
        
        btnAddAllMissingTransitionsDFA.setEnabled( false );
        btnRemoveInaccessibleAndUselessStates.setEnabled( false );
        
        btnGenerateEquivalentDFA.setEnabled( false );
        btnGenerateMinimizedDFA.setEnabled( false );
        btnRegularOperations.setEnabled( false );
        
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
        btnSaveAsImage.setEnabled( true );
        btnCodeGen.setEnabled( true );
        btnClone.setEnabled( true );
        
        btnAddState.setEnabled( true );
        btnAddTransition.setEnabled( true );
        
        btnAddAllMissingTransitionsDFA.setEnabled( true );
        btnRemoveInaccessibleAndUselessStates.setEnabled( true );
        
        btnGenerateEquivalentDFA.setEnabled( true );
        btnGenerateMinimizedDFA.setEnabled( true );
        btnRegularOperations.setEnabled( true );
        
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
        
        int xEvt = drawPanel.getZoomFacility().screenToWorld( evt.getX() );
        int yEvt = drawPanel.getZoomFacility().screenToWorld( evt.getY() );
        
        xSnap = ( xEvt + DrawingConstants.STATE_RADIUS / 2 ) / 
                DrawingConstants.STATE_RADIUS * 
                DrawingConstants.STATE_RADIUS;
        ySnap = ( yEvt + DrawingConstants.STATE_RADIUS / 2 ) / 
                DrawingConstants.STATE_RADIUS * 
                DrawingConstants.STATE_RADIUS;
        
    }

    public void setCurrentState( int currentState ) {
        this.currentState = currentState;
    }

    public void setCurrentFile( File currentFile ) {
        this.currentFile = currentFile;
        updateTitle();
    }
    
    public void setCurrentFileSaved( boolean currentFileSaved ) {
        this.currentFileSaved = currentFileSaved;
        updateTitle();
    }
    
    private void updateTitle() {
        
        if ( currentFile == null ) {
            setTitle( String.format( "*%s - %s", "new", baseTitle ) );
        } else {
            
            if ( currentFileSaved ) {
                setTitle( String.format( "%s - %s", currentFile.getName(), baseTitle ) );
            } else {
                setTitle( String.format( "*%s - %s", currentFile.getName(), baseTitle ) );
            }
            
        }
        
    }
    
    private void serializeFAToFile( File file ) {
        
        try ( FileOutputStream fos = new FileOutputStream( file );
                ObjectOutputStream oos = new ObjectOutputStream( fos ) ) {

            oos.writeObject( fa );
            oos.flush();
            currentFile = file;
            setCurrentFileSaved( true );

        } catch ( IOException exc ) {
            Utils.showException( exc );
        }
        
    }
    
    private boolean saveFA() {
        
        if ( currentFile == null ) {
            return saveFAAs( "Save Finite Automaton" );
        } else {
            serializeFAToFile( currentFile );
            return true;
        }
        
    }
    
    private boolean saveFAAs( String saveDialogTitle ) {
        
        JFileChooser jfc = new JFileChooser( new File( ApplicationPreferences.getPref( ApplicationPreferences.PREF_DEFAULT_FOLDER_PATH ) ) );
        jfc.setDialogTitle( saveDialogTitle );
        jfc.setMultiSelectionEnabled( false );
        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        jfc.removeChoosableFileFilter( jfc.getFileFilter() );
        jfc.setFileFilter( new FileNameExtensionFilter( "YAAS Finite Automaton", "yfa" ) );

        if ( jfc.showSaveDialog( mainWindow ) == JFileChooser.APPROVE_OPTION ) {

            File f = jfc.getSelectedFile();

            if ( !f.getName().endsWith( ".yfa" ) ) {
                f = new File( f.getAbsolutePath() + ".yfa" );
            }

            boolean save = true;

            if ( f.exists() ) {
                if ( Utils.showConfirmationMessageYesNo(
                        this, 
                        "The file already exists.\nDo you want to overwrite it?"  )
                        == JOptionPane.NO_OPTION ) {
                    save = false;
                }
            }

            if ( save ) {

                ApplicationPreferences.setPref( 
                        ApplicationPreferences.PREF_DEFAULT_FOLDER_PATH, 
                        f.getParentFile().getAbsolutePath() );

                serializeFAToFile( f );
                return true;

            }

        }
        
        return false;
            
    }
    
    private void openFA() {
        
        JFileChooser jfc = new JFileChooser( new File( ApplicationPreferences.getPref( ApplicationPreferences.PREF_DEFAULT_FOLDER_PATH ) ) );
        jfc.setDialogTitle( "Open Finite Automaton" );
        jfc.setMultiSelectionEnabled( false );
        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        jfc.removeChoosableFileFilter( jfc.getFileFilter() );
        jfc.setFileFilter( new FileNameExtensionFilter( "YAAS Finite Automaton", "yfa" ) );
        
        if ( jfc.showOpenDialog( mainWindow ) == JFileChooser.APPROVE_OPTION ) {

            File f = jfc.getSelectedFile();

            if ( f.exists() ) {
                
                ApplicationPreferences.setPref( 
                        ApplicationPreferences.PREF_DEFAULT_FOLDER_PATH, 
                        f.getParentFile().getAbsolutePath() );
                
                try ( FileInputStream fis = new FileInputStream( f );
                      ObjectInputStream ois = new ObjectInputStream( fis ) ) {
                    
                    fa = (FA) ois.readObject();
                    fa.deactivateAllStatesInSimulation();
                    fa.deselectAll();
                    fa.setTransitionsControlPointsVisible( false );
                    setCurrentState( fa.getNewStateId() + 1 );
                    drawPanel.setFa( fa );
                    
                    faPPanel.setFa( fa );
                    faPPanel.readProperties();
                    cardLayout.show( panelProperties, MODEL_PROPERTIES_CARD );
        
                    currentFile = f;
                    setCurrentFileSaved( true );
                    
                    repaintDrawPanel();
                    
                } catch ( IOException | ClassNotFoundException exc ) {
                    Utils.showException( exc );
                }
                
            }
            
        }
        
    }
    
    private FA loadFA( String title ) {
        
        JFileChooser jfc = new JFileChooser( new File( ApplicationPreferences.getPref( ApplicationPreferences.PREF_DEFAULT_FOLDER_PATH ) ) );
        jfc.setDialogTitle( title );
        jfc.setMultiSelectionEnabled( false );
        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        jfc.removeChoosableFileFilter( jfc.getFileFilter() );
        jfc.setFileFilter( new FileNameExtensionFilter( "YAAS Finite Automaton", "yfa" ) );
        
        if ( jfc.showSaveDialog( mainWindow ) == JFileChooser.APPROVE_OPTION ) {

            File f = jfc.getSelectedFile();

            if ( f.exists() ) {
                
                ApplicationPreferences.setPref( 
                        ApplicationPreferences.PREF_DEFAULT_FOLDER_PATH, 
                        f.getParentFile().getAbsolutePath() );
                
                try ( FileInputStream fis = new FileInputStream( f );
                      ObjectInputStream ois = new ObjectInputStream( fis ) ) {
                    
                    FA fa = (FA) ois.readObject();
                    fa = (FA) fa.clone();
                    fa.deactivateAllStatesInSimulation();
                    fa.deselectAll();
                    fa.setTransitionsControlPointsVisible( false );
                    
                    return fa;
                    
                } catch ( IOException | ClassNotFoundException | CloneNotSupportedException exc ) {
                    Utils.showException( exc );
                }
                
            }
            
        }
        
        return null;
        
    }
    
    private void registerActions() {
        
        InputMap im = getRootPane().getInputMap( 
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT );
        ActionMap am = getRootPane().getActionMap();
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK ), "createNewModel" );
        am.put( "createNewModel", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnNew.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK ), "openModel" );
        am.put( "openModel", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnOpen.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK ), "saveModel" );
        am.put( "saveModel", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnSave.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK ), "saveModelAs" );
        am.put( "saveModelAs", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnSaveAs.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK ), "saveModelAsImage" );
        am.put( "saveModelAsImage", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnSaveAsImage.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_U, KeyEvent.SHIFT_DOWN_MASK ), "multipleSelection" );
        am.put( "multipleSelection", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnSelectMultipleStates.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_M, KeyEvent.SHIFT_DOWN_MASK ), "move" );
        am.put( "move", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnMove.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_S, KeyEvent.SHIFT_DOWN_MASK ), "addState" );
        am.put( "addState", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnAddState.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_T, KeyEvent.SHIFT_DOWN_MASK ), "addTransition" );
        am.put( "addTransition", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnAddTransition.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_D, KeyEvent.ALT_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK ), "toDFA" );
        am.put( "toDFA", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnGenerateMinimizedDFA.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_M, KeyEvent.ALT_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK ), "minimizeDFA" );
        am.put( "minimizeDFA", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnGenerateMinimizedDFA.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_T, KeyEvent.ALT_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK ), "addAllTransitions" );
        am.put( "addAllTransitions", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnAddAllMissingTransitionsDFA.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK ), "showCP" );
        am.put( "showCP", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnShowTransitionControls.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK ), "showGrid" );
        am.put( "showGrid", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnShowGrid.doClick();;
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK ), "snapToGrid" );
        am.put( "snapToGrid", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnSnapToGrid.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK + KeyEvent.ALT_DOWN_MASK ), "runTest" );
        am.put( "runTest", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnTest.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK + KeyEvent.ALT_DOWN_MASK ), "openBatch" );
        am.put( "openBatch", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnBatchTest.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK + KeyEvent.ALT_DOWN_MASK ), "runSimulation" );
        am.put( "runSimulation", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnStart.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_LEFT, KeyEvent.CTRL_DOWN_MASK + KeyEvent.ALT_DOWN_MASK ), "firstStep" );
        am.put( "firstStep", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnFirstStep.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_LEFT, KeyEvent.CTRL_DOWN_MASK ), "previousStep" );
        am.put( "previousStep", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnPreviousStep.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_RIGHT, KeyEvent.CTRL_DOWN_MASK ), "nextStep" );
        am.put( "nextStep", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnNextStep.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_RIGHT, KeyEvent.CTRL_DOWN_MASK + KeyEvent.ALT_DOWN_MASK ), "lastStep" );
        am.put( "lastStep", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnLastStep.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0 ), "stop" );
        am.put( "stop", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btnStop.doClick();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_PLUS, KeyEvent.CTRL_DOWN_MASK ), "zoomIn" );
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_EQUALS, KeyEvent.CTRL_DOWN_MASK ), "zoomIn" );
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_ADD, KeyEvent.CTRL_DOWN_MASK ), "zoomIn" );
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_UP, KeyEvent.CTRL_DOWN_MASK ), "zoomIn" );
        am.put( "zoomIn", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                zoomIn();
            }
        });
        
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_MINUS, KeyEvent.CTRL_DOWN_MASK ), "zoomOut" );
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_SUBTRACT, KeyEvent.CTRL_DOWN_MASK ), "zoomOut" );
        im.put( KeyStroke.getKeyStroke( KeyEvent.VK_DOWN, KeyEvent.CTRL_DOWN_MASK ), "zoomOut" );
        am.put( "zoomOut", new AbstractAction() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                zoomOut();
            }
        });
        
    }
    
    private void resetGUIToAddStatesAndTransitions() {
        selectedStates.clear();
        fa.deselectAll();
        cardLayout.show( panelProperties, MODEL_PROPERTIES_CARD );
        repaintDrawPanel();
        drawPanel.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
    }
    
    public void updateUIFlatLaf() {
        SwingUtilities.updateComponentTreeUI( this );
        SwingUtilities.updateComponentTreeUI( popupMenuRegularOperations );
        SwingUtilities.updateComponentTreeUI( popupMenuReorganizeStates );
        SwingUtilities.updateComponentTreeUI( popupMenuStateProperties );
        SwingUtilities.updateComponentTreeUI( popupMenuTransitionProperties );
        saveDefaultColors();
    }
    
    private void saveDefaultColors() {
        txtTestStringDefaultBC = UIManager.getColor( "TextField.background" );
        txtTestStringDefaultFC = UIManager.getColor( "TextField.foreground" );
        txtTestStringDefaultCaretColor = UIManager.getColor( "TextField.caretForeground" );
        lblTestResultDefaultFC = UIManager.getColor( "Label.foreground" );
    }
    
    public void setInputExample( String inputExample ) {
        if ( inputExample != null ) {
            txtTestString.setText( inputExample );
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAllMissingTransitionsDFA;
    private javax.swing.JToggleButton btnAddState;
    private javax.swing.JToggleButton btnAddTransition;
    private javax.swing.JButton btnBatchTest;
    private javax.swing.JButton btnClone;
    private javax.swing.JButton btnCodeGen;
    private javax.swing.JButton btnFirstStep;
    private javax.swing.JButton btnGenerateEquivalentDFA;
    private javax.swing.JButton btnGenerateMinimizedDFA;
    private javax.swing.ButtonGroup btnGroup;
    private javax.swing.JButton btnLastStep;
    private javax.swing.JToggleButton btnMove;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNextStep;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnPreviousStep;
    private javax.swing.JButton btnRearrangeStates;
    private javax.swing.JButton btnRegularOperations;
    private javax.swing.JButton btnRemoveInaccessibleAndUselessStates;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSaveAs;
    private javax.swing.JButton btnSaveAsImage;
    private javax.swing.JToggleButton btnSelectMultipleStates;
    private javax.swing.JToggleButton btnShowGrid;
    private javax.swing.JToggleButton btnShowTransitionControls;
    private javax.swing.JToggleButton btnSnapToGrid;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JButton btnTest;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    private javax.swing.JCheckBoxMenuItem checkFinalState;
    private javax.swing.JCheckBoxMenuItem checkInitialState;
    private br.com.davidbuzatto.yaas.gui.DrawPanel drawPanel;
    private javax.swing.Box.Filler hFiller;
    private javax.swing.JLabel lblTestResult;
    private javax.swing.JLabel lblTestString;
    private javax.swing.JPanel panelModel;
    private javax.swing.JPanel panelProperties;
    private javax.swing.JPanel panelTestsAndSimulation;
    private javax.swing.JMenuItem popItemByLevel;
    private javax.swing.JMenuItem popItemCircular;
    private javax.swing.JMenuItem popItemComplement;
    private javax.swing.JMenuItem popItemConcatenation;
    private javax.swing.JMenuItem popItemDiagonal;
    private javax.swing.JMenuItem popItemHorizontal;
    private javax.swing.JMenuItem popItemIntersection;
    private javax.swing.JMenuItem popItemKleeneStar;
    private javax.swing.JMenuItem popItemRectangular;
    private javax.swing.JMenuItem popItemRemoveState;
    private javax.swing.JMenuItem popItemRemoveTransition;
    private javax.swing.JMenuItem popItemResetTransitionTransformations;
    private javax.swing.JMenuItem popItemStateColor;
    private javax.swing.JMenuItem popItemStateCustomLabel;
    private javax.swing.JMenuItem popItemTransitionColor;
    private javax.swing.JMenuItem popItemTransitionSymbols;
    private javax.swing.JMenuItem popItemUnion;
    private javax.swing.JMenuItem popItemVertical;
    private javax.swing.JPopupMenu popupMenuRegularOperations;
    private javax.swing.JPopupMenu popupMenuReorganizeStates;
    private javax.swing.JPopupMenu popupMenuStateProperties;
    private javax.swing.JPopupMenu popupMenuTransitionProperties;
    private javax.swing.JScrollPane scrollPaneModel;
    private javax.swing.JToolBar.Separator sep01;
    private javax.swing.JToolBar.Separator sep02;
    private javax.swing.JToolBar.Separator sep03;
    private javax.swing.JToolBar.Separator sep04;
    private javax.swing.JToolBar.Separator sep05;
    private javax.swing.JToolBar.Separator sep06;
    private javax.swing.JToolBar.Separator sepTS01;
    private javax.swing.JToolBar.Separator sepTS02;
    private javax.swing.JPopupMenu.Separator spPopupState;
    private javax.swing.JPopupMenu.Separator spPopupState01;
    private javax.swing.JPopupMenu.Separator spPopupTransition01;
    private javax.swing.JPopupMenu.Separator spPopupTransition02;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JToolBar toolBarTestsAndSimulation;
    private javax.swing.JTextField txtTestString;
    // End of variables declaration//GEN-END:variables
}
