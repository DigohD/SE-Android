package se.chalmers.spaceshooter.game.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * An Interface which all gameobjects that should be drawn every frame must
 * implement
 * 
 * @author Anders
 * 
 */
public interface Drawable {
	public void draw(Canvas canvas, float interpolation);

	public Bitmap getBitmap();
}
