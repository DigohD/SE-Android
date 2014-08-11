package se.chalmers.spaceshooter.game.object;

/**
 * An Interface which all gameobjects that should be updated every frame must
 * implement
 * 
 * @author Anders
 * 
 */
public interface Tickable {
	public void tick(float dt);
}
