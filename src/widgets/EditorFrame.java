package widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import figures.Drawing;
import figures.creationListeners.AbstractCreationListener;
import figures.creationListeners.MoveShapeListener;
import figures.enums.FigureType;
import figures.enums.LineType;
import figures.enums.PaintToType;
import filters.EdgeColorFilter;
import filters.FillColorFilter;
import filters.LineFilter;
import filters.ShapeFilter;
import utils.IconFactory;
import utils.PaintFactory;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.SwingConstants;
import java.awt.Panel;
import javax.swing.BoxLayout;

/**
 * Classe de la fenêtre principale de l'éditeur de figures
 * @author davidroussel
 */
public class EditorFrame extends JFrame
{
	/**
	 * Le nom de l'éditeur
	 */
	protected static final String EditorName = "Figure Editor v3.0";
	
	/**
	 * Le modèle de dessin sous-jacent;
	 */
	protected Drawing drawingModel;

	/**
	 * La zone de dessin dans laquelle seront dessinées les figures.
	 * On a besoin d'une référence à la zone de dessin (contrairement aux
	 * autres widgets) car il faut lui affecter un xxxCreationListener en
	 * fonction de la figure choisie dans la liste des figures possibles.
	 */
	protected DrawingPanel drawingPanel;

	/**
	 * Le creationListener à mettre en place dans le drawingPanel en fonction
	 * du type de figure choisie;
	 */
	protected AbstractCreationListener creationListener;

	/**
	 * Le label dans la barre d'état en bas dans lequel on affiche les
	 * conseils utilisateur pour créer une figure
	 */
	protected JLabel infoLabel;

	/**
	 * L'index de l'élément sélectionné par défaut pour le type de figure
	 */
	private final static int defaultFigureTypeIndex = 0;

	/**
	 * Les noms des couleurs de remplissage à utiliser pour remplir
	 * la [labeled]combobox des couleurs de remplissage
	 */
	protected final static String[] fillColorNames = {
		"Black",
		"White",
		"Red",
		"Orange",
		"Yellow",
		"Green",
		"Cyan",
		"Blue",
		"Magenta",
		"Others",
		"None"
	};

	/**
	 * Les couleurs de remplissage à utiliser en fonction de l'élément
	 * sélectionné dans la [labeled]combobox des couleurs de remplissage
	 */
	protected final static Paint[] fillPaints = {
		Color.black,
		Color.white,
		Color.red,
		Color.orange,
		Color.yellow,
		Color.green,
		Color.cyan,
		Color.blue,
		Color.magenta,
		null, // Color selected by a JColorChooser
		null // No Color
	};

	/**
	 * L'index de l'élément sélectionné par défaut dans les couleurs de
	 * remplissage
	 */
	private final static int defaultFillColorIndex = 1; // white

	/**
	 * L'index de la couleur de remplissage à choisir avec un
	 * {@link JColorChooser} fournit par la {@link PaintFactory}
	 */
	private final static int specialFillColorIndex = 9;

	/**
	 * Les noms des couleurs de trait à utiliser pour remplir
	 * la [labeled]combobox des couleurs de trait
	 */
	protected final static String[] edgeColorNames = {
		"Magenta",
		"Red",
		"Orange",
		"Yellow",
		"Green",
		"Cyan",
		"Blue",
		"Black",
		"Others"
	};

	/**
	 * Les couleurs de trait à utiliser en fonction de l'élément
	 * sélectionné dans la [labeled]combobox des couleurs de trait
	 */
	protected final static Paint[] edgePaints = {
		Color.magenta,
		Color.red,
		Color.orange,
		Color.yellow,
		Color.green,
		Color.cyan,
		Color.blue,
		Color.black,
		null // Color selected by a JColorChooser
	};

	/**
	 * L'index de l'élément sélectionné par défaut dans les couleurs de
	 * trait
	 */
	private final static int defaultEdgeColorIndex = 7; // black;

	/**
	 * L'index de la couleur de remplissage à choisir avec un
	 * {@link JColorChooser} fournit par la {@link PaintFactory}
	 */
	private final static int specialEdgeColorIndex = 8;

	/**
	 * L'index de l'élément sélectionné par défaut dans les types de
	 * trait
	 */
	private final static int defaultEdgeTypeIndex = 1; // solid

	/**
	 * La largeur de trait par défaut
	 */
	private final static int defaultEdgeWidth = 4;

	/**
	 * Largeur de trait minimum
	 */
	private final static int minEdgeWidth = 1;

	/**
	 * Largeur de trait maximum
	 */
	private final static int maxEdgeWidth = 30;

	/**
	 * l'incrément entre deux largeurs de trait
	 */
	private final static int stepEdgeWidth = 1;

