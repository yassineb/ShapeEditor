package figures;

import java.awt.BasicStroke;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

import figures.enums.FigureType;

/**
 * Figure correspondant aux rectangle à coins arrondis
 * @author davidroussel
 */
public class RoundedRectangle extends Rectangle
{
	/**
	 * Le compteur d'instance des rectangles à coins arrondis.
	 * Utilisé pour donner un numéro d'instance après l'avoir incrémenté
	 */
	private static int counter = 0;

	/**
	 * Constructeur d'un rectangle à coins arrondis
	 * @param stroke le type de trait
	 * @param edge la couleur du trait
	 * @param fill la couleur de remplissage
	 * @param topLeft le point en haut à gauche
	 * @param bottomRight le point en bas à droite
	 * @param arcSize la taille de l'arrondi des coins
	 */
	public RoundedRectangle(BasicStroke stroke, Paint edge, Paint fill,
			Point2D topLeft, Point2D bottomRight, int arcSize)
	{
		super(stroke, edge, fill);
		instanceNumber = ++counter;
		float x = (float) topLeft.getX();
		float y = (float) topLeft.getY();
		float width = (float) (bottomRight.getX() - x);
		float height = (float) (bottomRight.getY() - y);
		float minDim = (width < height ? width : height) / 2.0f;
		float actualArcSize = (arcSize < minDim ? arcSize : minDim);
		shape = new RoundRectangle2D.Float(x, y, width, height, actualArcSize,
				actualArcSize);

		// System.out.println("Rounded Rectangle created");
	}

	/*
	 * (non-Javadoc)
	 * @see figures.AbstractFigure#setLastPoint(Point2D)
	 */
	@Override
	public void setLastPoint(Point2D p)
	{
		RoundRectangle2D.Float rect = (RoundRectangle2D.Float) shape;
		rect.width = (float) (p.getX() - rect.x);;
		rect.height = (float) (p.getY() - rect.y);
	}

	/**
	 * Mise en place de la taille de l'arc en fonction de la position
	 * d'un point par rapport au coin inférieur droit
	 * @param p le point déterminant la taille de l'arc
	 */
	public void setArc(Point2D p)
	{
		RoundRectangle2D.Float rect = (RoundRectangle2D.Float)shape;

		double bottomRightX = rect.getMaxX();
		double bottomRightY = rect.getMaxY();
		double x = p.getX();
		double y = p.getY();

		if (x > bottomRightX)
		{
			if (y < bottomRightY)
			{
				rect.arcwidth = (float) (bottomRightY - y);
				rect.archeight = rect.arcwidth;
			}
			else
			{
				rect.arcwidth = 0;
				rect.archeight = 0;
			}
		}
		else
		{
			if (y > bottomRightY)
			{
				rect.arcwidth = (float) (bottomRightX - x);
				rect.archeight = rect.arcwidth;
			}
			else
			{
				rect.arcwidth = 0;
				rect.archeight = 0;
			}
		}
	}
	
 	/**
 	 * Accesseur du type de figure selon {@link FigureType}
 	 * @return le type de figure
 	 */
 	@Override
	public FigureType getType()
 	{
 		return FigureType.ROUNDED_RECTANGLE;
 	}
}
