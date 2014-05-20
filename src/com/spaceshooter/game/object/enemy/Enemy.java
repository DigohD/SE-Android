package com.spaceshooter.game.object.enemy;

import android.graphics.Rect;

import com.spaceshooter.game.engine.CollisionManager;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.object.Collideable;
import com.spaceshooter.game.object.DynamicObject;
import com.spaceshooter.game.object.GameObject;
import com.spaceshooter.game.object.loot.HealthPack;
import com.spaceshooter.game.object.projectile.Projectile;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

public abstract class Enemy extends DynamicObject implements Collideable {
	
	protected Rect rect;
	protected float hp, maxHp, enemyPoints;

	public Enemy(Vector2f position) {
		super(position);
	}
	
	public abstract void death();

	@Override
	public void tick(float dt) {
		if(getX() < -width) live = false;
		rect.set((int)position.x, (int)position.y, (int)position.x + width, (int)position.y + height);
	}
	
	public void init(){
		position.y += GameObjectManager.bg.getPosition().y;
		GameObjectManager.addGameObject(this);
	}

	public int calculatePlayerScore(float points, int level, float combo){
		if(combo == 0)
			return (int) (points * Math.pow(1.2f, level) * 1.0f);
		else
			return (int) (points * Math.pow(1.2f, level) * combo);
	}
	
	@Override
	public void collisionWith(Collideable obj) {
		if(obj instanceof Projectile){
			Projectile p = (Projectile) obj;
			hp = hp - p.getDamage();
			if(hp <= 0){
				GameObjectManager.getPlayer().incEnemyKillCount(1);
				int score = calculatePlayerScore(enemyPoints, GameView.getLevelID(), GameObjectManager.getPlayer().getCombo());
				GameObjectManager.getPlayer().incScore(score);
				death();
				live = false;
				int rn = Randomizer.getInt(0, 10);
				if(rn == 2){
					new HealthPack(position, new Vector2f(-15f, 0), 10);
				}
			}
		}
	}
	
	public Rect getRect(){
		return rect;
	}

}
