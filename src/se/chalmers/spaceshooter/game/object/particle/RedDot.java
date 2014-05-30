package se.chalmers.spaceshooter.object.particle;

import se.chalmers.spaceshooter.engine.GameObjectManager;
import se.chalmers.spaceshooter.util.BitmapHandler;
import se.chalmers.spaceshooter.util.Randomizer;
import se.chalmers.spaceshooter.util.Vector2f;

public class RedDot extends Particle{

	public RedDot(Vector2f position, Vector2f velocity) {
		super(position, velocity, ParticleID.RED_DOT);
		
		this.bitmap = BitmapHandler.loadBitmap("particles/DotRed");
		
		lifetime = 15 + Randomizer.getInt(0, 25);
		timeLived = 0;
		
		GameObjectManager.addGameObject(this);
	}
	
	public RedDot(Vector2f position, Vector2f velocity, int lifetime) {
		super(position, new Vector2f(75f, 0f), ParticleID.RED_DOT);
		
		this.bitmap = BitmapHandler.loadBitmap("particles/DotRed");
		
		this.lifetime = lifetime;
		timeLived = 0;
		
		GameObjectManager.addGameObject(this);
	}
	
}
