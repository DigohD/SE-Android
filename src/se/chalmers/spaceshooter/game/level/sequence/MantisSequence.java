package se.chalmers.spaceshooter.level.sequence;

import se.chalmers.spaceshooter.object.enemy.Mantis;
import se.chalmers.spaceshooter.util.Color;

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
