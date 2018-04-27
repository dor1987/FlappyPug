package com.dorashush.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Screens.PlayScreen;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
/**
 * Created by Dor on 04/24/18.
 */

public class EndGameMenu implements Disposable{
    private static Integer amountOfBombs,amountOfSpeeds;
    private Viewport viewPort;
    private Stage stage;
    boolean playPressed, firstdraw,homePressed;
    private Table table,tableForBtns;
    private Image restartBtn,scoreIcon,homeBtn;
    private AssetManager manager;
    private static Label scoreLabel;
    private Skin skin;

    public EndGameMenu(PlayScreen screen,SpriteBatch sb){
        this.manager=  screen.getManager();
        viewPort = new ExtendViewport(FlappyPug.WIDTH / 2,FlappyPug.HEIGHT / 2,new OrthographicCamera());
        stage = new Stage(viewPort,sb);
        skin = new Skin(Gdx.files.internal("textSkin/comic-ui.json"));
        firstdraw = true;
       // Gdx.input.setInputProcessor(stage);
        initPauseMenu();
        initStage();
    }

    public void initStage(){
        stage.addActor(table);
        stage.addActor(tableForBtns);
    }

    public void initPauseMenu(){
        initPauseBtns();
        initPauseScore();
        initPauseMenuListener();
        initPauseTable();
    }

    public void initPauseBtns(){
        restartBtn = new Image(manager.get("images/replaybtn.png",Texture.class));
        scoreIcon = new Image(manager.get("images/score.png",Texture.class));
        homeBtn =new Image(manager.get("images/homebtn.png",Texture.class));
    }

    public void initPauseScore(){
        scoreLabel= new Label("0",skin,"title");
    }

    public void initPauseTable(){
        table= new Table();
        //table.center().left();
       // table.setFillParent(true);
        //table.setVisible(false);
        table.setPosition(table.getX(),FlappyPug.HEIGHT/3);
        table.setSize(250,200);
        table.setTransform(true);
        table.setOrigin(table.getWidth()/2,table.getHeight()/2);
        table.add(scoreIcon);
        table.row();
        table.add(scoreLabel);


        tableForBtns = new Table();
        tableForBtns.setPosition(table.getX(),FlappyPug.HEIGHT/3 -table.getY());
        tableForBtns.setSize(250,150);
        tableForBtns.setTransform(true);
        tableForBtns.setOrigin(tableForBtns.getWidth()/2,tableForBtns.getHeight()/2);
        tableForBtns.add(homeBtn).padBottom(20f);
        tableForBtns.add(restartBtn).padBottom(20f);

    }

    public void initPauseMenuListener(){
        restartBtn.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                playPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                playPressed = false;
            }
        });

        homeBtn.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                homePressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                homePressed = false;
            }
        });

    }



    public void draw(float dt){

        if(firstdraw) {

            table.addAction(sequence(scaleTo(.1f, .1f), parallel(fadeIn(2f, Interpolation.pow2), scaleTo(1.2f, 1.2f, 1.5f, Interpolation.pow5),
                    moveTo(FlappyPug.WIDTH / 2 / FlappyPug.PPM, FlappyPug.HEIGHT /4, 1.5f, Interpolation.swing))));


            tableForBtns.addAction(sequence(scaleTo(.1f, .1f), parallel( scaleTo(1f, 1f, 1.5f, Interpolation.pow5)), delay(1f)));


            firstdraw= false;
            Gdx.input.setInputProcessor(stage);

        }

            table.act(dt);
            tableForBtns.act(dt);
            stage.draw();

        }

    public boolean isPlayPressed() {
        return playPressed;
    }
    public boolean isHomePressed() {
        return homePressed;
    }

    public void resize(int width, int height){
        viewPort.update(width, height);
    }

    public void setScore(float score){
        scoreLabel.setText(String.format("%06.4f", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