	/**
	 * Action déclenchée lorsque l'on clique sur le bouton quit ou sur l'item
	 * de menu quit
	 */
	private final Action quitAction = new QuitAction();

	/**
	 * Action déclenchée lorsque l'on clique sur le bouton undo ou sur l'item
	 * de menu undo
	 */
	private final Action undoAction = new UndoAction();

	/**
	 * Action déclenchée lorsque l'on clique sur le bouton clear ou sur l'item
	 * de menu clear
	 */
	private final Action clearAction = new ClearAction();
	
	/**
	 * Action déclenchée lorsque l'on clique sur le bouton move
	 */
	private final Action moveAction = new MoveAction();

	/**
	 * Action déclenchée lorsque l'on clique sur le bouton about ou sur l'item
	 * de menu about
	 */
	private final Action aboutAction = new AboutAction();
	
	/**
	 * Action déclenchée lorsque l'on clique sur l'item de menu de filtrage
	 * des cercles
	 */
	private final Action circleFilterAction = new ShapeFilterAction(FigureType.CIRCLE);

	/**
	 * Action déclenchée lorsque l'on clique sur l'item de menu de filtrage
	 * des ellipse
	 */
	private final Action ellipseFilterAction = new ShapeFilterAction(FigureType.ELLIPSE);

	/**
	 * Action déclenchée lorsque l'on clique sur l'item de menu de filtrage
	 * des rectangles
	 */
	private final Action rectangleFilterAction = new ShapeFilterAction(FigureType.RECTANGLE);

	/**
	 * Action déclenchée lorsque l'on clique sur l'item de menu de filtrage
	 * des rectangles arrondis
	 */
	private final Action rRectangleFilterAction = new ShapeFilterAction(FigureType.ROUNDED_RECTANGLE);
	
	/**
	 * Action déclenchée lorsque l'on clique sur l'item de menu de filtrage
	 * des rectangles arrondis
	 */
	private final Action RegularPolygonFilterAction = new ShapeFilterAction(FigureType.REGULAR_POLYGON);
	
	/**
	 * Action déclenchée lorsque l'on clique sur l'item de menu de filtrage
	 * des rectangles arrondis
	 */
	private final Action StarFilterAction = new ShapeFilterAction(FigureType.STAR);

	/**
	 * Action déclenchée lorsque l'on clique sur l'item de menu de filtrage
	 * des polygones
	 */
	private final Action polyFilterAction = new ShapeFilterAction(FigureType.POLYGON);
	
	/**
	 * Action déclenchée lorsque l'on clique sur l'item de menu de filtrage
	 * des type de lignes vides
	 */
	private final Action noneLineFilterAction = new LineFilterAction(LineType.NONE);
	
	/**
	 * Action déclenchée lorsque l'on clique sur l'item de menu de filtrage
	 * des type de lignes pleines
	 */
	private final Action solidLineFilterAction = new LineFilterAction(LineType.SOLID);
	
	/**
	 * Action déclenchée lorsque l'on clique sur l'item de menu de filtrage
	 * des type de lignes pointillées
	 */
	private final Action dashedLineFilterAction = new LineFilterAction(LineType.DASHED);
	
	
	private final Action FillColorFilterAction = new ColorFilterAction(PaintToType.FILL);
	private final Action EdgeColorFilterAction = new ColorFilterAction(PaintToType.EDGE);

