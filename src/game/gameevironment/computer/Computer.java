package game.gameevironment.computer;
import java.util.ArrayList;
import java.util.List;

public abstract class Computer {
	protected final List<String> codeList = createCodeList();

	/**
	 *This method generates all the possible unique 4 digit codes and returns an ArrayList
	 */
	protected List<String> createCodeList () {
		final List<String> CODE_LIST = new ArrayList<>();
		String digits = "";
		for (int a = 0; a <= 9; a++) {
			for (int b = 0; b <= 9; b++) {
				for (int c = 0; c <= 9; c++) {
					for(int d = 0; d <=9; d++) {
						if (a!=b && a!=c && a!=d && b!=c && b!=d && c!=d) {
							CODE_LIST.add(digits + a + b + c + d);
						}
					}
				}
			}
		}
		return CODE_LIST;
	}

	public abstract String computerGuess();

	public abstract void setSavedCode();

	public abstract String getSavedCode();

	public abstract String toString();

	public abstract Difficulty difficulty();
}
