package com.spaceshooter.game.object.loot;

import android.graphics.Rect;

import com.spaceshooter.game.object.Collideable;
import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.util.Vector2f;

public abstract class Loot extends DynamicObject implements Collideable{
	
	protected Rect rect;
	protected boolean saved = false;

	public Loot(Vector2f position) {
		super(position);
		
	}
	
	@Override
	public void tick(float dt) {
		if(getX() < -width)
			live = false;
		rect.set((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		move(dt);
	}
	
	public abstract void death();
	
	public Rect getRect(){
		return rect;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}	
	

}
