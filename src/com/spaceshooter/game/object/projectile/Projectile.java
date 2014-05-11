package com.spaceshooter.game.object.projectile;

import android.graphics.Canvas;

import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public abstract class Projectile extends DynamicObject {

	protected float damage;
	
	public Projectile(Vector2f position, Vector2f velocity) {
		super(position);
		this.velocity = velocity;
	}
	
	@Override
	public void tick(float dt){
		if(isOutOfBound())
			live = false;
		
		rect.set((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		move(dt);
	}
	
	@Override
	public void draw(Canvas canvas,  float interpolation) {
		super.draw(canvas, interpolation);
	}
	
	public boolean isOutOfBound(){
		return getX() >= GameView.WIDTH || getX() <= -width || getY() >= GameView.HEIGHT || getY() <= -height;
	}
	
	public abstract void death();

	public float getDamage() {
		return damage;
	}

}
