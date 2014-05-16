package com.spaceshooter.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.spaceshooter.game.view.GlobalHighScoreView;

public class GlobalHighScoreActivity extends Activity{
	
	private GlobalHighScoreView gv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
				WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		getWindow().setFormat(PixelFormat.RGBA_8888);
		
		gv = new GlobalHighScoreView(this);
		setContentView(gv);
		
	}
	

	@Override
	public void onBackPressed() {
		exitDialog();
	}
	
	private void exitDialog() {
		
		Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle("Game Paused");
		builder.setMessage("What do you want to do?");
		builder.setNegativeButton("Resume Game", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
		});
		builder.setPositiveButton("Main Menu", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				
				GlobalHighScoreActivity.super.onBackPressed();
			}
		});

		builder.create().show();
	}

}
