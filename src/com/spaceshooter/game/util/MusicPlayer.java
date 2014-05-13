package com.spaceshooter.game.util;

import java.io.IOException;

import android.R;
import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayer {

	private static MediaPlayer mp;
	
	public MusicPlayer(Context context){
		mp = MediaPlayer.create(context, com.example.se_android.R.raw.starduster);	
		mp.setVolume(1f, 1f);
		mp.start();
	}
	
	public static boolean isDone(){
		return !mp.isPlaying();
	}
	
	public static void pause(){
		mp.pause();
	}
	
	public static void stop(){
		mp.stop();
	}
	
	public static void resume(){
		mp.start();
	}
	
}
