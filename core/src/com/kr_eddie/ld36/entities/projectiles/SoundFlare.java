package com.kr_eddie.ld36.entities.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.kr_eddie.ld36.Assets;
import com.kr_eddie.ld36.FadeEffect;
import com.kr_eddie.ld36.Level;
import com.kr_eddie.ld36.RenderContext;
import com.kr_eddie.ld36.Util;
import com.kr_eddie.ld36.entities.Entity;

public class SoundFlare extends Projectile {

	public float ks, kd, kf;
	public float igt, igd;
	public float acc, maxspeed, dirspeed;
	public Entity target;
	public FadeEffect fadeout;
	
	public SoundFlare(Level level) {
		super(level);
		
		sprite = Assets.spritesheet16[2][0];
		offx = 8;
		offy = 8;
		
		friendly = true;
		hw = 4;
		hh = 4;
		
		damage = 25;
		
		// kickback
		ks = Util.randomRangef(100, 200);
		kd = Util.randomRangef(0, (float) (2*Math.PI));
		kf = 200;
		
		// ignite delay
		igt = 0;
		igd = 0.5f;		
		
		// speed
		speed = 0;
		acc = 256;
		maxspeed = 256;
		
		// direction
		direction = (float) (Math.PI / 2);
		dirspeed = (float) (Math.PI);
				
		type = Type.SOUND;
		
		fadeout = new FadeEffect();
		fadeout.duration = 1f;
		fadeout.up = false;
		fadeout.startWithdelay(2f);
	}
	
	@Override
	public void update(float delta) {		
		// Kickback
		
		
		ks = Util.stepTo(ks, 0, delta*kf);
		x += ks*Math.cos(kd)*delta;
		y += ks*Math.sin(kd)*delta;
		
		// Active
		/*
		igt += delta;
		if (igt > igd) {
			// Find target
			if (target == null || target.remove) {

				float min = Float.MAX_VALUE;
				for(Entity e : level.entities) {
					if (!(e instanceof Enemy) || e.y > LD36Game.V_HEIGHT) continue;
					float d = Util.pointSquareDistance(x, y, e.x, e.y);
					if (d < min) {
						target = (Entity) e;
						d = min;
					}			
				}
			}
			speed = Util.stepTo(speed, maxspeed, delta*acc);
			
			// Steer
			if (target != null) {
				float td = Util.pointDirection(x, y, target.x, target.y);
				direction = Util.stepToDirection(direction, td, delta*dirspeed);
			}
		}*/
		fadeout.update(delta);
		super.update(delta);
		
		if (fadeout.getValue() == 0) remove = true;
	}
	
	@Override
	public void render(RenderContext ctx) {
		ctx.batch.setColor(Assets.sound_red.r,Assets.sound_red.g,Assets.sound_red.b,fadeout.getValue());
		super.render(ctx);
		ctx.batch.setColor(Color.WHITE);
	}
	

	
}
