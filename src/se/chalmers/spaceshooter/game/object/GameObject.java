package se.chalmers.spaceshooter.game.object;

import se.chalmers.spaceshooter.game.util.Vector2f;

public abstract class GameObject {

	protected Vector2f position;
	protected int width, height;
	protected boolean live = true;

	public GameObject(Vector2f position) {
		this.position = position;
	}

	public int getHeight() {
		return height;
	}

	public Vector2f getPosition() {
		return position;
	}

	public int getWidth() {
		return width;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public boolean isLive() {
		return live;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
