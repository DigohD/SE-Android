package com.spaceshooter.game.settings;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsActivity extends ListActivity {

	String settingsClassNames[] = { "ToggleMusic", "ToggleSFX", "ResetProgress" };
	String settingsMenuOptions[] = { "Toggle music", "Toggle SFX",
			"Reset progress" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(SettingsActivity.this,
				android.R.layout.simple_list_item_1, settingsMenuOptions));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String selectedSettingsClass = settingsClassNames[position];
		try {
			Class startSettingsClass = Class
					.forName("com.spaceshooter.game.settings."
							+ selectedSettingsClass);
			Intent ourIntent = new Intent(SettingsActivity.this,
					startSettingsClass);
			startActivity(ourIntent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
