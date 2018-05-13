package com.dorashush.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.dorashush.game.Abstract.ScreenWithPopUps;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Scenes.NoInternetWindow;
import com.dorashush.game.Tools.MyList;
import com.dorashush.game.Tools.MyTextButton;

import java.util.ArrayList;

/**
 * Created by Dor on 05/05/18.
 */

public class LeaderBoardScreen extends ScreenWithPopUps {
    private Game game;
    private OrthographicCamera camera;
    private Stage stage,backStage;
    private Table scoreTable,btnsTable;
    private Image playBtn,menuTitle,backgroundTexture,backGroundPanel;
    private AssetManager manager;
    private ExtendViewport viewPort,backViewPort;
    private ScrollPane scrollPane;
    //Name windows
    private Skin skin,skin2;
    private List list;
    private MyList myList;
    private ImageTextButton scoreLine;
    private Drawable scoreLineImage;
    private NoInternetWindow noInternetWindow;

    public LeaderBoardScreen(FlappyPug game) {
        this.game = game;
        this.manager =game.getManager();

        viewPort = new ExtendViewport(FlappyPug.WIDTH / 2,FlappyPug.HEIGHT / 2,new OrthographicCamera());
        skin = new Skin(Gdx.files.internal("textSkin/comic-ui.json"));
        skin2 = new Skin(Gdx.files.internal("textSkin/uiskin.json"));

        stage = new Stage(viewPort, game.batch);
        scoreLineImage = new TextureRegionDrawable(new TextureRegion(manager.get("images/testscoreline.png",Texture.class)));
        noInternetWindow = new NoInternetWindow(this,game.batch);

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

        if(noInternetWindow.isStatus()){
            noInternetWindow.draw(delta);
        }
        if(noInternetWindow.isBackToMainMenu())
            returnToMenu();

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
       // List.ListStyle listStyle = new List.ListStyle(skin.getFont("font"), Color.BLACK,Color.BLUE,scoreLineImage);
        //list = new List(skin);
        list = new List(skin);
        myList = new MyList(skin);

        ArrayList testtemp  = FlappyPug.handler.getScoreList();


        //String[] temp412 = new String[testtemp.size()];
/*
        for(int i = 0; i< testtemp.size(); i++){
            temp412[i] = String.format("%4d",(i+1))+". "+(testtemp.get(i).toString());
        }
*/
        MyTextButton[] textButtons = new  MyTextButton[100];
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(scoreLineImage,scoreLineImage,scoreLineImage,skin.getFont("font"));
        if(testtemp.size()!=0){
            for(int i = 0; i< testtemp.size(); i++) {
                String[] words = ((String)testtemp.get(i)).split("\\|");
                //textButtons[i] = new MyTextButton(String.format("%-3d",(i+1))+(testtemp.get(i).toString()),textButtonStyle);
                textButtons[i] = new MyTextButton((i+1)+".",words[0],words[1],textButtonStyle);

            }
        }

        else{
            for(int i = 0; i< 100; i++) {
                //textButtons[i] = new MyTextButton(String.format("%-3d",(i+1))+(testtemp.get(i).toString()),textButtonStyle);
                textButtons[i] = new MyTextButton((i+1)+".","","",textButtonStyle);
            }
            noInternetWindow.setStatus(true);

        }

            //  ImageTextButton[] temp412 = new ImageTextButton[testtemp.size()];
        //ImageTextButton.ImageTextButtonStyle tempStyle = new ImageTextButton.ImageTextButtonStyle(scoreLineImage,scoreLineImage,scoreLineImage,skin.getFont("font"));
/*
        TextButton.TextButtonStyle tempStyle = new TextButton.TextButtonStyle(scoreLineImage,scoreLineImage,scoreLineImage,skin.getFont("font"));

        Array<TextButton> temp412 = new Array<TextButton>();

        for(int i = 0; i< testtemp.size(); i++){
            temp412.add(new TextButton("Text",tempStyle));
        }
*/

        //list.setItems(temp412);
        myList.setItems(textButtons);


      //    list.setItems(tempArrayImage);

        // list.setItems(new String[]{"dor","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy","tal","guy"});
        //list.setItems(FlappyPug.handler.getScoreList());
        //ArrayList tempArrayList = FlappyPug.handler.getScoreList();
        //list.setItems(FlappyPug.handler.getScoreList());



        menuTitle  = new Image(manager.get("images/highscore.png",Texture.class));
        playBtn = new Image(manager.get("images/homebtn.png",Texture.class));

        scoreTable =new Table(skin);

        scrollPane = new ScrollPane(myList,skin);

        scoreTable.setBounds(0,0,170,375);
        scoreTable.setPosition(backGroundPanel.getX()+scoreTable.getWidth()/4,backGroundPanel.getY()+scoreTable.getHeight()/11);

        //scoreTable.debug();
        //table.setFillParent(true);

        //table.add(menuTitle);
        scoreTable.add(menuTitle).height(75f);
        scoreTable.row();
        scoreTable.add(String.format("%-13s %s","Name","Score"));
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

    @Override
    public void popUpWindowControl(String popUpName, boolean openWindow) {

    }

    @Override
    public AssetManager getManager() {
        return manager;
    }
/*
    private void createBasicSkin(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Cornerstone.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        skin = new Skin();
        skin.add("default", font);

        Pixmap pixmap = new Pixmap(width/4,height/10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
    }
    */
}
