package com.example.se_android;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View{

	Paint paint = new Paint();
	Random rnd = new Random();
	
	static MediaPlayer mp; 
	
	float finalWidth;
	float finalHeight;
	boolean ratioCalc, music;
	
	GameWorld gw;
	
	Context c;
	
    public MainView(Context context){
        super(context);
        
        gw = new GameWorld();
        
        c = context;
    }

    public void playSound(Context context){      
//    	mp = MediaPlayer.create(context, R.raw.n);
//    	if(!mp.isPlaying()){
//        	mp.start();
//    	}
    }
    
    @Override
    public void onDraw(Canvas canvas) {
    	// x 1920
    	// y 885
    	
//    	if(!music){
//    		playSound(c);
//    		music = true;
//    	}
    	
    	if(!ratioCalc){
    		float newWidth = canvas.getWidth();
        	float newHeight = canvas.getHeight();
        	
        	finalWidth = newWidth / 1920;
        	finalHeight = newHeight / 885;
    	}
    	
    	canvas.scale(finalWidth, finalHeight);
    	
    	gw.draw(canvas);
    }
	
    @Override
	    public boolean onTouchEvent(MotionEvent event) {
	    float eventX = event.getX();
	    float eventY = event.getY();
      
    	// Schedules a repaint.
    	invalidate();
    	return true;
    }
    
    public void tick(){
    	
    }
    
}
