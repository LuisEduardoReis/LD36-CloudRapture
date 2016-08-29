package com.kr_eddie.ld36.entities;

import java.util.Comparator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Affine2;
import com.kr_eddie.ld36.Assets;
import com.kr_eddie.ld36.Level;
import com.kr_eddie.ld36.RenderContext;
import com.kr_eddie.ld36.Util;

public class Entity {
	
	public Level level;
	
	// Logic
	public float x, y;
	public float dx, dy;
	public float ax, ay;
	
	public float health;
	public float da_timer, da_delay;
	public float db_timer, db_delay;
	public boolean indestructible;
	public boolean remove;
	
	public float hw, hh;
	public float speed, direction;
	
	//Render
	public TextureRegion sprite;
	public float offx, offy, offd;
	
	public Color color;
	
	public float depth;
	
	public Entity(Level level) {
		this.level = level;
		
		this.health = 100;
		this.da_timer = 0;
		this.da_delay = 1f;
		this.db_timer = 0;
		this.db_delay = 2.5f;
		this.indestructible = false;
		
		this.remove = false;
			
		this.hw = 8;
		this.hh = 8;
		
		this.speed = 0;		
		this.direction = (float) (Math.PI / 2);
		
		this.offx = 16;
		this.offy = 16;
		this.offd = 0;
		
		this.color = Color.WHITE;
	} 
	
	public void update(float delta) {
		this.dx += delta*ax;
		this.dy += delta*ay;
		
		this.x += delta*dx;
		this.y += delta*dy;
		
		if (speed != 0) {
			x += Math.cos(direction) * speed * delta;
			y += Math.sin(direction) * speed * delta;
		}
		
		da_timer = Util.stepTo(da_timer, 0, delta);
		db_timer = Util.stepTo(db_timer, 0, delta);
		
		if (health <= 0) die();
	}
	
	public void die() { remove = true; }

	public void collision(Entity o) { }
	
	public static Affine2 t = new Affine2();
	public static Color c = new Color();
	public void render(RenderContext ctx) {
		
		// Damage indication
		if (da_timer > 0) {
			c.set(Color.WHITE);
			c.add(1, 0, 0, da_timer/da_delay);
			ctx.batch.setColor(c);
		}
		
		// Sprite
		if (sprite != null) {
			t.idt();
			t.translate(Math.round(x),Math.round(y));
			if (direction != (float) (Math.PI / 2)) t.rotate(direction * Util.radToDeg + offd);
			t.translate(-offx, -offy);
			ctx.batch.draw(sprite, 2*offx,2*offy, t);
		}	

		// Health Bar
		ctx.batch.setColor(color.r, color.g, color.b, db_timer > 1 ? 1 : db_timer/da_delay);
		ctx.batch.draw(Assets.spritesheet16[3][1], x-12, y+offy-12, 0,0, 24*health/100, 8, 1,1,0);
		
		ctx.batch.setColor(Color.WHITE);
	}
	
	public void renderDebug(RenderContext ctx) {
		ctx.shape.begin(ShapeType.Line);
		ctx.shape.setColor(1, 1, 1, 1);
		ctx.shape.rect(x-hw, y-hh, 2*hw, 2*hh);
		ctx.shape.end();
	}
	
	
	public void takeDamage(float damage) {
		if (indestructible) return;
		
		health = Util.stepTo(health, 0, damage);
		da_timer = da_delay;
		db_timer = db_delay;
	}

	public static Comparator<Entity> sortByDepth = new Comparator<Entity>() {
		@Override
		public int compare(Entity a, Entity b) {
			return Float.compare(b.depth, a.depth);
		}
	};


}
