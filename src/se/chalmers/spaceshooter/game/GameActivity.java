package se.chalmers.spaceshooter.game;

import se.chalmers.spaceshooter.engine.GameObjectManager;
import se.chalmers.spaceshooter.startmenu.TabMenu;
import se.chalmers.spaceshooter.util.BitmapHandler;
import se.chalmers.spaceshooter.util.SoundPlayer;
import se.chalmers.spaceshooter.util.Vector2f;
import se.chalmers.spaceshooter.view.GameView;
import se.chalmers.spaceshooter.view.InventoryView;
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


public class GameActivity extends Activity {
	SharedPreferences sp;
	private GameView gameView;
	private InventoryView invView;

	public boolean isInvView;

	public static Vector2f savedPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		savedPos = new Vector2f(GameView.WIDTH / 2, GameView.HEIGHT / 2);
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
		if (TabMenu.helpShown == 0) {
			TabMenu.helpDialog(this);
		}
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
				gameView.stop();
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
			super.onBackPressed();
		else
			exitDialog();
	}

	public void onStop() {
		super.onStop();
		System.out.println("q12STOP");
		if(!isInvView)
			saveState();

	}

	public void onPause() {
		super.onPause();
		System.out.println("q12PAUSE");
		if(!isInvView)
			saveState();
	}

	private void saveState() {
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
