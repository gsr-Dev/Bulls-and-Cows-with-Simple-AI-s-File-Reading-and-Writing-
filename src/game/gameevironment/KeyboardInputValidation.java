package game.gameevironment;

import game.Keyboard;

public class KeyboardInputValidation {
	/**
	 * This method takes the players input and tells the player if their number won't work
	 * for the game
	 */
	protected String playerKeyboardInput() {
		while (true) {
			String input = Keyboard.readInput();
			if (input.length() != 4) {
				System.out.println("Your code must be four digits long, please enter again.");
			} else if (stringContainsLetters(input)) {
				System.out.println("Your code must not contain any letters, please enter again.");
			} else if (stringIsNotUnique(input)) {
				System.out.println("Your digits are not unique, please enter again.");
			} else {
				return input;
			}
		}
	}

	private boolean stringContainsLetters(String inputString) {
		char[] charArrayOfInputtedString = inputString.toCharArray();
		for (char c : charArrayOfInputtedString) {
			if (Character.isLetter(c)) {
				return true;
			}
		}
		return false;
	}

	private boolean stringIsNotUnique(String inputString) {
		char[] c = inputString.toCharArray();
		for (int i = 0; i < c.length; i++) {
			for (int j = i + 1; j < c.length; j++) {
				if (c[i] == c[j]) {
					return true;
				}
			}
		}
		return false;
	}
}
