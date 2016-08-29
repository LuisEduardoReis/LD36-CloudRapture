package com.kr_eddie.ld36;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.kr_eddie.ld36.entities.Entity;
import com.kr_eddie.ld36.entities.Player;
import com.kr_eddie.ld36.entities.enemies.Enemy;
import com.kr_eddie.ld36.entities.enemies.EnemySpawner;
import com.kr_eddie.ld36.entities.enemies.LevelSpawner;
import com.kr_eddie.ld36.screens.GameScreen;

public class Level {
	
	GameScreen game;
	
	public TiledMap map;
	public EnemySpawner spawner;
	
	public Player player;
	public ArrayList<Entity> entities;
	public ArrayList<Entity> newEntities;
	
	public int score, s_score;
	
	public Background background;
	
	public float mouse_x, mouse_y;

	public boolean gameover, victory;
	public float gameover_timer, gameover_delay;
	public float score_timer;
	
	
	public Level(GameScreen game, TiledMap map, Color color) {
		this.game = game;
		
		entities = new ArrayList<Entity>();
		newEntities = new ArrayList<Entity>();
		
		spawner = new LevelSpawner(this, map);
		
		this.player = new Player(this);
		addEntity(this.player, LD36Game.V_WIDTH/2, 32 );
				
		background = new Background(color);
		
		gameover = false;
		victory = false;
		gameover_timer = 0;
		gameover_delay = 5f;
		score_timer = -1;
	}

	public void update(float delta) {		
		// Spawn enemies
		spawner.update(delta);
		
		// Update misc
		background.update(delta);
		s_score = (int) Util.stepTo(s_score, score, Math.max(256,Math.abs(score-s_score))*delta);
		if (gameover) gameover_timer += delta;
		if (score_timer >= 0) 
			score_timer+=delta;
		else if (gameover_timer > gameover_delay) {
			score_timer = 0;
			s_score = 0;
		}
		
		// Update entities
		for(Entity e : entities) e.update(delta);
		mouse_x = ((float) Gdx.input.getX() / Gdx.graphics.getWidth()) * LD36Game.V_WIDTH;
		mouse_y = (1 - (float) Gdx.input.getY() / Gdx.graphics.getHeight()) * LD36Game.V_HEIGHT;
			
		// Add new entities
		for(Entity e : newEntities) entities.add(e);
		newEntities.clear();
		
		// Collisions
		for(Entity e : entities) {
			for(Entity o : entities) {
				if (e == o) continue;
				if (Util.aabbToaabb(e.x-e.hw, e.y-e.hh, 2*e.hw, 2*e.hh, o.x-o.hw, o.y-o.hh, 2*o.hw, 2*o.hh))
					e.collision(o);
		}}
		
		// Remove old entities
		for(int i = 0; i < entities.size(); i++) 
			if (entities.get(i).remove)
				entities.remove(i);
	}
	
	
	public void render(RenderContext ctx) {
		background.render(ctx);
		
		Collections.sort(entities, Entity.sortByDepth);
		for(Entity e : entities) e.render(ctx);	
		
		int w = LD36Game.V_WIDTH, h = LD36Game.V_HEIGHT;
			
		if (gameover) {
			if (victory) {
				ctx.batch.draw(Assets.victory, 
					w/2 - Assets.victory.getRegionWidth()/2, 
					(float) ((1.67 - Math.min(gameover_timer/gameover_delay, 1))*h - Assets.victory.getRegionHeight()/2));
			} else {
				ctx.batch.draw(Assets.defeat, 
					w/2 - Assets.defeat.getRegionWidth()/2, 
					(float) ((1.67 - Math.min(gameover_timer/gameover_delay, 1))*h - Assets.defeat.getRegionHeight()/2));
			}
			
			if (gameover_timer > gameover_delay) {
				Assets.font.getData().setScale(1.5f);
				Util.drawTextCentered(ctx.batch, Assets.font, s_score+" KB", w/2, h/2);
				Assets.font.getData().setScale(0.8f);
				Util.drawTextCentered(ctx.batch, Assets.font, "Press esc for Main Menu", w/2, h/4);
			}		
		} else {
			Assets.font.draw(ctx.batch, s_score+" KB", w-16, h-8, 10, 0, false);			
		}
	}
	
	public void renderDebug(RenderContext ctx) {
		for(Entity e : entities) e.renderDebug(ctx);
	}

	public void addEntity(Entity e, float x, float y) {
		e.x = x;
		e.y = y;
		addEntity(e);
	}

	public void addEntity(Entity e) {newEntities.add(e);}

	public void addScore(int scoreValue) {
		score += scoreValue;		
	}
	
	public void victory() {
		if (player == null || gameover) return;		
		
		System.out.println("Victory!");
		player.indestructible = true;
		player.clamp = false;
		player.ay = 128;
		
		LD36Game.unlockLevel(game.levelId+1);
		
		victory = true;
		gameover = true;
	}
	
	public void defeat() {
		if (player == null || gameover) return;	
		
		System.out.println("Defeat");
		
		victory = false;
		gameover = true;		
		player = null;
	}

	public int countEnemies() {
		int c = 0;
		for(Entity e : entities) if (e instanceof Enemy) c++; 	
		return c;
	}

	

	
	

}
