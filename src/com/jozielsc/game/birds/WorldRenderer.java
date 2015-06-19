package com.jozielsc.game.birds;

import com.jozielsc.g4a.gl.Camera2D;
import com.jozielsc.g4a.gl.SpriteBatcher;

import com.jozielsc.g4a.interfaces.IGLGraphics;

public class WorldRenderer {

	public static final float FRUSTUM_WIDTH = 15;
	public static final float FRUSTUM_HEIGHT = 10;


	private IGLGraphics glGraphics;
	private World world;
	private Camera2D camera;
	private SpriteBatcher batcher;

	public WorldRenderer(IGLGraphics glGraphics, SpriteBatcher batcher,
			World world) {
		
		this.glGraphics = glGraphics;
		this.batcher = batcher;
		this.world = world;
		camera = new Camera2D(this.glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
	}

	public void renderer() {
		camera.setViewportAndMatrices();
		rendererBackground();
		rendererObjects();

	}

	private void rendererObjects() {
		rendererHero();
		rendererEnemy();
	}

	private void rendererHero() {

		world.hero.renderer(batcher);
	}
	
	private void rendererEnemy(){
		world.enemys.renderer(batcher);
	}

	private void rendererBackground() {

		world.background.renderer(batcher);
		world.foreground.renderer(batcher);
		
	}

}
