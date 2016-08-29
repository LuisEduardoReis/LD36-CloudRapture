package com.kr_eddie.ld36.entities.enemies;

import com.kr_eddie.ld36.LD36Game;
import com.kr_eddie.ld36.Level;
import com.kr_eddie.ld36.Util;

public class RandomEnemySpawner extends EnemySpawner {
	
	float timer, delay;
	
	public RandomEnemySpawner(Level level) {
		super(level);
		delay = 1.5f;
	}
	
	public void update(float delta) {
		timer += delta;
		
		while(timer > delay) {
			timer -= delay;
			
			try {
				
				Enemy e = Enemy.instanceEnemy(
						Enemy.Type.values()[Util.randomRangei(Enemy.Type.values().length)], 
						level);
				e.x = Util.randomRangef(32,LD36Game.V_WIDTH-32);
				e.y = LD36Game.V_HEIGHT+64;
				
				level.addEntity(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
	}

}
