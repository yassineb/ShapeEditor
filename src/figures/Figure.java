package figures;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import figures.enums.FigureType;

/**
 * Classe commune à toutes les sortes de figures
 *
 * @author davidroussel
 */
public abstract class Figure
{
	/**
	 * La forme à dessiner
	 */
	protected Shape shape;

	/**
	 * Couleur du bord de la figure
	 */
	protected Paint edge;

	/**
	 * Couleur de remplissage de la figure
	 */
	protected Paint fill;

	/**
	 * Caractéristiques de la bordure des figure : épaisseur, forme des
	 * extremités et [evt] forme des jointures
	 */
	protected BasicStroke stroke;
	
	
	protected AffineTransform transform = null;

	/**
	 * Le numéro d'instance de cette figure.
	 * 1 si c'est la première figure de ce type, etc.
	 */
	protected int instanceNumber;

	/**
	 * Constructeur d'une figure abstraite à partir d'un style de ligne d'une
	 * couleur de bordure et d'une couleur de remplissage. Les styles de lignes
	 * et les couleurs étant souvent les même entre les différentes figures ils
	 * devront être fournis par un flyweight. Le stroke, le edge et le fill
	 * peuvent chacun être null.
	 *
	 * @param stroke caractéristiques de la ligne de bordure
	 * @param edge couleur de la ligne de bordure
	 * @param fill couleur (ou gradient de couleurs) de remplissage
	 */
	protected Figure(BasicStroke stroke, Paint edge, Paint fill)
	{
		this.stroke = stroke;
		this.edge = edge;
		this.fill = fill;
		shape = null;
		transform = null;
	}

	/**
	 * Déplacement du dernier point de la figure (utilisé lors du dessin d'une
	 * figure tant que l'on déplace le dernier point)
	 *
	 * @param p la nouvelle position du dernier point
	 */
	public abstract void setLastPoint(Point2D p);

	/**
	 * Dessin de la figure dans un contexte graphique fournit par le système.
	 * Met en place le stroke et les couleur, puis dessine la forme géométrique
	 * correspondant à la figure (figure remplie d'abord si le fill est non
	 * null, puis bordure si le edge est non null)
	 *
	 * @param g2D le contexte graphique
	 */
	public final void draw(Graphics2D g2D)
	{
		if (fill != null)
		{
			g2D.setPaint(fill);
			g2D.fill(shape);
		}
		if ((edge != null) && (stroke != null))
		{
			g2D.setStroke(stroke);
			g2D.setPaint(edge);
			if (transform != null)
				g2D.translate(transform.getTranslateX(), transform.getTranslateY());
			else
				g2D.translate(0, 0);
			
			g2D.draw(shape);
		}
	}

	/**
	 * Obtention du nom de la figure. Le nom d'une figure est composé de son
	 * type suivi par le numéro de l'instance de ce type
	 *
	 * @return le nom de la figure
	 */
	public String getName()
	{
		return new String(getClass().getSimpleName() + " " + instanceNumber);
	}

	/**
	 * Obtention du rectangle englobant de la figure.
	 * Obtenu grâce au {@link Shape#getBounds2D()}
	 * @return le rectangle englobant de la figure
	 */
	public Rectangle2D getBounds2D()
	{
		return shape.getBounds2D();
	}

	/**
	 * Obtention du barycentre de la figure.
	 * @return le point correspondant au barycentre de la figure
	 */
	public abstract Point2D getCenter();

	/**
	 * Teste si le point p est contenu dans cette figure.
	 * Utilise {@link Shape#contains(Point2D)}
	 * @param p le point dont on veut tester s'il est contenu dans la figure
	 * @return true si le point p est contenu dans la figure, false sinon
	 */
 	public boolean contains(Point2D p)
 	{
 		return shape.contains(p);
 	}

 	/**
 	 * Accesseur du type de figure selon {@link FigureType}
 	 * @return le type de figure
 	 */
 	public abstract FigureType getType();
 	
 	/**
 	 * Accesseur en lecture de la forme interne
 	 * @return la forme interne
 	 */
	public Shape getShape()
	{
		return shape;
	}

	/**
	 * Accesseur en lecture du {@link Paint} du contour
	 * @return le {@link Paint} du contour
	 */
	public Paint getEdgePaint()
	{
		return edge;
	}

	/**
	 * Accesseur en lecture du {@link Paint} du remplissage
	 * @return le {@link Paint} du remplissage
	 */
	public Paint getFillPaint()
	{
		return fill;
	}

	/**
	 * Accesseur en lecture du {@link BasicStroke} du contour
	 * @return le {@link BasicStroke} du contour
	 */
	public BasicStroke getStroke()
	{
		return stroke;
	}

	public AffineTransform getTransform() {
		return transform;
	}

	public void setTransform(AffineTransform transform) {
		this.transform = transform;
	}

	public void setShape(Shape shape) {
		this.shape = shape;		
	}	
}
