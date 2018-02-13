package word;

import text.Special;

public class SpellCheckWord {

	private static String[] quotation = {"?", "!", ".", ",", ";", ":", "(", ")"};


	public static Boolean validWord(String word) {
		if(word.length() > 0) {
			return alphabetical(word);
		}

		return false;
	}


	/**
	 * Determines if a given word begins with a character from the alphabet.
	 * @param word is the word to be worked upon.
	 * @return true if word is alphabetical; else, false.
	 */
	public static boolean alphabetical(String word) {		
		return ((word.charAt(0) >= 'a' && word.charAt(0) <= 'z') || 
				(word.charAt(0) >= 'A' && word.charAt(0) <= 'Z'));
	}

	/**
	 * Returns the first letter of a given string.
	 * @param word is the word whose first letter is to be returned.
	 * @return first letter of word.
	 */
	public static String firstLetter(String word) {
		return Character.toString(word.charAt(0));
	}

	public static String stripWord(String word) {
		for(String s : quotation) {
			if(word.contains(s)) {
				word = word.replace(s, "");
			}
		}

		word = word.replace(Special.Unicode.ellipsis, "");
		word = word.replace(Special.Unicode.rightDoubleQuotationMark, "");
		word = word.replace(Special.Unicode.leftDoubleQuotationMark, "");

		if(word.startsWith(Special.Unicode.leftSingleQuotationMark)) {
			word = word.substring(0, word.length());
		}

		if(word.endsWith(Special.Unicode.rightSingleQuotationMark)) {
			word = word.substring(0, word.length()-1);
		}

		return word;
	}
}
