package se.chalmers.spaceshooter.game.object.particle;

import se.chalmers.spaceshooter.game.object.DynamicObject;
import se.chalmers.spaceshooter.game.util.Vector2f;

public abstract class Particle extends DynamicObject {
	protected int lifetime, timeLived;
	protected ParticleID pID;

	public Particle(Vector2f position, Vector2f velocity, ParticleID pID) {
		super(position);
		this.velocity = velocity;
		this.pID = pID;
	}

	@Override
	public void tick(float dt) {
		timeLived++;
		if (timeLived > lifetime)
			live = false;
		move(dt);
	}
}
