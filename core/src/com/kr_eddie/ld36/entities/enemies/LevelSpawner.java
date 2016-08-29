package com.kr_eddie.ld36.entities.enemies;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.kr_eddie.ld36.LD36Game;
import com.kr_eddie.ld36.Level;
import com.kr_eddie.ld36.Util;
import com.kr_eddie.ld36.entities.enemies.Enemy.Type;

public class LevelSpawner extends EnemySpawner {

	public class Spawn {
		public Enemy.Type type;
		public float x,y,t;
		
		public Spawn() {}
		public Spawn(Enemy.Type type) {
			this.type = type;
		}
		public Spawn(Enemy.Type type, float x, float y, float t) {
			this.type = type;
			this.x = x;
			this.y = y;
			this.t = t;
		}
	}

	
	
	public TiledMap map;
	public float w,h;
	public LinkedList<Spawn> spawns;
	
	public float victory_time;
	private boolean sidemotion;

	public LevelSpawner(Level level, TiledMap map) {
		super(level);
		this.map = map;
		this.w = (Integer) map.getProperties().get("width");
		this.h = (Integer) map.getProperties().get("height");
		this.spawns = new LinkedList<Spawn>();
	
		MapObjects spawnsObj = map.getLayers().get("spawns").getObjects();
		for (MapObject so : spawnsObj) {
			Type t;	try {		
				t = Type.valueOf((String) so.getProperties().get("type"));
			} catch(Exception e) {
				e.printStackTrace();
				continue; 
			}
			
			Spawn s = new Spawn(t);
			s.x = ((Float) so.getProperties().get("x"))+24;
			s.y = LD36Game.V_HEIGHT + 24;
			s.t = ((Float) so.getProperties().get("y"))/32;
			spawns.add(s);			
		}
		
		MapObjects controlObjs = map.getLayers().get("control").getObjects();
		for(MapObject co : controlObjs) {
			if ("VICTORY".equals(co.getName())) victory_time = ((Float) co.getProperties().get("y"))/32;
			if ("SIDEMOTION".equals(co.getName())) sidemotion = true;
		}
		
		
		Collections.sort(spawns, sortByT);
	}
	
	public static Comparator<Spawn> sortByT = new Comparator<LevelSpawner.Spawn>() {
		@Override
		public int compare(Spawn o1, Spawn o2) {
			return Float.compare(o1.t, o2.t);
		}
	};
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		if (victory_time <= time && level.countEnemies() == 0) level.victory();
		
		while(spawns.size() > 0 && spawns.peek().t <= time) {
			Spawn spawn = spawns.removeFirst();		
			Enemy e = Enemy.instanceEnemy(spawn.type, level);
			if (sidemotion && e instanceof SmallFloppyDiskEnemy) e.dx += Util.randomRangef(-32, 32);
			level.addEntity(e, spawn.x, spawn.y);
		}
	}

}
