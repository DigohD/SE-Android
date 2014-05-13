package com.spaceshooter.game.object.enemy;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.emitter.RadialEmitter;
import com.spaceshooter.game.object.projectile.enemy.PredatorProj;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.SoundPlayer;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class Predator extends Enemy {
	
	private Vector2f targetVelocity;
	private int reload;
	
	public Predator() {
		this(new Vector2f(GameView.WIDTH + 40, 0));
	}

	public Predator(Vector2f position) {
		super(position);
		this.bitmap = BitmapHandler.loadBitmap("enemies/predator");
		
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		
		speedX = -10f;
		speedY = 0;
		
		velocity = new Vector2f(speedX, speedY);
		targetVelocity = new Vector2f(speedX,speedY);
		hp = 50f;
		maxHp = 50f;
		enemyPoints = 20;
	}

	@Override
	public void tick(float dt) {
		super.tick(dt);
		reload++;
		if(reload > 65){
			reload = 0;
			Vector2f v = new Vector2f(position.x, position.y + width/2);
			new PredatorProj(v);
			//SoundPlayer.playSound(1);
		}

		velocity.x = approach(targetVelocity.x, velocity.x, dt);
		velocity.y = approach(targetVelocity.y, velocity.y, dt);
		move(dt);
	}

	@Override
	public void draw(Canvas canvas, float interpolation) {
		super.draw(canvas, interpolation);
	}

	@Override
	public void death() {
		Vector2f center = position.add(new Vector2f(width/2f, height/2f));
		new RadialEmitter(8, ParticleID.PURPLE_DOT, center, new Vector2f(20f, 0f));
		SoundPlayer.playSound(2);
	}

}
