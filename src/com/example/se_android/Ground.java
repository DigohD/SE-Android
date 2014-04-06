package com.example.se_android;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ground extends GameObject{
	

	public Ground(float x, float y, int width, int height) {
		super(x, y, width, height);
		
	}

	@Override
	public void tick(float dt, float interpolation) {
		
		
	}

	@Override
	public void draw(Canvas canvas,  float interpolation) {
		paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(rect, paint);
	}

}
