package com.spaceshooter.game.object;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import android.graphics.Canvas;

import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.view.GameView;

public class GameObjectHandler{
	
	private List<GameObject> gameObjects;
	private Semaphore mutex = new Semaphore(1);
	
	public GameObjectHandler() {
		gameObjects = new ArrayList<GameObject>();
	}

	public void addGameObject(GameObject go){
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gameObjects.add(go);
		mutex.release();
	}
	
	public void removeGameObject(GameObject go){
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gameObjects.remove(go);
		mutex.release();
	}
	
	public void tick(float dt){
		for(int i = 0; i < gameObjects.size(); i++)
			if(gameObjects.get(i).getY() > GameView.height){
				gameObjects.get(i).getBitmap().recycle();
				removeGameObject(gameObjects.get(i));
			}
				
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(GameObject go : gameObjects)
			go.tick(dt);
		
		mutex.release();

	}
	
	public void draw(Canvas canvas, float interpolation){

		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(GameObject go : gameObjects)
    		go.draw(canvas, interpolation);
		
		mutex.release();
	}
	
	public List<GameObject> getGameObject(){
		return gameObjects;
	}

}
