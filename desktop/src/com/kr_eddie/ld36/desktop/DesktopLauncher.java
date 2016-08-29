package com.kr_eddie.ld36.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kr_eddie.ld36.LD36Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = LD36Game.V_WIDTH * LD36Game.SCALE;
		config.height = LD36Game.V_HEIGHT * LD36Game.SCALE;
		config.resizable = false;
		
		new LwjglApplication(new LD36Game(), config);
		
		
	}
}
