package com.spaceshooter.game.database;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.se_android.R;
import com.spaceshooter.game.engine.GameObjectManager;

public class DatabaseActivity extends Activity {

	private DBAdapter myDb;
	private long id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database_main);
		myDb = new DBAdapter(this);
		openDB();
	}
	
//	@Override
//	public void onBackPressed() {
//		Intent intent = new Intent(this, TabMenu.class);
//		startActivity(intent);
//	}

	@Override
	protected void onDestroy() {
		super.onDestroy();	
		closeDB();
	}

	public void openDB() {
		myDb.open();
		//myDb.deleteAll();
		if(GameObjectManager.getPlayer() != null)
			addHighscore(GameObjectManager.getPlayer().getName(),GameObjectManager.getPlayer().getScore());
	}
	
	public void closeDB() {
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
	
	
	public void addHighscore(String name, int highscore) {
		
		Cursor c = myDb.getRow(1);
		long newId;
		if(c != null && c.getCount() > 0){
			updateHighscore(name, highscore);
			newId = id;
			c = myDb.getRow(newId);
		}else{
			newId = myDb.insertRow(name, highscore);
			c = myDb.getRow(newId);
		}
		
		displayHighscores(c);
	}
	
	public void updateHighscore(String name, int newHighscore){
		Cursor cursor = myDb.getRow(1);
		int oldHighscore = cursor.getInt(DBAdapter.COL_HIGHSCORE);
		id = cursor.getLong(DBAdapter.COL_ROWID);
		if(oldHighscore < newHighscore){
			oldHighscore = newHighscore;
		}
		myDb.updateRow(id, name, oldHighscore);
	}
	
	// Display an entire recordset to the screen.
	private void displayHighscores(Cursor cursor) {
		String messageName = "", messageHighscore = "";
		// populate the message from the cursor

		// Reset cursor to start, checking to see if there's data:
		if (cursor.moveToFirst()) {
			do {
				// Process the data:
				String name = cursor.getString(DBAdapter.COL_NAME);
				int highscore = cursor.getInt(DBAdapter.COL_HIGHSCORE);
			
				// Append data to the message:
				messageName += name
						   +"\n";
				messageHighscore += highscore + "\n"; 
			} while(cursor.moveToNext());
		}
		
		// Close the cursor to avoid a resource leak.
		cursor.close();
		
		displayText(messageName);
		displayHighscore(messageHighscore);
	}
}
