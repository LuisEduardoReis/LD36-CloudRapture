package com.kr_eddie.ld36.entities.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.kr_eddie.ld36.Assets;
import com.kr_eddie.ld36.Level;
import com.kr_eddie.ld36.RenderContext;

public class DataBullet extends Projectile {

	public DataBullet(Level level, float dir) {
		super(level);
				
		sprite = Assets.spritesheet16[2][1];
		offx = 8;
		offy = 8;
		
		friendly = true;
		hw = 4;
		hh = 4;
		
		damage = 25;
		speed = 256;
		direction = dir;
		
		type = Type.DATA;
		color = Assets.data_yellow;
	}
	
	@Override
	public void render(RenderContext ctx) {
		ctx.batch.setColor(color);
		super.render(ctx);
		ctx.batch.setColor(Color.WHITE);
	}

}
