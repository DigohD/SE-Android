package se.chalmers.spaceshooter.game.object.enemy;

import se.chalmers.spaceshooter.game.util.BitmapHandler;
import se.chalmers.spaceshooter.game.util.Vector2f;
import se.chalmers.spaceshooter.game.view.GameView;
import android.graphics.Rect;

public class Locust extends Enemy {

	int timer = 0;
	int steps = 20;
	private int reload;

	public Locust() {
		this(new Vector2f(GameView.WIDTH + 40, 0));
	}

	public Locust(Vector2f position) {
		super(position);
		this.bitmap = BitmapHandler.loadBitmap("enemies/locust");

		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		rect = new Rect((int) position.x, (int) position.y, (int) position.x
				+ width, (int) position.y + height);

		speedX = -15f;
		speedY = 0;

		velocity = new Vector2f(speedX, speedY);

		hp = 50f;
		maxHp = 50f;
	}

	@Override
	public void tick(float dt) {
		timer++;
		super.tick(dt);
		move(dt);
	}

	@Override
	public void death() {

	}

}
