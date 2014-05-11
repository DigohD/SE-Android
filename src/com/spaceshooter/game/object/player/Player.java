package com.spaceshooter.game.object.player;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.object.Collideable;
import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.enemy.Enemy;
import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.emitter.ConstantEmitter;
import com.spaceshooter.game.object.particle.emitter.RadialEmitter;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.object.weapon.Gun;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.SoundPlayer;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class Player extends DynamicObject implements Collideable {

	private Vector2f targetPosition = new Vector2f(0,0);
	private Vector2f targetVelocity;
	private Vector2f topGunPos;
	private Vector2f bottomGunPos;
	
	private ConstantEmitter engine;
	
	private Gun topGun;
	private Gun bottomGun;
	
	private boolean update = false;
	private int score = 0;
	
	private float steps = 15;
	//lower values result in more inertia, eg the ship feels heavier and takes more time to stop
	private float dtSteps = 9.82f;
	private float hp = 100f, maxHP = 100f;
	
	public Player(Vector2f position) {
		super(position);
		
		this.bitmap = BitmapHandler.loadBitmap("player/ship");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();

		rect = new Rect((int) position.x, (int) position.y, (int) position.x
				+ width, (int) position.y + height);

		targetVelocity = new Vector2f(0, 0);
		velocity = new Vector2f(0, 0);
		
		topGunPos = new Vector2f(position.x, position.y + 4);
		bottomGunPos = new Vector2f(position.x, position.y + width-6);
	}

	public void init(){
		position = new Vector2f(GameView.WIDTH/2, GameView.HEIGHT/2);
		targetPosition.set(position.x, position.y);
		engine = new ConstantEmitter(1, ParticleID.ENGINE, new Vector2f(position.y + height/2, position.x - 8),
				new Vector2f(-7f, 0f));
		engine.setPosition(new Vector2f(position.x - 8, position.y + height/2 - 7));
		engine.setIsSpread(true);
		live = true;
		update = false;
		targetVelocity.x = 0;
		targetVelocity.y = 0;
	}
	
	public void incTargetPos(float dX, float dY) {
		targetVelocity.x = dX;
		targetVelocity.y = dY;
		update = true;
	}

	@Override
	public void move(float dt){
		if(!update){
			targetVelocity.x = 0;
			targetVelocity.y = 0;
		}
		
		velocity.x = approach(targetVelocity.x, velocity.x , dt*dtSteps);
		velocity.y = approach(targetVelocity.y, velocity.y , dt*dtSteps);

		targetPosition.x = targetPosition.x + velocity.x;
		targetPosition.y = targetPosition.y + velocity.y;
		
		Vector2f diff = targetPosition.sub(position).div(steps);
		distance = diff;
		position = position.add(diff);
	
		engine.setPosition(new Vector2f(position.x - 8, position.y + height/2 - 7));
		topGunPos.set(position.x, position.y + 4);
		bottomGunPos.set(position.x, position.y + width - 6);
	}
	
	private void inBound(){
		if(position.x < 0) 
			position.x = 4;
		if(position.x + width >= GameView.WIDTH)
			position.x = (GameView.WIDTH - width) - 4;
		if(position.y < 0) 
			position.y = 0;
		if(position.y + height >= GameView.HEIGHT)
			position.y = (GameView.HEIGHT - height) - 4;
			
		if(targetPosition.x < 0) 
			targetPosition.x = 4;
		if(targetPosition.x + width >= GameView.WIDTH)
			targetPosition.x = (GameView.WIDTH - width) - 4;
		if(targetPosition.y < 0) 
			targetPosition.y = 0;
		if(targetPosition.y + height >= GameView.HEIGHT)
			targetPosition.y = (GameView.HEIGHT - height) - 4;
	}
	
	@Override
	public void tick(float dt) {
		inBound();
		rect.set((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		move(dt);
	}

	@Override
	public void draw(Canvas canvas, float interpolation) {
		super.draw(canvas, interpolation);
	}

	@Override
	public void collisionWith(GameObject obj) {
		if(obj instanceof Enemy){
			if(live) death();
			hp = 0;
			live = false;
			engine.setLive(false);
		}

		if (obj instanceof Projectile) {
			Projectile p = (Projectile) obj;
			hp = hp - p.getDamage();
			p.death();
			p.setLive(false);
			if(hp <= 0) {
				if(live) death();
				live = false;
				engine.setLive(false);
			}
		}
	}
	
	public void death() {
		Vector2f center = position.add(new Vector2f(width/2f, height/2f));
		new RadialEmitter(8, ParticleID.RED_PLASMA, center, new Vector2f(20f, 0f));
		SoundPlayer.playSound(2);
	}


	public void setScore(int value) {
		score = value;
	}
	

	public void incScore(int value) {
		score += value;
	}

	public int getScore() {
		return score;
	}

	public float getHp() {
		return hp;
	}

	public float getMaxHP() {
		return maxHP;
	}

	public void setHp(float hp) {
		this.hp = hp;
	}

	public void setMaxHP(float maxHP) {
		this.maxHP = maxHP;
	}
	
	public void setUpdate(boolean u){
		update = u;
	}
	
	public void setTopGun(Gun gun){
		topGun = gun;
	}
	
	public void setBottomGun(Gun gun){
		bottomGun = gun;
	}

	public Vector2f getTopGunPos() {
		return topGunPos;
	}

	public Vector2f getBottomGunPos() {
		return bottomGunPos;
	}

	public Gun getTopGun() {
		return topGun;
	}

	public Gun getBottomGun() {
		return bottomGun;
	}

	public Vector2f getTargetPosition() {
		return targetPosition;
	}

}
