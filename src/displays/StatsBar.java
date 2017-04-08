package displays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 * A bar that can be filled as needed.
 * @author Alexander Porrello
 */
public class StatsBar extends JMPanel {
	private static final long serialVersionUID = 1L;

	int current;
	int total;
	
	Color background;
	Color foreground;
	Color fontColor;

	Font toPaint = new Font("Arial", Font.ITALIC, 12);

	public StatsBar(Color background, Color foreground, Color fontColor, int current, int total) {
		this.current = current;
		this.total   = total;

		setPreferredSize(new Dimension(56, 18));
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D gg = (Graphics2D) g;
		gg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		gg.setColor(background);
		gg.fillRect(0, 0, getWidth(), getHeight());

		int xposn = (int) ((double) getWidth() * ((double) current/(double) total));

		gg.setColor(foreground);
		gg.fillRect(xposn, 0, getWidth(), getHeight());

		Word   toDraw = new Word(current + "/" + total);
		double width  = ((double) (getWidth()-toDraw.width))/2;
		double height = ((double) (getHeight()+toDraw.height))/2;

		gg.setFont(toPaint);
		gg.setColor(fontColor);
		gg.drawString(toDraw.word, (int) width, (int) height);
	}

	public class Word {

		String word    = "";
		int    width   = 0;
		int    height  = 0;

		public Word(String word) {
			this.word = word;

			updateSize();
		}

		public void updateSize() {
			String toDraw = current + "/" + total;			
			AffineTransform   affinetransform = new AffineTransform();    
			FontRenderContext frc             = new FontRenderContext(affinetransform,true,true);  

			this.width  = (int)(toPaint.getStringBounds(toDraw, frc).getWidth());
			this.height = (int)(toPaint.getStringBounds(toDraw, frc).getHeight());
		}
	}
}