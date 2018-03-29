package com.dorashush.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private float score;
    private static Label scoreLabel;
    private Table table;

    public Hud(SpriteBatch sb){
        score = 0;

        viewPort = new ExtendViewport(FlappyPug.WIDTH / 2,FlappyPug.HEIGHT / 2,new OrthographicCamera());
        stage = new Stage(viewPort,sb);
        initLabels();
        initTable();
        stage.addActor(table);
    }

    public void initLabels(){
        scoreLabel= new Label(String.format("%06.4f",score),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    }
    public void initTable(){
        table =new Table();
        table.top().left();
        table.setFillParent(true);
        table.add(scoreLabel).expandX().padBottom(25);
    }
    public void update(float dt){
        score += dt;
        if(score >=1) {
            scoreLabel.setText(String.format("%06.4f", score));
        }
    }


    public float getScore() {
        return score;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}