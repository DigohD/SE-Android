package com.spaceshooter.game.engine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.spaceshooter.game.view.GameView;

/**
 * The game thread which runs the main loop.
 * The main loop is responsible for updating the game logic and calling the draw method in game view
 * @author Anders
 *
 */
public class GameThread implements Runnable{
	
	public static final double TARGET_TPS = 60.0;

	private volatile boolean running = false;
	
	private Thread thread;
	private GameView gameView;
	private SurfaceHolder surfaceHolder;
	
	/**
	 * Instantiates the game thread
	 * @param holder the surfaceholder which provides the canvas on which to draw on
	 * @param gameView the main view for the game
	 */
	public GameThread(SurfaceHolder holder, GameView gameView){
		this.gameView = gameView;
		this.surfaceHolder = holder;
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
		boolean retry = true;
		while(retry){
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {e.printStackTrace();}	
		}
	}
	
	public synchronized void pause(){
		
	}
	
	public synchronized void resume(){
		
	}
	
	/**
	 * Calls the tick method in gameview which will update all game logic
	 * @param dt the delta time variable which is used for physics calculations
	 */
	private void tick(float dt){
		gameView.tick(dt);
	}
	
	/**
	 * Gets hold of a canvas via the surfaceholder and then draws to it by calling the draw method in gameview.
	 * @param canvas the canvas which everything will get drawn on
	 * @param interpolation a factor based on how much time has gone since the last tick,
	 *  which will be used in a prediction function which is defined for all dynamic objects
	 */
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
	
	/**
	 * The heartbeat of the game: the main game loop.
	 * It is a fixed time step loop which means that the game logic gets updated
	 * at regular intervals, in our case 60 times every second. The reason for this is that we dont want our game logic updated
	 * faster on faster machines and slower on slower machines, the game should not depend on the machine you play it on.
	 *  
	 *  The way to achieve this is by letting the accumulator variable store the time it takes to complete one loop every time until it
	 * reaches the desired interval time. In our case we want 60 updates every second so the time between every update call
	 * should therefor be 1/60 seconds which is about 16.6 ms. So when our accumulator reaches 16.6 ms we know it is time to update.
	 * 
	 * We also use linear interpolation to gain more smoother animations and movement. The value we use for linear interpolation is the remainder
	 * of the accumulator which simply is a measure of how much more time is required before the next update.
	 * The idea of interpolation is to predict a state to draw which is between the current and the next update state. Why this is necessary is
	 * because a draw might happen in between two states and to interpolate between the two states results in smoother movement/animation.
	 */
	@Override
	public void run() {
		double previousTime = System.nanoTime();
		double currentTime = 0;
		double passedTime = 0; 
		double accumulator = 0;
		double frameCounter = 0;
		final double OPTIMAL_UPDATETIME = 1.0/TARGET_TPS;
		float dt = (float)OPTIMAL_UPDATETIME * 10;
		
		float interpolation = 0;
		
		int fps = 0;
		int tps = 0;
	
		Canvas canvas;
		boolean render;
		
//		double startTime = 0, endTime = 0;
//		double finalDelta = 0;
//		long sleepTime = 0;
//		double msPerTick = (OPTIMAL_UPDATETIME*1000.0);
		
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
				//startTime = System.nanoTime();
				tick(dt);
				tps++;
				accumulator -= OPTIMAL_UPDATETIME;
			}

			if(render){
				interpolation = (float) (accumulator / OPTIMAL_UPDATETIME);
				draw(canvas, interpolation);
				fps++;
				
//				endTime = System.nanoTime();
//				finalDelta = (endTime - startTime) / 1000000.0;
//				sleepTime = (long) (msPerTick - finalDelta);
//				
//				if(finalDelta < msPerTick){
//					try {
//						Thread.sleep(sleepTime);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
			}
			
			//fps and tps counter
			if(frameCounter >= 1){
				//System.out.println(tps +  " tps, " + fps + " fps" );
				tps = 0;
				fps = 0;
				frameCounter = 0;
			}
			
		}
	}
	
}
