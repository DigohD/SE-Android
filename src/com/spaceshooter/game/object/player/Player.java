package com.spaceshooter.game.object.player;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.engine.CollisionManager;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.object.Collideable;
import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.enemy.Enemy;
import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.emitter.ConstantEmitter;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.object.projectile.RedPlasma;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;

public class Player extends DynamicObject implements Collideable {

	private Vector2f targetPosition = new Vector2f(0, 0);
	private Vector2f targetVelocity;
	private boolean update = false;
	private int score = 0;
	private float steps = 10;
	private int reload;
	private ConstantEmitter engine;
	
	public Player(Vector2f position) {
		super(position);
		
		this.bitmap = BitmapHandler.loadBitmap("player/ship");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();

		rect = new Rect((int) position.x, (int) position.y, (int) position.x
				+ width, (int) position.y + height);

		speedX = 10f;
		speedY = 10f;
		
		targetVelocity = new Vector2f(speedX, speedY);
		velocity = new Vector2f(0, 0);
	}

	public void init(){
		engine = new ConstantEmitter(1, ParticleID.ENGINE, new Vector2f(position.y + height/2, position.x - 8),
				new Vector2f(-7f, 0f));
		engine.setIsSpread(true);
	}
	
	public void setTargetPos(float x, float y) {
		targetPosition = new Vector2f(x, y);
		update = true;
	}
	
	public void incTargetPos(float dX, float dY) {
		Vector2f newTarget = new Vector2f(targetPosition.x + dX, targetPosition.y + dY);
		targetPosition = newTarget;
		update = true;
	}
	
	private float approach(float target, float current, float dt){
		float diff = target - current;
		if(diff > dt)
			return current + dt;
		if(diff < -dt)
			return current - dt;
		return target;
	}
	
	private void move(float dt){
		velocity.x = approach(targetVelocity.x, velocity.x, dt*20f);
		velocity.y = approach(targetVelocity.y, velocity.y, dt*20f);
		distance = velocity.mul(dt);
		Vector2f diff = targetPosition.sub(position).div(steps);
		position = position.add(diff.div(distance));
		engine.setPosition(new Vector2f(position.x - 8, position.y + height/2 - 7));
	}
	
	@Override
	public void tick(float dt) {
		super.tick(dt);
		reload++;
		if(reload > 15){
			reload = 0;
			new RedPlasma(position);
			Vector2f v = new Vector2f(position.x, position.y + width-2);
			new RedPlasma(v);
		}
		if(update) {
			move(dt);
		}
	}

	@Override
	public void draw(Canvas canvas, float interpolation) {
		super.draw(canvas, interpolation);
	}

	@Override
	public void collisionWith(GameObject obj) {
		if(obj instanceof Enemy){
			live = false;
			obj.setLive(false);
		}

		if (obj instanceof Projectile) {

		}

	}

	public void decreaseSteps(float amount) {
		steps -= amount;
	}

	public void increaseSteps(float amount) {
		steps += amount;
	}

	public float getSteps() {
		return steps;
	}

	public void setScore(int value) {
		score = score + value;
	}

	public int getScore() {
		return score;
	}

}
