package com.example.se_android;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameWorld{

	private World world; 
	private Body body;
	
	public GameWorld(){
		Vec2 gravity = new Vec2(0.0f, -10.0f);
		boolean doSleep = true;
		world = new World(gravity, doSleep);
		
		//body definition
		BodyDef bd = new BodyDef();
		bd.position.set(50, 50); 
		bd.type = BodyType.DYNAMIC;
		 
		//define shape of the body.
		CircleShape cs = new CircleShape();
		cs.m_radius = 5f; 
		 
		//define fixture of the body.
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		fd.density = 0.5f;
		fd.friction = 0.3f;       
		fd.restitution = 0.5f;
		 
		//create the body and add fixture to it
		body =  world.createBody(bd);
		body.createFixture(fd);
	}
	
	public void draw(Canvas canvas){
		Paint paint = new Paint(Color.MAGENTA);
		canvas.drawCircle(body.getPosition().x, body.getPosition().y, 5, paint);
	}
	
}
