package com.dorashush.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.dorashush.game.FlappyPug;


/**
 * Created by Dor on 04/11/18.
 */

public class LoadingScreen implements Screen {

    private final FlappyPug game;

    private ShapeRenderer shapeRenderer;
    private float progress;

    public LoadingScreen(final FlappyPug game) {
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();


    }

    private void queueAssets() {
        /*
    }
        app.assets.load("img/splash.png", Texture.class);
        app.assets.load("ui/uiskin.atlas", TextureAtlas.class);
    */
    }

    @Override
    public void show() {
        System.out.println("LOADING");
        shapeRenderer.setProjectionMatrix(game.camera.combined);
        this.progress = 0f;
        queueAssets();
    }

    private void update(float delta) {
        progress = MathUtils.lerp(progress, game.getManager().getProgress(), .1f);
        if (game.getManager().update() && progress >= game.getManager().getProgress() - .001f) {
          //  game.setScreen(new PlayScreen(game));
            game.setScreen(new MainMenuScreen(game,true));
            //game.setScreen(new LeaderBoardScreen(game));

        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(32, game.camera.viewportHeight / 2 - 8, (game.camera.viewportWidth - 64), 16/FlappyPug.PPM);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(32, game.camera.viewportHeight / 2 - 8, progress * (game.camera.viewportWidth - 64), 16/FlappyPug.PPM);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}