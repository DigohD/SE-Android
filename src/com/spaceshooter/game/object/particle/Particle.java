package com.spaceshooter.game.object.particle;

import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.util.Vector2f;

public class Particle extends DynamicObject{
	
	protected int lifetime, timeLived;
	
	public Particle(Vector2f position, Vector2f velocity) {
		super(position);
		this.velocity = velocity;
	}
}
