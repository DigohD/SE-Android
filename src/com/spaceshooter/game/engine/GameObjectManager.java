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
import com.spaceshooter.game.object.particle.Particle;
import com.spaceshooter.game.object.player.Player;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.object.projectile.Projectile.Type;
import com.spaceshooter.game.object.weapon.Gun;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class GameObjectManager {
	
	private static List<Tickable> tickableObjects;
	private static List<Tickable> tToAdd;
	private static List<Drawable> drawableObjects;
	private static List<Drawable> dToAdd;
	
	private static Player player;
	private	static Gun topGun;
	private static Gun bottomGun;
	
	public static BackGround bg;
	
	private Paint paint;
	
	private static boolean slowTime = false;
	private int timer = 0;
	public static float slowtime = 0.35f;
	
	public static void setSlowTime(boolean sTime){
		slowTime = sTime;
	}
	
	public static boolean isSlowTime(){
		return slowTime;
	}
	
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
		slowTime = false;
	}

	public static void addGameObject(GameObject go){
		if(go instanceof Tickable){
			Tickable t = (Tickable) go;
			tToAdd.add(t);
		}
		
		if(go instanceof Drawable){
			Drawable d = (Drawable) go;
			dToAdd.add(d);
		}
		
		if (go instanceof Enemy) {
			Enemy e = (Enemy) go;
			CollisionManager.addEnemy(e);
		}
		
		if (go instanceof Loot){
			Loot l = (Loot) go;
			CollisionManager.addLoot(l);
		}
		
		if(go instanceof Projectile){
			Projectile p = (Projectile) go;
			if(p.getType() == Type.ENEMY)
				CollisionManager.addEnemyProjectile(p);
			if(p.getType() == Type.PLAYER)
				CollisionManager.addPlayerProjectile(p);
		}
	}

	public static void removeGameObject(GameObject go){
		if(go instanceof Drawable){
			Drawable d = (Drawable) go;
			//Clear the reference to the pixeldata of the bitmap
			//Much more efficient then waiting for the garbage collector to do it.
			if(d instanceof Loot){
				Loot l = (Loot) d;
				if(!l.isSaved()){
					if(d.getBitmap() != null)
						d.getBitmap().recycle();
				}
			}else{
				if(d.getBitmap() != null)
					d.getBitmap().recycle();
			}
			
			drawableObjects.remove(d);
		}
		
		if(go instanceof Tickable){
			Tickable t = (Tickable) go;
			tickableObjects.remove(t);
		}
		
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
			if(CollisionManager.getEnemyProjectiles().contains(p)){
				CollisionManager.removeEnemyProjectile(p);
			}
			if(CollisionManager.getPlayerProjectiles().contains(p))
				CollisionManager.removePlayerProjectile(p);
		}
	}

	public static void clear(){
		tickableObjects.clear();
		drawableObjects.clear();
		tToAdd.clear();
		dToAdd.clear();
		CollisionManager.clear();
	}
	
	/**
	 * Removes all gameobjects that has been marked as dead
	 */
	private void removeDeadGameObjects(){
		for(int i = 0; i < tickableObjects.size(); i++){
			Tickable t = tickableObjects.get(i);
			GameObject go = null;
			
			if(t instanceof GameObject)
				go = (GameObject) t;
			
			if(!go.isLive())
				removeGameObject(go);
		}
	}
	
	
	/**
	 * Updates the state of all tickable gameobjects
	 * @param dt time step variable used for physics calculations
	 */
	public void tick(float dt){
		//copy everything from the toaddlists in order to avoid concurrent modification error 
		for(Tickable t : tToAdd)
			tickableObjects.add(t);
		for(Drawable d : dToAdd)
			drawableObjects.add(d);
		
		tToAdd.clear();
		dToAdd.clear();
		
		removeDeadGameObjects();
		CollisionManager.collisionCheck(player);
		
		if(player.isLive()){
			player.tick(dt);
			topGun.tick(dt);
			bottomGun.tick(dt);
		}
		
		backgroundScrolling(dt);
		
		for(Tickable t : tickableObjects){
			if(t instanceof Loot && slowTime){
				Loot l = (Loot)t;
				l.tick(dt*slowtime);
			}else if(t instanceof Projectile && slowTime){
				Projectile p = (Projectile) t;
				if(p.getType() == Type.ENEMY)
					p.tick(dt*slowtime);
				else p.tick(dt);
			}else if(t instanceof Enemy && slowTime){
				Enemy e = (Enemy) t;
				e.tick(dt*slowtime);
			}else
				t.tick(dt);
			offset(t);
		}
		if(slowTime){
			timer++;
			if(timer >= 5*60) {
				slowTime = false;
				timer = 0;
			}
		}
		
	}
	
	/**
	 * Draws all drawable gameobjects
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
		
		paint.setTextSize(15);
		canvas.drawText("SCORE: " + player.getScore(), 20, 42, paint);
		
		
		if(player.getCombo() == 0)
			paint.setColor(Color.RED);
		else paint.setColor(Color.GREEN);
		
		paint.setTextSize(15);
		canvas.drawText("COMBO: " + player.getCombo(), 20, 62, paint);
	}
	
	private void backgroundScrolling(float dt){
		if(player.getPosition().y + player.getHeight() >= 300)
			bg.yScroll = true;
		
		if(player.getPosition().y + player.getHeight() < 300){
			bg.yScroll = false;
			if(bg.getPosition().y <= 0){
				bg.getPosition().y = 0;
				bg.targetPosition.y = 0;
			}
		}
		
		if(bg.getPosition().y <= -160){
			bg.getPosition().y = -160;
			bg.targetPosition.y = -160;
			bg.yScroll = false;
		}
		
		if(bg.getPosition().y < 0 && bg.getPosition().y > -160) {
			bg.yScroll = true;
			player.targetOK = false;
		}
		
		if(bg.getPosition().y <= -160 && Math.signum(player.targetVelocity.y) == -1){
			bg.yScroll = true;
		}
		
		bg.scrollY(dt, player.targetVelocity);
		
		if(bg.getPosition().y >= 0) {
			bg.getPosition().y = 0;
			bg.targetPosition.y = 0;
			bg.yScroll = false;
			player.targetOK = true;
		}
	}
	
	private void offset(Tickable t){
		if(t instanceof Enemy && t != null){
			Enemy e = (Enemy) t;
			if(bg.yScroll){
				e.getPosition().y += bg.diff.y;
			}
			if(e.getPosition().y < 0 && bg.getPosition().y > -5){
				e.getPosition().y = 2;
			}
		}
		if(t instanceof Projectile && t != null){
			Projectile e = (Projectile) t;
			if(bg.yScroll){
				e.getPosition().y += bg.diff.y;
			}
			if(e.getPosition().y < 0 && bg.getPosition().y > -5){
				e.getPosition().y = 2;
			}
		}
		
		if(t instanceof Loot && t != null){
			Loot e = (Loot) t;
			if(bg.yScroll){
				e.getPosition().y += bg.diff.y;
			}
			if(e.getPosition().y < 0 && bg.getPosition().y > -5){
				e.getPosition().y = 2;
			}
		}
		
		if(t instanceof Particle && t != null){
			Particle e = (Particle) t;
			if(bg.yScroll){
				e.getPosition().y += bg.diff.y;
			}
			if(e.getPosition().y < 0 && bg.getPosition().y > -5){
				e.getPosition().y = 2;
			}
		}
	}

	public static Player getPlayer() {
		return player;
	}

}
