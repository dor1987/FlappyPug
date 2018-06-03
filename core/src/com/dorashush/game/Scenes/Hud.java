package com.dorashush.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.game.FlappyPug;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.dorashush.game.Screens.PlayScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.repeat;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sizeBy;

/**
 * Created by Dor on 03/28/18.
 */

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewPort;
    private float timeCount;
    private float speed;
    private static float timePassed;
    private float distance;
    private Integer countDownTimer;
    private static Label timeLabel, distanceLabel, speedLabel, testLabel;
    private static Table lettersTable;
    private Table centerOfScreenTable,table;
    private static Image pImage, uImage, gImage,touchImage,avatarImage,topBar,botBar;
    private boolean gameOver;
    private Skin skin;
    private AssetManager manager;
    //private static Group group;

    public Hud(SpriteBatch sb, PlayScreen screen) {
        this.manager = screen.getManager();

        distance = 0;
        speed = 0;
        timePassed = 0;
        //group = new Group();
        viewPort = new ExtendViewport(FlappyPug.WIDTH / 2, FlappyPug.HEIGHT / 2, new OrthographicCamera());
        stage = new Stage(viewPort, sb);
        skin = new Skin(Gdx.files.internal("textSkin/comic-ui.json"));
        countDownTimer = 3;
        timeCount = 0;

        initLettersImages();
        initLabels();
        initTable();
        final float width = stage.getWidth();
        final float height = stage.getHeight();
        topBar.setBounds(0,0,width,height/9);
        //topBar.setOrigin(Align.center);
        topBar.setPosition(0,height-topBar.getHeight());

        stage.addActor(topBar);

        botBar.setBounds(0,0,width,height/6f);
        //botBar.setOrigin(Align.center);
        botBar.setPosition(0,0);

        stage.addActor(botBar);

        avatarImage.setBounds(0,0,width/3.7f,width/3.7f);
        avatarImage.setOrigin(Align.center);
        avatarImage.setPosition(width-botBar.getWidth()/7.3f,botBar.getHeight()-avatarImage.getHeight()/1.8f,Align.center);

        stage.addActor(avatarImage);

        //initCenterOfScreenTable();
        stage.addActor(table);
        stage.addActor(lettersTable);
        stage.addActor(centerOfScreenTable);
        gameOver = false;
    }

    public void initLettersImages() {
        pImage = new Image(manager.get("images/pnobubble.png", Texture.class));
        uImage = new Image(manager.get("images/unobubble.png", Texture.class));
        gImage = new Image(manager.get("images/gnobbuble.png", Texture.class));
        //group.addActor(pImage);
        //group.addActor(uImage);
        //group.addActor(gImage);
        touchImage = new Image(manager.get("images/touch.png", Texture.class));
        avatarImage = new Image(manager.get("images/avatar.png", Texture.class));
        topBar = new Image(manager.get("images/topbar.png", Texture.class));
        botBar = new Image(manager.get("images/botbar.png", Texture.class));

    }

    public void initLabels() {
        timeLabel = new Label(String.format("Time %04.2f", timePassed), skin);
        testLabel = new Label(String.format("%d", countDownTimer), skin, "title");
        distanceLabel = new Label(String.format("Distance:\t%04.2f\tMeters", distance), skin);
        speedLabel = new Label(String.format("%04.2f Km/h", speed), skin);

    }

    public void initTable() {
        table = new Table();
        table.center().top();
        table.setFillParent(true);

       // table.setBackground(topHud);
        table.add(timeLabel).width(50f).padTop(15f).padRight(10f);

        //table.add(speedLabel).expandX().padBottom(25f);
        lettersTable = new Table();
        lettersTable.center().bottom();
        lettersTable.setFillParent(true);

        //lettersTable.setBounds(FlappyPug.WIDTH/3-lettersTable.getWidth()*1.5f,Gdx.graphics.getHeight()/6,60f,20f);
        //lettersTable.setPosition(,Gdx.graphics.getHeight()/6);
        lettersTable.setTransform(true);
        //group.setTransform(true);

        lettersTable.setOrigin(lettersTable.getWidth()/2,lettersTable.getHeight()/2);

       // lettersTable.setBackground(botHud);
        lettersTable.add(pImage).width(20f).height(20f).padBottom(10f).padRight(5f);
        lettersTable.add(uImage).width(20f).height(20f).padBottom(10f).padRight(5f);
        lettersTable.add(gImage).width(20f).height(20f).padBottom(10f).padRight(5f);
        //lettersTable.debug();
        setLettersViability();

        centerOfScreenTable = new Table();
        centerOfScreenTable.center().left();
        centerOfScreenTable.setFillParent(true);
        centerOfScreenTable.setTransform(true);
        centerOfScreenTable.add(touchImage).width(50f).height(50f);
        centerOfScreenTable.row();
        centerOfScreenTable.add(testLabel).expandX();
        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                touchImage.setVisible(false);
            }
        };
        touchImage.addAction(sequence(repeat(4,sequence(scaleTo(1.25F, 1.25F, 0.30F), scaleTo(1F, 1F, 0.30F))),run(transitionRunnable)));

    }



    public void update(float dt) {





        stage.act();

        if (!gameOver) {
            if (countDownTimer <= 0) {
                timePassed += dt;
                distance += dt * speed;
                timeLabel.setText(String.format("Time %04.2f", timePassed));
                distanceLabel.setText(String.format("Distance:\t%04.2f\tMeters", distance));
                //speedLabel.setText(String.format("%04.2f Km/h",speed));
            }
        }
        if (countDownTimer > 1) {
            timeCount += dt;
            if (timeCount >= 1) {
                countDownTimer--;
                testLabel.setText(String.format("%d", countDownTimer));
                timeCount = 0;
            }
        } else if (countDownTimer == 1) {
            timeCount += dt;
            if (timeCount >= 1) {
                countDownTimer--;
                testLabel.setText("GO!");
                timeCount = 0;
            }
        } else {
            timeCount += dt;
            if (timeCount >= 1) {

                testLabel.setText("");
                timeCount = 0;
            }
        }
        //group.act(dt);
        pImage.act(dt);
        uImage.act(dt);
        gImage.act(dt);

    }

    public Integer getCountDownTimer() {
        return countDownTimer;
    }

    public float getTime() {
        return timePassed;
    }

    public void setGameOver() {
        this.gameOver = true;
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();

    }

    public void setSpeed(float speed) {
        this.speed = -speed;
    }

    public static void onLetterChangedStatus(boolean visible, char letter) {
        switch (letter) {
            case 'p':
                letterVisualControl(visible,pImage);
                break;
            case 'u':
                letterVisualControl(visible,uImage);
                break;
            case 'g':
                letterVisualControl(visible,gImage);
                break;
            case 't':
                timePassed+=5;
                break;
        }

    }



    public void setLettersViability(){
        letterVisualControl(false,pImage);
        letterVisualControl(false,uImage);
        letterVisualControl(false,gImage);
    }
    public static void hideAllLetters(){
        /*
        letterVisualControl(false,pImage);
        letterVisualControl(false,uImage);
        letterVisualControl(false,gImage);
        */


        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
               letterVisualControl(false,pImage);
               letterVisualControl(false,uImage);
               letterVisualControl(false,gImage);
               //lettersTable.scaleBy(0.9f);

            }
        };

        //group.addAction(sequence(parallel(sequence(scaleTo(1.4f,1.4f,0.5f),scaleTo(.1f,.1f,0.5f)),rotateBy(360,1f)),run(transitionRunnable)));
        uImage.addAction(sequence(parallel(sequence(scaleTo(1.4f,1.4f,0.5f),scaleTo(.1f,.1f,0.5f)),rotateBy(360,1f)),run(transitionRunnable),scaleTo(1f,1f)));
        gImage.addAction(sequence(parallel(sequence(scaleTo(1.4f,1.4f,0.5f),scaleTo(.1f,.1f,0.5f)),rotateBy(360,1f)),run(transitionRunnable),scaleTo(1f,1f)));
        pImage.addAction(sequence(parallel(sequence(scaleTo(1.4f,1.4f,0.5f),scaleTo(.1f,.1f,0.5f)),rotateBy(360,1f)),run(transitionRunnable),scaleTo(1f,1f)));

    }
    public static void letterVisualControl(boolean visible, Actor actor) {
        if (lettersTable.getChildren().contains(actor, true)) {
            actor.setVisible(visible);
        }
    }
}