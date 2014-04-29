package com.spaceshooter.game.engine;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.enemy.Enemy;
import com.spaceshooter.game.object.player.Player;
import com.spaceshooter.game.util.Vector2f;

public class GameObjectManager {

	public static List<GameObject> gameObjects;
	public static List<GameObject> toAdd;
	private ProjectileManager projectileManager;
	private Player player;
	private Paint paint;

	public GameObjectManager() {
		player = new Player(new Vector2f(40, 40));
		gameObjects = new ArrayList<GameObject>();
		toAdd = new ArrayList<GameObject>();
		projectileManager = new ProjectileManager();
		paint = new Paint();
	}
	
	public static void clear(){
		gameObjects.clear();
		ProjectileManager.playerProjectiles.clear();
		ProjectileManager.enemyProjectiles.clear();
		CollisionManager.enemies.clear();
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
					player.setScore(10);
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
		player.tick(dt);
		
		
		
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
		player.draw(canvas, interpolation);
		
		for(GameObject go : gameObjects)
			go.draw(canvas, interpolation);
		
		projectileManager.draw(canvas, interpolation);

		paint.setColor(Color.RED);
		canvas.drawText("SCORE: " + player.getScore(), 20, 20, paint);
	}

	public List<GameObject> getGameObject() {
		return gameObjects;
	}

	public Player getPlayer() {
		return player;
	}

}
