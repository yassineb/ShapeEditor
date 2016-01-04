package figures.creationListeners;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

import figures.Drawing;
import figures.Star;
import figures.RegularPolygon;

/**
 * Listener permettant d'enchainer les actions souris pour créer des formes
 * rectangulaires comme des rectangles ou des ellipse (evt des cercles):
 * <ol>
 * 	<li>bouton 1 pressé et maintenu enfoncé</li>
 * 	<li>déplacement de la souris avec le bouton enfoncé</li>
 * 	<li>relachement du bouton</li>
 * </ol>
 * @author davidroussel
 */
public class RectangularNShapeCreationListener extends AbstractCreationListener
{
	/**
	 * Constructeur d'un listener à deux étapes: pressed->drag->release pour
	 * toutes les figures à caractère rectangulaire (Rectangle, Ellipse, evt
	 * Cercle)
	 *
	 * @param model le modèle de dessin à modifier par ce creationListener
	 * @param tipLabel le label dans lequel afficher les conseils utilisateur
	 */
	public RectangularNShapeCreationListener(Drawing model, JLabel tipLabel)
	{
		super(model, tipLabel, 3);

		tips[0] = new String("Cliquez et maintenez enfoncé pour initier la figure");
		tips[1] = new String("Relâchez pour terminer la figure");
		tips[2] = new String("Cliquez pour terminer la figure");

		updateTip();

		System.out.println("RectangularNShapeCreationListener created");
	}

	/**
	 * Création d'une nouvelle figure rectangulaire de taille 0 au point de
	 * l'évènement souris, si le bouton appuyé est le bouton gauche.
	 *
	 * @param e l'évènement souris
	 * @see AbstractCreationListener#startFigure(MouseEvent)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		/*
		 * si c'est le bouton 1 qui est pressé on démarre la figure
		 */
		if ((e.getButton() == MouseEvent.BUTTON1) && currentStep == 0) {
			startFigure(e);
		}
	}
	

	/**
	 * Terminaison de la nouvelle figure rectangulaire si le bouton appuyé
	 * était le bouton gauche
	 * @param e l'évènement souris
	 * @see AbstractCreationListener#endFigure(MouseEvent)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		/*
		 * si c'est le bouton 1 qui est relaché on termine la figure
		 */
		if (currentStep == 2)
		{
			nextStep();
		}
		if ((e.getButton() == MouseEvent.BUTTON1) && (currentStep == 1))
		{
			nextStep();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		// Rien
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e)
	{
		// Rien
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e)
	{
		// Rien
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e)
	{
		if(currentStep == 2){
			if(((Object) currentFigure).getClass()==(RegularPolygon.class)){
				RegularPolygon polygon = (RegularPolygon) currentFigure;
				polygon.setN(e.getPoint());
				drawingModel.update();
			}
			else{
				Star polygon = (Star) currentFigure;
				polygon.setN(e.getPoint());
				drawingModel.update();
			}
		}
	}

	/**
	 * Déplacement du point en bas à droite de la figure rectangulaire, si
	 * l'on se trouve �  l'étape 1 (après initalisation de la figure) et que
	 * le bouton enfoncé est bien le bouton gauche.
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e)
	{
		/*
		 *  
		 * Si on est à l'étape 1 : on est en train de tirer le pointeur après
		 * avoir appuyé sur le bouton 1
		 * 	On déplace le dernier point de la figure
		 * 	On met à jour le modèle de dessin
		 */
		if (currentStep == 1){
			if(((Object) currentFigure).getClass()==(RegularPolygon.class)){
				RegularPolygon poly = (RegularPolygon) currentFigure;
				currentFigure.setLastPoint(e.getPoint());
				poly.setSize(e.getPoint());
				
				drawingModel.update();
			}
			else{
				Star poly = (Star) currentFigure;
				currentFigure.setLastPoint(e.getPoint());
				poly.setSize(e.getPoint());
				
				drawingModel.update();
			}
		}
		
	}

}