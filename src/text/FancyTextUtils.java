package text;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.text.JTextComponent;

public class FancyTextUtils {

	/**
	 * Sets the foreground and the background color.
	 * @param background is the background color.
	 * @param foreground is the foreground color.
	 */
	public static void setColor(JTextComponent c, Color background, Color foreground) {			
		c.setBackground(background);
		c.setForeground(foreground);
		c.setCaretColor(foreground);
	}

	/**
	 * Sets the border: a 'padding' of 5 and then a line.
	 * @param color is the color of the border to be added.
	 * @param top is the width of the top line.
	 * @param left is the width of the left line.
	 * @param bot is the width of the bottom line.
	 * @param right is the width of the right line.
	 */
	public static void setBorder(JTextComponent c, Color color, int top, int left, int bot, int right) {
		c.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(top, left, bot, right, color),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}

	/**
	 * Once this size is set, the component will not resize.
	 * @param width is the width to be set.
	 * @param height is the height to be set.
	 */
	public static void setFixedSize(JTextComponent c, int width, int height) {
		c.setPreferredSize(new Dimension(width, height));
		c.setMinimumSize(c.getPreferredSize());
		c.setMaximumSize(c.getPreferredSize());
	}

	
	
	/** Changes the font size of a JTextComponent's **/
	public static void setFontSize(JTextComponent c, int fontSize) {
		c.setFont(new Font(c.getFont().getName(), fontSize, c.getFont().getStyle()));
	}

	/** Changes the name of a JTextComponent's font **/
	public static void setFont(JTextComponent c, String fontName) {
		c.setFont(new Font(c.getFont().getName(), c.getFont().getSize(), c.getFont().getStyle()));
	}

	/** Changes a JTextComponent's font's style **/
	public static void setFontStyle(JTextComponent c, int fontStyle) {
		c.setFont(new Font(c.getFont().getName(), c.getFont().getSize(), fontStyle));
	}

}
