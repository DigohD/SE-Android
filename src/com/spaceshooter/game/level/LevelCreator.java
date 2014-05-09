package com.spaceshooter.game.level;

import java.util.HashMap;

import com.spaceshooter.game.level.sequence.MantisSequence;
import com.spaceshooter.game.level.sequence.PredatorSequence;
import com.spaceshooter.game.level.sequence.Sequence;

public class LevelCreator {
	
	private EnemyGenerator enemyGen;
	private HashMap<Integer, Sequence> seqMap;
	
	public LevelCreator(EnemyGenerator enemyGen){
		this.enemyGen = enemyGen;
		seqMap = new HashMap<Integer, Sequence>();
	}
	
	private void initLevel(int[] seqTypes){
		for(int i = 0; i < seqTypes.length; i++){
			int seqType = seqTypes[i];
			switch(seqType){
			case 0:
				seqMap.put(seqType, new PredatorSequence());
				break;
			case 1:	
				seqMap.put(seqType, new MantisSequence());
				break;
			}
		}
		
		for(Integer i : seqMap.keySet())
			enemyGen.addSequence(seqMap.get(i));
		
		enemyGen.generateRandomTimeLine();
	}
	
	public void runLevel(int level){
		switch(level){
		case 1:
			int[] seqTypes = {0,1};
			initLevel(seqTypes);
			break;
		case 2:
			int[] seqTypes2 = {0,1};
			initLevel(seqTypes2);
			break;
		case 3:
			int[] seqTypes3 = {0,1};
			initLevel(seqTypes3);
			break;
		}
	}
}
