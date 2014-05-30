package se.chalmers.spaceshooter.object.particle.emitter;

import se.chalmers.spaceshooter.game.GameObjectManager;
import se.chalmers.spaceshooter.game.object.particle.GreenBall;
import se.chalmers.spaceshooter.game.object.particle.LBlueBall;
import se.chalmers.spaceshooter.game.object.particle.ParticleID;
import se.chalmers.spaceshooter.game.object.particle.PurpleDot;
import se.chalmers.spaceshooter.game.object.particle.RedDot;
import se.chalmers.spaceshooter.game.util.Randomizer;
import se.chalmers.spaceshooter.game.util.Vector2f;

public class ImpactEmitter extends Emitter {

	protected Vector2f impactVelocity;

	public ImpactEmitter(int particleCount, ParticleID pID, Vector2f position,
			Vector2f impactVelocity) {
		super(particleCount, pID, position, 1);
		this.impactVelocity = impactVelocity;
		GameObjectManager.addGameObject(this);
	}

	@Override
	public void emit() {
		float yVel;
		float xVel;
		for (int i = 0; i < particleCount; i++) {
			switch (pID) {
			case RED_PLASMA:
				yVel = impactVelocity.y + Randomizer.getFloat(-10f, 10f);
				xVel = impactVelocity.x * 0.75f;
				new RedPlasma(position, new Vector2f(xVel, yVel));
				break;
			case RED_DOT:
				yVel = impactVelocity.y + Randomizer.getFloat(-20f, 20f);
				xVel = impactVelocity.x * 0.5f;
				new RedDot(position, new Vector2f(xVel, yVel));
				break;
			case PURPLE_DOT:
				yVel = impactVelocity.y + Randomizer.getFloat(-20f, 20f);
				xVel = impactVelocity.x * 0.5f;
				new PurpleDot(position, new Vector2f(xVel, yVel));
				break;
			case LBlueBall:
				yVel = impactVelocity.y + Randomizer.getFloat(-20f, 20f);
				xVel = impactVelocity.x * 0.5f;
				new LBlueBall(position, new Vector2f(xVel, yVel));
				break;
			case GreenBall:
				yVel = impactVelocity.y + Randomizer.getFloat(-20f, 20f);
				xVel = impactVelocity.x * 0.5f;
				new GreenBall(position, new Vector2f(xVel, yVel));
				break;
			case YellowPlasma:
				yVel = impactVelocity.y + Randomizer.getFloat(-40f, 40f);
				xVel = impactVelocity.x * 0.5f;
				new YellowPlasma(position, new Vector2f(xVel, yVel));
				break;
			}
		}
	}

}
