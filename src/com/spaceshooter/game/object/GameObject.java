package com.spaceshooter.game.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.spaceshooter.game.util.Vector2f;

public abstract class GameObject {
	
	protected Vector2f position;
	protected Bitmap bitmap;
	//protected float x, y;
	
	protected int width, height;
	
	public GameObject(Vector2f position){
		this.position = position;
	}
	
	public abstract void tick(float dt);
	public abstract void draw(Canvas canvas, float interpolation);


	public Vector2f getPosition() {
		return position;
	}
	
	public float getX(){
		return position.getX();
	}
	
	public float getY(){
		return position.getY();
	}

	public Bitmap getBitmap() {
		return bitmap;
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

}
