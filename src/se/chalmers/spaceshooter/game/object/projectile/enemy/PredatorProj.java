package se.chalmers.spaceshooter.game.object.projectile.enemy;

import se.chalmers.spaceshooter.game.GameObjectManager;
import se.chalmers.spaceshooter.game.object.particle.ParticleID;
import se.chalmers.spaceshooter.game.object.particle.emitter.ImpactEmitter;
import se.chalmers.spaceshooter.game.object.projectile.Projectile;
import se.chalmers.spaceshooter.game.util.BitmapHandler;
import se.chalmers.spaceshooter.game.util.Vector2f;
import android.graphics.Rect;

public class PredatorProj extends Projectile {

	public PredatorProj(Vector2f position) {
		super(position, new Vector2f(-40f, 0f));
		damage = 2f;
		this.bitmap = BitmapHandler.loadBitmap("projectiles/PlasmaPurple");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();

		rect = new Rect((int) position.x, (int) position.y, (int) position.x
				+ width, (int) position.y + height);

		type = Type.ENEMY;

		GameObjectManager.addGameObject(this);
	}

	// used for unit testing
	public PredatorProj(Vector2f position, int width, int height) {
		super(position, new Vector2f(80f, 0f));
		damage = 20f;

		this.width = width;
		this.height = height;

		rect = new Rect((int) position.x, (int) position.y, (int) position.x
				+ width, (int) position.y + height);
	}

	@Override
	public void death() {
		new ImpactEmitter(3, ParticleID.PURPLE_DOT, position, velocity);
	}

}
