package com.jozielsc.game.airplane.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


import com.jozielsc.g4a.gl.SpriteBatcher;
import com.jozielsc.g4a.math.Vector2;
import com.jozielsc.game.birds.Assets;
import com.jozielsc.game.birds.WorldRenderer;

public class ControllerEnemy {

	
	public List<Enemy> enemys;
	private Vector2 position = new Vector2();
	private int level;
	public boolean isLevelEnd;
	public int countIvited = 0;
	public static Random random = new Random();
	
	public ControllerEnemy(float width, float height, int level){
		nextLevel(level);
	}
		
	private void init() {
		enemys = new ArrayList<Enemy>();
		int num = 10 * level;
		for(int i = 0; i < num; i++){
			addEnemy();	
		}
		
		
	}

	public void update(float deltaTime){
		
		Iterator<Enemy> m = enemys.iterator();
		
		while(m.hasNext()){
			Enemy mt = m.next();
			mt.update(deltaTime);
			if(mt.position.x < 16){
				mt.isVisible = true;
			}
			
			if(mt.position.x < 4){
				if(!mt.isIvited){
					countIvited++;
					mt.isIvited = true;
					Assets.audioPlay.ring();
				}
			}
			
			if(mt.position.x < -2){
				m.remove();
			}
			
		}
	
			if(enemys.isEmpty()){
				isLevelEnd = true;
			}	
		
	
		
		
	}
	
	private void addEnemy() {
	
		if(enemys.size() == 0){
			position.x = (float) (WorldRenderer.FRUSTUM_WIDTH + Math.random());
		}else{
			position.x = enemys.get(enemys.size()-1).position.x + ((float)( 6 + Math.random()));
		}
	
		position.y = (float) getPositionRandom(2, 8);
		
		enemys.add(new Enemy(position.x, position.y, 1, 1));
	}
	
	private int getPositionRandom(int start, int end) {
		return start + random.nextInt(end - start + 1);
	}


	public void renderer(SpriteBatcher batcher){
		int len = enemys.size();
		
		for(int i = 0; i < len; i++){
			if(enemys.get(i).isVisible){
				enemys.get(i).renderer(batcher);
			}
		}
	}

	public void nextLevel(int level2) {
		isLevelEnd = false;
		this.level = level2;
		init();
	}
}
