package game.gameevironment;

import game.Keyboard;
import game.gameevironment.players.*;
import java.util.List;


public class GameEnvironment {
	private static Computer computer;
	private static Player newPlayer;


	public GameEnvironment() {
		printWelcomeMessage();
		Computer newAI;
		System.out.println("Select difficulty: \n");
		System.out.println("Easy\nMedium\nHard\n");

		difficultyLoop:
		while (true) {
			System.out.print("Select difficulty --> ");
			String selectDifficulty = Keyboard.readInput().toLowerCase();
			switch (selectDifficulty) {
				case "easy":
					newAI = new Easy();
					break difficultyLoop;
				case "medium":
					newAI = new Medium();
					break difficultyLoop;
				case "hard":
					newAI = new Hard();
					break difficultyLoop;
				default:
					System.out.println("Please type Easy, Medium, or Hard\n");
					break;
			}
		}
		computer = newAI;
	}


	protected void runGame() {
		System.out.println("\n");
		askPlayerForCode();
		newPlayer = new Player(playerKeyboardInput());
		System.out.println("\n");
		System.out.println("Your secret code is: " + newPlayer.getSavedCode());
		computer.setSavedCode();

		if (computer.difficulty() == Difficulty.EASY) {
			easyMode();
		} else if(computer.difficulty() == Difficulty.MEDIUM) {
			mediumMode();
		} else if (computer.difficulty() == Difficulty.HARD) {
			hardMode();
		}
	}

	//Welcome message and asking the player for their secret code
	private static void printWelcomeMessage() {
		System.out.println("Hello there!!! Welcome to Bulls and Cows!\n");
	}

	private static void askPlayerForCode() {
		System.out.print("Type in your secret code --> ");
	}




	private static boolean sameCode(String codeOne, String codeTwo) {
		return codeOne.equals(codeTwo);
	}


	private static int[] counters(String codeOne, String codeTwo) {
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

	private String getComputerCode() {
		return computer.getSavedCode();
	}

	private String getPlayerCode() {
		return newPlayer.getSavedCode();
	}

	private String playerGuess() {
		return playerKeyboardInput();
	}

	//Easy game
	private void easyMode() {
		String comp = getComputerCode();
		String player = getPlayerCode();
		System.out.println(getComputerCode());
		runGuesses(comp, player);

	}
	private void mediumMode() {
		String comp = getComputerCode();
		String player = getPlayerCode();
		System.out.println(getComputerCode());
		runGuesses(comp, player);
	}

	private void hardMode() {
		String comp = getComputerCode();
		String player = getPlayerCode();
		System.out.println(getComputerCode());
		runGuesses(comp, player);
	}

	private void runGuesses(String computerSecretCode, String playerSecretCode) {
		int outerCounter = 0;
		String tempGuess = "";
		List<String> newList = null;
		while (true) {
			String computerGuess = "";
			//Player guess
			System.out.print("Type in your guess here --> ");
			String playerGuess = playerGuess();
			//Computer guess

			if (computer.difficulty() == Difficulty.HARD) {
				if (outerCounter == 0) {
					computerGuess = computer.computerGuess();
					newList = ((Hard)computer).analyzer(computerGuess, playerSecretCode, ((Hard) computer).returnList());
					tempGuess = ((Hard)computer).hardSubsequentGuesses(newList);
				} else {
					List<String> tempList = ((Hard) computer).analyzer(tempGuess, playerSecretCode, newList);
					computerGuess = ((Hard)computer).hardSubsequentGuesses(tempList);
					tempGuess = computerGuess;
					newList = tempList;
				}
			} else {
				computerGuess = computer.computerGuess();
			}

			System.out.println("You guess: " + playerGuess);
			//How many Bulls and how many Cows the player got compared to the computers code
			int[] playerCounters = counters(playerGuess, computerSecretCode);
			System.out.println("Bulls: " + playerCounters[0] + " Cows: " + playerCounters[1] + "\n");

			System.out.println("Computer guess: " + computerGuess);

			//How many Bulls and how many Cows the COMPUTER got compared to the player
			int[] computerCounters = counters(computerGuess, playerGuess);
			System.out.println("Bulls: " + computerCounters[0] + " Cows: " + computerCounters[1]);
			System.out.println("---\n");

			//And one to counter per turn
			outerCounter++;
			//Game Status
			if(sameCode(playerGuess, computerSecretCode)) {
				System.out.println("You win! :)");
				break;
			} else if (outerCounter > 7) {
				System.out.println("Draw!");
				break;
			} else if (sameCode(computerGuess, playerSecretCode)) {
				System.out.println("Computer wins!");
				break;
			}
		}
	}
}


