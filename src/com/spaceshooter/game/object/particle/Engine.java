package com.spaceshooter.game.object.particle;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;

public class Engine extends Particle{

	public Engine(Vector2f position, Vector2f velocity) {
		super(position, velocity, ParticleID.ENGINE);
		
		this.bitmap = BitmapHandler.loadBitmap("particles/Engine");
		
		lifetime = 30 + Randomizer.getInt(0, 20);
		timeLived = 0;
		
		GameObjectManager.addGameObject(this);
	}
	
	public Engine(Vector2f position, Vector2f velocity, int lifetime) {
		super(position, new Vector2f(75f, 0f), ParticleID.ENGINE);
		
		this.bitmap = BitmapHandler.loadBitmap("particles/Engine");
		
		this.lifetime = lifetime;
		timeLived = 0;
		
		GameObjectManager.addGameObject(this);
	}
	
}
