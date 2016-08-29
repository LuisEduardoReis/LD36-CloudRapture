package com.kr_eddie.ld36.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.kr_eddie.ld36.Assets;
import com.kr_eddie.ld36.LD36Game;
import com.kr_eddie.ld36.LD36Game.GameKey;
import com.kr_eddie.ld36.Level;
import com.kr_eddie.ld36.RenderContext;
import com.kr_eddie.ld36.Util;
import com.kr_eddie.ld36.entities.enemies.Enemy;
import com.kr_eddie.ld36.entities.projectiles.DataBullet;
import com.kr_eddie.ld36.entities.projectiles.Projectile;
import com.kr_eddie.ld36.entities.projectiles.SoundFlare;
import com.kr_eddie.ld36.entities.projectiles.VideoTorpedo;

public class Player extends Entity {

	// Logic
	float data_timer, video_timer, sound_timer;
	float data_delay, video_delay, sound_delay;
	
	public boolean clamp;

	public Player(Level level) { 
		super(level); 
		// Logic
		data_timer = 0;
		video_timer = 0;
		sound_timer = 0;
		data_delay = 1 / 10f;
		video_delay = 1;
		sound_delay = 1 / 2f;
		
		hw = 6;
		hh = 6;
		
		clamp = true;
		// Render
		depth = -10;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		
		// Movement
		float speed = 128;
		if (Gdx.input.isKeyPressed(LD36Game.keybindings.get(GameKey.UP))) this.y += speed*delta;
		if (Gdx.input.isKeyPressed(LD36Game.keybindings.get(GameKey.DOWN))) this.y -= speed*delta;
		if (Gdx.input.isKeyPressed(LD36Game.keybindings.get(GameKey.RIGHT))) this.x += speed*delta;
		if (Gdx.input.isKeyPressed(LD36Game.keybindings.get(GameKey.LEFT))) this.x -= speed*delta;
		
		if (clamp) {
			float xb = 20, yb = 8;
			x = Util.clamp(x,xb,LD36Game.V_WIDTH-xb);
			y = Util.clamp(y,yb,LD36Game.V_WIDTH-yb);
		}

		data_timer = Util.stepTo(data_timer,0,delta);
		video_timer = Util.stepTo(video_timer,0,delta);
		sound_timer = Util.stepTo(sound_timer,0,delta);
		if (!level.gameover) {
			
			// Data bullets
			if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
				if(data_timer == 0) {
				
				float dir = Util.pointDirection(x,y,level.mouse_x,level.mouse_y);
								
				level.addEntity(new DataBullet(level, dir), (float) (x + 4*Math.cos(dir-Math.PI/2)), (float) (y + 4*Math.sin(dir-Math.PI/2)));
				level.addEntity(new DataBullet(level, dir), (float) (x - 4*Math.cos(dir-Math.PI/2)), (float) (y - 4*Math.sin(dir-Math.PI/2)));
				
				data_timer += data_delay;
				Assets.fire_data.play();
				}
			// Video torpedos
			} else if (Gdx.input.isKeyPressed(LD36Game.keybindings.get(GameKey.FIRE_TORPEDO)))	{
				if (video_timer == 0) {
				level.addEntity(new VideoTorpedo(level), x,y);
				video_timer += video_delay;
				Assets.fire_video.play();
				}
			// Sound flares
			} else if (Gdx.input.isButtonPressed(Buttons.RIGHT) || Gdx.input.isKeyPressed(LD36Game.keybindings.get(GameKey.FIRE_FLARES))) {
				if (sound_timer == 0) {
				for(int i = 0; i< 16; i++)
					level.addEntity(new SoundFlare(level),x,y);
				sound_timer += sound_delay;
				Assets.fire_sound.play();
				}
			}
		}
		
		// Anim
		Assets.player_animation.update(delta);
		
	}
	
	@Override
	public void render(RenderContext ctx) {
		if (da_timer > 0) ctx.batch.setColor(1, 1 - da_timer/da_delay, 1 - da_timer/da_delay, 1);
		
		Assets.player_animation.render(ctx, Math.round(x-16),Math.round(y-16));
		
		ctx.batch.setColor(color.r, color.g, color.b, db_timer > 1 ? 1 : db_timer/da_delay);
		ctx.batch.draw(Assets.spritesheet16[3][1], x-12, y+offy-12, 0,0, 24*health/100, 8, 1,1,0);
			
		ctx.batch.setColor(Color.WHITE);
	}
	
	@Override
	public void collision(Entity o) {
		super.collision(o);
		
		if (o instanceof Projectile) {
			Projectile p = (Projectile) o;
			if (p.friendly || o.remove) return;
			
			takeDamage(p.damage);
			o.remove = true;
		}
		
		if (o instanceof Enemy) {
			Enemy e = (Enemy) o;
			if (e.kamikaze) {
				health = Util.stepTo(health, 0, e.kamikaze_damage);
				e.remove = true;
			} else {
				takeDamage(5);
				o.takeDamage(25);
			}
		}
	}
	
	@Override
	public void takeDamage(float damage) {
		super.takeDamage(damage);
		Assets.hurt.play();
	}
	
	@Override
	public void die() {
		super.die();
		
		level.defeat();
		level.addEntity(new Explosion(level), x, y);
	}
}
