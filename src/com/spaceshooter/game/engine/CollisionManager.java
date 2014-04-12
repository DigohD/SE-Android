package com.spaceshooter.game.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import android.graphics.Rect;

import com.spaceshooter.game.object.enemy.Enemy;
import com.spaceshooter.game.object.player.Player;

/**
 * Class for managing and detecting collisions
 * 
 * @author Anders
 * 
 */
public class CollisionManager {

	// the list of enemies which will be checked for collisions
	public static List<Enemy> enemies = new ArrayList<Enemy>();
	// synchronization primitive used to ensure mutual exclusion
	private static Semaphore mutex = new Semaphore(1);

	/**
	 * Stores an enemy in the enemy collision list. The list will be traversed
	 * in order to check for collisions
	 * 
	 * @param e
	 *            the enemy to be stored
	 */
	public static void addEnemy(Enemy e) {
		try {
			mutex.acquire();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		enemies.add(e);
		mutex.release();
	}

	/**
	 * Removes an enemy from the enemy collision list
	 * 
	 * @param e
	 *            the enemy to be removed
	 */
	public static void removeEnemy(Enemy e) {
		try {
			mutex.acquire();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		enemies.remove(e);
		mutex.release();
	}

	/**
	 * Checks if two rectangles intersect
	 * 
	 * @param r1
	 *            first rectangle
	 * @param r2
	 *            second rectangle
	 * @return true if the two rectangles intersects otherwise it returns false
	 */
	private static boolean collisionBetween(Rect r1, Rect r2) {
		if (r1.intersect(r2) || r1.contains(r2))
			return true;
		return false;
	}

	/**
	 * Checks for the following collisions: 1.) Player and enemies 2.) Player
	 * projectiles and enemies 3.) Enemy projectiles and player If collision
	 * occurs the collisionWith method gets called for the victim of the
	 * collision where the collision gets handled internally by that object
	 * 
	 * @param player
	 *            the player ship
	 */
	public static void collisionCheck(Player player) {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if (player != null && e != null)
				if (collisionBetween(player.getRect(), e.getRect()))
					player.collisionWith(e);
		}
	}

}
