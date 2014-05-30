package se.chalmers.spaceshooter.game.level.sequence;

import se.chalmers.spaceshooter.game.object.enemy.Mantis;
import se.chalmers.spaceshooter.game.util.ColorRGBA;

public class MantisSequence extends Sequence {

	public MantisSequence(String path) {
		super();
		timeLimit = false;
		loadSequence(path);
		enemies.put(ColorRGBA.RED, new Mantis());
		scanSequence();
	}

	public MantisSequence() {
		super();
		timeLimit = false;
		loadRandomSequence("mantis/MantisSequence", 2);
		enemies.put(ColorRGBA.RED, new Mantis());
		scanSequence();
	}

}
