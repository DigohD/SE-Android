package com.spaceshooter.game.object.enemy;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;

public class Locust extends Enemy{
	
	private Vector2f targetPos = new Vector2f(0,0);
	private Vector2f diff = new Vector2f(0,0);
	int timer = 0;
	int steps = 10;
	
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
		scanPathNodes("enemies/enemyPathNodes");
		int rnd = Randomizer.getInt(0, pathNodes.size());
		targetPos = pathNodes.get(rnd);
		diff = targetPos.sub(position);
	}

	private void moveToTarget(){
		boolean test1 = (int)position.x == (int)targetPos.x && (int)position.y == (int)targetPos.y;
		boolean test2 = (targetPos.x - position.x) <= 0.1f && (targetPos.y - position.y) <= 0.1f;
		
		if(test1 || test2){
			int rnd = Randomizer.getInt(0, pathNodes.size());
			targetPos = pathNodes.get(rnd);
		}
	
		diff = targetPos.sub(position);
		position = position.add(diff.div(steps));

	}
	
	private void move(float dt){
		distance = velocity.mul(dt);
		position = position.add(distance);
	}
	
	@Override
	public void tick(float dt) {
		timer++;
		super.tick(dt);
	
		if(timer >= 60*3){
			moveToTarget();
		}else{
			move(dt);
		}
	}
	
	@Override
	public void draw(Canvas canvas,  float interpolation) {
		super.draw(canvas, interpolation);
	}

	@Override
	public void death() {
		
		
	}

}
