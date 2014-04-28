package com.spaceshooter.game.object.particle;

import android.graphics.Rect;

import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;

public abstract class Particle extends DynamicObject{

	protected int lifetime, timeLived;
	
	public Particle(Vector2f position, Vector2f velocity) {
		super(position);
		this.velocity = velocity;
	}
	
	

}
