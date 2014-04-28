package com.spaceshooter.game.level;

import android.graphics.Canvas;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.engine.GameThread;
import com.spaceshooter.game.level.sequence.PredatorSequence;
import com.spaceshooter.game.object.enemy.Locust;
import com.spaceshooter.game.object.player.Player;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class Level {
	
	private Player player;
	private GameObjectManager gameObjectManager;
	private EnemyGenerator enemyGen;
	
	private int timer = 0;
	private int TIME;
	private int LEVEL_TIME;
	private int TPS = (int) GameThread.TARGET_TPS; 
	
	/**
	 * Creates a new level that will last for the given time
	 * @param TIME the time the level will take in minutes
	 */
	public Level(int time){
		TIME = time * TPS;
		LEVEL_TIME = TIME * TPS;
		gameObjectManager = new GameObjectManager();

		player = gameObjectManager.getPlayer();
		enemyGen = new EnemyGenerator(TIME);
		
		enemyGen.addSequence(new PredatorSequence());
		for(int i = 10; i < TIME; i+=10)
			enemyGen.addEnemyToTimeline(new Locust(new Vector2f(GameView.width, GameView.height/2)), i);
		enemyGen.generateRandomTimeLine();
	}
	
	public void tick(float dt){
		timer++;
		if(timer >= LEVEL_TIME){
			enemyGen.setUpdate(false);
		}
		
		enemyGen.tick();
		gameObjectManager.tick(dt);
	}
	
	public void draw(Canvas canvas, float interpolation){
		gameObjectManager.draw(canvas, interpolation);
	}
	
	public Player getPlayer(){
		return player;
	}

}
