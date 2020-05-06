package game.gameevironment;

import game.Keyboard;
import game.gameevironment.players.Computer;
import game.gameevironment.players.Easy;
import game.gameevironment.players.Player;

import javax.sound.midi.Soundbank;

public class GameEnvironment {
	private final Player newPlayer;
	private final Easy newComputer;

	public GameEnvironment() {
		newPlayer = new Player();
		newComputer = new Easy();
	}

	protected void start() {
		printWelcomeMessage();
		askPlayerForCode();

		newPlayer.setSavedCode(playerKeyboardInput());
		System.out.println("Your secret code is: " + newPlayer.getSavedCode());

		newComputer.setSavedCode();
	}

	protected void runGame() {
		String computerCode = newComputer.getSavedCode();
		String playerCode = newPlayer.getSavedCode();
		String playerGuess;
		String computerGuess;
//		System.out.println(computerCode);

		int count = 0;
		do {
			//Player guess
			playerGuess = playerKeyboardInput();
			System.out.println("You guess: " + playerGuess);

			int[] playerCounters = counters(playerGuess, computerCode);
			System.out.println("Bulls: " + playerCounters[0] + " Cows: " + playerCounters[1] + "\n");

			//Computer guess
			computerGuess = newComputer.easyComputerGuess();
			System.out.println("Computer guess: " + computerGuess);

			int[] computerCounters = counters(computerGuess, playerGuess);
			System.out.println("Bulls: " + computerCounters[0] + " Cows: " + computerCounters[1]);
			System.out.println("---\n");
			count++;

			if(sameCode(playerCode, computerCode)) {
				System.out.println("You win! :)");
			} else if (count == 7) {
				System.out.println("You have no more tries... so I guess you lose :(");
			} else if (sameCode(computerCode, playerCode)) {
				System.out.println("Computer wins!");
			}
		} while (!sameCode(playerCode, computerCode));
	}

	private boolean sameCode(String codeOne, String codeTwo) {
		return codeOne.equals(codeTwo);
	}


	private int[] counters(String codeOne, String codeTwo) {
		int bulls = 0;
		int cows = 0;

		String trimmedCode = codeOne.trim();

		for (int i = 0; i < trimmedCode.length(); i++) {
			if (codeOne.charAt(i) == codeTwo.charAt(i)) {
				bulls++;
			} else {
				for (int j = 0; j < trimmedCode.length(); j++) {
					if (codeOne.charAt(i) == codeTwo.charAt(j)) {
						cows++;
					}
				}

			}
		}
		return new int[]{bulls, cows};
	}

	//Welcome message and asking the player for their secret code
	private void printWelcomeMessage() {
		System.out.println("Hello there!!! Welcome to Bulls and Cows!\n");
	}

	private void askPlayerForCode() {
		System.out.println("Type in your secret code:");
	}


	//Input validation methods for players.
	private static String playerKeyboardInput() {
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

	private static boolean stringContainsLetters(String inputString) {
		char[] charArrayOfInputtedString = inputString.toCharArray();
		for (char c : charArrayOfInputtedString) {
			if (Character.isLetter(c)) {
				return true;
			}
		}
		return false;
	}

	private static boolean stringIsNotUnique(String inputString) {
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
