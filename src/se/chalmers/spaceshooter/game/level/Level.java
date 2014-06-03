package se.chalmers.spaceshooter.game.level;

import se.chalmers.spaceshooter.game.CollisionManager;
import se.chalmers.spaceshooter.game.GameObjectManager;
import se.chalmers.spaceshooter.game.GameThread;
import se.chalmers.spaceshooter.game.object.enemy.Asteroid;
import se.chalmers.spaceshooter.game.util.Randomizer;
import se.chalmers.spaceshooter.game.util.Vector2f;
import se.chalmers.spaceshooter.game.view.GameView;
import android.graphics.Canvas;

public class Level {

	private final static int numOfLevels = 3;

	private int timer = 0;
	private int LEVEL_TIME;

	private boolean levelDone = false;
	private GameObjectManager gameObjectManager;
	private EnemyGenerator enemyGen;

	private LevelCreator lc;

	/**
	 * Creates a new level that will last for the given time
	 * 
	 * @param TIME
	 *            the time the level will take in seconds
	 */
	public Level(int time) {
		LEVEL_TIME = time * (int) GameThread.TARGET_TPS;

		gameObjectManager = new GameObjectManager();

		GameObjectManager.getPlayer().setScore(0);
		GameObjectManager.getPlayer().setHp(100);
	}

	public void startLevel(int level) {
		int time = LEVEL_TIME / (int) GameThread.TARGET_TPS;
		enemyGen = new EnemyGenerator(time);
		enemyGen.setUpdate(true);
		lc = new LevelCreator(enemyGen);
		lc.runLevel(level);
	}

	public void tick(float dt) {
		timer++;
		if (timer >= LEVEL_TIME) {
			enemyGen.setUpdate(false);
			if (CollisionManager.getEnemies().size() == 0) {
				levelDone = true;
				timer = 0;
			}
		}
		if (enemyGen.isUpdate() && lc.asteroids) {
			if (GameObjectManager.isSlowTime()) {
				if (timer % 40 * (1 + GameObjectManager.slowtime) == 0) {
					float y = Randomizer.getFloat(2, 440);
					new Asteroid(new Vector2f(GameView.WIDTH, y)).init();
				}
			} else {
				if (timer % 40 == 0) {
					float y = Randomizer.getFloat(2, 440);
					new Asteroid(new Vector2f(GameView.WIDTH, y)).init();
				}
			}
		}

		enemyGen.tick();
		gameObjectManager.tick(dt);
	}
	
	public void draw(Canvas canvas, float interpolation) {
		gameObjectManager.draw(canvas, interpolation);
	}
	
	public boolean isFinished() {
		return levelDone;
	}

	public void setFinished(boolean finished) {
		levelDone = finished;
	}
	
	public static int getNumOfLevels() {
		return numOfLevels;
	}

}
