package com.spaceshooter.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.SoundPlayer;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;
import com.spaceshooter.game.view.InventoryView;

public class GameActivity extends Activity {
	SharedPreferences sp;
	private GameView gameView;
	private InventoryView invView;

	public boolean isInvView;

	public static Vector2f savedPos = new Vector2f(GameView.WIDTH / 2,
			GameView.HEIGHT / 2);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GameView.dialogBoxShowing = false;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
				WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		getWindow().setFormat(PixelFormat.RGBA_8888);

		new BitmapHandler(this);

		GameObjectManager go = new GameObjectManager();

		isInvView = true;
		invView = new InventoryView(this);
		setContentView(invView);

		new SoundPlayer(this);
	}

	private void exitDialog() {
		if (gameView != null) {
			gameView.dialogBoxShowing = true;
			gameView.pause();
		}
		Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle("Game Paused");
		builder.setMessage("What do you want to do?");
		builder.setNegativeButton("Resume Game", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				gameView.dialogBoxShowing = false;
				gameView.resume();
			}
		});
		builder.setPositiveButton("Main Menu", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				gameView.dialogBoxShowing = false;
				gameView.stop();
				GameActivity.super.onBackPressed();
			}
		});

		builder.create().show();
	}

	private void exitDialogInv() {
		invView.pause();
		Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle("Game Paused");
		builder.setMessage("What do you want to do?");
		builder.setNegativeButton("Resume Game", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				invView.resume();
			}
		});
		builder.setPositiveButton("Main Menu", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				invView.stop();
				GameActivity.super.onBackPressed();
			}
		});

		builder.create().show();
	}

	public void goToGame() {
		isInvView = false;
		gameView = new GameView(this);
		setContentView(gameView);
	}

	public void onBackPressed2() {
		GameActivity.super.onBackPressed();
	}

	@Override
	public void onBackPressed() {
		if (isInvView)
			exitDialogInv();
		else
			exitDialog();
	}

	public void onStop() {
		super.onStop();
		System.out.println("q12STOP");
		saveState();

	}

	public void onPause() {
		super.onPause();
		System.out.println("q12PAUSE");
		saveState();

	}
	
	private void saveState(){
		if (!GameView.dialogBoxShowing) {
			exitDialog();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (gameView.sdestroyed) {
			GameObjectManager.removeGameObject(GameObjectManager.getPlayer()
					.getEngine());
		}
		savedPos = GameObjectManager.getPlayer().getPosition();
	}

	public void onResume() {
		super.onResume();
		System.out.println("q12RESUME");
		// gameView.resume();
	}

}
