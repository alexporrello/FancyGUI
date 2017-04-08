package spellCheck;

import gui.RedZigZagPainter;

import java.awt.Point;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.JTextComponent;

import text.Ascii;
import text.StringUtils;
import text.Unicode;
import word.SpellCheckWord;
import word.WordList;

public class JMSpellCheck {

	/** The locations of all the errors in the document **/
	private ArrayList<Error> errorLocations = new ArrayList<Error>();

	/** When any of these are encountered, re-check spelling **/
	private int[] check = {Ascii.space, Ascii.backspace, Ascii.comma, Ascii.period, 
			Ascii.delete, Ascii.letter_v, Ascii.question_mark, Ascii.exclaimation, 
			Ascii.colon, Ascii.semi_colon};

	/** If true, checks for spelling errors **/
	private Boolean spellCheckEnable = true;

	/** If true, corrects certain common misspellings **/
	private Boolean autoCorrect = true;

	/** The list of suggestions when the error is clicked upon **/
	public JPopupMenu menu = new JPopupMenu();

	/** A list of autoCorrectOptions **/
	private HashMap<String, String> autoCorrectOptions = new HashMap<String, String>();

	/** Paints the red underline for incorrectly spelled words **/
	private RedZigZagPainter painter = new RedZigZagPainter();

	/** The error that is currently selected **/
	private String selectedError = "";

