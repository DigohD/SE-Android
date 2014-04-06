package com.spaceshooter.game;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.spaceshooter.game.view.GameView;

public class MainActivity extends Activity {

		private GameView gameView;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			double refreshRating = display.getRefreshRate();
			
	        gameView = new GameView(this, refreshRating);
	        setContentView(gameView);

	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

}
