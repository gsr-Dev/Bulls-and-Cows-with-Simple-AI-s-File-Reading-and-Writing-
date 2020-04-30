package game.gameevironment;

import game.Keyboard;
import game.gameevironment.players.Computer;
import game.gameevironment.players.Player;

import java.util.*;

public class GameEnvironment {
	private final Player newPlayer;
	private final Computer newComputer;

	public GameEnvironment() {
		newPlayer = new Player();
		newComputer = new Computer();
	}

	protected void start() {
		welcomeMessage();
		getAndSetNewPlayer();
	}

	protected void runGame() {
		askForCode();
		String computerCode = newComputer.generateCode();
		System.out.println(computerCode);
		String playerCode;
		int count = 0;
		do {
			playerCode = newPlayer.generateCode();
			int[] counters = counters(playerCode, computerCode);
			System.out.println("Bulls: " + counters[0] + " Cows: " + counters[1]);
			count++;

			if(sameCode(playerCode, computerCode)) {
				System.out.println("You win!");
			} else if (count == 7) {
				System.out.println("You took too many tries :(");
			}
		} while (!sameCode(playerCode, computerCode));
	}

	private void welcomeMessage() {
		System.out.println("Hello there!!! Welcome to Bulls and Cows!\n");
	}

	private void getAndSetNewPlayer() {
		System.out.println("What is your name?");
		while (true) {
			String enterNewPlayerName = Keyboard.readInput();
			if (enterNewPlayerName.isBlank()) {
				System.out.println("Please type in a name.\n");
			} else {
				newPlayer.setPlayerName(enterNewPlayerName);
				System.out.println(newPlayer.getPlayerName() + ", is it?\n");
				break;
			}
		}
	}

	private void askForCode() {
		System.out.println("Type in your secret code");
	}

	private boolean sameCode(String playerCode, String computerCode) {
		return playerCode.equals(computerCode);
	}

	private int[] counters(String playerCode, String computerCode) {
		int bulls = 0;
		int cows = 0;

		String p = playerCode.trim();
		String c = computerCode.trim();


		for (int i = 0; i < c.length(); i++) {
			if (playerCode.charAt(i) == computerCode.charAt(i)) {
				bulls++;
			} else {
				for (int j = 0; j < p.length(); j++) {
					if (playerCode.charAt(i) == computerCode.charAt(j)) {
						cows++;
					}
				}

			}
		}
		return new int[]{bulls, cows};
	}
}
