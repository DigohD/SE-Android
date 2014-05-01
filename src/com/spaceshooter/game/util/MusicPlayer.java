package com.spaceshooter.game.util;

import java.io.IOException;

import android.R;
import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayer {

	private MediaPlayer mp;
	
	public MusicPlayer(Context context){
		mp = MediaPlayer.create(context, com.example.se_android.R.raw.starduster);	
		mp.setVolume(0.5f, 0.5f);
		mp.start();
	}
	
	public boolean isDone(){
		return !mp.isPlaying();
	}
	
}
