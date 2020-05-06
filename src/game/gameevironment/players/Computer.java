package game.gameevironment.players;

import java.util.ArrayList;
import java.util.List;

public class Computer {
	private final List<String> codeList = new ArrayList<>();
	protected String getSecretCodeFromList() {
		String digits = "";
		for (int a = 0; a <= 9; a++) {
			for (int b = 0; b <= 9; b++) {
				for (int c = 0; c <= 9; c++) {
					for(int d = 0; d <=9; d++) {
						if (a!=b && a!=c && a!=d && b!=c && b!=d && c!=d) {
							codeList.add(digits + a + b + c + d);
						}
					}
				}
			}
		}

		int randomCode = (int) (Math.random() * codeList.size());

		return codeList.get(randomCode);
	}
}
