package com.spaceshooter.game.engine;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.spaceshooter.game.object.Drawable;
import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.Tickable;
import com.spaceshooter.game.object.background.BackGround;
import com.spaceshooter.game.object.enemy.Enemy;
import com.spaceshooter.game.object.loot.Loot;
import com.spaceshooter.game.object.player.Player;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.object.weapon.Gun;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class GameObjectManager {

	public static List<Tickable> tickableObjects;
	public static List<Tickable> tToAdd;
	public static List<Drawable> drawableObjects;
	public static List<Drawable> dToAdd;
	
	private static Player player;
	private	static Gun topGun;
	private static Gun bottomGun;
	
	private BackGround bg;
	private Paint paint;
	
	public GameObjectManager() {
		tickableObjects = new ArrayList<Tickable>();
		tToAdd = new ArrayList<Tickable>();
		drawableObjects = new ArrayList<Drawable>();
		dToAdd = new ArrayList<Drawable>();
		
		if(player == null)
			player = new Player(new Vector2f(GameView.WIDTH/2, GameView.HEIGHT/2));
		
		topGun = player.getTopGun();
		bottomGun = player.getBottomGun();
		bg = new BackGround();
		paint = new Paint();
	}
	
	public static void clear(){
		tickableObjects.clear();
		drawableObjects.clear();
		tToAdd.clear();
		dToAdd.clear();
		CollisionManager.clear();
	}


	/**
	 * Adds a gameobject to the gameobject list
	 * @param go the gameobject to be added
	 */
	public static void addGameObject(GameObject go){
		if(go instanceof Tickable){
			Tickable t = (Tickable) go;
			tToAdd.add(t);
		}
		
		if(go instanceof Drawable){
			Drawable d = (Drawable) go;
			dToAdd.add(d);
		}
	}

	
	/**
	 * Removes a gameobject from the gameobject list
	 * @param go the gameobject to be removed
	 */
	public static void removeGameObject(GameObject go){
		//Clear the reference to the pixeldata of the bitmap
		//Much more efficient then waiting for the garbage collector to do it.
	
		if(go instanceof Drawable){
			Drawable d = (Drawable) go;
			if(d.getBitmap() != null)
				d.getBitmap().recycle();
			drawableObjects.remove(d);
		}
		
		if(go instanceof Tickable){
			Tickable t = (Tickable) go;
			tickableObjects.remove(t);
		}
	}

	
	/**
	 * Removes all gameobjects that has been marked as dead
	 */
	private void clearDeadGameObjects(){
		for(int i = 0; i < tickableObjects.size(); i++){
			Tickable t = tickableObjects.get(i);
			GameObject go = null;
			if(t instanceof GameObject)
				go = (GameObject) t;
			
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
				
				if(go instanceof Projectile){
					Projectile p = (Projectile) go;
					if(CollisionManager.enemyProjectiles.contains(p)){
						CollisionManager.removeEnemyProjectile(p);
					}
					if(CollisionManager.playerProjectiles.contains(p))
						CollisionManager.removePlayerProjectile(p);
				}
				
			}
		}
	}

	
	/**
	 * Updates the state of all gameobjects
	 * @param dt time step variable used for physics calculations
	 */
	public void tick(float dt){
		for(Tickable t : tToAdd)
			tickableObjects.add(t);
		for(Drawable d : dToAdd)
			drawableObjects.add(d);
		
		tToAdd.clear();
		dToAdd.clear();
		
		clearDeadGameObjects();
		CollisionManager.collisionCheck(player);
		
		if(player.isLive()){
			player.tick(dt);
			topGun.tick(dt);
			bottomGun.tick(dt);
		}
		
		for(Tickable t : tickableObjects)
			t.tick(dt);
	}
	
	/**
	 * Draws all gameobjects
	 * @param canvas the canvas used for drawing
	 * @param interpolation the interpolation factor used for calculating 
	 * the interpolated position of a dynamic object
	 */
	public void draw(Canvas canvas, float interpolation){
		for(Drawable d : drawableObjects)
			d.draw(canvas, interpolation);
		
		if(player.isLive())
			player.draw(canvas, interpolation);
		
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

	public static Player getPlayer() {
		return player;
	}

}
