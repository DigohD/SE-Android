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

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.engine.GameThread;
import com.spaceshooter.game.level.Level;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	private Context context;
	private SurfaceHolder holder;
	private GameThread game;
	private Level level;

	public static int width, height;

	public GameView(Context context) {
		super(context);
		level = new Level(3);
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
		super.draw(canvas);
		// float scaleX = canvas.getWidth() / 1920;
		// float scaleY = canvas.getHeight() / 1080;

		// System.out.println(scaleX + " - " + scaleY);

		// clear the screen with black pixels
		canvas.drawColor(Color.BLACK);

	}

	public void draw(Canvas canvas, float interpolation) {
		draw(canvas);
		level.draw(canvas, interpolation);
	}

	public void tick(float dt) {
		level.tick(dt);

	}

	public boolean onTouchEvent(MotionEvent event) {
		float eventX = event.getX();
		float eventY = event.getY();

		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			level.getPlayer().setTargetPos(eventX, eventY);
		}

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
		GameObjectManager.clear();
		game.stop();
	}

}
