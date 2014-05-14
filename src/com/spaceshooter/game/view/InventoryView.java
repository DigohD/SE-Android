package com.spaceshooter.game.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.spaceshooter.game.GameActivity;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.engine.GameThread;
import com.spaceshooter.game.level.Level;
import com.spaceshooter.game.object.weapon.BluePlasmaGun;
import com.spaceshooter.game.object.weapon.GreenPlasmaGun;
import com.spaceshooter.game.object.weapon.RedPlasmaGun;
import com.spaceshooter.game.object.weapon.YellowPlasmaGun;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.MusicPlayer;
import com.spaceshooter.game.util.SoundPlayer;
import com.spaceshooter.game.util.SoundPlayer.SoundID;

public class InventoryView extends SurfaceView implements
		SurfaceHolder.Callback {

	public static final int WIDTH = 800, HEIGHT = 480;
	private float scaleX, scaleY;
	private int timer = 0, timer2 = 0;

	private Bitmap banner;
	private Bitmap banner2;

	private Bitmap[] vapen = new Bitmap[5];

	private float previousY, offsetY;

	private boolean okToRestartMP = true;

	private Context context;
	private SurfaceHolder holder;
	private GameThread game;
	private Level level;
	private MusicPlayer mp;
	private Paint p = new Paint();

	private int pressTimer, startTimer;
	private boolean start = false;;

	public InventoryView(Context context) {
		super(context);
		this.context = context;

		// mp = new MusicPlayer(context);

		startTimer = 0;
		start = false;

		game = new GameThread(getHolder(), this);

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		scaleX = ((float) size.x / (float) WIDTH);
		scaleY = ((float) size.y / (float) HEIGHT);

		setLayerType(View.LAYER_TYPE_HARDWARE, null);
		holder = getHolder();
		holder.setFormat(PixelFormat.RGBA_8888);
		holder.setFixedSize(WIDTH, HEIGHT);

		holder.addCallback(this);

		banner = BitmapHandler.loadBitmap("ui/weaponOne");
		banner2 = BitmapHandler.loadBitmap("ui/weaponTwo");

		this.vapen[0] = BitmapHandler.loadBitmap("ui/RedPlasma");
		this.vapen[1] = BitmapHandler.loadBitmap("ui/BluePlasma");
		this.vapen[2] = BitmapHandler.loadBitmap("ui/GreenPlasma");
		this.vapen[3] = BitmapHandler.loadBitmap("ui/YellowPlasma");

	}

	/**
	 * Creates a dialogbox on the screen with a title, message and two buttons
	 * one button for yes and one for no
	 * 
	 * @param title
	 *            the title of the box, eg "Level completed!" or "You died"
	 * @param msg
	 *            the message in the box, eg "Restart the level?"
	 * @param positiveBtn
	 *            the text in the positive button
	 * @param negativeBtn
	 *            the text in the negative button
	 */
	// private void dialogBox(final String title, final String msg, final String
	// positiveBtn, final String negativeBtn){
	// GameActivity ga = (GameActivity) context;
	// ga.runOnUiThread(new Runnable() {
	// public void run() {
	// game.pause();
	// mp.stop();
	// Builder builder = new AlertDialog.Builder(context);
	// builder.setCancelable(false);
	// builder.setTitle(title);
	// builder.setMessage(msg);
	// builder.setNegativeButton(negativeBtn, new
	// DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface arg0, int arg1) {
	// GameActivity ga2 = (GameActivity) context;
	// stop();
	// ga2.onBackPressed2();
	// }});
	// builder.setPositiveButton(positiveBtn, new
	// DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface arg0, int arg1) {
	// GameObjectManager.clear();
	// level = new Level(1);
	// GameObjectManager.getPlayer().init();
	// game.resume();
	// mp = new MusicPlayer(context);
	// okToRestartMP = true;
	// drawJoystick = true;
	// level.setFinished(false);
	// timer = 0;
	// timer2 = 0;
	// firstLevel = true;
	// levelID = 2;
	// }});
	// builder.create().show();
	// }});
	// }

	public void tick(float dt) {
		pressTimer++;
		if (start) {
			startTimer++;
			if (startTimer > 150) {
				final GameActivity gA = (GameActivity) context;
				gA.runOnUiThread(new Runnable() {
					public void run() {
						gA.goToGame();
					}
				});

			}
		}
	}

	public void draw(Canvas canvas, float interpolation) {
		// clear the screen with black pixels
		canvas.drawColor(Color.BLACK);

		for (int i = 0; i < 4; i++)
			canvas.drawBitmap(vapen[i], 0, (i * 120) + 80 + offsetY, null);

		canvas.drawBitmap(banner, 0, 0, null);

		// for(int i = 0; i < 10; i++)
		// canvas.drawBitmap(vapen, 0, (i * 120) + 80 + offsetY, null);

	}

	public boolean onTouchEvent(MotionEvent event) {
		float eventX = event.getX();
		float eventY = event.getY();

		float nY = scaleY * 1.0f;

		eventX = eventX / scaleX;
		eventY = eventY / nY;

		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			float dY = eventY - previousY;

			if (dY > 100 || dY < -100) {
				previousY = eventY;
				return true;
			}

			offsetY = offsetY + dY;
			previousY = eventY;
		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			pressTimer = 0;
		} else if (event.getAction() == MotionEvent.ACTION_UP
				&& pressTimer < 15) {
			int weapon = 0;
			weapon = (int) ((eventY - offsetY - 80) / 120);

			SoundPlayer.playSound(SoundID.ui_guns);

			switch (weapon) {
			case 0:
				GameObjectManager.getPlayer().setBottomGun(
						new RedPlasmaGun(GameObjectManager.getPlayer()
								.getBottomGunPos()));
				GameObjectManager.getPlayer().setTopGun(
						new RedPlasmaGun(GameObjectManager.getPlayer()
								.getTopGunPos()));
				break;
			case 1:
				GameObjectManager.getPlayer().setBottomGun(
						new BluePlasmaGun(GameObjectManager.getPlayer()
								.getBottomGunPos()));
				GameObjectManager.getPlayer().setTopGun(
						new BluePlasmaGun(GameObjectManager.getPlayer()
								.getTopGunPos()));
				break;
			case 2:
				GameObjectManager.getPlayer().setBottomGun(
						new GreenPlasmaGun(GameObjectManager.getPlayer()
								.getBottomGunPos()));
				GameObjectManager.getPlayer().setTopGun(
						new GreenPlasmaGun(GameObjectManager.getPlayer()
								.getTopGunPos()));
				break;
			case 3:
				GameObjectManager.getPlayer().setBottomGun(
						new YellowPlasmaGun(GameObjectManager.getPlayer()
								.getBottomGunPos()));
				GameObjectManager.getPlayer().setTopGun(
						new YellowPlasmaGun(GameObjectManager.getPlayer()
								.getTopGunPos()));
				break;
			}
			start = true;
		}

		invalidate();
		return true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stop();
	}

	public void start() {
		game.start();
	}

	public void stop() {
		game.stop();
	}

	public void pause() {
		okToRestartMP = false;
		game.pause();
	}

	public void resume() {
		okToRestartMP = true;
		game.resume();
	}

}
