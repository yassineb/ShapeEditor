package figures;

import java.awt.BasicStroke;
import java.awt.Paint;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import figures.enums.FigureType;

/**
 * Une classe représentant les ligne polygonales composées de 2 ou + de points
 * @author davidroussel
 */
public class Polygon extends Figure
{
	/**
	 * Le compteur d'instance des cercles.
	 * Utilisé pour donner un numéro d'instance après l'avoir incrémenté
	 */
	private static int counter = 0;

	/**
	 * Constructeur valué d'une ligne polyonale à partir d'un style de ligne,
	 * d'une couleur et des deux premiers point de la ligne
	 * @param stroke le style de la ligne
	 * @param edgeColor la couleur de la ligne
	 * @param fillColor la couleur de remplissage
	 * @param point1 le premier point de la ligne
	 * @param point2 le second point de la ligne
	 */
	public Polygon(BasicStroke stroke, Paint edgeColor, Paint fillColor,
		Point point1, Point point2)
	{
		super(stroke, edgeColor, fillColor);
		instanceNumber = ++counter;

		java.awt.Polygon poly = new java.awt.Polygon();
		poly.addPoint(point1.x, point1.y);
		poly.addPoint(point2.x, point2.y);
		shape = poly;
	}

	/**
	 * Ajout d'un point au polygone
	 * @param x l'abcisse du point à ajouter
	 * @param y l'ordonnée du point à ajouter
	 */
	public void addPoint(int x, int y)
	{
		java.awt.Polygon poly = (java.awt.Polygon) shape;
		poly.addPoint(x, y);
	}

	/**
	 * Suppression du dernier point du polygone.
	 * Uniquement s'il y en a plus d'un
	 */
	public void removeLastPoint()
	{
		java.awt.Polygon poly = (java.awt.Polygon) shape;

		if (poly.npoints > 1)
		{
			// Sauvegarde des coords des points - le dernier
			int[] xs = new int[poly.npoints-1];
			int[] ys = new int[poly.npoints-1];
			for (int i = 0; i < xs.length; i++)
			{
				xs[i] = poly.xpoints[i];
				ys[i] = poly.ypoints[i];
			}

			// reset poly
			poly.reset();

			// Reajout des points sauvegardés
			for (int i = 0; i < xs.length; i++)
			{
				poly.addPoint(xs[i], ys[i]);
			}
		}
	}

	/**
	 * Déplacement du dernier point du polygone
	 * @param p la position du dernier point
	 * @see lines.AbstractLine#setLastPoint(Point2D)
	 */
	@Override
	public void setLastPoint(Point2D p)
	{
		java.awt.Polygon poly = (java.awt.Polygon) shape;
		int lastIndex = poly.npoints-1;
		if (lastIndex >= 0)
		{
			poly.xpoints[lastIndex] = Double.valueOf(p.getX()).intValue();
			poly.ypoints[lastIndex] = Double.valueOf(p.getY()).intValue();
		}
	}

	/**
	 * Obtention du barycentre de la figure.
	 * @return le point correspondant au barycentre de la figure
	 */
	@Override
	public Point2D getCenter()
	{
		Rectangle2D bounds = shape.getBounds2D();
		return new Point2D.Double(bounds.getCenterX(), bounds.getCenterY());
		
		/*
		java.awt.Polygon poly = (java.awt.Polygon) shape;	=> Can't cast Path2D to Polygon

		float xm = 0.0f;
		float ym = 0.0f;

		if (poly.npoints > 0)
		{
			for (int i = 0; i < poly.npoints; i++)
			{
				xm += poly.xpoints[i];
				ym += poly.ypoints[i];
			}

			xm /= poly.npoints;
			ym /= poly.npoints;
		}

		return new Point2D.Float(xm, ym);*/
	}
	
 	/**
 	 * Accesseur du type de figure selon {@link FigureType}
 	 * @return le type de figure
 	 */
 	@Override
	public FigureType getType()
 	{
 		return FigureType.POLYGON;
 	}
}
