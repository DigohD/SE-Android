package se.chalmers.spaceshooter.game.object.weapon;

import se.chalmers.spaceshooter.game.object.projectile.player.RedPlasma;
import se.chalmers.spaceshooter.game.util.Randomizer;
import se.chalmers.spaceshooter.game.util.SoundPlayer;
import se.chalmers.spaceshooter.game.util.SoundPlayer.SoundID;
import se.chalmers.spaceshooter.game.util.Vector2f;

public class RedPlasmaGun extends Gun {
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
