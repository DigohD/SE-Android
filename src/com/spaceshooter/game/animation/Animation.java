package com.spaceshooter.game.animation;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

import com.spaceshooter.game.util.BitmapHandler;

public class Animation {

	private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
	private Bitmap bitmap;
	private int animSpeed;
	private int anim = 0;
	private int currentFrame = 0;
	private int numOfBitmaps;

	public Animation(Bitmap bitmap, int numOfBitmaps, int animSpeed) {
		this.bitmap = bitmap;
		this.numOfBitmaps = numOfBitmaps;
		this.animSpeed = animSpeed;

		int width = bitmap.getWidth() / numOfBitmaps;
		int height = bitmap.getHeight();
		for (int i = 0; i < numOfBitmaps; i++)
			bitmaps.add(BitmapHandler.getSubBitmap(bitmap, i * width, 0, width,
					height));
		bitmap = bitmaps.get(0);
	}

	public void animate() {
		if (anim < 7500)
			anim++;
		else
			anim = 0;

		if (anim % animSpeed == 0 && currentFrame < bitmaps.size()) {
			bitmap = bitmaps.get(currentFrame);
			currentFrame++;
		}
		if (currentFrame == bitmaps.size())
			currentFrame = 0;
	}

	public int getNumOfBitmaps() {
		return numOfBitmaps;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

}
