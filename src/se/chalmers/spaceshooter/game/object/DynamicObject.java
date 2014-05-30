package se.chalmers.spaceshooter.game.object;

import se.chalmers.spaceshooter.game.util.Vector2f;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class DynamicObject extends GameObject implements Tickable,
		Drawable {

	protected float speedX, speedY;

	protected Bitmap bitmap;

	protected Vector2f velocity;
	protected Vector2f distance;

	protected Vector2f currentPosition;
	protected Vector2f nextPosition;
	protected Vector2f interpolatedPosition;

	public DynamicObject(Vector2f position) {
		super(position);
		distance = new Vector2f(0, 0);
		currentPosition = position;
		nextPosition = new Vector2f(0, 0);
		interpolatedPosition = new Vector2f(0, 0);
	}

	@Override
	public void draw(Canvas canvas, float interpolation) {
		interpolate(interpolation);
		canvas.drawBitmap(bitmap, interpolatedPosition.x,
				interpolatedPosition.y, null);
	}

	@Override
	public Bitmap getBitmap() {
		return bitmap;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public void move(float dt) {
		distance = velocity.mul(dt);
		position = position.add(distance);
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	@Override
	public void tick(float dt) {

	}

	protected float approach(float target, float current, float dt) {
		float diff = target - current;
		if (diff > dt)
			return current + dt;
		if (diff < -dt)
			return current - dt;
		return target;
	}

	protected void interpolate(float interpolation) {
		currentPosition = position;
		nextPosition = currentPosition.add(distance);
		// predict where the new position to draw will be
		interpolatedPosition = currentPosition.mul(interpolation).add(
				nextPosition.mul((1.0f - interpolation)));
	}

}
