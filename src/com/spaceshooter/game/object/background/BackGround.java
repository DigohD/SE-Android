package com.spaceshooter.game.object.background;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;

public class BackGround extends DynamicObject{

	private float offset1 = 0;
	private float offset2 = 0;
	
	private Bitmap nebula;
	
	public BackGround() {
		super(new Vector2f(0, 0));
		
		this.bitmap = BitmapHandler.loadBitmap("bg/bg");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		
		nebula = BitmapHandler.loadBitmap("bg/nebula");

		velocity = new Vector2f(-5f, 0f);
		
		GameObjectManager.addGameObject(this);
	}
	
	@Override
	public void tick(float dt) {
		move(dt);
	}
	
	@Override
	public void draw(Canvas canvas,  float interpolation) {
		interpolate(interpolation);
		
		if(interpolatedPosition.x + offset1 < 0)
			offset1+=800;
		if(interpolatedPosition.x + offset2 < -800)
			offset2+=800;
			
		canvas.drawBitmap(bitmap, interpolatedPosition.x + offset2, interpolatedPosition.y, null);
		canvas.drawBitmap(bitmap, interpolatedPosition.x + offset1, interpolatedPosition.y, null);
	}

}
