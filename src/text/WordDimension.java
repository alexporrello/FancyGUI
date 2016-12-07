package text;

import java.awt.Dimension;
import java.io.Serializable;

/**
 * A glorified version of java's built-in Dimension.
 * @author Alexander Porrello
 */
public class WordDimension extends Dimension implements Serializable {
	private static final long serialVersionUID = 4915449771336367037L;
	
	public WordDimension(int width, int height) {
		super.width  = width;
		super.height = height;
	}
}