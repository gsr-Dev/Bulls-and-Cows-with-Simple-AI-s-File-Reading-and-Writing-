package game.gameevironment;

import game.Keyboard;
import game.gameevironment.computer.*;
import game.gameevironment.player.Auto;
import game.gameevironment.player.Player;

import java.io.File;
import java.util.HashMap;
import java.util.List;


public class GameEnvironment {
	private Computer computer;
	private Player newPlayer;

	private final KeyboardInputValidation keyboardInputValidation = new KeyboardInputValidation();
	private final SaveFile createSaveFile = new SaveFile();

	public GameEnvironment() {
		printWelcomeMessage();
	}

	private void printWelcomeMessage() {
		System.out.println("Hello there!!! Welcome to Bulls and Cows!\n");
	}

	private static void askPlayerForCode() {
		System.out.print("Type in your secret code --> ");
	}

	private void spacer() {
		System.out.println("\n------");
	}

	/**
	 * This method setups up the two players to play the game. This method is what is used by the main method
	 * in the Main Class when you create a GameEnvironment object.
	 **/
	protected void setUpGame() {
		newComputer();
		newPlayer();
		if (computer.difficulty() == Difficulty.EASY) {
			System.out.println("EASY AI has selected a code!\n");
		} else if (computer.difficulty() == Difficulty.MEDIUM) {
			System.out.println("MEDIUM AI has selected a code!\n");
		} else if (computer.difficulty() == Difficulty.HARD) {
			System.out.println("HARD AI has selected a code!\n");
		}
		runGame(computer.getSavedCode(), newPlayer.getSavedCode());
	}

	/**
	 * This method creates a new Computer opponent for the player to face
	 **/
	private void newComputer() {
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
		computer.setSavedCode();
		spacer();
	}

	/**
	 * This method creates a new Player to play the game
	 **/
	private void newPlayer() {
		System.out.println("Use auto-guess (Y/N)?");
		outerLoop:
		while (true) {
			String input = Keyboard.readInput();
			if (input.toLowerCase().equals("y")) {
				while (true) {
					System.out.println("Supply a .txt file");
					String fileName = Keyboard.readInput();
					File newFile = new File(fileName);
					if (newFile.exists()) {
						askPlayerForCode();
						newPlayer = new Player(keyboardInputValidation.playerKeyboardInput(), newFile);
						break outerLoop;
					} else {
						System.out.println("Please type in a valid file name\n");
					}
				}
			} else if (input.toLowerCase().equals("n")) {
				askPlayerForCode();
				newPlayer = new Player(keyboardInputValidation.playerKeyboardInput());
				break;
			} else {
				System.out.println("Please type in (Y/N)");
			}
		}
		System.out.println("\nYour secret code is: " + newPlayer.getSavedCode());
	}

	private String playerGuess() {
		return keyboardInputValidation.playerKeyboardInput();
	}

	/**
	 * The sameCode method compares the two opponents codes and compares if they are the same
	 **/
	private static boolean sameCode(String codeOne, String codeTwo) {
		return codeOne.equals(codeTwo);
	}

	/**
	 * This method does the numbers whenever the players get a bull or a cow and saves the results to an
	 * array
	 **/
	private static int[] counters(String codeOne, String codeTwo) {
		int bulls = 0;
		int cows = 0;
		for (int i = 0; i < 4; i++) {
			if (codeOne.charAt(i) == codeTwo.charAt(i)) {
				bulls++;
			} else if (codeOne.contains(codeTwo.charAt(i) + "")) {
				cows++;
			}
		}
		return new int[]{bulls, cows};
	}

	/**
	 * This method runs the game for the players and has differing logic
	 * depending on the difficulty of the computer. It also asks the player if they want
	 * to save their results to a file at the end of the game
	 **/
	private void runGame(String computerSecretCode, String playerSecretCode) {
		int outerCounter = 0;
		String tempGuess = "";
		List<String> newList = null;

		String winState;

		HashMap<Integer, String> storedPlayerGuesses = new HashMap<>();
		HashMap<Integer, String> storedComputerGuesses = new HashMap<>();

		HashMap<Integer, Integer> storedPlayerBulls= new HashMap<>();
		HashMap<Integer, Integer> storedPlayerCows = new HashMap<>();
		HashMap<Integer, Integer> storedComputerBulls = new HashMap<>();
		HashMap<Integer, Integer> storedComputerCows = new HashMap<>();


		System.out.println("GAME ON!");
		while (true) {
			String computerGuess;
			String playerGuess = "";

			if (newPlayer.isAuto() == Auto.YES) {
				List<String> autoList = newPlayer.getList();
				if (autoList.size() > outerCounter) {
					playerGuess = autoList.get(outerCounter);
				} else {
					System.out.print("Type in your guess here --> ");
					playerGuess = playerGuess();
				}
			} else if (newPlayer.isAuto() == Auto.NO) {
				System.out.print("Type in your guess here --> ");
				playerGuess = playerGuess();
			}
			System.out.println("\n");

			if (computer.difficulty() == Difficulty.HARD) {
				if (outerCounter == 0) {
					computerGuess = computer.computerGuess();
					newList = ((Hard) computer).analyzer(computerGuess, playerSecretCode, ((Hard) computer).returnList());
					tempGuess = ((Hard) computer).hardSubsequentGuesses(newList);
				} else {
					List<String> tempList = ((Hard) computer).analyzer(tempGuess, playerSecretCode, newList);
					computerGuess = ((Hard) computer).hardSubsequentGuesses(tempList);
					tempGuess = computerGuess;
					newList = tempList;
				}
			} else {
				computerGuess = computer.computerGuess();
			}

			System.out.println("You guess: " + playerGuess);
			int[] playerCounters = counters(playerGuess, computerSecretCode);
			System.out.println("Bulls: " + playerCounters[0] + " Cows: " + playerCounters[1] + "\n");

			System.out.println("Computer guess: " + computerGuess);
			int[] computerCounters = counters(computerGuess, playerSecretCode);
			System.out.println("Bulls: " + computerCounters[0] + " Cows: " + computerCounters[1]);
			spacer();


			//data for saved file
			storedPlayerBulls.put(outerCounter, playerCounters[0]);
			storedPlayerCows.put(outerCounter, playerCounters[1]);
			storedComputerBulls.put(outerCounter, computerCounters[0]);
			storedComputerCows.put(outerCounter, computerCounters[1]);

			storedPlayerGuesses.put(outerCounter, playerGuess);
			storedComputerGuesses.put(outerCounter, computerGuess);
			outerCounter++;


			if (sameCode(playerGuess, computerSecretCode) && outerCounter <= 7) {
				winState = "You win! :)";
				System.out.println(winState);
				break;
			} else if (sameCode(computerGuess, playerSecretCode) && outerCounter <= 7) {
				winState = computer.toString() + " wins! :(";
				System.out.println(winState);
				break;
			} else if (outerCounter == 7) {
				winState = "Draw!";
				System.out.println(winState);
				break;
			}
		}
		createSaveFile.saveResults(playerSecretCode, computerSecretCode, storedPlayerGuesses, storedComputerGuesses,
				storedPlayerBulls, storedPlayerCows, storedComputerBulls, storedComputerCows, winState);
	}
}


