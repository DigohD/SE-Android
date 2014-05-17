package com.spaceshooter.game.view;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.spaceshooter.game.net.HighScoreParser;
import com.spaceshooter.game.net.TCPClient;

public class LeaderBoardView extends View{
	
	public static final int WIDTH = 800, HEIGHT = 480;
	private float scaleX, scaleY;
	
	private String response;
	private HighScoreParser parser;
	private String[] entries;
	private Paint p = new Paint();
	
	public LeaderBoardView(Context context) {
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
		
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		scaleX = ((float) size.x / (float) WIDTH);
		scaleY = ((float) size.y / (float) HEIGHT);

	}
	
	@Override
	public void onDraw(Canvas c){
		c.scale(scaleX, scaleY);
		c.drawColor(Color.BLACK);
		
		p.setColor(Color.RED);
		p.setTextSize(30);
		c.drawText("Leaderboard", 300, 30, p);
		p.setTextSize(20);
		p.setColor(Color.WHITE);
		
		for(int i = 0; i < entries.length; i++){
			if(55+20*i <= 780)
				c.drawText(i+1 + ".) " + entries[i], 300, 55+20*i, p);
		}
	}

}
