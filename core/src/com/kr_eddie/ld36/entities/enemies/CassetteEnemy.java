package com.kr_eddie.ld36.entities.enemies;

import com.kr_eddie.ld36.Assets;
import com.kr_eddie.ld36.Level;
import com.kr_eddie.ld36.Util;

public class CassetteEnemy extends Enemy {

	public CassetteEnemy(Level level) {
		super(level);
		
		sprite = Assets.spritesheet32[1][6];
		
		offx = 16;
		offy = 16;
		offd = 90;
		
		dy = 0;	speed = 32;
		hw = 6;
		hh = 4;
		kamikaze = true;
		kamikaze_damage = 35;
		
		
		dmul_sound = 4;
		color = Assets.sound_red;
		
		scoreValue = 128;
	}
	
	@Override
	public void update(float delta) {
		if (level.player != null) 
			direction = Util.pointDirection(x, y, level.player.x, level.player.y);
		else
			direction = (float) (-Math.PI/2);
		
		super.update(delta);	
		
	}

}
