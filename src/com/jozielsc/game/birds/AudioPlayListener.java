package com.jozielsc.game.birds;

import com.jozielsc.g4a.interfaces.Sound;

public class AudioPlayListener implements WorldListener{

	@Override
	public void fly() {
		//if(Settings.soundEnabled)
			play(Assets.fly);
	}
	
	
	
	private void play(Sound sound){
		if(Settings.soundEnabled)
			sound.play(1);
	}

	@Override
	public void playMusic(){
		Assets.journey.setLooping(true);
		Assets.journey.setVolume(0.5f);
		if(Settings.soundEnabled)
			Assets.journey.play();
	}
	@Override
	public void stopMusic(){
		
			Assets.journey.stop();		
	}
	@Override
	public void finishMusic(){
		
		Assets.journey.dispose();
	}
	
	@Override
	public void click() {
		//if(Settings.soundEnabled)
			play(Assets.click);
	}

	@Override
	public void crash() {
		//if(Settings.soundEnabled)
			play(Assets.crash);
	}



	@Override
	public void pauseMusic() {
		Assets.journey.pause();
	}



	@Override
	public void ring() {
		play(Assets.ring);
		
	}
	

}
