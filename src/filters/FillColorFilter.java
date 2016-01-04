/**
 * 
 */
package filters;

import java.awt.Paint;

import figures.Figure;

/**
 * Filtre filtrant les figures possédant une certaine couleur de remplissage
 * @author davidroussel
 */
public class FillColorFilter extends FigureFilter<Paint>
{
	
	public FillColorFilter() {
		super();
	}

	public FillColorFilter(Paint element) {
		super(element);
	}

	@Override
	public boolean test(Figure f) {
		return this.getElement().equals(f.getFillPaint());
	}
}
