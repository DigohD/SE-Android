package com.spaceshooter.game.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.enemy.Enemy;
import com.spaceshooter.game.object.player.Player;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class GameObjectManager{
	
	public static List<GameObject> gameObjects;
	private Player player;
	private Paint paint;
	
	public GameObjectManager() {
		player = new Player(new Vector2f(400, 400));
		gameObjects = new ArrayList<GameObject>();
		paint = new Paint();
	}

	public static void addGameObject(GameObject go){
		gameObjects.add(go);
	}
	
	public static void removeGameObject(GameObject go){
		go.getBitmap().recycle();
		gameObjects.remove(go);
	}
	
	private void clearDeadGameObjects(){
		for(int i = 0; i < gameObjects.size(); i++){
			GameObject go = gameObjects.get(i);
			if(!go.isLive()){
				removeGameObject(go);
				if(go instanceof Enemy){
					Enemy e = (Enemy)go;
					CollisionManager.removeEnemy(e);
				}
			}
		}
	}
	
	public void tick(float dt){
		clearDeadGameObjects();
		CollisionManager.collisionCheck(player);
		player.tick(dt);
		
		for(GameObject go : gameObjects)
			go.tick(dt);
	}
	
	public void draw(Canvas canvas, float interpolation){
		player.draw(canvas, interpolation);
		
		for(GameObject go : gameObjects)
			go.draw(canvas, interpolation);

		paint.setColor(Color.RED);
		canvas.drawText("SCORE: " + player.getScore(), 20, 20, paint);
	}
	
	public List<GameObject> getGameObject(){
		return gameObjects;
	}
	
	public Player getPlayer(){
		return player;
	}

}
