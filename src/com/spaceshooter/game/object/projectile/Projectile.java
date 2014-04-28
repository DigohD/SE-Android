package com.spaceshooter.game.object.projectile;

import android.graphics.Rect;

import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;

public abstract class Projectile extends DynamicObject {

	public Projectile(Vector2f position, Vector2f velocity) {
		super(position);
		this.velocity = velocity;
	}
	
	

}
