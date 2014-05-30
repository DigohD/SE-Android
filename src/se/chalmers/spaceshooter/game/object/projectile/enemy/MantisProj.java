package se.chalmers.spaceshooter.object.projectile.enemy;

import se.chalmers.spaceshooter.engine.GameObjectManager;
import se.chalmers.spaceshooter.object.particle.ParticleID;
import se.chalmers.spaceshooter.object.particle.emitter.ImpactEmitter;
import se.chalmers.spaceshooter.object.projectile.Projectile;
import se.chalmers.spaceshooter.util.BitmapHandler;
import se.chalmers.spaceshooter.util.Vector2f;
import android.graphics.Rect;


public class MantisProj extends Projectile{

	public MantisProj(Vector2f position) {
		super(position, new Vector2f(-60f, 0f));
		damage = 5f;
		this.bitmap = BitmapHandler.loadBitmap("projectiles/PlasmaGreen2");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
	
		type = Type.ENEMY;
		
		GameObjectManager.addGameObject(this);
	}
	
	//used for unit testing
	public MantisProj(Vector2f position, int width, int height) {
		super(position, new Vector2f(80f, 0f));
		damage = 20f;
				
		this.width = width;
		this.height = height;
			
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		
	}	

	@Override
	public void death() {
		new ImpactEmitter(3, ParticleID.GreenBall, 
				position, velocity);
	}

}
