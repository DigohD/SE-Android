package com.example.se_android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player extends GameObject{
	
<<<<<<< HEAD
	float speed = 5f;
	private Bitmap bmp;
=======
	float speed = 2f;
>>>>>>> 23bebbb359ced754930765b91cc74ff11ef9e911
	
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
