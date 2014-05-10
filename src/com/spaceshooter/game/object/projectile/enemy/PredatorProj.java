package com.spaceshooter.game.object.projectile.enemy;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.engine.ProjectileManager;
import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.emitter.ImpactEmitter;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class PredatorProj extends Projectile{

	public PredatorProj(Vector2f position) {
		super(position, new Vector2f(-40f, 0f));
		damage = 1f;
		this.bitmap = BitmapHandler.loadBitmap("projectiles/PlasmaPurple");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
	
		ProjectileManager.addEnemyProjectile(this);
	}

	@Override
	public void death() {
		new ImpactEmitter(3, ParticleID.PURPLE_DOT, 
				position, velocity);
	}

}
