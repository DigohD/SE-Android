package com.spaceshooter.game.object.player;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;

public class Player extends DynamicObject{
	
	private Vector2f target = new Vector2f(0,0);
	private boolean update = false;
	
	private int score = 0;
	
	private boolean live = true;
	
	private float step = 20;
	
	public void decreaseStep(float amount){
		step -= amount;
	}
	
	public float getStep(){
		return step;
	}
	
	public Player(Vector2f position) {
		super(position);
		
		this.bitmap = BitmapHandler.loadBitmap("player/ship.png");
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		
		rect = new Rect((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
		
		speed = 15f;
		velocity = new Vector2f(0, speed);
	}
	
	public void setTarget(float x, float y){
		target = new Vector2f(x, y);
		update = true;
	}
	
	@Override
	public void tick(float dt) {
		super.tick(dt);
		if(update){
			Vector2f diff = target.sub(position);
			distance = velocity.mul(dt);
			position = position.add(diff.div(step));
		}
	}

	@Override
	public void draw(Canvas canvas,  float interpolation) {
		super.draw(canvas, interpolation);
	}
	
	public void setLive(boolean live){
		this.live = live;
	}
	
	public boolean isLive(){
		return live;
	}
	
	public void setScore(int value){
		score = score + value;
	}
	
	public int getScore(){
		return score;
	}
	
}
