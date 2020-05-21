package game.gameevironment.players;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Hard extends Computer {
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
		return codeList.get(randomCode);
	}

	public List<String> returnList() {
		return codeList;
	}



	public List<String> analyzer(String hardAIGuess, String playersSavedCode, List<String> codesToPickFrom) {
		String string = "";
		for (int i = 0; i < 4; i++) {
			boolean notMatching = true;
			if (hardAIGuess.charAt(i) == playersSavedCode.charAt(i)) {
				string+=hardAIGuess.charAt(i);
				notMatching = false;
			}
			for (int j = 0; j < 4; j++) {
				if (hardAIGuess.charAt(i) == playersSavedCode.charAt(j) && hardAIGuess.indexOf(hardAIGuess.charAt(i)) != playersSavedCode.indexOf(hardAIGuess.charAt(i))) {
					char[] temp = playersSavedCode.toCharArray();
					Arrays.sort(temp);
					string+="["+temp[0]+temp[1]+temp[2]+temp[3]+"]";
					notMatching = false;
				}
			}
			if (notMatching) {
				string+=".";
			}
		}

		List<String> newList = new ArrayList<>();

		for (String next : codesToPickFrom) {
			if (Pattern.matches(string, next) && !next.equals(hardAIGuess)) {
					newList.add(next);
			}
		}

		return newList;
	}

	public String hardSubsequentGuesses (List<String> analyzedList) {
		int randomCode = (int) (Math.random() * analyzedList.size());
		return analyzedList.get(randomCode);
	}

	public String toString() {
		return "This is the Hard AI";
	}


	public Difficulty difficulty() {
		return Difficulty.HARD;
	}
}
