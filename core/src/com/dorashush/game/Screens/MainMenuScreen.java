package com.dorashush.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.dorashush.game.FlappyPug;

/**
 * Created by Dor on 04/01/18.
 */
//Todo - Full implmention and adjusment needed
public class MainMenuScreen implements Screen {
    private Game game;
    private OrthographicCamera camera;
    private Stage stage,backStage;
    private Table table;
    private Image playBtn,optionsBtn,leaderBoardBtn,menuTitle,backgroundTexture;
    private AssetManager manager;

    public MainMenuScreen(FlappyPug game) {
/*
        this.game = game;
        this.manager =game.getManager();;
        camera = new OrthographicCamera();

        backgroundTexture = new Image(new Texture("background.png"));
        backgroundTexture.setFillParent(true);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport.apply();
        backViewPort.apply();
        stage = new Stage(viewport, game.batch);
        backStage = new Stage(backViewPort);
        menuInit();
        addListeners();
*/
    }
    /*
    public void menuInit(){
        table =new Table();
        playBtn = new Image(new Texture("newgamebtn.png"));
        optionsBtn = new Image(new Texture("optionsbtn.png"));
        leaderBoardBtn = new Image(new Texture("leaderboardbtn.png"));
        menuTitle = new Image(new Texture("menutitle.png"));


        table.center();
        table.setFillParent(true);

        table.add(menuTitle).padBottom(70f);
        table.row();
        table.add(playBtn).padBottom(20f);
        table.row();
        table.add(optionsBtn).padBottom(20f);
        table.row();
        table.add(leaderBoardBtn).padBottom(20f);

    }
*/
    /*
    public void addListeners(){

        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new PlayScreen((Defenders) game,Defenders.FIRST_LEVEL,Defenders.STARTING_SCORE,Defenders.STARTING_LIVES,manager));
                dispose();
            }
        });

        optionsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Add option Screen
                game.setScreen(new OptionScreen(game,manager));

                dispose();
            }
        });

        leaderBoardBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Add leaderbaord Screen
                game.setScreen(new LeaderBoardScreen(game,manager));
                dispose();
            }
        });
    }
    */

    @Override
    public void show() {
        /*
        Gdx.input.setInputProcessor(stage);
        backStage.addActor(backgroundTexture);
        stage.addActor(table);
        */
    }

    @Override
    public void render(float delta) {
        /*
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backStage.draw();
        stage.draw();
        */

    }

    @Override
    public void resize(int width, int height) {
        /*
        viewport.update(width,height);
        backViewPort.update(width,height);
        */
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
        /*
        backStage.dispose();
        stage.dispose();
        */
    }


}