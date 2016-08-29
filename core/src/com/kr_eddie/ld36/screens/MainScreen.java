package com.kr_eddie.ld36.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.kr_eddie.ld36.Assets;
import com.kr_eddie.ld36.Background;
import com.kr_eddie.ld36.FadeEffect;
import com.kr_eddie.ld36.LD36Game;
import com.kr_eddie.ld36.LD36Game.GameKey;
import com.kr_eddie.ld36.Util;

public class MainScreen extends Screen {
	
	public MainScreen(LD36Game game) { this.game = game; }

	Background background;
	
	int subscreen, smin, smax;
	float scroll, timer;
	FadeEffect fadeout;
	
	boolean transitioning;
	int targetLevel;
	
	public class Button {
		int x,y;
		int w,h;
		
		int id;
		boolean hovered, locked;
	}
	public ArrayList<Button> levelButtons;
	public Button arrow_left, arrow_right;
	
	public int unlocked_levels;
	
	@Override
	public void show() {
		super.show();
		
		background = new Background(Assets.back_green);
		
		subscreen = 0;
		smin = -1;
		smax = 1;
		scroll = 0;
		
		timer = 0;
		
		fadeout = new FadeEffect();
		fadeout.duration = 1.5f;
		
		unlocked_levels = LD36Game.getUnlockedLevels();
		
		int w = LD36Game.V_WIDTH, h = LD36Game.V_HEIGHT; 
		levelButtons = new ArrayList<Button>();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				Button button = new Button();
				button.x = j*48+32 + w;
				button.y = (4-i)*48-16;
				button.w = 32; button.h = 32;
				button.id = i*4 + j + 1;
				button.locked = button.id > unlocked_levels;
				levelButtons.add(button);
			}
		}			
		arrow_left = new Button();
		arrow_left.x = 64-16; arrow_left.y = h/4-16;
		arrow_left.w = 32; arrow_left.h = 32;
		
		arrow_right = new Button();
		arrow_right.x = w-64-16; arrow_right.y = h/4-16;
		arrow_right.w = 32; arrow_right.h = 32;
		
		transitioning = false;
		targetLevel = -1;
		
		Gdx.input.setInputProcessor(new MainScreenInputProcessor(this));
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		timer += delta;
		Assets.player_animation.update(delta);
		background.update(delta);
		fadeout.update(delta);
		
		if (!fadeout.started) {
			if (Gdx.input.isKeyJustPressed(Keys.LEFT) || Gdx.input.isKeyJustPressed(LD36Game.keybindings.get(GameKey.LEFT)))
				subscreen = Math.max(subscreen-1, smin);
			if (Gdx.input.isKeyJustPressed(Keys.RIGHT) || Gdx.input.isKeyJustPressed(LD36Game.keybindings.get(GameKey.RIGHT))) 
				subscreen = Math.min(subscreen+1, smax);
		} else if (fadeout.getValue() == 1){
			if (targetLevel != -1) game.setScreen(new GameScreen(game, targetLevel, Assets.levelColors[targetLevel-1]));
		}

		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) Gdx.app.exit();
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) game.setScreen(new SettingsScreen(game));
		
		
		scroll = Util.stepTo(scroll, subscreen, 4*delta);
		
		// Render
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
			
			viewport.apply();
			batch.enableBlending();
			int w = LD36Game.V_WIDTH, h = LD36Game.V_HEIGHT; 
			
			// Background
			camera.position.set(w/2, h/2, 0); camera.update();		
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			background.render(renderContext);
			batch.end();
			
			// Menus
			camera.position.set(w/2 + w*scroll, h/2, 0); camera.update();
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
				// Main menu
				batch.draw(Assets.title, 
						w/2 - Assets.title.getRegionWidth()/2, 
						0.67f*h - Assets.title.getRegionHeight()/2 + Util.pulse(timer,1,0.5f,0.75f));
				
				Assets.font.getData().setScale(0.5f);
				Util.drawTextCentered(batch, Assets.font, "Guide", 	arrow_left.x+16, 		h/4+26 + Util.pulse(timer,1,0.5f,0.25f));
				batch.draw(Assets.spritesheet32[4][2], 	arrow_left.x, arrow_left.y + Util.pulse(timer,1,0.5f,0));
				
				Util.drawTextCentered(batch, Assets.font, "Play", 		arrow_right.x+16,		h/4+26 + Util.pulse(timer,1,0.5f,0.25f));
				batch.draw(Assets.spritesheet32[4][1], 	arrow_right.x,	arrow_right.y + Util.pulse(timer,1,0.5f,0));
				
				Util.drawTextCentered(batch, Assets.font, "Press Enter to setup controls", 		w/2,		16+ Util.pulse(timer,1,0.5f,0.5f));
				
				
				// Tutorial
				Assets.player_animation.render(renderContext, 32 - w, h/3-16);
				Assets.font.draw(batch, "This is you.", 32 - w, h/3+24);
				
				
				batch.draw(Assets.spritesheet32[0][6], w-32-48-32 - w, h/3-16);
				batch.draw(Assets.spritesheet32[2][6], w-32-48 - w, h/3-16);
				batch.draw(Assets.spritesheet32[1][6], w-32-48+32 - w, h/3-16);
				Util.drawTextCentered(batch, Assets.font, "These are your enemies",  w-32-32 - w, h/3+24);
				
				batch.setColor(Assets.sound_red);
				batch.draw(Assets.spritesheet16[2][0], w/2-8 -w - 16, 16);
				batch.setColor(Assets.data_yellow);
				batch.draw(Assets.spritesheet16[2][1], w/2-8 -w, 16);
				batch.setColor(Assets.video_cyan);
				batch.draw(Assets.spritesheet16[3][0], w/2-8 -w + 16, 16);
				batch.setColor(Color.WHITE);
				Util.drawTextCentered(batch, Assets.font, "These are your weapons",  w/2 - w, 48);
				
				Assets.font.getData().setScale(0.5f);
				Assets.font.draw(batch,
						"Physical storage is no more!"
						+ " Discard ancient technologies and let your data ascend to the magnificent cloud.\n"
						+ " Each weapon is more efficient when it deals damage to enemies of the same medium.\n"
						+ "Mediums are: \n "
						+ "- Data (yellow)\n"
						+ "- Video (blue)\n"
						+ "- Audio (red)"
						
						, -w+24, h - 16, w - 48, 1 , true);
				
				// Levels
				for(Button b : levelButtons) {
					if (b.locked) batch.setColor(Color.BLACK);
					else if (b.hovered) batch.setColor(Assets.levelColors[b.id-1]);
					batch.draw(Assets.spritesheet32[8][b.id-1], b.x,b.y);
					batch.setColor(Color.WHITE);
				}
				// Fadeout
				if (fadeout.getValue() > 0) {
					camera.position.set(0, 0, 0); camera.update();		
					batch.setProjectionMatrix(camera.combined);
					fadeout.render(batch,-w/2,-h/2,w,h);
				}
			batch.end();
		
		
	}
	
}
