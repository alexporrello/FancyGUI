package text;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.JTextField;

import spellCheck.FancySpellCheck;
import word.WordList;
import colors.FancyColor;

public class FancyTextField extends JTextField {
	private static final long serialVersionUID = -554080067019339123L;

	/** If true, this form will accept only numbers; otherwise, everything. **/
	private Boolean acceptNumber = false;

	/** Responsible for smart quotes and other auto replacements **/
	public SmartSubstitute smartSubstitute;

	/** The spell checker for this **/
	private FancySpellCheck fancySpellCheck;
	
	/**
	 * To be used if one would like a standard JTextField with text.
	 * @param text is this JTextField's text.
	 */
	public FancyTextField(String text) {
		super(text);
		setTextField();
	}

	/**
	 * Sets the font, the foreground color, and the margin.
	 * Also, implements keyListener whereby one can only enter
	 * numbers.
	 */
	private void setTextField() {
		super.setFont(new Font(FancyFont.ARIAL, Font.PLAIN, 15));
		super.setForeground(FancyColor.DARK_FONT);
		super.setMargin(new Insets(5, 5, 5, 5));

		this.smartSubstitute = new SmartSubstitute(this);
		this.addKeyTypedListener(e -> {
			if(acceptNumber) {
				if(e.getKeyChar() < 48 || e.getKeyChar() > 57) {
					e.consume();
				}
			}
		});
	}

	/**
	 * Should this form only accept number input?
	 * @param acceptNumber is true if only accept numbers; else, false.
	 */
	public void acceptOnlyNumbers(boolean acceptNumber) {
		this.acceptNumber = acceptNumber;
	}

	/**
	 * Changes the size of the font.
	 */
	public void setFontSize(int fontSize) {
		this.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), fontSize));
	}

	public void enableSpellCheck(WordList wordlist) {
		fancySpellCheck = new FancySpellCheck(this, wordlist);
	}
	
	public void disableSpellCheck() {
		if(fancySpellCheck != null) {
			fancySpellCheck.enableSpellCheck(false);
		}
	}
	
	/**
	 * Use a lambda to write a k3yPressed listener.
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
	 * Use a lambda to write a keyTyped listener.
	 * @param listener is the lambda to be accepted.
	 */
	public void addKeyTypedListener(Consumer<KeyEvent> listener) {
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
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
