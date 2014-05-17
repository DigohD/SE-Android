package com.spaceshooter.game.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public interface Drawable {
	
	public void draw(Canvas canvas, float interpolation);
	public Bitmap getBitmap();

}
