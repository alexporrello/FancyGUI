package colors;

import java.awt.Color;

public class FancyColor extends Color {
	private static final long serialVersionUID = -4326439530864245307L;

	public static Color DARK_GREEN		= Color.decode("#669900");
	public static Color LIGHT_GREEN		= Color.decode("#99CC00");
	
	public static Color DARK_BLUE		= Color.decode("#33B5E5");
	public static Color LIGHT_BLUE		= Color.decode("#0099CC");
	
	public static Color DARKER_GRAY     = FancyColor.decode("#d4d4d4");
	public static Color DARK_GRAY		= Color.decode("#c2c2c2");
	public static Color LIGHT_GRAY		= Color.decode("#d6d6d6");
	
	public static Color DARK_ORANGE		= Color.decode("#FF8800");
	public static Color LIGHT_ORANGE	= Color.decode("#FFBB33");
	
	public static Color DARK_RED		= Color.decode("#CC0000");
	public static Color LIGHT_RED		= Color.decode("#FF4444");
	
	public static Color DARK_FONT		= Color.decode("#2a2a2a");
	public static Color BLACK           = Color.BLACK;
	
	public static Color WHITE			= Color.decode("#ffffff");
	public static Color NEAR_WHITE 		= Color.decode("#fcfcfc");
	
	public static Color SELECTED_BLUE   = Color.decode("#ade1f4");
	public static Color BLUE_BORDER		= Color.decode("#33b5e5");
	
	public static final Color ACCENT = FancyColor.decode("#1883d7");
	
	public static Color DARK_GUI_BLUE_ACCENT      = Color.decode("#3399cc");
	public static Color DARK_GUI_FOCUSED_WINDOW   = Color.decode("#262626");
	public static Color DARK_GUI_UNFOCUSED_WINDOW = Color.decode("#1a1a1a");
	public static Color DARK_GUI_LIGHT_BUTTON     = Color.decode("#656565");
	
	
	public FancyColor(int arg0) {
		super(arg0);
	}
	
	public static Color getColorFromString(String s) {
		if(s.endsWith("Red")) {
			return FancyColor.DARK_RED;
		} else if(s.endsWith("Black")) {
			return FancyColor.BLACK;
		} else if(s.endsWith("Blue")) {
			return FancyColor.DARK_BLUE;
		} else if(s.endsWith("Gray")) {
			return FancyColor.DARK_GRAY;
		} else if(s.endsWith("Orange")) {
			return FancyColor.DARK_ORANGE;
		} else {
			return FancyColor.WHITE;
		}
	}
}