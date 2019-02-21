import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* CS 314 STUDENTS: FILL IN THIS HEADER AND THEN COPY AND PASTE IT TO YOUR
 * LetterInventory.java AND AnagramSolver.java CLASSES.
 *
 * Student information for assignment:
 *
 *  On my honor, Gokul Anandaraman, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID:gna323
 *  email address:gokul.narayan@utexas.edu
 *  Grader name:
 *  Number of slip days I am using:0
 */

public class AnagramFinderTester {

    
	private static final String testCaseFileName = "testCaseAnagrams.txt";
	private static final String dictionaryFileName = "d3.txt";

    /**
     * main method that executes tests.
     * @param args Not used.
     */
    public static void main(String[] args) {

		cs314StudentTestsForLetterInventory();

        // tests on the anagram solver itself
		boolean displayAnagrams = getChoiceToDisplayAnagrams();
		AnagramSolver solver = new AnagramSolver(AnagramMain.readWords(dictionaryFileName));
		runAnagramTests(solver, displayAnagrams);
    }

    private static void cs314StudentTestsForLetterInventory() {
        // CS314 Students, delete the above tests when you turn in your assignment
        // CS314 Students add your LetterInventory tests here. 

		// Constructor(with param) tests:
		LetterInventory letterInventory1 = new LetterInventory("Gokul");
		Object expected = "gklou";
		Object actual = letterInventory1.toString();
		showTestResults(expected, actual, 1, "LetterInventory constructor(with param), and toString test");

		letterInventory1 = new LetterInventory("max");
		expected = letterInventory1.size();
		actual = 3;
		showTestResults(expected, actual, 2, "LetterInventory constructor(with param), and size test");

		// Constructor(no param) tests:
		letterInventory1 = new LetterInventory();
		expected = "";
		actual = letterInventory1.toString();
		showTestResults(expected, actual, 3, "LetterInventory constructor(no param), and toString test");

		letterInventory1 = new LetterInventory();
		expected = 0;
		actual = letterInventory1.size();
		showTestResults(expected, actual, 4, "LetterInventory constructor(no param), and size test");

		// get() method tests:
		letterInventory1 = new LetterInventory("ggggokul");
		expected = 4;
		actual = letterInventory1.get('G');
		showTestResults(expected, actual, 5, "get method test");

		letterInventory1 = new LetterInventory("GDJKJFDSSSSSSSLKHGJ!!!????l");
		expected = 7;
		actual = letterInventory1.get('S');
		showTestResults(expected, actual, 6, "get method test");

		// size method tests:
		expected = 20;
		actual = letterInventory1.size();
		showTestResults(expected, actual, 7, "size method test");

		letterInventory1 = new LetterInventory("????????,,,,,,/:::::';;G");
		expected = 1;
		actual = letterInventory1.size();
		showTestResults(expected, actual, 8, "size method test");

		// isEmpty tests:
		expected = false;
		actual = letterInventory1.isEmpty();
		showTestResults(expected, actual, 9, "isEmpty method test");

		letterInventory1 = new LetterInventory();
		expected = true;
		actual = letterInventory1.isEmpty();
		showTestResults(expected, actual, 10, "isEmpty method test");

		// toString tests:
		letterInventory1 = new LetterInventory("PatRicK");
		expected = "acikprt";
		actual = letterInventory1.toString();
		showTestResults(expected, actual, 11, "toString method test");

		letterInventory1 = new LetterInventory("KHFFHHDDSSS???>><!@@#$$$");
		expected = "ddffhhhksss";
		actual = letterInventory1.toString();
		showTestResults(expected, actual, 12, "toString method test");

		// add tests:
		LetterInventory letterInventory2 = new LetterInventory("AADDKZZYY");
		expected = 20;
		actual = letterInventory1.add(letterInventory2).size();
		showTestResults(expected, actual, 13, "add method test");

		letterInventory1 = new LetterInventory("abcd");
		letterInventory2 = new LetterInventory("DCBA");
		expected = "aabbccdd";
		actual = letterInventory1.add(letterInventory2).toString();
		showTestResults(expected, actual, 14, "add method test");

		// subtract tests:
		expected = "";
		actual = letterInventory1.subtract(letterInventory2).toString();
		showTestResults(expected, actual, 15, "subtract method test");

		letterInventory1 = new LetterInventory("gokul");
		letterInventory2 = new LetterInventory("zxx");
		expected = null;
		actual = letterInventory1.subtract(letterInventory2);
		showTestResults(expected, actual, 16, "subtract method test");

		// equals tests:
		expected = false;
		actual = letterInventory1.equals(letterInventory2);
		showTestResults(expected, actual, 17, "equals method test");

		letterInventory1 = new LetterInventory("ABCDEFGHIJKLMNOP");
		letterInventory2 = new LetterInventory("abcdefghijklmnop");
		expected = true;
		actual = letterInventory1.equals(letterInventory2);
		showTestResults(expected, actual, 18, "equals method test");

    }

