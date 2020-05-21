package game.gameevironment.players;

public class Player {
	private final String savedCode;

	public Player(String code) {
		this.savedCode = code;
	}

	public String getSavedCode() {
		return savedCode;
	}
}
