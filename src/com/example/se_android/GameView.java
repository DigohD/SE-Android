package com.example.se_android;

import java.util.Random;

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
	
	public GameView(Context context, double refreshRate) {
		super(context);
		world = new GameWorld();
		game = new GameThread(getHolder(),this, refreshRate);
		this.context = context;
		
		
		holder = getHolder();
		holder.addCallback(this);
	}
	
	public GameThread getThread(){
		return game;
	}
	
	
//	@Override
//	public void draw(Canvas canvas){
//		
////		float scaleX = canvas.getWidth() / 1920;
////		float scaleY = canvas.getHeight() / 1080;
//
//		//System.out.println(scaleX + " - " + scaleY);
//		
//	
//
//	}
	
	public void draw(Canvas c, float interpolation){
		c.drawColor(Color.BLACK);
		world.draw(c, interpolation);
	}
	
	int c = 0;
	Random r = new Random();
	int rn = 0;
	public void tick(float dt){
		c++;
		if(c >= 60*3){
			rn = 1+r.nextInt(420);
			world.addGameObject(new Player(BitmapFactory.decodeResource(getResources(), R.drawable.ship),rn, 0, 40, 40));
			c = 0;
		}
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
