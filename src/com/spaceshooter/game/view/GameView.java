package com.spaceshooter.game.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
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

import com.spaceshooter.game.GameActivity;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.engine.GameThread;
import com.spaceshooter.game.level.Level;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.MusicPlayer;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	public static final int WIDTH = 800, HEIGHT = 480; 
	private float scaleX, scaleY;
	private int timer = 0;
	
	private Context context;
	private SurfaceHolder holder;
	private GameThread game;
	private Level level;
	private Bitmap joystick;
	private MusicPlayer mp;
	
	public GameView(Context context) {
		super(context);
		this.context = context;
		
		level = new Level(1);
		game = new GameThread(getHolder(),this);
		mp = new MusicPlayer(context);
		
		joystick = BitmapHandler.loadBitmap("ui/joystick");
		
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		
		scaleX = ((float)size.x / (float)WIDTH);
		scaleY = ((float)size.y / (float)HEIGHT);

		setLayerType(View.LAYER_TYPE_HARDWARE, null);
		holder = getHolder();
		holder.setFormat(PixelFormat.RGBA_8888);
		holder.setFixedSize(WIDTH, HEIGHT);

		holder.addCallback(this);
	}
	
	/**
	 * Creates a dialogbox on the screen with a title, message and two buttons
	 * one button for yes and one for no
	 * @param title the title of the box, eg "Level completed!" or "You died"
	 * @param msg the message in the box, eg "Restart the level?"
	 */
	private void dialogBox(final String title, final String msg){
		GameActivity ga = (GameActivity) context;
		ga.runOnUiThread(new Runnable() {
            public void run() {
            	game.pause();
            	mp.stop();
            	Builder builder = new AlertDialog.Builder(context);
        		builder.setCancelable(false);
        		builder.setTitle(title);
        		builder.setMessage(msg);
        		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    	GameActivity ga2 = (GameActivity) context;
                    	stop();
                        ga2.onBackPressed2();
                    }});
        		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    	GameObjectManager.clear();
                    	level = new Level(1);
                    	GameObjectManager.getPlayer().init();
                    	game.resume();
                    	mp = new MusicPlayer(context);
                    }});
        		builder.create().show();
            }});
	}

	public void draw(Canvas canvas, float interpolation){
		//clear the screen with black pixels
		canvas.drawColor(Color.BLACK);
		//draw the level
		level.draw(canvas, interpolation);
		//draw the joystick
		canvas.drawBitmap(joystick, 40, 320, null);
	}
	
	public void tick(float dt){
		//update the level
		level.tick(dt);
		
		if(level.isFinished() && GameObjectManager.getPlayer().isLive()){
			timer++;
			if(timer >= 3*60){
				dialogBox("Level completed!", "Your highscore: " + GameObjectManager.getPlayer().getScore() + "\nRestart level?");
				timer = 0;
			}
		}
			
		if(!GameObjectManager.getPlayer().isLive()){
			timer++;
			if(timer >= 2*60){
				dialogBox("You are dead!", "Your highscore: " + GameObjectManager.getPlayer().getScore() + "\nRestart level?");
				timer = 0;
			}	
		}

//		if(mp.isDone())
//			mp = new MusicPlayer(context);
	}

	public boolean onTouchEvent(MotionEvent event) {
	    float eventX = event.getX();
	    float eventY = event.getY();
	    
	    float nY = scaleY * 0.95f;
	    
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
		    	
		    	GameObjectManager.getPlayer().incTargetPos(dX, dY);
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
		    	
		    	GameObjectManager.getPlayer().incTargetPos(dX, dY);
		    }
	    	
	    	if(event.getAction() == MotionEvent.ACTION_UP){
	    		GameObjectManager.getPlayer().setUpdate(false);
	    	}
	    }
	    
    	// Schedules a repaint.
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
	
	
	public void start(){
		game.start();
		GameObjectManager.getPlayer().init();
	}
	
	public void stop() {
		GameObjectManager.clear();
		GameObjectManager.getPlayer().setScore(0);
		mp.stop();
		game.stop();
	}
	
	public void pause() {
		mp.pause();
		game.pause();
	}

	public void resume() {
		mp.resume();
		game.resume();
	}

}
