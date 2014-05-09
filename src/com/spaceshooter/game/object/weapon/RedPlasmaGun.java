package com.spaceshooter.game.object.weapon;

import com.spaceshooter.game.object.projectile.player.RedPlasma;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;

public class RedPlasmaGun extends Gun{

	public RedPlasmaGun(Vector2f gunPos) {
		super(gunPos);
	}

	@Override
	public void fire() {
		Vector2f finalV = pVelocity.rotate(Randomizer.getFloat(-spread, spread));
		new RedPlasma(gunPos, finalV);
	}

	@Override
	public void init() {
		pVelocity = new Vector2f(50f, 0f);
		reload = 25;
		spread = 5f;
	}

}
