package com.spaceshooter.game.object.enemy;

import android.graphics.Canvas;

import com.spaceshooter.game.engine.CollisionManager;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.object.Collideable;
import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.util.Vector2f;

public abstract class Enemy extends DynamicObject implements Collideable{

	public Enemy(Vector2f position) {
		super(position);
		
	}
	
	@Override
	public void tick(float dt) {
		super.tick(dt);	
	}
	
	/**
	 * Adds the enemy to the gameobject list so that it will be updated and drawn.
	 * The enemy also gets added to the list of enemies in CollisionManager so that it
	 * can be checked for collisions
	 */
	public void addToManagerLists(){
		GameObjectManager.addGameObject(this);
		CollisionManager.addEnemy(this);
	}

	@Override
	public void draw(Canvas canvas,  float interpolation) {
		super.draw(canvas, interpolation);
	}
	
	@Override
	public void collisionWith(GameObject obj) {
		if(obj instanceof Projectile){
			
		}
		
	}

}
