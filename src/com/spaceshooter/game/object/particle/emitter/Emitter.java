package com.spaceshooter.game.object.particle.emitter;


import android.graphics.Canvas;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.util.Vector2f;

public abstract class Emitter extends GameObject{

	protected int particleCount, lifetime, timeLived;
	protected ParticleID pID;
	
	public Emitter(int particleCount, ParticleID pID, Vector2f position, int lifetime){
		super(position);
		this.particleCount = particleCount;
		this.pID = pID;
		this.lifetime = lifetime;
		timeLived = 0;
		
		GameObjectManager.addGameObject(this);
	}
	
	@Override
	public void tick(float dt) {
		timeLived++;
		if(timeLived > lifetime)
			this.closeEmitter();
		emit();
	}
	
	@Override
	public void draw(Canvas canvas, float interpolation) {
		return;
	}
	
	public abstract void emit();
	
	public void closeEmitter(){
		live = false;
	}
}
