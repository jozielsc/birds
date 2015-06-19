package com.jozielsc.game.airplane.objects;


import com.jozielsc.g4a.gl.Animation;
import com.jozielsc.g4a.gl.SpriteBatcher;
import com.jozielsc.g4a.gl.TextureRegion;
import com.jozielsc.game.birds.Assets;
import com.jozielsc.game.birds.World;

public class Hero extends DynamicObject {

	public enum HERO_STATE {
		FLY, HIT, UP;
	}

	public HERO_STATE state;

	public final int HERO_UP_VELOCITY = 4;

	public static final float HERO_WIDHT = 0.8f;
	public static final float HERO_HEIGH = 0.8f;
	public float stateTime;

	public Hero(float positionX, float positionY) {
		super(positionX, positionY, HERO_WIDHT, HERO_HEIGH);

		state = HERO_STATE.FLY;
		stateTime = 0;
	}

	public void update(float deltaTime) {
		velocity.add(0, World.gravity.y * deltaTime);
		position.add(0, velocity.y * deltaTime);

		bounds.lowerLeft.set(position);
		boundsCircle.center.set(position);
		
		if (velocity.y > 0 && state != HERO_STATE.HIT) {
			if (state != HERO_STATE.UP) {
				state = HERO_STATE.UP;
				stateTime = 0;
			}
		}

		if (velocity.y < 0 && state != HERO_STATE.HIT) {
			if (state != HERO_STATE.FLY) {
				state = HERO_STATE.FLY;
				stateTime = 0;
			}
		}

		stateTime += deltaTime;
	}

	public void renderer(SpriteBatcher batcher) {
		
		batcher.beginBatch(Assets.heroTexture);
		TextureRegion frame;
		switch (state) {
		case FLY:
			frame = Assets.heroAnimation.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
			break;
		default:
			frame = Assets.heroAnimation.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
			break;
		}

		batcher.drawSprite(position.x, position.y, HERO_WIDHT, HERO_HEIGH, frame);

		batcher.endBatch();

	}

	public void touch() {

		velocity.y = HERO_UP_VELOCITY * 1.5f;

		state = HERO_STATE.UP;

		stateTime = 0;
	}

	public void hit() {
		Assets.audioPlay.crash();
		velocity.set(0, 0);
		state = HERO_STATE.HIT;
		stateTime = 0;
	}

	public void down() {
		velocity.y = -1f;
		
	}
}
