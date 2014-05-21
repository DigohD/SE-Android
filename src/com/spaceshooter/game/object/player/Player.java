package com.spaceshooter.game.object.player;

import java.util.HashMap;

import android.graphics.Rect;

import com.spaceshooter.game.GameActivity;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.object.Collideable;
import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.object.enemy.Asteroid;
import com.spaceshooter.game.object.enemy.Enemy;
import com.spaceshooter.game.object.loot.HealthPack;
import com.spaceshooter.game.object.loot.Loot;
import com.spaceshooter.game.object.loot.SlowTimePack;
import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.emitter.ConstantEmitter;
import com.spaceshooter.game.object.particle.emitter.Emitter;
import com.spaceshooter.game.object.particle.emitter.RadialEmitter;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.object.weapon.Gun;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.SoundPlayer;
import com.spaceshooter.game.util.SoundPlayer.SoundID;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class Player extends DynamicObject implements Collideable {

	private Vector2f targetPosition = new Vector2f(0, 0);
	public Vector2f targetVelocity;
	public Vector2f diff;
	private Vector2f topGunPos;
	private Vector2f bottomGunPos;

	protected Rect rect;

	private static ConstantEmitter engine;

	public static ConstantEmitter getEngine() {
		return engine;
	}

	private Emitter emitter;

	private Gun topGun;
	private Gun bottomGun;

	private boolean update = false;
	private boolean startComboCount = false;

	private int enemyKillCount = 0;
	private int timer = 0;
	private int combo = 0;
	private int score = 0;

	private float steps = 15;
	private float dtSteps = 5f;
	private float hp = 100f, maxHP = 100f;

	private HashMap<Integer, Loot> loots;

	public int lootCounter = 0;

	public HashMap<Integer, Loot> getLoots() {
		return loots;
	}

	public Player(Vector2f position) {
		super(position);

		this.bitmap = BitmapHandler.loadBitmap("player/ship");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();

		rect = new Rect((int) position.x, (int) position.y, (int) position.x
				+ width, (int) position.y + height);

		loots = new HashMap<Integer, Loot>();

		targetVelocity = new Vector2f(0, 0);
		velocity = new Vector2f(0, 0);

		topGunPos = new Vector2f(position.x, position.y + 4);
		bottomGunPos = new Vector2f(position.x, position.y + width - 6);
	}

	// used for unit testing
	public Player(Vector2f position, int width, int height) {
		super(position);

		this.width = width;
		this.height = height;

		rect = new Rect((int) position.x, (int) position.y, (int) position.x
				+ width, (int) position.y + height);

		targetVelocity = new Vector2f(0, 0);
		velocity = new Vector2f(0, 0);

		topGunPos = new Vector2f(position.x, position.y + 4);
		bottomGunPos = new Vector2f(position.x, position.y + width - 6);
	}

	public void init() {
		position = GameActivity.savedPos;

		targetPosition.set(position.x, position.y);
		targetVelocity.x = 0;
		targetVelocity.y = 0;

		lootCounter = 0;
		loots = new HashMap<Integer, Loot>();

		HealthPack startPack = new HealthPack(new Vector2f(-100, 0), 10);
		HealthPack startPack2 = new HealthPack(new Vector2f(-100, 0), 10);
		SlowTimePack startPack3 = new SlowTimePack(new Vector2f(-100, 0));

		startPack.setSaved(true);
		startPack2.setSaved(true);
		startPack3.setSaved(true);

		loots.put(0, startPack);
		loots.put(1, startPack2);
		loots.put(2, startPack3);

		emitter = new RadialEmitter(8, ParticleID.RED_PLASMA,
				new Vector2f(0, 0), new Vector2f(20f, 0f));

		engine = new ConstantEmitter(1, ParticleID.ENGINE, new Vector2f(
				position.y + height / 2, position.x - 8), new Vector2f(-7f, 0f));
		engine.setPosition(new Vector2f(position.x - 8, position.y + height / 2
				- 7));
		engine.setIsSpread(true);
		engine.init();

		live = true;
		update = false;
		combo = 0;
		timer = 0;
		enemyKillCount = 0;
	}

	public void setTargetVelocity(float dX, float dY) {
		targetVelocity.x = dX;
		targetVelocity.y = dY;
		update = true;
	}

	public boolean targetOK = true;

	@Override
	public void move(float dt) {
		if (!update) {
			targetVelocity.x = 0;
			targetVelocity.y = 0;
		}

		velocity.x = approach(targetVelocity.x, velocity.x, dt * dtSteps);
		velocity.y = approach(targetVelocity.y, velocity.y, dt * dtSteps);

		if (!targetOK) {
			targetPosition.x = targetPosition.x + velocity.x;

			diff = targetPosition.sub(position).div(steps);
			distance = diff;
			position = position.add(diff);
		} else {
			targetPosition.x = targetPosition.x + velocity.x;
			targetPosition.y = targetPosition.y + velocity.y;

			diff = targetPosition.sub(position).div(steps);
			distance = diff;
			position = position.add(diff);
		}

		engine.setPosition(new Vector2f(position.x - 8, position.y + height/2 - 7));

		topGunPos.set(position.x, position.y + 4);
		bottomGunPos.set(position.x, position.y + width - 6);
	}

	private void inBound() {
		if (position.x < 0)
			position.x = 2;
		if (position.x + width >= GameView.WIDTH)
			position.x = (GameView.WIDTH - width) - 2;
		if (position.y < 0)
			position.y = 0;
		if (position.y + height >= GameView.HEIGHT - 160)
			position.y = (GameView.HEIGHT - height) - 160;

		if (targetPosition.x < 0)
			targetPosition.x = 2;
		if (targetPosition.x + width >= GameView.WIDTH)
			targetPosition.x = (GameView.WIDTH - width) - 2;
		if (targetPosition.y < 0)
			targetPosition.y = 0;
		if (targetPosition.y + height >= GameView.HEIGHT - 160)
			targetPosition.y = (GameView.HEIGHT - height) - 160;
	}

	@Override
	public void tick(float dt) {
		inBound();
		rect.set((int) position.x, (int) position.y, (int) position.x + width,
				(int) position.y + height);
		move(dt);

		if (startComboCount) {
			timer++;
			if(GameObjectManager.isSlowTime()){
				if(timer > 60*1*(1+GameObjectManager.slowtime)){
					startComboCount = false;
					enemyKillCount = 0;
					combo = 0;
					timer = 0;
				}
				if(timer <= 60*1*(1+GameObjectManager.slowtime) && combo != enemyKillCount){
					combo = enemyKillCount;
					timer = 0;
				}
			}else{
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

	}

	@Override
	public void collisionWith(Collideable obj) {
		if (obj instanceof Enemy) {
			if (obj instanceof Asteroid) {
				Asteroid ast = (Asteroid) obj;
				if (ast.getWidth() <= 30) {
					hp = hp - 20;
					if (hp <= 0) {
						if (live)
							death();
					}
				} else {
					if (live)
						death();
				}
			} else {
				if (live)
					death();
			}
		}

		if (obj instanceof Projectile) {
			Projectile p = (Projectile) obj;
			hp = hp - p.getDamage();
			if (hp <= 0) {
				if (live)
					death();
			}
		}

		if (obj instanceof Loot) {
			Loot loot = (Loot) obj;
			if (loot instanceof HealthPack) {
				HealthPack hpack = (HealthPack) obj;
				if (lootCounter < 3 && hp >= maxHP) {
					lootCounter = 0;
					while (loots.containsKey(lootCounter) && lootCounter < 3) {
						lootCounter++;
					}
					if (lootCounter < 3) {
						loots.put(lootCounter, hpack);
						lootCounter++;
						hpack.setSaved(true);
					}
				}
				if (hp < maxHP) {
					hp = hp + hpack.getHp();
					if (hp > maxHP)
						hp = maxHP;
				}
			}

			if (loot instanceof SlowTimePack) {
				SlowTimePack stp = (SlowTimePack) obj;
				if (lootCounter < 3 && GameObjectManager.isSlowTime()) {
					lootCounter = 0;
					while (loots.containsKey(lootCounter) && lootCounter < 3) {
						lootCounter++;
					}
					if (lootCounter < 3) {
						loots.put(lootCounter, stp);
						lootCounter++;
						stp.setSaved(true);
					}

				}

				if (!GameObjectManager.isSlowTime())
					GameObjectManager.setSlowTime(true);
			}
		}
	}

	public void death() {
		Vector2f center = position.add(new Vector2f(width / 2f, height / 2f));
		emitter.getPosition().set(center.x, center.y);
		emitter.init();
		SoundPlayer.playSound(SoundID.exp_1);
		hp = 0;
		live = false;
		engine.setLive(false);
	}

	public Rect getRect() {
		return rect;
	}

	public int getCombo() {
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

	public void incHp(float amount) {
		this.hp += amount;
		if (this.hp > maxHP)
			hp = maxHP;
	}

	public void setMaxHP(float maxHP) {
		this.maxHP = maxHP;
	}

	public void setUpdate(boolean u) {
		update = u;
	}

	public void setTopGun(Gun gun) {
		topGun = gun;
	}

	public void setBottomGun(Gun gun) {
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
