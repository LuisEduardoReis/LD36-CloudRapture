package com.kr_eddie.ld36;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FadeEffect {
	
	public float t, duration;
	public boolean up, started;
	
	public FadeEffect() {
		reset();
		duration = 1;
		up = true;		
	}
	
	public void startWithdelay(float delay) {
		t = -delay;
		started = true;
	}
	
	public void reset() {
		t = 0;
		started = false;
	}
	
	public void update(float delta) {
		if (!started) return;
		
		t += delta;
	}
	
	public float getValue() {
		float v = Math.max(0,Math.min(t / duration, 1));
		return up ? v : 1-v;
	}

	public void render(SpriteBatch batch, float x, float y, float w, float h) {
		batch.setColor(0,0,0,getValue());
		batch.draw(Assets.fillTexture, x,y,w,h);
		batch.setColor(Color.WHITE);		
	}

	public void start() {
		started = true;		
	}
}
