package se.chalmers.spaceshooter.game.object.loot;

import se.chalmers.spaceshooter.game.GameObjectManager;
import se.chalmers.spaceshooter.game.object.Collideable;
import se.chalmers.spaceshooter.game.object.particle.ParticleID;
import se.chalmers.spaceshooter.game.object.particle.emitter.Emitter;
import se.chalmers.spaceshooter.game.object.particle.emitter.RadialEmitter;
import se.chalmers.spaceshooter.game.object.player.Player;
import se.chalmers.spaceshooter.game.util.BitmapHandler;
import se.chalmers.spaceshooter.game.util.Vector2f;
import android.graphics.Rect;

public class HealthPack extends Loot {

	private int hp;
	private Emitter emitter;

	public HealthPack(Vector2f position, int hp) {
		super(position);

		this.hp = hp;
		this.bitmap = BitmapHandler.loadBitmap("loot/healthpack");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();

		rect = new Rect((int) position.x, (int) position.y, (int) position.x
				+ width, (int) position.y + height);
		emitter = new RadialEmitter(8, ParticleID.RED_DOT, new Vector2f(0, 0),
				new Vector2f(20f, 0f));
		velocity = new Vector2f(-15f, 0);

		GameObjectManager.addGameObject(this);
	}

	@Override
	public void collisionWith(Collideable obj) {
		if (obj instanceof Player) {
			if (live) {
				death();
				live = false;
			}
		}

	}

	@Override
	public void death() {
		Vector2f center = position.add(new Vector2f(width / 2f, height / 2f));
		emitter.getPosition().set(center.x, center.y);
		emitter.init();
		// SoundPlayer.playSound(SoundID.exp_1);
	}

	public int getHp() {
		return hp;
	}

}
