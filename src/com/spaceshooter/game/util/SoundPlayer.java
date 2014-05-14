package com.spaceshooter.game.util;

import java.util.HashMap;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

public class SoundPlayer {

	private static SoundPool soundPool;
	private static boolean loaded;
	private static int laser, explosion, lasershot;

	private static HashMap<SoundID, Integer> sounds;

	public enum SoundID {
		nullValue, ui_guns, fire_RedPlasma, fire_BluePlasma, fire_GreenPlasma, fire_YellowPlasma, hit_RedPlasma, hit_BluePlasma, hit_GreenPlasma, hit_YellowPlasma, exp_1, exp_2
	}

	public SoundPlayer(Activity activity) {
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				loaded = true;
			}
		});

		sounds = new HashMap<SoundID, Integer>();

		sounds.put(SoundID.nullValue,
				soundPool.load(activity, com.example.se_android.R.raw.laser, 1));

		sounds.put(SoundID.ui_guns, soundPool.load(activity,
				com.example.se_android.R.raw.ui_guns, 1));

		sounds.put(SoundID.exp_1,
				soundPool.load(activity, com.example.se_android.R.raw.exp_1, 1));
		sounds.put(SoundID.exp_2,
				soundPool.load(activity, com.example.se_android.R.raw.exp_2, 1));

		sounds.put(SoundID.fire_RedPlasma, soundPool.load(activity,
				com.example.se_android.R.raw.fire_redplasma, 1));
		sounds.put(SoundID.fire_BluePlasma, soundPool.load(activity,
				com.example.se_android.R.raw.fire_blueplasma, 1));
		sounds.put(SoundID.fire_GreenPlasma, soundPool.load(activity,
				com.example.se_android.R.raw.fire_greenplasma, 1));
		sounds.put(SoundID.fire_YellowPlasma, soundPool.load(activity,
				com.example.se_android.R.raw.fire_yellowplasma, 1));

		sounds.put(SoundID.hit_RedPlasma, soundPool.load(activity,
				com.example.se_android.R.raw.hit_redplasma, 1));
		sounds.put(SoundID.hit_BluePlasma, soundPool.load(activity,
				com.example.se_android.R.raw.hit_blueplasma, 1));
		sounds.put(SoundID.hit_GreenPlasma, soundPool.load(activity,
				com.example.se_android.R.raw.hit_greenplasma, 1));
		sounds.put(SoundID.hit_YellowPlasma, soundPool.load(activity,
				com.example.se_android.R.raw.hit_yellowplasma, 1));
	}

	public static void playSound(SoundID ID) {
		if (loaded) {
			soundPool.play(sounds.get(ID), 1.0f, 1.0f, 1, 0, 1f);
		}
	}

	// private static MediaPlayer[] mp = new MediaPlayer[5];
	// private static Context context;
	//
	// public SoundPlayer(Context context){
	// for(int i = 0; i < mp.length; i++)
	// mp[i] = new MediaPlayer();
	// this.context = context;
	// }
	//
	// public static void playSound(int soundID){
	// // for(int i = 0; i < mp.length; i++)
	// // if(!mp[i].isPlaying()){
	// mp[0] = new MediaPlayer();
	// mp[0].create(context, com.example.se_android.R.raw.laser);
	// mp[0].start();
	// // return;
	// // }
	// }

}
