package se.chalmers.spaceshooter.object.weapon;

import se.chalmers.spaceshooter.object.projectile.player.BluePlasma;
import se.chalmers.spaceshooter.util.Randomizer;
import se.chalmers.spaceshooter.util.SoundPlayer;
import se.chalmers.spaceshooter.util.Vector2f;
import se.chalmers.spaceshooter.util.SoundPlayer.SoundID;

public class BluePlasmaGun extends Gun{

	public BluePlasmaGun(Vector2f gunPos) {
		super(gunPos);
	}

	@Override
	public void fire() {
		Vector2f finalV = pVelocity.rotate(Randomizer.getFloat(-spread, spread));
		new BluePlasma(gunPos, finalV);
		SoundPlayer.playSound(SoundID.fire_BluePlasma);
	}

	@Override
	public void init() {
		pVelocity = new Vector2f(30f, 0f);
		reload = 10;
		spread = 15f;
	}

}
