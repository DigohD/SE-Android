package com.example.se_android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player extends GameObject{
	
	
	float speed = 15.0f;
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
	
	}

	@Override
	public void tick(float dt) {
		rect.set((int)x, (int)y, (int)x + width,(int) y + height);
		y = y + (speed*dt);
		
	}

	@Override
	public void draw(Canvas canvas) {
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect((int)x, (int)y, (int)x + width,(int) y + height, paint);
	}

}
