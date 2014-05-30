package se.chalmers.spaceshooter.game.object.background;

import se.chalmers.spaceshooter.game.GameObjectManager;
import se.chalmers.spaceshooter.game.object.DynamicObject;
import se.chalmers.spaceshooter.game.util.BitmapHandler;
import se.chalmers.spaceshooter.game.util.Vector2f;
import android.graphics.Canvas;

public class BackGround extends DynamicObject {

	private float xOffset1 = 0;
	private float xOffset2 = 0;

	private float yOffset1 = 0;
	private float yOffset2 = 0;

	public boolean yScroll = false;

	public Vector2f targetPosition;

	public BackGround() {
		super(new Vector2f(0, 0));

		this.bitmap = BitmapHandler.loadBitmap("bg/bg");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();

		velocity = new Vector2f(0, 0f);
		targetPosition = new Vector2f(0f, 0f);

		GameObjectManager.addGameObject(this);
	}

	public Vector2f diff = new Vector2f(0, 0);

	public void scrollY(float dt, Vector2f v) {
		if (yScroll) {
			velocity.y = approach(-v.y, velocity.y, dt * 5);
			targetPosition.y = targetPosition.y + velocity.y;

			diff = targetPosition.sub(position).div(15);
			distance = diff;
			position = position.add(diff);
		}
	}

	private void scrollX(float dt) {
		distance.x = velocity.x * dt;
		position.x = position.x + distance.x;
	}

	@Override
	public void tick(float dt) {
		scrollX(dt);
	}

	@Override
	public void draw(Canvas canvas, float interpolation) {
		interpolate(interpolation);

		if (interpolatedPosition.x + xOffset1 < 0)
			xOffset1 += 800;
		if (interpolatedPosition.x + xOffset2 < -800)
			xOffset2 += 800;

		if (interpolatedPosition.y + yOffset1 < 0)
			yOffset1 += 480;
		if (interpolatedPosition.y + yOffset2 < -480)
			yOffset2 += 480;

		canvas.drawBitmap(bitmap, interpolatedPosition.x,
				interpolatedPosition.y + yOffset2, null);
		canvas.drawBitmap(bitmap, interpolatedPosition.x,
				interpolatedPosition.y + yOffset1, null);

		// canvas.drawBitmap(bitmap, interpolatedPosition.x + xOffset2,
		// interpolatedPosition.y, null);
		// canvas.drawBitmap(bitmap, interpolatedPosition.x + xOffset1,
		// interpolatedPosition.y, null);

	}

}
