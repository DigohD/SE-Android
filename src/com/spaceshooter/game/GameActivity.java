package com.spaceshooter.game;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.se_android.R;
import com.spaceshooter.game.database.HighScoreDataHelper;
import com.spaceshooter.game.database.HighScoreEntry;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.SoundPlayer;
import com.spaceshooter.game.view.GameView;

public class GameActivity extends Activity {

	public static SimpleDateFormat myDateFormat = new SimpleDateFormat("MMM d");
	private HighScoreDataHelper highScoreAccessor;

	private Button submitButton;
	private Button showScoresButton;

	private EditText playerName;
	private EditText gameId;
	private EditText score;

	private GameView gameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// we'll use this to manipulate the list of high scores

//		highScoreAccessor = new HighScoreDataHelper(getApplicationContext());
		
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        getWindow().setFlags(
        	    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
        	    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        getWindow().setFormat(PixelFormat.RGBA_8888);
        
        new BitmapHandler(this);
		
        gameView = new GameView(this);
        setContentView(gameView);
        
        
        new SoundPlayer(this);
        
    	// we'll use this to manipulate the list of high scores
// 		highScoreAccessor = new HighScoreDataHelper(getApplicationContext());
//
// 		submitButton = (Button) findViewById(R.id.submitButton);
// 		submitButton.setOnClickListener(new View.OnClickListener() {
// 			@Override
// 			public void onClick(View v) {
// 				submitScore();
// 			}
// 		});
//
// 		showScoresButton = (Button) findViewById(R.id.showScoresButton);
// 		showScoresButton.setOnClickListener(new View.OnClickListener() {
// 			@Override
// 			public void onClick(View v) {
// 				Intent highScoreListActivity = new Intent(getBaseContext(), HighScoreList.class);
// 				highScoreListActivity.putExtra(HighScoreList.EXTRA_GAME_ID, gameId.getText().toString());
// 				startActivity(highScoreListActivity);
// 			}
// 		});
//
// 		gameId = (EditText) findViewById(R.id.gameId);
// 		playerName = (EditText) findViewById(R.id.playerName);
// 		score = (EditText) findViewById(R.id.score);
//        

	}
	
	private void exitDialog(){
		gameView.pause();
		Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle("Quit Game?");
		builder.setMessage("Return to main menu?");
		builder.setNegativeButton("Cancel", new OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                gameView.resume();
            }});
		builder.setPositiveButton("Ok", new OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            	gameView.stop();
                GameActivity.super.onBackPressed();
            }});
		
		builder.create().show();
	}
	
	public void onBackPressed2(){
		 GameActivity.super.onBackPressed();
	}
	
	@Override
	public void onBackPressed() {
		exitDialog();
	}
	
//	public void onStop(){
//		super.onStop();
//		gameView.pause();
//	}
//
//	 public void onPause(){
//		 super.onPause();
//		 gameView.pause();
//	 }
//	
//	 public void onResume(){
//		 super.onResume();
//		 gameView.resume();
//	 }

	// throw the score at the database
	private void submitScore() {
		HighScoreEntry entry = null;
		try {
			entry = new HighScoreEntry(playerName.getText().toString(),
					new Date(), Double.valueOf(score.getText().toString()),
					Long.valueOf(gameId.getText().toString()));
		} catch (NumberFormatException e) {
			Toast.makeText(getApplicationContext(), R.string.badInput,
					Toast.LENGTH_LONG).show();
			return;
		}

		double highest = Double.NEGATIVE_INFINITY;
		List<HighScoreEntry> scores = highScoreAccessor.getByGameId(entry
				.getGameId());
		if (scores.size() > 0) {
			for (HighScoreEntry hse : scores) { // simply look for the highest
												// existing score
				if (highest < hse.getScore()) {
					highest = hse.getScore();
				}
			}
		}

		// give a little "congratulations" message if yours is the highest score
		// so far
		if (highest < entry.getScore()) { // you beat everyone else (including
											// yourself) on this game!
			Toast newHighScore = Toast.makeText(getApplicationContext(),
					R.string.newHighScore, Toast.LENGTH_LONG);
			newHighScore.show();
		} else {
			Toast.makeText(getApplicationContext(), R.string.saving,
					Toast.LENGTH_LONG).show();
		}

		// well, we could probably push this into the HighScoreDataHelper
		if (highScoreAccessor.alreadyExists(entry)) { // only store a single
														// score per
														// player/gameID
														// combination
			highScoreAccessor.update(entry);
		} else {
			highScoreAccessor.insert(entry);
		}
	}

}
