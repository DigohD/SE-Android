package se.chalmers.spaceshooter.game.util;

import java.util.HashMap;

import se.chalmers.spaceshooter.menu.TabMenu;
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

public class SoundPlayer {
	public enum SoundID {
		nullValue, ui_guns, fire_RedPlasma, fire_BluePlasma, fire_GreenPlasma, fire_YellowPlasma, hit_RedPlasma, hit_BluePlasma, hit_GreenPlasma, hit_YellowPlasma, exp_1, exp_2
	}

	private static SoundPool soundPool;
	private static boolean loaded;
	private static HashMap<SoundID, Integer> sounds;

	public static void playSound(SoundID ID) {
		if (loaded && TabMenu.sfxState) {
			soundPool.play(sounds.get(ID), 1.0f, 1.0f, 1, 0, 1f);
		}
	}

	public SoundPlayer(Activity activity) {
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
				loaded = true;
			}
		});
		sounds = new HashMap<SoundID, Integer>();
		sounds.put(SoundID.nullValue, soundPool.load(activity, se.chalmers.spaceshooter.R.raw.laser, 1));
		sounds.put(SoundID.ui_guns, soundPool.load(activity, se.chalmers.spaceshooter.R.raw.ui_guns, 1));
		sounds.put(SoundID.exp_1, soundPool.load(activity, se.chalmers.spaceshooter.R.raw.exp_1, 1));
		sounds.put(SoundID.exp_2, soundPool.load(activity, se.chalmers.spaceshooter.R.raw.exp_2, 1));
		sounds.put(SoundID.fire_RedPlasma, soundPool.load(activity, se.chalmers.spaceshooter.R.raw.fire_redplasma, 1));
		sounds.put(SoundID.fire_BluePlasma, soundPool.load(activity, se.chalmers.spaceshooter.R.raw.fire_blueplasma, 1));
		sounds.put(SoundID.fire_GreenPlasma,
				soundPool.load(activity, se.chalmers.spaceshooter.R.raw.fire_greenplasma, 1));
		sounds.put(SoundID.fire_YellowPlasma,
				soundPool.load(activity, se.chalmers.spaceshooter.R.raw.fire_yellowplasma, 1));
		sounds.put(SoundID.hit_RedPlasma, soundPool.load(activity, se.chalmers.spaceshooter.R.raw.hit_redplasma, 1));
		sounds.put(SoundID.hit_BluePlasma, soundPool.load(activity, se.chalmers.spaceshooter.R.raw.hit_blueplasma, 1));
		sounds.put(SoundID.hit_GreenPlasma, soundPool.load(activity, se.chalmers.spaceshooter.R.raw.hit_greenplasma, 1));
		sounds.put(SoundID.hit_YellowPlasma,
				soundPool.load(activity, se.chalmers.spaceshooter.R.raw.hit_yellowplasma, 1));
	}
}
