package com.spaceshooter.game.object.particle;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;

public class LBlueBall extends Particle{

	public LBlueBall(Vector2f position, Vector2f velocity) {
		super(position, velocity, ParticleID.LBlueBall);
		
		this.bitmap = BitmapHandler.loadBitmap("particles/LBlueBall");
		
		lifetime = 15 + Randomizer.getInt(0, 25);
		timeLived = 0;
		
		GameObjectManager.addGameObject(this);
	}
	
	public LBlueBall(Vector2f position, Vector2f velocity, int lifetime) {
		super(position, new Vector2f(75f, 0f), ParticleID.LBlueBall);
		
		this.bitmap = BitmapHandler.loadBitmap("particles/LBlueBall");
		
		this.lifetime = lifetime;
		timeLived = 0;
		
		GameObjectManager.addGameObject(this);
	}
	
}
