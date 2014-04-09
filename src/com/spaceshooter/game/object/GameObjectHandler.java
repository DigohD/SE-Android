package com.spaceshooter.game.object;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.spaceshooter.game.object.player.Player;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class GameObjectHandler{
	
	private List<DynamicObject> gameObjects;
	private Player player;
	private Semaphore mutex = new Semaphore(1);
	
	private Paint paint;
	
	public GameObjectHandler() {
		player = new Player(new Vector2f(400, 400));
		gameObjects = new ArrayList<DynamicObject>();
		
		paint = new Paint();
	}
	
	public boolean collisionBetween(Rect r1, Rect r2){
		if(r1.intersect(r2) || r1.contains(r2))
			return true;
		return false;
	}
	
	public void collisionCheck(){
		for(int i = 0; i < gameObjects.size(); i++){
			DynamicObject obj = gameObjects.get(i);
			if(collisionBetween(player.getRect(), obj.getRect())){
				removeGameObject(obj);
				player.setScore(10);
			}
				
		}
	}

	public void addGameObject(DynamicObject go){
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gameObjects.add(go);
		mutex.release();
	}
	
	public void removeGameObject(DynamicObject go){
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		go.getBitmap().recycle();
		gameObjects.remove(go);
		mutex.release();
	}
	
	public void tick(float dt){
		for(int i = 0; i < gameObjects.size(); i++)
			if(gameObjects.get(i).getY() > GameView.height){
				removeGameObject(gameObjects.get(i));
			}
		collisionCheck();
		
		player.tick(dt);
				
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(DynamicObject go : gameObjects)
			go.tick(dt);
		
		
		
		mutex.release();

	}
	
	public void draw(Canvas canvas, float interpolation){
		
		player.draw(canvas, interpolation);
		
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(DynamicObject go : gameObjects)
    		go.draw(canvas, interpolation);
		
		mutex.release();
		
		paint.setColor(Color.RED);
		canvas.drawText("SCORE: " + player.getScore(), 20, 20, paint);
	}
	
	public List<DynamicObject> getGameObject(){
		return gameObjects;
	}
	
	public Player getPlayer(){
		return player;
	}

}
