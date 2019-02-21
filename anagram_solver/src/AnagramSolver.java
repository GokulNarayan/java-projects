/* Student information for assignment:
 *
 *  On my honor, Gokul Anandaraman, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID:gna323
 *  email address:gokul.narayan@utexas.edu
 *  Grader name:
 *  Number of slip days I am using:0
 */

// import statements
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

// AnagramSolver finds all possible anagrams of a given word/phrase based on an input dictionary
// and max set of words allowed. 
public class AnagramSolver {

	// instance vars:
	private HashMap<String, LetterInventory> dictionaryMap;
	// will contain the LetterInventory of user input.
	private LetterInventory tgtInventory;

	// constructor
	// words = the list of words from a particular dictionary.
	// post: initializes the dictionaryMap, map is populated.
	public AnagramSolver(List<String> words) {
		// initialize the map.
		dictionaryMap = new HashMap<String, LetterInventory>();
		// iterate through all words in list.
		for (String word : words) {
			// create a LetterInventory for each word.
			// key = word, value = LetterInventory.
			dictionaryMap.put(word, new LetterInventory(word));
		}
		// default value of other instance vars is okay.
	}

	// helper method to check if user input has at least one english letter.
	private boolean hasOneEnglishLetter(String word) {
		final char FIRST_LETTER = 'a';
		final char LAST_LETTER = 'z';
		// iterate through each character in input.
		for (int i = 0; i < word.length(); i++) {
			char letter = word.charAt(i);
			// check if it is an english letter.
			if (FIRST_LETTER <= letter && LAST_LETTER >= letter) {
				return true;
			}
		}
		return false;
	}

	// method returns a list of all possible anagrams based on input and
	// maxNumWords.
	// tgtWord: the user input (can be word or phrase)
	// maxNumWords: the maximum number of words that can be included in an anagram.
	// (if maxNumWords = 0, unlimited)
	// pre: tgtWord!=null, tgtWord has at least one english letter , maxNumWords>0.
	// post: return a list of all anagrams found given the conditions.
	public List<List<String>> getAnagrams(String tgtWord, int maxNumWords) {
		// check precon:
		if (tgtWord == null || !hasOneEnglishLetter(tgtWord.toLowerCase()) || maxNumWords < 0) {
			throw new IllegalArgumentException("illegal params");
		}

		// create List<List<String>> to return.
		List<List<String>> result = new ArrayList<List<String>>();
		// initialize tgtInventory based on user input.
		tgtInventory = new LetterInventory(tgtWord);
		// check if there is a valid limit.
		maxNumWords = (maxNumWords == 0) ? tgtInventory.size() : maxNumWords;
		// get possible words (many words in original dictionary can be disregarded)
		ArrayList<String> possibleWords = getPossibleWords(tgtInventory);
		// call recursive helper method to get all anagrams.
		getAnagramsHelper(result, new ArrayList<String>(), possibleWords, new LetterInventory(), tgtInventory.size(),
				maxNumWords, 0);
		// sort the list of anagrams based on specifications using a comparator.
		Collections.sort(result, new AnagramComparator());
		return result;
	}

	// helper method for get anagrams (recursive method)
	// anagramsSoFar: the list of anagrams that we must populate,
	// currentWords: the words currently found to make a possible anagram.
	// possibleWords: the possibleWords that can be used to form anagrams.
	// possibleWordSize: the max possible size of a word that can be added.
	// int numWordsLeft: the number of words that we can still add
	// int indexOfLast: the index of the last word we stopped at to make an anagram.
	// pre: none
	// post: return a list of all anagrams.
	private void getAnagramsHelper(List<List<String>> anagramsSoFar, ArrayList<String> currentWords,
			ArrayList<String> possibleWords, LetterInventory currInventory, int possibleWordSize, int wordsLeft,
			int lastIndex) {

		// base case: if the possibleWordSize = 0, we can't add any more words.
		if (possibleWordSize == 0) {
			anagramsSoFar.add(currentWords);
		} else {
			// check if we can add any more words. (or if there is no limit)
			if (wordsLeft > 0) {
				// loop through remaining words in possibleWords.
				for (int i = lastIndex; i < possibleWords.size(); i++) {
					// get the current word we are on. 
					String possibleWord = possibleWords.get(i);
					// check if the length of the possible word < possibleWordSize.
					// check if the word can possibly lead to another anagram.
					if (possibleWord.length() <= possibleWordSize && canBeInAnagram(currInventory, possibleWord)) {
						// add the word to currentWords.
						currentWords.add(possibleWord);
						// get a new possibleWordSize.
						int newPossibleWordSize = possibleWordSize - possibleWord.length();
						// create a copy of currentWords for next recursive call.
						ArrayList<String> newCurrentWords = new ArrayList<String>(currentWords);
						// get new currentInventory:
						LetterInventory newCurrInventory = currInventory.add(dictionaryMap.get(possibleWord));
						// make recursive call.
						getAnagramsHelper(anagramsSoFar, newCurrentWords, possibleWords, newCurrInventory,
								newPossibleWordSize, wordsLeft - 1, i);
						// remove the word previously added to currentWords.
						currentWords.remove(possibleWord);
					}
				}
			}
		}
	}

	// helper method for getAnagramHelper()
	// currentInventory = inventory of words currently added to a possible anagram.
	// wordToAdd = word to be added to this possible anagram.
	private boolean canBeInAnagram(LetterInventory currentInventory, String wordToAdd) {
		// create a new inventory which stores the frequencies of all letters in
		// currentInventory + the wordToAdd.
		LetterInventory newInventory = currentInventory.add(dictionaryMap.get(wordToAdd));
		// check if adding this word could lead to a possible anagram
		// if subtract() leads to null, there are certain letters with greater frequency
		// than available.
		if (tgtInventory.subtract(newInventory) != null) {
			return true;
		} else {
			return false;
		}
	}

	// helper method for getAnagram()
	// inputInventory: LetterInventory of user input.
	// post: return a list of all possible words that could be in an anagram.
	private ArrayList<String> getPossibleWords(LetterInventory inputInventory) {
		// create an ArrayList
		ArrayList<String> result = new ArrayList<>();
		// iterate through each word in the map.
		for (String key : dictionaryMap.keySet()) {
			// get a newInvntory which is the result of inputInventory - word's inventory.
			LetterInventory newInventory = inputInventory.subtract(dictionaryMap.get(key));
			// check if it is null. if it is, the word cannot be in an anagram.
			if (newInventory != null) {
				result.add(key);
			}
		}
		// sort the possible words to ensure anagrams found are already in alphabetical
		// order.
		Collections.sort(result);
		return result;
	}

	// Class which implements comparator to sort all anagrams.
	private static class AnagramComparator implements Comparator<List<String>> {

		// method which compares two anagrams.
		// pre: none.
		// post: returns num>0 if anagram1>anagram2, num<0 if the opposite.
		public int compare(List<String> anagram1, List<String> anagram2) {
			// find the difference in num words in each anagram.
			int sizeDiff = anagram1.size() - anagram2.size();
			// if the size difference is not 0, return the difference.
			if (sizeDiff != 0) {
				return sizeDiff;
			} else {
				// the anagram must be sorted alphabetically. (if same size)
				// iterate over all words in each anagram.
				// if any word produces a non zero value when compared to each other,
				// return that value.
				for (int i = 0; i < anagram1.size(); i++) {
					int returnValue = anagram1.get(i).compareTo(anagram2.get(i));
					if (returnValue != 0) {
						return returnValue;
					}
				}
				// if equal return 0.
				return 0;
			}
		}
	}
}
