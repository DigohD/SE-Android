package com.spaceshooter.game.object.enemy;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.spaceshooter.game.util.Color;

import com.spaceshooter.game.engine.CollisionManager;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.object.Collideable;
import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.player.Player;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public abstract class Enemy extends DynamicObject implements Collideable {
	
	protected float hp, maxHp;
	
	protected static List<Vector2f> pathNodes = new ArrayList<Vector2f>();

	public Enemy(Vector2f position) {
		super(position);

	}

	@Override
	public void tick(float dt) {
		super.tick(dt);
	}
	
	/**
	 * Adds the enemy to the gameobject list so that it will be updated and drawn.
	 * The enemy also gets added to the list of enemies in CollisionManager so that it
	 * can be checked for collisions
	 */
	public void addToManagerLists(){
		GameObjectManager.addGameObject(this);
		CollisionManager.addEnemy(this);
	}

	@Override
	public void draw(Canvas canvas, float interpolation) {
		super.draw(canvas, interpolation);
	}

	@Override
	public void collisionWith(GameObject obj) {
		if(obj instanceof Projectile){
			Projectile p = (Projectile) obj;
			hp = hp - p.getDamage();
			if(hp <= 0){
				GameObjectManager.getPlayer().setScore((int) (maxHp * 2.5f));
				death();
				live = false;
			}
			p.death();
			p.setLive(false);
		}
	}
	
	protected void scanPathNodes(String path){
		Bitmap bmp = BitmapHandler.loadBitmap(path);
		int w = bmp.getWidth();
		int h = bmp.getHeight();
		int wRatio = GameView.WIDTH / w;
		int hRatio = GameView.HEIGHT / h;
		int[] pixels = new int[w*h];
		bmp.getPixels(pixels, 0, w, 0, 0, w, h);
		bmp.recycle();
		
		for(int y = 0; y < h; y++)
			for(int x = 0; x < w; x++)
				if(pixels[x + (y*w)] == Color.RED)
					pathNodes.add(new Vector2f(x*wRatio, y*hRatio));
	}
	
	public abstract void death();

}
