package com.spaceshooter.game.object.weapon;

import com.spaceshooter.game.object.projectile.BluePlasma;
import com.spaceshooter.game.object.projectile.RedPlasma;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;

public class BluePlasmaGun extends Gun{

	public BluePlasmaGun(Vector2f gunPos) {
		super(gunPos);
	}

	@Override
	public void fire() {
		Vector2f finalV = pVelocity.rotate(Randomizer.getFloat(-spread, spread));
		new BluePlasma(gunPos, finalV);
	}

	@Override
	public void init() {
		pVelocity = new Vector2f(30f, 0f);
		reload = 10;
		spread = 15f;
	}

}
