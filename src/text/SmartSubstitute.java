package text;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.text.JTextComponent;

/**
 * Responsible for smart quotes and other auto replacements.
 * @author Alexander Porrello
 */
public class SmartSubstitute {

	private Boolean autoCorrect    = true;
	private Boolean smartQuotes    = true;
	private Boolean signCorrect    = true;
	private Boolean mathCorrect    = true;
	private Boolean dashCorrect    = true;
	private Boolean elipsisCorrect = true;
	private Boolean autoCensor     = false;

	JTextComponent jText;

	public SmartSubstitute(JTextComponent jText) {
		this.jText = jText;

		addKeyListener();
	}

	public void addKeyListener() {
		jText.addKeyListener(new KeyAdapter() {
			/** Keeps track of the position of the caret before the required substitution **/
			int caretPosn;

			@Override
			public void keyTyped(KeyEvent e) {
				caretPosn = jText.getCaretPosition();

				if(smartQuotes) {
					if(e.getKeyChar() == '\"') {					
						if(caretPosn > 0) {
							int before = (int) jText.getText().charAt(caretPosn-1);

							if (before == 10 || before == 9 || before == 32) {
								replaceRange(Special.Unicode.leftDoubleQuotationMark, caretPosn, caretPosn);
								e.consume();
//							} else if(before == 32) {
//								replaceRange(Special.Unicode.leftDoubleQuotationMark, caretPosn, caretPosn);
//								e.consume();
							} else {
								replaceRange(Special.Unicode.rightDoubleQuotationMark, caretPosn, caretPosn);
								e.consume();
							}
						} else {
							replaceRange(Special.Unicode.leftDoubleQuotationMark, caretPosn, caretPosn);
							e.consume();
						}
					}

					if(e.getKeyChar() == '\'') {					
						if(caretPosn > 0) {
							int before = (int) jText.getText().charAt(caretPosn-1);

							if (before == 10 || before == 9) {
								replaceRange(Special.Unicode.leftSingleQuotationMark, caretPosn, caretPosn);
								e.consume();
							} else if(before == 32) {
								replaceRange(Special.Unicode.leftSingleQuotationMark, caretPosn, caretPosn);
								e.consume();
							} else {
								replaceRange(Special.Unicode.rightSingleQuotationMark, caretPosn, caretPosn);
								e.consume();
							}
						} else {
							replaceRange(Special.Unicode.leftSingleQuotationMark, caretPosn, caretPosn);
							e.consume();
						}
					}
				}

				if(autoCorrect) {
					if(caretPosn > 4 && (e.getKeyChar() == 'e' || e.getKeyChar() == 'E')) {
						Boolean isCliche = (
								((jText.getText().charAt(caretPosn-5) == 67)  || (jText.getText().charAt(caretPosn-5) == 99)) &&
								(jText.getText().charAt(caretPosn-4) == 108)  && (jText.getText().charAt(caretPosn-3) == 105) &&
								(jText.getText().charAt(caretPosn-2) == 99)   && (jText.getText().charAt(caretPosn-1) == 104));

						if(isCliche) {
							if(jText.getText().charAt(caretPosn-5) == 67) {
								replaceRange("Clich" + Special.Unicode.latinSmallLetterEWithAcute, caretPosn-5, caretPosn);
								e.consume();
							} else if(jText.getText().charAt(caretPosn-5) == 99) {
								replaceRange("clich" + Special.Unicode.latinSmallLetterEWithAcute, caretPosn-5, caretPosn);
								e.consume();
							}
						}
					}
				}

				if(signCorrect) {
					if(caretPosn > 1) {
						if(e.getKeyChar() == ')' && jText.getText().charAt(caretPosn-2) == 40) {		
							int before = (int) jText.getText().charAt(caretPosn-1);

							if(before == 82 || before == 114) {
								replaceRange(Special.Unicode.registered, caretPosn-2, caretPosn);
								e.consume();
							}

							if(before == 69 || before == 101) {
								replaceRange(Special.Unicode.euro, caretPosn-2, caretPosn);
								e.consume();
							}


							if(before == 67 || before == 99) {
								replaceRange(Special.Unicode.copyright, caretPosn-2, caretPosn);
								e.consume();
							}
						}
					}

					if(caretPosn > 2) {
						if(e.getKeyChar() == ')' && jText.getText().charAt(caretPosn-3) == 40) {		
							int before = (int) jText.getText().charAt(caretPosn-1);

							if(before == 77 || before == 109) {
								replaceRange(Special.Unicode.euro, caretPosn-3, caretPosn);
								e.consume();
							}
						}
					}
				}

				if(dashCorrect) {
					if(caretPosn > 0) {
						if(e.getKeyChar() == '-' && jText.getText().charAt(caretPosn-1) == 45) {
							int before = (int) jText.getText().charAt(caretPosn-1);

							if(before == 45) {
								replaceRange(Special.Unicode.emDash, caretPosn-1, caretPosn);
								e.consume();
							}
						}
					}
				}

				if(elipsisCorrect) {
					if(caretPosn > 1) {
						if(e.getKeyChar() == '.' && jText.getText().charAt(caretPosn-2) == 46) {
							int before = (int) jText.getText().charAt(caretPosn-1);

							if(before == 46) {
								replaceRange(Special.Unicode.ellipsis, caretPosn-2, caretPosn);
								e.consume();
							}
						}
					}
				}

				if(mathCorrect) {
					// TODO implement math correct, which is to say, correct += to ±, etc.
				}
				
				if(autoCensor) {
					String[] swears  = {"fuck", "shit", "ass"};
					String[] replace = {"f*ck", "s*it", "a*s"};
					
					int i = 0;
					
					for(String s : swears) {
						if(jText.getText().contains(s)) {
							replaceRange(replace[i], caretPosn-(replace[i].length()), caretPosn);
						}
						
						i++;
					}
					
					
					
				}
			}
		});
	}

	/**
	 * Replaces the text within a given range.
	 * @param toReplace is the text to replace.
	 * @param start is the starting index of the text to replace.
	 * @param end is the ending index of the text to replace.
	 */
	private void replaceRange(String toReplace, int start, int end) {
		String beginning = jText.getText().substring(0, start);
		String ending    = jText.getText().substring(end, jText.getText().length());

		jText.setText(beginning + toReplace + ending);

		if(jText.getText().length() < end) {
			jText.setCaretPosition(start+=1);
		} else {
			jText.setCaretPosition(end+=1);
		}
	}
	
	/** Enables auto-correct of words **/
	public void enableAutoCorrect(Boolean enable) {
		autoCorrect = enable;
	}

	/** Enables smart-quotes **/
	public void enableSmartQuotes(Boolean enable) {
		smartQuotes = enable;
	}

	/** Enables m-dash auto correct **/
	public void enableDashCorrect(Boolean enable) {
		dashCorrect = enable;
	}

	/** Enables ellipses auto correct **/
	public void enableEllipsesCorrect(Boolean enable) {
		elipsisCorrect = enable;
	}
	
	/** Enables the automatic censoring of swear words **/
	public void enableAutoCensor(Boolean enable) {
		autoCensor = enable;
	}
}
