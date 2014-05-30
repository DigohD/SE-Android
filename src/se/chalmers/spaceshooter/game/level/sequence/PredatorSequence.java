package se.chalmers.spaceshooter.game.level.sequence;

import se.chalmers.spaceshooter.game.object.enemy.Predator;
import se.chalmers.spaceshooter.game.util.Color;

public class PredatorSequence extends Sequence {

	public PredatorSequence(String path) {
		super();
		timeLimit = true;
		loadSequence(path);
		enemies.put(Color.RED, new Predator());
		scanSequence();
	}

	public PredatorSequence() {
		super();
		timeLimit = true;
		loadRandomSequence("predator/PredatorSequence", 3);
		enemies.put(Color.RED, new Predator());
		scanSequence();
	}

}
