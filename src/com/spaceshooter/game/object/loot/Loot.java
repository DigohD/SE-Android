package com.spaceshooter.game.object.loot;

import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.util.Vector2f;

public abstract class Loot extends DynamicObject {

	protected Vector2f targetVelocity;

	public Loot(Vector2f position) {
		super(position);

	}

	@Override
	public void tick(float dt) {
		if (getX() < -width)
			live = false;
		rect.set((int) position.x, (int) position.y, (int) position.x + width,
				(int) position.y + height);
		velocity.x = approach(targetVelocity.x, velocity.x, dt * 10f);
		velocity.y = approach(targetVelocity.y, velocity.y, dt * 10f);
		move(dt);
	}

	public abstract void death();

}
