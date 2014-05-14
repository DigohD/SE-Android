package com.spaceshooter.game.database;

import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;

import com.example.se_android.R;
import com.spaceshooter.game.menu.TabMenu;

public class DatabaseActivity {

	private DBAdapter myDb;
	private long id;
	private Context context;

	public DatabaseActivity(Context context) {
		this.context = context;
		myDb = new DBAdapter(context);
	}

	public void openDB() {
		myDb.open();
		// myDb.deleteAll();
		// if(GameObjectManager.getPlayer() != null)
		// addHighscore(GameObjectManager.getPlayer().getName(),GameObjectManager.getPlayer().getScore());
		// addHighscore("JOSEF", 1);
	}

	public void closeDB() {
		myDb.close();
	}

	private void displayName(String message) {
		TabMenu tm = (TabMenu) context;

		TextView textView = (TextView) tm.findViewById(R.id.ListName);
		textView.setText(message);
	}

	private void displayHighscore(String message) {
		TabMenu tm = (TabMenu) context;
		TextView textView = (TextView) tm.findViewById(R.id.listScore);
		textView.setText(message);
	}

	public void addHighscore(String name, int highscore) {

		Cursor c = myDb.getRow(1);
		long newId;
		if (c != null && c.getCount() > 0) {
			updateHighscore(name, highscore);
			newId = id;
			c = myDb.getRow(newId);
		} else {
			newId = myDb.insertRow(name, highscore);
			c = myDb.getRow(newId);
		}

	}

	public void updateHighscore(String name, int newHighscore) {
		Cursor cursor = myDb.getRow(1);
		int oldHighscore = cursor.getInt(DBAdapter.COL_HIGHSCORE);
		id = cursor.getLong(DBAdapter.COL_ROWID);
		if (oldHighscore < newHighscore) {
			oldHighscore = newHighscore;
		}
		myDb.updateRow(id, name, oldHighscore);
	}

	public void showHighscore() {
		Cursor cursor = myDb.getRow(1);
		displayHighscores(cursor);
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
				messageName += name + "\n";
				messageHighscore += highscore + "\n";
			} while (cursor.moveToNext());
		}

		// Close the cursor to avoid a resource leak.
		cursor.close();

		displayName(messageName);
		displayHighscore(messageHighscore);
	}

	public DBAdapter getDBAdapter() {
		return myDb;
	}
}
