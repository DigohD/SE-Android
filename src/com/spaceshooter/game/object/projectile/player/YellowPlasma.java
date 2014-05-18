package com.spaceshooter.game.object.projectile.player;

import android.graphics.Rect;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.emitter.ImpactEmitter;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.SoundPlayer;
import com.spaceshooter.game.util.SoundPlayer.SoundID;
import com.spaceshooter.game.util.Vector2f;

public class YellowPlasma extends Projectile{

	public YellowPlasma(Vector2f position) {
		super(position, new Vector2f(80f, 0f));
		damage = 5f;
		this.bitmap = BitmapHandler.loadBitmap("projectiles/PlasmaYellow");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		
		type = Type.PLAYER;
	
		GameObjectManager.addGameObject(this);
	}
	
	public YellowPlasma(Vector2f position, Vector2f velocity) {
		super(position, velocity);
		damage = 5f;
		this.bitmap = BitmapHandler.loadBitmap("projectiles/PlasmaYellow");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		
		type = Type.PLAYER;
		
		GameObjectManager.addGameObject(this);
	}
	
	//used for unit testing
	public YellowPlasma(Vector2f position, int width, int height) {
		super(position, new Vector2f(80f, 0f));
		damage = 5f;
			
		this.width = width;
		this.height = height;
		
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
	}
	
	@Override
	public void death() {
		new ImpactEmitter(1, ParticleID.YellowPlasma, 
				position, velocity);
		SoundPlayer.playSound(SoundID.hit_YellowPlasma);
	}
	
}
