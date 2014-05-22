package com.spaceshooter.game.view;

import java.util.concurrent.Semaphore;

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
import com.spaceshooter.game.net.TCPClient;
import com.spaceshooter.game.object.loot.HealthPack;
import com.spaceshooter.game.object.loot.Loot;
import com.spaceshooter.game.object.loot.SlowTimePack;
import com.spaceshooter.game.startmenu.TabMenu;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.MusicPlayer;
import com.spaceshooter.game.util.Vector2f;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	public static final int WIDTH = 800, HEIGHT = 480;

	private int timer = 0;
	private int musicStartTimer;
	private int levelTime = 30;
	private static int levelID;

	private float scaleX, scaleY;
	private float knobX;
	private float knobY;

	private boolean okToRestartMP = true;
	private boolean displayLevelID = false;
	public boolean gwMusicState;
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
	
	private void init(){
		GameActivity ga = (GameActivity) context;
		gwMusicState = TabMenu.musicState;
		mp = null;
		musicStartTimer = 0;
		
		levelID = 1;
		level = new Level(levelTime);
		level.startLevel(levelID);
		
		game = new GameThread(getHolder(), this);

		joystick = BitmapHandler.loadBitmap("ui/joystick");
		knob = BitmapHandler.loadBitmap("ui/joystickKnob");
		lootSlot = BitmapHandler.loadBitmap("ui/lootSlot");
		emptySlot =  BitmapHandler.loadBitmap("ui/emptySlot");

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
		canvas.drawColor(Color.BLACK);
		level.draw(canvas, interpolation);
		canvas.drawBitmap(joystick, 40, 320, null);
		canvas.drawBitmap(knob, knobX, knobY, null);
		
		for(int i = 0; i < GameObjectManager.getPlayer().lootArray.length; i++){
			if(GameObjectManager.getPlayer().lootArray[i] == null)
				canvas.drawBitmap(emptySlot, 350+70*i,380, null);
			else canvas.drawBitmap(lootSlot, 350+70*i,380, null);
			if(GameObjectManager.getPlayer().lootArray[i] != null){
				Bitmap bmp = GameObjectManager.getPlayer().lootArray[i].getBitmap();
				int x = lootSlot.getWidth()/2 - bmp.getWidth()/2;
				int y = lootSlot.getHeight()/2 - bmp.getHeight()/2;
				canvas.drawBitmap(bmp, 350+x+70*i, 380+y, null);
			}
		}


		if (!displayLevelID) {
			p.setColor(Color.GREEN);
			p.setTextSize(20);
			canvas.drawText("LEVEL " + levelID, WIDTH / 2 - 20, HEIGHT / 2, p);
		}
	}

	
	public boolean onTouchEvent(MotionEvent event) {
		
	    final int maskedAction = MotionEventCompat.getActionMasked(event); 

		if(maskedAction == MotionEvent.ACTION_POINTER_DOWN ){
			int pointerIndex = MotionEventCompat.getActionIndex(event); 
		    float x = MotionEventCompat.getX(event, pointerIndex); 
		    float y = MotionEventCompat.getY(event, pointerIndex); 
		    float nY = scaleY * 1.0f;
			x = x / scaleX;
			y = y / nY;

		    manageLootSlots(x, y);
		}

		if(maskedAction == MotionEvent.ACTION_MOVE || maskedAction == MotionEvent.ACTION_DOWN){
			int pointerIndex = MotionEventCompat.getActionIndex(event); 
			float x = MotionEventCompat.getX(event, pointerIndex);
			float y = MotionEventCompat.getY(event, pointerIndex);
			
			float nY = scaleY * 1.0f;

			x = x / scaleX;
			y = y / nY;
			
			manageLootSlots(x, y);

			if(x <= 180 && x >= 20 && y <= 470 && y >= 308) {
				knobX = x - knob.getWidth() / 2;
				knobY = y - knob.getHeight() / 2;

				float dX = x - 100;
				float dY = y - 380;

				dX = dX / 8;
				dY = dY / 8;

				GameObjectManager.getPlayer().setTargetVelocity(dX, dY);
			}else{
				GameObjectManager.getPlayer().setUpdate(false);
				knobX = 40 + (joystick.getWidth() / 2) - (knob.getWidth() / 2);
				knobY = 320 + (joystick.getHeight() / 2) - (knob.getHeight() / 2);
			}

		}

		if(event.getAction() == MotionEvent.ACTION_UP) {
			knobX = 40 + (joystick.getWidth() / 2) - (knob.getWidth() / 2);
			knobY = 320 + (joystick.getHeight() / 2) - (knob.getHeight() / 2);
			GameObjectManager.getPlayer().setUpdate(false);
		}

		// Schedules a repaint.
		invalidate();
		return true;
	}
	

	
	private void manageLootSlots(float x, float y){
		if(x >= 350 && x <= 400 && y >= 380 && y <= 430){
			Loot loot = GameObjectManager.getPlayer().lootArray[0];
			if(loot instanceof HealthPack){
				HealthPack hp = (HealthPack) loot;
				GameObjectManager.getPlayer().incHp(hp.getHp());
				GameObjectManager.getPlayer().lootArray[0] = null;
			}
			if(loot instanceof SlowTimePack){
				SlowTimePack st = (SlowTimePack) loot;
				GameObjectManager.setSlowTime(true);
				GameObjectManager.getPlayer().lootArray[0] = null;
			}
		
		}
		if(x >= 420 && x <= 470 && y >= 380 && y <= 430){
			Loot loot = GameObjectManager.getPlayer().lootArray[1];
			if(loot instanceof HealthPack){
				HealthPack hp = (HealthPack) loot;
				GameObjectManager.getPlayer().incHp(hp.getHp());
				GameObjectManager.getPlayer().lootArray[1] = null;
			}
			if(loot instanceof SlowTimePack){
				SlowTimePack st = (SlowTimePack) loot;
				GameObjectManager.setSlowTime(true);
				GameObjectManager.getPlayer().lootArray[1] = null;
			}
		}
		if(x >= 490 && x <= 540 && y >= 380 && y <= 430){
			Loot loot = GameObjectManager.getPlayer().lootArray[2];
			if(loot instanceof HealthPack){
				HealthPack hp = (HealthPack) loot;
				GameObjectManager.getPlayer().incHp(hp.getHp());
				GameObjectManager.getPlayer().lootArray[2] = null;
			}
			if(loot instanceof SlowTimePack){
				SlowTimePack st = (SlowTimePack) loot;
				GameObjectManager.setSlowTime(true);
				GameObjectManager.getPlayer().lootArray[2] = null;
			}
		}
	}
	
	private void resetGameState(){
		GameObjectManager.clear();
		GameObjectManager.setSlowTime(false);
		levelID = 1;
		level = new Level(levelTime);
		level.setFinished(false);
		level.startLevel(levelID);
		GameObjectManager.getPlayer().init();
		GameObjectManager.getPlayer().setPosition(new Vector2f(GameView.WIDTH/2, GameView.HEIGHT/2));
		GameObjectManager.getPlayer().getTargetPosition().set(GameObjectManager.getPlayer().getPosition().x, 
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
		start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//stop();
	}

	public void start() {
		game.start();
		GameObjectManager.getPlayer().init();
		
		//GameObjectManager.getPlayer().setPosition(GameActivity.savedPos);
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
				    	TCPClient tcp = new TCPClient();
				    	if(TabMenu.playerName != null){
				    		String[] querys = {"insert", 
				    				TabMenu.playerName, 
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
	public void dialogBox(final String title, final String msg,
			final String positiveBtn, final String neutralBtn,
			final String negativeBtn) {

		dialogBoxShowing = true;
		
		final GameActivity ga = (GameActivity) context;
		ga.runOnUiThread(new Runnable() {
			public void run() {
				game.pause();
				if (gwMusicState) {
					MusicPlayer.stop();
				}

				TabMenu.db.openDB();
				TabMenu.db.addHighscore(TabMenu.playerName,GameObjectManager.getPlayer().getScore());
				TabMenu.db.closeDB();
				
				Builder builder = new AlertDialog.Builder(context);
				builder.setCancelable(false);
				builder.setTitle(title);
				builder.setMessage(msg);
				builder.setNegativeButton(negativeBtn,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
//								dialogBoxShowing = false;
								GameActivity ga2 = (GameActivity) context;
								stop();
								ga2.onBackPressed2();
							}
						});
				builder.setNeutralButton(neutralBtn,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								dialogBoxShowing = false;
								textBox("Submit to Global Leaderboard", "Enter name:", "Submit", "Cancel");
							}
						});
				builder.setPositiveButton(positiveBtn,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								dialogBoxShowing = false;
								resetGameState();
							}
						});
				builder.create().show();
			}
		});

	}
	
	public void dialogBox(final String title, final String msg,
			final String positiveBtn,
			final String negativeBtn) {

		dialogBoxShowing = true;
		
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
								dialogBoxShowing = false;
								GameActivity ga2 = (GameActivity) context;
								stop();
								ga2.onBackPressed2();
							}
						});
				builder.setPositiveButton(positiveBtn,
						new DialogInterface.OnClickListener() {
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
