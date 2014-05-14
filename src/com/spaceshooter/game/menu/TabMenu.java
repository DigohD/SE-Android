package com.spaceshooter.game.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.widget.ToggleButton;

import com.example.se_android.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.spaceshooter.game.GameActivity;
import com.spaceshooter.game.database.DBAdapter;
import com.spaceshooter.game.database.DatabaseActivity;
import com.spaceshooter.game.engine.GameObjectManager;

public class TabMenu extends Activity {

	public static DatabaseActivity db;
	public boolean musicState = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		setContentView(R.layout.tabs);
		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR) // Emulator
				.addTestDevice("CC224A050390619FD22B9448CC95A60D") // Jonas
				.addTestDevice("e83ab40d") // Simon
				.addTestDevice("CC502939B1954AAF341181CF3BDAFAEA") // Anders
				.build();
		adView.loadAd(adRequest);

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
		TabSpec creditsSpecs = th.newTabSpec("tag3");
		creditsSpecs.setContent(R.id.tabCredits);
		creditsSpecs.setIndicator("Credits");
		th.addTab(creditsSpecs);
		db = new DatabaseActivity(this);
		db.openDB();
		db.showHighscore();
	}

	public static void newScore(Boolean b){
		db.addHighscore(GameObjectManager.getPlayer().getName(),GameObjectManager.getPlayer().getScore());
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
	public void onResume()
	{
		super.onResume();
		db.openDB();
		db.showHighscore();
		}
	@Override
	public void onBackPressed() {
		exitDialog();
	}
	
	public void onDestroy(){
		super.onDestroy();
		db.closeDB();
	}

	// Menu tab options
	public void play(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra("EXTRA_musicState", musicState);
		
		startActivity(intent);
	}

	public void onToggleClicked(View view) {
		musicState = ((ToggleButton) view).isChecked();
	}
	

	// Add methods for everything that is handled in the menus, e.g. scores etc.

	// Settings tab options

}