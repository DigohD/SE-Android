package com.spaceshooter.game.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.spaceshooter.game.util.Vector2f;

public abstract class DynamicObject extends GameObject implements Tickable, Drawable{

	protected Bitmap bitmap;
	
	protected float speedX, speedY;
	protected Vector2f distance = new Vector2f(0, 0);
	protected Vector2f currentPosition = position;
	protected Vector2f nextPosition = new Vector2f(0, 0);
	protected Vector2f interpolatedPosition = new Vector2f(0, 0);
	protected Vector2f velocity;

	public DynamicObject(Vector2f position) {
		super(position);
	}

	protected float approach(float target, float current, float dt){
		float diff = target - current;
		if(diff > dt)
			return current + dt;
		if(diff < -dt)
			return current - dt;
		return target;
	}
	
	protected void interpolate(float interpolation) {
		currentPosition = position;
		nextPosition = currentPosition.add(distance);
		// predict where the new position to draw will be
		interpolatedPosition = currentPosition.mul(interpolation).add(
				nextPosition.mul((1.0f - interpolation)));
	}
	
	public void move(float dt){
		distance = velocity.mul(dt);
		position = position.add(distance);
	}
	
	@Override
	public void tick(float dt){
		
	}

	@Override
	public void draw(Canvas canvas, float interpolation) {
		interpolate(interpolation);
		canvas.drawBitmap(bitmap, interpolatedPosition.x, interpolatedPosition.y, null);
	}

	@Override
	public Bitmap getBitmap(){
		return bitmap;
	}
	
	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

}
