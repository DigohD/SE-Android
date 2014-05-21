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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.spaceshooter.game.GameActivity;
import com.spaceshooter.game.LeaderBoardActivity;
import com.spaceshooter.game.R;
import com.spaceshooter.game.database.Database;
import com.spaceshooter.game.start.Start;

public class TabMenu extends Activity {
	SharedPreferences sp;
	boolean dialogOpen = false;
	public boolean musicState;
	public boolean sfxState;
	public int starts;
	public static Database db;
	public TabHost th;
	public String playerName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		System.out.println("a Started " + starts);
		sp = getSharedPreferences(
				getString(R.string.sharedpreference_file_key),
				Context.MODE_PRIVATE);
		musicState = sp.getBoolean("musicState", true);
		sfxState = sp.getBoolean("sfxState", true);
		starts = sp.getInt("starts", 0);
		System.out.println("b Started " + starts);
		playerName = sp.getString("playerName",
				getString(R.string.sharedpreferences_default_player_name));

		SharedPreferences.Editor editor = sp.edit();
		editor.putInt("starts", starts);
		editor.putString("playerName", playerName);
		editor.putBoolean("musicState", musicState);
		editor.putBoolean("sfxState", sfxState);
		editor.commit();

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
		System.out.println("c Started " + starts);
		if (starts == 0) {
			th.setCurrentTab(3);
			welcomeDialog();
		}
		starts = starts + 1;
		System.out.println("d Started " + starts);
		editor = sp.edit();
		editor.putInt("spStarts", starts);
		editor.commit();
		System.out.println("e Started " + sp.getInt("starts", 0));
		View musicToggle = findViewById(R.id.toggleMusic);
		((ToggleButton) musicToggle).setChecked(musicState);
		View sfxToggle = findViewById(R.id.toggleSFX);
		((ToggleButton) sfxToggle).setChecked(sfxState);
		final TextView playingAsText = (TextView) findViewById(R.id.textPlayingAs);
		playingAsText.setText("Playing as " + playerName);
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
		db.resetScore(playerName);
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
		musicState = ((ToggleButton) view).isChecked();
		Editor editor = sp.edit();
		editor.putBoolean("spMusicState", musicState);
		editor.commit();
	}

	public void onToggleClickedSFX(View view) {
		sfxState = ((ToggleButton) view).isChecked();
		Editor editor = sp.edit();
		editor.putBoolean("spSfxState", sfxState);
		editor.commit();
	}

	public void selectPlayer(View view) {
		playerDialog();
	}

	public void playerDialog() {
		// String reg = "^[a-zA-Z0-9]*$";
		dialogOpen = true;
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Change player");
		alert.setMessage("Enter player name (only a-z, A-Z and numbers, and maximum 12 characters)");
		final EditText input = new EditText(this);
		input.setText(playerName);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String enteredPlayer = input.getText().toString();
				if (enteredPlayer.matches("^[a-zA-Z0-9]*$")
						&& !enteredPlayer.isEmpty()
						&& enteredPlayer.length() <= 12) {
					Log.i("ALERT", "Store username -" + enteredPlayer);
					playerName = enteredPlayer;
					final TextView playingAsText = (TextView) findViewById(R.id.textPlayingAs);
					playingAsText.setText("Playing as " + playerName);
					Editor editor = sp.edit();
					editor.putString("spPlayerName", playerName);
					editor.commit();
				} else {
					Toast.makeText(TabMenu.this, "Please put valid username",
							Toast.LENGTH_LONG).show();
				}

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

	public void restartApp(View view) {
		Intent intent = new Intent(this, Start.class);
		startActivity(intent);
		finish();
	}

	// Other
	public void showScoreTab() {
		th.setCurrentTab(1);
	}
}