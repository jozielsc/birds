package com.jozielsc.game.birds;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.jozielsc.g4a.game.GLScreen;
import com.jozielsc.g4a.gl.Camera2D;
import com.jozielsc.g4a.gl.FPSCounter;
import com.jozielsc.g4a.gl.SpriteBatcher;

import com.jozielsc.g4a.interfaces.Game;
import com.jozielsc.g4a.interfaces.Input.TouchEvent;
import com.jozielsc.g4a.math.Circle;
import com.jozielsc.g4a.math.OverlapTester;
import com.jozielsc.g4a.math.Vector2;
import com.jozielsc.game.birds.World.WORLD_STATE;

public class GameScreen extends GLScreen {

	public enum GAME_STATE {
		READY, RUNNING, PAUSED, GAME_OVER
	}

	public GAME_STATE state;

	public final String TAG = getClass().getSimpleName();

	private SpriteBatcher batcher;
	private Camera2D guiCamera;

	private Vector2 touchPoint;


	private Circle touchPlay;
	private Circle touchAudioBegin;
	private Circle touchPause;
	private Circle touchAudioInPause;
	private Circle touchPlayInPause;
	private Circle touchRetry;
	
	public World world;
	public WorldRenderer worldRenderer;
	public FPSCounter fpsCounter;

	public GameScreen(Game game) {
		super(game);
		init();
	}

	public void init() {

		fpsCounter = new FPSCounter();
		touchPoint = new Vector2();

		batcher = new SpriteBatcher(glGraphics, 100);
		guiCamera = new Camera2D(glGraphics,320, 240);
		world = new World();
		worldRenderer = new WorldRenderer(glGraphics, batcher, world);

		touchAudioBegin = new Circle((guiCamera.position.x * 2) - 25, (guiCamera.position.y * 2) - 25, 25);
		touchPlay = new Circle(guiCamera.position.x, guiCamera.position.y, 25);
		touchPause = new Circle(guiCamera.position.x - 130, guiCamera.position.y + 100, 25);
		touchPlayInPause = new Circle(guiCamera.position.x - (guiCamera.position.x / 5), guiCamera.position.y, 25);
		touchAudioInPause = new Circle(guiCamera.position.x + (guiCamera.position.x / 5), guiCamera.position.y, 25);
		touchRetry = new Circle(guiCamera.position.x, guiCamera.position.y - (guiCamera.position.y / 2) - 10, 25);
		state = GAME_STATE.READY;
	}

	@Override
	public void update(float deltaTime) {

		if (deltaTime > 0.1f) {
			deltaTime = 0.1f;
		}

		switch (state) {
		case READY:
			updateReady();
			break;
		case RUNNING:
			updateRunning(deltaTime);
			break;
		case PAUSED:
			updatePaused();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}

	}

