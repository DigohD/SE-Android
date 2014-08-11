package se.chalmers.spaceshooter.game.object;

import android.graphics.Rect;

/**
 * An interface which all collideable gameobjects much implement
 * 
 * @author Anders
 * 
 */
public interface Collideable {
	/**
	 * In this method the collideable gameobject will handle what happens when
	 * it collides with another collideable gameobject.
	 * 
	 * @param obj
	 *            the gameobject it collided with
	 */
	public void collisionWith(Collideable obj);

	public Rect getRect();
}
