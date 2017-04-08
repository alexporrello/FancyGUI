package text;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

import spellCheck.FancySpellCheck;
import word.WordList;
import colors.FancyColor;
import displays.FancyFrame;

public class FancyTextArea extends JTextArea {
	private static final long serialVersionUID = -3323550333805756766L;
	
	/** Responsible for smart quotes and other auto replacements **/
	public SmartSubstitute smartSubstitute;
	
	/** The spell checker for this **/
	private FancySpellCheck fancySpellCheck;
	
	/**
	 * To be used if one would like a standard FancyTextArea with text.
	 * @param text is this FancyTextArea's text.
	 */
	public FancyTextArea(String text) {
		super(text);
		setTextArea();
	}
	
	/**
	 * Sets the font, the foreground color, and the border. <br>
	 * Sets word wrap and line wrap to true.
	 */
	private void setTextArea() {
		super.setFont(new Font(FancyFont.ARIAL, Font.PLAIN, 15));
		super.setForeground(FancyColor.DARK_FONT);
		super.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		super.setWrapStyleWord(true);
		super.setLineWrap(true);
		
		this.smartSubstitute = new SmartSubstitute(this);
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
	 * Returns the JPopupMenu if fancySpellCheck has been enabled; otherwise 
	 * throws an exception.
	 * @return the JPopupMenu for the fancySpellChecker.
	 */
	public JPopupMenu getJPopupMenu() {
		if(fancySpellCheck != null) {
			return fancySpellCheck.menu;
		} else {
			throw new NoSuchElementException();
		}
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
	
	public static void main(String args[]) {
		FancyFrame frame = new FancyFrame();
		
		FancyTextArea text = new FancyTextArea("Hello! This is a test.");
		
		new FancySpellCheck(text, new WordList());
		
		frame.add(text);

		frame.setSize(new Dimension(300, 300));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(FancyFrame.EXIT_ON_CLOSE);
		
	}
}