	private void updateGameOver() {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();

		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;

			touchPoint.set(event.x, event.y);
			guiCamera.touchToWorld(touchPoint);

			if (OverlapTester.pointInCircle(touchRetry, touchPoint)) {
				Assets.audioPlay.click();
				game.setScreen(new GameScreen(game));
				return;
			} 

		}

	}

	private void updatePaused() {

		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;

			touchPoint.set(event.x, event.y);
			guiCamera.touchToWorld(touchPoint);

			if (OverlapTester.pointInCircle(touchPlayInPause, touchPoint)) {
				Assets.audioPlay.click();

				state = GAME_STATE.RUNNING;
				return;
			} else if (OverlapTester.pointInCircle(touchAudioInPause, touchPoint)) {
				Assets.audioPlay.click();
				Settings.soundEnabled = !Settings.soundEnabled;
				if (Settings.soundEnabled) {
					Assets.audioPlay.playMusic();
				} else {
					Assets.audioPlay.pauseMusic();
				}
				return;
			}

		}

	}

	private void updateRunning(float deltaTime) {

		world.update(deltaTime);

		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);

			touchPoint.set(event.x, event.y);
			guiCamera.touchToWorld(touchPoint);

			if (OverlapTester.pointInCircle(touchPause, touchPoint)) {
				Assets.audioPlay.click();
				state = GAME_STATE.PAUSED;
				return;
			} else {
				world.touch(event);
			}

			if (event.type != TouchEvent.TOUCH_UP)
				continue;

		}

		if (world.state == WORLD_STATE.GAME_OVER) {
			state = GAME_STATE.GAME_OVER;
		}
	}

	private void updateReady() {
	
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;

			touchPoint.set(event.x, event.y);
			guiCamera.touchToWorld(touchPoint);

			if (OverlapTester.pointInCircle(touchPlay, touchPoint)) {
				Assets.audioPlay.click();

				state = GAME_STATE.RUNNING;
				return;
			} 
			else if (OverlapTester.pointInCircle(touchAudioBegin, touchPoint)) {
				Assets.audioPlay.click();
				Settings.soundEnabled = !Settings.soundEnabled;
				if (Settings.soundEnabled) {
					Assets.audioPlay.playMusic();
				} else {
					Assets.audioPlay.pauseMusic();
				}
				return;
			}
		
		}

		
		
		
	}

	@Override
	public void renderer(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		worldRenderer.renderer();

		guiCamera.setViewportAndMatrices();

		batcher.beginBatch(Assets.btGui);
		switch (state) {
		case READY:
			rendererReady();
			break;
		case RUNNING:
			rendererRunning();
			break;
		case PAUSED:
			rendererPaused();
			break;
		case GAME_OVER:
			rendererGameOver();
			break;
		}
		batcher.endBatch();

		switch (state) {
		case RUNNING:
			batcher.beginBatch(Assets.textureFont);
			Assets.font.drawText(batcher, world.score, 160, 220);
			batcher.endBatch();
			break;
		case GAME_OVER:
			batcher.beginBatch(Assets.textureFont);
			
			Assets.font.drawText(batcher, "" + world.score,
					165 - ((world.score.length() * 16) / 2), 90);
					
			batcher.endBatch();
			break;

		default:
			break;
		}

		gl.glDisable(GL10.GL_BLEND);
		fpsCounter.logFrame();

	}

	private void rendererGameOver() {
		batcher.drawSprite(guiCamera.position.x, guiCamera.position.y, Assets.windowGameOver.width / 2, Assets.windowGameOver.height / 2, Assets.windowGameOver);
		batcher.drawSprite(touchRetry.center.x, touchRetry.center.y, Assets.btRetry.width / 2, Assets.btRetry.height / 2, Assets.btRetry);
	}

	private void rendererPaused() {

		batcher.drawSprite(guiCamera.position.x, guiCamera.position.y, Assets.windowPause.width / 4, Assets.windowPause.height / 2, Assets.windowPause);
		batcher.drawSprite(touchPlayInPause.center.x, touchPlayInPause.center.y, Assets.btPlay.width / 2, Assets.btPlay.height / 2, Assets.btPlay);
		batcher.drawSprite(touchAudioInPause.center.x, touchAudioInPause.center.y, Assets.btPlay.width / 2, Assets.btPlay.height / 2, Settings.soundEnabled ? Assets.btAudionOn : Assets.btAudioOff);
		
	}

	private void rendererRunning() {
		batcher.drawSprite(touchPause.center.x, touchPause.center.y, Assets.btPause.width / 2, Assets.btPause.height / 2, Assets.btPause);
		batcher.drawSprite(guiCamera.position.x, guiCamera.position.y + 100, Assets.moldScore.width / 2, Assets.moldScore.height / 4, Assets.moldScore);
	}

	private void rendererReady() {
		
		batcher.drawSprite(touchPlay.center.x, touchPlay.center.y, Assets.btPlay.width / 2, Assets.btPlay.height / 2, Assets.btPlay);
		batcher.drawSprite(touchAudioBegin.center.x, touchAudioBegin.center.y, Assets.btAudionOn.width / 2, Assets.btAudionOn.height / 2, Settings.soundEnabled ? Assets.btAudionOn : Assets.btAudioOff);
	
	}

	@Override
	public void pause() {
		if (state == GAME_STATE.RUNNING)
			state = GAME_STATE.PAUSED;
		
	}

	
	
	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
	
	}

}
