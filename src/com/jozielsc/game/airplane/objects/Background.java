package com.jozielsc.game.airplane.objects;

import com.jozielsc.g4a.gl.SpriteBatcher;
import com.jozielsc.game.birds.Assets;

public class Background extends DynamicObject{
	public final int BACKGROUND_VELOCITY = 2;
	
	public Background(float x, float y, float width, float height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		
	}
	
	public void update(float deltaTime){
		
		position.add(-(deltaTime), 0);
		
	}	
	
	public void renderer(SpriteBatcher batcher){
		batcher.beginBatch(Assets.cenario);
		batcher.drawSprite(position.x, position.y, bounds.width,
				bounds.height, Assets.backgroundRegion);
		batcher.endBatch();
	}

	public void setPositionX(float x) {
		position.x = x;
		bounds.lowerLeft.x = x;
		
	}
	
}
