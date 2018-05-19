package com.dorashush.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.dorashush.game.Screens.BeforeLoadingScreen;
import com.dorashush.game.Screens.LoadingScreen;
import com.dorashush.game.Screens.PlayScreen;

public class FlappyPug extends Game {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final float PPM = 100;
	public static final float SPEED_MODIFIER = (float)-0.1;
	public static final float SPEED_TIME_JUMP = (float)10;


	//Box2D Collision Bits
	public static final short NOTHING_BIT = 0;
	public static final short ENEMY_BIT = 1;
	public static final short DOG_BIT = 2;
	public static final short POWER_UP_BIT = 4;
	public static final short DESTROYED_BIT = 8;
	public static final short INVISABLE_BIT = 16;
	public static final short BORDERS_BIT = 32;

	//Preferencses
	public static Preferences flappyDogPreferences;
	public static String NAME;
	public static float VOLUME = 1;
	public static boolean VIBRATION = true;
	public static float HIGH_SCORE;
	public static String HIGH_SCORE_NAME;
	public static boolean SCORE_AS_TIME;


	public AssetManager manager = new AssetManager();
	public SpriteBatch batch;
	public Skin skin;
	public OrthographicCamera camera;

	public static LeaderBoardHandler handler;

	public FlappyPug(LeaderBoardHandler handler) {
		this.handler = handler;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, FlappyPug.WIDTH / 2 /FlappyPug.PPM, FlappyPug.HEIGHT / 2/FlappyPug.PPM);



		//AssetsLoad();
		skin = new Skin(Gdx.files.internal("textSkin/comic-ui.json"));


		//setScreen(new PlayScreen(this));
		//setScreen(new LoadingScreen(this));
		setScreen(new BeforeLoadingScreen(this));

		loadPreferences();

	}

	@Override
	public void render () {
		super.render();
		if(manager.update()){
			Gdx.app.log("manager status"," loaded");
		}
	}


	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();
	}

	public void AssetsLoad(){
		manager.load("images/openingscreen.png", Texture.class);
		manager.load("images/mainscreentitle.png", Texture.class);
		manager.load("images/play.png", Texture.class);
		manager.load("images/settings.png", Texture.class);
		manager.load("images/highscore.png", Texture.class);
		manager.load("images/windowpanel.png", Texture.class);


		manager.load("atlas/animations", TextureAtlas.class);
		manager.load("images/fireon.png", Texture.class);
		manager.load("images/fireoff.png", Texture.class);
		manager.load("images/wall.png", Texture.class);
		manager.load("images/wall2.png", Texture.class);
		manager.load("images/wall3.png", Texture.class);
		manager.load("images/wall4.png", Texture.class);
		manager.load("images/wall5.png", Texture.class);

		manager.load("images/background.png", Texture.class);
		manager.load("images/replaybtn.png", Texture.class);
		manager.load("images/score.png", Texture.class);
		manager.load("images/homebtn.png", Texture.class);
		manager.load("images/backgroundfull.png", Texture.class);
		manager.load("images/coulds.png", Texture.class);
		manager.load("images/ground.png", Texture.class);
		manager.load("images/p.png", Texture.class);
		manager.load("images/u.png", Texture.class);
		manager.load("images/g.png", Texture.class);
		manager.load("images/pnobubble.png", Texture.class);
		manager.load("images/unobubble.png", Texture.class);
		manager.load("images/gnobbuble.png", Texture.class);
		manager.load("images/sandclock.png", Texture.class);
		manager.load("images/speeddown.png", Texture.class);
		manager.load("images/timeaddtion.png", Texture.class);
		manager.load("images/testscoreline.png", Texture.class);
		manager.load("images/currencypowerup.png", Texture.class);

	}

	public AssetManager getManager() {
		return manager;
	}


	public void loadPreferences(){
		flappyDogPreferences = Gdx.app.getPreferences("flappyDogPreferences");
		NAME = flappyDogPreferences.getString("name","No name stored");
		VOLUME = flappyDogPreferences.getFloat("volume",0.5f);
		VIBRATION = flappyDogPreferences.getBoolean("vibration",true);
		HIGH_SCORE = flappyDogPreferences.getFloat("highScore",0f);
		HIGH_SCORE_NAME = flappyDogPreferences.getString("highScoreName","");
		SCORE_AS_TIME = flappyDogPreferences.getBoolean("scoreAsTime",true);
	}
}
