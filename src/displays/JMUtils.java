package displays;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class JMUtils {

	/**
	 * Sets the border: a 'padding' of 5 and then a line.
	 * @param color is the color of the border to be added.
	 * @param top is the width of the top line.
	 * @param left is the width of the left line.
	 * @param bot is the width of the bottom line.
	 * @param right is the width of the right line.
	 */
	public static void setBorder(JComponent c, Color color, int top, int left, int bot, int right) {
		c.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(top, left, bot, right, color),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}
	
	/**
	 * Once this size is set, the component will not resize.
	 * @param width is the width to be set.
	 * @param height is the height to be set.
	 */
	public static void setFixedSize(JComponent c, int width, int height) {
		c.setPreferredSize(new Dimension(width, height));
		c.setMinimumSize(c.getPreferredSize());
		c.setMaximumSize(c.getPreferredSize());
	}
	
	
}
