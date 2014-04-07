package com.spaceshooter.game.database;

import java.util.ArrayList;
import java.util.List;

import com.example.se_android.R;
import com.spaceshooter.game.MainActivity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HighScoreList extends ListActivity {
	public static final String EXTRA_GAME_ID = "com.spaceshooter.game.database.GAME_ID";
	public static final long ALL_GAMES = -1;

	private ProgressDialog progressDialog = null;
	private HighScoreDataHelper highScoreAccessor;
	private List<HighScoreEntry> entries = null;
	private EntryAdapter adapter;
	private Runnable viewEntries;
	private long gameId = ALL_GAMES; // default to showing all games

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.high_score_list);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			try {
				gameId = Long.valueOf(bundle.getString(EXTRA_GAME_ID));
			} catch (NumberFormatException e) {
				// really nothing to do here, if we fail, we'll just keep the "ALL_GAMES" value
			}
			
		}

		registerForContextMenu(getListView()); // handle long clicks for a context menu

        // we'll use this to manipulate the list of high scores
		highScoreAccessor = new HighScoreDataHelper(getApplicationContext());

		getListView().addHeaderView(getLayoutInflater().inflate(R.layout.high_score_list_header, null));

		entries = new ArrayList<HighScoreEntry>();
		this.adapter = new EntryAdapter(this, R.layout.high_score_list_row, entries);
		setListAdapter(this.adapter);

		viewEntries = new Runnable() {
			@Override
			public void run() {
				getEntries();
			}
		};

		reloadEntries(); // initialize
	}

	@Override
	protected void onPause() {
		super.onPause();
		// otherwise we can leak the ProgressDialog and crash on things like rotation
		progressDialog.dismiss();
	}
	
	private void reloadEntries() {
		adapter.clear();
		Thread thread = new Thread(null, viewEntries, "HighScoreListBackground");
		thread.start();
		progressDialog = ProgressDialog.show(HighScoreList.this, "Please wait...", "Retrieving data ...", true);
	}

	private void getEntries() {
		try {
			entries = highScoreAccessor.getByGameId(gameId);
		} catch (Exception e) {
			Log.e("BACKGROUND_PROC", e.getMessage());
		}
		runOnUiThread(returnRes);
	}

	private Runnable returnRes = new Runnable() {
		@Override
		public void run() {
			if (entries != null && entries.size() > 0) {
				adapter.notifyDataSetChanged();
				for (int i = 0; i < entries.size(); i++)
					adapter.add(entries.get(i));
			}
			progressDialog.dismiss();
			adapter.notifyDataSetChanged();
		}
	};

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.weight_list_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		HighScoreEntry e = adapter.getItem(info.position - 1); // pull the item that was selected, BEWARE!  "-1"!
		
		switch (item.getItemId()) {
		case R.id.menu_delete: // the only thing we can do right now is delete
			highScoreAccessor.delete(e);
			entries.remove(e);
			reloadEntries();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	
	private class EntryAdapter extends ArrayAdapter<HighScoreEntry> {
		private List<HighScoreEntry> items;

		public EntryAdapter(Context context, int textViewResourceId, List<HighScoreEntry> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.high_score_list_row, null);
			}

			final HighScoreEntry e = items.get(position);
			if (e != null) {
				((TextView) v.findViewById(R.id.playerName)).setText("" + e.getUser());
				((TextView) v.findViewById(R.id.gameId)).setText("" + e.getGameId());
				((TextView) v.findViewById(R.id.score)).setText("" + e.getScore());
				((TextView) v.findViewById(R.id.date)).setText(MainActivity.myDateFormat.format(e.getDate()));
			}

			return v;
		}
	}
}
