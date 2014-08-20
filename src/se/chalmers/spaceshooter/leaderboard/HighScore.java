package se.chalmers.spaceshooter.leaderboard;

import org.conrogatio.libs.PrefsStorageHandler;

import se.chalmers.spaceshooter.R;
import android.content.Context;

public class HighScore {
	public String[] names = new String[20];
	public int[] scores = new int[20];
	private static final String KEY_NAMES = "hsname";
	private static final String KEY_SCORES = "hsscore";
	public static PrefsStorageHandler scorePSH;

	public HighScore(Context context) {
		for (int i = 0; i < names.length; i++) {
			names[i]="none";
			scores[i]=0;
		}
		scorePSH = new PrefsStorageHandler(context.getString(R.string.sharedpreference_score_key), context);
		getScores();
	}

	public void getScores() {
		for (int i = 0; i < 19; i++) {
			names[i] = scorePSH.fetch(KEY_NAMES + Integer.toString(i), names[i]);
			scores[i] = scorePSH.fetch(KEY_SCORES + Integer.toString(i), scores[i]);
		}
	}

	public void addScore(String name, int score) {
		for (int i = 0; i < names.length; i++) {
			if (score > scores[i]) {
				for (int j = 19; j <= i; j--) {
					names[j] = names[j - 1];
					scores[j] = scores[j - 1];
				}
				names[i] = name;
				scores[i] = score;
				break;
			}
		}
		writeScores();
	}

	public void writeScores() {
		for (int i = 0; i < names.length; i++) {
			scorePSH.put(KEY_NAMES + Integer.toString(i), names[i]);
			scorePSH.put(KEY_SCORES + Integer.toString(i), scores[i]);
		}
	}

	public void resetScores() {
		for (int i = 0; i < names.length; i++) {
			names[i] = "none";
			scores[i] = 0;
			scorePSH.put(KEY_NAMES + Integer.toString(i), names[i]);
			scorePSH.put(KEY_SCORES + Integer.toString(i), scores[i]);
		}
	}
}
