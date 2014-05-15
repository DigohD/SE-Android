package com.spaceshooter.game.level;

import android.graphics.Canvas;

import com.spaceshooter.game.engine.CollisionManager;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.engine.GameThread;
import com.spaceshooter.game.object.enemy.Asteroid;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class Level {
	
	private final static int numOfLevels = 3;
	private int timer = 0;
	private int LEVEL_TIME;

	private boolean levelDone = false;
	
	private GameObjectManager gameObjectManager;
	private EnemyGenerator enemyGen;
	

	/**
	 * Creates a new level that will last for the given time
	 * @param TIME the time the level will take in seconds
	 */
	public Level(int time){
		LEVEL_TIME = time * (int) GameThread.TARGET_TPS;
		
		gameObjectManager = new GameObjectManager();
		
		GameObjectManager.getPlayer().setScore(0);
		GameObjectManager.getPlayer().setHp(100);
	}
	
	public void startLevel(int level){
		int time = LEVEL_TIME / (int) GameThread.TARGET_TPS;
		enemyGen = new EnemyGenerator(time);
		enemyGen.setUpdate(true);
		new LevelCreator(enemyGen).runLevel(level);	
	}
	
	public void tick(float dt){
		timer++;
		if(timer >= LEVEL_TIME){
			enemyGen.setUpdate(false);
			if(CollisionManager.enemies.size() == 0){
				levelDone = true;
				timer = 0;
			}
		}
		if(enemyGen.isUpdate()){
			if(timer % 12 == 0){
				float y = Randomizer.getFloat(10, 790);
				new Asteroid(new Vector2f(GameView.WIDTH,y)).init();
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
