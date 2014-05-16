package com.spaceshooter.game.view;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.spaceshooter.tcp.HighScoreParser;
import com.spaceshooter.tcp.TCPClient;

public class GlobalHighScoreView extends View{
	
	public static final int WIDTH = 800, HEIGHT = 480;
	
	private String response;
	private HighScoreParser parser;
	private String[] entries;
	private Paint p = new Paint();
	
	public GlobalHighScoreView(Context context) {
		super(context);
		
		parser = new HighScoreParser();
		
		TCPClient tcp = new TCPClient();
		String[] querys = {"select"};
		
		try {
			response = tcp.execute(querys).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		entries = parser.parseQuery(response);
	}
	
	@Override
	public void onDraw(Canvas c){
		c.drawColor(Color.BLACK);
		p.setColor(Color.RED);
		
		for(int i = 0; i < entries.length; i++){
			c.drawText(entries[i], 300, 50+20*i, p);
		}
		
	}
	
	
	

}
