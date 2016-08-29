package com.kr_eddie.ld36.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kr_eddie.ld36.LD36Game;
import com.kr_eddie.ld36.RenderContext;

public class Screen extends ScreenAdapter {

	protected LD36Game game;
	protected OrthographicCamera camera;
	protected Viewport viewport;
	protected SpriteBatch batch;
	protected ShapeRenderer shapeRenderer;
	protected RenderContext renderContext;

	@Override
	public void show() {
		super.show();
		
		
		// Render	
		camera = new OrthographicCamera();
		viewport = new FitViewport(LD36Game.V_WIDTH, LD36Game.V_HEIGHT, camera);
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		renderContext = new RenderContext();
		renderContext.batch = batch;
		renderContext.shape = shapeRenderer;
	}

	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

}
