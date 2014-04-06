package com.spaceshooter.game.object.player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.spaceshooter.game.object.GameObject;

public class Player extends GameObject{
	
	float speed = 15f;
	float sY;
	float currentPos = y;
	float nextPos;
	float interpolatedValue;
	
	public Player(Bitmap bitmap, float x, float y) {
		super(x, y);
		this.bitmap = bitmap;
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	}

	@Override
	public void tick(float dt) {
		rect.set((int)x, (int)y, (int)x + width,(int) y + height);
		
		sY = (speed*dt);
		y = y + sY;
	}

	@Override
	public void draw(Canvas canvas,  float interpolation) {
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL);

		currentPos = y;
		nextPos = y+sY;
		interpolatedValue = currentPos*interpolation + (nextPos * (1.0f - interpolation));
		
		canvas.drawBitmap(bitmap, (int)x, (int)(interpolatedValue), null);
	}
	
	

}
