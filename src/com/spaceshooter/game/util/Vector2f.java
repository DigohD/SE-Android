package com.spaceshooter.game.util;


public class Vector2f {
	
	private float x;
	private float y;
	
	public Vector2f(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public float length(){
		return (float)Math.sqrt(x*x + y*y);
	}
	
	public float dot(Vector2f v){
		return x * v.getX() + y * v.getY();
	}
	
	public Vector2f normalize(){
		float length = length();
		return new Vector2f(x / length, y / length);
	}
	
	public Vector2f rotate(float angle){
		
		float rad = (float)Math.toRadians(angle);
		float cos = (float)Math.cos(rad);
		float sin = (float)Math.sin(rad);
		
		return new Vector2f(x * cos - y * sin, x * sin + y * cos);
	}
	
	public Vector2f add(Vector2f v){
		return new Vector2f(x + v.getX(), y + v.getY());
	}
	
	public Vector2f add(float scalar){
		return new Vector2f(x + scalar, y + scalar);
	}
	
	public Vector2f sub(Vector2f v){
		return new Vector2f(x - v.getX(), y - v.getY());
	}
	
	public Vector2f sub(float scalar){
		return new Vector2f(x - scalar, y - scalar);
	}
	
	public Vector2f mul(Vector2f v){
		return new Vector2f(x * v.getX(), y * v.getY());
	}
	
	public Vector2f mul(float scalar){
		return new Vector2f(x * scalar, y * scalar);
	}
	
	public Vector2f div(Vector2f v){
		return new Vector2f(x / v.getX(), y / v.getY());
	}
	
	public Vector2f div(float scalar){
		return new Vector2f(x / scalar, y / scalar);
	}
	
	@Override
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
