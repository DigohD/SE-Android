package se.chalmers.spaceshooter.game.object.particle;

import se.chalmers.spaceshooter.game.GameObjectManager;
import se.chalmers.spaceshooter.game.util.BitmapLoader;
import se.chalmers.spaceshooter.game.util.Randomizer;
import se.chalmers.spaceshooter.game.util.Vector2f;

public class PurpleDot extends Particle {

	public PurpleDot(Vector2f position, Vector2f velocity) {
		super(position, velocity, ParticleID.PURPLE_DOT);

		this.bitmap = BitmapLoader.loadBitmap("particles/DotPurple");

		lifetime = 15 + Randomizer.getInt(0, 25);
		timeLived = 0;

		GameObjectManager.addGameObject(this);
	}

	public PurpleDot(Vector2f position, Vector2f velocity, int lifetime) {
		super(position, new Vector2f(75f, 0f), ParticleID.RED_PLASMA);

		this.bitmap = BitmapLoader.loadBitmap("particles/PlasmaRed");

		this.lifetime = lifetime;
		timeLived = 0;

		GameObjectManager.addGameObject(this);
	}

}
