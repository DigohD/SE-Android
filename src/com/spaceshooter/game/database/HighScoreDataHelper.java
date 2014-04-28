package com.spaceshooter.game.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class HighScoreDataHelper {

	private static final String DATABASE_NAME = "spaceshooter.sqlite";
	private static final int DATABASE_VERSION = 1;
	private static final String HIGH_SCORE_TABLE = "highscores";
	private static final String KEY_DATE = "date";
	private static final String KEY_USER = "user";
	private static final String KEY_SCORE = "score";
	private static final String KEY_GAME_ID = "gameid";

	private static final String HIGH_SCORE_TABLE_CREATE = "CREATE TABLE "
			+ HIGH_SCORE_TABLE + " (" + KEY_USER + " TEXT, " + KEY_SCORE
			+ " INTEGER, " + KEY_GAME_ID + " INTEGER, " + KEY_DATE
			+ " DATETIME);";

	private Context context;
	private SQLiteDatabase db;

	private SQLiteStatement insertStmt;
	private static final String INSERT = "insert into " + HIGH_SCORE_TABLE
			+ "(" + KEY_USER + "," + KEY_DATE + "," + KEY_SCORE + ","
			+ KEY_GAME_ID + ")" + " values (?,?,?,?)";

	private SQLiteStatement updateStmt;
	private static final String UPDATE = "update " + HIGH_SCORE_TABLE + " set "
			+ KEY_DATE + "=?, " + KEY_SCORE + "=?" + " where " + KEY_USER
			+ "=? and " + KEY_GAME_ID + "=?";

	private SQLiteStatement deleteStmt;
	private static final String DELETE = "delete from " + HIGH_SCORE_TABLE
			+ " where " + KEY_USER + "=? and " + KEY_GAME_ID + "=?";

	public HighScoreDataHelper(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();

		// compile the statements up front, then just bind variables before
		// executing each time
		this.insertStmt = this.db.compileStatement(INSERT);
		this.updateStmt = this.db.compileStatement(UPDATE);
		this.deleteStmt = this.db.compileStatement(DELETE);
	}

	/**
	 * Insert a new high score entry This user doesn't already have a score for
	 * this game ID
	 * 
	 * @param entry
	 *            an entry representing (by player and gameId) the entry to
	 *            remove
	 * @return the row ID of the row inserted, if this insert is successful. -1
	 *         otherwise.
	 */
	public long insert(HighScoreEntry entry) {
		return insert(entry.getUser(), entry.getDate(), entry.getScore(),
				entry.getGameId());
	}

	/**
	 * Update a high score entry This user already has a score for this game ID,
	 * just update the score and date For raising a particular user's score
	 * value for a given game ID
	 * 
	 * @param entry
	 *            an entry representing (by player and gameId) the entry to
	 *            remove
	 * @param gameId
	 *            the identifier of the game
	 */
	public void update(HighScoreEntry entry) {
		update(entry.getUser(), entry.getDate(), entry.getScore(),
				entry.getGameId());
	}

	/**
	 * Delete a high score entry
	 * 
	 * @param entry
	 *            an entry representing (by player and gameId) the entry to
	 *            remove
	 */
	public void delete(HighScoreEntry entry) {
		delete(entry.getUser(), entry.getGameId());
	}

	// just check if an entry for this player/game combination is already
	// entered.
	public boolean alreadyExists(HighScoreEntry entry) {
		String[] params = { entry.getUser(), String.valueOf(entry.getGameId()) };
		Cursor cursor = this.db.query(HIGH_SCORE_TABLE, new String[] {
				KEY_USER, KEY_GAME_ID }, KEY_USER + "=? and " + KEY_GAME_ID
				+ "=?", params, null, null, null);

		boolean exists = cursor.getCount() > 0;

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return exists;
	}

	/**
	 * Insert a new high score entry This user doesn't already have a score for
	 * this game ID
	 * 
	 * @param name
	 *            the player's name
	 * @param date
	 *            what date was this high score achieved
	 * @param score
	 *            what was the score
	 * @param gameId
	 *            the identifier of the game
	 * @return the row ID of the row inserted, if this insert is successful. -1
	 *         otherwise.
	 */
	public long insert(String name, Date date, double score, long gameId) {
		this.insertStmt.bindString(1, name);
		this.insertStmt.bindLong(2, date.getTime());
		this.insertStmt.bindDouble(3, score);
		this.insertStmt.bindLong(4, gameId);
		return this.insertStmt.executeInsert();
	}

	/**
	 * Update an existing high score entry This user already has a score for
	 * this game ID, just update the score and date For raising a particular
	 * user's score value for a given game ID
	 * 
	 * @param name
	 *            the player's name
	 * @param date
	 *            what date was this high score achieved
	 * @param score
	 *            what was the score
	 * @param gameId
	 *            the identifier of the game
	 */
	public void update(String name, Date date, double score, long gameId) {
		this.updateStmt.bindLong(1, date.getTime());
		this.updateStmt.bindDouble(2, score);
		this.updateStmt.bindString(3, name);
		this.updateStmt.bindLong(4, gameId);
		this.updateStmt.execute();
	}

	/**
	 * Delete a high score entry
	 * 
	 * @param name
	 *            the player's name
	 * @param gameId
	 *            the identifier of the game
	 */
	public void delete(String name, long gameId) {
		this.deleteStmt.bindString(1, name);
		this.deleteStmt.bindLong(2, gameId);
		this.deleteStmt.execute();
	}

	public void deleteAll() {
		this.db.delete(HIGH_SCORE_TABLE, null, null);
	}

	/**
	 * Pull all HighScoreEntries from the DB for a given gameId. Can also pull
	 * all scores if passed ALL_GAMES
	 * 
	 * @param gameId
	 *            which one (or ALL_GAMES)
	 * @return a list of HighScoreEntries
	 */
	public List<HighScoreEntry> getByGameId(long gameId) {
		String[] params = { String.valueOf(gameId) };
		String condition = KEY_GAME_ID + "=?"; // this is the normal condition
												// for what we're looking
		List<HighScoreEntry> list = new ArrayList<HighScoreEntry>();

		// special case, just get all the scores
		if (gameId == HighScoreList.ALL_GAMES) {
			condition = null; // clear the condition out, we want everything
			params = null; // and clear the parameters for the condition, too
		}

		// build the query, condition and params are set appropriately already
		Cursor cursor = this.db.query(HIGH_SCORE_TABLE, new String[] {
				KEY_USER, KEY_DATE, KEY_SCORE, KEY_GAME_ID }, condition,
				params, null, null, KEY_GAME_ID + " asc, " + KEY_SCORE
						+ " desc, " + KEY_DATE); // somewhat silly sort
		if (cursor.moveToFirst()) { // do we have anything at all?
			do {
				list.add(entryFromCursor(cursor)); // build the entry and add to
													// the list
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	// pull all the relevant information from the cursor to create a
	// HighScoreEntry
	private HighScoreEntry entryFromCursor(Cursor cursor) {
		return new HighScoreEntry(cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_USER)), new Date(
				cursor.getLong(cursor.getColumnIndexOrThrow(KEY_DATE))),
				cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_SCORE)),
				cursor.getLong(cursor.getColumnIndexOrThrow(KEY_GAME_ID)));
	}

	public void close() {
		this.db.close();
	}

	private static class OpenHelper extends SQLiteOpenHelper {
		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(HIGH_SCORE_TABLE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Example",
					"Upgrading database, this will drop tables and recreate.");
			db.execSQL("DROP TABLE IF EXISTS " + HIGH_SCORE_TABLE);
			onCreate(db);
		}
	}
}
