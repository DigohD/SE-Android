package com.spaceshooter.game.util;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

public class SoundPlayer {
	
	private static SoundPool soundPool;
	private static boolean loaded;
	private static int laser, explosion, lasershot;
	
	public SoundPlayer(Activity activity){
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	    soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
	      @Override
	      public void onLoadComplete(SoundPool soundPool, int sampleId,
	          int status) {
	        loaded = true;
	      }
	    });
	    laser = soundPool.load(activity, com.example.se_android.R.raw.laser, 1);
	    explosion = soundPool.load(activity, com.example.se_android.R.raw.explosion, 1);
	    lasershot = soundPool.load(activity, com.example.se_android.R.raw.laser_shoot, 1);
	}
	
	public static void playSound(int ID){
		if (loaded) {
			switch(ID){
				case 1:
			        soundPool.play(laser, 1.0f, 1.0f, 1, 0, 1f);
					break;
				case 2:
			        soundPool.play(explosion, 1.0f, 1.0f, 1, 0, 1f);
					break;
				case 3:
			        soundPool.play(lasershot, 1.0f, 1.0f, 1, 0, 1f);
					break;
			}
	      }
	}
	
//	private static MediaPlayer[] mp = new MediaPlayer[5];
//	private static Context context;
//	
//	public SoundPlayer(Context context){
//		for(int i = 0; i < mp.length; i++)
//			mp[i] = new MediaPlayer();
//		this.context = context;
//	}
//	
//	public static void playSound(int soundID){
////		for(int i = 0; i < mp.length; i++)
////			if(!mp[i].isPlaying()){
//				mp[0] = new MediaPlayer();
//				mp[0].create(context, com.example.se_android.R.raw.laser);
//				mp[0].start();
////				return;
////			}	
//	}

}
