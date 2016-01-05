package figures.creationListeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JLabel;

import figures.Drawing;
import figures.Figure;
import figures.enums.FigureType;

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

		tips[0] = new String("Drag/drop une figure pour la déplacer");
		tips[1] = new String("Relâchez pour terminer");

		updateTip();

		System.out.println("MoveShapeListener created");
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
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
		figure = drawingModel.getFigureAt(arg0.getPoint());
		if (figure != null)
				nextStep();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (figure != null) {
			nextStep();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (figure != null) {
			AffineTransform t = new AffineTransform();
			t.translate(e.getX() - figure.getCenter().getX(), e.getY() - figure.getCenter().getY());
			figure.setShape(t.createTransformedShape(figure.getShape()));		
			drawingModel.update();
		}		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
