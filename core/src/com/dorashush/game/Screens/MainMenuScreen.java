package com.dorashush.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Tools.ActionMoveCircular;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Dor on 04/01/18.
 */
//Todo - Full implmention and adjusment needed
public class MainMenuScreen implements Screen {
    private Game game;
    private OrthographicCamera camera;
    private Stage stage,backStage;
    private Table table;
    private Image playBtn,optionsBtn,leaderBoardBtn,menuTitle,backgroundTexture,dog;
    private AssetManager manager;
    private ExtendViewport viewPort,backViewPort;


    public MainMenuScreen(FlappyPug game) {

        this.game = game;
        this.manager =game.getManager();;
        viewPort = new ExtendViewport(FlappyPug.WIDTH / 2,FlappyPug.HEIGHT / 2,new OrthographicCamera());




        //camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        stage = new Stage(viewPort, game.batch);
       initDog();
        initBackground();
        menuInit();
        addListeners();

    }

    public void menuInit(){
        table =new Table();
        playBtn = new Image(manager.get("images/play.png",Texture.class));
        optionsBtn = new Image(manager.get("images/settings.png",Texture.class));
        leaderBoardBtn = new Image(manager.get("images/highscore.png",Texture.class));
        menuTitle  = new Image(manager.get("images/mainscreentitle.png",Texture.class));


        table.center();
        table.setFillParent(true);

        table.add(menuTitle);
        table.row();
        table.add(playBtn);
        table.row();
        table.add(optionsBtn);
        table.row();
        table.add(leaderBoardBtn);

    }
    public void initBackground(){
        backViewPort = new ExtendViewport( FlappyPug.WIDTH, FlappyPug.HEIGHT );
        backStage = new Stage(backViewPort);
        backgroundTexture  = new Image(manager.get("images/openingscreen.png",Texture.class));
        backgroundTexture.setFillParent(true);
    }

    public void initDog(){
        dog  = new Image(manager.get("images/fireon.png",Texture.class));
        dog.setSize(100,90);
        dog.setPosition(0,FlappyPug.HEIGHT/3);

    }

    public void addListeners(){

        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new PlayScreen((FlappyPug) game));
                dispose();
            }
        });
/*
        optionsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Add option Screen
                game.setScreen(new OptionScreen(game,manager));

                dispose();
            }
        });
*/
/*
        leaderBoardBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Add leaderbaord Screen
                game.setScreen(new LeaderBoardScreen(game,manager));
                dispose();
            }
        });
        */
    }


    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
        backStage.addActor(backgroundTexture);
        stage.addActor(table);
        stage.addActor(dog);


        dog.addAction(forever(ActionMoveCircular.actionEllipse(70, 290, 90, 70, 1, false, 6)));

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backStage.draw();
        stage.draw();

        dog.act(delta);

    }

    @Override
    public void resize(int width, int height) {

        viewPort.update(width,height);
        backViewPort.update(width,height);

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

        backStage.dispose();
        stage.dispose();

    }


}
