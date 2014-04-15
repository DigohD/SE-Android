package com.spaceshooter.game.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.se_android.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.spaceshooter.game.GameActivity;
import com.spaceshooter.game.database.HighScoreList;
import com.spaceshooter.game.settings.SettingsActivity;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

	
		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR) // Test ID:
																// Emulator
				.addTestDevice("0071a84d4acd309b") // Test ID: Jonas Nexus 4
				.build();
		adView.loadAd(adRequest);
	}

	public void play(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}

	public void highscorelist(View view) {
		Intent intent = new Intent(this, HighScoreList.class);
		startActivity(intent);
	}

	public void achievments(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	public void credits(View view) {
		Intent intent = new Intent(this, CreditsActivity.class);
		startActivity(intent);
	}

	public void settings(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	public void quit(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
}
