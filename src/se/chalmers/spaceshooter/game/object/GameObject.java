package se.chalmers.spaceshooter.game.object;

import se.chalmers.spaceshooter.game.util.Vector2f;

/**
 * The GamObject class which everything in the game inherits from such as the
 * player, enemies projectiles, particles and loot etc..
 * 
 * @author Anders
 * 
 */
public abstract class GameObject {
	// the position of the gameobject
	protected Vector2f position;
	// the width and height of the gameobject
	protected int width, height;
	// boolean to determine if the gameobject is dead or alive
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