	/**
	 * Constructeur de la fenètre de l'éditeur.
	 * Construit les widgets et assigne les actions et autres listeners
	 * aux widgets
	 * @throws HeadlessException
	 */
	public EditorFrame() throws HeadlessException
	{
		boolean isMacOS = System.getProperty("os.name").startsWith("Mac OS");

		/*
		 * Construire l'interface graphique en utilisant WindowBuilder:
		 * Menu Contextuel -> Open With -> WindowBuilder Editor puis
		 * aller dans l'onglet Design
		 */
		setPreferredSize(new Dimension(700, 600));
		drawingModel = new Drawing();
		creationListener = null;

		setTitle(EditorName);
		
		if (!isMacOS)
		{
			setIconImage(Toolkit.getDefaultToolkit().getImage(
					EditorFrame.class.getResource("/images/Logo.png")));
		}

		infoLabel = new JLabel("Test");
		JLabel coordLabel = new JLabel();
		InfoPanel infoPanel = new InfoPanel();
		drawingPanel = new DrawingPanel(drawingModel, coordLabel, infoPanel);

		// --------------------------------------------------------------------
		// Toolbar en haut
		// --------------------------------------------------------------------
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton undoButton = new JButton("Undo");
		JButton clearButton = new JButton("Clear");		
		JButton moveButton = new JButton("Move");		
		JButton aboutButton = new JButton("About");		
		JButton quitButton = new JButton("Quit");
		quitButton.setHorizontalAlignment(SwingConstants.RIGHT);
		
		undoButton.setAction(undoAction);
		clearButton.setAction(clearAction);
		moveButton.setAction(moveAction);
		aboutButton.setAction(aboutAction);
		quitButton.setAction(quitAction);
		
		toolBar.add(undoButton);		
		toolBar.add(clearButton);		
		toolBar.add(moveButton);		
		toolBar.add(aboutButton);		
		toolBar.add(quitButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(61, 11, 120, 140);
		getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		
		JLabeledComboBox labeledComboBox = new JLabeledComboBox("Shape", FigureType.stringValues(), defaultFigureTypeIndex,
													new ShapeItemListener(FigureType.fromInteger(defaultFigureTypeIndex))
												);
		labeledComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(labeledComboBox);
		
		JLabeledComboBox labeledComboBox_1 = new JLabeledComboBox("Fill Color", fillColorNames, defaultFillColorIndex,
													new ColorItemListener(fillPaints, defaultFillColorIndex, specialFillColorIndex, PaintToType.FILL)
												);
		labeledComboBox_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(labeledComboBox_1);
		
		JLabeledComboBox labeledComboBox_2 = new JLabeledComboBox("Edge Color", edgeColorNames, defaultEdgeColorIndex,
				new ColorItemListener(edgePaints, defaultEdgeColorIndex, specialEdgeColorIndex, PaintToType.EDGE)
			);
		labeledComboBox_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(labeledComboBox_2);
		
		
		JLabeledComboBox labeledComboBox_3 = new JLabeledComboBox("Line Type", LineType.stringValues(), defaultEdgeTypeIndex, 
				new EdgeTypeListener(LineType.fromInteger(defaultEdgeTypeIndex))
			);
		labeledComboBox_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(labeledComboBox_3);
		
		
		
		
		
		
		JPanel panel_s = new JPanel();
		panel.add(panel_s);
		
		JLabel lblNewLabel = new JLabel("Line width");
		panel_s.add(lblNewLabel);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(defaultEdgeWidth, minEdgeWidth, maxEdgeWidth, stepEdgeWidth));
		spinner.addChangeListener(new EdgeWidthListener(defaultEdgeWidth));	
		panel_s.add(spinner);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);		

		// --------------------------------------------------------------------
		// Panneau de contrôle à gauche
		// --------------------------------------------------------------------
		//infoPanel.setPreferredSize(new Dimension(170, 130));
		panel_1.add(infoPanel);
		
		
		// --------------------------------------------------------------------
		// Barre d'état en bas
		// --------------------------------------------------------------------
		JPanel panel_bas = new JPanel();
		getContentPane().add(panel_bas, BorderLayout.SOUTH);
		
		panel_bas.add(infoLabel);
		panel_bas.add(coordLabel);
		

		// --------------------------------------------------------------------
		// Zone de dessin
		// --------------------------------------------------------------------
		// drawingPanel = new DrawingPanel(...);
		// <zone de dessin>.setViewportView(drawingPanel);
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(drawingPanel);
		

