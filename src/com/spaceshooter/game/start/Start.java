package com.spaceshooter.game.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.spaceshooter.game.menu.TabMenu;

public class Start extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		openStartMenu();
	}

	public void openStartMenu() {
		Intent intent = new Intent(this, TabMenu.class);
		startActivity(intent);
		finish();
	}
}
