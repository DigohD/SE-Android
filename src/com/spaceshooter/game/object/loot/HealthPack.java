package com.spaceshooter.game.object.loot;

import android.graphics.Rect;

import com.spaceshooter.game.engine.CollisionManager;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.emitter.RadialEmitter;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.SoundPlayer;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.util.SoundPlayer.SoundID;

public class HealthPack extends Loot{
	
	private int hp;
	

	public HealthPack(Vector2f position, int hp) {
		super(position);
		
		this.hp = hp;
		this.bitmap = BitmapHandler.loadBitmap("loot/healthpack");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		velocity = new Vector2f(-15f, 0);
		targetVelocity = new Vector2f(-20f, 0);
		
		GameObjectManager.addGameObject(this);
		CollisionManager.addLoot(this);
	}
	

	@Override
	public void death() {
		Vector2f center = position.add(new Vector2f(width/2f, height/2f));
		new RadialEmitter(8, ParticleID.PURPLE_DOT, center, new Vector2f(20f, 0f));
		//SoundPlayer.playSound(SoundID.exp_1);
	}
	

	public int getHp() {
		return hp;
	}

}
