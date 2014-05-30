package se.chalmers.spaceshooter.game;

import se.chalmers.spaceshooter.game.object.player.Player;
import se.chalmers.spaceshooter.game.util.BitmapLoader;
import se.chalmers.spaceshooter.game.util.SoundPlayer;
import se.chalmers.spaceshooter.game.util.Vector2f;
import se.chalmers.spaceshooter.game.view.GameView;
import se.chalmers.spaceshooter.game.view.InventoryView;
import se.chalmers.spaceshooter.menu.TabMenu;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {
	
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

		new BitmapLoader(this);
		new GameObjectManager();

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
			GameView.dialogBoxShowing = true;
			gameView.pause();
		}
		Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle("Game Paused");
		builder.setMessage("What do you want to do?");
		builder.setNegativeButton("Resume Game", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				GameView.dialogBoxShowing = false;
				gameView.resume();
			}
		});
		builder.setPositiveButton("Main Menu", new OnClickListener() {
			@Override
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

	@Override
	public void onStop() {
		super.onStop();
		if (!isInvView)
			saveState();

	}

	@Override
	public void onPause() {
		super.onPause();
		if (!isInvView)
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
			GameObjectManager.getPlayer();
			GameObjectManager.removeGameObject(Player.getEngine());
		}
		savedPos = GameObjectManager.getPlayer().getPosition();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
