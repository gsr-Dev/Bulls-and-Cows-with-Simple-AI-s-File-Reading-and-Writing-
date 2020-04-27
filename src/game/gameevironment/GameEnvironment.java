package game.gameevironment;

import game.gameevironment.players.Computer;

public class GameEnvironment {
	public void start() {
		Computer test = new Computer();
		System.out.println(test.generateCode());


	}
}
