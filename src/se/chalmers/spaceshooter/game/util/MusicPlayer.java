package se.chalmers.spaceshooter.game.util;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayer {
	private static MediaPlayer mp;

	public MusicPlayer(Context context) {
		mp = MediaPlayer.create(context, se.chalmers.spaceshooter.R.raw.starduster);
		mp.setVolume(1f, 1f);
		mp.start();
	}

	public static boolean isDone() {
		return !mp.isPlaying();
	}

	public static void pause() {
		mp.pause();
	}

	public static void resume() {
		mp.start();
	}

	public static void stop() {
		mp.stop();
	}
}
