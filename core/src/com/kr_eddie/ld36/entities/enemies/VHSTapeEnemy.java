package com.kr_eddie.ld36.entities.enemies;

import com.kr_eddie.ld36.Assets;
import com.kr_eddie.ld36.Level;
import com.kr_eddie.ld36.Util;
import com.kr_eddie.ld36.entities.projectiles.EnemyDataBullet;

public class VHSTapeEnemy extends Enemy {

	public VHSTapeEnemy(Level level) {
		super(level);
		
		sprite = Assets.spritesheet32[2][6];
		
		offx = 16;
		offy = 16;
		
		hw = 14;
		hh = 6;
		
		shotdelay = 2f;

		dy = -16;
		
		dmul_video = 1;
		dmul_sound = 0.25f;
		dmul_data = 0.1f;
		color = Assets.video_cyan;
		
		scoreValue = 1024;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		if (shoottimer == 0 && level.player != null) {
			
			float dir = Util.pointDirection(x, y, level.player.x, level.player.y);
			level.addEntity(new EnemyDataBullet(level,  dir, Assets.video_cyan), x,y);
			shoottimer += shotdelay;			
		}
	}

}
