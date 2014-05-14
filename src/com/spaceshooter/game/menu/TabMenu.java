package com.spaceshooter.game.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.example.se_android.R;
import com.spaceshooter.game.GameActivity;
import com.spaceshooter.game.database.DatabaseActivity;

public class TabMenu extends Activity {
	public boolean musicStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_tab_view);
		// AdView adView = (AdView) this.findViewById(R.id.adView);
		// AdRequest adRequest = new AdRequest.Builder()
		// .addTestDevice(AdRequest.DEVICE_ID_EMULATOR) // Emulator
		// .addTestDevice("CC224A050390619FD22B9448CC95A60D") // Jonas Nexus 4
		// // .addTestDevice("e83ab40d") // Simons Note
		// // .addTestDevice("0009478f6e129f") // Anders Galaxy S2
		// .build();
		// adView.loadAd(adRequest);

		TabHost th = (TabHost) findViewById(R.id.tabhost);
		th.setup();
		TabSpec menuSpecs = th.newTabSpec("tag1");
		menuSpecs.setContent(R.id.tabMenu);
		menuSpecs.setIndicator("Menu");
		th.addTab(menuSpecs);
		TabSpec ScoresSpecs = th.newTabSpec("tag2");
		ScoresSpecs.setContent(R.id.tabScores);
		ScoresSpecs.setIndicator("Scores");
		th.addTab(ScoresSpecs);
		TabSpec SettingsSpecs = th.newTabSpec("tag3");
		SettingsSpecs.setContent(R.id.tabSettings);
		SettingsSpecs.setIndicator("Settings");
		th.addTab(SettingsSpecs);
		TabSpec creditsSpecs = th.newTabSpec("tag4");
		creditsSpecs.setContent(R.id.tabCredits);
		creditsSpecs.setIndicator("Credits");
		th.addTab(creditsSpecs);

		Switch toggle = (Switch) findViewById(R.id.switchMusic);
		toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				musicStatus = isChecked;
			}
		});
	}

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

	@Override
	public void onBackPressed() {
		exitDialog();
	}

	// Menu tab options
	public void play(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
	
//	public void highscore(View view) {
//		Intent intent = new Intent(this, DatabaseActivity.class);
//		startActivity(intent);
//	}
	// Settings tab options

}