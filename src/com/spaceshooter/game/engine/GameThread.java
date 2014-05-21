package com.spaceshooter.game.engine;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.spaceshooter.game.GameActivity;
import com.spaceshooter.game.view.GameView;
import com.spaceshooter.game.view.InventoryView;

/**
 * The game thread which runs the main loop. The main loop is responsible for
 * updating the game logic and calling the draw method in game view which will draw
 * the new updated state of the game
 * 
 * @author Anders
 * 
 */
public class GameThread implements Runnable {

	//the desired amount of update calls we want every second
	public static final double TARGET_TPS = 60.0;
	
	private volatile boolean running = false;
	private boolean paused = false;
	private boolean inventory;

	private Thread thread;
	private GameView gameView;
	private InventoryView invView;
	private SurfaceHolder surfaceHolder;
	
	private Lock lock = new ReentrantLock();
	private Condition okToRun = lock.newCondition();
	
	private Canvas canvas;

	/**
	 * The constructor for the game thread
	 * 
	 * @param holder the surfaceholder which provides the canvas on which to drawon
	 * @param gameView the main view for the game
	 */
	public GameThread(SurfaceHolder holder, GameView gameView) {
		this.gameView = gameView;
		this.surfaceHolder = holder;
		inventory = false;
	}
	
	/**
	 * The constructor for the game thread
	 * 
	 * @param holder the surfaceholder which provides the canvas on which to drawon
	 * @param gameView the main view for the game
	 */
	public GameThread(SurfaceHolder holder, InventoryView invView) {
		this.invView = invView;
		this.surfaceHolder = holder;
		inventory = true;
	}

	/**
	 * Starts the game thread
	 */
	public synchronized void start() {
		running = true;
		if (thread == null) {
			System.out.println("NEW THREAD");
			thread = new Thread(this, "Game-Thread");
			thread.start();
		}
	}

	/**
	 * Stops the game thread
	 */
	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		resume();
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * Pauses the game thread
	 */
	public synchronized void pause() {
		paused = true;
	}

	/**
	 * resumes the game thread
	 */
	public synchronized void resume() {
		lock.lock();
		paused = false;
		okToRun.signalAll();
		lock.unlock();
	}

	/**
	 * Calls the tick method in gameview which will update all game logic
	 * 
	 * @param dt the delta time variable which is used for physics calculations
	 */
	private void tick(float dt) {
		if(inventory)
			invView.tick(dt);
		else
			gameView.tick(dt);
	}

	/**
	 * Gets hold of a canvas via the surfaceholder and then draws to it by
	 * calling the draw method in gameview.
	 * 
	 * @param canvas the canvas which everything will get drawn on
	 * @param interpolation a factor based on how much time has gone since the last tick,
	 * 		  which will be used in a prediction function which is defined
	 *        for all dynamic objects
	 */
	private void draw(Canvas canvas, float interpolation) {
		synchronized (surfaceHolder) {
			try {
				canvas = surfaceHolder.lockCanvas();
				if (canvas != null) {
					
						if(inventory)
							invView.draw(canvas, interpolation);
						else
							gameView.draw(canvas, interpolation);
					
				}
			} finally {
				if (canvas != null)
					surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
		
		
	}

	/**
	 * The heartbeat of the game: the main game loop. It is a fixed time step
	 * loop which means that the game logic gets updated at regular intervals,
	 * in our case 60 times every second. The reason for this is that we dont
	 * want our game logic updated faster on faster machines and slower on
	 * slower machines, the speed of the game should not depend on the machine 
	 * you play it on.
	 * 
	 * The way to achieve this is by letting the accumulator variable store the
	 * time it takes to complete one loop every time until it reaches the
	 * desired interval time. In our case we want 60 updates every second so the
	 * time between every update call should therefor be 1/60 seconds which is
	 * about 16.6 ms. So when our accumulator reaches 16.6 ms we know it is time
	 * to update.
	 * 
	 * We also use linear interpolation to gain more smoother animations and
	 * movement. The value we use for linear interpolation is the remainder of
	 * the accumulator which simply is a measure of how much more time is
	 * required before the next update. The idea of interpolation is to predict
	 * a state to draw which is between the current and the next update state.
	 * Why this is necessary is because a draw might happen in between two
	 * states and to interpolate between the two states results in smoother
	 * movement/animation.
	 */
	@Override
	public void run() {
		double previousTime = System.nanoTime();
		double currentTime = 0;
		double passedTime = 0;
		double accumulator = 0;
		double frameCounter = 0;
		final double OPTIMAL_UPDATETIME = 1.0 / TARGET_TPS;
		float dt = (float) OPTIMAL_UPDATETIME * 10;

		float interpolation = 0;

		int fps = 0;
		int tps = 0;

		boolean shouldDraw;
		
		while (running) {
	
			lock.lock();
			while(paused){
				try {
					okToRun.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			lock.unlock();
			
			canvas = null;
			shouldDraw = false;
			
			currentTime = System.nanoTime();
			passedTime = (currentTime - previousTime) / 1000000000.0;
			//set a max limit for passedtime to avoid spiral of death
			if (passedTime > 0.25)
				passedTime = 0.25;
			accumulator += passedTime;
			frameCounter += passedTime;
			previousTime = currentTime;

			while(accumulator >= OPTIMAL_UPDATETIME) {
				shouldDraw = true;
				tick(dt);
				tps++;
				accumulator -= OPTIMAL_UPDATETIME;
			}
			
			if(shouldDraw){
				interpolation = (float) (accumulator / OPTIMAL_UPDATETIME);
				draw(canvas, interpolation);	
				fps++;
			}

			if(frameCounter >= 1) {
				//System.out.println(tps + " tps, " + fps + " fps" );
				tps = 0;
				fps = 0;
				frameCounter = 0;
			}
		}
	}
	
	public void goInventory(){
		Context c = gameView.getContext();
		GameActivity gA = (GameActivity) c;
		
		invView = new InventoryView(gameView.getContext());
		
		inventory = true;
		
		gA.setContentView(invView);
	}
	

	
	//Alternative game loop , does not appear to increase performance
	
//	double startTime = 0, endTime = 0;
//	double frameTime = 0;
//	long sleepTime = 0;
//	double targetFrameTime = (OPTIMAL_UPDATETIME*1000.0);
//	
//	int framesSkipped;
//	final int MAX_SKIPS = 5;
	
//	framesSkipped = 0;
	
//	startTime = System.nanoTime();
//	tick(dt);
//	interpolation = (float) (accumulator / OPTIMAL_UPDATETIME);
//	draw(canvas, interpolation);	
//endTime = System.nanoTime();
//
//frameTime = (endTime - startTime) / 1000000.0;
//sleepTime = (long) (targetFrameTime - frameTime);
//	
//if(frameTime < targetFrameTime){
//	try {
//		Thread.sleep(sleepTime);
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
//}
//	
//while(frameTime > targetFrameTime && framesSkipped < MAX_SKIPS){
//	tick(dt);
//	frameTime -= targetFrameTime;
//	framesSkipped = 0;
//}
	
	

}
