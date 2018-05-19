package com.dorashush.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.dorashush.game.FlappyPug;

/**
 * Created by Dor on 05/19/18.
 */

public class BeforeLoadingScreen implements Screen {

    private final FlappyPug game;

    public BeforeLoadingScreen(FlappyPug game){
        this.game = game;

        queueAssets();


    }

    void queueAssets(){
        game.getManager().load("images/area.png", Texture.class);
        game.getManager().load("images/fill.png", Texture.class);
        //game.getManager().load("loading/FillStart.png", Texture.class);
        game.getManager().load("images/loadingbackground.png", Texture.class);
        game.getManager().load("images/mylogo.png", Texture.class);
    }

    @Override
    public void show() {
        System.out.println("BeforeLoadingScreen");
    }

    @Override
    public void render(float delta) {
        if (game.getManager().update()) {
            game.setScreen(new LoadingScreenTesting(game));
        }

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

    }
}
