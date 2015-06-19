package com.jozielsc.game.birds;

import com.jozielsc.g4a.game.GLGame;
import com.jozielsc.g4a.gl.Animation;
import com.jozielsc.g4a.gl.Font;
import com.jozielsc.g4a.gl.Texture;
import com.jozielsc.g4a.gl.TextureRegion;
import com.jozielsc.g4a.interfaces.Music;
import com.jozielsc.g4a.interfaces.Sound;

public class Assets {
	public static WorldListener audioPlay;
	public static Texture cenario;
	public static TextureRegion backgroundRegion;
	public static TextureRegion foregroundDown;
	public static Texture heroTexture;
	public static Animation heroAnimation;
	public static Texture enemyTexture;
	public static Animation enemyAnimation;
	public static Texture btGui;
	public static TextureRegion btPlay;
	public static TextureRegion btAudionOn;
	public static TextureRegion btAudioOff;
	public static TextureRegion btPause;
	public static TextureRegion btCancel;
	public static TextureRegion btConfirm;
	public static TextureRegion btRetry;
	public static TextureRegion windowGameOver;
	public static TextureRegion windowPause;
	public static TextureRegion windowSair;
	public static TextureRegion moldScore;
	public static Texture textureFont;
	public static Font font;
	public static Sound fly;
	public static Sound click;
	public static Sound crash;
	public static Sound ring;
	public static Music journey;
	private final static String DIRETORIO = "assets/images/";

	public static void load(GLGame game) {
		btGui = new Texture(game, DIRETORIO + "gui.png");
		btConfirm = new TextureRegion(btGui, 480, 639, 100, 99);
		btCancel = new TextureRegion(btGui, 0, 99, 100, 99);
		btPlay = new TextureRegion(btGui, 190, 199, 100, 99);
		btRetry = new TextureRegion(btGui, 480, 540, 100, 99);
		btAudioOff = new TextureRegion(btGui, 580, 639, 100, 99);
		btAudionOn = new TextureRegion(btGui, 0, 0, 100, 99);
		btPause = new TextureRegion(btGui, 290, 199, 100, 99);
		windowGameOver = new TextureRegion(btGui, 0, 618, 480, 320);
		windowSair = new TextureRegion(btGui, 0, 298, 480, 320);
		windowPause = new TextureRegion(btGui, 480, 738, 480, 200);
		moldScore = new TextureRegion(btGui, 0, 198, 190, 100);
		audioPlay = new AudioPlayListener();
		cenario = new Texture(game, DIRETORIO + "cenario.png");
		backgroundRegion = new TextureRegion(cenario, 0, 0, 320, 240);
		foregroundDown = new TextureRegion(cenario, 320, 210, 320, 30);
		heroTexture = new Texture(game, DIRETORIO + "hero.png");
		enemyTexture = new Texture(game, DIRETORIO + "enemy.png");
		heroAnimation = new Animation(0.2f, new TextureRegion(heroTexture, 0,
				0, 58, 48), new TextureRegion(heroTexture, 116, 0, 58, 48),
				new TextureRegion(heroTexture, 58, 0, 58, 48));
		enemyAnimation = new Animation(1f, new TextureRegion(enemyTexture, 116,
				0, 58, 48), new TextureRegion(enemyTexture, 0, 0, 58, 48),
				new TextureRegion(enemyTexture, 58, 0, 58, 48));
		textureFont = new Texture(game, DIRETORIO + "font.png");
		font = new Font(textureFont, 0, 0, 16, 16, 20);
		fly = game.getAudio().newSound("assets/sounds/fly.ogg");
		click = game.getAudio().newSound("assets/sounds/click.ogg");
		crash = game.getAudio().newSound("assets/sounds/crash.ogg");
		journey = game.getAudio().newMusic("assets/sounds/journey.mp3");
		ring = game.getAudio().newSound("assets/sounds/ring.ogg");
		audioPlay.playMusic();
	}

	public static void reload() {
		cenario.reload();
		btGui.reload();
		heroTexture.reload();
		enemyTexture.reload();
		textureFont.reload();
	}

}
