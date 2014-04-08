package com.spaceshooter.game.engine;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.spaceshooter.game.view.GameView;

public class GameEngine implements Runnable{
	
	public static final double TARGET_TPS = 60.0;
	private double refreshRate;

	private volatile boolean running = false;
	private volatile boolean okToDraw = false;
	private volatile boolean finished = false;
	private volatile float interpolation;
	
	private Thread updateThread, drawingThread;
	
	private GameView gameView;
	private SurfaceHolder surfaceHolder;

	private Lock lock = new ReentrantLock();
	private Condition drawingCall = lock.newCondition();
	
	public GameEngine(SurfaceHolder holder, GameView gameView, final double refreshRate){
		this.gameView = gameView;
		this.surfaceHolder = holder;
		this.refreshRate = refreshRate;
	}
	
	/**
	 * Starts the update and draw threads
	 */
	public synchronized void start(){
		running = true;
		
		if(updateThread == null){
			updateThread = new Thread(this, "Game-Thread");
			updateThread.start();
		}
		
		startDrawingThread();
	}
	
	/**
	 * Stops the update and draw threads
	 */
	public synchronized void stop(){
		if(!running) return;
		running = false;
		boolean retry = true;
		while(retry){
			try {
				drawingThread.join();
				updateThread.join();
				retry = false;
			} catch (InterruptedException e) {e.printStackTrace();}	
		}
	}
	
	public synchronized void pause(){
		
	}
	
	public synchronized void resume(){
		
	}
	
	public synchronized void startDrawingThread(){
		if(drawingThread == null){
			drawingThread = new Thread(){
				public void run(){
					Canvas canvas;
					while(running){
						canvas = null;
						lock.lock();
						while(!okToDraw){
							try {
								drawingCall.await();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					
						if(!finished){
							draw(canvas, interpolation);
							okToDraw = false;
						}

						lock.unlock();
					}
				}
			};
			drawingThread.start();
		}
	}
	
	private void tick(float dt){
		gameView.tick(dt);
	}
	
	private void draw(Canvas canvas, float interpolation){
			try{
				canvas = surfaceHolder.lockCanvas();
				if(canvas != null){
					synchronized(surfaceHolder){
						gameView.draw(canvas, interpolation);
					}
				}	
			}finally{
				if(canvas != null)
					surfaceHolder.unlockCanvasAndPost(canvas);
			}
		
	}
	
	@Override
	public void run() {
		double previousTime = System.nanoTime();
		double currentTime = 0;
		//the time it takes to execute one cycle of the gameloop
		double passedTime = 0;
		//keeps track of how many times the game needs to be updated
		double accumulator = 0;
		//used to keep track of tps and fps count
		double frameCounter = 0;
		//the time between every update call in seconds
		final double OPTIMAL_UPDATETIME = 1.0/TARGET_TPS;
	
		float dt = (float)OPTIMAL_UPDATETIME * 10;
		
		int fps = (int) refreshRate;
		int tps = 0;
	
		while(running){	
			currentTime = System.nanoTime();
			passedTime = (currentTime - previousTime);
			accumulator += passedTime / 1000000000.0;	
			frameCounter += passedTime;
			
			interpolation = (float) ((passedTime/1000000000.0) / OPTIMAL_UPDATETIME);
	
			previousTime = currentTime;
		
			while(accumulator >= OPTIMAL_UPDATETIME){
				lock.lock();
				tick(dt);
				tps++;
				accumulator -= OPTIMAL_UPDATETIME;
				okToDraw = true;
				drawingCall.signalAll();
				lock.unlock();
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
		okToDraw = true;
		drawingCall.signalAll();
		lock.unlock();
	}
	
	
// one thread solution(pretty bug)
//	@Override
//	public void run() {
//		double previousTime = System.nanoTime();
//		double currentTime = 0;
//		double passedTime = 0; 
//		double accumulator = 0;
//		final double OPTIMAL_UPDATETIME = 1.0/TARGET_TPS;
//		float dt = (float)OPTIMAL_UPDATETIME * 10;
//		final int MAX_FRAME_SKIP = 5;
//		Canvas c;
//		boolean render;
//		
//		double last = System.nanoTime();
//		double now = 0;
//		double elapsed = 0;
//		int loops = 0;
//		int sleepTime = 0;
//		while(running){	
//			c = null;
//			render = false;
//			now = 0;
//			elapsed = 0;
//			loops = 0;
//			sleepTime = 0;
//			currentTime = System.nanoTime();
//			passedTime = (currentTime - previousTime) / 1000000000.0;
//			if(passedTime > 0.25) passedTime = 0.25;
//			accumulator += passedTime;	
//			previousTime = currentTime;
//		
//			while(accumulator >= OPTIMAL_UPDATETIME){
//				render = true;
//				//now = System.nanoTime();
//				tick(dt);
//				accumulator -= OPTIMAL_UPDATETIME;
//			}
//
//			if(render){
//				interpolation = (float) ((accumulator) / OPTIMAL_UPDATETIME);
//				draw(interpolation);
//				elapsed = (System.nanoTime() - currentTime) / 1000000.0;
//				//System.out.println(elapsed + " ms");
//				sleepTime = (int) (elapsed - (1000.0/TARGET_TPS));
//				if(sleepTime > 0){
//					try {
//						Thread.sleep(sleepTime);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//				
//				while(sleepTime < 0 && loops < MAX_FRAME_SKIP){
//					tick(dt);
//					sleepTime += (1000.0/TARGET_TPS);
//					loops++;
//				}
//				
//			}
//		
//		}
//	}
	
	
	
}
