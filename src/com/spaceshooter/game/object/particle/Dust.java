package com.spaceshooter.game.object.particle;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.engine.ProjectileManager;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class Dust extends Particle{

	public Dust(Vector2f position, Vector2f velocity) {
		super(position, velocity, ParticleID.DUST);
		
		int rnd = Randomizer.getInt2(0, 2);
		this.bitmap = BitmapHandler.loadBitmap("particles/dust" + rnd);
		
		lifetime = 5 + Randomizer.getInt(0, 40);
		timeLived = 0;
		
		GameObjectManager.addGameObject(this);
	}
	
	public Dust(Vector2f position, Vector2f velocity, int lifetime) {
		super(position, new Vector2f(75f, 0f), ParticleID.DUST);
		
		int rnd = Randomizer.getInt2(0, 2);
		this.bitmap = BitmapHandler.loadBitmap("particles/dust" + rnd);
		
		this.lifetime = lifetime;
		timeLived = 0;
		
		GameObjectManager.addGameObject(this);
	}
	
}
