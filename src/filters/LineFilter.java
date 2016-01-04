package filters;

import figures.Figure;
import figures.enums.LineType;

/**
 * Filtre filtrant les figures ayant un certain type de trait
 * @author davidroussel
 */
public class LineFilter extends FigureFilter<LineType>
{
	public boolean test(Figure f) {
		return this.element.equals(f.getStroke());
	}

	public LineFilter() {
		super();
	}

	public LineFilter(LineType element) {
		super(element);
	}
	
	
}
