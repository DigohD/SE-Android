package com.spaceshooter.game.util;

import java.io.IOException;

import android.R;
import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayer {

	private MediaPlayer mp;
	
	public MusicPlayer(Context context){
		mp = MediaPlayer.create(context, com.example.se_android.R.raw.starduster);	
		mp.setVolume(1f, 1f);
		mp.start();
	}
	
	public boolean isDone(){
		return !mp.isPlaying();
	}
	
	public void pause(){
		mp.pause();
	}
	
	public void stop(){
		mp.stop();
	}
	
	public void resume(){
		mp.start();
	}
	
}
