package word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class WordList extends HashMap<String, ArrayList<String>> {
	private static final long serialVersionUID = -7545820016026594266L;

	private CustomWordList  custom = new CustomWordList();
	public HashSet<String>  ignore = new HashSet<String>();

	public WordList() {
		for(int i = 'a'; i <= 'z'; i++) {
			this.put(Character.toString((char) i), new ArrayList<String>());
		}

		for(int i = 'A'; i <= 'Z'; i++) {
			this.put(Character.toString((char) i), new ArrayList<String>());
		}

		this.put("other", new ArrayList<String>());

		try {
			BufferedReader wordlist = new BufferedReader(new InputStreamReader(
					this.getClass().getClassLoader().getResourceAsStream("words.txt")));

			String thisWord;

			while ((thisWord = wordlist.readLine()) != null) {
				if(thisWord.length() >= 1) {
					this.add(thisWord);
					this.add(thisWord.substring(0, 1).toUpperCase() + thisWord.substring(1));
				}
			}

			BufferedReader contractions = new BufferedReader(new InputStreamReader(
					this.getClass().getClassLoader().getResourceAsStream("contractions.txt")));

			while ((thisWord = contractions.readLine()) != null) {
				if(thisWord.length() >= 1) {
					this.add(thisWord);
					this.add(thisWord.substring(0, 1).toUpperCase() + thisWord.substring(1));
				}
			}

			wordlist.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a word to the proper ArrayList.
	 * @param word is the word to be added.
	 */
	private void add(String word) {
		//TODOword = word.toLowerCase();
		String firstLetter = String.valueOf(word.charAt(0));

		if(SpellCheckWord.alphabetical(firstLetter)) {
			this.get(firstLetter).add(word);
		} else {
			this.get("other").add(word);
		}
	}

	/**
	 * @param word is the word that may or may not exist.
	 * @return true if word exists; else, false.
	 */
	public Boolean contains(String word) {
		if(word.length() > 0) {
			String firstLetter = String.valueOf(word.charAt(0));

			if(SpellCheckWord.alphabetical(word)) {
				if(this.get(firstLetter).contains(word) || custom.contains(word) || ignore.contains(word)) {
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * Add a word to the custom dictionary.
	 * @param word is the word to be added.
	 * @return true if the word exists; else, false.
	 */
	public Boolean addCustomWord(String word) {
		return custom.addCustomWord(word);
	}
	
	public void loadCustomWordList() {
		custom.loadWordlist();
	}
	
	
}
