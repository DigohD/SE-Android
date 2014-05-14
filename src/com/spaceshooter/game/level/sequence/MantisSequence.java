package com.spaceshooter.game.level.sequence;

import com.spaceshooter.game.object.enemy.Mantis;
import com.spaceshooter.game.util.Color;

public class MantisSequence extends Sequence{
	
	public MantisSequence(String path){
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
