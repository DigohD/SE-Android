package com.spaceshooter.game.object.enemy;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public class Locust extends Enemy{

	public Locust(){
		this(new Vector2f(0,0));
	}

	public Locust(Vector2f position) {
		super(position);
		this.bitmap = BitmapHandler.loadBitmap("enemies/locust");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		
		speedX = -15f;
		speedY = 0;
		
		velocity = new Vector2f(speedX, speedY);
	}
	
	boolean rotate = false;
	boolean flag = true;
	float angle = 0;

	Vector2f rotationPoint = new Vector2f(800/2, 480/2 - 100);
	
	@Override
	public void tick(float dt) {
		super.tick(dt);
		
		if(!rotate){
			distance = velocity.mul(dt);
			position = position.add(distance);
		}
		
		
		if(position.x <= 800/2 - 50 && flag){
			rotate = true;
		}
		
		if(rotate){
			angle++;
			float r = Randomizer.getFloat(25, 65);
			if(angle >= r) {
				angle = r;
//				rotate = false;
//				flag = false;
			}
			
			position = position.rotate(rotationPoint, angle*dt);
			
		}
		

	}
	
	@Override
	public void draw(Canvas canvas,  float interpolation) {
		super.draw(canvas, interpolation);
	}

	@Override
	public void death() {
		// TODO Auto-generated method stub
		
	}

}
