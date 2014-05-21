package com.spaceshooter.game.database;

import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;

import com.spaceshooter.game.R;
import com.spaceshooter.game.engine.GameObjectManager;
import com.spaceshooter.game.startmenu.TabMenu;

public class Database {

	private DBAdapter myDb;
	private Context context;
	private Cursor cursor;
	private long id;

	public Database(Context context) {
		this.context = context;
		myDb = new DBAdapter(context);
	}

	public void openDB() {
		myDb.open();
		cursor = myDb.getRow(1);
	}

	public void closeDB() {
		myDb.close();
	}

	private void displayName(String message) {
		TabMenu tm = (TabMenu) context;
		TextView textView = (TextView) tm.findViewById(R.id.textName);
		textView.setText(message);
	}

	private void displayHighscore(String message) {
		TabMenu tm = (TabMenu) context;
		TextView textView = (TextView) tm.findViewById(R.id.textScore);
		textView.setText(message);
	}

	public void resetScore(String playerName) {
		cursor = myDb.getRow(1);
		if (cursor != null && GameObjectManager.getPlayer() != null) {
			myDb.updateRow(cursor.getLong(DBAdapter.COL_ROWID),
					playerName, 0);
			cursor = myDb.getRow(1);
		}

		// TEMPORARY FIX IF PLAYER IS NULL
		if (cursor != null && cursor.getCount() > 0
				&& GameObjectManager.getPlayer() == null) {
			myDb.updateRow(cursor.getLong(DBAdapter.COL_ROWID), "Player1", 0);
			cursor = myDb.getRow(1);
		}
	}

	public void addHighscore(String playerName, int highscore) {
		cursor = myDb.getRow(1);
		long newId;
		if (cursor != null && cursor.getCount() > 0) {
			updateHighscore(playerName, highscore);
			newId = id;
			cursor = myDb.getRow(newId);
		} else {
			newId = myDb.insertRow(playerName, highscore);
			cursor = myDb.getRow(newId);
		}
	}

	public void updateHighscore(String playerName, int newHighscore) {
		cursor = myDb.getRow(1);
		int oldHighscore = cursor.getInt(DBAdapter.COL_HIGHSCORE);
		id = cursor.getLong(DBAdapter.COL_ROWID);
		if (oldHighscore < newHighscore) {
			oldHighscore = newHighscore;
		}
		myDb.updateRow(id, playerName, oldHighscore);
	}

	public void showHighscore() {
		if (cursor != null)
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
