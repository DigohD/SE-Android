package com.example.se_android;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread implements Runnable{
	
	public static final double TARGET_TPS = 60.0;
	
	private Thread thread, renderThread;
	
	public Thread getUpdateThread(){
		return thread;
	}
	
	public Thread getRenderhread(){
		return renderThread;
	}
	
	private volatile boolean running = false;
	private volatile boolean okToRender = false;
	private volatile boolean finished = false;
	
	private GameView gameView;
	private SurfaceHolder holder;
	
	private Lock lock = new ReentrantLock();
	private Condition renderCall = lock.newCondition();
	
	public GameThread(SurfaceHolder holder, GameView gameView){
		this.gameView = gameView;
		this.holder = holder;
	}
	
	/**
	 * Starts the game thread
	 */
	public synchronized void start(){
		running = true;
		startRender();
		if(thread == null){
			thread = new Thread(this, "Game-Thread");
			thread.start();
			
		}
		
	}
	
//	/**
//	 * Stops the game thread
//	 */
//	public synchronized void stop(){
//		if(!running) return;
//		running = false;
//		if(thread != null){
//			try {
//				renderThread.join();
//				thread.join();
//			} catch (InterruptedException e) {e.printStackTrace();}
//		}
//		
//	}
	
	public synchronized void setRunning(boolean running){
		this.running = running;
	}
	
	public synchronized void startRender(){
		if(renderThread == null){
			renderThread = new Thread(){
				public void run(){
					Canvas canvas;
					while(running){
						canvas = null;
						
						lock.lock();
						while(!okToRender){
							if(finished) break;
							try {
								renderCall.await();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
							
						if(!finished){
							try{
								canvas = holder.lockCanvas();
								if(canvas != null)
									synchronized(holder){
										gameView.draw(canvas);
									}
									
							}finally{
								if(canvas != null)
										holder.unlockCanvasAndPost(canvas);
							}	
							okToRender = false;
						}
					
						lock.unlock();
					}
					
				}
			};
			renderThread.start();
		}
	}
	
	
	private void tick(float dt){
		gameView.tick(dt);
	}
	
//	private void draw(Canvas canvas){
//		try{
//			canvas = holder.lockCanvas();
//			if(canvas != null){
//				synchronized(holder){
//					gameView.draw(canvas);
//				}
//			}
//		}finally{
//			if(canvas != null)
//					holder.unlockCanvasAndPost(canvas);
//		}	
//	}
	

	@Override
	public void run() {
		double previousTime = System.nanoTime();
		double currentTime = 0;
		//the time it takes to execute one cycle of the gameloop
		double passedTime = 0;
		//keeps track of how many times the game needs to be updated
		double unprocessedTime = 0;
		//used to keep track of tps and fps count
		double frameCounter = 0;
		//the time between every update call in seconds
		final double OPTIMAL_UPDATETIME = 1.0/TARGET_TPS;
	
		//the value of delta will be used for time dependent calculations like physics
		float delta = 0;
		//number of frames per second
		int fps = 0;
		//number of updates per second
		int tps = 0;
		
		while(running){	
		
			currentTime = System.nanoTime();
			passedTime = (currentTime - previousTime);
			unprocessedTime += passedTime / 1000000000.0;	
			frameCounter += passedTime;
					
			delta = (float) ( passedTime / 100000.0f );
			if(delta > 0.15f) delta = 0.15f;
					
			previousTime = currentTime;
					
			while(unprocessedTime >= OPTIMAL_UPDATETIME){
				
				lock.lock();
				tick(delta);
				tps++;
				okToRender = true;
				renderCall.signalAll();
				lock.unlock();
				
				unprocessedTime -= OPTIMAL_UPDATETIME;
			}
						
			if(frameCounter >= 1000000000){
				//System.out.println(tps +  " tps, " + fps + " fps" );
				tps = 0;
				fps = 0;
				frameCounter = 0;
			}
		}
		lock.lock();
		finished = true;
		renderCall.signalAll();
		lock.unlock();
	
	}
}