    private static boolean getChoiceToDisplayAnagrams() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter y or Y to display anagrams during tests: ");
        String response = console.nextLine();
        console.close();
        return response.length() > 0 && response.toLowerCase().charAt(0) == 'y';
    }

    private static boolean showTestResults(Object expected, Object actual, int testNum, String featureTested) {
        System.out.println("Test Number " + testNum + " testing " + featureTested);
        System.out.println("Expected result: " + expected);
        System.out.println("Actual result: " + actual);
        boolean passed = (actual == null && expected == null) || (actual != null && actual.equals(expected));
        if(passed)
            System.out.println("Passed test " + testNum);
        else
            System.out.println("!!! FAILED TEST !!! " + testNum);
        System.out.println();
        return passed;
    }

    /**
     * Method to run tests on Anagram solver itself.
     * pre: the files d3.txt and testCaseAnagrams.txt are in the local directory
     * 
     * assumed format for file is
     * <NUM_TESTS>
     * <TEST_NUM>
     * <MAX_WORDS>
     * <PHRASE>
     * <NUMBER OF ANAGRAMS>
     * <ANAGRAMS>
     */
    private static void runAnagramTests(AnagramSolver solver, boolean displayAnagrams) {
        int solverTestCases = 0;
        int solverTestCasesPassed = 0;
        Stopwatch st = new Stopwatch();
        try {
            Scanner sc = new Scanner(new File(testCaseFileName));
			System.out.println(testCaseFileName);
            final int NUM_TEST_CASES = Integer.parseInt(sc.nextLine().trim());
            System.out.println(NUM_TEST_CASES);
            for(int i = 0; i < NUM_TEST_CASES; i++) {
                // expected results
                TestCase currentTest = new TestCase(sc);
                solverTestCases++;
                st.start();
                // actual results
                List<List<String>> actualAnagrams = solver.getAnagrams(currentTest.phrase, currentTest.maxWords);
                st.stop();
                if(displayAnagrams) {
                    displayAnagrams("actual anagrams", actualAnagrams);
                    displayAnagrams("expected anagrams", currentTest.anagrams);
                }


                if(checkPassOrFailTest(currentTest, actualAnagrams))
                    solverTestCasesPassed++;
                System.out.println("Time to find anagrams: " + st.time());
            }
            sc.close();
        }
        catch(IOException e) {
            System.out.println("\nProblem while running test cases on AnagramSolver. Check" +
                    " that file testCaseAnagrams.txt is in the correct location.");
            System.out.println(e);
            System.out.println("AnagramSolver test cases run: " + solverTestCases);
            System.out.println("AnagramSolver test cases failed: " + (solverTestCases - solverTestCasesPassed));
        }
        System.out.println("\nAnagramSolver test cases run: " + solverTestCases);
        System.out.println("AnagramSolver test cases failed: " + (solverTestCases - solverTestCasesPassed));
    }


    // print out all of the anagrams in a list of anagram
    private static void displayAnagrams(String type,
            List<List<String>> anagrams) {

        System.out.println("Results for " + type);
        System.out.println("num anagrams: " + anagrams.size());
        System.out.println("anagrams: ");
        for(List<String> singleAnagram : anagrams)
            System.out.println(singleAnagram);
    }


    // determine if the test passed or failed
    private static boolean checkPassOrFailTest(TestCase currentTest,
            List<List<String>> actualAnagrams) {
        
        boolean passed = true;
        System.out.println();
        System.out.println("Test number: " + currentTest.testCaseNumber);
        System.out.println("Phrase: " + currentTest.phrase);
        System.out.println("Word limit: " + currentTest.maxWords);
        System.out.println("Expected Number of Anagrams: " + currentTest.anagrams.size());
        if(actualAnagrams.equals(currentTest.anagrams)) {
            System.out.println("Passed Test");
        }
        else{
            System.out.println("\n!!! FAILED TEST CASE !!!");
            System.out.println("Recall MAXWORDS = 0 means no limit.");
            System.out.println("Expected number of anagrams: " + currentTest.anagrams.size());
            System.out.println("Actual number of anagrams:   " + actualAnagrams.size());
            if(currentTest.anagrams.size() == actualAnagrams.size()) {
                System.out.println("Sizes the same, but either a difference in anagrams or anagrams not in correct order.");
            }
            System.out.println();
            passed = false;
        }  
        return passed;
    }

    // class to handle the parameters for an anagram test 
    // and the expected result
    private static class TestCase {

        private int testCaseNumber;
        private String phrase;
        private int maxWords;
        private List<List<String>> anagrams;

        // pre: sc is positioned at the start of a test case
        private TestCase(Scanner sc) {
            testCaseNumber = Integer.parseInt(sc.nextLine().trim());
            maxWords = Integer.parseInt(sc.nextLine().trim());
            phrase = sc.nextLine().trim();
            anagrams = new ArrayList<List<String>>();
            readAndStoreAnagrams(sc);
        }

        // pre: sc is positioned at the start of the resulting anagrams
        // read in the number of anagrams and then for each anagram:
        //  - read in the line
        //  - break the line up into words
        //  - create a new list of Strings for the anagram
        //  - add each word to the anagram
        //  - add the anagram to the list of anagrams
        private void readAndStoreAnagrams(Scanner sc) {
            int numAnagrams = Integer.parseInt(sc.nextLine().trim());
            for(int j = 0; j < numAnagrams; j++){
                String[] words = sc.nextLine().split("\\s+");
                ArrayList<String> anagram = new ArrayList<String>();
                for(String st : words)
                    anagram.add(st);
                anagrams.add(anagram);
            }
            assert anagrams.size() == numAnagrams : "Wrong number of angrams read or expected";
        }
    }
}
