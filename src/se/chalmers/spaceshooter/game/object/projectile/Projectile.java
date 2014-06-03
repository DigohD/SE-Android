package se.chalmers.spaceshooter.game.object.projectile;

import se.chalmers.spaceshooter.game.object.Collideable;
import se.chalmers.spaceshooter.game.object.DynamicObject;
import se.chalmers.spaceshooter.game.object.enemy.Enemy;
import se.chalmers.spaceshooter.game.object.player.Player;
import se.chalmers.spaceshooter.game.util.Vector2f;
import se.chalmers.spaceshooter.game.view.GameView;
import android.graphics.Rect;

public abstract class Projectile extends DynamicObject implements Collideable {

	public enum Type {
		PLAYER, ENEMY;
	}

	protected float damage;

	protected Rect rect;

	protected Type type;

	public Projectile(Vector2f position, Vector2f velocity) {
		super(position);
		this.velocity = velocity;
	}
	
	@Override
	public void tick(float dt) {
		if (isOutOfBound())
			live = false;

		rect.set((int) position.x, (int) position.y, (int) position.x + width,
				(int) position.y + height);
		move(dt);
	}
	
	public boolean isOutOfBound() {
		return (getX() >= GameView.WIDTH || getX() <= -width
				|| getY() >= GameView.HEIGHT || getY() <= -height);
	}

	@Override
	public void collisionWith(Collideable obj) {
		if (obj instanceof Player) {
			if (live) {
				death();
				live = false;
			}
		}

		if (obj instanceof Enemy) {
			if (live) {
				death();
				live = false;
			}
		}
	}

	public abstract void death();

	public float getDamage() {
		return damage;
	}

	@Override
	public Rect getRect() {
		return rect;
	}

	public Type getType() {
		return type;
	}

}
