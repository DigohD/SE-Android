package se.chalmers.spaceshooter.game.object.enemy;

import se.chalmers.spaceshooter.game.GameObjectManager;
import se.chalmers.spaceshooter.game.object.particle.ParticleID;
import se.chalmers.spaceshooter.game.object.particle.emitter.Emitter;
import se.chalmers.spaceshooter.game.object.particle.emitter.RadialEmitter;
import se.chalmers.spaceshooter.game.object.projectile.enemy.MantisProj;
import se.chalmers.spaceshooter.game.util.BitmapHandler;
import se.chalmers.spaceshooter.game.util.SoundPlayer;
import se.chalmers.spaceshooter.game.util.SoundPlayer.SoundID;
import se.chalmers.spaceshooter.game.util.Vector2f;
import se.chalmers.spaceshooter.game.view.GameView;
import android.graphics.Rect;

public class Mantis extends Enemy {

	private int reload;
	private Vector2f targetVelocity;

	private boolean directionChoosed = false;
	private boolean fire = false;
	private boolean top, bottom;

	private Emitter emitter;

	public Mantis() {
		this(new Vector2f(GameView.WIDTH + 40, 0));
	}

	public Mantis(Vector2f position) {
		super(position);
		this.bitmap = BitmapHandler.loadBitmap("enemies/mantis");

		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();

		rect = new Rect((int) position.x, (int) position.y, (int) position.x
				+ width, (int) position.y + height);
		emitter = new RadialEmitter(8, ParticleID.GreenBall,
				new Vector2f(0, 0), new Vector2f(20f, 0f));
		speedX = -25f;
		speedY = 0;

		velocity = new Vector2f(speedX, speedY);
		targetVelocity = new Vector2f(speedX, speedY);
		hp = 20f;
		maxHp = 20f;
		enemyPoints = 10;
	}

	// for unit testing
	public Mantis(Vector2f position, int width, int height) {
		super(position);

		this.width = width;
		this.height = height;

		rect = new Rect((int) position.x, (int) position.y, (int) position.x
				+ width, (int) position.y + height);
		emitter = new RadialEmitter(8, ParticleID.GreenBall,
				new Vector2f(0, 0), new Vector2f(20f, 0f));
		speedX = -25f;
		speedY = 0;

		velocity = new Vector2f(speedX, speedY);
		targetVelocity = new Vector2f(speedX, speedY);
		hp = 20f;
		maxHp = 20f;
		enemyPoints = 10;
	}

	@Override
	public void tick(float dt) {
		super.tick(dt);

		if (position.y > GameView.HEIGHT / 2
				&& position.x < GameView.WIDTH - 30 && !directionChoosed) {
			targetVelocity.y = -50f;
			directionChoosed = true;
			top = true;
		}

		if (position.y < GameView.HEIGHT / 2
				&& position.x < GameView.WIDTH - 30 && !directionChoosed) {
			targetVelocity.y = 50f;
			directionChoosed = true;
			bottom = true;
		}

		if (position.x <= GameView.WIDTH / 2 + 130 && !fire) {
			targetVelocity.y = 0;
			targetVelocity.x = -15f;
			fire = true;
		}

		if (position.x <= GameView.WIDTH / 2) {
			fire = false;
			if (top) {
				targetVelocity.y = 50f;
				targetVelocity.x = -20f;
			}
			if (bottom) {
				targetVelocity.y = -50f;
				targetVelocity.x = -20f;
			}
		}

		if (fire) {
			reload++;
			if (GameObjectManager.isSlowTime()) {
				if (reload > 20 * (1 + GameObjectManager.slowtime)) {
					reload = 0;
					Vector2f v = new Vector2f(position.x, position.y + 2);
					new MantisProj(v);
				}
			} else {
				if (reload > 20) {
					reload = 0;
					Vector2f v = new Vector2f(position.x, position.y + 2);
					new MantisProj(v);
				}
			}

		}

		velocity.x = approach(targetVelocity.x, velocity.x, dt);
		velocity.y = approach(targetVelocity.y, velocity.y, dt);

		move(dt);
	}

	@Override
	public void death() {
		Vector2f center = position.add(new Vector2f(width / 2f, height / 2f));
		emitter.getPosition().set(center.x, center.y);
		emitter.init();
		SoundPlayer.playSound(SoundID.exp_2);
	}

}
