package se.chalmers.spaceshooter.object;

import android.graphics.Rect;

public interface Collideable {

	public void collisionWith(Collideable obj);

	public Rect getRect();

}
