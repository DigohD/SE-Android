package com.spaceshooter.game.object.particle.emitter;

import com.spaceshooter.game.object.particle.Engine;
import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;

public class ConstantEmitter extends Emitter {

	protected Vector2f velocity;
	protected boolean isSpread;

	public ConstantEmitter(int particleCount, ParticleID pID,
			Vector2f position, Vector2f velocity) {
		super(particleCount, pID, position, 1);
		this.velocity = velocity;
	}

	@Override
	public void emit() {
		Vector2f finalV = velocity;
		if (isSpread) {
			finalV = velocity
					.add(new Vector2f(0, Randomizer.getFloat(-5f, 5f)));
		}
		for (int i = 0; i < particleCount; i++) {
			switch (pID) {
			case ENGINE:
				new Engine(position, finalV);
				break;
			}
		}
	}

	@Override
	public void tick(float dt) {
		emit();
	}

	public void setIsSpread(boolean spread) {
		isSpread = spread;
	}

}
