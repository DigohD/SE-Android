package com.spaceshooter.game.level;

import android.graphics.Canvas;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.engine.GameThread;
import com.spaceshooter.game.level.sequence.PredatorSequence;
import com.spaceshooter.game.object.background.BackGround;
import com.spaceshooter.game.object.enemy.Locust;
import com.spaceshooter.game.object.player.Player;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class Level {
	
	private Player player;
	private GameObjectManager gameObjectManager;
	private EnemyGenerator enemyGen;
	
	private int timer = 0;
	private int LEVEL_TIME;
	private int TPS = (int) GameThread.TARGET_TPS; 
	
	/**
	 * Creates a new level that will last for the given time
	 * @param TIME the time the level will take in minutes
	 */
	public Level(int time){
		int TIME = time * TPS;
		LEVEL_TIME = TIME * TPS;
		gameObjectManager = new GameObjectManager();
		
		player = gameObjectManager.getPlayer();
		enemyGen = new EnemyGenerator(time);
		
		enemyGen.addSequence(new PredatorSequence());
		for(int i = 1; i < TIME; i+=2){
			enemyGen.addEnemyToTimeline(new Locust(new Vector2f(GameView.WIDTH, GameView.HEIGHT/2)), i);
		}
			
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
