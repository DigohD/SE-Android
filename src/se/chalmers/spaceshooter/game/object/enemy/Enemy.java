package se.chalmers.spaceshooter.game.object.enemy;

import se.chalmers.spaceshooter.game.GameObjectManager;
import se.chalmers.spaceshooter.game.object.Collideable;
import se.chalmers.spaceshooter.game.object.DynamicObject;
import se.chalmers.spaceshooter.game.object.loot.HealthPack;
import se.chalmers.spaceshooter.game.object.loot.SlowTimePack;
import se.chalmers.spaceshooter.game.object.projectile.Projectile;
import se.chalmers.spaceshooter.game.util.Randomizer;
import se.chalmers.spaceshooter.game.util.Vector2f;
import se.chalmers.spaceshooter.game.view.GameView;
import android.graphics.Rect;

public abstract class Enemy extends DynamicObject implements Collideable {
	protected Rect rect;
	protected float hp, maxHp, enemyPoints;

	public Enemy(Vector2f position) {
		super(position);
	}

	public void init() {
		position.y += GameObjectManager.bg.getPosition().y;
		GameObjectManager.addGameObject(this);
	}

	@Override
	public void tick(float dt) {
		if (getX() < -width)
			live = false;
		rect.set((int) position.x, (int) position.y, (int) position.x + width, (int) position.y + height);
	}

	public int calculatePlayerScore(float points, int level, float combo) {
		if (combo == 0)
			return (int) (points * Math.pow(1.2f, level) * 1.0f);
		else
			return (int) (points * Math.pow(1.2f, level) * combo);
	}

	@Override
	public void collisionWith(Collideable obj) {
		if (obj instanceof Projectile) {
			Projectile p = (Projectile) obj;
			hp = hp - p.getDamage();
			if (hp <= 0) {
				GameObjectManager.getPlayer().incEnemyKillCount(1);
				int score = calculatePlayerScore(enemyPoints, GameView.getLevelID(), GameObjectManager.getPlayer()
						.getCombo());
				GameObjectManager.getPlayer().incScore(score);
				death();
				live = false;
				int rn = Randomizer.getInt(0, 10);
				int rn2 = Randomizer.getInt(0, 14);
				boolean lootDropped = false;
				if (rn == 2) {
					new HealthPack(position, 10);
					lootDropped = true;
				}
				if (rn2 == 2 && !lootDropped) {
					new SlowTimePack(position);
				}
			}
		}
	}

	public abstract void death();

	@Override
	public Rect getRect() {
		return rect;
	}
}
