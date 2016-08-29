package com.kr_eddie.ld36.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kr_eddie.ld36.Assets;
import com.kr_eddie.ld36.Level;

public class Explosion extends Entity {

	
	int anim_index, anim_timer, anim_delay, anim_length; 
	ArrayList<TextureRegion> anim;
	
	public Explosion(Level level) {
		super(level);
		
		anim = new ArrayList<TextureRegion>();
		for(int i = 0; i < 8; i++)	anim.add(Assets.spritesheet32[3][i]);
		offx = 16;
		offy = 16;
		
		anim_index = 0;
		anim_timer = 0;
		anim_delay = 3;
		anim_length = 7;
		
		Assets.explosion.setVolume(Assets.explosion.play(), 0.5f);
	}
	
	public void update(float delta) {
		anim_timer++;
		while(anim_timer >= anim_delay) {
			anim_index = Math.min(anim_index+1, 7);
			anim_timer -= anim_delay;
		}		
		
		if (anim_index == 7) remove = true;
		sprite = anim.get(anim_index);
	}
}
