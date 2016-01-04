package figures.creationListeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JLabel;

import figures.Figure;
import figures.Drawing;

/**
 * Listener (incomplet) des évènements souris pour créer une figure. Chaque
 * figure (Cercle, Ellipse, Rectangle, etc) est graphiquement construite par une
 * suite de pressed/drag/release ou de clicks qui peut être différente pour
 * chaque type de figure. Aussi les classes filles devront implémenter leur
 * propre xxxCreationListener assurant la gestion de la création d'une nouvelle
 * figure.
 *
 * @author davidroussel
 */
public abstract class AbstractCreationListener implements MouseListener,
		MouseMotionListener
{
	/**
	 * Le drawing model à modifier par ce creationListener. Celui ci contient
	 * tous les élements nécessaires à la modification du dessin par les
	 * évènements souris.
	 */
	protected Drawing drawingModel;

	/**
	 * La figure en cours de dessin. Obtenue avec
	 * {@link Drawing#initiateFigure(java.awt.geom.Point2D)}. Evite d'avoir à
	 * appeler {@link Drawing#getLastFigure()} à chaque fois que la figure en
	 * cours de construction est modifiée.
	 */
	protected Figure currentFigure;

	/**
	 * Le label dans lequel afficher les instructions nécessaires à la
	 * complétion de la figure
	 */
	protected JLabel tipLabel;

	/**
	 * Le point de départ de la création de la figure. Utilisé pour comparer le
	 * point de départ et le point terminal pour élminier les figures de taille
	 * 0;
	 */
	protected Point2D startPoint;

	/**
	 * Le point terminal de la création de la figure. Utilisé pour comparer le
	 * point de départ et le point terminal pour élminier les figures de taille
	 * 0;
	 */
	protected Point2D endPoint;

	/**
	 * le conseil par défaut à afficher dans le {@link #tipLabel}
	 */
	public static final String defaultTip = new String(
			"Cliquez pour initier une figure");

	/**
	 * Le tableau de chaines de caractères contenant les conseils à
	 * l'utilisateur pour chacune des étapes de la création. Par exemple [0] :
	 * cliquez et maintenez enfoncé pour initier la figure [1] : relâchez pour
	 * terminer la figure
	 */
	protected String[] tips;

	/**
	 * Le nombre d'étapes (typiquement click->drag->release) nécessaires à la
	 * création de la figure
	 */
	protected final int nbSteps;

	/**
	 * L'étape actuelle de création de la figure
	 */
	protected int currentStep;

	/**
	 * Constructeur protégé (destiné à être utilisé par les classes filles)
	 *
	 * @param model le modèle de dessin à modifier par ce creationListener
	 * @param infoLabel le label dans lequel afficher les conseils d'utilisation
	 * @param nbSteps le nombres d'étapes de création de la figure
	 */
	protected AbstractCreationListener(Drawing model, JLabel infoLabel,
			int nbSteps)
	{
		drawingModel = model;
		currentFigure = null;
		tipLabel = infoLabel;
		this.nbSteps = nbSteps;
		currentStep = 0;

		// Allocation du nombres de conseils utilisateurs nécessaires
		tips = new String[(nbSteps > 0 ? nbSteps : 0)];

		if (drawingModel == null)
		{
			System.err.println("AbstractCreationListener caution null "
					+ "drawing model");
		}

		if (tipLabel == null)
		{
			System.err.println("AbstractCreationListener caution null "
					+ "tip label");
		}
	}

//	/**
//	 * Mise en place du label dans lequel afficher les conseils d'utilisation
//	 *
//	 * @param label le label dans lequel afficher les conseils d'utilisation
//	 */
//	public void setTipLabel(JLabel label)
//	{
//		tipLabel = label;
//	}

	/**
	 * Initialisation de la création d'une nouvelle figure. détermine le point
	 * de départ de la figure ({@link #startPoint}), initie une nouvelle figure
	 * à la position de l'évènement ({@link Drawing#initiateFigure(Point2D)}),
	 * met à jour le dessin {@link Drawing#update()}, puis passe à l'étape
	 * suivante en mettant à jour les conseils utilisateurs (
	 * {@link #updateTip()}). Pour la plupart des figures la création commence
	 * par un appui sur le bouton gauche de la souris. A utiliser dans
	 * {@link MouseListener#mousePressed(MouseEvent)} ou bien dans
	 * {@link MouseListener#mouseClicked(MouseEvent)} suivant la figure à créer.
	 *
	 * @param e l'évènement souris à utiliser pour initier la création d'une
	 *            nouvelle figure à la position de cet évènement
	 */
	public void startFigure(MouseEvent e)
	{
		if (!drawingModel.isMoveMode()) {
			startPoint = e.getPoint();
			currentFigure = drawingModel.initiateFigure(e.getPoint());
	
			nextStep();
	
			drawingModel.update();
		}
	}

	/**
	 * Terminaison de la création d'une figure. remet l'étape courante à 0,
	 * détermine la position du point de terminaison de la figure (
	 * {@link #endPoint}), vérifie que la figure ainsi terminée n'est pas de
	 * taille 0 ({@link #checkZeroSizeFigure()}), puis met à jour le dessin (
	 * {@link Drawing#update()}) et les conseils utilisateurs (
	 * {@link #updateTip()}). A utiliser dans un
	 * {@link MouseListener#mousePressed(MouseEvent)} ou bien dans un
	 * {@link MouseListener#mouseClicked(MouseEvent)} suivant la figure à créer.
	 *
	 * @param e l'évènement souris à utiliser lors de la terminaison d'un figure
	 */
	public void endFigure(MouseEvent e)
	{
		// Remise à zéro de currentStep pour pouvoir réutiliser ce
		// listener sur une autre figure
		if (!drawingModel.isMoveMode()) {
			nextStep();
	
			endPoint = e.getPoint();
	
			checkZeroSizeFigure();
	
			drawingModel.update();
		}
	}

	/**
	 * Passage à l'étape suivante et mise à jours des conseils utilisateurs
	 * relatifs à l'étape suivante.
	 * Lorsque le passage à l'étape suivante dépasse le nombre d'étapes prévues
	 * l'étape courante est remise à 0.
	 * @see #currentStep
	 * @see #updateTip()
	 */
	protected void nextStep()
	{
		if (currentStep < (nbSteps-1))
		{
			currentStep++;
		}
		else
		{
			currentStep = 0;
		}

		updateTip();
	}

	/**
	 * Mise à jour du conseil dans le {@link #tipLabel} en fonction de l'étape
	 * courante
	 */
	protected void updateTip()
	{
		if (tipLabel != null)
		{
			tipLabel.setText(tips[currentStep]);
		}
	}

	/**
	 * Contrôle de la taille de la figure créée à effectuer à la fin de la
	 * création afin d'éliminer les figures de taille 0;
	 * @see #startPoint
	 * @see #endPoint
	 */
	protected void checkZeroSizeFigure()
	{
		if (startPoint.distance(endPoint) < 1.0)
		{
			drawingModel.removeLastFigure();
			System.err.println("Removed zero sized figure");
		}
	}
}
