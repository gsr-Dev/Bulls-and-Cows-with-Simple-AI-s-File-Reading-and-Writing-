package game.gameevironment.players;


import game.Keyboard;

public class Player implements SecretCode {
	private String playerName;

	public Player(String playerName) {
		this.playerName = playerName;
	}


	public String getPlayerName() {
		return playerName;
	}

	@Override
	public String generateCode() {
		return Keyboard.readInput();
	}
}
