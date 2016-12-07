package text;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FancyLabel extends JLabel {
	private static final long serialVersionUID = -4100040909900412981L;

	/**
	 * To be used if the user should be presented with a blank FancyLabel.
	 */
	public FancyLabel() {
		setLabel();
	}
	
	/**
	 * To be used if there is the label should be titled.
	 * @param text is the message to be displayed to the user.
	 */
	public FancyLabel(String text) {
		super(text, SwingConstants.CENTER);
		setLabel();
	}
	
	/**
	 * To be used if the label should be a color besides dark gray.
	 * @param text is the content to be displayed to the user.
	 * @param color is the FancyLabel's color.
	 */
	public FancyLabel(String text, Color color) {
		super(text, SwingConstants.CENTER);

		setForeground(color);
		setLabel();
	}
	
	/**
	 * Sets the label font and empty border.
	 */
	private void setLabel() {
		setOpaque(true);
		setFont(FancyFont.DEFAULT);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}
	
	/**
	 * Sets the foreground and the background color of a JComponent.
	 * @param background is the background color.
	 * @param foreground is the foreground color.
	 */
	public void setColor(Color background, Color foreground) {			
		this.setBackground(background);
		this.setForeground(foreground);
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
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(top, left, bot, right, color),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}
	
	/**
	 * Changes this components size.
	 * @param height is the new height.
	 * @param width is the new width.
	 */
	public void setAbsoluteSize(int width, int height) {
		this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
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
	
	/**
	 * Use a lambda to write a mouseClicked listener.
	 * @param listener is the lambda to be accepted.
	 */
	public void addMouseClickedListener(Consumer<MouseEvent> listener) {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listener.accept(e);
			}
		});
	}
	
	/**
	 * Use a lambda to write a mousePress listener.
	 * @param listener is the lambda to be accepted.
	 */
	public void addMousePressedListener(Consumer<MouseEvent> listener) {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				listener.accept(e);
			}
		});
	}
	
	/**
	 * Use a lambda to write a mousePress listener.
	 * @param listener is the lambda to be accepted.
	 */
	public void addMouseReleasedListener(Consumer<MouseEvent> listener) {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				listener.accept(e);
			}
		});
	}
	
	/**
	 * Use a lambda to write a focusGained listener.
	 * @param listener is the lambda to be accepted.
	 */
	public void addFocusGainedListener(Consumer<FocusEvent> listener) {
		this.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				listener.accept(e);
			}
		});
	}
	
	/**
	 * Use a lambda to write a focusLost listener.
	 * @param listener is the lambda to be accepted.
	 */
	public void addFocusLostListener(Consumer<FocusEvent> listener) {
		this.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				listener.accept(e);
			}
		});
	}
}
