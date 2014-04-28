package com.spaceshooter.game.object.projectile;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.engine.ProjectileManager;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class RedPlasma extends Projectile{

	public RedPlasma(Vector2f position) {
		super(position, new Vector2f(75f, 0f));
		
		this.bitmap = BitmapHandler.loadBitmap("projectiles/PlasmaRed");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
	
		ProjectileManager.addPlayerProjectile(this);
	}
	
	@Override
	public void tick(float dt){
		if(this.getX() > GameView.WIDTH)
			live = false;
		
		distance = velocity.mul(dt);
		position = position.add(distance);
		
		rect.set((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
	}
	
	@Override
	public void draw(Canvas canvas,  float interpolation) {
		super.draw(canvas, interpolation);
	}
	
}
