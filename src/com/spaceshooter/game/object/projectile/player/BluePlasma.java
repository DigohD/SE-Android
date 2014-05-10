package com.spaceshooter.game.object.projectile.player;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.engine.ProjectileManager;
import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.emitter.ImpactEmitter;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;

public class BluePlasma extends Projectile{

	public BluePlasma(Vector2f position) {
		super(position, new Vector2f(80f, 0f));
		damage = 8f;
		this.bitmap = BitmapHandler.loadBitmap("projectiles/PlasmaBlue");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
	
		ProjectileManager.addPlayerProjectile(this);
	}
	
	public BluePlasma(Vector2f position, Vector2f velocity) {
		super(position, velocity);
		damage = 8f;
		this.bitmap = BitmapHandler.loadBitmap("projectiles/PlasmaBlue");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
	
		ProjectileManager.addPlayerProjectile(this);
	}

	@Override
	public void death() {
		new ImpactEmitter(1, ParticleID.LBlueBall, 
				position, velocity);
	}
	
}
