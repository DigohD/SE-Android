package com.spaceshooter.game.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import android.graphics.Canvas;

import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.view.GameView;

public class ProjectileManager {
	
	public static List<Projectile> playerProjectiles = new ArrayList<Projectile>();
	public static List<Projectile> enemyProjectiles = new ArrayList<Projectile>();
	
	private static Semaphore mutex = new Semaphore(1);
	
	private int timer = 0;
	
	public static void addEnemyProjectile(Projectile proj){
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		enemyProjectiles.add(proj);
		mutex.release();
	}
	
	public static void removeEnemyProjectile(Projectile proj){
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		proj.getBitmap().recycle();
		enemyProjectiles.remove(proj);
		mutex.release();
	}
	
	public static void addPlayerProjectile(Projectile proj){
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		playerProjectiles.add(proj);
		mutex.release();
	}
	
	public static void removePlayerProjectile(Projectile proj){
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		proj.getBitmap().recycle();
		playerProjectiles.remove(proj);
		mutex.release();
	}
	
	private void removeDeadProjectiles(){
		for(int i = 0; i < playerProjectiles.size(); i++){
			Projectile p = playerProjectiles.get(i);
			if(!p.isLive())
				removePlayerProjectile(p);
		}
		
		for(int i = 0; i < enemyProjectiles.size(); i++){
			Projectile p = enemyProjectiles.get(i);
			if(!p.isLive())
				removeEnemyProjectile(p);
		}		
	}
	
	public void tick(float dt){
		if(timer < 7500) timer++;
		else timer = 0;
		
		if(timer % 5 == 0)
			removeDeadProjectiles();
		
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(Projectile p : playerProjectiles)
			p.tick(dt);
		for(Projectile p : enemyProjectiles)
			p.tick(dt);
		mutex.release();
	}
	
	public void draw(Canvas canvas, float interpolation){
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(Projectile p : playerProjectiles)
			p.draw(canvas, interpolation);
		for(Projectile p : enemyProjectiles)
			p.draw(canvas, interpolation);
		mutex.release();
	}

}
