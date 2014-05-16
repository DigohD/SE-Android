package com.spaceshooter.game.view;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.spaceshooter.tcp.TCPClient;

public class GlobalHighScoreView extends View{
	
	public static final int WIDTH = 800, HEIGHT = 480;
	
	public GlobalHighScoreView(Context context) {
		super(context);
		
		TCPClient tcp = new TCPClient();
		String[] querys = {"select"};
		
		try {
			String response = tcp.execute(querys).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		tcp.execute(querys);
	
	}
	
	@Override
	public void onDraw(Canvas c){
		
	}
	
	
	

}
