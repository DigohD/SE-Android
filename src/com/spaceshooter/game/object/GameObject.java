package com.spaceshooter.game.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.spaceshooter.game.util.Vector2f;

public abstract class GameObject {
	
	protected Vector2f position;
	protected Bitmap bitmap;
	protected float x, y;
	
	protected int width, height;
	protected Rect rect;
	protected Paint paint;
	
	public GameObject(float x, float y){
		this.x = x;
		this.y = y;
		paint = new Paint();
		rect = new Rect((int)x, (int)y, (int)x + width,(int) y + height);
	}
	
	public abstract void tick(float dt);
	public abstract void draw(Canvas canvas, float interpolation);

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Rect getRect() {
		return rect;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
