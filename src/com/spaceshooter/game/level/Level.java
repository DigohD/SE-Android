package com.spaceshooter.game.level;

import android.graphics.Canvas;

import com.spaceshooter.game.engine.CollisionManager;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.engine.GameThread;

public class Level {
	
	private GameObjectManager gameObjectManager;
	private LevelCreator lvlCreator;
	private EnemyGenerator enemyGen;
	
	private static int numOfLevels = 3;
	private int timer = 0;
	private int LEVEL_TIME;
	private int time;
	private int TPS = (int) GameThread.TARGET_TPS; 
	
	private boolean levelDone = false;
	
	/**
	 * Creates a new level that will last for the given time
	 * @param TIME the time the level will take in seconds
	 */
	public Level(int time){
		this.time = time;
		LEVEL_TIME = time * TPS;
		
		gameObjectManager = new GameObjectManager();
		
		GameObjectManager.getPlayer().setScore(0);
		GameObjectManager.getPlayer().setHp(100);
		
		enemyGen = new EnemyGenerator(time);
		lvlCreator = new LevelCreator(enemyGen);
		lvlCreator.runLevel(1);
	}
	
	public void selectLevel(int level){
		enemyGen = new EnemyGenerator(time);
		enemyGen.setUpdate(true);
		new LevelCreator(enemyGen).runLevel(level);	
	}
	
	public void tick(float dt){
		timer++;
		if(timer >= LEVEL_TIME){
			if(CollisionManager.enemies.size() == 0){
				enemyGen.setUpdate(false);
				levelDone = true;
				timer = 0;
			}
		}
		
		enemyGen.tick();
		gameObjectManager.tick(dt);
	}
	
	public void draw(Canvas canvas, float interpolation){
		gameObjectManager.draw(canvas, interpolation);
	}
	
	public boolean isFinished(){
		return levelDone;
	}
	
	public void setFinished(boolean finished){
		levelDone = finished;
	}

	public static int getNumOfLevels() {
		return numOfLevels;
	}

}
