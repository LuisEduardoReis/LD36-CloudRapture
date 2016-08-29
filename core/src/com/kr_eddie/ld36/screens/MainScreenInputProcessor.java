package com.kr_eddie.ld36.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.kr_eddie.ld36.Util;
import com.kr_eddie.ld36.screens.MainScreen.Button;

public class MainScreenInputProcessor implements InputProcessor {


	private MainScreen screen;
	public MainScreenInputProcessor(MainScreen screen) {
		this.screen = screen;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	Vector3 v = new Vector3();
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		if (button == Input.Buttons.LEFT) {
			v.set(screenX, screenY, 0);
			screen.camera.unproject(v);
			
			
			for(Button b : screen.levelButtons) {
				if (!b.locked && Util.aabbToaabb(v.x, v.y, 0, 0, b.x, b.y, b.w, b.h) && !screen.transitioning) {
					System.out.println("Starting level " + b.id);
					screen.targetLevel = b.id;
					screen.fadeout.start();
					screen.transitioning = true;
				}	
			}
			
			Button a = screen.arrow_left;
			if (Util.aabbToaabb(v.x, v.y, 0, 0, a.x, a.y, a.w, a.h))
				screen.subscreen = Math.max(screen.subscreen-1, screen.smin);
			
			a = screen.arrow_right;
			if (Util.aabbToaabb(v.x, v.y, 0, 0, a.x, a.y, a.w, a.h))
				screen.subscreen = Math.min(screen.subscreen+1, screen.smax);
									
		}	
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		v.set(screenX, screenY, 0);
		screen.camera.unproject(v);
				
		for(Button b : screen.levelButtons) {
			b.hovered = Util.aabbToaabb(v.x, v.y, 0, 0, b.x, b.y, b.w, b.h);
		}
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
