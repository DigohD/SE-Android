package com.spaceshooter.game.object.player;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;

public class Player extends GameObject{
	
	private float speed = 15f;
	private Vector2f sY = new Vector2f(0,0);
	private Vector2f currentPos = new Vector2f(0,0);
	private Vector2f nextPos = new Vector2f(0,0);
	private Vector2f interpolatedPos = new Vector2f(0,0);
	
	private Vector2f velocity;
	
	public Player(Vector2f position) {
		super(position);
		this.bitmap = BitmapHandler.loadBitmap("player/ship.png");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		velocity = new Vector2f(0, speed);
	}

	@Override
	public void tick(float dt) {
		sY = velocity.mul(dt);
		position = position.add(sY);
	}

	@Override
	public void draw(Canvas canvas,  float interpolation) {
		currentPos = position;
		nextPos = currentPos.add(sY);
 		interpolatedPos = currentPos.mul(interpolation).add(nextPos.mul((1.0f - interpolation)));

//		interpolatedValue = currentPos*interpolation + (nextPos * (1.0f - interpolation));
		
		canvas.drawBitmap(bitmap, (int)position.getX(), (int)interpolatedPos.getY(), null);
	}
	
}
