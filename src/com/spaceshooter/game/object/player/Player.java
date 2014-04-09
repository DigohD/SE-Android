package com.spaceshooter.game.object.player;

import android.graphics.Canvas;

import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;

public class Player extends DynamicObject{
	
	public Player(Vector2f position) {
		super(position);
		
		this.bitmap = BitmapHandler.loadBitmap("player/ship.png");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		
		speed = 15f;
		velocity = new Vector2f(0, speed);
	}

	@Override
	public void tick(float dt) {
		super.tick(dt);
	}

	@Override
	public void draw(Canvas canvas,  float interpolation) {
		super.draw(canvas, interpolation);
	}
	
}
