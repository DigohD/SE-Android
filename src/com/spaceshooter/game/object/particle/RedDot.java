package com.spaceshooter.game.object.particle;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.engine.ProjectileManager;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

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
	
	@Override
	public void tick(float dt){
		timeLived++;
		if(timeLived > lifetime)
			live = false;
		
		distance = velocity.mul(dt);
		position = position.add(distance);
	}
	
	@Override
	public void draw(Canvas canvas,  float interpolation) {
		super.draw(canvas, interpolation);
	}
	
}