package se.chalmers.spaceshooter.leaderboard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	private static final String TAG = "DBAdapter";
	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	public static final String KEY_NAME = "level";
	public static final String KEY_HIGHSCORE = "highscore";
	public static final int COL_NAME = 1;
	public static final int COL_HIGHSCORE = 2;
	// public static final String DATABASE_NAME = "Spaceshooter";
	// public static final String DATABASE_TABLE = "highScores";
	public static final String[] ALL_KEYS = new String[] { KEY_ROWID, KEY_NAME, KEY_HIGHSCORE };
	public static final String DATABASE_NAME = "sql340234";
	public static final String DATABASE_TABLE = "highScores";
	public static final int DATABASE_VERSION = 2;
	private static final String DATABASE_CREATE_SQL = "create table " + DATABASE_TABLE + " (" + KEY_ROWID
			+ " integer primary key autoincrement, " + KEY_NAME + " String not null, " + KEY_HIGHSCORE
			+ " integer not null" + ");";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	public void close() {
		DBHelper.close();
	}

	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));
			} while (c.moveToNext());
		}
		c.close();
	}

	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}

	public Cursor getAllRows() {
		Cursor c = db.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NAME, KEY_HIGHSCORE }, null, null, null,
				null, KEY_NAME + " ASC");
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	/*
	 * public Cursor getHighestScoreInLevel(int i){ Cursor c = null, cursor =
	 * getHighestLevel(); c =
	 * db.rawQuery("SELECT * FROM highScores WHERE level = '" + i +
	 * "'ORDER BY highscore DESC LIMIT 1", null); if (c != null) {
	 * c.moveToFirst(); } return c; }
	 * 
	 * public Cursor getHighestLevel(){ Cursor c =
	 * db.rawQuery("SELECT * FROM highScores ORDER BY level DESC LIMIT 1",
	 * null); if (c != null) { c.moveToFirst(); } return c; }
	 */
	public Cursor getRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getRowWithName(String name) {
		String where = KEY_NAME + "=" + name;
		Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public long insertRow(String name, int highscore) {
		/*
		 * CHANGE 3:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_HIGHSCORE, highscore);
		// Insert it into the database.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	public DBAdapter open() {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void updateRow(long rowId, String name, int highscore) {
		String where = KEY_ROWID + "=" + rowId;
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_NAME, name);
		newValues.put(KEY_HIGHSCORE, highscore);
		// Insert it into the database.
		db.update(DATABASE_TABLE, newValues, where, null);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion + " to " + newVersion
					+ ", which will destroy all old data!");
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			// Recreate new database:
			onCreate(_db);
		}
	}
}
