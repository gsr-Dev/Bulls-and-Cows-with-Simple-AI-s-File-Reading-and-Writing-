package game.gameevironment.computer;

public class Easy extends Computer {
	private String savedCode;


	//Getter and Setter for Easy Computer
	@Override
	public void setSavedCode() {
		int randomCode = (int) (Math.random() * codeList.size());
		this.savedCode = codeList.get(randomCode);
	}

	@Override
	public String getSavedCode() {
		return savedCode;
	}

	@Override
	public String computerGuess() {
		int randomCode = (int) (Math.random() * codeList.size());
		return codeList.get(randomCode);
	}

	public String toString() {
		return "Easy AI";
	}

	@Override
	public Difficulty difficulty() {
		return Difficulty.EASY;
	}
}
