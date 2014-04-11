package com.spaceshooter.game.object.enemy;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.engine.CollisionManager;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class Predator extends Enemy{
	
	public Predator(){
		this(new Vector2f(0,0));
	}

	public Predator(Vector2f position) {
		super(position);
		this.bitmap = BitmapHandler.loadBitmap("enemies/predator.png");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		
		speedX = 0;
		speedY = 15f;
		
		velocity = new Vector2f(speedX, speedY);
		
		GameObjectManager.addGameObject(this);
		CollisionManager.addEnemy(this);
	}
	
	@Override
	public void tick(float dt) {
		super.tick(dt);
		distance = velocity.mul(dt);
		position = position.add(distance);
		
//		if(getY() > GameView.height){
//			GameObjectManager.removeGameObject(this);
//			CollisionManager.removeEnemy(this);
//		}
	}
	
	@Override
	public void draw(Canvas canvas,  float interpolation) {
		super.draw(canvas, interpolation);
	}

	

}
