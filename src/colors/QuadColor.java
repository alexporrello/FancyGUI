package colors;

import java.awt.Color;
import java.io.Serializable;

/**
 * For use mostly when you have four shades of
 * one color. Clears up clutter.
 * 
 * @author Alexander Porrello
 */
public class QuadColor implements Serializable {
	private static final long serialVersionUID = -260752348471659773L;
	
	public Color darkest;
	
	public Color dark;
	
	public Color light;
	
	public Color lightest;
	
	public QuadColor(String darkest, String dark, String light, String lightest) {
		this.darkest  = Color.decode(darkest);
		this.dark     = Color.decode(dark);
		this.light    = Color.decode(light);
		this.lightest = Color.decode(lightest);
	}
	
	public QuadColor(Color darkest, Color dark, Color light, Color lightest) {
		this.darkest  = darkest;
		this.dark     = dark;
		this.light    = light;
		this.lightest = lightest;
	}
}
