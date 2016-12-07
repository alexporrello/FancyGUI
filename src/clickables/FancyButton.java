package clickables;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import text.FancyFont;
import colors.FancyColor;
import colors.HoverColor;

/**
 * FancyButton styles Java's built-in JButton by removing all 
 * beveling, shading, and roundness. A FancyButton is simple, 
 * clean, flat, and modern; the edges are square, and there are 
 * two colors: dark (its hover color) and light (its normal color).
 * 
 * @author Alexander Porrello
 */
public class FancyButton extends JButton {
	private static final long serialVersionUID = 5942922989567675303L;

	/** Determines the colors of this button **/
	private HoverColor color = HoverColor.GREY;
	
	/** Determines the color of the button's text **/
	public Color font = FancyColor.WHITE;
	
	/** This is the icon to be painted on the button **/
	public ButtonIcon icon = ButtonIcon.NOTHING;

	/** The height of the button underline **/
	public int focusDrawHeight = 3; 

	/**
	 * Creates a FancyButton with no set text or icon.
	 */
	public FancyButton() {
		setButton();
	}

	/**
	 * Creates a FancyButton with text.
	 * @param name is the text to be displayed on the FancyButton.
	 */
	public FancyButton(String text) {
		super(text);
		setButton();
	}
	
	/**
	 * A given ButtonIcon will be painted onto the FancyButton.
	 * @param icon is the icon to be painted.
	 */
	public FancyButton(ImageIcon icon) {
		super(icon);

		setButton();
	}

	/** Sets all the main settings of the JButton **/
	private void setButton() {
		super.setContentAreaFilled(false);
		super.setFocusPainted(false);
		super.setFont(FancyFont.DEFAULT);
	}

	/**
	 * Sets the border: a 'padding' of 5 and then a line.
	 * @param color is the color of the border to be added.
	 * @param top is the width of the top line.
	 * @param left is the width of the left line.
	 * @param bot is the width of the bottom line.
	 * @param right is the width of the right line.
	 */
	public void setBorder(Color color, int top, int left, int bot, int right) {
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(top, left, bot, right, color),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}

	/**
	 * Changes this components width.
	 * @param width is the new width.
	 */
	public void updateWidth(int width) {
		this.setMinimumSize(new Dimension(width, 10));
		this.setPreferredSize(new Dimension(width, 10));
		this.setMaximumSize(new Dimension(width, 100));
		
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Changes the size of the font.
	 */
	public void setFontSize(int fontSize) {
		this.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), fontSize));
	}
	
	/**
	 * Changes this color.
	 * @param color is the button's color.
	 */
	public void setColor(HoverColor color) {
		this.color = color;
	}
	
	public Color getDarkColor() {
		return this.color.dark;
	}
	
	public Color getLightColor() {
		return this.color.light;
	}
	
	/**
	 * Use a lambda to write a kyPressed listener.
	 * @param listener is the lambda to be accepted.
	 */
	public void addKeyPressListener(Consumer<KeyEvent> listener) {
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				listener.accept(e);
			}
		});
	}

	/**
	 * Use a lambda to write a keyReleased listener.
	 * @param listener is the lambda to be accepted.
	 */
	public void addKeyReleasedListener(Consumer<KeyEvent> listener) {
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listener.accept(e);
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D gg = (Graphics2D) g;

		if(getModel().isPressed()) {
			gg.setColor(color.dark);
		} else if(getModel().isRollover()) {
			gg.setColor(color.dark);
		} else if(getModel().isSelected()) {
			gg.setColor(color.dark);
		} else {
			gg.setColor(color.light);
		}

		setForeground(font);

		if(this.hasFocus()) {
			gg.fillRect(0, 0, getWidth(), getHeight()-focusDrawHeight);

			super.paintComponent(gg);

			gg.setColor(color.dark);
			gg.fillRect(0, getHeight()-focusDrawHeight, getWidth(), getHeight());
		} else {
			gg.fillRect(0, 0, getWidth(), getHeight());
			super.paintComponent(gg);
		}
	}
	
	/**
	 * The enum used for the type of button one wishes to paint.
	 */
	public static enum ButtonIcon {
		ADD(0), SUBTRACT(1), DELETE(2), 
		NEXT(3), LAST(4), SQUARE(5), NOTHING(6);
		
		int number;
		
		private ButtonIcon(int number) {
			this.number = number;
		}
	}
}
