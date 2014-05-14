package com.spaceshooter.game.object.weapon;

import com.spaceshooter.game.object.projectile.player.RedPlasma;
import com.spaceshooter.game.object.projectile.player.YellowPlasma;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.SoundPlayer;
import com.spaceshooter.game.util.SoundPlayer.SoundID;
import com.spaceshooter.game.util.Vector2f;

public class YellowPlasmaGun extends Gun{

	public YellowPlasmaGun(Vector2f gunPos) {
		super(gunPos);
	}

	@Override
	public void fire() {
		Vector2f finalV = pVelocity.rotate(Randomizer.getFloat(-spread, spread));
		new YellowPlasma(gunPos, finalV);
		SoundPlayer.playSound(SoundID.fire_YellowPlasma);
	}

	@Override
	public void init() {
		pVelocity = new Vector2f(40f, 0f);
		reload = 8;
		spread = 4f;
	}

}
