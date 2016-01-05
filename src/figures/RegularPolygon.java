package figures;

import java.awt.BasicStroke;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import figures.enums.FigureType;

/**
 * Classe RegularPolygon pour les {@link Figure}
 * @uml.dependency supplier="java.awt.geom.Ellipse2D.Float"
 */
public class RegularPolygon extends Figure
{
	/**
	 * Le rayon par défaut pour un polygone
	 */
	public final static int DEFAULT_N = 5;
	public final static int DEFAULT_ANGLE = 0;
	public final static int DEFAULT_SIZE = 20;

	/**
	 * Le compteur d'instance des cercles. Utilisé pour donner un numéro
	 * d'instance après l'avoir incrémenté
	 */
	private static int counter = 0;
	
	private Point2D center;
	private double size;
	private double angle;
	private int n;

	/**
	 * Constructeur valué d'un cercle
	 * 
	 * @param stroke le type du trait de la bordure
	 * @param edge la couleur de la bordure
	 * @param fill la couleur de remplissage
	 * @param center le centre du cercle
	 * @param rayon le rayon du cercle
	 */
	public RegularPolygon(BasicStroke stroke, Paint edge, Paint fill, Point2D center)
	{
		super(stroke, edge, fill);
		instanceNumber = ++counter;
		this.center = center;
		this.size = DEFAULT_SIZE;
		this.angle = DEFAULT_ANGLE;
		this.n = DEFAULT_N;
		draw();
	}

	private void draw(){
		int xPolygon[] = new int[n];
		int yPolygon[] = new int[n];
		
		double theta = (2 * Math.PI / n);
		for (int i = 0; i < n; ++i) {
		    xPolygon[i] = (int) (center.getX() + size*Math.cos(theta * i + angle));
		    yPolygon[i] = (int) (center.getY() + size*Math.sin(theta * i + angle));
		}

		shape = new Polygon(xPolygon, yPolygon, xPolygon.length);
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
		size = p.distance(center);
		draw();
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
 		return FigureType.REGULAR_POLYGON;
 	}

	public void setSize(Point p) {
		size = p.distance(center);
		angle = Math.atan2(p.y - center.getY(), p.x - center.getX());
		draw();
	}

	public void setN(Point p) {
		int n = (int) (p.distance(center)-size) / 10 + DEFAULT_N;
		this.n = (n <= 3) ? 3 : n;
		draw();
	}
}