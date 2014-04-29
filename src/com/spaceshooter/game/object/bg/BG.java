package com.spaceshooter.game.object.bg;

import android.graphics.Canvas;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;

public class BG extends DynamicObject{

	public BG() {
		super(new Vector2f(0, 0));
		
		this.bitmap = BitmapHandler.loadBitmap("bg/bg");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		
		velocity = new Vector2f(-1f, 0f);
	}
	
	@Override
	public void tick(float dt) {
		distance = velocity.mul(dt);
		position = position.add(distance);
	}
	
	@Override
	public void draw(Canvas canvas,  float interpolation) {
		interpolate(interpolation);
		canvas.drawBitmap(bitmap, interpolatedPosition.x, interpolatedPosition.y, null);
		canvas.drawBitmap(bitmap, interpolatedPosition.x + 800, interpolatedPosition.y, null);
	}

}
