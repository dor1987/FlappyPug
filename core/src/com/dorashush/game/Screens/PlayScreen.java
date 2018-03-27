package com.dorashush.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Sprites.Dog;

/**
 * Created by Dor on 03/27/18.
 */

public class PlayScreen implements Screen {
    private FlappyPug game;
   /*
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    */
    private static final int GROUND_Y_OFFSET = -50;

    private Dog dog;
    private Texture backGround;
    private Texture bottom,top;
    private Vector2 groundPos1, groundPos2;
    private OrthographicCamera cam;
    //private Array<Obstacle> obstacle;


    public PlayScreen(FlappyPug game) {
        this.game = game;
        cam.setToOrtho(false, FlappyPug.WIDTH / 2, FlappyPug.HEIGHT / 2);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
