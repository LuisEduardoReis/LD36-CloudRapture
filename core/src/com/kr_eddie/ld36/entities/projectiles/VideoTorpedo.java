package com.kr_eddie.ld36.entities.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.kr_eddie.ld36.Assets;
import com.kr_eddie.ld36.Level;
import com.kr_eddie.ld36.RenderContext;

public class VideoTorpedo extends Projectile {

	public VideoTorpedo(Level level) {
		super(level);
		
		sprite = Assets.spritesheet16[3][0];
		offx = 8;
		offy = 8;
		
		friendly = true;
		hw = 4;
		hh = 4;
		
		damage = 150;
		dy = 128;
		
		type = Type.VIDEO;
	}
	
	@Override
	public void render(RenderContext ctx) {
		ctx.batch.setColor(Assets.video_cyan);
		super.render(ctx);
		ctx.batch.setColor(Color.WHITE);
	}

}
