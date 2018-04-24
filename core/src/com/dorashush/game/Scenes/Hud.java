package com.dorashush.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.game.FlappyPug;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by Dor on 03/28/18.
 */

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewPort;
    private float score,timeCount;
    private Integer countDownTimer;
    private static Label scoreLabel,testLabel;
    private Table table,centerOfScreenTable;
    private boolean gameOver;
    private Skin skin;
    public Hud(SpriteBatch sb){
        score = 0;

        viewPort = new ExtendViewport(FlappyPug.WIDTH / 2,FlappyPug.HEIGHT / 2,new OrthographicCamera());
        stage = new Stage(viewPort,sb);
        skin = new Skin(Gdx.files.internal("textSkin/comic-ui.json"));
        countDownTimer = 3;
        timeCount = 0;
        initLabels();
        initTable();
        //initCenterOfScreenTable();
        stage.addActor(table);
        stage.addActor(centerOfScreenTable);
        gameOver=false;
    }

    public void initLabels(){
        scoreLabel= new Label(String.format("%06.4f",score),skin);
        testLabel = new Label(String.format("%d",countDownTimer), skin,"title");
    }
    public void initTable(){
        table =new Table();
        table.top().left();
        table.setFillParent(true);
        table.add(scoreLabel).expandX().padBottom(25f);

        centerOfScreenTable = new Table();
        centerOfScreenTable.center().left();
        centerOfScreenTable.setFillParent(true);
        centerOfScreenTable.add(testLabel).expandX();
    }


    public void update(float dt){
        if(!gameOver) {
            if (countDownTimer <=0) {
                score += dt;
                scoreLabel.setText(String.format("%06.4f", score));
            }
        }
        if(countDownTimer >1){
            timeCount+=dt;
            if(timeCount>=1) {
                countDownTimer--;
                testLabel.setText(String.format("%d", countDownTimer));
                timeCount =0;
            }
        }
        else if(countDownTimer ==1){
            timeCount+=dt;
            if(timeCount>=1) {
                countDownTimer--;
                testLabel.setText("GO!");
                timeCount =0;
            }
        }
        else {
            timeCount+=dt;
            if(timeCount>=1) {

                testLabel.setText("");
                timeCount =0;
            }
        }
    }

    public Integer getCountDownTimer() {
        return countDownTimer;
    }

    public float getScore() {
        return score;
    }

    public void setGameOver() {
        this.gameOver = true;
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();

    }
}