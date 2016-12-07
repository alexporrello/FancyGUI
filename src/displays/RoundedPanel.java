package displays;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import layout.GBC;
import colors.FancyColor;

public class RoundedPanel extends FancyPanel {
	private static final long serialVersionUID = -6924681512516854970L;

	public Color border     = FancyColor.DARKER_GRAY;
	public Color background = FancyColor.DARK_GRAY;
	public Color foreground = FancyColor.LIGHT_GRAY;
	public Color shadow     = FancyColor.DARK_GRAY;

	public Boolean topRightRounded    = false;
	public Boolean bottomRightRounded = false;
	public Boolean topLeftRounded     = false;
	public Boolean bottomLeftRounded  = false;

	public int borderWidth  = 4;
	public int arcWidth     = 10;
	
	private Boolean paintFocus = false;

	private Boolean rightFocusBorder = true;
	private Boolean leftFocusBorder  = true;
	
	
	public RoundedPanel() {
		this.setOpaque(false);
	}
	
	public RoundedPanel(int borderWidth, int arcWidth) {
		this.arcWidth           = arcWidth;
		this.borderWidth        = borderWidth;
		this.setOpaque(false);
	}

	public RoundedPanel(Boolean topRight, Boolean botRight, Boolean topLeft, Boolean botLeft) {
		this.topRightRounded    = topRight;
		this.bottomRightRounded = botRight;
		this.topLeftRounded     = topLeft;
		this.bottomLeftRounded  = botLeft;
		this.setOpaque(false);
	}

	public RoundedPanel(int borderWidth, int arcWidth, Boolean topRight, Boolean botRight, Boolean topLeft, Boolean botLeft) {
		this.arcWidth           = arcWidth;
		this.borderWidth        = borderWidth;
		this.topRightRounded    = topRight;
		this.bottomRightRounded = botRight;
		this.topLeftRounded     = topLeft;
		this.bottomLeftRounded  = botLeft;
		this.setOpaque(false);
	}
	
	public void setRoundedCorners(Boolean topRight, Boolean botRight, Boolean topLeft, Boolean botLeft) {
		this.topRightRounded    = topRight;
		this.bottomRightRounded = botRight;
		this.topLeftRounded     = topLeft;
		this.bottomLeftRounded  = botLeft;
		repaint();
	}

	public void addSingleComponent(JComponent toAdd) {
		GBC.addWithGBC(this, toAdd, 1.0, 1.0, 0, 0, GBC.BOTH, GBC.NORTH, GBC.insets(5, 10, 5, 10), 1);
	}
	
	public void setFocusPainted(Boolean paintFocus) {
		this.paintFocus = paintFocus;
		repaint();
	}
	
	public void rightFocusBorderPainted(Boolean painted) {
		rightFocusBorder = painted;
		repaint();
	}
	
	public void leftFocusBorderPainted(Boolean painted) {
		leftFocusBorder = painted;
		repaint();
	}
	
	public void setBorderWidth(int width) {
		borderWidth = width;
		repaint();
	}

	public void setArcWidth(int arcWidth) {
		this.arcWidth = arcWidth;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D gg = (Graphics2D) g;
		
		int width   = getWidth();
		int height  = getHeight();
		int borderW = borderWidth;
		int arcW    = arcWidth;
		
		Boolean simpleRender = true;
		
		if(simpleRender) {
			gg.setColor(background);
			gg.fillRect(0, 0, getWidth(), getHeight());
			gg.setColor(foreground);
			gg.fillRoundRect(borderW, borderW, width-(borderW*2), height-(borderW*2), arcW, arcW);

			if(paintFocus) {
				gg.setColor(FancyColor.ACCENT);
				gg.drawRoundRect(borderW,   borderW, width-(borderW*2), 
						height-(borderW*2), arcW,    arcW);
			} else {
				gg.setColor(FancyColor.DARKER_GRAY);
				gg.drawRoundRect(borderW, borderW, width-(borderW*2), 
						height-(borderW*2), arcW,    arcW);
			}
		} else {
			int heightCentre = height/2;
			int centW        = width/2;
			
			int lw = 1;

			Color border = this.border;
			if(paintFocus) {border = FancyColor.ACCENT;}

			int botRightArc = arcWidth;
			int topRightArc = arcWidth;
			int topLeftArc  = arcWidth;
			int botLeftArc  = arcWidth;

			if(!bottomRightRounded) {botRightArc = 0;}
			if(!topRightRounded)    {topRightArc = 0;}
			if(!topLeftRounded)     {topLeftArc  = 0;}
			if(!bottomLeftRounded)  {botLeftArc  = 0;}

			gg.setColor(border);
			gg.fillRoundRect(centW-arcW, heightCentre,    //Bottom Right       
					centW-borderW+arcW,  heightCentre-borderW,       
					botRightArc,         botRightArc);
			gg.fillRoundRect(centW,      borderW,         //Top Right     
					centW-borderW,       heightCentre-borderW+arcW,  
					topRightArc,         topRightArc);
			gg.fillRoundRect(borderW,    borderW,         // Top Left  
					centW-borderW+arcW,  heightCentre-borderW+arcW,  
					topLeftArc,          topLeftArc);
			gg.fillRoundRect(borderW,    heightCentre,   // Bottom Left     
					centW-borderW+arcW,  heightCentre-borderW,       
					botLeftArc,          botLeftArc);


			int rlw = lw;
			int llw = lw;

			if(!rightFocusBorder) {rlw = 0;}
			if(!leftFocusBorder)  {llw = 0;}

			gg.setColor(foreground);
			gg.fillRoundRect(centW-arcW,   heightCentre,    //Bottom Right
					centW-borderW+arcW-rlw, heightCentre-borderW-lw,
					botRightArc,           botRightArc);
			gg.fillRoundRect(centW,        borderW+lw,      //Top Right
					centW-borderW-rlw,      heightCentre-borderW+arcW,
					topRightArc,           topRightArc);

			gg.fillRoundRect(borderW+llw,   borderW+lw,      // Top Left
					centW-borderW+arcW,    heightCentre-borderW+arcW,
					topLeftArc,            topLeftArc);
			gg.fillRoundRect(borderW+llw,   heightCentre-lw, // Bottom Left
					centW-borderW+arcW,    heightCentre-borderW,       
					botLeftArc,            botLeftArc);

			gg.fillRoundRect(borderW+lw, borderW+lw,  width-(borderW*2)-(lw*2), 
					height-(borderW*2)-(lw*2),        arcW-lw, arcW-lw);
		}
	}
}
