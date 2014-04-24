package com.spaceshooter.game.object.player;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.engine.CollisionManager;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.object.Collideable;
import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.enemy.Enemy;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.object.projectile.RedPlasma;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;

public class Player extends DynamicObject implements Collideable{
	
	private Vector2f targetPos = new Vector2f(0,0);
	private boolean update = false;
	private int score = 0;
	private float steps = 20;
	
	private int reload;
	
	public Player(Vector2f position) {
		super(position);
		
		this.bitmap = BitmapHandler.loadBitmap("player/ship");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		
		speedX = 0;
		speedY = 15f;
		
		velocity = new Vector2f(speedX, speedY);
	}
	
	public void setTargetPos(float x, float y){
		targetPos = new Vector2f(x, y);
		update = true;
	}
	
	@Override
	public void tick(float dt) {
		super.tick(dt);
		if(update){
			Vector2f diff = targetPos.sub(position);
			distance = velocity.mul(dt);
			position = position.add(distance.add(diff.div(steps)));
			
			reload++;
			if(reload > 100){
				reload = 0;
				new RedPlasma(position);
			}
		}
	}

	@Override
	public void draw(Canvas canvas,  float interpolation) {
		super.draw(canvas, interpolation);
	}
	
	@Override
	public void collisionWith(GameObject obj) {
		if(obj instanceof Enemy){
			Enemy e = (Enemy) obj;
			CollisionManager.removeEnemy(e);
			GameObjectManager.removeGameObject(e);
			score += 10;
		}
		
		if(obj instanceof Projectile){
			
		}
		
	}
	
	public void decreaseSteps(float amount){
		steps -= amount;
	}
	
	public void increaseSteps(float amount){
		steps += amount;
	}
	
	public float getSteps(){
		return steps;
	}
	
	public void setScore(int value){
		score = score + value;
	}
	
	public int getScore(){
		return score;
	}

	
	
}
