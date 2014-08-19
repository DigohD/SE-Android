package se.chalmers.spaceshooter.leaderboard.conrogatio;

import org.conrogatio.libs.PrefsStorageHandler;

import se.chalmers.spaceshooter.R;
import android.content.Context;

public class HighScore {
	public String[] names = new String[20];
	public int[] scores = new int[20];
	public String[] stringScores = new String[20];;
	private static final String KEY_NAMES = "hsnames";
	private static final String KEY_SCORES = "hsscores";
	public static PrefsStorageHandler psh;

	public HighScore(Context context) {
		psh = new PrefsStorageHandler(context.getString(R.string.sharedpreference_file_key), context);
		getScores();
	}

	public void getScores() {
	}

	public void addScore(String name, int score) {
		for (int i = 0; i < scores.length; i++) {
			if (score > scores[i]) {
				for (int j = 19; j <= i; j--) {
					scores[j] = scores[j - 1];
					names[j] = names[j - 1];
				}
				scores[i] = score;
				names[i] = name;
				break;
			}
		}
	}

	public String[] convert() {
		String[] scoreExport = new String[2];
		return scoreExport;
	}

	public static void writeScores() {
		psh.put(KEY_SCORES, "");
		psh.put(KEY_NAMES, "");
	}
}
