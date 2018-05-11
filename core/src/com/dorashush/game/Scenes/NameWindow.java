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
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Dor on 04/27/18.
 */

public class NameWindow implements Disposable {
    private Viewport viewPort;
    private Stage stage;
    private Table table;
    private TextButton submitBtn;
    private AssetManager manager;
    private static Label whatYourNameLabel;
    private static Label whatYourNameLabel2;

    private Skin skin;
    private boolean firstdraw,status;
    private TextArea userNameTextArea;
    private Image backGroundPanel;
    private ScreenWithPopUps screen;

    public NameWindow(ScreenWithPopUps screen, SpriteBatch sb){
        this.manager=  screen.getManager();
        this.screen = screen;
        viewPort = new ExtendViewport(FlappyPug.WIDTH / 2,FlappyPug.HEIGHT / 2,new OrthographicCamera());
        stage = new Stage(viewPort,sb);
        skin = new Skin(Gdx.files.internal("textSkin/comic-ui.json"));
        firstdraw = true;
        status = false;
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
        submitBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if(userNameTextArea.getText().compareTo("Click Here!")!=0) {
                        FlappyPug.NAME = userNameTextArea.getText();
                        FlappyPug.flappyDogPreferences.putString("name", FlappyPug.NAME);
                        FlappyPug.flappyDogPreferences.flush();

                        Gdx.input.setOnscreenKeyboardVisible(false);
                    }
                    Runnable closeNameWindow = new Runnable() {
                        @Override
                        public void run() {
                            //screen.popUpWindowControl("NameWindow",false);
                            status=false;
                            dispose();
                        }
                    };
                    stage.addAction(sequence(parallel( scaleTo(.01f, .01f, 0.5f, Interpolation.pow5)),run(closeNameWindow)));

                }
            });

        userNameTextArea.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                userNameTextArea.setText("");
            }
        });
    }

    public void initNameWindowBtnsAndLabels(){
    whatYourNameLabel = new Label("What is", skin,"big");
    whatYourNameLabel2 = new Label("Your Name?", skin,"big");
    submitBtn = new TextButton("Submit",skin);
    userNameTextArea = new TextArea("Click Here!", skin);
    userNameTextArea.setMaxLength(6);
    }
    public void initBackGroundPanel(){
        backGroundPanel = new Image(manager.get("images/windowpanel.png",Texture.class));
        backGroundPanel.setSize(240,250);
        backGroundPanel.setPosition(FlappyPug.WIDTH/2-backGroundPanel.getWidth(),FlappyPug.HEIGHT/2-backGroundPanel.getHeight());
    }


    public void initNameWindowTable(){
        table= new Table();
        table.setSize(120,120);
        table.setPosition(backGroundPanel.getX()+table.getWidth()/2,backGroundPanel.getY()+table.getHeight()/2);
        table.setTransform(true);
        table.add(whatYourNameLabel).expandX();
        table.row();
        table.add(whatYourNameLabel2).expandX().padBottom(20f);
        table.row();
        table.add(userNameTextArea).expandX().padBottom(20f);
        table.row();
        table.add(submitBtn).expandX();

    }





    public void draw(float dt){

        if(firstdraw) {
            stage.addAction(sequence(scaleTo(.1f, .1f), parallel( scaleTo(1f, 1f, 0.5f, Interpolation.pow5)), delay(1f)));

            status=true;
            //screen.popUpWindowControl("NameWindow",true);
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
}
