package se.chalmers.spaceshooter.game.util;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Class for handling bitmaps
 * 
 * @author Anders
 * 
 */
public class BitmapLoader {

	private static Activity activity;

	/**
	 * Returns an immutable bitmap from the specified subset of the source
	 * bitmap
	 * 
	 * @param source
	 *            the bitmap to subset
	 * @param x
	 *            the x position for the first pixel in the source bitmap
	 * @param y
	 *            the y position for the first pixel in the source bitmap
	 * @param width
	 *            the number of pixels in every row
	 * @param height
	 *            the number of rows
	 * @return returns a subset of the source bitmap
	 */
	public static Bitmap getSubBitmap(Bitmap source, int x, int y, int width,
			int height) {
		return Bitmap.createBitmap(source, x, y, width, height);
	}

	/**
	 * Loads a bitmap from a given source
	 * 
	 * @param path
	 *            the location of the bitmap
	 * @return returns the bitmap located at the given source
	 */
	public static Bitmap loadBitmap(String path) {
		// get acces to the raw assets files
		AssetManager assetManager = activity.getAssets();
		InputStream inputStream = null;
		Bitmap bitmap = null;
		try {
			inputStream = assetManager.open("images/" + path + ".png");
			bitmap = BitmapFactory.decodeStream(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}

	public BitmapLoader(Activity activity) {
		BitmapLoader.activity = activity;
	}

}
