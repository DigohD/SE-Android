package com.spaceshooter.game.object.loot;

import android.graphics.Rect;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.object.Collideable;
import com.spaceshooter.game.object.particle.ParticleID;
import com.spaceshooter.game.object.particle.emitter.Emitter;
import com.spaceshooter.game.object.particle.emitter.RadialEmitter;
import com.spaceshooter.game.object.player.Player;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;

public class SlowTimePack extends Loot{
	
	private Emitter emitter;

	public SlowTimePack(Vector2f position) {
		super(position);
		this.bitmap = BitmapHandler.loadBitmap("loot/slowTime");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		emitter = new RadialEmitter(8, ParticleID.PURPLE_DOT, new Vector2f(0,0), new Vector2f(20f, 0f));
		velocity =  new Vector2f(-15f, 0);
		
		GameObjectManager.addGameObject(this);
	}

	@Override
	public void collisionWith(Collideable obj) {
		if(obj instanceof Player){
			if(live){
				death();
				live = false;
			}
		}
	}

	@Override
	public void death() {
		Vector2f center = position.add(new Vector2f(width/2f, height/2f));
		emitter.getPosition().set(center.x, center.y);
		emitter.init();
	}

}
