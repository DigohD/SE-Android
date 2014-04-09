package com.spaceshooter.game.object;

import android.graphics.Canvas;

import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.util.Vector2f;

public class DynamicObject extends GameObject{
	
	protected float speed;
	
	protected Vector2f distance = new Vector2f(0,0);
	protected Vector2f currentPosition = position;
	protected Vector2f nextPosition = new Vector2f(0,0);
	protected Vector2f interpolatedPosition = new Vector2f(0,0);
	
	protected Vector2f velocity;

	public DynamicObject(Vector2f position) {
		super(position);
	}
	
	protected void interpolate(float interpolation){
		currentPosition = position;
		nextPosition = currentPosition.add(distance);
		//predict where the new position will be
		interpolatedPosition = currentPosition.mul(interpolation).add(nextPosition.mul((1.0f - interpolation)));
	}

	@Override
	public void tick(float dt) {
		distance = velocity.mul(dt);
		position = position.add(distance);
	}
	
	@Override
	public void draw(Canvas canvas, float interpolation) {
		interpolate(interpolation);
		canvas.drawBitmap(bitmap, position.getX(), interpolatedPosition.getY(), null);
	}

}
