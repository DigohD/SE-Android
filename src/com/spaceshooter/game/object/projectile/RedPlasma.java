package com.spaceshooter.game.object.projectile;

import android.graphics.Rect;

import com.spaceshooter.game.engine.ProjectileManager;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class RedPlasma extends Projectile{

	public RedPlasma(Vector2f position) {
		super(position, new Vector2f(15f, 0f));
		
		this.bitmap = BitmapHandler.loadBitmap("projectiles/PlasmaRed.png");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
	
		ProjectileManager.addPlayerProjectile(this);
	}
	
	@Override
	public void tick(float dt){
		if(this.getX() > GameView.width)
			live = false;
		
		position = position.add(velocity.mul(dt));
		
		rect.set((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
	}
	
}
