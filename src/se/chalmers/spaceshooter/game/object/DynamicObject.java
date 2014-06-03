package se.chalmers.spaceshooter.game.object;

import se.chalmers.spaceshooter.game.util.Vector2f;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Most of our GameObject are DYnamicObjects which means thath they should
 * be updated and drawn every frame.
 * @author Anders
 *
 */
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
	
	/**
	 * Method used to simulate acceleration. You set a target
	 * velocity and then you pass in the current velocity and then the
	 * method will increment the current velocity every frame with dt until it reaches
	 * the target.
	 * @param target the target velocity
	 * @param current the current velocity
	 * @param dt the timestep
	 * @return
	 */
	protected float approach(float target, float current, float dt) {
		float diff = target - current;
		if (diff > dt)
			return current + dt;
		if (diff < -dt)
			return current - dt;
		return target;
	}

	/**
	 * The prediction method used for calculating the interpolated state which
	 * will be drawn.
	 * @param interpolation the interpolation factor which is passed down from the gameloop
	 */
	protected void interpolate(float interpolation) {
		currentPosition = position;
		nextPosition = currentPosition.add(distance);
		// predict where the new position to draw will be
		interpolatedPosition = currentPosition.mul(interpolation).add(
				nextPosition.mul((1.0f - interpolation)));
	}
	
	/**
	 * A basic movement method following the fundamental formula: s = v*t
	 * @param dt the timestep
	 */
	protected void move(float dt) {
		distance = velocity.mul(dt);
		position = position.add(distance);
	}

	/**
	 * Draws the dynamicobject at the interpolated position.
	 */
	@Override
	public void draw(Canvas canvas, float interpolation) {
		interpolate(interpolation);
		canvas.drawBitmap(bitmap, interpolatedPosition.x,
				interpolatedPosition.y, null);
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	@Override
	public Bitmap getBitmap() {
		return bitmap;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

}
