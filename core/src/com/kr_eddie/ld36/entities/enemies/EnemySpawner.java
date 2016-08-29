package com.kr_eddie.ld36.entities.enemies;

import com.kr_eddie.ld36.Level;

public class EnemySpawner {
	
	public float time;
	public Level level;
	
	public EnemySpawner(Level level) {
		this.level = level;
		time = 0;
	}

	public void update(float delta) {
		time += delta;
	}
}
