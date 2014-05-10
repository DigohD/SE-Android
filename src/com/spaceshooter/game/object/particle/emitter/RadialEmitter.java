package com.spaceshooter.game.object.particle.emitter;

import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.PurpleDot;
import com.spaceshooter.game.object.particle.RedPlasma;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;

public class RadialEmitter extends Emitter{

	protected Vector2f particleVelocity;
	
	public RadialEmitter(int particleCount, ParticleID pID, Vector2f position, 
			Vector2f particleVelocity) {
		super(particleCount, pID, position, 1);
		this.particleVelocity = particleVelocity;
	}

	@Override
	public void emit() {
		for(int i = 0; i < particleCount; i++){
			Vector2f finalV = particleVelocity.rotate(Randomizer.getFloat(0, 359));
			switch(pID){
				case RED_PLASMA:
					new RedPlasma(position, finalV);
					break;
				case PURPLE_DOT:
					new PurpleDot(position, finalV);
					break;
			}
		}
	}

}
