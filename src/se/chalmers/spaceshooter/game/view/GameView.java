package se.chalmers.spaceshooter.game.view;

import se.chalmers.spaceshooter.game.GameActivity;
import se.chalmers.spaceshooter.game.GameObjectManager;
import se.chalmers.spaceshooter.game.GameThread;
import se.chalmers.spaceshooter.game.level.Level;
import se.chalmers.spaceshooter.game.object.loot.HealthPack;
import se.chalmers.spaceshooter.game.object.loot.Loot;
import se.chalmers.spaceshooter.game.object.loot.SlowTimePack;
import se.chalmers.spaceshooter.game.util.BitmapLoader;
import se.chalmers.spaceshooter.game.util.MusicPlayer;
import se.chalmers.spaceshooter.game.util.Vector2f;
import se.chalmers.spaceshooter.leaderboard.TCPClient;
import se.chalmers.spaceshooter.menu.TabMenu;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	public static final int WIDTH = 800, HEIGHT = 480;

	//boolean to check if surface has been destroyed which is useful when we want
	//to return to the app after a phone call as example.
	public boolean sdestroyed = false;
	//a timer used for various things such as when and how long to display what level the player is on
	private int timer = 0;
	//timer for the musicplayer, used to ensure the musicplayer only get started once
	private int musicStartTimer;
	//the time we want the first level to take, might be changed in the levelcreator
	private int levelTime = 30;
	//offset for the loot slots
	private int slotOffset = 600;

	//indicator for what level the player is currently on
	private static int levelID;
	//scaling variables used to ensure the game scales properly depending on the phones resolution
	private float scaleX, scaleY;
	//positions for the joystick knob
	private float knobX, knobY;

	//boolean to make sure the musicplayer dont get restarted when it is already on
	private boolean okToRestartMP = true;
	//boolean to check wether we should draw what level the player is on
	private boolean displayLevelID = false;
	//boolean to check if the music should be played or not
	public boolean gwMusicState;

	//boolean to check wheter a dialogbox is currently showing, useful when we resume the game after for example
	//a phone call
	public static boolean dialogBoxShowing = false;
	
	
	private Context context;
	private SurfaceHolder holder;
	private GameThread game;
	private Level level;
	private Bitmap joystick, knob, lootSlot, emptySlot;
	private MusicPlayer mp;
	private Paint p = new Paint();

	public GameView(Context context) {
		super(context);
		this.context = context;

		init();

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		scaleX = ((float) size.x / (float) WIDTH);
		scaleY = ((float) size.y / (float) HEIGHT);

		setLayerType(View.LAYER_TYPE_HARDWARE, null);
		holder = getHolder();
		holder.setFormat(PixelFormat.RGBA_8888);
		holder.setFixedSize(WIDTH, HEIGHT);

		holder.addCallback(this);
	}
	

	private void init() {

		gwMusicState = TabMenu.musicState;
		mp = null;
		musicStartTimer = 0;

		levelID = 1;
		level = new Level(levelTime);
		level.startLevel(levelID);

		game = new GameThread(getHolder(), this);

		joystick = BitmapLoader.loadBitmap("ui/joystick");
		knob = BitmapLoader.loadBitmap("ui/joystickKnob");
		lootSlot = BitmapLoader.loadBitmap("ui/lootSlot");
		emptySlot = BitmapLoader.loadBitmap("ui/emptySlot");

		knobX = 40 + (joystick.getWidth() / 2) - (knob.getWidth() / 2);
		knobY = 320 + (joystick.getHeight() / 2) - (knob.getHeight() / 2);

	}

	/**
	 * Updates the gamelogic in the levels and ensures that levels
	 * keep coming continuosly as long as there are levels to generate
	 * @param dt the timestep, eg the time it takes to complete one frame
	 */
	public void tick(float dt) {
		if (gwMusicState) {
			if (mp == null) {
				musicStartTimer++;
				if (musicStartTimer > 10)
					mp = new MusicPlayer(context);
			}
		}

		if (!displayLevelID)
			timer++;

		if (timer >= 2 * 60) {
			displayLevelID = true;
			timer = 0;
		}

		level.tick(dt);

		if (level.isFinished() && GameObjectManager.getPlayer().isLive()) {
			timer++;
			if (timer >= 2 * 60) {
				if (levelID >= Level.getNumOfLevels()) {
					okToRestartMP = false;
					dialogBox("Game completed!", "Highscore: "
							+ GameObjectManager.getPlayer().getScore(),
							"Restart", "Submit score", "Main Menu");
				} else {
					levelID++;
					level.startLevel(levelID);
					level.setFinished(false);
					displayLevelID = false;
					timer = 0;
				}
			}
		}

		if (!GameObjectManager.getPlayer().isLive()) {
			okToRestartMP = false;
			timer++;
			if (timer >= 2 * 60) {
				dialogBox("You died! You made it to level " + (levelID),
						"Highscore: "
								+ GameObjectManager.getPlayer().getScore(),
						"Restart", "Submit score", "Main Menu");
				timer = 0;
			}
		}

		if (gwMusicState) {
			if (mp != null && MusicPlayer.isDone() && okToRestartMP)
				mp = new MusicPlayer(context);
		}

	}

	/**
	 * Draws everything in the game, like everything in the level, the joystick and so on
	 * @param canvas
	 * @param interpolation
	 */
	public void draw(Canvas canvas, float interpolation) {
		//clear the background
		canvas.drawColor(Color.BLACK);
		//draw everything in the level
		level.draw(canvas, interpolation);
		
		//draw the joystick and the joystick knob
		canvas.drawBitmap(joystick, 40, 320, null);
		canvas.drawBitmap(knob, knobX, knobY, null);

		//draw the lootslots
		for (int i = 0; i < GameObjectManager.getPlayer().lootArray.length; i++) {
			if (GameObjectManager.getPlayer().lootArray[i] == null)
				canvas.drawBitmap(emptySlot, slotOffset + 70 * i, 380, null);
			else canvas.drawBitmap(lootSlot, slotOffset + 70 * i, 380, null);
			if (GameObjectManager.getPlayer().lootArray[i] != null) {
				Bitmap bmp = GameObjectManager.getPlayer().lootArray[i].getBitmap();
				int x = lootSlot.getWidth() / 2 - bmp.getWidth() / 2;
				int y = lootSlot.getHeight() / 2 - bmp.getHeight() / 2;
				canvas.drawBitmap(bmp, slotOffset + x + 70 * i, 380 + y, null);
			}
		}

		//draw which level the player is on if displayLevelID is false
		if (!displayLevelID) {
			p.setColor(Color.GREEN);
			p.setTextSize(20);
			canvas.drawText("LEVEL " + levelID, WIDTH / 2 - 20, HEIGHT / 2, p);
		}
	}

	/**
	 * Handle the touch events. There is support for two different touches:
	 * STeering the player ship with the joystick and applying collected loot 
	 * from one of the lootslots.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		final int maskedAction = MotionEventCompat.getActionMasked(event);

		if (maskedAction == MotionEvent.ACTION_POINTER_DOWN) {
			int pointerIndex = MotionEventCompat.getActionIndex(event);
			float x = MotionEventCompat.getX(event, pointerIndex);
			float y = MotionEventCompat.getY(event, pointerIndex);
			float nY = scaleY * 1.0f;
			x = x / scaleX;
			y = y / nY;

			manageLootSlots(x, y);
		}

		if (maskedAction == MotionEvent.ACTION_MOVE
				|| maskedAction == MotionEvent.ACTION_DOWN) {
			int pointerIndex = MotionEventCompat.getActionIndex(event);
			float x = MotionEventCompat.getX(event, pointerIndex);
			float y = MotionEventCompat.getY(event, pointerIndex);

			float nY = scaleY * 1.0f;

			x = x / scaleX;
			y = y / nY;

			manageLootSlots(x, y);

			//define the joystick touch area and set the targetVelocity to the touch event
			//with proper scaling applied
			if (x <= 180 && x >= 20 && y <= 470 && y >= 308) {
				knobX = x - knob.getWidth() / 2;
				knobY = y - knob.getHeight() / 2;

				float dX = x - 100;
				float dY = y - 380;

				dX = dX / 8;
				dY = dY / 8;

				GameObjectManager.getPlayer().setTargetVelocity(dX, dY);
			} else {
				GameObjectManager.getPlayer().setUpdate(false);
				knobX = 40 + (joystick.getWidth() / 2) - (knob.getWidth() / 2);
				knobY = 320 + (joystick.getHeight() / 2)
						- (knob.getHeight() / 2);
			}

		}

		if (event.getAction() == MotionEvent.ACTION_UP) {
			knobX = 40 + (joystick.getWidth() / 2) - (knob.getWidth() / 2);
			knobY = 320 + (joystick.getHeight() / 2) - (knob.getHeight() / 2);
			GameObjectManager.getPlayer().setUpdate(false);
		}

		// Schedules a repaint.
		invalidate();
		return true;
	}
	
	/**
	 * Handles what will happen when player press one of the three lootslots
	 * @param x x position of the touchevent area
	 * @param y y position of the touchevent area
	 */
	private void manageLootSlots(float x, float y) {
		if (x >= slotOffset && x <= slotOffset + 50 && y >= 380 && y <= 430) {
			Loot loot = GameObjectManager.getPlayer().lootArray[0];
			if (loot instanceof HealthPack) {
				HealthPack hp = (HealthPack) loot;
				GameObjectManager.getPlayer().incHp(hp.getHp());
				GameObjectManager.getPlayer().lootArray[0] = null;
			}
			if (loot instanceof SlowTimePack) {
				GameObjectManager.setSlowTime(true);
				GameObjectManager.getPlayer().lootArray[0] = null;
			}

		}
		if (x >= slotOffset + 70 && x <= slotOffset + 70 + 50 && y >= 380
				&& y <= 430) {
			Loot loot = GameObjectManager.getPlayer().lootArray[1];
			if (loot instanceof HealthPack) {
				HealthPack hp = (HealthPack) loot;
				GameObjectManager.getPlayer().incHp(hp.getHp());
				GameObjectManager.getPlayer().lootArray[1] = null;
			}
			if (loot instanceof SlowTimePack) {
				GameObjectManager.setSlowTime(true);
				GameObjectManager.getPlayer().lootArray[1] = null;
			}
		}
		if (x >= slotOffset + 70 * 2 && x <= slotOffset + (70 * 2) + 50
				&& y >= 380 && y <= 430) {
			Loot loot = GameObjectManager.getPlayer().lootArray[2];
			if (loot instanceof HealthPack) {
				HealthPack hp = (HealthPack) loot;
				GameObjectManager.getPlayer().incHp(hp.getHp());
				GameObjectManager.getPlayer().lootArray[2] = null;
			}
			if (loot instanceof SlowTimePack) {
				GameObjectManager.setSlowTime(true);
				GameObjectManager.getPlayer().lootArray[2] = null;
			}
		}
	}

	/**
	 * Reset the state of the game to its initial state
	 */
	private void resetGameState() {
		GameObjectManager.clear();
		GameObjectManager.setSlowTime(false);
		levelID = 1;
		level = new Level(levelTime);
		level.setFinished(false);
		level.startLevel(levelID);
		GameObjectManager.getPlayer().init();
		GameObjectManager.getPlayer().setPosition(
				new Vector2f(GameView.WIDTH / 2, GameView.HEIGHT / 2));
		GameObjectManager
				.getPlayer()
				.getTargetPosition()
				.set(GameObjectManager.getPlayer().getPosition().x,
						GameObjectManager.getPlayer().getPosition().y);

		okToRestartMP = true;
		displayLevelID = false;
		timer = 0;

		game.resume();

		if (gwMusicState) {
			if (mp == null)
				mp = new MusicPlayer(context);
		}

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		sdestroyed = false;
		start();
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		sdestroyed = true;
	}
	
	/**
	 * Pauses the game
	 */
	public void pause() {
		okToRestartMP = false;
		if (gwMusicState) {
			MusicPlayer.pause();
		}
		game.pause();
	}

	/**
	 * Resumes the game
	 */
	public void resume() {
		okToRestartMP = true;
		if (gwMusicState) {
			MusicPlayer.resume();
		}
		game.resume();
	}

	/**
	 * Starts the game
	 */
	public void start() {
		game.start();
		GameObjectManager.getPlayer().init();
	}

	/**
	 * Stops the game
	 */
	public void stop() {
		GameObjectManager.clear();
		if (gwMusicState) {
			MusicPlayer.stop();
		}
		game.stop();
	}


	/**
	 * Creates a dialogbox on the screen with a title, message and two buttons
	 * one button for yes and one for no
	 * 
	 * @param title
	 *            the title of the box, eg "Level completed!" or "You died"
	 * @param msg
	 *            the message in the box, eg "Restart the level?"
	 * @param positiveBtn
	 *            the text in the positive button
	 * @param negativeBtn
	 *            the text in the negative button
	 */
	public void dialogBox(final String title, final String msg,
			final String positiveBtn, final String negativeBtn) {

		dialogBoxShowing = true;

		final GameActivity ga = (GameActivity) context;
		ga.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				game.pause();
				if (gwMusicState) {
					MusicPlayer.stop();
				}

				Builder builder = new AlertDialog.Builder(context);
				builder.setCancelable(false);
				builder.setTitle(title);
				builder.setMessage(msg);
				builder.setNegativeButton(negativeBtn,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {

								GameActivity ga2 = (GameActivity) context;
								stop();
								ga2.onBackPressed2();
							}
						});
				builder.setPositiveButton(positiveBtn,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								dialogBoxShowing = false;
								resetGameState();
							}
						});
				builder.create().show();
			}
		});

	}

	/**
	 * Creates a dialogbox on the screen with a title, message and two buttons
	 * one button for yes and one for no
	 * 
	 * @param title
	 *            the title of the box, eg "Level completed!" or "You died"
	 * @param msg
	 *            the message in the box, eg "Restart the level?"
	 * @param positiveBtn
	 *            the text in the positive button
	 * @param neutralBtn
	 *            the text in the neutral button
	 * @param negativeBtn
	 *            the text in the negative button
	 */
	public void dialogBox(final String title, final String msg,
			final String positiveBtn, final String neutralBtn,
			final String negativeBtn) {

		dialogBoxShowing = true;

		final GameActivity ga = (GameActivity) context;
		ga.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				game.pause();
				if (gwMusicState) {
					MusicPlayer.stop();
				}

				TabMenu.db.openDB();
				TabMenu.db.addHighscore(TabMenu.playerName, GameObjectManager
						.getPlayer().getScore());
				TabMenu.db.closeDB();

				Builder builder = new AlertDialog.Builder(context);
				builder.setCancelable(false);
				builder.setTitle(title);
				builder.setMessage(msg);
				builder.setNegativeButton(negativeBtn,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {

								GameActivity ga2 = (GameActivity) context;
								stop();
								ga2.onBackPressed2();
							}
						});
				builder.setNeutralButton(neutralBtn,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								dialogBoxShowing = false;
								TCPClient tcp = new TCPClient();
								if (TabMenu.playerName != null) {
									String[] querys = {
											"insert",
											TabMenu.playerName,
											GameObjectManager.getPlayer()
													.getScore() + "" };
									tcp.execute(querys);
								} else {
									String[] querys = {
											"insert",
											"Player",
											GameObjectManager.getPlayer()
													.getScore() + "" };
									tcp.execute(querys);
								}
								dialogBox("Score submitted!",
										"What do you want to do?", "Restart",
										"Main Menu");
							}
						});
				builder.setPositiveButton(positiveBtn,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								dialogBoxShowing = false;
								resetGameState();
							}
						});
				builder.create().show();
			}
		});

	}
	
	public static int getLevelID() {
		return levelID;
	}

}
