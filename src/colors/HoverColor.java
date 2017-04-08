package colors;

import java.awt.Color;
import java.io.Serializable;

import clickables.JMButton;

/**
 * Contains a dark and a light color for classes such as {@link JMButton}
 * that require two different colors: one for the button's normal state 
 * and another for the button when it is hovered over.
 * 
 * @author Alexander Porrello
 */
public class HoverColor implements Serializable {
	private static final long serialVersionUID = 7293875703408040205L;
	
	public static HoverColor GREEN  = new HoverColor(JMColor.DARK_GREEN, JMColor.LIGHT_GREEN);
	public static HoverColor BLUE   = new HoverColor(JMColor.DARK_BLUE, JMColor.LIGHT_BLUE);
	public static HoverColor RED    = new HoverColor(JMColor.DARK_RED, JMColor.LIGHT_RED);
	public static HoverColor ORANGE = new HoverColor(JMColor.DARK_ORANGE, JMColor.LIGHT_ORANGE);
	public static HoverColor GREY   = new HoverColor(JMColor.DARK_GRAY, JMColor.LIGHT_GRAY);
	
	public Color light;
	public Color dark;
	public Color focus;
	
	/**
	 * @param dark  is the dark color.
	 * @param light is the light color.
	 */
	public HoverColor(Color dark, Color light) {
		this.light = light;
		this.dark  = dark;
		this.focus = dark;
	}
	
	/**
	 * @param dark  is the dark color.
	 * @param light is the light color.
	 */
	public HoverColor(String dark, String light) {
		this.light = Color.decode(light);
		this.dark  = Color.decode(dark);
		this.focus = Color.decode(dark);
	}
}
