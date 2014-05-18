package com.spaceshooter.game.object.particle.emitter;

import com.spaceshooter.game.object.particle.Dust;
import com.spaceshooter.game.object.particle.GreenBall;
import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.PurpleDot;
import com.spaceshooter.game.object.particle.RedDot;
import com.spaceshooter.game.object.particle.RedPlasma;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;

public class RadialEmitter extends Emitter{

	protected Vector2f particleVelocity;
	
	public RadialEmitter(int particleCount, ParticleID pID, Vector2f position, 
			Vector2f particleVelocity) {
		super(particleCount, pID, position, 4);
		this.particleVelocity = particleVelocity;
	}

	@Override
	public void emit() {
		for(int i = 0; i < (particleCount / 4) + 1; i++){
			Vector2f finalV = particleVelocity.rotate(Randomizer.getFloat(0, 359));
			switch(pID){
				case GreenBall:
					new GreenBall(position, finalV);
					break;
				case RED_PLASMA:
					new RedPlasma(position, finalV);
					break;
				case RED_DOT:
					new RedDot(position, finalV);
					break;
				case PURPLE_DOT:
					new PurpleDot(position, finalV);
					break;
				case DUST:
					new Dust(position, finalV.mul(Randomizer.getFloat(1f, 2f)));
					break;
			default:
				break;
			}
		}
	}

}
