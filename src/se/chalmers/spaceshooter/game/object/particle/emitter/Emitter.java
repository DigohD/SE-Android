package se.chalmers.spaceshooter.object.particle.emitter;


import se.chalmers.spaceshooter.engine.GameObjectManager;
import se.chalmers.spaceshooter.object.GameObject;
import se.chalmers.spaceshooter.object.Tickable;
import se.chalmers.spaceshooter.object.particle.ParticleID;
import se.chalmers.spaceshooter.util.Vector2f;

public abstract class Emitter extends GameObject implements Tickable{

	protected int particleCount, lifetime, timeLived;
	protected ParticleID pID;
	
	public Emitter(int particleCount, ParticleID pID, Vector2f position, int lifetime){
		super(position);
		this.particleCount = particleCount;
		this.pID = pID;
		this.lifetime = lifetime;
		timeLived = 0;
	}
	
	public void init(){
		GameObjectManager.addGameObject(this);
	}
	
	@Override
	public void tick(float dt) {
		timeLived++;
		if(timeLived > lifetime)
			this.closeEmitter();
		emit();
	}
	
	public abstract void emit();
	
	public void closeEmitter(){
		live = false;
	}
}
