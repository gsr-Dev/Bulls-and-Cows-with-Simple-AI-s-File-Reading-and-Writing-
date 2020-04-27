package game.gameevironment.players;

import java.util.ArrayList;
import java.util.List;

public class Computer implements SecretCode {
	@Override
	public String generateCode() {

		String digits = "";
		List<String> codeList = new ArrayList<>();

		for (int a = 0; a <= 9; a++) {
			for (int b = 0; b <= 9; b++) {
				for (int c = 0; c <= 9; c++) {
					for(int d = 0; d <=9; d++) {
						if (a!=b && a!=c && a!=d && b!=c && b!=d && c!=d) {
							String tempString = digits + a + b + c + d;
							codeList.add(tempString);
						}
					}
				}
			}
		}

		int random = (int) (Math.random() * codeList.size());

		return codeList.get(random);
	}
}
