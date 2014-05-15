package com.spaceshooter.game.engine;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.background.BackGround;
import com.spaceshooter.game.object.enemy.Enemy;
import com.spaceshooter.game.object.loot.Loot;
import com.spaceshooter.game.object.player.Player;
import com.spaceshooter.game.object.weapon.Gun;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class GameObjectManager {

	public static List<GameObject> gameObjects;
	public static List<GameObject> toAdd;
	private ProjectileManager projectileManager;
	
	private static Player player;
	private	static Gun topGun;
	private static Gun bottomGun;
	
	private BackGround bg;
	private Paint paint;
	
	public GameObjectManager() {
		gameObjects = new ArrayList<GameObject>();
		toAdd = new ArrayList<GameObject>();
		if(player == null){
			player = new Player(new Vector2f(GameView.WIDTH/2, GameView.HEIGHT/2));
		}
		player.setScore(0);
		topGun = player.getTopGun();
		bottomGun = player.getBottomGun();
		bg = new BackGround();
		projectileManager = new ProjectileManager();
		paint = new Paint();
	}
	
	public static void clear(){
		gameObjects.clear();
		toAdd.clear();
		ProjectileManager.clear();
		CollisionManager.enemies.clear();
		CollisionManager.loots.clear();
	}


	/**
	 * Adds a gameobject to the gameobject list
	 * @param go the gameobject to be added
	 */
	public static void addGameObject(GameObject go){
		toAdd.add(go);
	}

	
	/**
	 * Removes a gameobject from the gameobject list
	 * @param go the gameobject to be removed
	 */
	public static void removeGameObject(GameObject go){
		//Clear the reference to the pixeldata of the bitmap
		//Much more efficient then waiting for the garbage collector to do it.
	
		if(go.getBitmap() != null)
			go.getBitmap().recycle();
		
		gameObjects.remove(go);
	}

	
	/**
	 * Removes all gameobjects that has been marked as dead
	 */
	private void clearDeadGameObjects(){
		for(int i = 0; i < gameObjects.size(); i++){
			GameObject go = gameObjects.get(i);
			if(!go.isLive()){
				removeGameObject(go);
				if (go instanceof Enemy) {
					Enemy e = (Enemy) go;
					CollisionManager.removeEnemy(e);
				}
				if (go instanceof Loot){
					Loot l = (Loot) go;
					CollisionManager.removeLoot(l);
				}
			}
		}
	}

	
	/**
	 * Updates the state of all gameobjects
	 * @param dt time step variable used for physics calculations
	 */
	public void tick(float dt){
		for(GameObject x : toAdd)
			gameObjects.add(x);
		
		toAdd.clear();
		
		clearDeadGameObjects();
		CollisionManager.collisionCheck(player);
		
		if(player.isLive()){
			player.tick(dt);
			topGun.tick(dt);
			bottomGun.tick(dt);
		}
		
		for(GameObject go : gameObjects)
			go.tick(dt);
		
		projectileManager.tick(dt);
	}
	
	/**
	 * Draws all gameobjects
	 * @param canvas the canvas used for drawing
	 * @param interpolation the interpolation factor used for calculating 
	 * the interpolated position of a dynamic object
	 */
	public void draw(Canvas canvas, float interpolation){
		for(GameObject go : gameObjects)
			go.draw(canvas, interpolation);
		
		if(player.isLive())
			player.draw(canvas, interpolation);
		
		projectileManager.draw(canvas, interpolation);
		drawPlayerUI(canvas);
	}
	
	private void drawPlayerUI(Canvas canvas){
		paint.setColor(Color.WHITE);
		canvas.drawRect(19, 19, 20 + 151, 20 + 6, paint);
		
		paint.setColor(Color.RED);
		canvas.drawRect(20, 20, 20 + 150, 20 + 5, paint);
		
		paint.setColor(Color.GREEN);
		if(player.getHp() <= 0) player.setHp(0);
		canvas.drawRect(20, 20, 20 + ((player.getHp()/player.getMaxHP())*150), 20 + 5, paint);
		
		canvas.drawText("SCORE: " + player.getScore(), 20, 42, paint);
		
		
		if(player.getCombo() == 0)
			paint.setColor(Color.RED);
		else paint.setColor(Color.GREEN);
		
		canvas.drawText("COMBO: " + player.getCombo(), 20, 62, paint);
	}

	public List<GameObject> getGameObject() {
		return gameObjects;
	}

	public static Player getPlayer() {
		return player;
	}

}
