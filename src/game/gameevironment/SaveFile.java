package game.gameevironment;

import game.Keyboard;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class SaveFile {

	/**
	 * This method asks whether or not the player wants to save a game results text file
	 */
	protected void saveResults(String playerSecretCode, String computerSecretCode, HashMap<Integer, String> storedPlayerGuesses, HashMap<Integer, String> storedComputerGuesses, HashMap<Integer, Integer> storedPlayerBulls, HashMap<Integer, Integer> storedPlayerCows, HashMap<Integer, Integer> storedComputerBulls, HashMap<Integer, Integer> storedComputerCows, String winState) {
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

	/**
	 * This method creates the save file
	 */
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
}
