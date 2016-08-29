package com.kr_eddie.ld36.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.kr_eddie.ld36.FadeEffect;
import com.kr_eddie.ld36.LD36Game;
import com.kr_eddie.ld36.Level;

public class GameScreen extends Screen {
	
	public int levelId;
	public Color color;
	
	public GameScreen(LD36Game game, int levelId, Color color) {this.game = game; this.levelId = levelId; this.color = color;}
	
	// Logic
	Level level;
	public TiledMap map;
	
	FadeEffect fadein, fadeout;
	
	// Init
	@Override
	public void show() {
		super.show();
	
		// Logic
		map = new TmxMapLoader(new InternalFileHandleResolver()).load("levels/level"+levelId+".tmx");
		
		level = new Level(this, map, color);
	
		fadein = new FadeEffect();
		fadein.duration = 1.5f;
		fadein.up = false;
		fadein.started = true;
		
		fadeout = new FadeEffect();
		fadeout.duration = 2f;
	}
	
	
		@Override
	public void render(float delta) {
		super.render(delta);
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			game.setScreen(new MainScreen(game));
		}
		if (Gdx.input.isKeyJustPressed(Keys.O)) LD36Game.DEBUG = !LD36Game.DEBUG;
		if (Gdx.input.isKeyJustPressed(Keys.P)) LD36Game.PAUSE = !LD36Game.PAUSE;
		
		// Logic
		delta = Math.min(delta, 0.25f);
		
		if (!LD36Game.PAUSE) level.update(delta);
		
		fadein.update(delta); fadeout.update(delta);
		if (level.gameover && !fadeout.started) fadeout.startWithdelay(3);
		
		// Render
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		int w = LD36Game.V_WIDTH, h = LD36Game.V_HEIGHT; 
		viewport.apply();
		camera.position.set(w/2, h/2, 0);
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.enableBlending();
		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.begin();
			level.render(renderContext);
			
			if (fadein.getValue() > 0) {
				fadein.render(batch, 0, 0, w, h);
			}
		batch.end();
		
		if (LD36Game.DEBUG) level.renderDebug(renderContext);
		
	}
	



}
