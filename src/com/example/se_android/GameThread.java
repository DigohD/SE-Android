package com.example.se_android;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.content.Context;
import android.graphics.Canvas;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

public class GameThread implements Runnable{
	
	public static final double TARGET_TPS = 60.0;
	
	private Thread thread, renderThread;
	
	private volatile boolean running = false;
	private volatile boolean okToRender = false;
	private volatile boolean finished = false;
	
	private volatile int stateIndex = 0;
	
	private GameView gameView;
	private SurfaceHolder holder;
	private GameWorld gw;
	
	volatile float interpolation;
	
	private double refreshRate;
	
	private Lock lock = new ReentrantLock();
	private Condition renderCall = lock.newCondition();
	
	public GameThread(SurfaceHolder holder, GameView gameView, final double refreshRate){
		this.gameView = gameView;
		this.holder = holder;
		this.refreshRate = refreshRate;

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
	
	/**
	 * Stops the game thread
	 */
	public synchronized void stop(){
		if(!running) return;
		running = false;
		boolean retry = true;
		if(thread != null && renderThread != null){
			while(retry){
				try {
					renderThread.join();
					thread.join();
					retry = false;
				} catch (InterruptedException e) {e.printStackTrace();}
				
			}
		}
		
		
		
	}
	
	public Thread getUpdateThread(){
		return thread;
	}
	
	public Thread getRenderhread(){
		return renderThread;
	}
	
	public synchronized void startRender(){
		if(renderThread == null){
			renderThread = new Thread(){

				public void run(){
					double previousTime = System.nanoTime();
					double currentTime = 0;
					//the time it takes to execute one cycle of the gameloop
					double passedTime = 0;
					Canvas canvas;
					while(running){
						
						
						canvas = null;

						lock.lock();
						
						while(!okToRender){
							
							try {
								renderCall.await();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
						currentTime = System.nanoTime();
						passedTime = (currentTime - previousTime);
						previousTime = currentTime;
						
						if(!finished){
							//interpolation = (float) ((passedTime/1000000000.0) / (1.0/refreshRate));
							draw(canvas, interpolation);
						}

						lock.unlock();
					}
				}
			};
			renderThread.start();
		}
	}
	
	private synchronized float lerp(float start, float end, float dt){
		return (start + dt*(end-start));
	}
	
	
	private void tick(float dt){
		gameView.tick(dt);
	}
	
	private void draw(Canvas canvas, float interpolation){
		try{
			canvas = holder.lockCanvas();
			if(canvas != null){
				//System.out.println(canvas.isHardwareAccelerated());
				gameView.draw(canvas, interpolation);
			}
				
		}finally{
			if(canvas != null)
					holder.unlockCanvasAndPost(canvas);
		}	
		okToRender = false;
	}
	
	@Override
	public void run() {
		Canvas canvas;
		double previousTime = System.nanoTime();
		double currentTime = 0;
		//the time it takes to execute one cycle of the gameloop
		double passedTime = 0;
		//keeps track of how many times the game needs to be updated
		double accumulator = 0;
		//used to keep track of tps and fps count
		double frameCounter = 0;
		//the time between every update call in seconds
		final double OPTIMAL_UPDATETIME = 1.0/refreshRate;
		
		final int MAX_FRAMESKIP = 5;
	
		float dt = (float)OPTIMAL_UPDATETIME * 10;
		//number of frames per second
		int fps = 0;
		//number of updates per second
		int tps = 0;
		int loops;
		while(running){	

			currentTime = System.nanoTime();
			passedTime = (currentTime - previousTime);
			accumulator += passedTime / 1000000000.0;	
			frameCounter += passedTime;
			
			interpolation = (float) ((passedTime/1000000000.0) / OPTIMAL_UPDATETIME);
	
			previousTime = currentTime;
			loops = 0;
			while(accumulator >= OPTIMAL_UPDATETIME && loops < MAX_FRAMESKIP){
				
				lock.lock();
				tick(dt);
				tps++;
				okToRender = true;
				renderCall.signalAll();
				lock.unlock();

				accumulator -= OPTIMAL_UPDATETIME;
				loops++;
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
		okToRender = true;
		renderCall.signalAll();
		lock.unlock();
	
	}
}
