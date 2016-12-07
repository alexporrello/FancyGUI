package displays;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import text.FancyLabel;
import colors.FancyColor;

public class FancyDivider extends FancyLabel {
	private static final long serialVersionUID = 7182851391064996451L;

	private int dividerWidth = 1;
	
	private Color dividerColor = FancyColor.LIGHT_GRAY;
	
	public Boolean fillHeight = false;
	
	public FancyDivider() {
		
	}
	
	public void setDividerWidth(int dividerWidth) {
		this.dividerWidth = dividerWidth;
		this.repaint();
	}
	
	public void setDividerColor(Color dividerColor) {
		this.dividerColor = dividerColor;
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D gg = (Graphics2D) g;
		
		gg.setColor(dividerColor);
		
		int dividerHeight;
		
		if(fillHeight) {
			dividerHeight = getHeight();
		} else {
			dividerHeight = getHeight()-20;
		}
		
		gg.fillRect(0, (getHeight()-dividerHeight)/2, dividerWidth, dividerHeight);
	}
		
	
}
