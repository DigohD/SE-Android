package com.example.se_android;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import android.graphics.Canvas;

public class GameWorld{
	
	private List<GameObject> gameObjects;
	private Semaphore mutex = new Semaphore(1);
	
	public GameWorld() {
		
		gameObjects = new ArrayList<GameObject>();
		gameObjects.add(new Ground(0, 650, 480, 20));
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
	
	public void tick(float dt){
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(GameObject go : gameObjects)
			go.tick(dt);
		
		mutex.release();
	}
	
	public void draw(Canvas canvas){
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(GameObject go : gameObjects)
    		go.draw(canvas);
		
		mutex.release();
	}

}
