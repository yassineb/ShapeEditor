package figures.creationListeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JLabel;

import figures.Drawing;
import figures.Figure;

public class MoveShapeListener extends AbstractCreationListener {
	/**
	 * Constructeur d'un Listener pour créer un polygon en plusieurs clicks
	 *
	 * @param model le modèle de dessin à modifier
	 * @param infoLabel le label dans lequel afficher les conseils utilisateurs
	 */
	
	Figure figure;
	
	public MoveShapeListener(Drawing model, JLabel infoLabel)
	{
		super(model, infoLabel, 2);

		tips[0] = new String("Clic gauche pour commencer le polygone");
		tips[1] = new String("clic gauche pour ajouter / droit pour terminer");

		updateTip();

		System.out.println("MoveShapeListener created");
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		Figure f = drawingModel.getFigureAt(new Point(arg0.getX(), arg0.getY()));
		if (f != null) {
				System.out.println(f.getName());
		}
		System.out.println("Start: " + arg0.getX() + " " + arg0.getY());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (figure != null) {
			
		}
		System.out.println("Dragging" + arg0.getX() + " " + arg0.getY());
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
