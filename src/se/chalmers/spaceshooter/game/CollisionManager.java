package se.chalmers.spaceshooter.engine;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.spaceshooter.game.object.enemy.Enemy;
import se.chalmers.spaceshooter.game.object.loot.Loot;
import se.chalmers.spaceshooter.game.object.projectile.Projectile;
import android.graphics.Rect;

/**
 * Class for managing and detecting collisions
 * 
 * @author Anders
 * 
 */
public class CollisionManager {
	// the list of enemies which will be checked for collisions
	private static List<Enemy> enemies = new ArrayList<Enemy>();
	// the list of loot which will be checked for collisions
	private static List<Loot> loots = new ArrayList<Loot>();
	// the list of player projectiles which will be checked for collisions
	private static List<Projectile> playerProjectiles = new ArrayList<Projectile>();
	// the list of enemy projectiles which will be checked for collisions
	private static List<Projectile> enemyProjectiles = new ArrayList<Projectile>();

	public static void addEnemy(Enemy e) {
		enemies.add(e);
	}

	public static void addLoot(Loot loot) {
		loots.add(loot);
	}

	public static void addPlayerProjectile(Projectile proj) {
		playerProjectiles.add(proj);
	}

	public static void addEnemyProjectile(Projectile proj) {
		enemyProjectiles.add(proj);
	}

	public static void removeEnemy(Enemy e) {
		enemies.remove(e);
	}

	public static void removeLoot(Loot loot) {
		loots.remove(loot);
	}

	public static void removeEnemyProjectile(Projectile proj) {
		enemyProjectiles.remove(proj);
	}

	public static void removePlayerProjectile(Projectile proj) {
		playerProjectiles.remove(proj);
	}

	public static void clear() {
		enemies.clear();
		loots.clear();
		playerProjectiles.clear();
		enemyProjectiles.clear();
	}

	/**
	 * Checks if two rectangles intersect
	 * 
	 * @param r1
	 *            the first rectangle which will be checked against the second
	 *            one
	 * @param r2
	 *            the second rectangle
	 * @return returns true if the two rectangles intersects otherwise returns
	 *         false
	 */
	private static boolean collisionBetween(Rect r1, Rect r2) {
		if (r1.intersect(r2) || r1.contains(r2))
			return true;
		return false;
	}

	/**
	 * Checks for collisions between collideable gameobjects and if a collision
	 * has occured then the collisionWith method gets called for the affected
	 * object. All collideable objects must implement the collisionWith method.
	 * 
	 * @param player
	 *            the player ship
	 */
	public static void collisionCheck(Player player) {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if (player != null && e != null && player.isLive())
				if (collisionBetween(player.getRect(), e.getRect())) {
					e.collisionWith(player);
					player.collisionWith(e);
				}
		}

		for (int i = 0; i < loots.size(); i++) {
			Loot l = loots.get(i);
			if (player != null && l != null && player.isLive())
				if (collisionBetween(player.getRect(), l.getRect())) {
					player.collisionWith(l);
					l.collisionWith(player);
				}
		}

		for (int i = 0; i < playerProjectiles.size(); i++)
			for (int j = 0; j < enemies.size(); j++) {
				Projectile p = playerProjectiles.get(i);
				Enemy e = enemies.get(j);
				if (p != null && e != null)
					if (collisionBetween(p.getRect(), e.getRect())) {
						e.collisionWith(p);
						p.collisionWith(e);
					}
			}

		for (int i = 0; i < enemyProjectiles.size(); i++) {
			Projectile p = enemyProjectiles.get(i);
			if (p != null && player != null && player.isLive())
				if (collisionBetween(p.getRect(), player.getRect())) {
					player.collisionWith(p);
					p.collisionWith(player);
				}
		}

	}

	public static List<Enemy> getEnemies() {
		return enemies;
	}

	public static List<Loot> getLoots() {
		return loots;
	}

	public static List<Projectile> getPlayerProjectiles() {
		return playerProjectiles;
	}

	public static List<Projectile> getEnemyProjectiles() {
		return enemyProjectiles;
	}

}
