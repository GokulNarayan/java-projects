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

// Letter Inventory stores the frequency of each letter in the alphabet in a particular
// word / phrase. Provides certain methods to access information about the inventory.
public class LetterInventory {

	// class constant:
	private static final int NUM_POSSIBLE_LETTERS = 26;
	private static final char FIRST_LETTER = 'a';
	private static final char LAST_LETTER = 'z';
	// instance var:
	private int numLettersInWord;
	private int[] letterFrequencies;

	// constructor
	// input is the word / phrase that an inventory must be created for.
	// array containing frequency of each letter is populated.
	public LetterInventory(String input) {
		// convert input to all lowercase.
		input = input.toLowerCase();
		// the array is initialized to a length of 26 (size of alphabet)
		letterFrequencies = new int[NUM_POSSIBLE_LETTERS];
		// loop iterates through each character in input
		for (int i = 0; i < input.length(); i++) {
			char character = input.charAt(i);
			// check if valid char:
			if (FIRST_LETTER <= character && LAST_LETTER >= character) {
				// add to the correct element in array, increment num letters.
				letterFrequencies[character - FIRST_LETTER] += 1;
				numLettersInWord++;
			}
		}
	}

	// constructor
	// initializes array to a size = number of alphabets.
	public LetterInventory() {
		letterFrequencies = new int[NUM_POSSIBLE_LETTERS];
	}

	// method returns the frequency of a particular letter.
	// letter - the letter whose frequency must be returned.
	// returns the frequency of the letter in a particular word/phrase.
	public int get(char letter) {
		final int UPPERCASE_SHIFTER = 32;
		// check precon: (check if it is an english letter)
		if (((char) (FIRST_LETTER - UPPERCASE_SHIFTER) > letter || (char) (LAST_LETTER - UPPERCASE_SHIFTER) < letter)
				&& (FIRST_LETTER > letter || LAST_LETTER < letter)) {
			throw new IllegalArgumentException("invalid letter");
		}
		// ensure that the letter is converted to lowercase form before returning
		// frequency.
		letter = letterAdjuster(letter);
		return letterFrequencies[letter - FIRST_LETTER];
	}

	// helper method for get().
	// letter - the letter to be adjusted if necessary.
	// returns lowercase form of the letter.
	private char letterAdjuster(char letter) {
		// ASCII value of 'A' = 65. 'a' = 97, a shift of 32 is required to convert.
		final int UPPERCASE_SHIFTER = 32;
		// convert if necessary.
		letter = (FIRST_LETTER <= letter && LAST_LETTER >= letter) ? letter : (char) (letter + UPPERCASE_SHIFTER);
		return letter;
	}

	// method returns the total number of letters in inventory.
	// pre: none
	// post: return number of letters.
	public int size() {
		return numLettersInWord;
	}

	// method checks if inventory is empty.
	// pre: none
	// post: returns true if empty, false otherwise.
	public boolean isEmpty() {
		// inventory is empty if size = 0.
		return numLettersInWord == 0;
	}

	// method returns a string version of the data.
	// pre: none:
	// post: returns all letters in inventory in alphabetical order.
	public String toString() {
		// use string builder for efficiency.
		StringBuilder sb = new StringBuilder();
		// outer loop goes through all cells in array.
		for (int i = 0; i < letterFrequencies.length; i++) {
			// inner loop appends a letter based on its frequency.
			for (int u = 0; u < letterFrequencies[i]; u++) {
				sb.append((char) (FIRST_LETTER + i));
			}
		}
		return sb.toString();
	}

	// method adds the inventories of two LetterInventory objects.
	// pre: other != null
	// post: returns a new LetterInventory object whose frequencies are the sum of
	// the two LetterInventory objects added.
	public LetterInventory add(LetterInventory other) {
		// check precon:
		if (other == null) {
			throw new IllegalArgumentException("invalid param");
		}

		// create the new LetterInventory object.
		LetterInventory output = new LetterInventory();
		// loop through each cell of the inventory array.
		for (int i = 0; i < letterFrequencies.length; i++) {
			// update the frequency of each letter in new inventory.
			output.letterFrequencies[i] = letterFrequencies[i] + other.letterFrequencies[i];
		}
		// update size of the new inventory.
		output.numLettersInWord = this.numLettersInWord + other.numLettersInWord;
		return output;
	}

	public LetterInventory subtract(LetterInventory other) {
		// check precon:
		if (other == null) {
			throw new IllegalArgumentException("invalid param");
		}
		// create the new LetterInventory object.
		LetterInventory output = new LetterInventory();
		// loop through each cell of the inventory array.
		for (int i = 0; i < letterFrequencies.length; i++) {
			// find the subtracted value.
			int subtractedValue = letterFrequencies[i] - other.letterFrequencies[i];
			// if subtracted value is less than 0, return null, else update new
			// LetterInventory's frequency.
			if (subtractedValue < 0) {
				return null;
			} else {
				output.letterFrequencies[i] = subtractedValue;
			}
		}
		// update size of new inventory
		output.numLettersInWord = numLettersInWord - other.numLettersInWord;
		return output;
	}

	// method checks if two LetterInventory objects are equal.
	// pre: none
	// post: returns true if the LetterInventories are equal, false otherwise.
	public boolean equals(Object obj2) {
		boolean result = false;
		// check if obj2 is not null and is an LetterInventory object.
		if (obj2 != null && this.getClass() == obj2.getClass()) {
			// cast into LetterInventory object.
			LetterInventory other = (LetterInventory) obj2;
			// call helper method to determine if each frequency is equal (if each has same
			// num letters).
			if (numLettersInWord == other.numLettersInWord)
				result = equalsHelper(other);
		}
		return result;
	}

	// helper method for equals
	// returns true if each frequency is equal to the corresponding frequency.
	private boolean equalsHelper(LetterInventory other) {
		// iterate through each cell in inventory array.
		for (int i = 0; i < NUM_POSSIBLE_LETTERS; i++) {
			// check if the inventories are the same.
			if (other.letterFrequencies[i] != letterFrequencies[i]) {
				return false;
			}
		}
		return true;
	}
}
