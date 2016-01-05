package figures;

import java.awt.BasicStroke;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import figures.enums.FigureType;

/**
 * Classe de Figure pour les cercles
 * 
 * @author davidroussel
 * @uml.dependency supplier="java.awt.geom.Ellipse2D.Float"
 */
public class Circle extends Figure
{
	/**
	 * Le rayon par défaut pour un cercle
	 */
	public final static float DEFAULT_RAYON = 2.0f;

	/**
	 * Le compteur d'instance des cercles. Utilisé pour donner un numéro
	 * d'instance après l'avoir incrémenté
	 */
	private static int counter = 0;

	/**
	 * Constructeur valué d'un cercle
	 * 
	 * @param stroke le type du trait de la bordure
	 * @param edge la couleur de la bordure
	 * @param fill la couleur de remplissage
	 * @param center le centre du cercle
	 * @param rayon le rayon du cercle
	 */
	public Circle(BasicStroke stroke, Paint edge, Paint fill, Point2D center,
			float rayon)
	{
		super(stroke, edge, fill);
		instanceNumber = ++counter;
		float width = rayon * 2.0f;
		float height = width;
		float x = (float) (center.getX() - rayon);
		float y = (float) (center.getY() - rayon);
		shape = new Ellipse2D.Float(x, y, width, height);

		// System.out.println("Cercle created");
	}

	/**
	 * Déplacement du dernier point de la ligne (utilisé lors du dessin d'un
	 * cercle pour faire varier le centre et le rayon tant que l'on déplace un
	 * point)
	 * 
	 * @param p la nouvelle position du dernier point
	 * @see figures.Figure#setLastPoint(Point2D)
	 */
	@Override
	public void setLastPoint(Point2D p)
	{
		Ellipse2D.Float ellipse = (Ellipse2D.Float) shape;
		float newWidth = (float) (p.getX() - ellipse.x);
		float newHeight = (float) (p.getY() - ellipse.y);
		float size = (Math.abs(newWidth) < Math.abs(newHeight) ? newWidth
				: newHeight);
		ellipse.width = size;
		ellipse.height = size;
	}

	/**
	 * Obtention du barycentre de la figure.
	 * 
	 * @return le point correspondant au barycentre de la figure
	 */
	@Override
	public Point2D getCenter()
	{
		Shape ellipse = shape;

		return new Point2D.Float((float) ellipse.getBounds2D().getCenterX(),
				(float) ellipse.getBounds2D().getCenterY());
	}
	
 	/**
 	 * Accesseur du type de figure selon {@link FigureType}
 	 * @return le type de figure
 	 */
 	@Override
	public FigureType getType()
 	{
 		return FigureType.CIRCLE;
 	}
}
