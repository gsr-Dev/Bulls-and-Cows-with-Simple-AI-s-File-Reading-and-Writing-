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
		spacer();
	}


	protected void runGame() {
		System.out.println("\n");
		askPlayerForCode();
		newPlayer = new Player(playerKeyboardInput());
		System.out.println("\nYour secret code is: " + newPlayer.getSavedCode());
		System.out.println("Computer has selected a code!");
		spacer();
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
		for (int i = 0; i < 4; i++) {
			if (codeOne.charAt(i) == codeTwo.charAt(i)) {
				bulls++;
			} else if (codeOne.contains(codeTwo.charAt(i)+"")) {
				cows++;
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
		runGuesses(comp, player);

	}
	private void mediumMode() {
		String comp = getComputerCode();
		String player = getPlayerCode();
		runGuesses(comp, player);
	}

	private void hardMode() {
		String comp = getComputerCode();
		String player = getPlayerCode();
		runGuesses(comp, player);
	}

	private void runGuesses(String computerSecretCode, String playerSecretCode) {
		int outerCounter = 0;
		String tempGuess = "";
		List<String> newList = null;
		System.out.println("GAME ON!");
		while (true) {
			String computerGuess = "";
			//Player guess
			System.out.print("Type in your guess here --> ");
			String playerGuess = playerGuess();
			System.out.println("\n");
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
			int[] computerCounters = counters(computerGuess, playerSecretCode);
			System.out.println("Bulls: " + computerCounters[0] + " Cows: " + computerCounters[1]);
			spacer();

			//And one to counter per turn
			outerCounter++;
			//Game Status
			if(sameCode(playerGuess, computerSecretCode) && outerCounter <=7) {
				System.out.println("You win! :)");
				spacer();
				break;
			} else if (sameCode(computerGuess, playerSecretCode) && outerCounter <=7) {
				System.out.println("Computer wins! :(");
				spacer();
				break;
			} else if (outerCounter == 7) {
				System.out.println("Draw!");
				spacer();
				break;
			}
		}
	}

	private void spacer() {
		System.out.println("\n------");
	}
}


