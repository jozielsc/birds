package com.jozielsc.game.airplane.objects;

import com.jozielsc.g4a.gl.Animation;
import com.jozielsc.g4a.gl.SpriteBatcher;
import com.jozielsc.g4a.gl.TextureRegion;
import com.jozielsc.game.birds.Assets;

public class Enemy extends DynamicObject{


	public boolean isVisible;
	public boolean isIvited;
	private float stateTime;
	
	public Enemy(float x, float y, float width, float height) {
		super(x, y, width, height);
		isVisible = false;
		isIvited = false;
		stateTime=0;
	}
	
	public void update(float deltaTime){
		if(isVisible){
			stateTime+=deltaTime;
		}
		position.add(-(deltaTime), 0);
		bounds.lowerLeft.set(position);
		boundsCircle.center.set(position);
	}
	
	public void renderer(SpriteBatcher batcher){
		batcher.beginBatch(Assets.enemyTexture);
		
		TextureRegion frame = Assets.enemyAnimation.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
	
		batcher.drawSprite(position.x, position.y, bounds.width, bounds.height,frame);
		
		batcher.endBatch();
	
	}
}
