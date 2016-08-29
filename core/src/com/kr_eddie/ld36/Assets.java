package com.kr_eddie.ld36;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

	public static Texture testImg;
	
	public static Texture spritesheet, fillTexture;
	public static TextureRegion title, victory, defeat;
	public static TextureRegion[][] spritesheet16;
	public static TextureRegion[][] spritesheet32;
	
	public static Animation player_animation;
	
	public static Music main_music;
	public static Sound fire_data, fire_video, fire_sound, explosion, hurt; 
	
	public static BitmapFont default_font, font;
	
	public static void createAssets() {
		// Graphic
		testImg = new Texture(Gdx.files.internal("badlogic.jpg"));
		
		spritesheet = new Texture(Gdx.files.internal("spritesheet.png"));
		spritesheet16 = new TextureRegion[32][32];
		for(int i = 0; i < 32; i++) {
		for(int j = 0; j < 32; j++) {
			spritesheet16[i][j] = new TextureRegion(spritesheet, j*16, i*16, 16, 16);
		}}
		spritesheet32 = new TextureRegion[16][16];
		for(int i = 0; i < 16; i++) {
		for(int j = 0; j < 16; j++) {
			spritesheet32[i][j] = new TextureRegion(spritesheet, j*32, i*32, 32, 32);
		}}
		
		title = new TextureRegion(spritesheet, 0*32,5*32,7*32,3*32);
		victory = new TextureRegion(spritesheet, 0*32,9*32,8*32,2*32);
		defeat = new TextureRegion(spritesheet, 0*32,11*32,8*32,2*32);
		
		
		Pixmap p = new Pixmap(LD36Game.V_WIDTH, LD36Game.V_HEIGHT, Format.RGBA8888);
		p.setColor(1, 1, 1, 1);	p.fill();
		fillTexture = new Texture(p);
		
		// Animations
		player_animation = new Animation();
		player_animation.sprite_delay = 1/1.5f;
		player_animation.frames.add(Assets.spritesheet32[0][0]);
		player_animation.frames.add(Assets.spritesheet32[0][1]);
		player_animation.frames.add(Assets.spritesheet32[0][2]);
		
		// Sound
		main_music = Gdx.audio.newMusic(Gdx.files.internal("audio/main_music.mp3"));
		
		fire_data = Gdx.audio.newSound(Gdx.files.internal("audio/fire_data.wav"));
		fire_video = Gdx.audio.newSound(Gdx.files.internal("audio/fire_video.wav"));
		fire_sound = Gdx.audio.newSound(Gdx.files.internal("audio/fire_sound.wav"));
		explosion = Gdx.audio.newSound(Gdx.files.internal("audio/explosion.wav"));
		hurt = Gdx.audio.newSound(Gdx.files.internal("audio/hurt.wav"));
		
		// Fonts
		default_font = new BitmapFont();
		font = new BitmapFont(Gdx.files.internal("fonts/font.fnt")); 
	}
	
	public static void dispose() {
		testImg.dispose();
	}
	
	
	public static Color data_yellow = new Color(1,1,0.5f,1);
	public static Color video_cyan = new Color(0,0.5f,1f,1);
	public static Color sound_red = new Color(1,0,0,1);
	
	public static Color back_green = new Color(0,1,0,1);
	public static Color back_yellow = new Color(1,1,0,1);
	public static Color back_red = new Color(1,0,0,1);
	public static Color back_black = new Color(0.25f,0.25f,0.25f,1);

	public static Color[] levelColors = {
		back_green,back_green,back_green,back_green,
		back_yellow,back_yellow,back_yellow,back_yellow,
		back_red,back_red,back_red,back_red,
		back_black,back_black,back_black,back_black
	};

}
