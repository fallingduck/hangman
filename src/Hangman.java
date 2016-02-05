import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hangman {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		ArrayList<String> wordbuilder = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader("hangman.txt"))) {
			String line;
		    while ((line = br.readLine()) != null) {
		    	wordbuilder.add(line.trim().toUpperCase());
		    }
		}
		
		String[] wordlist = wordbuilder.toArray(new String[wordbuilder.size()]);
		Random rand = new Random();
		Scanner input = new Scanner(System.in);
		
		String chosenWord;
		StringBuilder userWord;
		String rawInput;
		int lives;
		boolean won;
		char userChar;
		
		while (true) {
			lives = 5;
			won = false;
			chosenWord = chooseWord(wordlist, rand);
			userWord = initUserWord(chosenWord);
			System.out.println("Welcome to Hangman!");
			while (lives > 0) {
				printStatus(userWord, lives);
				rawInput = input.nextLine();
				if (rawInput.length() > 0) {
					userChar = Character.toUpperCase(rawInput.charAt(0));
				} else {
					continue;
				}
				if (evalUserInput(userChar, chosenWord, userWord)) {
					System.out.println("Haw haw haw!");
				} else {
					System.out.println("Try again, putz!");
					lives--;
				}
				if (userHasWon(userWord)) {
					won = true;
					break;
				}
			}
			if (won) {
				System.out.println("\nYou have won!");
			} else {
				System.out.println("\nYou are a loser!");
			}
			System.out.println("The word was: " + chosenWord);
			System.out.println("Rematch?");
			rawInput = input.nextLine();
			if (rawInput.length() > 0) {
				userChar = Character.toUpperCase(rawInput.charAt(0));
			} else {
				userChar = 'N';
			}
			if (userChar == 'N') {
				break;
			}
		}
		
		System.out.println("\nGoodbye!");
		input.close();
	}

	private static boolean userHasWon(StringBuilder userWord) {
		for (int i = 0; i < userWord.length(); i++) {
			if (userWord.charAt(i) == '_') {
				return false;
			}
		}
		return true;
	}

	private static boolean evalUserInput(char userChar, String chosenWord,
			                             StringBuilder userWord) {
		boolean found = false;
		for (int i = 0; i < chosenWord.length(); i++) {
			if (userChar == chosenWord.charAt(i)) {
				found = true;
				userWord.setCharAt(i, userChar);
			}
		}
		return found;
	}

	private static void printStatus(StringBuilder userWord, int lives) {
		System.out.println("\n\n");
		System.out.println(userWord);
		System.out.println("Lives: " + lives);
		System.out.print("\nEnter a character: ");
	}

	private static StringBuilder initUserWord(String chosenWord) {
		StringBuilder result = new StringBuilder(chosenWord);
		for (int i = 0; i < result.length(); i++) {
			result.setCharAt(i, '_');
		}
		return result;
	}

	private static String chooseWord(String[] wordlist, Random rand) {
		return wordlist[rand.nextInt(wordlist.length)];
	}

}
