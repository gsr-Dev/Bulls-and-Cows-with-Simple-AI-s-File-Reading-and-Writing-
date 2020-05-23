package game.gameevironment.computer;


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
		StringBuilder pattern = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			boolean notMatching = true;
			if (hardAIGuess.charAt(i) == playersSavedCode.charAt(i)) {
				pattern.append(hardAIGuess.charAt(i));
				notMatching = false;
			}
			for (int j = 0; j < 4; j++) {
				if (hardAIGuess.charAt(i) == playersSavedCode.charAt(j) && hardAIGuess.indexOf(hardAIGuess.charAt(i)) != playersSavedCode.indexOf(hardAIGuess.charAt(i))) {
					char[] temp = playersSavedCode.toCharArray();
					Arrays.sort(temp);
					pattern.append("[").append(temp[0]).append(temp[1]).append(temp[2]).append(temp[3]).append("]");
					notMatching = false;
				}
			}
			if (notMatching) {
				pattern.append(".");
			}
		}

		List<String> newList = new ArrayList<>();

		for (String next : codesToPickFrom) {
			if (Pattern.matches(pattern.toString(), next) && !next.equals(hardAIGuess)) {
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
		return "Hard AI";
	}


	public Difficulty difficulty() {
		return Difficulty.HARD;
	}
}
