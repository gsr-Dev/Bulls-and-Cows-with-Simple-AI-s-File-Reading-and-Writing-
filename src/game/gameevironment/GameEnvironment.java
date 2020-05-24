package game.gameevironment;

import game.Keyboard;
import game.gameevironment.computer.*;
import game.gameevironment.player.Auto;
import game.gameevironment.player.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;


public class GameEnvironment {
	private Computer computer;
	private Player newPlayer;


	public GameEnvironment() {
		printWelcomeMessage();
	}

	/**
	This Method Runs the game
	 **/
	protected void runGame() {
		newComputer();
		newPlayer();
		System.out.println("\nYour secret code is: " + newPlayer.getSavedCode());
		computer.setSavedCode();

		if (computer.difficulty() == Difficulty.EASY) {
			System.out.println("EASY AI has selected a code!\n");
		} else if (computer.difficulty() == Difficulty.MEDIUM) {
			System.out.println("MEDIUM AI has selected a code!\n");
		} else if (computer.difficulty() == Difficulty.HARD) {
			System.out.println("HARD AI has selected a code!\n");
		}
		runGuesses(computer.getSavedCode(), newPlayer.getSavedCode());
	}

	private void printWelcomeMessage() {
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
			} else if (codeOne.contains(codeTwo.charAt(i) + "")) {
				cows++;
			}
		}
		return new int[]{bulls, cows};
	}

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
		spacer();
	}

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
						newPlayer = new Player(playerKeyboardInput(), newFile);
						break outerLoop;
					} else {
						System.out.println("Please type in a valid file name\n");
					}
				}
			} else if (input.toLowerCase().equals("n")) {
				askPlayerForCode();
				newPlayer = new Player(playerKeyboardInput());
				break;
			} else {
				System.out.println("Please type in (Y/N)");
			}
		}

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



	private String playerGuess() {
		return playerKeyboardInput();
	}

	private void runGuesses(String computerSecretCode, String playerSecretCode) {
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
		System.out.println("Do you wish to save the results (Y/N)?");
		outerLoop:
		while(true) {
			String input = Keyboard.readInput();
			if (input.toLowerCase().equals("y")) {
				while(true) {
					System.out.println("Type in the name of a text file (.txt)");
					String fileName = Keyboard.readInput();
					if(fileName.endsWith(".txt")) {
						createSaveFile(fileName,
								playerSecretCode,
								computerSecretCode,
								storedPlayerGuesses,
								storedComputerGuesses,
								storedPlayerBulls,
								storedPlayerCows,
								storedComputerBulls,
								storedComputerCows,
								winState);
						break outerLoop;
					} else {
						System.out.println("Please use a .txt file extension");
					}
				}
			} else if (input.toLowerCase().equals("n")) {
				break;
			} else {
				System.out.println("Please type (Y/N)");
			}
		}
	}

	private void createSaveFile(String fileName,
								String playerCode,
								String computerCode,
								HashMap<Integer, String> storedPlayerGuesses,
								HashMap<Integer, String> storedComputerGuesses,
								HashMap<Integer, Integer> storedPlayerBulls,
								HashMap<Integer, Integer> storedPlayerCows,
								HashMap<Integer, Integer> storedComputerBulls,
								HashMap<Integer, Integer> storedComputerCows,
								String winState) {

		try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
			writer.println("Bulls & Cows game result.");
			writer.println("Your code: " + playerCode);
			writer.println("Computer's code: " + computerCode);
			writer.println("---");
			for (int i = 0; i < storedPlayerGuesses.size(); i++) {
				String pBulls = (storedPlayerBulls.get(i) == 1) ? "bull" : "bulls";
				String pCows = (storedPlayerCows.get(i) == 1) ? "cow" : "cows";
				String cBulls = (storedComputerBulls.get(i) == 1) ? "bull" : "bulls";
				String cCows = (storedComputerCows.get(i) == 1) ? "cow" : "cows";

				writer.println("Turn " + (i + 1) +":");
				writer.println("You guessed " + storedPlayerGuesses.get(i) + ", scoring " + storedPlayerBulls.get(i) + " " + pBulls + " and " + storedPlayerCows.get(i) + " " + pCows);
				writer.println("Computer guessed " + storedComputerGuesses.get(i) + ", scoring " + storedComputerBulls.get(i) + " " + cBulls + " and " + storedComputerCows.get(i) + " " + cCows);
				writer.println("---");
			}
			writer.println(winState);

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void spacer() {
		System.out.println("\n------");
	}
}


