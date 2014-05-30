package se.chalmers.spaceshooter.object.weapon;

import se.chalmers.spaceshooter.object.projectile.player.RedPlasma;
import se.chalmers.spaceshooter.object.projectile.player.YellowPlasma;
import se.chalmers.spaceshooter.util.Randomizer;
import se.chalmers.spaceshooter.util.SoundPlayer;
import se.chalmers.spaceshooter.util.Vector2f;
import se.chalmers.spaceshooter.util.SoundPlayer.SoundID;

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
