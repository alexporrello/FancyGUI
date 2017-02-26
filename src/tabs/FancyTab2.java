package tabs;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.JComponent;

import text.Word;

public class FancyTab2 {

	public String text;

	public String drawText;
	
	public int x;
	public int y;
	public int width;
	public int height;

	public Rectangle xClickPosn;
	
	public JComponent content;

	public int index;

	public Consumer<MouseEvent> listener;
	
	public FancyTab2(String title, int width, int height, 
			int posn, Font theFont, JComponent content) {
		this.text       = title;
		this.width      = width;
		this.height     = height;
		this.content    = content;
		this.index      = posn;
		this.y          = 0;
		this.x          = determineXPosition();
		this.xClickPosn = determineXClickPosn();
		
		setDrawString(theFont);
	}
	
	private void setDrawString(Font font) {
		Word word = new Word(text, font);
		
		int sub = 0;
		
		while(word.width > (width / 4) * 3) {
			word = new Word(text.substring(0, text.length() + -sub++) + "...", font);
		}
		
		drawText = word.word;
	}
	
	public int determineXPosition() {
		return (index * width) + index;
	}
	
	public Rectangle determineXClickPosn() {
		int leftX  = x + ((width/7)*6);
		int leftY  = 7;
		int width  = this.width/15;
		int height = this.height-(this.height/3);
		
		return new Rectangle(leftX, leftY, width, height);
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
