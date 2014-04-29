package com.spaceshooter.game.object.particle.emitter;

import android.graphics.Canvas;

import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.RedPlasma;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;

public class ImpactEmitter extends Emitter{

	protected Vector2f impactVelocity;
	
	public ImpactEmitter(int particleCount, ParticleID pID, Vector2f position, 
			Vector2f impactVelocity) {
		super(particleCount, pID, position, 1);
		this.impactVelocity = impactVelocity;
	}

	@Override
	public void emit() {
		for(int i = 0; i < particleCount; i++){
			switch(pID){
				case RED_PLASMA:
					float yVel = impactVelocity.y + Randomizer.getFloat(-10f, 10f);
					float xVel = impactVelocity.x * 0.75f;
					new RedPlasma(position, new Vector2f(xVel, yVel));
					break;
			}
		}
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

}
