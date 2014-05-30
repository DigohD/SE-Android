package se.chalmers.spaceshooter.object.enemy;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.spaceshooter.engine.GameObjectManager;
import se.chalmers.spaceshooter.object.Collideable;
import se.chalmers.spaceshooter.object.loot.HealthPack;
import se.chalmers.spaceshooter.object.loot.SlowTimePack;
import se.chalmers.spaceshooter.object.particle.ParticleID;
import se.chalmers.spaceshooter.object.particle.emitter.Emitter;
import se.chalmers.spaceshooter.object.particle.emitter.RadialEmitter;
import se.chalmers.spaceshooter.object.player.Player;
import se.chalmers.spaceshooter.object.projectile.Projectile;
import se.chalmers.spaceshooter.util.BitmapHandler;
import se.chalmers.spaceshooter.util.Randomizer;
import se.chalmers.spaceshooter.util.SoundPlayer;
import se.chalmers.spaceshooter.util.Vector2f;
import se.chalmers.spaceshooter.util.SoundPlayer.SoundID;
import se.chalmers.spaceshooter.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Rect;


public class Asteroid extends Enemy{

	private Vector2f targetVelocity;
	private Emitter emitter;
	
	public Asteroid(){
		this(new Vector2f(0,0));
	}

	public Asteroid(Vector2f position) {
		super(position);

		this.bitmap = loadRandomAsteroid("enemies/asteroids/asteroid", 5);
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		emitter = new RadialEmitter((bitmap.getWidth() / 2), ParticleID.DUST,  new Vector2f(0f, 0f), new Vector2f(10f, 0f));
		
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
	public void collisionWith(Collideable obj) {
		if(obj instanceof Player){
			if(live){
				death();
				live = false;
			}
		}
		
		if(obj instanceof Projectile){
			Projectile p = (Projectile) obj;
			hp = hp - p.getDamage();
			if(hp <= 0){
				GameObjectManager.getPlayer().incEnemyKillCount(1);
				int score = calculatePlayerScore(enemyPoints, GameView.getLevelID(), GameObjectManager.getPlayer().getCombo());
				GameObjectManager.getPlayer().incScore(score);
				death();
				live = false;
				int rn = Randomizer.getInt(0, 10);
				int rn2 = Randomizer.getInt(0, 14);
				boolean lootDropped = false;
				if(rn == 2){
					new HealthPack(position, 10);
					lootDropped = true;
				}
				if(rn2 == 2 && !lootDropped){
					new SlowTimePack(position);
				}
			}
		}
	}

	@Override
	public void death() {
		Vector2f center = position.add(new Vector2f(width/2f, height/2f));
		emitter.getPosition().set(center.x, center.y);
		emitter.init();
		SoundPlayer.playSound(SoundID.exp_1);
	}

}
