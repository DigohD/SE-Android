package com.spaceshooter.game.object.weapon;

import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.Tickable;
import com.spaceshooter.game.util.Vector2f;

public abstract class Gun extends GameObject implements Tickable{

	protected Vector2f gunPos;
	protected float spread;
	protected Vector2f pVelocity;
	protected int reload, reloadTimer;
	
	public Gun(Vector2f gunPos){
		super(gunPos);
		this.gunPos = gunPos;
		reloadTimer = 0;
		init();
	}

	@Override
	public void tick(float dt) {
		reloadTimer++;
		if(reloadTimer > reload){
			fire();
			reloadTimer = 0;
		}
	}
	
	public abstract void fire();
	public abstract void init();
	
	
}