	public JMSpellCheck(JTextComponent jText, WordList words) {
		jText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				for(int i : check) {
					if(e.getKeyChar() == i) {
						checkSpelling(jText, words);
						break;
					}
				}
			}
		});

		jText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				checkSpelling(jText, words);
			}
		});

		setUpJPopupMenu(jText, words);
		setUpAutoCorrect();
		checkSpelling(jText, words);
		listenForCustomDictionaryChange(jText, words);
	}

	/**
	 * Adds a JPopupMenu to a given JTextComponent.
	 * @param jText the JTextComponent to which a JPopupMenu will be added.
	 * @param words the list where correctly spelled words are.
	 */
	private void setUpJPopupMenu(JTextComponent jText, WordList words) {
		JMenuItem addToDict = new JMenuItem("Add to Dictionary");
		addToDict.addActionListener(e -> {
			words.addCustomWord(selectedError);
			checkSpelling(jText, words);
		});

		JMenuItem ignore = new JMenuItem("Ignore All");
		ignore.addActionListener(e -> {
			words.ignore.add(selectedError);
			checkSpelling(jText, words);
		});

		jText.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				if(e.isPopupTrigger()) {
					showPopupMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e){
				if(e.isPopupTrigger()) {
					showPopupMenu(e);
				}
			}

			private void showPopupMenu(MouseEvent e){

				int point = jText.viewToModel(e.getPoint());

				menu.removeAll();

				for(Error error : errorLocations) {
					if((point >= error.x) && (point < error.y)) {
						selectedError = error.error;

						int stop = 0;

						for(String s : findCorrectWord(selectedError, words)) {
							if(stop < 5) {
								menu.add(makeSuggestionMenuItem(jText, error, s, words));
								stop++;
							} else {
								break;
							}
						}

						if(menu.getComponents().length >= 1) {
							menu.addSeparator();
						}

						menu.add(ignore);
						menu.add(addToDict);
						menu.show(e.getComponent(), e.getX(), e.getY());

						break;
					}
				}
			}
		});	
	}

	/** Populates the autoCorrectOptions HashMap **/
	private void setUpAutoCorrect() {
		autoCorrectOptions.put("abbout", "about");
		autoCorrectOptions.put("abotu", "about");
		autoCorrectOptions.put("alot", "a lot");
		autoCorrectOptions.put("wnat", "want");
		autoCorrectOptions.put("teh", "the");
	}

	/**
	 * Checks a given JTextComponent for any spelling errors.
	 * @param jText the JTextComponent to be checked.
	 * @param words the list where correctly spelled words are.
	 */
	private void checkSpelling(JTextComponent jText, WordList words) {
		errorLocations.clear();
		jText.getHighlighter().removeAllHighlights();

		if(spellCheckEnable) {
			String[] split = jText.getText().split(" |\n|\r|-|—");

			int beginIndex = 0;
			int endIndex = 0;

			for(String word : split) {
				String wordCopy = SpellCheckWord.stripWord(word);

				endIndex = beginIndex + wordCopy.length();

				if(SpellCheckWord.validWord(wordCopy)) {
					if(autoCorrect) {
						if(autoCorrectOptions.containsKey(wordCopy)) {
							replaceRange(jText, beginIndex, endIndex, autoCorrectOptions.get(wordCopy));
							checkSpelling(jText, words);
						} else {
							if(wordCopy.contains(Unicode.rightSingleQuotationMark + "s")) {
								String tempWord = wordCopy.replace(Unicode.rightSingleQuotationMark + "s", "");
								
								if(!words.contains(tempWord)) {
									this.paintRedUnderline(jText, beginIndex, endIndex, wordCopy);
								}
							} else {
								if(!words.contains(wordCopy)) {
									this.paintRedUnderline(jText, beginIndex, endIndex, wordCopy);
								}
							}
						}
					} else {
						if(!words.contains(wordCopy)) {
							this.paintRedUnderline(jText, beginIndex, endIndex, wordCopy);
						}
					}
				}

				beginIndex = beginIndex + word.length() + 1;
			}
		}
	}

	/**
	 * If the custom word list is modified, re-check the spelling.
	 * @param jText the JTextComponent whose spelling is to be checked.
	 * @param words the list where correctly spelled words are.
	 */
	private void listenForCustomDictionaryChange(JTextComponent jText, WordList words) {
		new Thread(() -> {
			try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {

				Path path = new File(System.getenv("APPDATA")).toPath();
				path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

				while (true) {
					final WatchKey wk = watchService.take();

					for (WatchEvent<?> event : wk.pollEvents()) {
						final Path changed = (Path) event.context();

						if (changed.endsWith("custom_wordlist.txt")) {						
							words.loadCustomWordList();
							checkSpelling(jText, words);
						}
					}
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	/**
	 * Paints a red underline under a portion of a JTextArea's text.
	 * @param jText the JTextArea whose text will be underlined in red.
	 * @param beginIndex the index of the beginning of the red underline. 
	 * @param endIndex the index of the end of the red underline.
	 * @param error the incorrectly spelled word.
	 */
	private void paintRedUnderline(JTextComponent jText, int beginIndex, int endIndex, String error) {		
		int start = beginIndex;//jText.getText().indexOf(error);
		int end   = endIndex;//start + error.length();

		Highlighter highlighter = jText.getHighlighter();
		Highlight[] highlights  = highlighter.getHighlights();

		for(Highlight h : highlights) {
			int hlStartOffset = h.getStartOffset();
			int hlEndOffset   = h.getEndOffset();

			if((start <= hlStartOffset && hlStartOffset <= end) || 
					(start <= hlEndOffset && hlEndOffset <= end)) {
				if(h.getPainter() == painter) {
					highlighter.removeHighlight(h);
				}
			}
		}

		int length = ((AbstractDocument)jText.getDocument()).getLength();
		end = Math.min(end, length);

		if(start >= end) {
			return;
		}

		try {
			highlighter.addHighlight(start, end, painter);

			errorLocations.add(new Error(beginIndex, endIndex, error));
		} catch (BadLocationException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * For given word, return closest match correct spelling.
	 * @param word the word to be corrected.
	 * @param words the list where correctly spelled words are.
	 * @return an ArrayList of possible matches.
	 */
	private ArrayList<String> findCorrectWord(String word, WordList words) {

		ArrayList<String> suggestions = new ArrayList<String>();

		if(words.containsKey(SpellCheckWord.firstLetter(word))) {
			ArrayList<String> check = words.get(SpellCheckWord.firstLetter(word));

			for(String s: check) {
				if(StringUtils.computeLevenshteinDistance(word, s) < 3) {
					suggestions.add(s);
				}
			}
		}

		return suggestions;
	}

	/**
	 * 
	 * @param jText the JTextComponent whose text will be replaced.
	 * @param error the word that is spelled incorrectly. 
	 * @param correction the word's correct spelling.
	 * @param words the list where correctly spelled words are.
	 * @return a JMenuItem whereby a user can select a correctly spelled suggestion.
	 */
	private JMenuItem makeSuggestionMenuItem(JTextComponent jText, Error error, String correction, WordList words) {
		JMenuItem suggestion = new JMenuItem(correction);

		suggestion.addActionListener(e -> {
			replaceRange(jText, error.x, error.y, correction);
			checkSpelling(jText, words);
		});

		return suggestion;
	}

	/**
	 * Replaces the text within a given range.
	 * @param jText is the JTextComponent whose text will be replaced.
	 * @param beginIndex the index of the start of the string to be replaced.
	 * @param endIndex the index of the end of the string to be replaced.
	 * @param toReplace is the text to replace.
	 */
	private void replaceRange(JTextComponent jText, int beginIndex, int endIndex, String toReplace) {
		String start = jText.getText().substring(0, beginIndex);
		String end   = jText.getText().substring(endIndex, jText.getText().length());

		jText.setText(start + toReplace + end);
		jText.setCaretPosition(start.length()+toReplace.length());
	}

	private class Error extends Point {
		private static final long serialVersionUID = 1155837118625918746L;

		private String error;

		private Error(int x, int y, String error) {
			super(x, y);

			this.error = error;
		}
	}

	/**
	 * Enables and disables spell check.
	 * @param spellCheckEnable checks spelling if true; else, doesn't.
	 */
	public void enableSpellCheck(Boolean spellCheckEnable) {
		this.spellCheckEnable = spellCheckEnable;
	}
}
