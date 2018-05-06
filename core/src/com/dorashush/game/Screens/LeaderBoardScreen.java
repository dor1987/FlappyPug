package com.dorashush.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Scenes.NameWindow;
import com.dorashush.game.Scenes.WelcomeWindow;

import java.util.ArrayList;

/**
 * Created by Dor on 05/05/18.
 */

public class LeaderBoardScreen implements Screen {
    private Game game;
    private OrthographicCamera camera;
    private Stage stage,backStage;
    private Table scoreTable,btnsTable;
    private Image playBtn,menuTitle,backgroundTexture,backGroundPanel;
    private AssetManager manager;
    private ExtendViewport viewPort,backViewPort;
    private ScrollPane scrollPane;
    //Name windows
    private Skin skin;
    private List list;


    public LeaderBoardScreen(FlappyPug game) {
        this.game = game;
        this.manager =game.getManager();

        viewPort = new ExtendViewport(FlappyPug.WIDTH / 2,FlappyPug.HEIGHT / 2,new OrthographicCamera());
        skin = new Skin(Gdx.files.internal("textSkin/comic-ui.json"));
        stage = new Stage(viewPort, game.batch);


        initBackground();
        leaderBoardInit();

        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                returnToMenu();
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        backStage.addActor(backgroundTexture);
        stage.addActor(backGroundPanel);
        stage.addActor(scoreTable);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backStage.draw();
        stage.act(delta);
        stage.draw();
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
        skin.dispose();
    }

    public void initBackground(){
        backViewPort = new ExtendViewport( FlappyPug.WIDTH, FlappyPug.HEIGHT );
        backStage = new Stage(backViewPort);
        backgroundTexture  = new Image(manager.get("images/openingscreen.png",Texture.class));
        backgroundTexture.setFillParent(true);
    }

    public void leaderBoardInit(){
        //todo MUST FIX THIS LEADERBAORD!! IT SHOULDNT WORK LIKE THIS
        initBackGroundPanel();
        list = new List(skin);
        ArrayList testtemp  = FlappyPug.handler.getScoreList();

        String[] temp412 = new String[testtemp.size()];

        for(int i = 0; i< testtemp.size(); i++){
            temp412[i] = (i+1)+". "+(testtemp.get(i).toString());
        }


        list.setItems(temp412);
       // list.setItems(new String[]{"dor","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy"});
        //list.setItems(FlappyPug.handler.getScoreList());
        //ArrayList tempArrayList = FlappyPug.handler.getScoreList();
        //list.setItems(FlappyPug.handler.getScoreList());



        menuTitle  = new Image(manager.get("images/highscore.png",Texture.class));
        playBtn = new Image(manager.get("images/homebtn.png",Texture.class));

        scoreTable =new Table();
        scrollPane = new ScrollPane(list,skin);


        scoreTable.setBounds(0,0,170,375);
        scoreTable.setPosition(backGroundPanel.getX()+scoreTable.getWidth()/4,backGroundPanel.getY()+scoreTable.getHeight()/11);

        //scoreTable.debug();
        //table.setFillParent(true);

        //table.add(menuTitle);
        scoreTable.add(menuTitle).height(75f);
        scoreTable.row();
        scoreTable.add(scrollPane).fill().expand();
        scoreTable.row();
        scoreTable.add(playBtn).width(75f).height(75f).bottom();
        //table.row();
        //table.add(playBtn);

    }



    public void initBackGroundPanel(){
        backGroundPanel = new Image(manager.get("images/windowpanel.png",Texture.class));
        backGroundPanel.setSize(260,420);
        backGroundPanel.setPosition(FlappyPug.WIDTH/2-backGroundPanel.getWidth()+10,FlappyPug.HEIGHT/2-backGroundPanel.getHeight());
    }

    public void returnToMenu(){
        game.setScreen(new MainMenuScreen((FlappyPug) game,false));
        dispose();
    }
}
