package com.kr_eddie.ld36.entities.enemies;

import com.kr_eddie.ld36.Assets;
import com.kr_eddie.ld36.Level;
import com.kr_eddie.ld36.entities.projectiles.EnemyDataBullet;

public class SmallFloppyDiskEnemy extends Enemy {

	public SmallFloppyDiskEnemy(Level level) {
		super(level);
	
		sprite = Assets.spritesheet32[0][6];
		
		offx = 16;
		offy = 16;
		
		hw = 6;
		hh = 6;
		
		shotdelay = 2f;
	
		dmul_data = 1.5f;
		color = Assets.data_yellow;
		
		scoreValue = 256;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		if (shoottimer == 0) {
			
			level.addEntity(new EnemyDataBullet(level, (float) (-Math.PI/2), Assets.data_yellow), x,y);
			shoottimer += shotdelay;
			
		}
	}

}
