package com.spaceshooter.game.object.enemy;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.object.projectile.RedPlasma;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;

public class Locust extends Enemy{
	
	private Vector2f targetPos = new Vector2f(0,0);
	private Vector2f diff = new Vector2f(0,0);
	int timer = 0;
	int steps = 20;
	private int reload;
	
	private static int nodeIndex = 0;
	
	//private static List<Vector2f> pathNodes = new ArrayList<Vector2f>();
	
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
		targetPos = pathNodes.get(nodeIndex);
		nodeIndex++;
		diff = targetPos.sub(position);
	}
	
	
	private void moveToTarget(float dt){
		boolean check1 = (int)position.x == (int)targetPos.x && (int)position.y == (int)targetPos.y;
		boolean check2 = (targetPos.x - position.x) <= 0.1f && (targetPos.y - position.y) <= 0.1f;
		boolean reachedTarget = check1 || check2;
		
		if(!reachedTarget){
			diff = targetPos.sub(position);
			position = position.add(diff.div(steps));
		}else{
			move(dt);
		}
		
		if(position.x <= targetPos.x){
			move(dt);
		}
	}
	
	private void move(float dt){
		distance = velocity.mul(dt);
		position = position.add(distance);
	}
	
	@Override
	public void tick(float dt) {
		timer++;
		super.tick(dt);
		move(dt);
//		moveToTarget(dt);
	}
	
	@Override
	public void draw(Canvas canvas,  float interpolation) {
		super.draw(canvas, interpolation);
	}

	@Override
	public void death() {
		
		
	}

}
