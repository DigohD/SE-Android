package se.chalmers.spaceshooter.game.object.weapon;

import se.chalmers.spaceshooter.game.object.projectile.player.YellowPlasma;
import se.chalmers.spaceshooter.game.util.Randomizer;
import se.chalmers.spaceshooter.game.util.SoundPlayer;
import se.chalmers.spaceshooter.game.util.SoundPlayer.SoundID;
import se.chalmers.spaceshooter.game.util.Vector2f;

public class YellowPlasmaGun extends Gun {
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
