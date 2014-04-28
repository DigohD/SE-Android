package com.spaceshooter.game.object;

import android.graphics.Canvas;

import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public abstract class DynamicObject extends GameObject {

	protected float speedX, speedY;

	protected Vector2f distance = new Vector2f(0, 0);
	protected Vector2f currentPosition = position;
	protected Vector2f nextPosition = new Vector2f(0, 0);
	protected Vector2f interpolatedPosition = new Vector2f(0, 0);

	protected Vector2f velocity;

	public DynamicObject(Vector2f position) {
		super(position);
	}

	protected void interpolate(float interpolation) {
		currentPosition = position;
		nextPosition = currentPosition.add(distance);
		// predict where the new position will be
		interpolatedPosition = currentPosition.mul(interpolation).add(
				nextPosition.mul((1.0f - interpolation)));
	}

	// public abstract void move();

	@Override
	public void tick(float dt) {
		if(this.getX() < -width)
			live = false;
		rect.set((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
	}

	@Override
	public void draw(Canvas canvas, float interpolation) {
		interpolate(interpolation);
		canvas.drawBitmap(bitmap, interpolatedPosition.x,
				interpolatedPosition.y, null);
	}

}
