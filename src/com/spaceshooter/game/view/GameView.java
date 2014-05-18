package com.spaceshooter.game.view;

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
import android.text.InputType;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.spaceshooter.game.GameActivity;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.engine.GameThread;
import com.spaceshooter.game.level.Level;
import com.spaceshooter.game.menu.TabMenu;
import com.spaceshooter.game.net.TCPClient;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.MusicPlayer;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	public static final int WIDTH = 800, HEIGHT = 480;

	private int timer = 0;
	private int musicStartTimer;
	private int levelTime = 30;
	private static int levelID;

	private float scaleX, scaleY;
	private float knobX;
	private float knobY;

	private boolean drawJoystick = true;
	private boolean okToRestartMP = true;
	private boolean displayLevelID = false;
	public boolean gwMusicState;
	
	private Context context;
	private SurfaceHolder holder;
	private GameThread game;
	private Level level;
	private Bitmap joystick, knob;
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
	
	private void init(){
		GameActivity ga = (GameActivity) context;
		gwMusicState = ga.musicState;
		mp = null;
		musicStartTimer = 0;
		
		levelID = 1;
		level = new Level(levelTime);
		level.startLevel(levelID);
		
		game = new GameThread(getHolder(), this);

		joystick = BitmapHandler.loadBitmap("ui/joystick");
		knob = BitmapHandler.loadBitmap("ui/joystickKnob");

		knobX = 40 + (joystick.getWidth() / 2) - (knob.getWidth() / 2);
		knobY = 320 + (joystick.getHeight() / 2) - (knob.getHeight() / 2);
	}

	public void tick(float dt) {
		if (gwMusicState) {
			if (mp == null) {
				musicStartTimer++;
				if (musicStartTimer > 10)
					mp = new MusicPlayer(context);
			}
		}
		
		if(!displayLevelID)
			timer++;
		
		if(timer >= 2*60){
			displayLevelID = true;
			timer = 0;
		}
		
		level.tick(dt);
		
		if(level.isFinished() && GameObjectManager.getPlayer().isLive()){
			timer++;
			if(timer >= 2*60){
				if(levelID >= Level.getNumOfLevels()){
					okToRestartMP = false;
					dialogBox("Game completed!", "Highscore: "
							+ GameObjectManager.getPlayer().getScore(),
							"Restart", "Submit score", "Main Menu");
				}else{
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

	public void draw(Canvas canvas, float interpolation) {
		// clear the screen with black pixels
		canvas.drawColor(Color.BLACK);
		// draw the level
		level.draw(canvas, interpolation);

		if (drawJoystick) {
			canvas.drawBitmap(joystick, 40, 320, null);
			//canvas.drawBitmap(knob, knobX, knobY, null);
		}

		if (!displayLevelID) {
			p.setColor(Color.GREEN);
			p.setTextSize(20);
			canvas.drawText("LEVEL " + levelID, WIDTH / 2, HEIGHT / 2, p);
		}
	}
	
	
	public void textBox(final String title, final String msg,
			final String positiveBtn, final String negativeBtn){
		
		final GameActivity ga = (GameActivity) context;
		ga.runOnUiThread(new Runnable() {
			public void run() {
				Builder builder = new AlertDialog.Builder(context);
				builder.setCancelable(false);
				builder.setTitle(title);
				builder.setMessage(msg);

				// Set up the input
				final EditText input = new EditText(context);
				// Specify the type of input expected;
				input.setInputType(InputType.TYPE_CLASS_TEXT);
				builder.setView(input);

				// Set up the buttons
				builder.setPositiveButton(positiveBtn, new DialogInterface.OnClickListener() { 
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	GameObjectManager.getPlayer().setName(input.getText().toString());
				    	TCPClient tcp = new TCPClient();
				    	if(GameObjectManager.getPlayer().getName() != null){
				    		String[] querys = {"insert", 
									GameObjectManager.getPlayer().getName(), 
									GameObjectManager.getPlayer().getScore() + ""};
							tcp.execute(querys);
				    	}else{
				    		String[] querys = {"insert", 
									"Player", 
									GameObjectManager.getPlayer().getScore() + ""};
							tcp.execute(querys);
				    	}
				    	dialogBox("Score submitted!" ,
								"What do you want to do?",
								"Restart", "Main Menu");
				    }
				});
				builder.setNegativeButton(negativeBtn, new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	GameActivity ga2 = (GameActivity) context;
						stop();
						ga2.onBackPressed2();
				    }
				});

				builder.show();
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
	 * @param negativeBtn
	 *            the text in the negative button
	 */
	private void dialogBox(final String title, final String msg,
			final String positiveBtn, final String neutralBtn,
			final String negativeBtn) {

		
		
		final GameActivity ga = (GameActivity) context;
		ga.runOnUiThread(new Runnable() {
			public void run() {
				game.pause();
				if (gwMusicState) {
					MusicPlayer.stop();
				}

				TabMenu.db.openDB();
				TabMenu.db.addHighscore(GameObjectManager.getPlayer().getName(),GameObjectManager.getPlayer().getScore());
				TabMenu.db.closeDB();
				
				Builder builder = new AlertDialog.Builder(context);
				builder.setCancelable(false);
				builder.setTitle(title);
				builder.setMessage(msg);
				builder.setNegativeButton(negativeBtn,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								GameActivity ga2 = (GameActivity) context;
								stop();
								ga2.onBackPressed2();
							}
						});
				builder.setNeutralButton(neutralBtn,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								textBox("Submit to Global Leaderboard", "Enter name:", "Submit", "Cancel");
							}
						});
				builder.setPositiveButton(positiveBtn,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								resetGameState();
							}
						});
				builder.create().show();
			}
		});

	}
	
	private void dialogBox(final String title, final String msg,
			final String positiveBtn,
			final String negativeBtn) {

		
		
		final GameActivity ga = (GameActivity) context;
		ga.runOnUiThread(new Runnable() {
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
							public void onClick(DialogInterface arg0, int arg1) {
								GameActivity ga2 = (GameActivity) context;
								stop();
								ga2.onBackPressed2();
							}
						});
				builder.setPositiveButton(positiveBtn,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								resetGameState();
							}
						});
				builder.create().show();
			}
		});

	}
	
	private void resetGameState(){
		GameObjectManager.clear();
		levelID = 1;
		level = new Level(levelTime);
		level.startLevel(levelID);
		GameObjectManager.getPlayer().init();
		
		game.resume();
		
		if (gwMusicState) {
			if (mp == null)
				mp = new MusicPlayer(context);
		}
		
		okToRestartMP = true;
		drawJoystick = true;
		displayLevelID = false;
		level.setFinished(false);

		timer = 0;
	}

	public boolean onTouchEvent(MotionEvent event) {
		float eventX = event.getX();
		float eventY = event.getY();

		float nY = scaleY * 1.0f;

		eventX = eventX / scaleX;
		eventY = eventY / nY;

		// System.out.println("Moved!!!! Game!!!");

		// Joystick center point: X = 100, Y = 380
		if (eventX < 210 && eventX > 0 && eventY < 480 && eventY > 300) {
			if (event.getAction() == MotionEvent.ACTION_MOVE) {

				drawJoystick = true;

				if (eventX <= 180 && eventX >= 20 && eventY <= 470
						&& eventY >= 308) {

					knobX = eventX - knob.getWidth() / 2;
					knobY = eventY - knob.getHeight() / 2;

					float dX = eventX - 100;
					float dY = eventY - 380;

					dX = dX / 8;
					dY = dY / 8;

					GameObjectManager.getPlayer().incTargetPos(dX, dY);

				} else {
					GameObjectManager.getPlayer().setUpdate(false);
					knobX = 40 + (joystick.getWidth() / 2)
							- (knob.getWidth() / 2);
					knobY = 320 + (joystick.getHeight() / 2)
							- (knob.getHeight() / 2);
				}

			}

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				drawJoystick = true;

				if (eventX <= 180 && eventX >= 20 && eventY <= 470
						&& eventY >= 308) {

					knobX = eventX - knob.getWidth() / 2;
					knobY = eventY - knob.getHeight() / 2;

					float dX = eventX - 100;
					float dY = eventY - 380;

					dX = dX / 8;
					dY = dY / 8;

					GameObjectManager.getPlayer().incTargetPos(dX, dY);

				} else {
					GameObjectManager.getPlayer().setUpdate(false);
					knobX = 40 + (joystick.getWidth() / 2)
							- (knob.getWidth() / 2);
					knobY = 320 + (joystick.getHeight() / 2)
							- (knob.getHeight() / 2);
				}

				if (eventX > 700)
					game.goInventory();
			}

			if (event.getAction() == MotionEvent.ACTION_UP) {
				//drawJoystick = false;
				knobX = 40 + (joystick.getWidth() / 2) - (knob.getWidth() / 2);
				knobY = 320 + (joystick.getHeight() / 2)
						- (knob.getHeight() / 2);
				GameObjectManager.getPlayer().setUpdate(false);
			}
		}

		// Schedules a repaint.
		invalidate();
		return true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stop();
	}

	public void start() {
		game.start();
		GameObjectManager.getPlayer().init();
	}

	public void stop() {
		GameObjectManager.clear();
		if (gwMusicState) {
			MusicPlayer.stop();
		}
		game.stop();
	}

	public void pause() {
		okToRestartMP = false;
		if (gwMusicState) {
			MusicPlayer.pause();
		}
		game.pause();
	}

	public void resume() {
		okToRestartMP = true;
		if (gwMusicState) {
			MusicPlayer.resume();
		}
		game.resume();
	}

	public static int getLevelID() {
		return levelID;
	}

}
