package game.gameevironment.players;

import game.Keyboard;

public class Player implements SecretCode {
	private String playerName;

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

	@Override
	public String generateCode() {
		while (true) {
			String input = Keyboard.readInput();
			if (input.length() != 4) {
				System.out.println("Your code must be four unique digits");
			} else if (isAlpha(input)) {
				System.out.println("Your code must be unique digits");
			} else if (notUnique(input)) {
				System.out.println("Your digits are not unique");
			} else {
				return input;
			}
		}
	}

	private boolean isAlpha(String inputString) {
		char[] c = inputString.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if(!Character.isLetter(c[i])) {
				return false;
			}
		}
		return true;
	}

	private boolean notUnique(String inputString) {
		char[] c = inputString.toCharArray();
		for (int i = 0; i < c.length -1; i++) {
			for (int j = i + 1; j < c.length; j++) {
				if (c[i] == c[j]) {
					return true;
				}
			}
		}
		return false;
	}
}
