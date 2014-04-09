package com.spaceshooter.game;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.se_android.R;
import com.spaceshooter.game.database.HighScoreDataHelper;
import com.spaceshooter.game.database.HighScoreEntry;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.view.GameView;

public class GameActivity extends Activity {

	public static SimpleDateFormat myDateFormat = new SimpleDateFormat("MMM d");
	private HighScoreDataHelper highScoreAccessor;
	
	private GameView gameView;
	private EditText playerName;
	private EditText gameId;
	private EditText score;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// we'll use this to manipulate the list of high scores
		highScoreAccessor = new HighScoreDataHelper(getApplicationContext());
		new BitmapHandler(this);
		
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		double refreshRating = display.getRefreshRate();
		
        gameView = new GameView(this, refreshRating);
        setContentView(gameView);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        getWindow().setFlags(
        	    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
        	    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
//        gameId = (EditText) findViewById(R.id.gameId);
//		playerName = (EditText) findViewById(R.id.playerName);
//		score = (EditText) findViewById(R.id.score);
        
	}
	
	
//	// throw the score at the database
//	private void submitScore() {
//		HighScoreEntry entry = null;
//		try {
//			entry = new HighScoreEntry(playerName.getText().toString(), new Date(), Double.valueOf(score.getText()
//					.toString()), Long.valueOf(gameId.getText().toString()));
//		} catch (NumberFormatException e) {
//			Toast.makeText(getApplicationContext(), R.string.badInput, Toast.LENGTH_LONG).show();
//			return;
//		}
//		
//		
//		double highest = Double.NEGATIVE_INFINITY;
//		List<HighScoreEntry> scores = highScoreAccessor.getByGameId(entry.getGameId());
//		if (scores.size() > 0) {
//			for (HighScoreEntry hse : scores) { // simply look for the highest existing score
//				if (highest < hse.getScore()) {
//					highest = hse.getScore();
//				}
//			}
//		}
//		
//		// give a little "congratulations" message if yours is the highest score so far
//		if (highest < entry.getScore()) { // you beat everyone else (including yourself) on this game!
//			Toast newHighScore = Toast.makeText(getApplicationContext(), R.string.newHighScore, Toast.LENGTH_LONG);
//			newHighScore.show();
//		} else {
//			Toast.makeText(getApplicationContext(), R.string.saving, Toast.LENGTH_LONG).show();
//		}
//		
//		// well, we could probably push this into the HighScoreDataHelper
//		if (highScoreAccessor.alreadyExists(entry)) { // only store a single score per player/gameID combination
//			highScoreAccessor.update(entry);
//		} else {
//			highScoreAccessor.insert(entry);
//		}
///	}

}
