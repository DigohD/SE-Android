package com.spaceshooter.game.object.enemy;

import android.graphics.Canvas;

import com.spaceshooter.game.object.Collideable;
import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.util.Vector2f;

public abstract class Enemy extends DynamicObject implements Collideable {

	public Enemy(Vector2f position) {
		super(position);

	}

	@Override
	public void tick(float dt) {
		super.tick(dt);
	}

	@Override
	public void draw(Canvas canvas, float interpolation) {
		super.draw(canvas, interpolation);
	}

	@Override
	public void collisionWith(GameObject obj) {
		if (obj instanceof Projectile) {

		}

	}

}
