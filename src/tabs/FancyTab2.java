package tabs;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class FancyTab2 {

	public String text;

	public int x;
	public int y;
	public int width;
	public int height;

	public JComponent content;

	public int index;

	public FancyTab2(String title, int width, int height, 
			int posn, JComponent content) {
		this.text     = title;
		this.width    = width;
		this.height   = height;
		this.content  = content;
		this.index     = posn;
		this.y        = 0;
		this.x        = determineXPosition();
	}

	public int determineXPosition() {
		return (index * width) + index;
	}

	/**
	 * Checks if a given point is contained within this tab.
	 * @param p the point to check.
	 * @return true if the tab contains the point; else, false
	 */
	public boolean contains(Point p) {
		return new Rectangle(x, y, width, height).contains(
				new Point(p.x, p.y));
	}
}
