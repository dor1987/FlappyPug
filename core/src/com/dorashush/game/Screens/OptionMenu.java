package com.dorashush.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.game.Abstract.ScreenWithPopUps;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Scenes.NameWindow;

/**
 * Created by Dor on 04/01/18.
 */

public class OptionMenu extends ScreenWithPopUps{
    private Viewport viewPort;
    private Stage stage,backStage;
    private Game game;
    private Skin skin;
    private Table mainTitleTable,nameCheckTable,volumeTable,vibrationTable,homeBtnTable;
    private ExtendViewport backViewPort;
    private Image backgroundTexture,backGroundPanel,backBtn,settingTitle;
    ButtonGroup soundButtonGroup,controlButtonGroup,vibreationButtonGroup;
    private TextArea cheatTextArea;
    private AssetManager manager;
    private static Label nameCheckLabel,volumeLabel,volumePercentLabel,vibrationLabel;
    private TextButton changeNameBtn,vibreationOnButton,vibreationOffButton;
    private Slider volumeControlSlider;
    private NameWindow nameWindow;
    private boolean isNameWindowOn;

    public OptionMenu(FlappyPug game){
        this.game = game;
        this.manager= game.getManager();
        viewPort = new ExtendViewport(FlappyPug.WIDTH / 2,FlappyPug.HEIGHT / 2,new OrthographicCamera());
        stage = new Stage(viewPort,(game.batch));
        skin = new Skin(Gdx.files.internal("textSkin/uiskin.json"));

        //nameWindow = new NameWindow(this, game.batch,manager);
        initBackground();
        initBackGroundPanel();

        initTabels();
        addListeners();

        nameWindow = new NameWindow(this, game.batch);
        isNameWindowOn= false;

    }

    public void addListeners(){
        Gdx.input.setCatchBackKey(true);


        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                returnToMenu();
            }
        });

        changeNameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
               // popUpWindowControl("NameWindow",true);
                nameWindow.setStatus(true);

            }
        });


    }

    public void initBackGroundPanel(){
        backGroundPanel = new Image(manager.get("images/windowpanel.png",Texture.class));
        backGroundPanel.setSize(240,400);
        backGroundPanel.setPosition(FlappyPug.WIDTH/2-backGroundPanel.getWidth(),FlappyPug.HEIGHT/2-backGroundPanel.getHeight());
    }

    public void initTabels(){
        initTitleTable();
        initNameCheckTable();
        initSliderTable();
        initVibrationTable();
        initHomeButtonTable();
    }



    public void initTitleTable(){
        settingTitle  = new Image(manager.get("images/settings.png",Texture.class));

        mainTitleTable = new Table();

        mainTitleTable.setSize(200,75);
        mainTitleTable.setPosition(backGroundPanel.getX()+mainTitleTable.getWidth()/9,backGroundPanel.getY()+backGroundPanel.getHeight()-mainTitleTable.getHeight());
        mainTitleTable.add(settingTitle).expandX().height(100f);

    }


    public void initNameCheckTable(){
        nameCheckTable = new Table();
        nameCheckTable.setSize(197,50);
        nameCheckTable.setPosition(mainTitleTable.getX(),mainTitleTable.getY()-nameCheckTable.getHeight());

        nameCheckLabel = new Label("Not "+FlappyPug.NAME+"?", skin);
        changeNameBtn = new TextButton("Change Name",skin);

        nameCheckTable.add(nameCheckLabel).padRight(5f);
        nameCheckTable.row();
        nameCheckTable.add(changeNameBtn);


    }

    public void initSliderTable(){

        volumeTable = new Table();
        volumeTable.setSize(197,75);
        volumeTable.setPosition(nameCheckTable.getX(),nameCheckTable.getY()-volumeTable.getHeight());

        volumeLabel = new Label("Volume", skin);
        volumeControlSlider = new Slider(0,1,0.1f,false,skin);
        volumeControlSlider.setValue(FlappyPug.VOLUME);

        volumePercentLabel = new Label((volumeControlSlider.getValue()*100)+"%", skin);

        volumeTable.add(volumeLabel);
        volumeTable.row();
        volumeTable.add(volumeControlSlider);
        volumeTable.row();
        volumeTable.add(volumePercentLabel);

    }


    public void initVibrationTable(){

        vibrationTable = new Table();
        vibrationTable.setSize(197,75);
        vibrationTable.setPosition(volumeTable.getX(),volumeTable.getY()-vibrationTable.getHeight());

        vibrationLabel = new Label("Vibration", skin);

        vibreationOnButton = new TextButton("ON", skin,"toggle");
        vibreationOffButton = new TextButton("OFF", skin,"toggle");
        vibreationButtonGroup = new ButtonGroup(vibreationOnButton, vibreationOffButton);


        if(FlappyPug.VIBRATION==true)
            vibreationButtonGroup.setChecked("ON");
        else if(FlappyPug.VIBRATION==false){
            vibreationButtonGroup.setChecked("OFF");
        }

        vibrationTable.add(vibrationLabel);
        vibrationTable.row();
        vibrationTable.add(vibreationOnButton).width(85).height(30);
        vibrationTable.add(vibreationOffButton).width(85).height(30);

    }


    public void initHomeButtonTable(){

        homeBtnTable = new Table();
        homeBtnTable.setSize(197,120);
        homeBtnTable.setPosition(volumeTable.getX(),vibrationTable.getY()-vibrationTable.getHeight());

        backBtn  = new Image(manager.get("images/homebtn.png",Texture.class));


        homeBtnTable.add(backBtn).width(100).expandX().padTop(30);


    }


    public void initBackground(){
        backViewPort = new ExtendViewport( FlappyPug.WIDTH, FlappyPug.HEIGHT );
        backStage = new Stage(backViewPort);
        backgroundTexture  = new Image(manager.get("images/openingscreen.png",Texture.class));
        backgroundTexture.setFillParent(true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        backStage.addActor(backgroundTexture);

        stage.addActor(backGroundPanel);
        stage.addActor(mainTitleTable);
        stage.addActor(nameCheckTable);
        stage.addActor(volumeTable);
        stage.addActor(vibrationTable);
        stage.addActor(homeBtnTable);

    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backStage.act();
        backStage.draw();

        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            returnToMenu();
        }

        if(nameWindow.isStatus()){
            nameWindow.draw(delta);
        }

        else{
            nameCheckLabel.setText("Not "+FlappyPug.NAME+"?");
        }

        if(!nameWindow.isStatus()){
            Gdx.input.setInputProcessor(stage);
        }

        setVolumeLabel();
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width,height);

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
        stage.dispose();
        backStage.dispose();
    }

    public void returnToMenu(){
        game.setScreen(new MainMenuScreen((FlappyPug) game,false));
        dispose();
    }

    public void setVolumeLabel(){
        float temp = (volumeControlSlider.getValue()*100);
        volumePercentLabel.setText(String.format("%3.0f",temp)+"%");
        FlappyPug.VOLUME = temp/100;
    }



    @Override
    public void popUpWindowControl(String popUpName, boolean openWindow) {
        if(popUpName.compareTo("NameWindow")==0)
            isNameWindowOn = openWindow;

        isNameWindowOn = openWindow;
    }

    @Override
    public AssetManager getManager() {
        return manager;
    }
}