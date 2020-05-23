package game.gameevironment.player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player {
	private final String savedCode;
	private final List<String> list;
	private final Auto isAuto;



	public Player(String code) {
		this.savedCode = code;
		list = null;
		this.isAuto = Auto.NO;
	}


	public Player(String code, File file) {
		this.savedCode = code;
		this.list = supplyGuesses(file);
		this.isAuto = Auto.YES;
	}

	public Auto isAuto() {
		return isAuto;
	}

	public String getSavedCode() {
		return savedCode;
	}

	public List<String> getList() {
		return list;
	}

	public List<String> supplyGuesses(File file) {
		List<String> list = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
		} catch (IOException e) {
			System.out.println("File not found");
		}
		return list;
	}

}
