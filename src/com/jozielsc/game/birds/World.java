package com.jozielsc.game.birds;

import com.jozielsc.g4a.interfaces.Input.TouchEvent;
import com.jozielsc.g4a.math.OverlapTester;
import com.jozielsc.g4a.math.Rectangle;
import com.jozielsc.g4a.math.Vector2;
import com.jozielsc.game.airplane.objects.ControllerBackground;
import com.jozielsc.game.airplane.objects.ControllerEnemy;
import com.jozielsc.game.airplane.objects.ControllerForeground;
import com.jozielsc.game.airplane.objects.Enemy;
import com.jozielsc.game.airplane.objects.Hero;
import com.jozielsc.game.airplane.objects.Hero.HERO_STATE;

public class World {

	public enum WORLD_STATE {
		RUNNING, LEVEL_UP, GAME_OVER
	}

	public static final Vector2 gravity = new Vector2(0, -16);
	public static final Vector2 velocity = new Vector2(0.05f, 0);
	public int level;
	public WORLD_STATE state;

	public WorldListener worldListener;

	public Hero hero;

	public String score;

	public float stateTime;

	public ControllerBackground background;
	public ControllerForeground foreground;
	public ControllerEnemy enemys;

	public Rectangle chao;
	public Rectangle ceu;
	
	public World() {
		stateTime = 0;
		score = new String("0");
		level = 1;
		state = WORLD_STATE.RUNNING;
		
		chao = new Rectangle(0, 0, WorldRenderer.FRUSTUM_WIDTH, 1.5f);
		ceu = new Rectangle(0, WorldRenderer.FRUSTUM_HEIGHT - 1, WorldRenderer.FRUSTUM_WIDTH, 1);
		enemys = new ControllerEnemy(WorldRenderer.FRUSTUM_WIDTH,
				WorldRenderer.FRUSTUM_HEIGHT, level);

		background = new ControllerBackground(WorldRenderer.FRUSTUM_WIDTH / 2,
				WorldRenderer.FRUSTUM_HEIGHT / 2, WorldRenderer.FRUSTUM_WIDTH,
				WorldRenderer.FRUSTUM_HEIGHT);

		foreground = new ControllerForeground(WorldRenderer.FRUSTUM_WIDTH / 2,
				WorldRenderer.FRUSTUM_HEIGHT / 2, WorldRenderer.FRUSTUM_WIDTH,
				2);
		hero = new Hero(WorldRenderer.FRUSTUM_WIDTH / 2 - 3,
				WorldRenderer.FRUSTUM_HEIGHT / 2);
	}

	public void update(float deltaTime) {
		stateTime += deltaTime;

		if (state != WORLD_STATE.GAME_OVER) {

			updateEnemy(deltaTime);

			updateBackground(deltaTime);

			updateHero(deltaTime);

			if (hero.state != HERO_STATE.HIT) {
				checkCollisions();
			}
		
			checkScore();
			
			checkGameOver();
		}
	}

	private void checkScore() {
		score = ""+enemys.countIvited;
	}

	private void updateEnemy(float deltaTime) {

		if (enemys.isLevelEnd) {
			level++;
			enemys.nextLevel(level);
		} else {
			enemys.update((deltaTime + velocity.x) * level);
		}

	}

	private void updateBackground(float deltaTime) {
		background.update(deltaTime);
		foreground.update(deltaTime);
	}

	private void checkCollisions() {
		checkForegroundCollisions();
		checkEnemyCollisions();
	}

	private void checkEnemyCollisions() {

		int len = enemys.enemys.size();
		for (int i = 0; i < len; i++) {
			Enemy m = enemys.enemys.get(i);
			if (m.isVisible)
				
				if(OverlapTester.overlapCircles(m.boundsCircle, hero.boundsCircle)){
					hero.hit();
				}
		}

	}

	private void checkForegroundCollisions() {	
		if(OverlapTester.overlapCircleRectangle(hero.boundsCircle, chao)){
			
			hero.hit();
		}else if(OverlapTester.overlapCircleRectangle(hero.boundsCircle, ceu)){
			
			hero.down();
		}
	}

	private void checkGameOver() {
		if (hero.state == HERO_STATE.HIT) {
			state = WORLD_STATE.GAME_OVER;
		}
	}

	private void updateHero(float deltaTime) {
		hero.update(deltaTime);
	}

	public void touch(TouchEvent event) {
		if (event.type == TouchEvent.TOUCH_DOWN) {
			Assets.audioPlay.fly();
			hero.touch();
		}
	}

}
