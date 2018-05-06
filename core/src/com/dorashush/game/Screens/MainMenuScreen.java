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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.game.Abstract.ScreenWithPopUps;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Scenes.NameWindow;
import com.dorashush.game.Scenes.WelcomeWindow;
import com.dorashush.game.Tools.ActionMoveCircular;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Dor on 04/01/18.
 */
//Todo - Full implmention and adjusment needed
public class MainMenuScreen extends ScreenWithPopUps {
    private Game game;
    private OrthographicCamera camera;
    private Stage stage,backStage;
    private Table table,nameTable;
    private Image playBtn,optionsBtn,leaderBoardBtn,menuTitle,backgroundTexture,dog;
    private Label hiLabel;
    private AssetManager manager;
    private ExtendViewport viewPort,backViewPort;

    //Name windows
    private boolean isNameWindowOn,playPressed,isWelcomeWindowOn,isInitalStart;
    private NameWindow nameWindow;
    private WelcomeWindow welcomeWindow;
    private Skin skin;


    public MainMenuScreen(FlappyPug game,boolean isInitalStart) {

        this.game = game;
        this.manager =game.getManager();;
        viewPort = new ExtendViewport(FlappyPug.WIDTH / 2,FlappyPug.HEIGHT / 2,new OrthographicCamera());
        skin = new Skin(Gdx.files.internal("textSkin/comic-ui.json"));
        //skin can be deleted if Name window moved

        //camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        stage = new Stage(viewPort, game.batch);
        initDog();
        initBackground();
        menuInit();
        addListeners();

        welcomeWindow = new WelcomeWindow(this, game.batch);
        nameWindow = new NameWindow(this, game.batch);
        isNameWindowOn = false;
        isWelcomeWindowOn = false;
        playPressed = false;
        this.isInitalStart = isInitalStart;
    }

    public void menuInit(){
        table =new Table();
        playBtn = new Image(manager.get("images/play.png",Texture.class));
        optionsBtn = new Image(manager.get("images/settings.png",Texture.class));
        leaderBoardBtn = new Image(manager.get("images/highscore.png",Texture.class));
        menuTitle  = new Image(manager.get("images/mainscreentitle.png",Texture.class));

        /*
        if(FlappyPug.NAME.compareTo("No name stored")!=0) {
            hiLabel = new Label("Hello " + FlappyPug.NAME, skin, "big");
        }
        else{
            hiLabel = new Label("Hello... " , skin, "big");

        }
        */


        table.center();
        table.setFillParent(true);

        table.add(menuTitle);
        table.row();
        table.add(hiLabel);
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
        dog.setSize(100,110);
        dog.setPosition(0,FlappyPug.HEIGHT/3);

    }


    public void addListeners(){

        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                playPressed=true;
                dog.clearActions();
                Runnable transitionRunnable = new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new PlayScreen((FlappyPug) game));
                        dispose();
                    }
                };
                Gdx.input.setInputProcessor(null);
                dog.addAction(sequence(parallel(
                        moveBy(FlappyPug.WIDTH, FlappyPug.HEIGHT /2, 1.5f, Interpolation.swing)),run(transitionRunnable)));

                //game.setScreen(new PlayScreen((FlappyPug) game));

                //dispose();
            }
        });

        optionsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Add option Screen
                game.setScreen(new OptionMenu((FlappyPug) game));

                dispose();
            }
        });


        leaderBoardBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Add leaderbaord Screen
                game.setScreen(new LeaderBoardScreen((FlappyPug)game));
                dispose();
            }
        });

    }


    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
        backStage.addActor(backgroundTexture);
        stage.addActor(table);
        stage.addActor(dog);


        dog.addAction(forever(ActionMoveCircular.actionEllipse(70, 280, 90, 70, 1, false, 6)));


        }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backStage.draw();
        stage.draw();

        dog.act(delta);
/*
        if(FlappyPug.NAME.compareTo("No name stored")==0 || nameWindow.isStatus()|| isNameWindowOn){
            nameWindow.draw(delta);
        }
        else if (welcomeWindow.isStatus()&& isInitalStart ||isWelcomeWindowOn && isInitalStart){
          //  hiLabel.setText("Hello " + FlappyPug.NAME);
            welcomeWindow.draw(delta);
        }

        if(!isNameWindowOn&& !isWelcomeWindowOn && !playPressed){
            Gdx.input.setInputProcessor(stage);
        }
*/

        if(FlappyPug.NAME.compareTo("No name stored")==0 || nameWindow.isStatus() || welcomeWindow.openNameWindow()){
            nameWindow.draw(delta);
            welcomeWindow.openNameWindowControl();
            //nameWindow.setStatus(true);
            isInitalStart = false;
        }

        else if(isInitalStart || welcomeWindow.isStatus()){
            welcomeWindow.draw(delta);
            //welcomeWindow.setStatus(true);
            isInitalStart = false;

        }
        else if(!nameWindow.isStatus()&& !welcomeWindow.isStatus() && !playPressed){
            Gdx.input.setInputProcessor(stage);
        }
        Gdx.app.log("CheckHere","Name Status "+ !nameWindow.isStatus() + " Welcome Status " +!welcomeWindow.isStatus() +" playPressdStatus "+ !playPressed );

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
        nameWindow.dispose();
    }

    public AssetManager getManager() {
        return manager;
    }

    public void popUpWindowControl(String popUpName,boolean openWindow){
        if(popUpName.compareTo("NameWindow")==0)
             isNameWindowOn = openWindow;
        else if(popUpName.compareTo("WelcomeWindow")==0)
            isWelcomeWindowOn = openWindow;
    }
}
