package com.spaceshooter.game.database;


import com.example.se_android.R;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

public class DatabaseActivity extends Activity {

	private int i=0, x=0;
	DBAdapter myDb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database_main);
		
		openDB();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();	
		closeDB();
	}

	private void openDB() {
		myDb = new DBAdapter(this);
		myDb.open();
		myDb.deleteAll();
		addHighscore(1, 0);
		addHighscore(2, 0);
		addHighscore(3, 0);
		addHighscore(4, 0);
		addHighscore(5, 0);
		updateHighscore(1, 1000);
		updateHighscore(2, 2000);
		updateHighscore(2, 500);
		updateHighscore(3, 3000);
		updateHighscore(4, 4000);
		updateHighscore(5, 5000);
		updateHighscore(5, 4000);
		updateHighscore(1, 444400);
		
	}
	
	private void closeDB() {
		myDb.close();
	}

	private void displayText(String message) {
        TextView textView = (TextView) findViewById(R.id.textView3);
        textView.setText(message);
	}
	
	private void displayHighscore(String message) {
        TextView textView = (TextView) findViewById(R.id.textView4);
        textView.setText(message);
	}
	
	public void onClick_AddHighscore(View v) {

		long newId = myDb.insertRow(i, x);
		i ++;
		x += 1000;
		
		Cursor cursor = myDb.getRow(newId);
		displayHighscores(cursor);
	}
	
	public void addHighscore(int level, int highscore) {
		long newId = myDb.insertRow(level, highscore);
		Cursor cursor = myDb.getRow(newId);
		displayHighscores(cursor);
	}
	
	public void updateHighscore(int level, int newHighscore){
		Cursor cursor = myDb.getRowWithLevel(level);
		int oldHighscore = cursor.getInt(DBAdapter.COL_HIGHSCORE);
		long id = cursor.getLong(DBAdapter.COL_ROWID);
		if(oldHighscore < newHighscore){
			
			oldHighscore = newHighscore;
		}
		myDb.updateRow(id, level, oldHighscore);
	}
	
	public void onClick_ClearAllHighscores(View v) {
		displayText("Cleared level!");
		displayHighscore("Cleared higscore!");
		myDb.deleteAll();
	}

	public void onClick_DisplayHighscores(View v) {
		Cursor cursor = myDb.getAllRows();
		displayHighscores(cursor);
	}
	
	// Display an entire recordset to the screen.
	private void displayHighscores(Cursor cursor) {
		String messageLevel = "", messageHighscore = "";
		// populate the message from the cursor

		// Reset cursor to start, checking to see if there's data:
		if (cursor.moveToFirst()) {
			do {
				// Process the data:
				int level = cursor.getInt(DBAdapter.COL_LEVEL);
				int highscore = cursor.getInt(DBAdapter.COL_HIGHSCORE);
			
				// Append data to the message:
				messageLevel += level
						   +"\n";
				messageHighscore += highscore + "\n"; 
			} while(cursor.moveToNext());
		}
		
		// Close the cursor to avoid a resource leak.
		cursor.close();
		
		displayText(messageLevel);
		displayHighscore(messageHighscore);
	}
}
