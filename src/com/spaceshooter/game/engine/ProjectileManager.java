package com.spaceshooter.game.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import android.graphics.Canvas;

import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.view.GameView;

public class ProjectileManager {
	
	public static List<Projectile> playerProjectiles = new ArrayList<Projectile>();
	public static List<Projectile> pToAdd = new ArrayList<Projectile>();
	
	public static List<Projectile> enemyProjectiles = new ArrayList<Projectile>();
	public static List<Projectile> eToAdd = new ArrayList<Projectile>();
	
//	private static Semaphore mutex = new Semaphore(1);
	
	private int timer = 0;
	
	public static void addEnemyProjectile(Projectile proj){
		eToAdd.add(proj);
	}
	
	public static void removeEnemyProjectile(Projectile proj){
		proj.getBitmap().recycle();
		proj.death();
		enemyProjectiles.remove(proj);
	}
	
	public static void addPlayerProjectile(Projectile proj){
		pToAdd.add(proj);
	}
	
	public static void removePlayerProjectile(Projectile proj){
		proj.getBitmap().recycle();
		proj.death();
		playerProjectiles.remove(proj);
	}
	
	public static void clear(){
		playerProjectiles.clear();
		enemyProjectiles.clear();
		pToAdd.clear();
		eToAdd.clear();
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
		
		for(Projectile p : pToAdd)
			playerProjectiles.add(p);
		for(Projectile p : eToAdd)
			enemyProjectiles.add(p);
		
		pToAdd.clear();
		eToAdd.clear();
		
		removeDeadProjectiles();
		
		for(Projectile p : playerProjectiles)
			p.tick(dt);
		for(Projectile p : enemyProjectiles)
			p.tick(dt);
	}
	
	public void draw(Canvas canvas, float interpolation){
		for(Projectile p : playerProjectiles)
			p.draw(canvas, interpolation);
		for(Projectile p : enemyProjectiles)
			p.draw(canvas, interpolation);
	}

}
