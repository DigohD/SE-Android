package se.chalmers.spaceshooter.game.object.particle;

import se.chalmers.spaceshooter.game.GameObjectManager;
import se.chalmers.spaceshooter.game.util.BitmapLoader;
import se.chalmers.spaceshooter.game.util.Randomizer;
import se.chalmers.spaceshooter.game.util.Vector2f;

public class Engine extends Particle {
	public Engine(Vector2f position, Vector2f velocity) {
		super(position, velocity, ParticleID.ENGINE);
		this.bitmap = BitmapLoader.loadBitmap("particles/Engine");
		lifetime = 30 + Randomizer.getInt(0, 20);
		timeLived = 0;
		GameObjectManager.addGameObject(this);
	}

	public Engine(Vector2f position, Vector2f velocity, int lifetime) {
		super(position, new Vector2f(75f, 0f), ParticleID.ENGINE);
		this.bitmap = BitmapLoader.loadBitmap("particles/Engine");
		this.lifetime = lifetime;
		timeLived = 0;
		GameObjectManager.addGameObject(this);
	}
}
