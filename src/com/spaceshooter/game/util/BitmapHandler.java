package com.spaceshooter.game.util;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Class for handling bitmaps
 * @author Anders
 *
 */
public class BitmapHandler {
	
	private static Activity activity;

	public BitmapHandler(Activity activity) {
		BitmapHandler.activity = activity;
	}
	
	/**
	 * Loads a bitmap from a given source
	 * @param path the location of the bitmap
	 * @return returns the bitmap located at the given source
	 */
	public static Bitmap loadBitmap(String path){
		AssetManager assetManager = activity.getAssets();
	    InputStream inputStream = null;
	    Bitmap bitmap = null;
	    try {
	        inputStream = assetManager.open("images/" + path);
	        bitmap = BitmapFactory.decodeStream(inputStream);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }finally{
	    	if(inputStream != null){
	    		try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    }
	    return bitmap;
	}
}
