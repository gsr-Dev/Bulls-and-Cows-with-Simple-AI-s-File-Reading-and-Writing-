package game.gameevironment;

import game.Keyboard;
import game.gameevironment.players.Computer;
import game.gameevironment.players.Player;

public class GameEnvironment {
	public void start() {
		welcomeMessage();
		askName();
		Player newPlayer = new Player(getCommand());
		returnGreeting(newPlayer.getPlayerName());
		howToPlay(newPlayer.getPlayerName());
		selectOpponent();
	}

	private String getCommand() {
		System.out.println();
		return Keyboard.readInput();
	}

	private void welcomeMessage() {
		System.out.println("Hello there!!! Welcome to Bulls and Cows!");
	}

	private void askName() {
		System.out.println("What is your name, friend?");
	}

	private void returnGreeting(String playerName) {
		System.out.println(playerName + ", is it?");
	}

	private void howToPlay(String playerName) {
		System.out.println("Well, here's how to play, " + playerName +
				": choose a secret code of four digits; each digit must be unique, got it?");
	}

	private void selectOpponent() {
		System.out.println("But first select an Opponent. Who will it be? Easy, Medium, or Hard");
		String selection = Keyboard.readInput();

		switch (selection.toLowerCase()) {
			case "easy":
				System.out.println("Easy it is.");
				break;
			case "medium":
				System.out.println("Medium it is.");
				break;
			case "hard":
				System.out.println("Hard it is.");
				break;
			default:
				System.out.println("Pick someone.");
		}
	}

}
