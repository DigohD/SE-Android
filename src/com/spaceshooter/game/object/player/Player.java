package com.spaceshooter.game.object.player;

import android.graphics.Rect;

import com.spaceshooter.game.object.Collideable;
import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.enemy.Asteroid;
import com.spaceshooter.game.object.enemy.Enemy;
import com.spaceshooter.game.object.loot.HealthPack;
import com.spaceshooter.game.object.loot.Loot;
import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.emitter.ConstantEmitter;
import com.spaceshooter.game.object.particle.emitter.RadialEmitter;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.object.weapon.Gun;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.SoundPlayer;
import com.spaceshooter.game.util.SoundPlayer.SoundID;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class Player extends DynamicObject implements Collideable {
	
	private String name = "Player1";
	
	private Vector2f targetPosition = new Vector2f(0,0);
	private Vector2f targetVelocity;
	private Vector2f topGunPos;
	private Vector2f bottomGunPos;
	
	protected Rect rect;
	
	private ConstantEmitter engine;
	
	private Gun topGun;
	private Gun bottomGun;
	
	private boolean update = false;
	private boolean startComboCount = false;
	
	private int enemyKillCount = 0;
	private int timer = 0;
	private int combo = 0;
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
	
	//used for unit testing
	public Player(Vector2f position, int width, int height) {
		super(position);
		
		this.width = width;
		this.height = height;

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
		targetVelocity.x = 0;
		targetVelocity.y = 0;
		engine = new ConstantEmitter(1, ParticleID.ENGINE, new Vector2f(position.y + height/2, position.x - 8),
				new Vector2f(-7f, 0f));
		engine.setPosition(new Vector2f(position.x - 8, position.y + height/2 - 7));
		engine.setIsSpread(true);
		live = true;
		update = false;
		combo = 0;
		timer = 0;
		enemyKillCount = 0;
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
			position.x = 2;
		if(position.x + width >= GameView.WIDTH)
			position.x = (GameView.WIDTH - width) - 2;
		if(position.y < 0) 
			position.y = 0;
		if(position.y + height >= GameView.HEIGHT)
			position.y = (GameView.HEIGHT - height) - 2;
			
		if(targetPosition.x < 0) 
			targetPosition.x = 2;
		if(targetPosition.x + width >= GameView.WIDTH)
			targetPosition.x = (GameView.WIDTH - width) - 2;
		if(targetPosition.y < 0) 
			targetPosition.y = 0;
		if(targetPosition.y + height >= GameView.HEIGHT)
			targetPosition.y = (GameView.HEIGHT - height) - 2;
	}
	
	@Override
	public void tick(float dt) {
		inBound();
		rect.set((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		move(dt);
		
		if(startComboCount){
			timer++;
			if(timer > 60*1){
				startComboCount = false;
				enemyKillCount = 0;
				combo = 0;
				timer = 0;
			}
			if(timer <= 60*1 && combo != enemyKillCount){
				combo = enemyKillCount;
				timer = 0;
			}
		}

	}

	@Override
	public void collisionWith(Collideable obj) {
		if(obj instanceof Enemy){
			if(obj instanceof Asteroid){
				Asteroid ast = (Asteroid) obj;
				if(ast.getWidth() <= 30){
					hp = hp - 20;
					if(hp <= 0) {
						if(live) death();
						live = false;
						engine.setLive(false);
					}
				}else{
					if(live) death();
					live = false;
					engine.setLive(false);
				}
			}else{
				if(live) death();
				hp = 0;
				live = false;
				engine.setLive(false);
			}
			
		}

		if(obj instanceof Projectile) {
			Projectile p = (Projectile) obj;
			hp = hp - p.getDamage();
			if(hp <= 0) {
				if(live) death();
				live = false;
				engine.setLive(false);
			}
		}
		
		if(obj instanceof Loot){
			Loot loot = (Loot) obj;
			if(loot instanceof HealthPack){
				HealthPack hpack = (HealthPack) obj;
				if(hp < maxHP){
					hp = hp + hpack.getHp();
					if(hp > maxHP) hp = maxHP;
				}
			}
		}
	}
	
	public void death() {
		Vector2f center = position.add(new Vector2f(width/2f, height/2f));
		new RadialEmitter(8, ParticleID.RED_PLASMA, center, new Vector2f(20f, 0f));
		SoundPlayer.playSound(SoundID.exp_1);
	}
	
	public Rect getRect(){
		return rect;
	}

	public int getCombo(){
		return combo;
	}

	public int getEnemyKillCount() {
		return enemyKillCount;
	}

	public void incEnemyKillCount(int enemyKillCount) {
		startComboCount = true;
		this.enemyKillCount += enemyKillCount;
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
	
	public String getName(){
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
