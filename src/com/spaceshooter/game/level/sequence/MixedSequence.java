package com.spaceshooter.game.level.sequence;

import com.spaceshooter.game.object.enemy.Mantis;
import com.spaceshooter.game.object.enemy.Predator;
import com.spaceshooter.game.util.Color;

public class MixedSequence extends Sequence {

	public MixedSequence(String path) {
		super();
		timeLimit = true;
		loadSequence(path);
		enemies.put(Color.GREEN, new Predator());
		enemies.put(Color.RED, new Mantis());
		scanSequence();
	}

	public MixedSequence() {
		super();
		timeLimit = true;
		loadRandomSequence("mixed/MixedSequence", 4);
		enemies.put(Color.GREEN, new Predator());
		enemies.put(Color.RED, new Mantis());
		scanSequence();
	}
}
