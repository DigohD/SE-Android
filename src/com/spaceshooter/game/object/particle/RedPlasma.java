package com.spaceshooter.game.object.particle;

import android.graphics.Rect;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.engine.ProjectileManager;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class RedPlasma extends Particle{
	
	public RedPlasma(Vector2f position, Vector2f velocity) {
		super(position, new Vector2f(75f, 0f));
		this.bitmap = BitmapHandler.loadBitmap("particles/PlasmaRed");
		this.velocity = velocity;

		timeLived = 0;
		lifetime = 60;
		
		GameObjectManager.addGameObject(this);
	}
	
	@Override
	public void tick(float dt){
		timeLived++;
		if(timeLived > lifetime)
			live = false;
		
		position = position.add(velocity.mul(dt));
		
		rect.set((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
	}
	
}
