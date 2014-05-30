package se.chalmers.spaceshooter.object.loot;

import se.chalmers.spaceshooter.game.object.Collideable;
import se.chalmers.spaceshooter.game.object.DynamicObject;
import se.chalmers.spaceshooter.game.util.Vector2f;
import android.graphics.Rect;

public abstract class Loot extends DynamicObject implements Collideable {

	protected Rect rect;
	protected boolean saved = false;

	public Loot(Vector2f position) {
		super(position);

	}

	@Override
	public void tick(float dt) {
		if (getX() < -width)
			live = false;
		rect.set((int) position.x, (int) position.y, (int) position.x + width,
				(int) position.y + height);
		move(dt);
	}

	public abstract void death();

	public Rect getRect() {
		return rect;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

}
