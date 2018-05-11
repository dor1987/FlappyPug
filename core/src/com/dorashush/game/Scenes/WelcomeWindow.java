package com.dorashush.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.game.Abstract.ScreenWithPopUps;
import com.dorashush.game.FlappyPug;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Dor on 04/30/18.
 */

public class WelcomeWindow implements Disposable {
    private Viewport viewPort;
    private Stage stage;
    private Table table;
    private TextButton continueBtn,changeNameBtn;
    private Label welcomeLabel,nameLabel;
    private AssetManager manager;

    private Skin skin;
    private boolean firstdraw,status,openNameWindow;
    private Image backGroundPanel;
    private ScreenWithPopUps screen;

    public WelcomeWindow(ScreenWithPopUps screen, SpriteBatch sb){
        this.manager=  screen.getManager();
        this.screen = screen;
        viewPort = new ExtendViewport(FlappyPug.WIDTH / 2,FlappyPug.HEIGHT / 2,new OrthographicCamera());
        stage = new Stage(viewPort,sb);
        skin = new Skin(Gdx.files.internal("textSkin/comic-ui.json"));
        firstdraw = true;
        status = false;
        openNameWindow = false;
        // Gdx.input.setInputProcessor(stage);
        initNameWindow();
        initStage();
        initListeners();
    }

    public void initStage(){
        stage.addActor(backGroundPanel);
        stage.addActor(table);
    }

    public void initNameWindow(){
        initBackGroundPanel();
        initNameWindowBtnsAndLabels();
        initNameWindowTable();
    }

    public void initListeners(){
        continueBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Runnable closeNameWindow = new Runnable() {
                    @Override
                    public void run() {
                       // screen.popUpWindowControl("WelcomeWindow",false);
                      status= false;
                        dispose();
                    }
                };
                stage.addAction(sequence(parallel( scaleTo(.01f, .01f, 0.5f, Interpolation.pow5)),run(closeNameWindow)));

            }
        });

        changeNameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Runnable closeNameWindow = new Runnable() {
                    @Override
                    public void run() {
                        //screen.popUpWindowControl("WelcomeWindow",false);
                        status = false;
                        dispose();
                        //screen.popUpWindowControl("NameWindow",true);
                        openNameWindow = true;

                    }
                };
                stage.addAction(sequence(parallel( scaleTo(.01f, .01f, 0.5f, Interpolation.pow5)),run(closeNameWindow)));

            }
        });
    }

    public void initNameWindowBtnsAndLabels(){
        welcomeLabel = new Label("Welcome back", skin);
        nameLabel = new Label(FlappyPug.NAME, skin,"big");
        continueBtn = new TextButton("Continue",skin);
        changeNameBtn = new TextButton("Not "+FlappyPug.NAME,skin);
    }
    public void initBackGroundPanel(){
        backGroundPanel = new Image(manager.get("images/windowpanel.png",Texture.class));
        backGroundPanel.setSize(240,250);
        backGroundPanel.setPosition(FlappyPug.WIDTH/2-backGroundPanel.getWidth(),FlappyPug.HEIGHT/2-backGroundPanel.getHeight());
    }


    public void initNameWindowTable(){
        table= new Table();
        table.setSize(120,90);
        table.setPosition(backGroundPanel.getX()+table.getWidth()/2,backGroundPanel.getY()+table.getHeight());
        table.setTransform(true);
        table.add(welcomeLabel).expandX();
        table.row();
        table.add(nameLabel).expandX().padBottom(20f);
        table.row();
        table.add(continueBtn).expandX().padBottom(10f);
        table.row();
        table.add(changeNameBtn).expandX();

    }





    public void draw(float dt){

        if(firstdraw) {
            stage.addAction(sequence(scaleTo(.1f, .1f), parallel( scaleTo(1f, 1f, 1f, Interpolation.pow5)), delay(1f)));

            //screen.popUpWindowControl("WelcomeWindow",true);
            status = true;
            firstdraw= false;
            Gdx.input.setInputProcessor(stage);
        }

        stage.act(dt);


        //  Gdx.input.setInputProcessor(stage);
        stage.draw();

    }


    public void resize(int width, int height){
        viewPort.update(width, height);
    }


    @Override
    public void dispose() {
        stage.dispose();
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean openNameWindow(){
        //to open name window from welcome window
    return openNameWindow;
    }

    public void openNameWindowControl(){
        openNameWindow = false;
    }
}



