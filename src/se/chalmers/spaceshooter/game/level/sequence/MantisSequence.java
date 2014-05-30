package se.chalmers.spaceshooter.game.level.sequence;

import se.chalmers.spaceshooter.game.object.enemy.Mantis;

public class MantisSequence extends Sequence {

	public MantisSequence(String path) {
		super();
		timeLimit = false;
		loadSequence(path);
		enemies.put(Color.RED, new Mantis());
		scanSequence();
	}

	public MantisSequence() {
		super();
		timeLimit = false;
		loadRandomSequence("mantis/MantisSequence", 2);
		enemies.put(Color.RED, new Mantis());
		scanSequence();
	}

}
