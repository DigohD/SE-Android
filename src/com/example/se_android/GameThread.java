package com.example.se_android;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread implements Runnable{
	
	public static final int TARGET_TPS = 180;
	public static final double TARGET_FPS = 180;
	private boolean FPS_CAP = true;
	
	private Thread thread, renderThread;
	private volatile boolean running = false;
	
	private GameView gameView;
	private SurfaceHolder holder;
	
	public GameThread(SurfaceHolder holder, GameView gameView){
		this.gameView = gameView;
		this.holder = holder;
	}
	
	/**
	 * Starts the game thread
	 */
	public synchronized void start(){
		running = true;
		if(thread == null){
			thread = new Thread(this, "Game-Thread");
			thread.start();
		}
	}
	
	/**
	 * Stops the game thread
	 */
	public synchronized void stop(){
		if(!running) return;
		running = false;
		if(thread != null){
			try {
				thread.join();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	private void tick(float dt){
		gameView.tick(dt);
	}
	
	private void draw(Canvas canvas){
		try{
			canvas = holder.lockCanvas();
			if(canvas != null){
				synchronized(holder){
					gameView.draw(canvas);
				}
			}
		}finally{
			if(canvas != null)
					holder.unlockCanvasAndPost(canvas);
		}	
	}
	

	@Override
	public void run() {
		Canvas canvas;
		double previousTime = System.nanoTime();
		double currentTime = 0;
		//the time it takes to execute one cycle of the gameloop
		double passedTime = 0;
		//keeps track of how many times the game needs to be updated
		double unprocessedUpdateTime = 0;
		//keeps track of how many times the game needs to be rendered
		double unprocessedFrameTime = 0;
		//used to keep track of tps and fps count
		double frameCounter = 0;
		//the optimal time for every update
		final double OPTIMAL_UPDATETIME = 1.0/TARGET_TPS;
		final double OPTIMAL_FRAMETIME = 1.0/TARGET_FPS;
	
		//the value of delta will be used for time dependent calculations like physics
		float delta = 0;
		//number of frames per second
		int fps = 0;
		//number of updates per second
		int tps = 0;
		
		boolean shouldRender;
		
		while(running){	
			canvas = null;
			shouldRender = false;
			
			currentTime = System.nanoTime();
			passedTime = (currentTime - previousTime);
			unprocessedUpdateTime += passedTime / 1000000000.0;
			unprocessedFrameTime += passedTime / 1000000000.0;
					
			frameCounter += passedTime;
					
			delta = (float) ( passedTime / 100000000.0f );
			//System.out.println(delta);
			if(delta > 0.15f) delta = 0.15f;
					
			previousTime = currentTime;
					
			while(unprocessedUpdateTime >= OPTIMAL_UPDATETIME){
				tick(delta);
				tps++;
				draw(canvas);
				fps++;
				unprocessedUpdateTime -= OPTIMAL_UPDATETIME;
			}
					
//			try{
//				canvas = holder.lockCanvas();
//				if(canvas != null){
//					synchronized(holder){
//						
//						gameView.draw(canvas);
//						fps++;
//						
//					}
//				}
//			}finally{
//				if(canvas != null)
//						holder.unlockCanvasAndPost(canvas);
//			}	
//			try{
//				canvas = holder.lockCanvas();
//				if(canvas != null){
////					(holder){
////						
//						gameView.draw(canvas);
//						fps++;
//						
////					}
//				}
//			}finally{
//				if(canvas != null)
//						holder.unlockCanvasAndPost(canvas);
//			}	
				
			
				
					
			if(FPS_CAP){
				while(unprocessedFrameTime >= OPTIMAL_FRAMETIME){
					shouldRender = true;
					unprocessedFrameTime -= OPTIMAL_FRAMETIME;
				}
			}else{
				fps++;
				draw(canvas);
			}
					
			if(shouldRender){
				fps++;
				draw(canvas);
			}else if(!shouldRender && FPS_CAP){
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {e.printStackTrace();}
			}
						
			if(frameCounter >= 1000000000){
				System.out.println(tps +  " tps, " + fps + " fps" );
				tps = 0;
				fps = 0;
				frameCounter = 0;
			}
		}
		stop();	
	}
}
