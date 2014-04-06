package com.spaceshooter.game.util;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapHandler {
	
	private static Activity activity;

	public BitmapHandler(Activity activity) {
		BitmapHandler.activity = activity;
	}
	
	public static Bitmap loadBitmap(String path){
		AssetManager assetManager = activity.getAssets();
	    InputStream inputStream;
	    Bitmap bitmap = null;
	    try {
	        inputStream = assetManager.open("images/" + path);
	        bitmap = BitmapFactory.decodeStream(inputStream);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    return bitmap;
	}
}
