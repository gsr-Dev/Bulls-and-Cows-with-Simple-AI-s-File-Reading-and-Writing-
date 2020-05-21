package game.gameevironment.players;


public class Medium extends Computer {
	private String savedCode;


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
		String returnedCode = codeList.get(randomCode);
		codeList.remove(randomCode);
		return returnedCode;
	}

	public String toString() {
		return "This is the Medium AI";
	}

	@Override
	public Difficulty difficulty() {
		return Difficulty.MEDIUM;
	}
}
