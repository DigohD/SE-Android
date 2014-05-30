package se.chalmers.spaceshooter.object.weapon;

import se.chalmers.spaceshooter.object.projectile.player.GreenPlasma;
import se.chalmers.spaceshooter.util.Randomizer;
import se.chalmers.spaceshooter.util.SoundPlayer;
import se.chalmers.spaceshooter.util.Vector2f;
import se.chalmers.spaceshooter.util.SoundPlayer.SoundID;

public class GreenPlasmaGun extends Gun{

	public GreenPlasmaGun(Vector2f gunPos) {
		super(gunPos);
	}

	@Override
	public void fire() {
		Vector2f finalV = pVelocity.rotate(Randomizer.getFloat(-spread, spread));
		new GreenPlasma(gunPos, finalV);
		SoundPlayer.playSound(SoundID.fire_GreenPlasma);
	}

	@Override
	public void init() {
		pVelocity = new Vector2f(65f, 0f);
		reload = 50;
		spread = 2f;
	}

}
