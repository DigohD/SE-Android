package com.spaceshooter.game.view;

import android.content.Context;
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

import com.spaceshooter.game.engine.CollisionManager;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.engine.GameThread;
import com.spaceshooter.game.object.enemy.Predator;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	private Context context;
	private GameThread game;
	private SurfaceHolder holder;
	private GameObjectManager objectManager;
	public static int width, height;

	private int counter = 0;
	private int timer = 0;
	private int LEVEL_TIME = 60 * 60;
	private float randomNum;

	public GameView(Context context) {
		super(context);
		objectManager = new GameObjectManager();
		game = new GameThread(getHolder(), this);
		this.context = context;

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;

		setLayerType(View.LAYER_TYPE_HARDWARE, null);
		holder = getHolder();
		holder.setFormat(PixelFormat.RGBA_8888);
		holder.setFixedSize(width, height);
		holder.addCallback(this);
	}

	@Override
	public void draw(Canvas canvas) {

		// float scaleX = canvas.getWidth() / 1920;
		// float scaleY = canvas.getHeight() / 1080;

		// System.out.println(scaleX + " - " + scaleY);

		// clear the screen to prepare for the next frame
		canvas.drawColor(Color.BLACK);

	}

	public void draw(Canvas c, float interpolation) {
		draw(c);
		objectManager.draw(c, interpolation);
	}

	public void tick(float dt) {

		counter++;
		timer++;
		if ((timer < LEVEL_TIME)) {
			if (counter >= 20) {
				randomNum = Randomizer.getFloat(0, width - 30);
				new Predator(new Vector2f(randomNum, -30));
				counter = 0;
			}
		}
		CollisionManager.collisionCheck(objectManager.getPlayer());
		objectManager.tick(dt);
	}

	public boolean onTouchEvent(MotionEvent event) {
		float eventX = event.getX();
		float eventY = event.getY();

		if (event.getAction() == MotionEvent.ACTION_MOVE)
			objectManager.getPlayer().setTargetPos(eventX, eventY);
		if (event.getAction() == MotionEvent.ACTION_DOWN)
			objectManager.getPlayer().setTargetPos(eventX, eventY);

		// Schedules a repaint.
		invalidate();
		return true;
	}

	public void pause() {
		game.stop();
	}

	public void resume() {
		game.start();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		game.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		game.stop();
	}

}
