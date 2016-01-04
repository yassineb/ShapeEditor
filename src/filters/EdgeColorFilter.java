/**
 * 
 */
package filters;

import java.awt.Paint;

import figures.Figure;

/**
 * Filtre filtrant les figures possédant une certaine couleur de trait
 * @author davidroussel
 */
public class EdgeColorFilter extends FigureFilter<Paint>
{
	public EdgeColorFilter() {
		super();
	}

	public EdgeColorFilter(Paint element) {
		super(element);
	}

	@Override
	public boolean test(Figure f)
	{
		return this.getElement().equals(f.getEdgePaint());
	}
}
