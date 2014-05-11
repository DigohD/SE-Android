package com.spaceshooter.game.object.projectile.player;

import android.graphics.Rect;

import com.spaceshooter.game.engine.ProjectileManager;
import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.emitter.ImpactEmitter;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;

public class GreenPlasma extends Projectile{

	public GreenPlasma(Vector2f position) {
		super(position, new Vector2f(80f, 0f));
		damage = 100f;
		this.bitmap = BitmapHandler.loadBitmap("projectiles/PlasmaGreen");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
	
		ProjectileManager.addPlayerProjectile(this);
	}
	
	public GreenPlasma(Vector2f position, Vector2f velocity) {
		super(position, velocity);
		damage = 100f;
		this.bitmap = BitmapHandler.loadBitmap("projectiles/PlasmaGreen");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
	
		ProjectileManager.addPlayerProjectile(this);
	}
	
	//used for unit testing
	public GreenPlasma(Vector2f position, int width, int height) {
		super(position, new Vector2f(80f, 0f));
		damage = 20f;
				
		this.width = width;
		this.height = height;
			
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
			
		ProjectileManager.addPlayerProjectile(this);
	}

	@Override
	public void death() {
		new ImpactEmitter(2, ParticleID.GreenBall, 
				position, velocity);
	}
	
}
