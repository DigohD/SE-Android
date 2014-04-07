package com.spaceshooter.game.menu;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuActivity extends ListActivity {
	
	String classNames[] = { "com.spaceshooter.game.MainActivity", "topList", "achievments", "menu.Credits", "settings.SettingsActivity", "endApplication" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(MenuActivity.this, android.R.layout.simple_list_item_1, classNames));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String selectedClass = classNames[position];
		try {
			Class startClass = Class.forName(selectedClass);
			Intent ourIntent = new Intent(MenuActivity.this, startClass);
			startActivity(ourIntent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
