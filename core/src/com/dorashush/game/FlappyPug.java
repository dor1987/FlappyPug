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
