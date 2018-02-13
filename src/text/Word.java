package text;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 * A word contains a font, x and y coordinates, and the word's width/height in pixels at that font size.
 * @author Alexander Porrello
 */
public class Word implements Serializable {
	private static final long serialVersionUID = -7591184186473692109L;

	/** The font that will be used to determine this word's size **/
	private Font font;
	
	/** The string that is this word **/
	public String word;
	
	/** The word's style **/
	public int style;
	
	/** The word's width **/
	public int width;
	
	/** The word's height **/
	public int height;
	
	/** The word's x coordinate **/
	public int x = 0;
	
	/** The word's y coordinate **/
	public int y = 0;
	
	
	public Word(String word, Font font) {
		this.word = word;
		
		setFont(font);
	}
	
	/** Changes the word's font **/
	public void setFont(Font font) {
		this.font = font;
				
		Dimension size = getWordSize();
		
		width  = size.width;
		height = size.height;
	}
	
	/** Calculates the size of a word when drawn **/
	private Dimension getWordSize() {
		AffineTransform   affinetransform = new AffineTransform();    
		FontRenderContext frc             = new FontRenderContext(affinetransform,true,true);  
		int               width           = (int)(font.getStringBounds(word, frc).getWidth());
		int               height          = (int)(font.getStringBounds(word, frc).getHeight());

		return new Dimension(width, height);
	}
	
	/** Returns the number of characters in the word **/
	public int numCharacters() {
		return word.length();
	}
	
	public Font getFont() {
		return font;
	}
}