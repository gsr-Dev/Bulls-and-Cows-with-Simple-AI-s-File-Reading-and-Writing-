package game.gameevironment.players;

import java.util.ArrayList;
import java.util.List;

public class Easy extends Computer {
	private String savedCode;

	//Getter and Setter for Easy Computer
	public void setSavedCode() {
		this.savedCode = super.getSecretCodeFromList();
	}

	public String getSavedCode() {
		return savedCode;
	}

	public String easyComputerGuess() {
		return super.getSecretCodeFromList();
	}


	public String toString() {
		return "This is the Easy AI";
	}
}
