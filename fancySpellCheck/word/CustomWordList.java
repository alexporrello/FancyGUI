package word;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

/**
 * A custom word list is the list that contains all the words added
 * by the user to the dictionary. Creates a file called custom_wordlist.txt
 * that is to be included in the current system's appdata folder and which 
 * contains the custom words.
 * 
 * @author Alexander Porrello
 */
public class CustomWordList {
	
	public static final String CUSTOM_WORDLIST_URL = System.getenv("APPDATA") + "\\custom_wordlist.txt";
	
	private HashSet<String> words = new HashSet<String>();
	
	/**
	 * Reads the custom words list from the OS's appdata directory and adds them
	 * to the private {@link #words} ArrayList.
	 */
	public CustomWordList() {		
		loadWordlist();
	}
	
	public void loadWordlist() {
		try {
			File file = new File(CustomWordList.CUSTOM_WORDLIST_URL);
			
			if(!file.exists()) {
				file.createNewFile();
			}
			
			BufferedReader wordlist = new BufferedReader(new FileReader(file.getAbsolutePath()));

			String thisWord;
			
			while ((thisWord = wordlist.readLine()) != null) {
				if(thisWord.length() >= 1) {
					this.words.add(thisWord);
				}
			}
			
			wordlist.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Used to determine if the word is in the custom dictionary.
	 * @param word is the word that may or may not be in the custom dictionary.
	 * @return true if the word is in the dictionary; else, false.
	 */
	public Boolean contains(String word) {
		return words.contains(word);
	}
	
	/**
	 * Adds a custom word to the custom words file in the OS's appdata directory.
	 * @param word is the word to be added to the custom dictionary.
	 * @return true if the addition was successful; else, false.
	 */
	public Boolean addCustomWord(String word) {		
		words.add(word);
		
		return writeCustomWordList();
	}
	
	/**
	 * Removes a custom word to the custom words file in the OS's appdata directory.
	 * @param word is the word to be removed to the custom dictionary.
	 * @return true if the removal was successful; else, false.
	 */
	public Boolean removeCustomWord(String word) {
		words.remove(word);
		
		return writeCustomWordList();
	}
	
	/**
	 * Saves the user's preferences to the OS's app data folder.
	 * @return true if the prefs could be saved; else, false.
	 */
	private Boolean writeCustomWordList() {
		if(System.getProperty("os.name").contains("Windows")) {
			try {
				String content = "";
				
				for(String s : words) {
					content = content + "\n" + s;
				}
				
				File file = new File(System.getenv("APPDATA") + "\\custom_wordlist.txt");

				if(!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(content);
				bw.close();

				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}
	
}
