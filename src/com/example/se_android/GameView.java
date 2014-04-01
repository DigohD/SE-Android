package com.example.se_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

	private Context context;
	private GameThread game;
	private SurfaceHolder holder;
	private GameWorld world;
	private Bitmap bmp;
	private int width, height;
	
	public GameView(Context context) {
		super(context);
		game = new GameThread(getHolder(),this);
		this.context = context;
		world = new GameWorld();
		
		holder = getHolder();
		holder.addCallback(this);
	}
	
	
	@Override
	public void draw(Canvas canvas){
		
		float scaleX = canvas.getWidth() / 1920;
		float scaleY = canvas.getHeight() / 1080;

		System.out.println(scaleX + " - " + scaleY);
		
		canvas.drawColor(Color.BLACK);
	    world.draw(canvas, scaleX, scaleY);
	   // canvas.drawBitmap(bmp, 100, 100, null);

		
	}
	
	public void tick(float dt){
		world.tick(dt);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
	    float eventX = event.getX();
	    float eventY = event.getY();
	    
	    if(event.getAction() == MotionEvent.ACTION_DOWN){
	    	world.addGameObject(new Player(BitmapFactory.decodeResource(getResources(), R.drawable.ship),eventX, eventY, 40, 40));
	    }
	    
    	// Schedules a repaint.
    	invalidate();
    	return true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		game.start();
		
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		game.stop();
	}

}
