package com.spaceshooter.game.object.bg;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;

public class BG extends DynamicObject{

	private Bitmap nebula;
	
	public BG() {
		super(new Vector2f(0, 0));
		
		this.bitmap = BitmapHandler.loadBitmap("bg/bg");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		
		nebula = BitmapHandler.loadBitmap("bg/nebula");

		
		velocity = new Vector2f(-5f, 0f);
	}
	
	@Override
	public void tick(float dt) {
		distance = velocity.mul(dt);
		position = position.add(distance);
		
	}
	
	float i = 0, j = 0;
	
	@Override
	public void draw(Canvas canvas,  float interpolation) {
		interpolate(interpolation);
		
		if(interpolatedPosition.x + i < 0)
			i+=800;
		if(interpolatedPosition.x + j < -800)
			j+=800;
			
		canvas.drawBitmap(bitmap, interpolatedPosition.x + j, interpolatedPosition.y, null);
		canvas.drawBitmap(bitmap, interpolatedPosition.x + i, interpolatedPosition.y, null);
	}

}
