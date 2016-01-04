package filters;

import figures.Figure;
import figures.enums.FigureType;

/**
 * Filtre de figure appliqué au type de figure
 * @author davidroussel
 */
public class ShapeFilter extends FigureFilter<FigureType>
{
	public ShapeFilter() {
		super();
	}

	public ShapeFilter(FigureType element) {
		super(element);
	}

	@Override
	public boolean test(Figure f) {
		return this.getElement().equals(f.getType());
	}
}
