package com.example.se_android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player extends GameObject{
	
	
	private Bitmap bmp;
	float speed = 15f;
	float sY;
	
	float curr;
	
	
	public Player(Bitmap bmp, float x, float y, int width, int height) {
		super(x, y, width, height);
		this.bmp = bmp;

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
		//canvas.drawRect((int)x, (int)y, (int)x + width,(int) y + height, paint);
		
		float start = y;
		float end = y+sY;
		float interpolatedValue = start*interpolation + (end * (1.0f - interpolation));
		//float interpolatedValue = start + interpolation*sY;
		canvas.drawBitmap(bmp, (int)x, (int)(interpolatedValue), null);
	}
	
	

}
