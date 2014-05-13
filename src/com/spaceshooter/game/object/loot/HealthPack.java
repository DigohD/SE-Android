package com.spaceshooter.game.object.loot;

import android.graphics.Rect;

import com.spaceshooter.game.engine.CollisionManager;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;

public class HealthPack extends Loot{
	
	private int hp;

	public HealthPack(Vector2f position, int hp) {
		super(position);
		
		this.hp = hp;
		this.bitmap = BitmapHandler.loadBitmap("loot/healthpack");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		velocity = new Vector2f(-20f, 0);
		
		GameObjectManager.addGameObject(this);
		CollisionManager.addLoot(this);
	}
	
	public int getHp() {
		return hp;
	}

}
