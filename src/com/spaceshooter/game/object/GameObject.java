package com.spaceshooter.game.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.spaceshooter.game.util.Vector2f;

public abstract class GameObject {
	
	protected Vector2f position;
	protected Bitmap bitmap;
	protected Rect rect;
	
	protected int width, height;
	protected boolean live = true;
	
	public GameObject(Vector2f position){
		this.position = position;
	}
	
	public abstract void tick(float dt);
	public abstract void draw(Canvas canvas, float interpolation);

	
	public Vector2f getPosition() {
		return position;
	}
	
	public float getX(){
		return position.x;
	}
	
	public float getY(){
		return position.y;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public Rect getRect(){
		return rect;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setLive(boolean live){
		this.live = live;
	}
	
	public boolean isLive(){
		return live;
	}
	
}
