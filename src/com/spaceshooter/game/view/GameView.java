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

import com.spaceshooter.game.engine.GameEngine;
import com.spaceshooter.game.object.GameObjectHandler;
import com.spaceshooter.game.object.player.Player;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

	private Context context;
	private GameEngine game;
	private SurfaceHolder holder;
	private GameObjectHandler objectHandler;
	public static int width, height;
	
	private int counter = 0;
	
//	Player player;
	
	public GameView(Context context, double refreshRate) {
		super(context);
		objectHandler = new GameObjectHandler();
		game = new GameEngine(getHolder(),this, refreshRate);
		this.context = context;
		
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		
//		float rn = Randomizer.getFloat(0, width - 30);
//		player = new Player(new Vector2f(rn, -30));
		
		setLayerType(View.LAYER_TYPE_HARDWARE, null);
		holder = getHolder();
		holder.setFormat(PixelFormat.RGBA_8888);
		holder.setFixedSize(width, height);
		holder.addCallback(this);
	}
	

	
	@Override
	public void draw(Canvas canvas){
		
//		float scaleX = canvas.getWidth() / 1920;
//		float scaleY = canvas.getHeight() / 1080;

		//System.out.println(scaleX + " - " + scaleY);
		canvas.drawColor(Color.BLACK);
	
	}
	
	public void draw(Canvas c, float interpolation){
		draw(c);
		objectHandler.draw(c, interpolation);
	}
	
	public void tick(float dt){
		counter++;
		if(counter >= 10){
			float rn = Randomizer.getFloat(0, width - 30);
			objectHandler.addGameObject(new Player(new Vector2f(rn, -30)));
//			player.getPosition().setX(rn);
//			player.getPosition().setY(0);
			counter = 0;
		}
		objectHandler.tick(dt);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
	    float eventX = event.getX();
	    float eventY = event.getY();
	    
	    if(event.getAction() == MotionEvent.ACTION_DOWN)
	    	objectHandler.addGameObject(new Player(new Vector2f(eventX, eventY)));
	    
    	// Schedules a repaint.
    	invalidate();
    	return true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		game.start();
		//game.startDrawingThread();
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		game.stop();
	}

}
