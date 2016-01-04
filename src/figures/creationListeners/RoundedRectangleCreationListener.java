package figures.creationListeners;

import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import figures.Drawing;
import figures.RoundedRectangle;

/**
 * Listener permettant d'enchainer les actions souris nécessaires à la création
 * d'un Rectangle arrondi:
 *
 * @author davidroussel
 */
public class RoundedRectangleCreationListener extends AbstractCreationListener
{

	/**
	 * Constructeur d'un Listener pour créer un polygon en plusieurs clicks
	 *
	 * @param model le modèle de dessin à modifier
	 * @param infoLabel le label dans lequel afficher les consseils utilisateurs
	 */
	public RoundedRectangleCreationListener(Drawing model, JLabel infoLabel)
	{
		super(model, infoLabel, 3);

		tips[0] = new String("Bouton gauche + drag pour commencer le regtangle");
		tips[1] = new String("Relâchez pour terminer le rectangle");
		tips[2] = new String("Clic gauche pour terminer l'arrondi du rectangle");

		updateTip();

		System.out.println("RoundedRectangleCreationListener created");
	}

	/**
	 * Initiation de la création d'un rectangle arrondi et passage à l'étape
	 * suivante
	 *
	 * @param e l'évènement souris associé
	 * @see figures.creationListeners.AbstractCreationListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		if ((e.getButton() == MouseEvent.BUTTON1) && (currentStep == 0))
		{
			startFigure(e);
		}
	}

	/**
	 * Terminaison de la partie rectangle du rectangle arrondi et passage à
	 * l'étape suivante
	 *
	 * @param e l'évènement souris associé
	 * @see figures.creationListeners.AbstractCreationListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		// Terminaison du rectangle mais pas encore de l'arrondi
		if ((e.getButton() == MouseEvent.BUTTON1) && (currentStep == 1))
		{
			nextStep();
		}
	}

	/**
	 * Après la partie terminaison de la partie rectangle, terminaison de la
	 * partie arc (arrondi)
	 *
	 * @param e l'évènement souris associé
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if ((e.getButton() == MouseEvent.BUTTON1) && (currentStep == 2))
		{
			endFigure(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e)
	{
		// Rien
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e)
	{
		// Rien
	}

	/**
	 * Modification de l'arrondi en fonction de la position du point de
	 * l'évènement par rapport au coin inférieur droit du rectangle lorsque l'on
	 * se trouve dans l'étape de modification de l'arrondi
	 *
	 * @param e l'évènement souris associé
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e)
	{
		if (currentStep == 2)
		{
			RoundedRectangle rect = (RoundedRectangle) currentFigure;
			rect.setArc(e.getPoint());

			drawingModel.update();
		}
	}

	/**
	 * Déplacement du point en bas à droite du rectangle lorsque l'on est dans
	 * l'étape de formation du rectangle
	 *
	 * @param e l'évènement souris associé
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (currentStep == 1)
		{
			// déplacement du coin inférieur droit du rectangle
			currentFigure.setLastPoint(e.getPoint());
			drawingModel.update();
		}
	}
}
