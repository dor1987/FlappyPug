package com.dorashush.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.game.Abstract.ScreenWithPopUps;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Scenes.NameWindow;
import com.dorashush.game.Scenes.WelcomeWindow;
import com.dorashush.game.Tools.ActionMoveCircular;
import com.dorashush.game.Tools.MyCurrencyTextButton;

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
    private Table table,nameTable,countersTable;
    private Image playBtn,optionsBtn,leaderBoardBtn,menuTitle,backgroundTexture,dog,spinBtn,freeSpinBtn,spinTheWheel;
    private Label hiLabel;
    private AssetManager manager;
    private ExtendViewport viewPort,backViewPort;
    private Drawable coinCounterImage,dimondCounterImage,wheelCounterImage;
    private MyCurrencyTextButton diamonCounter,coinCounter,spinsCounter;

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
        spinBtn = new Image(manager.get("images/spinbtn.png",Texture.class));
        spinBtn.setOrigin(spinBtn.getWidth()/4,spinBtn.getHeight()/4);
        freeSpinBtn = new Image(manager.get("images/freespinbtn.png",Texture.class));
        spinTheWheel = new Image(manager.get("images/spinthewheelmenubtn.png",Texture.class));
        spinTheWheel.setOrigin(spinTheWheel.getWidth()/8,spinTheWheel.getHeight()/8);

        coinCounterImage = new TextureRegionDrawable(new TextureRegion(manager.get("images/coincounter.png",Texture.class)));
        dimondCounterImage = new TextureRegionDrawable(new TextureRegion(manager.get("images/dimondcounter.png",Texture.class)));
        wheelCounterImage = new TextureRegionDrawable(new TextureRegion(manager.get("images/spincounterimage.png",Texture.class)));

        TextButton.TextButtonStyle coinCounterStyle = new TextButton.TextButtonStyle(coinCounterImage,coinCounterImage,coinCounterImage,skin.getFont("font"));
        TextButton.TextButtonStyle dimonCounterStyle = new TextButton.TextButtonStyle(dimondCounterImage,dimondCounterImage,dimondCounterImage,skin.getFont("font"));
        TextButton.TextButtonStyle spinsCounterStyle = new TextButton.TextButtonStyle(wheelCounterImage,wheelCounterImage,wheelCounterImage,skin.getFont("font"));

        coinCounter = new MyCurrencyTextButton(FlappyPug.MONEY+"",coinCounterStyle);
        diamonCounter = new MyCurrencyTextButton(FlappyPug.DIAMONDS+"",dimonCounterStyle);
        spinsCounter = new MyCurrencyTextButton(FlappyPug.SPINS+"",spinsCounterStyle);

        countersTable = new Table();
        //countersTable.top().center();
        //countersTable.setFillParent(true);

        countersTable.add(diamonCounter).width(100f).height(30);
        //countersTable.row();
        countersTable.add(coinCounter).width(100f).height(30);
       // countersTable.row();
     //   countersTable.add(spinsCounter).expand().fill();
        countersTable.setOrigin(Align.center);


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
        table.add(menuTitle).colspan(3).height(150f).expandX().top();
        table.row();
        table.add().width(50f);
        table.add();
        table.add();
        table.row();
        table.add(playBtn).colspan(2).height(70f).width(200);
        table.add(spinBtn).width(50f).height(50f);
        table.row();
        table.add(optionsBtn).colspan(2).height(70f).width(200);
        table.add(freeSpinBtn).width(50f).height(50f).padRight(10f);
        table.row();
        table.add(leaderBoardBtn).colspan(2).height(70f).width(200);
        table.add(spinTheWheel).width(50f).height(50f).padRight(10f);

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

        spinBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Add leaderbaord Screen
                game.setScreen(new SpeenWheelScreen((FlappyPug)game));
                dispose();
            }
        });

        spinTheWheel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Add leaderbaord Screen
                game.setScreen(new SpeenWheelScreen((FlappyPug)game));
                dispose();
            }
        });

        freeSpinBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                FlappyPug.SPINS+=5;
            }
        });

    }


    @Override
    public void show() {
        final float width = stage.getWidth();
        final float height = stage.getHeight();

        countersTable.setBounds(0,0,width,height/9);
        countersTable.setPosition(width-countersTable.getWidth()/2,height-countersTable.getHeight()/2,Align.center);

        Gdx.input.setInputProcessor(stage);
        spinBtn.addAction(forever(sequence(scaleTo(1.25F, 1.25F, 0.30F), scaleTo(1F, 1F, 0.30F))));
        spinTheWheel.addAction(forever(sequence(rotateBy(360, 1),
                        rotateTo(0),delay(5f))));

        backStage.addActor(backgroundTexture);
        stage.addActor(countersTable);
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
        stage.act(delta);
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
        //countersTable.debug();
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
