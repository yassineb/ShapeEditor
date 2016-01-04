package widgets;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import figures.Drawing;
import figures.Figure;
import figures.enums.FigureType;
import figures.enums.LineType;
import utils.IconFactory;
import utils.PaintFactory;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class InfoPanel extends JPanel
{
	/**
	 * Une chaine vide pour remplir les champs lorsque la souris n'est au dessus
	 * d'aucune figure
	 */
	private static final String emptyString = new String();

	/**
	 * Une icône vide pour remplir les chanmps avec icône lorsque la souris
	 * n'est au dessus d'aucune figure
	 */
	private static final ImageIcon emptyIcon = IconFactory.getIcon("None");

	/**
	 * Le formatteur à utiliser pour formater les coordonnés
	 */
	private final static DecimalFormat coordFormat = new DecimalFormat("000");

	/**
	 * Le label contenant le nom de la figure
	 */
	private JLabel lblFigureName;
	
	/**
	 * Le label contenant l'icône correspondant à la figure
	 */
	private JLabel lblTypeicon;
	
	/**
	 * La map contenant les différentes icônes des types de figures
	 */
	private Map<FigureType, ImageIcon> figureIcons;
	
	/**
	 * Le label contenant l'icône de la couleur de remplissage
	 */
	private JLabel lblFillcolor;
	
	/**
	 * Le label contenant l'icône de la couleur du contour
	 */
	private JLabel lblEdgecolor;
	
	/**
	 * Map contenant les icônes relatives aux différentes couleurs (de contour
	 * ou de remplissage)
	 */
	private Map<Paint, ImageIcon> paintIcons;
	
	/**
	 * Le label contenant le type de contour
	 */
	private JLabel lblStroketype;
	
	/**
	 * Map contenant les icônes relatives au différents types de traits de 
	 * contour
	 */
	private Map<LineType, ImageIcon> lineTypeIcons;
	
	/**
	 * Le label contenant l'abcisse du point en haut à gauche de la figure
	 */
	private JLabel lblTlx;
	
	/**
	 * Le label contenant l'ordonnée du point en haut à gauche de la figure
	 */
	private JLabel lblTly;

	/**
	 * Le label contenant l'abcisse du point en bas à droite de la figure
	 */
	private JLabel lblBrx;

	/**
	 * Le label contenant l'ordonnée du point en bas à droite de la figure
	 */
	private JLabel lblBry;

	/**
	 * Le label contenant la largeur de la figure
	 */
	private JLabel lblDx;

	/**
	 * Le label contenant la hauteur de la figure
	 */
	private JLabel lblDy;

	/**
	 * Le label contenant l'abcisse du barycentre de la figure
	 */
	private JLabel lblCx;

	/**
	 * Le label contenant l'ordonnée du barycentre de la figure
	 */
	private JLabel lblCy;
	private JLabel lblFill;
	private JLabel lblStroke;
	private JLabel lblX;
	private JLabel lblY;
	private JLabel lblTopLeft;
	private JLabel lblBottomRight;
	private JLabel lblDimensions;
	private JLabel lblCenter;
	
	/**
	 * Create the panel.
	 */
	public InfoPanel()
	{
		// --------------------------------------------------------------------
		// Initialisation des maps
		// --------------------------------------------------------------------
		
		// Remplissage de figureIcons en utilisant l'IconFactory
		figureIcons = new HashMap<FigureType, ImageIcon>();
		for (int i = 0; i < FigureType.NbFigureTypes; i++) {
			FigureType f = FigureType.fromInteger(i);
			figureIcons.put(f, IconFactory.getIcon(f.toString()));
		}

		paintIcons = new HashMap<Paint, ImageIcon>();
		String[] colorStrings = {
			"Black",
			"Blue",
			"Cyan",
			"Green",
			"Magenta",
			"None",
			"Orange",
			"Others",
			"Red",
			"White",
			"Yellow"
		};

		/*
		 * Obtention des paints par la PaintFactory, puis
		 * Obtention des icônes correspondant aux paints avec l'IconFactory
		 * pour remplir paintIcons
		 */
		for (int i = 0; i < colorStrings.length; i++)
		{
			paintIcons.put(PaintFactory.getPaint(colorStrings[i]), IconFactory.getIcon(colorStrings[i]));
		}
		
		
		/*
		 * Remplissage de lineTypeIcons avec l'IconFactory pour chaque
		 * type de lignes
		 */
		lineTypeIcons = new HashMap<LineType, ImageIcon>();
		for (int i = 0; i < LineType.NbLineTypes; i++) {
			LineType l = LineType.fromInteger(i);
			lineTypeIcons.put(l, IconFactory.getIcon(l.toString()));
		}
		
		// --------------------------------------------------------------------
		// Création de l'UI
		// --------------------------------------------------------------------
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 80, 60, 60};
		gridBagLayout.rowHeights = new int[] {0, 30, 32, 32, 32, 20, 20, 20, 20, 20};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		setLayout(gridBagLayout);
		

		// Compléter ...
		// figure
		lblFigureName = new JLabel(emptyString);
		GridBagConstraints gbc_lblFigureName = new GridBagConstraints();
		gbc_lblFigureName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFigureName.gridx = 2;
		gbc_lblFigureName.gridy = 0;
		add(lblFigureName, gbc_lblFigureName);
		
		
		// typeicon
		JLabel lblType = new JLabel("type");
		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.insets = new Insets(0, 0, 5, 5);
		gbc_lblType.gridx = 0;
		gbc_lblType.gridy = 1;
		add(lblType, gbc_lblType);
		
		lblTypeicon = new JLabel(emptyString);
		GridBagConstraints gbc_lblTypeicon = new GridBagConstraints();
		gbc_lblTypeicon.insets = new Insets(0, 0, 5, 5);
		gbc_lblTypeicon.gridx = 3;
		gbc_lblTypeicon.gridy = 1;
		add(lblTypeicon, gbc_lblTypeicon);
		
		
		// fill 
		lblFill = new JLabel("fill");
		GridBagConstraints gbc_lblFill = new GridBagConstraints();
		gbc_lblFill.insets = new Insets(0, 0, 5, 5);
		gbc_lblFill.gridx = 0;
		gbc_lblFill.gridy = 2;
		add(lblFill, gbc_lblFill);
		
		
		lblFillcolor = new JLabel(emptyString);
		GridBagConstraints gbc_lblFillColor = new GridBagConstraints();
		gbc_lblFillColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblFillColor.gridx = 3;
		gbc_lblFillColor.gridy = 2;
		add(lblFillcolor, gbc_lblFillColor);
		
		// edgecolor/stroke
		lblStroke = new JLabel("stroke");
		GridBagConstraints gbc_lblStroke = new GridBagConstraints();
		gbc_lblStroke.insets = new Insets(0, 0, 5, 5);
		gbc_lblStroke.gridx = 0;
		gbc_lblStroke.gridy = 3;
		add(lblStroke, gbc_lblStroke);		
		
		lblEdgecolor = new JLabel(emptyString);
		GridBagConstraints gbc_lblEdgecolor = new GridBagConstraints();
		gbc_lblEdgecolor.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdgecolor.gridx = 2;
		gbc_lblEdgecolor.gridy = 3;
		add(lblEdgecolor, gbc_lblEdgecolor);
		
		lblStroketype = new JLabel(emptyString);
		GridBagConstraints gbc_lblStroketype = new GridBagConstraints();
		gbc_lblStroketype.insets = new Insets(0, 0, 5, 0);
		gbc_lblStroketype.gridx = 4;
		gbc_lblStroketype.gridy = 3;
		add(lblStroketype, gbc_lblStroketype);
		
		// x/y
		lblX = new JLabel("x");
		GridBagConstraints gbc_lblX = new GridBagConstraints();
		gbc_lblX.insets = new Insets(0, 0, 5, 5);
		gbc_lblX.gridx = 2;
		gbc_lblX.gridy = 4;
		add(lblX, gbc_lblX);
		
		lblY = new JLabel("y");
		GridBagConstraints gbc_lblY = new GridBagConstraints();
		gbc_lblY.insets = new Insets(0, 0, 5, 0);
		gbc_lblY.gridx = 4;
		gbc_lblY.gridy = 4;
		add(lblY, gbc_lblY);
		
		
		// top left
		lblTopLeft = new JLabel("top left");
		GridBagConstraints gbc_lblTopLeft = new GridBagConstraints();
		gbc_lblTopLeft.insets = new Insets(0, 0, 5, 5);
		gbc_lblTopLeft.gridx = 0;
		gbc_lblTopLeft.gridy = 5;
		add(lblTopLeft, gbc_lblTopLeft);
		
		lblTlx = new JLabel("Tlx");
		GridBagConstraints gbc_lblTlx = new GridBagConstraints();
		gbc_lblTlx.insets = new Insets(0, 0, 5, 5);
		gbc_lblTlx.gridx = 2;
		gbc_lblTlx.gridy = 5;
		add(lblTlx, gbc_lblTlx);
		
		lblTly = new JLabel("Tly");
		GridBagConstraints gbc_lblTly = new GridBagConstraints();
		gbc_lblTly.insets = new Insets(0, 0, 5, 0);
		gbc_lblTly.gridx = 4;
		gbc_lblTly.gridy = 5;
		add(lblTly, gbc_lblTly);
		
		
		// bottom right
		lblBottomRight = new JLabel("bottom right");
		GridBagConstraints gbc_lblBottomRight = new GridBagConstraints();
		gbc_lblBottomRight.insets = new Insets(0, 0, 5, 5);
		gbc_lblBottomRight.gridx = 0;
		gbc_lblBottomRight.gridy = 6;
		add(lblBottomRight, gbc_lblBottomRight);
		
		lblBrx = new JLabel("Brx");
		GridBagConstraints gbc_lblBrx = new GridBagConstraints();
		gbc_lblBrx.insets = new Insets(0, 0, 5, 5);
		gbc_lblBrx.gridx = 2;
		gbc_lblBrx.gridy = 6;
		add(lblBrx, gbc_lblBrx);
		
		lblBry = new JLabel("Bry");
		GridBagConstraints gbc_lblBry = new GridBagConstraints();
		gbc_lblBry.insets = new Insets(0, 0, 5, 0);
		gbc_lblBry.gridx = 4;
		gbc_lblBry.gridy = 6;
		add(lblBry, gbc_lblBry);
		
		// dimensions
		lblDimensions = new JLabel("dimensions");
		GridBagConstraints gbc_lblDimensions = new GridBagConstraints();
		gbc_lblDimensions.insets = new Insets(0, 0, 5, 5);
		gbc_lblDimensions.gridx = 0;
		gbc_lblDimensions.gridy = 7;
		add(lblDimensions, gbc_lblDimensions);
		
		lblDx = new JLabel("Dx");
		GridBagConstraints gbc_lblDx = new GridBagConstraints();
		gbc_lblDx.insets = new Insets(0, 0, 5, 5);
		gbc_lblDx.gridx = 2;
		gbc_lblDx.gridy = 7;
		add(lblDx, gbc_lblDx);
		
		lblDy = new JLabel("Dy");
		GridBagConstraints gbc_lblDy = new GridBagConstraints();
		gbc_lblDy.insets = new Insets(0, 0, 5, 0);
		gbc_lblDy.gridx = 4;
		gbc_lblDy.gridy = 7;
		add(lblDy, gbc_lblDy);
		
		
		// barrycentre
		lblCenter = new JLabel("center");
		GridBagConstraints gbc_lblCenter = new GridBagConstraints();
		gbc_lblCenter.insets = new Insets(0, 0, 5, 5);
		gbc_lblCenter.gridx = 0;
		gbc_lblCenter.gridy = 8;
		add(lblCenter, gbc_lblCenter);
		
		lblCx = new JLabel("Cx");
		GridBagConstraints gbc_lblCx = new GridBagConstraints();
		gbc_lblCx.insets = new Insets(0, 0, 5, 5);
		gbc_lblCx.gridx = 2;
		gbc_lblCx.gridy = 8;
		add(lblCx, gbc_lblCx);
		
		lblCy = new JLabel("Cly");
		GridBagConstraints gbc_lblCy = new GridBagConstraints();
		gbc_lblCy.insets = new Insets(0, 0, 5, 5);
		gbc_lblCy.gridx = 4;
		gbc_lblCy.gridy = 8;
		add(lblCy, gbc_lblCy);		
	}

	/**
	 * Mise à jour de tous les labels avec les informations de figure
	 * @param figure la figure dont il faut extraire les informations
	 */
	public void updateLabels(Figure figure)
	{
		// MAJ titre de la figure
		lblFigureName.setText(figure.getName());
		
		// MAJ Icône du type de figure
		lblTypeicon.setIcon(figureIcons.get(figure.getType()));
		
		// MAJ Icône de la couleur de remplissage
		lblFillcolor.setIcon(paintIcons.get(figure.getFillPaint()));
		
		// MAJ Icône de la couleur de trait
		lblEdgecolor.setIcon(paintIcons.get(figure.getEdgePaint()));
		
		// MAJ Icône du type de trait
		lblStroketype.setIcon(lineTypeIcons.get(LineType.fromStroke(figure.getStroke())));
		
		/*
		 * MAJ Données numériques de la figure en utilisant les méthodes
		 * de la classe Figure
		 * 	- Top left corner x & y
		 * 	- Bottom right corner ...
		 * 	- Dimensions ...
		 * 	- Center ...
		 */
		/*
		 * Formmattage des données numérique avec coordFormat pour mette
		 * à jour les différents label avec les nouvelles valeurs numériques
		 */
		
		Rectangle2D bounds = figure.getBounds2D();
		Point2D center = figure.getCenter();
		lblTlx.setText(coordFormat.format(bounds.getX()));
		lblTly.setText(coordFormat.format(bounds.getY()));
		
		lblBrx.setText(coordFormat.format(bounds.getMaxX()));
		lblBry.setText(coordFormat.format(bounds.getMaxY()));
		
		lblDx.setText(coordFormat.format(bounds.getWidth()));
		lblDy.setText(coordFormat.format(bounds.getHeight()));
		
		lblCx.setText(coordFormat.format(center.getX()));
		lblCy.setText(coordFormat.format(center.getY()));
	}

	/**
	 * Effacement de tous les labels	 
	 */
	public void resetLabels()
	{
		// RAZ titre de la figure
		lblFigureName.setText(emptyString);		
		
		// RAZ Icône du type de figure
		lblTypeicon.setIcon(emptyIcon);
		
		// RAZ Icône de la couleur de remplissage
		lblFillcolor.setIcon(emptyIcon);
		
		// RAZ Icône de la couleur de trait
		lblEdgecolor.setIcon(emptyIcon);
		
		// RAZ Icône du type de trait
		lblStroketype.setIcon(emptyIcon);
		
		// RAZ Données numériques
		lblTlx.setText(emptyString);
		lblTly.setText(emptyString);
		lblBrx.setText(emptyString);
		lblBry.setText(emptyString);
		lblDx.setText(emptyString);
		lblDy.setText(emptyString);
		lblCx.setText(emptyString);
		lblCy.setText(emptyString);
	}
}
