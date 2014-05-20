package com.spaceshooter.game.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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

	public boolean tmMusicState = true;
	public boolean tmSfxState = true;
	public static Database db;
	public TabHost th;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Intent intent = getIntent();
		tmMusicState = intent.getBooleanExtra("EXTRA_musicState", true);
		tmSfxState = intent.getBooleanExtra("EXTRA_sfxState", true);
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
	}

	// Exit dialog
	private void exitDialog() {
		Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle("Already done?");
		builder.setMessage("Really quit?");
		builder.setPositiveButton("No, not really", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
		});
		builder.setNegativeButton("Yes, really", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				TabMenu.super.onBackPressed();
				System.exit(0);
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
		intent.putExtra("EXTRA_musicState", tmMusicState);
		intent.putExtra("EXTRA_sfxState", tmSfxState);
		startActivity(intent);
	}

	public void globalHighscore(View view) {
		Intent intent = new Intent(this, LeaderBoardActivity.class);
		startActivity(intent);
	}

	// Settings
	public void onToggleClickedMusic(View view) {
		tmMusicState = ((ToggleButton) view).isChecked();
	}

	public void onToggleClickedSFX(View view) {
		tmSfxState = ((ToggleButton) view).isChecked();
	}

	// Other
	public void showScoreTab() {
		th.setCurrentTab(1);
	}
}