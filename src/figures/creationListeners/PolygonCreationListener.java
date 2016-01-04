package figures.creationListeners;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import figures.Figure;
import figures.Drawing;
import figures.Polygon;

/**
 * Listener permettant d'enchainer les actions souris nécessaires à la création
 * d'un polygone :
 * <ul>
 * <li>premier click avec le bouton gauche pour initier la création du polygone</li>
 * <li>les clicks suivants:
 * <ul>
 * <li>avec le bouton gauche ajoute un point au polygone</li>
 * <li>avec le bouton droit termine le polygone</li>
 * <li>avec le bouton du milieu retire le dernier point du polygone</li>
 * </ul>
 * </li>
 * <li>Une fois le polygone en cours de création, le déplacement de la souris
 * déplace le dernier point du polygone</li>
 * </ul>
 *
 * @author davidroussel
 */
public class PolygonCreationListener extends AbstractCreationListener
{

	/**
	 * Constructeur d'un Listener pour créer un polygon en plusieurs clicks
	 *
	 * @param model le modèle de dessin à modifier
	 * @param infoLabel le label dans lequel afficher les conseils utilisateurs
	 */
	public PolygonCreationListener(Drawing model, JLabel infoLabel)
	{
		super(model, infoLabel, 2);

		tips[0] = new String("Clic gauche pour commencer le polygone");
		tips[1] = new String("clic gauche pour ajouter / droit pour terminer");

		updateTip();

		System.out.println("PolygonCreationListener created");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * figures.creationListeners.AbstractCreationListener#mousePressed(java.
	 * awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		// Rien
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * figures.creationListeners.AbstractCreationListener#mouseReleased(java
	 * .awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		// Rien
	}

	/**
	 * Actions à réaliser lorsqu'un bouton de la souris est cliqué.
	 * Si l'on se trouve à l'étape 0 et que le bouton cliqué est
	 * {@link MouseEvent#BUTTON1}, on initie la figure et on passe à l'étape suivante.
	 * Dans l'étape suivante si c'est {@link MouseEvent#BUTTON1} qui est cliqué
	 * on ajoute un point, si c'est le {@link MouseEvent#BUTTON2} on supprime le
	 * dernier point ajouté et si c'est le bouton {@link MouseEvent#BUTTON3},
	 * on termine la figure.
	 * @param e l'évènement souris associé
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		Point p = e.getPoint();
		/*
		 * Initie la création d'un premier point fixé à l'endroit du click
		 * puis d'un deuxième point (créé au même endroit) qui se déplacera avec
		 * le pointeur de la souris. un nouveau click fixera ce nouveau point et
		 * en ajoutera un autre lui aussi attaché au pointeur de la souris, et
		 * ainsi de suite. Le dernier point est retiré si l'utilisateur clique
		 * avec le bouton du milieu. Le polygone est terminé si l'utilisateur
		 * clique sur le bouton droit.
		 */
		if (currentStep == 0)
		{
			if (e.getButton() == MouseEvent.BUTTON1)
			{
				// On initie le polygone
				startFigure(e);
				System.out.println("initating polygon");
			}
		}
		else
		{
			// Polygon poly = (Polygon) drawingModel.getLastFigure();
			Polygon poly = (Polygon) currentFigure;

			switch (e.getButton())
			{
				case MouseEvent.BUTTON1:
					// On ajoute un point au polygone
					poly.addPoint(p.x, p.y);
					break;
				case MouseEvent.BUTTON2:
					// On supprime le dernier point
					poly.removeLastPoint();
					break;
				case MouseEvent.BUTTON3:
					// On termine le polygone
					endFigure(e);
					break;
			}
		}

		drawingModel.update();
		updateTip();
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

	/*
	 * (non-Javadoc)
	 * @see
	 * java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent
	 * )
	 */
	@Override
	public void mouseDragged(MouseEvent e)
	{
		// Rien
	}

	/**
	 * Déplacement du dernier point du polygon si l'on se trouve à la seconde
	 * étape de la création
	 * @param e L'évènement souris associé
	 * @see
	 * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e)
	{
		/*
		 * déplacement du dernier point du polygone si l'on est toujours en
		 * cours de création du polygone, rien sinon
		 */
		if (currentStep > 0)
		{
			// AbstractFigure figure = drawingModel.getLastFigure();
			Figure figure = currentFigure;
			if (figure != null)
			{
				figure.setLastPoint(e.getPoint());
			}
			drawingModel.update();
		}
	}
}
