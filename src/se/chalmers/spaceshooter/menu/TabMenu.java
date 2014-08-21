package se.chalmers.spaceshooter.menu;

import org.conrogatio.libs.PrefsStorageHandler;
import org.conrogatio.libs.ScoreHandler;

import se.chalmers.spaceshooter.R;
import se.chalmers.spaceshooter.game.GameActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
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

public class TabMenu extends Activity {
	boolean dialogOpen = false;
	public TabHost th;
	public int starts;
	public static int helpShown;
	public static boolean musicState;
	public static boolean sfxState;
	public static String playerName;
	public static PrefsStorageHandler settingsPSH;
	public static ScoreHandler SH;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		SH=new ScoreHandler(20, getString(R.string.sharedpreference_score_key), this);
		
		SH.getScores();
		settingsPSH = new PrefsStorageHandler(getString(R.string.sharedpreference_settings_key), this);
		getSettings();
		settingsPSH.put("starts", starts);
		settingsPSH.put("helpShown", helpShown);
		settingsPSH.put("playerName", playerName);
		settingsPSH.put("musicState", musicState);
		settingsPSH.put("sfxState", sfxState);
		// Ads
		setContentView(R.layout.tabs);
		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR) // Emulator
				.addTestDevice("8E049A16A1B306B941023A9DC2687AFF") // mako
				.addTestDevice("75416FCD1AEA323DDAB1139D87A6ECB5") // ace
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
		if (starts == 0) {
			welcomeDialog();
		}
		starts++;
		settingsPSH.put("starts", starts);
		updateView();
	}

	public static void helpDialog(Context context) {
		Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setTitle("Help");
		builder.setMessage(R.string.help_message);
		builder.setNegativeButton("Thank you!", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				TabMenu.helpShown++;
				TabMenu.writeSettings("helpShown", TabMenu.helpShown);
			}
		});
		builder.create().show();
	}

	public static void writeSettings(String key, boolean value) {
		settingsPSH.put(key, value);
	}

	public static void writeSettings(String key, int value) {
		settingsPSH.put(key, value);
	}

	public void getSettings() {
		settingsPSH = new PrefsStorageHandler(getString(R.string.sharedpreference_settings_key), this);
		musicState = settingsPSH.fetch("musicState", true);
		sfxState = settingsPSH.fetch("sfxState", true);
		starts = settingsPSH.fetch("starts", 0);
		helpShown = settingsPSH.fetch("helpShown", 0);
		playerName = settingsPSH.fetch("playerName", getString(R.string.sharedpreferences_default_player_name));
	}

	public void help(View view) {
		helpDialog(this);
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
	}

	@Override
	public void onResume() {
		super.onResume();
		updateView();
	}

	// Settings
	public void onToggleClickedMusic(View view) {
		musicState = ((ToggleButton) view).isChecked();
		settingsPSH.put("musicState", musicState);
	}

	public void onToggleClickedSFX(View view) {
		sfxState = ((ToggleButton) view).isChecked();
		settingsPSH.put("sfxState", sfxState);
	}

	// Start
	public void play(View view) {
		// hs.addScore("a",1);
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
		// updateView();
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
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				String enteredPlayer = input.getText().toString();
				if (enteredPlayer.matches("^[a-zA-Z0-9]*$") && !enteredPlayer.isEmpty() && enteredPlayer.length() <= 12) {
					Log.i("ALERT", "Store username -" + enteredPlayer);
					playerName = enteredPlayer;
					((TextView) findViewById(R.id.textPlayingAs)).setText("Playing as " + playerName);
					settingsPSH.put("playerName", playerName);
				} else {
					Toast.makeText(TabMenu.this, "The name was not valid and was not changed", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});
		alert.show();
	}

	public void resetScores(View view) {
		SH.resetScores();
		updateView();
		Toast.makeText(this, "The scores have been resetted", Toast.LENGTH_LONG).show();
	}

	public void resetSettings(View view) {
		settingsPSH.clear();
		getSettings();
		updateView();
		Toast.makeText(this, "The settings have been resetted", Toast.LENGTH_LONG).show();
	}

	public void selectPlayer(View view) {
		playerDialog();
	}

	// Other
	public void showScoreTab() {
		th.setCurrentTab(1);
	}

	public void updateView() {
		View musicToggle = findViewById(R.id.toggleMusic);
		((ToggleButton) musicToggle).setChecked(musicState);
		View sfxToggle = findViewById(R.id.toggleSFX);
		((ToggleButton) sfxToggle).setChecked(sfxState);
		((TextView) findViewById(R.id.textPlayingAs)).setText("Playing as " + playerName);
		int resID;
		for (int i = 0; i <= 9; i++) {
			resID = getResources().getIdentifier("name" + i, "id", getPackageName());
			((TextView) findViewById(resID)).setText(SH.names[i]);
			resID = getResources().getIdentifier("score" + i, "id", getPackageName());
			((TextView) findViewById(resID)).setText(Integer.toString(SH.scores[i]));
		}
	}

	// Exit dialog
	private void exitDialog() {
		Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle("Already done?");
		builder.setMessage("Really quit?");
		builder.setPositiveButton("No, not really", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		builder.setNegativeButton("Yes, really", new OnClickListener() {
			@Override
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
		builder.setMessage(R.string.welcome_message);
		builder.setNegativeButton("Thank you!", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				playerDialog();
			}
		});
		builder.create().show();
	}
}