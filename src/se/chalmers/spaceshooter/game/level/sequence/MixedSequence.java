package se.chalmers.spaceshooter.game.level.sequence;

import se.chalmers.spaceshooter.game.object.enemy.Mantis;
import se.chalmers.spaceshooter.game.object.enemy.Predator;
import se.chalmers.spaceshooter.game.util.ColorRGBA;

public class MixedSequence extends Sequence {
	public MixedSequence() {
		super();
		timeLimit = true;
		loadRandomSequence("mixed/MixedSequence", 4);
		enemies.put(ColorRGBA.GREEN, new Predator());
		enemies.put(ColorRGBA.RED, new Mantis());
		scanSequence();
	}

	public MixedSequence(String path) {
		super();
		timeLimit = true;
		loadSequence(path);
		enemies.put(ColorRGBA.GREEN, new Predator());
		enemies.put(ColorRGBA.RED, new Mantis());
		scanSequence();
	}
}
