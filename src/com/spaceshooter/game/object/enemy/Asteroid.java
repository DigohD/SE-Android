package com.spaceshooter.game.object.enemy;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.loot.HealthPack;
import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.emitter.RadialEmitter;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.SoundPlayer;
import com.spaceshooter.game.util.SoundPlayer.SoundID;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class Asteroid extends Enemy{

	private Vector2f targetVelocity;
	
	public Asteroid(){
		this(new Vector2f(0,0));
	}

	public Asteroid(Vector2f position) {
		super(position);

		this.bitmap = loadRandomAsteroid("enemies/asteroids/asteroid", 5);
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		
		if(width >= 30) speedX = Randomizer.getFloat(-15f, -25f);
		else speedX = Randomizer.getFloat(-35f, -45f);
		speedY = 0;
		
		velocity = new Vector2f(-15f, 0);
		targetVelocity = new Vector2f(speedX,speedY);
		
		hp = width;
		maxHp = width;
		enemyPoints = 10;
	}
	
	private Bitmap loadRandomAsteroid(String name, int numOfAsteroids){
		List<String> asteroidNames = new ArrayList<String>();
		for(int i = 0; i < numOfAsteroids; i++)
			asteroidNames.add(name + i);
		int i = Randomizer.getInt(0, asteroidNames.size());
		return BitmapHandler.loadBitmap(asteroidNames.get(i));
	}

	@Override
	public void tick(float dt) {
		super.tick(dt);
		velocity.x = approach(targetVelocity.x, velocity.x, dt);
		velocity.y = approach(targetVelocity.y, velocity.y, dt);
		move(dt);
	}
	
	@Override
	public void collisionWith(GameObject obj) {
		if(obj instanceof Projectile){
			Projectile p = (Projectile) obj;
			hp = hp - p.getDamage();
			if(hp <= 0){
				GameObjectManager.getPlayer().incEnemyKillCount(1);
				int score = calculatePlayerScore(enemyPoints, GameView.getLevelID(), GameObjectManager.getPlayer().getCombo());
				GameObjectManager.getPlayer().incScore(score);
				death();
				live = false;
				int rn = Randomizer.getInt(0, 8);
				if(rn == 2){
					new HealthPack(position, velocity, 10);
				}
			}
			p.death();
			p.setLive(false);
		}
	}

	@Override
	public void death() {
		Vector2f center = position.add(new Vector2f(width/2f, height/2f));
		new RadialEmitter(40, ParticleID.DUST, center, new Vector2f(10f, 0f));
		SoundPlayer.playSound(SoundID.exp_1);
	}

}
