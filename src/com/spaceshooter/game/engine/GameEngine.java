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
	
	private Thread thread, drawingThread;
	
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
		
		if(thread == null){
			thread = new Thread(this, "Game-Thread");
			thread.start();
		}

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
				//drawingThread.join();
				thread.join();
				retry = false;
			} catch (InterruptedException e) {e.printStackTrace();}	
		}
	}
	
	public synchronized void pause(){
		
	}
	
	public synchronized void resume(){
		
	}
	
//	public synchronized void startDrawingThread(){
//		if(drawingThread == null){
//			drawingThread = new Thread(){
//				public void run(){
//					Canvas canvas;
//					while(running){
//						canvas = null;
//						lock.lock();
//						while(!okToDraw){
//							try {
//								drawingCall.await();
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//						}
//					
//						if(!finished){
//							draw(canvas, interpolation);
//							okToDraw = false;
//						}
//
//						lock.unlock();
//					}
//				}
//			};
//			drawingThread.start();
//		}
//	}
	
	private void tick(float dt){
		gameView.tick(dt);
	}
	
	private void draw(Canvas canvas, float interpolation){
			try{
				canvas = surfaceHolder.lockCanvas();
				if(canvas != null){
					gameView.draw(canvas, interpolation);
				}	
			}finally{
				if(canvas != null)
					surfaceHolder.unlockCanvasAndPost(canvas);
			}
	}
	
//	@Override
//	public void run() {
//		double previousTime = System.nanoTime();
//		double currentTime = 0;
//		//the time it takes to execute one cycle of the gameloop
//		double passedTime = 0;
//		//keeps track of how many times the game needs to be updated
//		double accumulator = 0;
//		//used to keep track of tps and fps count
//		double frameCounter = 0;
//		//the time between every update call in seconds
//		final double OPTIMAL_UPDATETIME = 1.0/TARGET_TPS;
//	
//		float dt = (float)OPTIMAL_UPDATETIME * 10;
//		
//		int fps = (int) refreshRate;
//		int tps = 0;
//	
//		while(running){	
//			currentTime = System.nanoTime();
//			passedTime = (currentTime - previousTime) / 1000000000.0;
//			if(passedTime > 0.25) passedTime = 0.25;
//			accumulator += passedTime;	
//			frameCounter += passedTime;
//			
//			interpolation = (float) ((passedTime) / OPTIMAL_UPDATETIME);
//	
//			previousTime = currentTime;
//		
//			while(accumulator >= OPTIMAL_UPDATETIME){
//				lock.lock();
//				tick(dt);
//				tps++;
//				accumulator -= OPTIMAL_UPDATETIME;
//				okToDraw = true;
//				drawingCall.signalAll();
//				lock.unlock();
//			}
//					
//			if(frameCounter >= 1000000000){
//				//System.out.println(tps +  " tps, " + fps + " fps" );
//				tps = 0;
//				fps = 0;
//				frameCounter = 0;
//			}
//		}
//		lock.lock();
//		finished = true;
//		okToDraw = true;
//		drawingCall.signalAll();
//		lock.unlock();
//	}
	
	// one thread solution
	@Override
	public void run() {
		double previousTime = System.nanoTime();
		double currentTime = 0;
		double passedTime = 0; 
		double accumulator = 0;
		double frameCounter = 0;
		final double OPTIMAL_UPDATETIME = 1.0/TARGET_TPS;
		float dt = (float)OPTIMAL_UPDATETIME * 10;
		
		int fps = 0;
		int tps = 0;
	
		Canvas canvas;
		boolean render;
		
		while(running){	
			canvas = null;
			render = false;
			
			currentTime = System.nanoTime();
			passedTime = (currentTime - previousTime) / 1000000000.0;
			if(passedTime > 0.25) passedTime = 0.25;
			accumulator += passedTime;	
			frameCounter += passedTime;
			previousTime = currentTime;
		
			while(accumulator >= OPTIMAL_UPDATETIME){
				render = true;
				tick(dt);
				tps++;
				accumulator -= OPTIMAL_UPDATETIME;
			}

			if(render){
				interpolation = (float) (accumulator / OPTIMAL_UPDATETIME);
				draw(canvas, interpolation);
				fps++;
			}
			
//			if(frameCounter >= 1){
//				System.out.println(tps +  " tps, " + fps + " fps" );
//				tps = 0;
//				fps = 0;
//				frameCounter = 0;
//			}
			
		}
	}
	
}
