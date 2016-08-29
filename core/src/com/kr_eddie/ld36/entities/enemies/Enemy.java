package com.kr_eddie.ld36.entities.enemies;

import com.kr_eddie.ld36.LD36Game;
import com.kr_eddie.ld36.Level;
import com.kr_eddie.ld36.Util;
import com.kr_eddie.ld36.entities.Entity;
import com.kr_eddie.ld36.entities.Explosion;
import com.kr_eddie.ld36.entities.projectiles.Projectile;

public class Enemy extends Entity {

	public static enum Type {
		CASSETTE, SFLOPPYDISK, VHSTAPE
	}
	public static Enemy instanceEnemy(Type type, Level level){
		switch(type)  {
		case SFLOPPYDISK: return new SmallFloppyDiskEnemy(level);
		case CASSETTE:	return new CassetteEnemy(level); 
		case VHSTAPE:	return new VHSTapeEnemy(level); 
		default: return new SmallFloppyDiskEnemy(level);
		}
	}
	
	public float shoottimer, shotdelay;
	
	public float dmul_data;
	public float dmul_video;
	public float dmul_sound;
	
	public boolean kamikaze;
	public float kamikaze_damage;
	
	public int scoreValue;
	
	public Enemy(Level level) {
		super(level);
		
		shoottimer = 0;
		shotdelay = 1f;
		
		dmul_data = 1f;
		dmul_video = 1f;
		dmul_sound = 0.25f;
		
		kamikaze = false;
		kamikaze_damage = 0;
		
		dy = -32;
		
		scoreValue = 100;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		shoottimer = Util.stepTo(shoottimer, 0, delta);
		
		float b = 8;
		if (x < b) {x = b; dx = -dx;}
		if (x > LD36Game.V_WIDTH-b) {x = LD36Game.V_WIDTH-b; dx = -dx;}
		
		if (y < -32) remove = true;		
	}

	@Override
	public void collision(Entity o) {
		super.collision(o);
		
		if (o instanceof Projectile) {
			Projectile p = (Projectile) o;
			if (!p.friendly || o.remove) return;
			
			float damage = p.damage;
			switch(p.type) {
				case DATA: damage *= dmul_data; break;
				case SOUND: damage *= dmul_sound; break;
				case VIDEO: damage *= dmul_video; break;
			}
					
			
			takeDamage(damage);
			o.remove = true;
		}
	}

	@Override
	public void die() {
		super.die();
		level.addScore(scoreValue);
		level.addEntity(new Explosion(level), x, y);
	}
	
}
