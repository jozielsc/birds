package com.jozielsc.game.airplane.objects;

import com.jozielsc.g4a.gl.SpriteBatcher;
import com.jozielsc.g4a.gl.TextureRegion;
import com.jozielsc.game.birds.Assets;
import com.jozielsc.game.birds.World;

public class Foreground extends DynamicObject{
	public final int FOREGROUND_VELOCITY = 1;
	
	public Foreground(float x, float y, float width, float height) {
		super(x, y, Math.abs(width), height);
	
	}
	
	public void update(float deltaTime){
		
		position.add(-(deltaTime + World.velocity.x), 0);
		
		bounds.lowerLeft.set(position);
	}	
	
	public void renderer(SpriteBatcher batcher){
		batcher.beginBatch(Assets.cenario);
		
		TextureRegion frame;
		frame = Assets.foregroundDown;
		
		batcher.drawSprite(position.x, position.y, bounds.width,
				bounds.height, frame);
		
		batcher.endBatch();
		
	}
	
}
