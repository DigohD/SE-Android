package se.chalmers.spaceshooter.game.view;

import se.chalmers.spaceshooter.game.GameActivity;
import se.chalmers.spaceshooter.game.GameObjectManager;
import se.chalmers.spaceshooter.game.GameThread;
import se.chalmers.spaceshooter.game.object.weapon.BluePlasmaGun;
import se.chalmers.spaceshooter.game.object.weapon.GreenPlasmaGun;
import se.chalmers.spaceshooter.game.object.weapon.RedPlasmaGun;
import se.chalmers.spaceshooter.game.object.weapon.YellowPlasmaGun;
import se.chalmers.spaceshooter.game.util.BitmapLoader;
import se.chalmers.spaceshooter.game.util.MusicPlayer;
import se.chalmers.spaceshooter.game.util.SoundPlayer;
import se.chalmers.spaceshooter.game.util.SoundPlayer.SoundID;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

public class InventoryView extends SurfaceView implements
		SurfaceHolder.Callback {

	public static final int WIDTH = 800, HEIGHT = 480;
	private float scaleX, scaleY;

	private Bitmap banner;
	private Bitmap banner2;

	private Bitmap[] vapen = new Bitmap[5];

	private float previousY, offsetY;

	private boolean okToRestartMP = true;

	private Context context;
	private SurfaceHolder holder;
	private GameThread game;
	private MusicPlayer mp;

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

		banner = BitmapLoader.loadBitmap("ui/weaponOne");
		banner2 = BitmapLoader.loadBitmap("ui/weaponTwo");

		this.vapen[0] = BitmapLoader.loadBitmap("ui/RedPlasma");
		this.vapen[1] = BitmapLoader.loadBitmap("ui/BluePlasma");
		this.vapen[2] = BitmapLoader.loadBitmap("ui/GreenPlasma");
		this.vapen[3] = BitmapLoader.loadBitmap("ui/YellowPlasma");

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

	@Override
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

	public void pause() {
		okToRestartMP = false;
		game.pause();
	}

	public void resume() {
		okToRestartMP = true;
		game.resume();
	}

	public void start() {
		game.start();
	}

	public void stop() {
		game.stop();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stop();
	}

	public void tick(float dt) {
		pressTimer++;
		if (start) {
			startTimer++;
			if (startTimer > 150) {
				final GameActivity gA = (GameActivity) context;
				gA.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						gA.goToGame();
					}
				});

			}
		}
	}

}
