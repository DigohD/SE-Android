package com.spaceshooter.game.object;

import android.graphics.Rect;

public interface Collideable {

	public void collisionWith(GameObject obj);
	public Rect getRect();

}
