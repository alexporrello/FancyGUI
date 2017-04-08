package clickables;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JCheckBox;

import colors.JMColor;
import displays.JMFrame;

/**
 * FancyCheckBox styles Java's built-in JCheckBox by 
 * increasing the font-size, changing it to Arial, 
 * and changing the font's color.
 * 
 * @author Alexander Porrello
 */
public class JMCheckBox extends JCheckBox {
	private static final long serialVersionUID = -334150206706342908L;
	
	public JMCheckBox(String name, Color color) {
		super(name);
		super.setFont(new Font("Arial", Font.PLAIN, 15));
		super.setForeground(color);
	}
	
	public JMCheckBox(String name, Color color, Boolean selected) {
		super(name);
		super.setFont(new Font("Arial", Font.PLAIN, 15));
		super.setSelected(selected);
		super.setForeground(color);
	}
	
	public static void main(String[] args) {
		JMFrame frame = new JMFrame();
		frame.setDefaultCloseOperation(JMFrame.EXIT_ON_CLOSE);
		frame.add(new JMCheckBox("Dog", JMColor.ACCENT));
		frame.pack();
		frame.setVisible(true);
	}
}
