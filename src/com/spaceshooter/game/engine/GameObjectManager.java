package com.spaceshooter.game.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.enemy.Enemy;
import com.spaceshooter.game.object.player.Player;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class GameObjectManager {

	public static List<GameObject> gameObjects;

	private Player player;
	private static Semaphore mutex = new Semaphore(1);

	private Paint paint;

	public GameObjectManager() {
		player = new Player(new Vector2f(400, 400));
		gameObjects = new ArrayList<GameObject>();
		paint = new Paint();
	}

	public static void addGameObject(GameObject go) {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		gameObjects.add(go);
		mutex.release();
	}

	public static void removeGameObject(GameObject go) {
		go.getBitmap().recycle();
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gameObjects.remove(go);
		mutex.release();
	}

	private void clearOutOfBoundObjects() {
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject go = gameObjects.get(i);
			if (go.getY() > GameView.height) {
				removeGameObject(go);
				if (go instanceof Enemy) {
					Enemy e = (Enemy) go;
					CollisionManager.removeEnemy(e);
				}
			}
		}
	}

	public void tick(float dt) {
		clearOutOfBoundObjects();
		player.tick(dt);

		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (GameObject go : gameObjects)
			go.tick(dt);

		mutex.release();
	}

	public void draw(Canvas canvas, float interpolation) {
		player.draw(canvas, interpolation);
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (GameObject go : gameObjects)
			go.draw(canvas, interpolation);

		mutex.release();

		paint.setColor(Color.RED);
		canvas.drawText("SCORE: " + player.getScore(), 20, 20, paint);
	}

	public List<GameObject> getGameObject() {
		return gameObjects;
	}

	public Player getPlayer() {
		return player;
	}

}
