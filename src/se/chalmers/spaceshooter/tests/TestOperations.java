package se.chalmers.spaceshooter.tests;
import java.util.ArrayList;
import java.util.List;

import se.chalmers.spaceshooter.engine.CollisionManager;
import se.chalmers.spaceshooter.engine.GameObjectManager;
import se.chalmers.spaceshooter.object.Collideable;
import se.chalmers.spaceshooter.object.Drawable;
import se.chalmers.spaceshooter.object.Tickable;
import se.chalmers.spaceshooter.object.enemy.Enemy;
import se.chalmers.spaceshooter.object.enemy.Mantis;
import se.chalmers.spaceshooter.object.enemy.Predator;
import se.chalmers.spaceshooter.object.particle.ParticleID;
import se.chalmers.spaceshooter.object.particle.emitter.Emitter;
import se.chalmers.spaceshooter.object.particle.emitter.RadialEmitter;
import se.chalmers.spaceshooter.object.projectile.Projectile;
import se.chalmers.spaceshooter.object.projectile.enemy.MantisProj;
import se.chalmers.spaceshooter.object.projectile.enemy.PredatorProj;
import se.chalmers.spaceshooter.object.projectile.player.BluePlasma;
import se.chalmers.spaceshooter.object.projectile.player.GreenPlasma;
import se.chalmers.spaceshooter.object.projectile.player.RedPlasma;
import se.chalmers.spaceshooter.util.Randomizer;
import se.chalmers.spaceshooter.util.Vector2f;
import se.chalmers.spaceshooter.view.GameView;

import junit.framework.TestCase;



public class TestOperations extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testGameObjectManager(){
		GameObjectManager.initLists();
		
		Enemy e = new Predator(new Vector2f(0,0), 40, 40);
		Enemy e2 = new Mantis(new Vector2f(0,0), 40, 40);
		
		GameObjectManager.addGameObject(e);
		GameObjectManager.addGameObject(e2);
		
		Tickable t = GameObjectManager.gettToAdd().get(0);
		Drawable d = GameObjectManager.getdToAdd().get(0);
		Collideable c = CollisionManager.getEnemies().get(0);
		
		Tickable t2 = GameObjectManager.gettToAdd().get(1);
		Drawable d2 = GameObjectManager.getdToAdd().get(1);
		Collideable c2 = CollisionManager.getEnemies().get(1);

		assertEquals(t, e);
		assertEquals(t2, e2);
		assertEquals(d, e);
		assertEquals(d2, e2);
		assertEquals(c, e);
		assertEquals(c2, e2);
		
		GameObjectManager.removeGameObject(e);
		assertEquals(1, GameObjectManager.gettToAdd().size());
		assertEquals(1, GameObjectManager.getdToAdd().size());
		assertEquals(1, CollisionManager.getEnemies().size());
		
		GameObjectManager.removeGameObject(e2);
		assertEquals(0, GameObjectManager.gettToAdd().size());
		assertEquals(0, GameObjectManager.getdToAdd().size());
		assertEquals(0, CollisionManager.getEnemies().size());
		
		Emitter em = new RadialEmitter(20, ParticleID.DUST,  new Vector2f(0f, 0f), new Vector2f(10f, 0f));
		
		GameObjectManager.addGameObject(em);
		assertEquals(GameObjectManager.gettToAdd().get(0), em);
		GameObjectManager.removeGameObject(em);
		assertEquals(0, GameObjectManager.gettToAdd().size());
	}
	
	
	public void testCalculatePlayerScore(){
		Enemy e = new Predator(new Vector2f(0,0), 40, 40);
		// 10 * 1.2^1 * 5
		int test1 = e.calculatePlayerScore(10, 1, 5);
		int test2 = e.calculatePlayerScore(20, 2, 8);
		int test3 = e.calculatePlayerScore(15, 3, 2);
		int test4 = e.calculatePlayerScore(30, 4, 4);
		int test5 = e.calculatePlayerScore(50, 5, 12);
		
		assertEquals(60, test1);
		assertEquals(230, test2);
		assertEquals(51, test3);
		assertEquals(248, test4);
		assertEquals(1492, test5);
	}
	
	public void testVector2fNormalize(){
		for(int i = 0; i < 10000; i++){
			float rX = Randomizer.getFloat(1, 800);
			float rY = Randomizer.getFloat(1, 480);
			Vector2f v = new Vector2f(rX, rY);
			assertEquals(1.0f, v.normalize().length());
		}
	}
	
	public void testVector2fDot(){
		Vector2f v1 = new Vector2f(5, 6);
		Vector2f v2 = new Vector2f(3, 7);
		
		Vector2f v3 = new Vector2f(15, 5);
		Vector2f v4 = new Vector2f(12, 8);
		
		Vector2f v5 = new Vector2f(11, 27);
		Vector2f v6 = new Vector2f(38, 70);
		
		Vector2f v7 = new Vector2f(130, 78);
		Vector2f v8 = new Vector2f(56, 18);
		
		assertEquals(57.0f, v1.dot(v2));
		assertEquals(220.0f, v3.dot(v4));
		assertEquals(2308.0f, v5.dot(v6));
		assertEquals(8684.0f, v7.dot(v8));
	}
	
	public void testProjectileOutOfBound(){
		List<Projectile> projectiles = new ArrayList<Projectile>();
		
		Projectile p1 = new RedPlasma(new Vector2f(GameView.WIDTH,0), 20, 5);
		Projectile p2 = new BluePlasma(new Vector2f(GameView.WIDTH/2, -12), 12, 12);
		Projectile p3 = new GreenPlasma(new Vector2f(GameView.WIDTH/2, GameView.HEIGHT+12), 30, 12);
		Projectile p4 = new MantisProj(new Vector2f(-20,GameView.HEIGHT/2), 20, 2);
		Projectile p5 = new PredatorProj(new Vector2f(-20,GameView.HEIGHT/2), 20, 5);
		
		projectiles.add(p1);
		projectiles.add(p2);
		projectiles.add(p3);
		projectiles.add(p4);
		projectiles.add(p5);
		
		boolean[] projsOutofBound = new boolean[5];
		
		for(int i = 0; i < projsOutofBound.length; i++)
			projsOutofBound[i] = false;
		
		for(int i = 0; i < projectiles.size(); i++){
			Projectile p = projectiles.get(i);
			if(p.isOutOfBound())
				projsOutofBound[i] = true;
		}
		
		int count = 0;
		for(int i = 0; i < projsOutofBound.length; i++)
			if(projsOutofBound[i])
				count++;
			
		assertEquals(projectiles.size(), count);
	}

}
