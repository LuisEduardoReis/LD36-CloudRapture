package com.kr_eddie.ld36;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

public class Background {
	
	Random random = new Random();
	
	public boolean[] tiles;
	public float scroll;
	public int w, h, s;
	
	public float speed;
	
	Color color;
	
	public Background(Color color) {
		s = 32;
		w = (int) Math.ceil(LD36Game.V_WIDTH / s) + 2;
		h = (int) Math.ceil(LD36Game.V_HEIGHT / s) + 2;
		tiles = new boolean[2*w*h];
		
		for(int i = 0; i < 2*w*h; i++) tiles[i] = random.nextBoolean();
		
		speed = 16f;
		
		this.color = color;
	}

	
	public void update(float delta) {
		scroll += delta*speed;
	}
	
	public void render(RenderContext ctx) {
		for(int i = 0; i < h; i++) {
			for(int j = 0; j < w; j++) {
				int y = (int) (i + Math.floor(scroll/s))%h;
				
				
				boolean fx = tiles[(y*w + j)];
				boolean fy = tiles[w*h + (y*w + j)];
				
				Assets.spritesheet32[4][0].flip(fx, fy);
				ctx.batch.setColor(color);
				ctx.batch.draw(Assets.spritesheet32[4][0],
						Math.round((j-1)*s) , 
						Math.round((i)*s - scroll%s)
					);
				ctx.batch.setColor(Color.WHITE);
				Assets.spritesheet32[4][0].flip(fx, fy);			
				
			}
		}
	}
}
