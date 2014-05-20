package com.spaceshooter.game.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.spaceshooter.game.GameActivity;
import com.spaceshooter.game.LeaderBoardActivity;
import com.spaceshooter.game.R;
import com.spaceshooter.game.database.Database;

public class TabMenu extends Activity {
	SharedPreferences sp;
	public boolean tmMusicState;
	public boolean tmSfxState;
	public int tmStarts;
	public static Database db;
	public TabHost th;
	public String tmPlayerName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		sp = getSharedPreferences(getString(R.string.preference_file_key),
				Context.MODE_PRIVATE);
		if (!sp.contains("spStarts")) {
			Editor editor = sp.edit();
			editor.putInt("spStarts", 0);
			editor.putString("spPlayerName", "Player 1");
			editor.putBoolean("spMusicState", true);
			editor.putBoolean("spSfxState", true);
			editor.commit();
		}
		tmMusicState = sp.getBoolean("spMusicState", true);
		tmSfxState = sp.getBoolean("spSfxState", true);
		tmStarts = sp.getInt("spStarts", 0);
		tmPlayerName = sp.getString("spPlayerName", "Player 1");

		// Ads
		setContentView(R.layout.tabs);
		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR) // Emulator
				.addTestDevice("CC224A050390619FD22B9448CC95A60D") // Jonas
				.addTestDevice("e83ab40d") // Simon
				.addTestDevice("CC502939B1954AAF341181CF3BDAFAEA") // Anders

				.build();
		adView.loadAd(adRequest);
		// Tabs
		th = (TabHost) findViewById(R.id.tabhost);
		th.setup();
		TabSpec menuSpecs = th.newTabSpec("tag1");
		menuSpecs.setContent(R.id.tabMenu);
		menuSpecs.setIndicator("Start Menu");
		th.addTab(menuSpecs);
		TabSpec ScoresSpecs = th.newTabSpec("tag2");
		ScoresSpecs.setContent(R.id.tabScores);
		ScoresSpecs.setIndicator("Scores");
		th.addTab(ScoresSpecs);
		TabSpec settingsSpecs = th.newTabSpec("tag3");
		settingsSpecs.setContent(R.id.tabSettings);
		settingsSpecs.setIndicator("Settings");
		th.addTab(settingsSpecs);
		TabSpec creditsSpecs = th.newTabSpec("tag4");
		creditsSpecs.setContent(R.id.tabCredits);
		creditsSpecs.setIndicator("Credits");
		th.addTab(creditsSpecs);
		// Database
		db = new Database(this);
		db.openDB();
		db.showHighscore();
		db.closeDB();
		if (tmStarts == 0) {
			th.setCurrentTab(3);
			welcomeDialog();
		}
		tmStarts = tmStarts + 1;
		Editor editor = sp.edit();
		editor.putInt("spStarts", tmStarts);
		editor.commit();
		View musicToggle = findViewById(R.id.toggleMusic);
		((ToggleButton) musicToggle).setChecked(tmMusicState);
		View sfxToggle = findViewById(R.id.toggleSFX);
		((ToggleButton) sfxToggle).setChecked(tmSfxState);
	}

	// Exit dialog
	private void exitDialog() {
		Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle("Already done?");
		builder.setMessage("Really quit?");
		builder.setPositiveButton("Yes, really", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				TabMenu.super.onBackPressed();
				System.exit(0);
			}
		});
		builder.setNegativeButton("No, not really", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		builder.create().show();
	}

	private void welcomeDialog() {
		Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle("Welcome to Spaceshooter Zero!");
		builder.setMessage(R.string.welcomeMessage);
		builder.setNegativeButton("Thank you!", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				playerDialog();
			}
		});
		builder.create().show();
	}

	// Back pressed
	@Override
	public void onBackPressed() {
		exitDialog();
	}

	// Scores
	@Override
	public void onDestroy() {
		super.onDestroy();
		db.closeDB();
	}

	public void onResume() {
		super.onResume();
		db.openDB();
		db.showHighscore();
		db.closeDB();
	}

	public void resetScores(View view) {
		db.openDB();
		db.resetScore();
		db.showHighscore();
		db.closeDB();
	}

	// Start
	public void play(View view) {
		db.closeDB();
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}

	public void globalHighscore(View view) {
		Intent intent = new Intent(this, LeaderBoardActivity.class);
		startActivity(intent);
	}

	// Settings
	public void onToggleClickedMusic(View view) {
		tmMusicState = ((ToggleButton) view).isChecked();
		Editor editor = sp.edit();
		editor.putBoolean("spMusicState", tmMusicState);
		editor.commit();
	}

	public void onToggleClickedSFX(View view) {
		tmSfxState = ((ToggleButton) view).isChecked();
		Editor editor = sp.edit();
		editor.putBoolean("spSfxState", tmSfxState);
		editor.commit();
	}

	public void selectPlayer(View view) {
		playerDialog();
		Editor editor = sp.edit();
		editor.putString("spPlayerName", tmPlayerName);
		editor.commit();
	}

	public void playerDialog() {
		// Need to define what happens when EditText is empty
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Change player");
		alert.setMessage("Enter player name");
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				tmPlayerName = input.getText().toString();
			}
		});
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

		alert.show();
	}

	public void resetSettings(View view) {
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}

	// Other
	public void showScoreTab() {
		th.setCurrentTab(1);
	}
}