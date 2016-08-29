package com.kr_eddie.ld36.entities.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.kr_eddie.ld36.Assets;
import com.kr_eddie.ld36.Level;
import com.kr_eddie.ld36.RenderContext;

public class EnemyDataBullet extends Projectile {

	public float flickertimer, flickerduration;
	
	public EnemyDataBullet(Level level, float dir, Color color) {
		super(level);
		
		sprite = Assets.spritesheet16[2][1];
		offx = 8;
		offy = 8;
		
		friendly = false;
		hw = 2;
		hh = 2;
		
		damage = 25;
		speed = 64;
		direction = dir;
		
		flickertimer = 0;
		flickerduration = 1/4f;
		
	
		type = Type.DATA;
		this.color = color;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		flickertimer += delta; flickertimer %= flickerduration;
	}
	
	@Override
	public void render(RenderContext ctx) {
		if (flickertimer > flickerduration/2) 
			ctx.batch.setColor(color);
		else
			ctx.batch.setColor(Color.WHITE);
		
		super.render(ctx);
		ctx.batch.setColor(Color.WHITE);
	}
}
