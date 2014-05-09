package com.spaceshooter.game.object.enemy;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.emitter.RadialEmitter;
import com.spaceshooter.game.object.projectile.enemy.MantisProj;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.SoundPlayer;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class Mantis extends Enemy {
	
	private int reload;
	private Vector2f targetVelocity;
	
	public Mantis() {
		this(new Vector2f(GameView.WIDTH-10, 0));
	}

	public Mantis(Vector2f position) {
		super(position);
		this.bitmap = BitmapHandler.loadBitmap("enemies/mantis");
		
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		
		speedX = -25f;
		speedY = 0;
		
		velocity = new Vector2f(speedX, speedY);
		targetVelocity = new Vector2f(speedX,speedY);
		hp = 20f;
		maxHp = 20f;
	}
	
	private float approach(float target, float current, float dt){
		float diff = target - current;
		if(diff > dt)
			return current + dt;
		if(diff < -dt)
			return current - dt;
		return target;
	}
	
	boolean directionChoosed = false;
	boolean fire = false;
	boolean top , bottom;

	@Override
	public void tick(float dt) {
		super.tick(dt);
		
		if(position.y > GameView.HEIGHT/2 && position.x < GameView.WIDTH - 30 && !directionChoosed){
			targetVelocity.y = -50f;
			directionChoosed = true;
			top = true;
		}
		
		if(position.y < GameView.HEIGHT/2 && position.x < GameView.WIDTH - 30 && !directionChoosed){
			targetVelocity.y = 50f;
			directionChoosed = true;
			bottom = true;
		}
		
		if(position.x <= GameView.WIDTH/2 + 120 && !fire){
			targetVelocity.y = 0;
			targetVelocity.x = -15f;
			fire = true;
		}
		
		if(position.x <= GameView.WIDTH/2){
			fire = false;
			if(top) {
				targetVelocity.y = 50f;
				targetVelocity.x = -20f;
			}
			if(bottom) {
				targetVelocity.y = -50f;
				targetVelocity.x = -20f;
			}
		}
		
		if(fire){
			reload++;
			if(reload > 20){
				reload = 0;
				Vector2f v = new Vector2f(position.x, position.y + 2);
				new MantisProj(v);
				//SoundPlayer.playSound(1);
			}
		}
		
		velocity.x = approach(targetVelocity.x, velocity.x, dt);
		velocity.y = approach(targetVelocity.y, velocity.y, dt);
		distance = velocity.mul(dt);
		position = position.add(distance);
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
