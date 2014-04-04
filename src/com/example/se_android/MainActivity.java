package com.example.se_android;

import com.example.se_android.openGLtest.GLGameView;
import com.example.se_android.openGLtest.GLRenderer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	
		private GameView gameView;
		//private GLGameView gameView;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			double refreshRating = display.getRefreshRate();
	        gameView = new GameView(this, refreshRating);
	        setContentView(gameView);
	       // gameView.setRenderer(new GLRenderer());

	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

//		@Override
//		protected void onResume() {
//			super.onResume();
//			gameView.onResume();
//		}
//
//		@Override
//		protected void onPause() {
//			super.onPause();
//			gameView.onPause();
//		}
		


}
