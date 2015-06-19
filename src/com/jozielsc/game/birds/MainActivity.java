package com.jozielsc.game.birds;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;

import com.jozielsc.g4a.game.GLGame;
import com.jozielsc.g4a.interfaces.Screen;

public class MainActivity extends GLGame {
	public final String TAG = getClass().getSimpleName();
	private boolean firstTimeCreate = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		if (firstTimeCreate) {
			Settings.load(getFileIO());
			Assets.load(this);
			firstTimeCreate = false;
		} else {
			Assets.reload();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		 Assets.audioPlay.pauseMusic();
	}

	@Override
	public void onResume() {
		super.onResume();
		if(!firstTimeCreate)
			Assets.audioPlay.playMusic();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Assets.audioPlay.finishMusic();
	
	}

	@Override
	public Screen getStartScreen() {
		return new GameScreen(this);
	}

}
