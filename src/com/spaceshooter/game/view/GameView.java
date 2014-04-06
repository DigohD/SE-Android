package com.spaceshooter.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
	
	public GameView(Context context, double refreshRate) {
		super(context);
		objectHandler = new GameObjectHandler();
		game = new GameEngine(getHolder(),this, refreshRate);
		this.context = context;
		
		holder = getHolder();
		holder.addCallback(this);
	}
	
	@Override
	public void draw(Canvas canvas){
		
		width = canvas.getWidth();
		height = canvas.getHeight();
		
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
			int rn = Randomizer.getInt(0, width - 30);
			objectHandler.addGameObject(new Player(new Vector2f(rn, -30)));
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
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		game.stop();
	}

}
