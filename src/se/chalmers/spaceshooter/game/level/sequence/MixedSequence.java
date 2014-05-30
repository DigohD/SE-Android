package se.chalmers.spaceshooter.level.sequence;

import se.chalmers.spaceshooter.object.enemy.Mantis;
import se.chalmers.spaceshooter.object.enemy.Predator;
import se.chalmers.spaceshooter.util.Color;

public class MixedSequence extends Sequence{
	
	public MixedSequence(String path){
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
