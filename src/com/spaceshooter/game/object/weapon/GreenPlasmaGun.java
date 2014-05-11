package com.spaceshooter.game.object.weapon;

import com.spaceshooter.game.object.projectile.player.GreenPlasma;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.SoundPlayer;
import com.spaceshooter.game.util.Vector2f;

public class GreenPlasmaGun extends Gun{

	public GreenPlasmaGun(Vector2f gunPos) {
		super(gunPos);
	}

	@Override
	public void fire() {
		Vector2f finalV = pVelocity.rotate(Randomizer.getFloat(-spread, spread));
		new GreenPlasma(gunPos, finalV);
		SoundPlayer.playSound(3);
	}

	@Override
	public void init() {
		pVelocity = new Vector2f(65f, 0f);
		reload = 50;
		spread = 2f;
	}

}