		// --------------------------------------------------------------------
		// Barre de menus
		// --------------------------------------------------------------------
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);		
		
		JMenu mnDrawing = new JMenu("Drawing");
		menuBar.add(mnDrawing);
		
		JMenuItem mntmUndo = new JMenuItem("Undo");
		mntmUndo.setAction(undoAction);
		mnDrawing.add(mntmUndo);
		
		JMenuItem mntmClear = new JMenuItem("Clear");
		mntmClear.setAction(clearAction);
		mnDrawing.add(mntmClear);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.setAction(quitAction);
		mnDrawing.add(mntmQuit);
		
		JMenu mnFilter = new JMenu("Filter");
		menuBar.add(mnFilter);
		
		JCheckBoxMenuItem chckbxmntmFiltering = new JCheckBoxMenuItem("Filtering");
		chckbxmntmFiltering.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				drawingModel.setFiltering(e.getStateChange() == ItemEvent.SELECTED);
				drawingPanel.repaint();
			}
		});		
		mnFilter.add(chckbxmntmFiltering);
		
		JMenu mnFigures = new JMenu("Figures");
		mnFilter.add(mnFigures);
		
		JCheckBoxMenuItem chckbxmntmCircle = new JCheckBoxMenuItem("Circle");
		chckbxmntmCircle.setAction(circleFilterAction);
		mnFigures.add(chckbxmntmCircle);
		
		JCheckBoxMenuItem chckbxmntmEllipse = new JCheckBoxMenuItem("Ellipse");
		chckbxmntmEllipse.setAction(ellipseFilterAction);
		mnFigures.add(chckbxmntmEllipse);
		
		JCheckBoxMenuItem chckbxmntmPolygon = new JCheckBoxMenuItem("Polygon");
		chckbxmntmPolygon.setAction(polyFilterAction);
		mnFigures.add(chckbxmntmPolygon);
		
		JCheckBoxMenuItem chckbxmntmRectangle = new JCheckBoxMenuItem("Rectangle");
		chckbxmntmRectangle.setAction(rectangleFilterAction);
		mnFigures.add(chckbxmntmRectangle);
		
		JCheckBoxMenuItem chckbxmntmRoundedRectangle = new JCheckBoxMenuItem("Rounded Rectangle");
		chckbxmntmRoundedRectangle.setAction(rRectangleFilterAction);
		mnFigures.add(chckbxmntmRoundedRectangle);
		
		JCheckBoxMenuItem chckbxmntmRegularPolygon = new JCheckBoxMenuItem("Regular Polygon");
		chckbxmntmRegularPolygon.setAction(RegularPolygonFilterAction);
		mnFigures.add(chckbxmntmRegularPolygon);
		
		JCheckBoxMenuItem chckbxmntmStar = new JCheckBoxMenuItem("Star");
		chckbxmntmStar.setAction(StarFilterAction);
		mnFigures.add(chckbxmntmStar);
		
		JMenu mnColors = new JMenu("Colors");
		mnFilter.add(mnColors);
		
		JCheckBoxMenuItem chckbxmntmFillColor = new JCheckBoxMenuItem("Fill Color");
		//chckbxmntmFillColor.setAction();
		chckbxmntmFillColor.setAction(FillColorFilterAction);
		mnColors.add(chckbxmntmFillColor);
		
		JCheckBoxMenuItem chckbxmntmEdgeColor = new JCheckBoxMenuItem("Edge Color");
		chckbxmntmEdgeColor.setAction(EdgeColorFilterAction);
		mnColors.add(chckbxmntmEdgeColor);
		
		JMenu mnStrokes = new JMenu("Strokes");
		mnFilter.add(mnStrokes);
		
		JCheckBoxMenuItem chckbxmntmNone = new JCheckBoxMenuItem("None");
		chckbxmntmNone.setAction(noneLineFilterAction);
		mnStrokes.add(chckbxmntmNone);
		
		JCheckBoxMenuItem chckbxmntmSolid = new JCheckBoxMenuItem("Solid");
		chckbxmntmSolid.setAction(solidLineFilterAction);
		mnStrokes.add(chckbxmntmSolid);
		
		JCheckBoxMenuItem chckbxmntmDashed = new JCheckBoxMenuItem("Dashed");
		chckbxmntmDashed.setAction(dashedLineFilterAction);
		mnStrokes.add(chckbxmntmDashed);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setAction(aboutAction);
		mnHelp.add(mntmAbout);

		// --------------------------------------------------------------------
		// Ajout des contrôleurs aux widgets
		// pour connaître les Listeners applicable à un widget
		// dans WindowBuilder, sélectionnez un widger de l'UI puis Menu
		// Conextuel -> Add event handler
		// --------------------------------------------------------------------
		// compléter ...
	}

	/**
	 * Action pour quitter l'application
	 * @author davidroussel
	 */
	private class QuitAction extends AbstractAction // implements QuitHandler
	{
		/**
		 * Constructeur de l'action pour quitter l'application.
		 * Met en place le raccourci clavier, l'icône et la description
		 * de l'action
		 */
		public QuitAction()
		{
			putValue(NAME, "Quit");
			/*
			 * Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()
			 * 	= InputEvent.CTRL_MASK on win/linux
			 *  = InputEvent.META_MASK on mac os
			 */
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			putValue(LARGE_ICON_KEY, IconFactory.getIcon("Quit"));
			putValue(SMALL_ICON, IconFactory.getIcon("Quit_small"));
			putValue(SHORT_DESCRIPTION, "Quits the application");
		}

		/**
		 * Opérations réalisées par l'action
		 * @param e l'évènement déclenchant l'action. Peut provenir d'un bouton
		 *            ou d'un item de menu
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			doQuit();
		}

//		/**
//		 * Opérations réalisées par le quit handler
//		 * @param e l'évènement de quit
//		 * @param r la réponse au quit
//		 */
//		@Override
//		public void handleQuitRequestWith(QuitEvent e, QuitResponse r)
//		{
//			doQuit();
//		}

		/**
		 * Action réalisée pour quitter dans un {@link Action}
		 */
		public void doQuit()
		{
			/*
			 * Action à effectuer lorsque l'action "undo" est cliquée :
			 * sortir avec un System.exit() (pas très propre, mais fonctionne)
			 */
			System.exit(0);
		}
	}

	/**
	 * Action réalisée pour effacer la dernière figure du dessin.
	 */
	private class UndoAction extends AbstractAction
	{
		/**
		 * Constructeur de l'action effacer la dernière figure du dessin
		 * Met en place le raccourci clavier, l'icône et la description
		 * de l'action
		 */
		public UndoAction()
		{
			putValue(NAME, "Undo");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			putValue(LARGE_ICON_KEY, IconFactory.getIcon("Undo"));
			putValue(SMALL_ICON, IconFactory.getIcon("Undo_small"));
			putValue(SHORT_DESCRIPTION, "Undo last drawing");
		}

		/**
		 * Opérations réalisées par l'action
		 * @param e l'évènement déclenchant l'action. Peut provenir d'un bouton
		 *            ou d'un item de menu
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			/*
			 * Action à effectuer lorsque l'action "undo" est cliquée :
			 * retirer la dernière figure dessinée
			 */
			drawingModel.removeLastFigure();
			drawingPanel.repaint();
		}
	}

	/**
	 * Action réalisée pour effacer toutes les figures du dessin
	 */
	private class ClearAction extends AbstractAction
	{
		/**
		 * Constructeur de l'action pour effacer toutes les figures du dessin
		 * Met en place le raccourci clavier, l'icône et la description
		 * de l'action
		 */
		public ClearAction()
		{
			putValue(NAME, "Clear");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			putValue(LARGE_ICON_KEY,IconFactory.getIcon("Delete"));
			putValue(SMALL_ICON, IconFactory.getIcon("Delete_small"));
			putValue(SHORT_DESCRIPTION, "Erase all drawings");
		}

		/**
		 * Opérations réalisées par l'action
		 * @param e l'évènement déclenchant l'action. Peut provenir d'un bouton
		 *            ou d'un item de menu
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			/*
			 * Action à effectuer lorsque l'action "clear" est cliquée :
			 * Effacer toutes les figures du dessin
			 */
			drawingModel.clear();
			drawingPanel.repaint();
		}
	}
	
	/**
	 * Action réalisée pour effacer toutes les figures du dessin
	 */
	private class MoveAction extends AbstractAction
	{
		/**
		 * Constructeur de l'action pour effacer toutes les figures du dessin
		 * Met en place le raccourci clavier, l'icône et la description
		 * de l'action
		 */
		public MoveAction()
		{
			putValue(NAME, "Move");
			putValue(LARGE_ICON_KEY,IconFactory.getIcon("Move"));
			putValue(SMALL_ICON, IconFactory.getIcon("Move_small"));
			putValue(SHORT_DESCRIPTION, "Move figures");
		}

		/**
		 * Opérations réalisées par l'action
		 * @param e l'évènement déclenchant l'action. Peut provenir d'un bouton
		 *            ou d'un item de menu
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			/*
			 * Action à effectuer lorsque l'action "move" est cliquée :
			 * Activer/Désactiver le mode de deplacement
			 */
			
			boolean movemode = !drawingModel.isMoveMode();
			drawingModel.setMoveMode(movemode);
			
			if (movemode) {
				drawingPanel.removeCreationListener(creationListener);
				creationListener = new MoveShapeListener(drawingModel, infoLabel);
				drawingPanel.addCreationListener(creationListener);
			}
			else {				
				drawingPanel.removeCreationListener(creationListener);
				creationListener = drawingModel.getType().getCreationListener(drawingModel, infoLabel);
				drawingPanel.addCreationListener(creationListener);	
			}			
		}
	}

	/**
	 * Action réalisée pour afficher la boite de dialogue "A propos ..."
	 */
	private class AboutAction extends AbstractAction // implements AboutHandler
	{
		/**
		 * Constructeur de l'action pour afficher la boite de dialogue
		 * "A propos ..." Met en place le raccourci clavier, l'icône et la
		 * description de l'action
		 */
		public AboutAction() 
		{
			putValue(LARGE_ICON_KEY, IconFactory.getIcon("About"));
			putValue(SMALL_ICON, IconFactory.getIcon("About_small"));
			putValue(NAME, "About");
			putValue(SHORT_DESCRIPTION, "App information");
		}

		/**
		 * Opérations réalisées par l'action
		 * @param e l'évènement déclenchant l'action. Peut provenir d'un bouton
		 *            ou d'un item de menu
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			doAbout(e);
		}

//		/**
//		 * Actions réalisées par le about handler
//		 * @param e l'évènement déclenchant le about handler
//		 */
//		@Override
//		public void handleAbout(AboutEvent e)
//		{
//			doAbout(e);
//		}

		/**
		 * Action réalisée pour "A propos" dans un {@link Action}
		 * @param e l'évènement ayant déclenché l'action
		 */
		public void doAbout(EventObject e)
		{
			/*
			 * Action à effectuer lorsque l'action "about" est cliquée :
			 * Ouvrir un MessageDialog (JOptionPane.showMessageDialog(...)) de
			 * type JOptionPane.INFORMATION_MESSAGE
			 */
			//JOptionPane.showMessageDialog(null, "Projet d'ILO 2013-2014.\nRéalisé par Maxence Bobin et François Ho.", "Projet d'ILO 2013-2014.\n Réalisé par Maxence Bobin et Fran
			JOptionPane.showMessageDialog(null, "Projet d'ILO 2015-2016.\nRéalisé par Yassine Belcaid et Nicolas Venter.", "Figure Editor v3.0", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/**
	 * Action réalisée pour ajouter ou retirer un filtre de type de figure
	 */
	private class ShapeFilterAction extends AbstractAction // implements AboutHandler
	{		
		/**
		 * Le type de figure
		 */
		private FigureType type;
		
		/**
		 * Constructeur de l'action pour mettre en place ou enlever un filtre
		 * pour filtrer les types de figures
		 */
		public ShapeFilterAction(FigureType type) 
		{
			this.type = type;
			String name = type.toString();
			putValue(LARGE_ICON_KEY, IconFactory.getIcon(name));
			putValue(SMALL_ICON, IconFactory.getIcon(name + "_small"));
			putValue(NAME, name);
			putValue(SHORT_DESCRIPTION, "Set/unset " + name  + " filter");
		}

		/**
		 * Opérations réalisées par l'action
		 * @param event l'évènement déclenchant l'action. Peut provenir d'un 
		 * bouton ou d'un item de menu
		 */
		@Override
		public void actionPerformed(ActionEvent event)
		{
			/*
			 * Si l'AbstractButton de la source est sélectionné,
			 * on ajoute un ShapeFilter correspondant au type de figure (type) 
			 * au drawing model
			 * Sinon on enlève du drawing modèle tout filtre correspondant 
			 * au type de figure 
			 */
			JCheckBoxMenuItem btn = (JCheckBoxMenuItem) event.getSource();
			ShapeFilter filter = new ShapeFilter(type);
			
			if (btn.isSelected()) {
				drawingModel.addShapeFilter(filter);
			} else {
				drawingModel.removeShapeFilter(filter);
			}
			drawingPanel.repaint();
		}
	}
	
	/**
	 * Action réalisée pour ajouter ou retirer un filtre de type trait de figure
	 */
	private class ColorFilterAction extends AbstractAction // implements AboutHandler
	{		
		/**
		 * Le type de trait de la figure
		 */
		private PaintToType type;
		
		/**
		 * Constructeur de l'action pour mettre en place ou enlever un filtre
		 * pour filtrer les types de figures
		 */
		public ColorFilterAction(PaintToType type) 
		{
			this.type = type;
			String name = type.toString();
			putValue(LARGE_ICON_KEY, IconFactory.getIcon(name));
			putValue(SMALL_ICON, IconFactory.getIcon(name + "_small"));
			putValue(NAME, name);
			putValue(SHORT_DESCRIPTION, "Set/unset " + name  + " filter");
		}

		/**
		 * Opérations réalisées par l'action
		 * @param event l'évènement déclenchant l'action. Peut provenir d'un 
		 * bouton ou d'un item de menu
		 */
		@Override
		public void actionPerformed(ActionEvent event)
		{
			/*
			 * Si l'AbstractButton de la source est sélectionné,
			 * on ajoute un LineFilter correspondant au type de trait de la 
			 * figure (type) au drawing model
			 * Sinon on enlève du drawing modèle tout filtre correspondant 
			 * au type de trait de la figure 
			 */
			JCheckBoxMenuItem btn = (JCheckBoxMenuItem) event.getSource();
			
			if (btn.isSelected()) {
				if (type == PaintToType.FILL)
					drawingModel.setFillColorFilter(new FillColorFilter(drawingModel.getFillpaint()));
				else
					drawingModel.setEdgeColorFilter(new EdgeColorFilter(drawingModel.getEdgePaint()));
			} else {
				if (type == PaintToType.FILL)
					drawingModel.setFillColorFilter(null);
				else
					drawingModel.setEdgeColorFilter(null);
			}
			drawingPanel.repaint();
		}
	}
	
	/**
	 * Action réalisée pour ajouter ou retirer un filtre de type trait de figure
	 */
	private class LineFilterAction extends AbstractAction // implements AboutHandler
	{		
		/**
		 * Le type de trait de la figure
		 */
		private LineType type;
		
		/**
		 * Constructeur de l'action pour mettre en place ou enlever un filtre
		 * pour filtrer les types de figures
		 */
		public LineFilterAction(LineType type) 
		{
			this.type = type;
			String name = type.toString();
			putValue(LARGE_ICON_KEY, IconFactory.getIcon(name));
			putValue(SMALL_ICON, IconFactory.getIcon(name + "_small"));
			putValue(NAME, name);
			putValue(SHORT_DESCRIPTION, "Set/unset " + name  + " filter");
		}

		/**
		 * Opérations réalisées par l'action
		 * @param event l'évènement déclenchant l'action. Peut provenir d'un 
		 * bouton ou d'un item de menu
		 */
		@Override
		public void actionPerformed(ActionEvent event)
		{
			/*
			 * Si l'AbstractButton de la source est sélectionné,
			 * on ajoute un LineFilter correspondant au type de trait de la 
			 * figure (type) au drawing model
			 * Sinon on enlève du drawing modèle tout filtre correspondant 
			 * au type de trait de la figure 
			 */
			JCheckBoxMenuItem btn = (JCheckBoxMenuItem) event.getSource();
			
			LineFilter filtre = new LineFilter(type);
			if (btn.isSelected()) {
				drawingModel.addLineFilter(filtre);
			} else {
				drawingModel.removeLineFilter(filtre);
			}
			drawingPanel.repaint();
		}
	}

	/**
	 * Contrôleur d'évènement permettant de modifier le type de figures à
	 * dessiner.
	 * @note dépends de #drawingModel et #infoLabel qui doivent être non
	 * null avant instanciation
	 */
	private class ShapeItemListener implements ItemListener
	{
		/**
		 * Constructeur valué du contrôleur.
		 * Initialise le type de dessin dans {@link EditorFrame#drawingModel}
		 * et crée le {@link AbstractCreationListener} correspondant.
		 * @param initialIndex l'index du type de forme sélectionné afin de
		 * mettre en place le bon creationListener dans le
		 * {@link EditorFrame#drawingPanel}.
		 */
		public ShapeItemListener(FigureType type)
		{
			// Mise en place du type de figure ds le drawingModel
			drawingModel.setType(type);
			/*
			 * Création et Mise en place du creationListener adéquat
			 * dans le drawingPanel
			 */
			
			creationListener = type.getCreationListener(drawingModel, infoLabel);
			drawingPanel.addCreationListener(creationListener);
		}

		@Override
		public void itemStateChanged(ItemEvent e)
		{
			JComboBox<?> items = (JComboBox<?>) e.getSource();
			
			/*
			 * Récupération de l'index et vérification que le JCombobox
			 * a bien été changé puis
			 * Mise en place du type de figure ds le drawingModel
			 * Retrait du dernier creationListener du drawingPanel
			 * Création et Mise en place du creationListener adéquat
			 * dans le drawingPanel
			 */
			int index = items.getSelectedIndex();
			int stateChange = e.getStateChange();
			
			
			if (stateChange == ItemEvent.SELECTED) {
				FigureType figuretype = FigureType.fromInteger(index);
				drawingModel.setType(figuretype);
				
				drawingPanel.removeCreationListener(creationListener);
				creationListener = figuretype.getCreationListener(drawingModel, infoLabel);
				drawingPanel.addCreationListener(creationListener);	
			}
		}
	}

	/**
	 * Contrôleur d'évènements permettant de modifier la couleur du trait
	 * @note utilise #drawingModel qui doit être non null avant instanciation
	 */
	private class ColorItemListener implements ItemListener
	{
		/**
		 * Ce à quoi s'applique la couleur choisie.
		 * Soit au rmplissage, soit au trait.
		 */
		private PaintToType applyTo;

		/**
		 * La dernière couleur choisie (pour le {@link JColorChooser})
		 */
		private Color lastColor;

		/**
		 * Le tableau des couleurs possibles
		 */
		private Paint[] colors;

		/**
		 * L'index de la couleur spéciale à choisir avec un {@link JColorChooser}
		 */
		private final int customColorIndex;

		/**
		 * L'index de la dernière couleur sélectionnée dans le combobox.
		 * Afin de pouvoir y revenir si jamais le {@link JColorChooser} est
		 * annulé.
		 */
		private int lastSelectedIndex;

		/**
		 * la couleur choisie
		 */
		private Paint paint;

		/**
		 * Constructeur du contrôleur d'évènements d'un combobox permettant
		 * de choisir la couleur de templissage
		 * @param colors le tableau des couleurs possibles
		 * @param selectedIndex l'index de l'élément actuellement sélectionné
		 * @param customColorIndex l'index de la couleur spéciale parmis les
		 * colors à définir à l'aide d'un {@link JColorChooser}.
		 * @param applyTo Ce à quoi s'applique la couleur (le remplissage ou
		 * bien le trait)
		 */
		public ColorItemListener(Paint[] colors,
		                         int selectedIndex,
		                         int customColorIndex,
		                         PaintToType applyTo)
		{
			this.colors = colors;
			lastSelectedIndex = selectedIndex;
			this.customColorIndex = customColorIndex;
			this.applyTo = applyTo;
			lastColor = (Color) colors[selectedIndex];
			paint = colors[selectedIndex];

			applyTo.applyPaintTo(paint, drawingModel);
		}

		/**
		 * Actions à réaliser lorsque l'élément sélectionné du combox change
		 * @param e l'évènement de changement d'item du combobox
		 */
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			JComboBox<?> combo = (JComboBox<?>) e.getSource();
			int index = combo.getSelectedIndex();
			
			/*
			 *  Si l'index est correct (< colors.length)
			 * 	Si l'état du combo est bien changé
			 * 	Si l'item sélectionné correspond à customColorIndex
			 * 	alors il faut ouvrir une Boite de dialoque de choix de couleur
			 * 	avec la PaintFactory et si la couleur résultante est non null
			 * 	l'appliquer au drawing model
			 * 	sinon déterminer la couleur correspondant à l'item sélectionné
			 * 	et l'appliquer au drawing model	
			 */
			if (index >= 0 && index < colors.length) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (index == customColorIndex) {
						Color c = JColorChooser.showDialog(combo,
								"Choose " + applyTo.toString() + " Color",
								lastColor);
						if (c != null)
							paint = c;
						else
							combo.setSelectedIndex(lastSelectedIndex);
							
					}
					else
						paint = colors[index];
					
					applyTo.applyPaintTo(paint, drawingModel);
				}
				else if (e.getStateChange() == ItemEvent.DESELECTED)
				{
					if (index >= 0 && index < customColorIndex)
					{
						lastColor = (Color) edgePaints[index];
						lastSelectedIndex = index;
					}
				}
			}
			else
			{
				System.err.println("Unknown " + applyTo.toString()
						+ " color index : " + index);
			}
			
			if (drawingModel.getFiltering())
				drawingPanel.repaint();
		}
	}

	/**
	 * Contrôleur d'évènements permettant de modifier le type de trait (normal,
	 * pointillé, sans trait)
	 * @note utilise #drawingModel qui doit être non null avant instanciation
	 */
	private class EdgeTypeListener implements ItemListener
	{
		/**
		 * Le type de trait à mettre en place
		 */
		private LineType edgeType;

		public EdgeTypeListener(LineType type)
		{
			edgeType = type;
			drawingModel.setEdgeType(edgeType);
		}

		@Override
		public void itemStateChanged(ItemEvent e)
		{
			JComboBox<?> items = (JComboBox<?>) e.getSource();
			int index = items.getSelectedIndex();

			/*
			 * Si l'état du combo est bien changé
			 * Récupérer le type de ligne correspondant à l'index sélectionné
			 * et l'applique au drawingModel
			 */
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				edgeType = LineType.fromInteger(index);
				drawingModel.setEdgeType(edgeType);
				
				if (drawingModel.getFiltering())
					drawingPanel.repaint();
			}
		}
	}

	/**
	 * Contrôleur d'évènement permettant de modifier la taille du trait
	 * en fonction des valeurs d'un {@link JSpinner}
	 */
	private class EdgeWidthListener implements ChangeListener
	{
		/**
		 * Constructeur du contrôleur d'évènements contrôlant l'épaisseur du
		 * trait
		 * @param initialValue la valeur initiale de la largeur du trait à
		 * appliquer au dessin (EditorFrame#drawingModel)
		 */
		public EdgeWidthListener(int initialValue)
		{
			drawingModel.setEdgeWidth(initialValue);
		}

		/**
		 * Actions à réaliser lorsque la valeur du spinner change
		 * @param e l'évènement de changement de valeur du spinner
		 */
		@Override
		public void stateChanged(ChangeEvent e)
		{
			/*
			 * récupérer le spinner d'après la source, puis
			 * son modèle et enfin mettre en place la valeur de l'épaisseur
			 * du trait dans le drawing model
			 */
			JSpinner spinner = (JSpinner) e.getSource();
			SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
			drawingModel.setEdgeWidth((int) model.getValue());
		}
	}
}
