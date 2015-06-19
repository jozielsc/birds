package com.jozielsc.game.airplane.objects;

import java.util.ArrayList;
import java.util.List;

import com.jozielsc.g4a.gl.SpriteBatcher;

public class ControllerBackground {

	List<Background> backgrounds;
	
	private final float WIDTH;
	
	
	public ControllerBackground(float x, float y, float width, float height){
		WIDTH = width;
		backgrounds = new ArrayList<Background>();
		Background back = new Background(x, y, width, height);
		backgrounds.add(back);
		backgrounds.add(new Background(back.position.x + width, y, -width, height));
	}
	
	
	public void update(float deltaTime){
		int len = backgrounds.size();
		for(int i = 0; i < len; i++){
			backgrounds.get(i).update(deltaTime);
			if(backgrounds.get(i).position.x < -WIDTH / 2){
				backgrounds.get(i).position.x = WIDTH + (WIDTH /2);
			}
		}
	}
	
	public void renderer(SpriteBatcher batcher){
		int len = backgrounds.size();
		for(int i = 0; i < len; i++){
			backgrounds.get(i).renderer(batcher);
		}
	}
}
