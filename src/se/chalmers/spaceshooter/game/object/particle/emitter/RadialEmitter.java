package se.chalmers.spaceshooter.object.particle.emitter;

import se.chalmers.spaceshooter.object.particle.Dust;
import se.chalmers.spaceshooter.object.particle.GreenBall;
import se.chalmers.spaceshooter.object.particle.ParticleID;
import se.chalmers.spaceshooter.object.particle.PurpleDot;
import se.chalmers.spaceshooter.object.particle.RedDot;
import se.chalmers.spaceshooter.object.particle.RedPlasma;
import se.chalmers.spaceshooter.util.Randomizer;
import se.chalmers.spaceshooter.util.Vector2f;

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
