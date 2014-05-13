package com.spaceshooter.game.object.loot;

import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.util.Vector2f;

public class Loot extends DynamicObject{

	public Loot(Vector2f position) {
		super(position);
		
	}
	
	@Override
	public void tick(float dt) {
		if(getX() < -width)
			live = false;
		rect.set((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		super.tick(dt);
	}

}
