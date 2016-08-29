package com.kr_eddie.ld36.entities.projectiles;

import com.kr_eddie.ld36.Assets;
import com.kr_eddie.ld36.LD36Game;
import com.kr_eddie.ld36.Level;
import com.kr_eddie.ld36.entities.Entity;

public class Projectile extends Entity {

	public static enum Type {
		DATA, VIDEO, SOUND
	}
	
	public boolean friendly;
	public float damage;
	public Type type;
	
	public Projectile(Level level) {
		super(level);
		
		sprite = Assets.spritesheet16[2][1];
		offx = 8;
		offy = 8;
		offd = -90;
		
		hw = 4;
		hh = 4;
		
		friendly = true;
		damage = 25;
		
		type = Type.DATA;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		float b = 32;
		if (x < -b || x > LD36Game.V_WIDTH + b || y < -b || y > LD36Game.V_HEIGHT + b) this.remove = true;
	}

	
}
