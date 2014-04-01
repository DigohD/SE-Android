package com.example.se_android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player extends GameObject{
	
	float speed = 2f;
	private Bitmap bmp;

	
	public Player(Bitmap bmp, float x, float y, int width, int height) {
		super(x, y, width, height);
		this.bmp = bmp;
	}

	@Override
	public void tick(float dt) {
		rect.set((int)x, (int)y, (int)x + width,(int) y + height);
		y = y + (speed);
		
	}

	@Override
	public void draw(Canvas canvas) {
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect((int)x, (int)y, (int)x + width,(int) y + height, paint);
		canvas.drawBitmap(bmp, (int)x, (int)y, null);
	}

}
