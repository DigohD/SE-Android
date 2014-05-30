package se.chalmers.spaceshooter.object.weapon;

import se.chalmers.spaceshooter.object.projectile.player.RedPlasma;
import se.chalmers.spaceshooter.util.Randomizer;
import se.chalmers.spaceshooter.util.SoundPlayer;
import se.chalmers.spaceshooter.util.Vector2f;
import se.chalmers.spaceshooter.util.SoundPlayer.SoundID;

public class RedPlasmaGun extends Gun{

	public RedPlasmaGun(Vector2f gunPos) {
		super(gunPos);
	}

	@Override
	public void fire() {
		Vector2f finalV = pVelocity.rotate(Randomizer.getFloat(-spread, spread));
		new RedPlasma(gunPos, finalV);
		SoundPlayer.playSound(SoundID.fire_RedPlasma);
	}

	@Override
	public void init() {
		pVelocity = new Vector2f(50f, 0f);
		reload = 25;
		spread = 5f;
	}

}
