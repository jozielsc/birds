package com.jozielsc.game.airplane.objects;

import java.util.ArrayList;
import java.util.List;

import com.jozielsc.g4a.gl.SpriteBatcher;


public class ControllerForeground {

	public List<Foreground> foregrounds;
	
	private final float WIDTH;
	
	
	public ControllerForeground(float x, float y, float width, float height){
		WIDTH = width;
		foregrounds = new ArrayList<Foreground>();
		Foreground foreDown = new Foreground(x, (y / 2) - height, width, height);
		foregrounds.add(foreDown);
		foregrounds.add(new Foreground(foreDown.position.x + width, foreDown.position.y, - width, height));
	}
	
	
	public void update(float deltaTime){
		int len = foregrounds.size();
		for(int i = 0; i < len; i++){
			foregrounds.get(i).update(deltaTime / 2);
			if(foregrounds.get(i).position.x < -WIDTH / 2){
				foregrounds.get(i).position.x = WIDTH + (WIDTH /2); 
			}
		}
	}
	
	public void renderer(SpriteBatcher batcher){
		int len = foregrounds.size();
		for(int i = 0; i < len; i++){
			foregrounds.get(i).renderer(batcher);
		}
	}
}
