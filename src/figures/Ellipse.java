package figures;

import java.awt.BasicStroke;
import java.awt.Paint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import figures.enums.FigureType;

/**
 * Classe de Ellipse pour les {@link Figure}
 * 
 * @author davidroussel
 * @uml.dependency supplier="java.awt.geom.Ellipse2D.Float"
 */
public class Ellipse extends Figure
{
	/**
	 * Le compteur d'instance des ellipses. Utilisé pour donner un numéro
	 * d'instance après l'avoir incrémenté
	 */
	private static int counter = 0;

	/**
	 * Création d'un ellipse avec les points en haut à gauche et en bas à droite
	 * 
	 * @param stroke le type de trait
	 * @param edge la couleur du trait
	 * @param fill la couleur de remplissage
	 * @param topLeft le point en haut à gauche
	 * @param bottomRight le point en bas à droite
	 */
	public Ellipse(BasicStroke stroke, Paint edge, Paint fill, Point2D topLeft,
			Point2D bottomRight)
	{
		super(stroke, edge, fill);
		instanceNumber = ++counter;
		float x = (float) topLeft.getX();
		float y = (float) topLeft.getY();
		float w = (float) (bottomRight.getX() - x);
		float h = (float) (bottomRight.getY() - y);
		shape = new Ellipse2D.Float(x, y, w, h);
	}

	/**
	 * Déplacement du point inférieur droit de l'ellipse
	 * 
	 * @param p le point où placer le dernier point (point inférieur droit)
	 * @see figures.Figure#setLastPoint(Point2D)
	 */
	@Override
	public void setLastPoint(Point2D p)
	{
		if (shape != null)
		{
			Ellipse2D.Float ellipse = (Ellipse2D.Float) shape;
			float newWidth = (float) (p.getX() - ellipse.x);
			float newHeight = (float) (p.getY() - ellipse.y);
			ellipse.width = newWidth;
			ellipse.height = newHeight;
		}
	}

	/**
	 * Obtention du barycentre de la figure.
	 * 
	 * @return le point correspondant au barycentre de la figure
	 */
	@Override
	public Point2D getCenter()
	{
		Rectangle2D bounds = shape.getBounds2D();

		return new Point2D.Double(bounds.getCenterX(), bounds.getCenterY());
	}
	
 	/**
 	 * Accesseur du type de figure selon {@link FigureType}
 	 * @return le type de figure
 	 */
 	@Override
	public FigureType getType()
 	{
 		return FigureType.ELLIPSE;
 	}

}
