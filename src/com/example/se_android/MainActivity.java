package com.example.se_android;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity implements Runnable{

	MainView drawView;
	boolean running = false;
	
	public static final double TARGET_TPS = 60.0;
	public static final double TARGET_FPS = 120.0;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        drawView = new MainView(this);
        drawView.setBackgroundColor(Color.BLACK);
        setContentView(drawView);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        (new Thread(this)).start();
    }
	
	public void run() {
		double previousTime = System.nanoTime();
		double currentTime = 0;
		double passedTime = 0;
		double timer = System.currentTimeMillis();
		double delta = 0;
		double delta2 = 0;
		final double OPTIMAL_TPSTIME = 1000000000.0/TARGET_TPS;
		final double OPTIMAL_FPSTIME = 1000000000.0/TARGET_FPS;
		//float dt = 0;
		int fps = 0;
		int tps = 0;
		
		running = true;
		
		while(running){		
			currentTime = System.nanoTime();
			passedTime = currentTime - previousTime;
			delta += passedTime / OPTIMAL_TPSTIME;
			delta2 += passedTime / OPTIMAL_FPSTIME;
			
			previousTime = currentTime;
			
			if(delta >= 1){
//				getInput();
				tick();
				tps++;
				delta--;
			}
			
			if(delta2 >= 1){
				drawView.postInvalidate();
				fps++;
				delta2--;
			}
		
			if((System.currentTimeMillis() - timer) >= 1000){
				timer += 1000;
				//Display.getFrame().setTitle(TITLE + "  ||  " + tps + " tps, " + fps +  " fps");
//				System.out.println(tps + " tps, " + fps +  " fps");
				tps = 0;
				fps = 0;
			}
		}
	}
	
	private void tick(){
		drawView.tick();
	}

}
