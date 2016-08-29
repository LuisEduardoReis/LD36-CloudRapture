package com.kr_eddie.ld36;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {

	public ArrayList<TextureRegion> frames;
	
	public boolean loop;
	public int sprite_index;
	public float sprite_timer, sprite_delay;
	
	public Animation() {
		
		sprite_timer = 0;
		sprite_index = 0;
		sprite_delay = 1f;
		loop = true;
		frames = new ArrayList<TextureRegion>();
	}
	
	public void update(float delta) {
		sprite_timer += delta;
		while(sprite_timer > sprite_delay) {
			sprite_timer -= sprite_delay;
			sprite_index++;
			if (loop) {sprite_index %= frames.size();}
			else sprite_index = Math.min(sprite_index,frames.size()-1);
		}
	}
	
	public void render(RenderContext ctx, float x, float y) {
		
		ctx.batch.draw(frames.get(sprite_index), x,y);
		
	}
	
	
}
