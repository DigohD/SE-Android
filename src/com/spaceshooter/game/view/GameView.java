package com.spaceshooter.game.view;

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

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.engine.GameThread;
import com.spaceshooter.game.level.Level;
import com.spaceshooter.game.util.BitmapHandler;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	private Context context;
	private SurfaceHolder holder;
	private GameThread game;
	private Level level;
	
	private float scaleX, scaleY;
	
	protected Bitmap joystick;
	
	public static final int WIDTH = 800, HEIGHT = 480; 
	
	public GameView(Context context) {
		super(context);
		level = new Level(3);
		game = new GameThread(getHolder(),this);
		this.context = context;

		
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		joystick = BitmapHandler.loadBitmap("ui/joystick");
		
		setLayerType(View.LAYER_TYPE_HARDWARE, null);
		holder = getHolder();
		holder.setFormat(PixelFormat.RGBA_8888);
		holder.setFixedSize(WIDTH, HEIGHT);

		scaleX = (float)(size.x / WIDTH);
		scaleY = (float)(size.y / HEIGHT);
		
		holder.addCallback(this);
	}

	
	@Override
	public void draw(Canvas canvas){
		super.draw(canvas);
		
		//clear the screen with black pixels
		canvas.drawColor(Color.BLACK);

	}

	
	public void draw(Canvas canvas, float interpolation){
		draw(canvas);
		level.draw(canvas, interpolation);
		
		canvas.drawBitmap(joystick, 40, 320, null);
		
	}
	
	public void tick(float dt){
		level.tick(dt);
	}

	public boolean onTouchEvent(MotionEvent event) {
	    float eventX = event.getX();
	    float eventY = event.getY();
	    
	    float nY = scaleY * 1f;
	    
	    eventX = eventX / scaleX;
	    eventY = eventY / nY;
		
	    //Joystick center point: X = 100, Y = 380
	    if(eventX < 210 && eventX > 0 && eventY < 480 && eventY > 300){
	    	if(event.getAction() == MotionEvent.ACTION_MOVE){
		    	if(eventX > 160)
		    		eventX = 160;
		    	else if(eventX < 40)
		    		eventX = 40;
		    	if(eventY > 440)
		    		eventY = 440;
		    	else if(eventY < 320)
		    		eventY = 320;
		    	
		    	float dX = eventX - 100;
		    	float dY = eventY - 380;
		    	
		    	dX = dX / 8;
		    	dY = dY / 8;
		    	
		     	level.getPlayer().incTargetPos(dX, dY);
	    	}
	    	if(event.getAction() == MotionEvent.ACTION_DOWN){
		    	if(eventX > 160)
		    		eventX = 160;
		    	else if(eventX < 40)
		    		eventX = 40;
		    	if(eventY > 440)
		    		eventY = 440;
		    	else if(eventY < 320)
		    		eventY = 320;
		    	
		    	float dX = eventX - 100;
		    	float dY = eventY - 380;
		    	
		    	dX = dX / 8;
		    	dY = dY / 8;
		    	
		     	level.getPlayer().incTargetPos(dX, dY);
		    }
	    }
	    
	    
    	// Schedules a repaint.
    	invalidate();
    	return true;
	}

	public void pause() {
		game.pause();
	}

	public void resume() {
		game.resume();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		game.start();
		level.getPlayer().init();
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
