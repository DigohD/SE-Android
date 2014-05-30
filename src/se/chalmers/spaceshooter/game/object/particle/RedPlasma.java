package se.chalmers.spaceshooter.object.particle;

import se.chalmers.spaceshooter.engine.GameObjectManager;
import se.chalmers.spaceshooter.util.BitmapHandler;
import se.chalmers.spaceshooter.util.Vector2f;

public class RedPlasma extends Particle{

	public RedPlasma(Vector2f position, Vector2f velocity) {
		super(position, velocity, ParticleID.RED_PLASMA);
		
		this.bitmap = BitmapHandler.loadBitmap("particles/PlasmaRed");
		
		lifetime = 15;
		timeLived = 0;
		
		GameObjectManager.addGameObject(this);
	}
	
	public RedPlasma(Vector2f position, Vector2f velocity, int lifetime) {
		super(position, new Vector2f(75f, 0f), ParticleID.RED_PLASMA);
		
		this.bitmap = BitmapHandler.loadBitmap("particles/PlasmaRed");
		
		this.lifetime = lifetime;
		timeLived = 0;
		
		GameObjectManager.addGameObject(this);
	}
	
}
