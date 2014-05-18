package com.spaceshooter.game.object.projectile;

import android.graphics.Rect;

import com.spaceshooter.game.object.Collideable;
import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.object.enemy.Enemy;
import com.spaceshooter.game.object.player.Player;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public abstract class Projectile extends DynamicObject implements Collideable{

	protected float damage;
	protected Rect rect;
	
	public enum Type{
		PLAYER, ENEMY;
	}
	
	protected Type type;
	
	public Projectile(Vector2f position, Vector2f velocity) {
		super(position);
		this.velocity = velocity;
	}
	
	public abstract void death();
	
	@Override
	public void tick(float dt){
		if(isOutOfBound())
			live = false;
		
		rect.set((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		move(dt);
	}
	
	public boolean isOutOfBound(){
		return (getX() >= GameView.WIDTH || getX() <= -width || getY() >= GameView.HEIGHT || getY() <= -height);
	}
	
	@Override
	public void collisionWith(Collideable obj) {
		if(obj instanceof Player){
			if(live){
				death();
				live = false;
			}
		}
		
		if(obj instanceof Enemy){
			if(live){
				death();
				live = false;
			}
		}
	}
	
	public float getDamage() {
		return damage;
	}
	
	public Rect getRect(){
		return rect;
	}
	
	public Type getType(){
		return type;
	}

}
