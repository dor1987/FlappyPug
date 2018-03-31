package com.dorashush.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dorashush.game.Screens.PlayScreen;

public class FlappyPug extends Game {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final float PPM = 100;
	public static final float SPEED_MODIFIER = (float)-0.25;
	public static final float SPEED_TIME_JUMP = (float)5;


	//Box2D Collision Bits
	public static final short NOTHING_BIT = 0;
	public static final short ENEMY_BIT = 1;
	public static final short DOG_BIT = 2;
	public static final short POWER_UP_BIT = 4;
	public static final short DESTROYED_BIT = 8;


	private AssetManager manager = new AssetManager();
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		AssetsLoad();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
		manager.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public void AssetsLoad(){

	}

	public AssetManager getManager() {
		return manager;
	}
}
