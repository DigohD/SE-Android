package com.spaceshooter.game.level;

import java.util.HashMap;

import com.spaceshooter.game.level.sequence.PredatorSequence;
import com.spaceshooter.game.level.sequence.Sequence;
import com.spaceshooter.game.object.enemy.Locust;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

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
				
			}
		}
		
		for(Integer i : seqMap.keySet())
			enemyGen.addSequence(seqMap.get(i));
		
		enemyGen.generateRandomTimeLine();
	}
	
	public void runLevel(int level){
		switch(level){
		case 1:
			int[] seqTypes = {0};
			initLevel(seqTypes);
//			for(int i = 1; i < enemyGen.getTime(); i+=2)
//				enemyGen.addEnemyToTimeline(new Locust(new Vector2f(GameView.WIDTH, GameView.HEIGHT/2)), i);
		
			break;
		case 2:
			int[] seqTypes2 = {0};
			initLevel(seqTypes2);
			break;
		case 3:
			int[] seqTypes3 = {0};
			initLevel(seqTypes3);
			break;
		}
	}
}
