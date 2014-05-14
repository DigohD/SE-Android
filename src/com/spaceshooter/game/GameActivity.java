package com.spaceshooter.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.spaceshooter.game.database.Database;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.object.enemy.Enemy;
import com.spaceshooter.game.object.enemy.Predator;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.SoundPlayer;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;
import com.spaceshooter.game.view.InventoryView;

public class GameActivity extends Activity {

	private GameView gameView;
	private InventoryView invView;
	public boolean musicState; // is false if music shouldn't be played and true
								// if it should
	public boolean isInvView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// we'll use this to manipulate the list of high scores

		// used to get if the music toggle is clicked or not
		Intent intent = getIntent();
		musicState = intent.getBooleanExtra("EXTRA_musicState", true);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
				WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		getWindow().setFormat(PixelFormat.RGBA_8888);

		new BitmapHandler(this);

		// gameView = new GameView(this);
		// setContentView(gameView);

		GameObjectManager go = new GameObjectManager();

		isInvView = true;
		invView = new InventoryView(this);
		setContentView(invView);

		new SoundPlayer(this);
	}

	public void database() {
		Intent intent = new Intent(this, Database.class);
		finish();
		startActivity(intent);
	}

	private void exitDialog() {
		gameView.pause();
		Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle("Quit Game?");
		builder.setMessage("Return to main menu?");
		builder.setNegativeButton("Cancel", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				gameView.resume();
			}
		});
		builder.setPositiveButton("Ok", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
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
		builder.setTitle("Quit Game?");
		builder.setMessage("Return to main menu?");
		builder.setNegativeButton("Cancel", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				invView.resume();
			}
		});
		builder.setPositiveButton("Ok", new OnClickListener() {
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

	// public void onStop(){
	// super.onStop();
	// gameView.pause();
	// }
	//
	// public void onPause(){
	// super.onPause();
	// gameView.pause();
	// }
	//
	// public void onResume(){
	// super.onResume();
	// gameView.resume();
	// }

	// UNIT TESTS

	// public void testCalculatePlayerScore(){
	// Enemy e = new Predator(new Vector2f(0,0), 40, 40);
	// // 10 * 1.2^1 * 5
	// int test1 = e.calculatePlayerScore(10, 1, 5);
	// int test2 = e.calculatePlayerScore(20, 2, 8);
	// int test3 = e.calculatePlayerScore(15, 3, 2);
	// int test4 = e.calculatePlayerScore(30, 4, 4);
	// int test5 = e.calculatePlayerScore(50, 5, 12);
	//
	// assertEquals(60, test1);
	// assertEquals(230, test2);
	// assertEquals(51, test3);
	// assertEquals(248, test4);
	// assertEquals(1492, test5);
	// }

	// public void testVector2fNormalize(){
	// for(int i = 0; i < 10000; i++){
	// float rX = Randomizer.getFloat(1, 800);
	// float rY = Randomizer.getFloat(1, 480);
	// Vector2f v = new Vector2f(rX, rY);
	// assertEquals(1.0f, v.normalize().length());
	// }
	// }
	//
	// public void testVector2fDot(){
	// Vector2f v1 = new Vector2f(5, 6);
	// Vector2f v2 = new Vector2f(3, 7);
	//
	// Vector2f v3 = new Vector2f(15, 5);
	// Vector2f v4 = new Vector2f(12, 8);
	//
	// Vector2f v5 = new Vector2f(11, 27);
	// Vector2f v6 = new Vector2f(38, 70);
	//
	// Vector2f v7 = new Vector2f(130, 78);
	// Vector2f v8 = new Vector2f(56, 18);
	//
	// assertEquals(57.0f, v1.dot(v2));
	// assertEquals(220.0f, v3.dot(v4));
	// assertEquals(2308.0f, v5.dot(v6));
	// assertEquals(8684.0f, v7.dot(v8));
	// }

	// public void testProjectileOutOfBound(){
	// List<Projectile> projectiles = new ArrayList<Projectile>();
	//
	// Projectile p1 = new RedPlasma(new Vector2f(GameView.WIDTH,0), 20, 5);
	// Projectile p2 = new BluePlasma(new Vector2f(GameView.WIDTH/2, -12), 12,
	// 12);
	// Projectile p3 = new GreenPlasma(new Vector2f(GameView.WIDTH/2,
	// GameView.HEIGHT+12), 30, 12);
	// Projectile p4 = new MantisProj(new Vector2f(-20,GameView.HEIGHT/2), 20,
	// 2);
	// Projectile p5 = new PredatorProj(new Vector2f(-20,GameView.HEIGHT/2), 20,
	// 5);
	//
	// projectiles.add(p1);
	// projectiles.add(p2);
	// projectiles.add(p3);
	// projectiles.add(p4);
	// projectiles.add(p5);
	//
	// boolean[] projsOutofBound = new boolean[5];
	//
	// for(int i = 0; i < projsOutofBound.length; i++)
	// projsOutofBound[i] = false;
	//
	// for(int i = 0; i < projectiles.size(); i++){
	// Projectile p = projectiles.get(i);
	// if(p.isOutOfBound())
	// projsOutofBound[i] = true;
	// }
	//
	// int count = 0;
	// for(int i = 0; i < projsOutofBound.length; i++)
	// if(projsOutofBound[i])
	// count++;
	//
	// assertEquals(projectiles.size(), count);
	// }

}
