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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.game.Abstract.ScreenWithPopUps;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Screens.MainMenuScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Dor on 05/13/18.
 */

public class NoInternetWindow  implements Disposable {
    private Viewport viewPort;
    private Stage stage;
    private Table table;
    private TextButton continueBtn;
    private Label welcomeLabel;
    private AssetManager manager;

    private Skin skin;
    private boolean firstdraw,status;
    private Image backGroundPanel;
    private ScreenWithPopUps screen;
    private boolean backToMainMenu;

    public NoInternetWindow(ScreenWithPopUps screen, SpriteBatch sb){
        this.manager=  screen.getManager();
        this.screen = screen;
        viewPort = new ExtendViewport(FlappyPug.WIDTH / 2,FlappyPug.HEIGHT / 2,new OrthographicCamera());
        stage = new Stage(viewPort,sb);
        skin = new Skin(Gdx.files.internal("textSkin/comic-ui.json"));
        firstdraw = true;
        status = false;
        backToMainMenu = false;
        // Gdx.input.setInputProcessor(stage);
        initNoInternerWindow();
        initStage();
        initListeners();
    }

    public void initStage(){
        stage.addActor(backGroundPanel);
        stage.addActor(table);
    }

    public void initNoInternerWindow(){
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
                        backToMainMenu =true;
                        status= false;
                        dispose();
                    }
                };
                stage.addAction(sequence(parallel( scaleTo(.01f, .01f, 0.5f, Interpolation.pow5)),run(closeNameWindow)));


            }
        });

    }

    public void initNameWindowBtnsAndLabels(){
        welcomeLabel = new Label("   No Internet\nTry again later", skin);
        continueBtn = new TextButton("Main Menu",skin);
    }
    public void initBackGroundPanel(){
        backGroundPanel = new Image(manager.get("images/windowpanel.png",Texture.class));
        backGroundPanel.setSize(200,150);
        backGroundPanel.setPosition(FlappyPug.WIDTH/4-backGroundPanel.getWidth()/2,FlappyPug.HEIGHT/3-backGroundPanel.getHeight());
    }


    public void initNameWindowTable(){
        table= new Table();
        table.setSize(100,50);
        table.setPosition(backGroundPanel.getX()+table.getWidth()/2,backGroundPanel.getY()+table.getHeight());
        table.setTransform(true);
        table.add(welcomeLabel).expandX().padBottom(10f);
        table.row();
        table.add(continueBtn).expandX();
        table.row();

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

    public boolean isBackToMainMenu() {
        return backToMainMenu;
    }
}



