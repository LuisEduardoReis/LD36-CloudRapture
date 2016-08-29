package com.kr_eddie.ld36;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.kr_eddie.ld36.screens.MainScreen;

public class LD36Game extends Game {
	public static final int V_WIDTH = 240;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 3;
	private static final String PREFS_NAME = "LD36_CLOUD_RAPTURE";
	
	public static boolean DEBUG = false;
	public static boolean PAUSE = false;

	public enum GameKey {
		UP,DOWN,LEFT,RIGHT,FIRE_TORPEDO, FIRE_FLARES
	}
	
	
	private static Preferences preferences;
	public static Preferences getPrefs() {
		if (preferences == null) {preferences = Gdx.app.getPreferences(PREFS_NAME);};
		return preferences;
	}
	public static int getUnlockedLevels() {
		Preferences p = getPrefs();
		if (!p.contains("UNLOCKED_LEVELS")) p.putInteger("UNLOCKED_LEVELS",1);
		return p.getInteger("UNLOCKED_LEVELS");
	}
	public static void unlockLevel(int l) {
		int c = getUnlockedLevels();
		if (l > c) {
			getPrefs().putInteger("UNLOCKED_LEVELS",l);
			getPrefs().flush();
		}	
		
	}
	
	
	public static HashMap<GameKey, Integer> keybindings;
	static {
		keybindings = new HashMap<GameKey, Integer>();
		keybindings.put(GameKey.UP, Keys.W);
		keybindings.put(GameKey.DOWN, Keys.S);
		keybindings.put(GameKey.LEFT, Keys.A);
		keybindings.put(GameKey.RIGHT, Keys.D);
		keybindings.put(GameKey.FIRE_TORPEDO, Keys.SHIFT_LEFT);
		keybindings.put(GameKey.FIRE_FLARES, Keys.SPACE);
	}
	
	
	@Override
	public void create () {
		Assets.createAssets();
		
		Assets.main_music.setLooping(true);
		Assets.main_music.play();
		
		setScreen(new MainScreen(this));
	}
	

}
