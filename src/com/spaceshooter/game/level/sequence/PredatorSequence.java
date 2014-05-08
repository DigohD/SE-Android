package com.spaceshooter.game.level.sequence;

import com.spaceshooter.game.object.enemy.Predator;
import com.spaceshooter.game.util.Color;

public class PredatorSequence extends Sequence {
	
	public PredatorSequence(String path){
		super();
		loadSequence(path);
		enemies.put(Color.RED, new Predator());
		scanSequence();
	}
	
	public PredatorSequence() {
		super();
		loadRandomSequence("level1/PredatorSequence", 3);
		enemies.put(Color.RED, new Predator());
		scanSequence();
	}
	
}
