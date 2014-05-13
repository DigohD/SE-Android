package com.spaceshooter.game.object.particle;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.engine.ProjectileManager;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

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