package se.chalmers.spaceshooter.object.particle;

import se.chalmers.spaceshooter.engine.GameObjectManager;
import se.chalmers.spaceshooter.util.BitmapHandler;
import se.chalmers.spaceshooter.util.Vector2f;

public class YellowPlasma extends Particle{

	public YellowPlasma(Vector2f position, Vector2f velocity) {
		super(position, velocity, ParticleID.YellowPlasma);
		
		this.bitmap = BitmapHandler.loadBitmap("particles/PlasmaYellow");
		
		lifetime = 15;
		timeLived = 0;
		
		GameObjectManager.addGameObject(this);
	}
	
	public YellowPlasma(Vector2f position, Vector2f velocity, int lifetime) {
		super(position, new Vector2f(75f, 0f), ParticleID.YellowPlasma);
		
		this.bitmap = BitmapHandler.loadBitmap("particles/PlasmaYellow");
		
		this.lifetime = lifetime;
		timeLived = 0;
		
		GameObjectManager.addGameObject(this);
	}
	
}
