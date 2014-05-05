package com.spaceshooter.game.object.projectile;

import android.graphics.Rect;

import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.util.BitmapHandler;
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
		rect.set((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		if(this.getX() > GameView.WIDTH || this.getX() < -width)
			live = false;
	}
	
	public abstract void death();

	public float getDamage() {
		return damage;
	}

}
