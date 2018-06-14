package com.dorashush.game.Screens;

/**
 * Created by Dor on 05/19/18.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.IntArray;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Scenes.SpinWheel;
import com.dorashush.game.Tools.BaseScreen;
import com.dorashush.game.Tools.MyCurrencyTextButton;
import com.dorashush.game.Tools.UserProfile;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class SpeenWheelScreen extends BaseScreen {
    private static final String TAG = SpeenWheelScreen.class.getSimpleName();

    // These attributes according with your UI designing.
    private static final float WHEEL_DIAMETER = 750F;
    private static final int NUMBER_OF_PEGS = 12;

    // only Box2D structure (without UI designing) as a Model.
    private SpinWheel spinWheel;

    // images for SpinWheel UI.
    private Image wheelImage;
    private Image needleImage;
    private AssetManager manager;
    private final TextureAtlas atlas;
    private Image background,diamondsRewardImg,moneyRewardImg,spinsRewardImage;
    private FlappyPug game;
    private Drawable coinCounterImage,dimondCounterImage,wheelCounterImage,bigBtnPushed,bigBtnNoPushed,tableBackground;
    private Table countersTable,rewardTable;
    private MyCurrencyTextButton diamonCounter,coinCounter,spinsCounter;
    private ImageButton bigButton;
    private Skin skin;
    private TextButton continueBtn;
    private boolean showReward,rewardFirstShow;
    private UserProfile userProfile;

    public SpeenWheelScreen(FlappyPug game) {
        this.manager = game.getManager();
        this.game = game;
        this.userProfile = UserProfile.getInstance();

        atlas = manager.get("atlas/spin_wheel_ui.atlas",TextureAtlas.class);
        background  = new Image(manager.get("images/loadingbackground.png",Texture.class));
        background.setFillParent(true);
        coinCounterImage = new TextureRegionDrawable(new TextureRegion(manager.get("images/coincounter.png",Texture.class)));
        dimondCounterImage = new TextureRegionDrawable(new TextureRegion(manager.get("images/dimondcounter.png",Texture.class)));
        wheelCounterImage = new TextureRegionDrawable(new TextureRegion(manager.get("images/spincounterimage.png",Texture.class)));


        skin = new Skin(Gdx.files.internal("textSkin/comic-ui.json"));
        TextButton.TextButtonStyle coinCounterStyle = new TextButton.TextButtonStyle(coinCounterImage,coinCounterImage,coinCounterImage,skin.getFont("title"));
        TextButton.TextButtonStyle dimonCounterStyle = new TextButton.TextButtonStyle(dimondCounterImage,dimondCounterImage,dimondCounterImage,skin.getFont("title"));
        TextButton.TextButtonStyle spinsCounterStyle = new TextButton.TextButtonStyle(wheelCounterImage,wheelCounterImage,wheelCounterImage,skin.getFont("title"));

        coinCounter = new MyCurrencyTextButton(userProfile.getMoney()+"",coinCounterStyle);
        diamonCounter = new MyCurrencyTextButton(userProfile.getDiamonds()+"",dimonCounterStyle);
        spinsCounter = new MyCurrencyTextButton(userProfile.getSpins()+"",spinsCounterStyle);

        countersTable = new Table();
        //countersTable.top().center();
        //countersTable.setFillParent(true);

        countersTable.add(diamonCounter).expand().fill();
        countersTable.row();
        countersTable.add(coinCounter).expand().fill();
        countersTable.row();
        countersTable.add(spinsCounter).expand().fill();
        countersTable.setOrigin(Align.center);

        bigBtnPushed = new TextureRegionDrawable(new TextureRegion(manager.get("images/bigbtnpushed.png",Texture.class)));
        bigBtnNoPushed = new TextureRegionDrawable(new TextureRegion(manager.get("images/bigbtnnotpush.png",Texture.class)));

        ImageButton.ImageButtonStyle bigBtnStyle = new ImageButton.ImageButtonStyle(bigBtnNoPushed,bigBtnPushed,bigBtnNoPushed,bigBtnNoPushed,bigBtnPushed,bigBtnNoPushed);

        bigButton = new ImageButton(bigBtnStyle);

        continueBtn = new TextButton("Continue",skin);

        rewardTable = new Table();
        rewardTable.setVisible(true);
        rewardTable.setTransform(true);
        showReward = false;
        rewardFirstShow = true;

        diamondsRewardImg = new Image(manager.get("images/diamond.png", Texture.class));
        moneyRewardImg = new Image(manager.get("images/money.png", Texture.class));
        spinsRewardImage = new Image(manager.get("images/wheel.png", Texture.class));

        tableBackground = new TextureRegionDrawable(new TextureRegion(manager.get("images/windowpanel.png",Texture.class)));


    }

    @Override
    public void show() {
        super.show();

        final float width = stage.getWidth();
        final float height = stage.getHeight();

        rewardTable.setBounds(0,0,width/2f,height/3);
        rewardTable.setOrigin(Align.center);
        rewardTable.setPosition(width/2,height/2,Align.center);

        countersTable.setBounds(0,0,width/2.5f,height/6);
        countersTable.setPosition(width-countersTable.getWidth()/2,height-countersTable.getHeight(),Align.center);

        // initialize SpinWheel.
        spinWheel = new SpinWheel(width, height, WHEEL_DIAMETER, width / 2, height / 2, NUMBER_OF_PEGS);

        /* *************** SpinWheel UI ************** */
        // load atlas file with all SpinWheel UI elements.

        // create wheel image
        spinWheel.getWheelBody().setUserData(wheelImage = new Image(atlas.findRegion("spin_wheel")));
        updateCoordinates(spinWheel.getWheelBody(), wheelImage, 0, 0);
        wheelImage.setOrigin(Align.center);
        stage.addActor(background);

        stage.addActor(countersTable);
        stage.addActor(wheelImage);

        bigButton.setOrigin(Align.center);
        bigButton.setPosition(width / 2, bigButton.getHeight()/2, Align.center);

        stage.addActor(bigButton);

        // create spin image button.
        final Image btnSpin = new Image(atlas.findRegion("spin_button"));
        btnSpin.setOrigin(Align.center);
        btnSpin.setPosition(width / 2, height / 2, Align.center);
        stage.addActor(btnSpin);

        stage.addActor(rewardTable);

        // add listener to spin button.
        /*
        btnSpin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                    btnSpin.addAction(sequence(scaleTo(1.25F, 1.25F, 0.10F), scaleTo(1F, 1F, 0.10F)));
                if(!spinWheel.spinningStopped() && FlappyPug.SPINS > 0 && !showReward) {
                    spinWheel.spin(MathUtils.random(5F, 30F));
                    FlappyPug.SPINS-=1;
                    FlappyPug.flappyDogPreferences.putInteger("spins",FlappyPug.SPINS);
                    FlappyPug.flappyDogPreferences.flush();
                    spinsCounter.setText(FlappyPug.SPINS+"");
                }

                Gdx.app.debug(TAG, "Spinning.");
            }
        });
        */

        bigButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean temp = spinWheel.spinningStopped();
                if(spinWheel.spinningStopped() && userProfile.getSpins() > 0 && !showReward) {
                    spinWheel.spin(MathUtils.random(5F, 28F));
                    userProfile.setSpins(userProfile.getSpins()-1);
                    spinsCounter.setText(userProfile.getSpins()+"");
                }

                Gdx.app.debug(TAG, "Spinning.");
            }
        });

        continueBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rewardTable.clear();
                rewardTable.setVisible(false);
                showReward=false;
                rewardFirstShow = true;
            }
        });

        // create needle image
        spinWheel.getNeedleBody().setUserData(needleImage = new Image(new Sprite(atlas.findRegion("needle"))));
        updateCoordinates(spinWheel.getNeedleBody(), needleImage, 0, -25F);
        needleImage.setOrigin(spinWheel.getNeedleCenterX(needleImage.getWidth()), spinWheel.getNeedleCenterY(needleImage.getHeight()));
        stage.addActor(needleImage);

        setElementData();
    }

    private void updateCoordinates(Body body, Image image, float incX, float incY) {
        image.setPosition((body.getPosition().x * SpinWheel.PPM) + incX, (body.getPosition().y * SpinWheel.PPM) + incY, Align.center);
        image.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }


    private void setElementData() {/*
        spinWheel.addElementData(Color.valueOf("e966ac"), getData(1, 2));
        spinWheel.addElementData(Color.valueOf("b868ad"), getData(2, 3));
        spinWheel.addElementData(Color.valueOf("8869ad"), getData(3, 4));
        spinWheel.addElementData(Color.valueOf("3276b5"), getData(4, 5));
        spinWheel.addElementData(Color.valueOf("33a7d8"), getData(5, 6));
        spinWheel.addElementData(Color.valueOf("33b8a5"), getData(6, 7));
        spinWheel.addElementData(Color.valueOf("a3fd39"), getData(7, 8));
        spinWheel.addElementData(Color.valueOf("fff533"), getData(8, 9));
        spinWheel.addElementData(Color.valueOf("fece3e"), getData(9, 10));
        spinWheel.addElementData(Color.valueOf("f9a54b"), getData(10, 11));
        spinWheel.addElementData(Color.valueOf("f04950"), getData(12, 1));
        */
        spinWheel.addElementData("1", getData(1, 2));
        spinWheel.addElementData("2", getData(2, 3));
        spinWheel.addElementData("3", getData(3, 4));
        spinWheel.addElementData("4", getData(4, 5));
        spinWheel.addElementData("5", getData(5, 6));
        spinWheel.addElementData("6", getData(6, 7));
        spinWheel.addElementData("7", getData(7, 8));
        spinWheel.addElementData("8", getData(8, 9));
        spinWheel.addElementData("9", getData(9, 10));
        spinWheel.addElementData("10", getData(10, 11));
        spinWheel.addElementData("11", getData(11, 12));
        spinWheel.addElementData("12", getData(12, 1));

    }

    private IntArray getData(int peg_1, int peg_2) {
        IntArray array = new IntArray(2);
        array.addAll(peg_1, peg_2);
        return array;
    }

    @Override
    public void render(float delta) {
        super.render(delta);



        spinWheel.render(false);

        if (!spinWheel.spinningStopped()) {
            // update wheel
            updateCoordinates(spinWheel.getWheelBody(), wheelImage, 0, 0);

            // update needle
            updateCoordinates(spinWheel.getNeedleBody(), needleImage, 0, -25F);

            if(!spinWheel.isBeforeFirstSpin() && !spinWheel.isDoubleSpin())
                showReward = true;
        }

        else if(spinWheel.spinningStopped()){
            System.out.println("lucky element is: " + spinWheel.getLuckyWinElement());
            if(spinWheel.getLuckyWinElement()!=null && showReward && rewardFirstShow) {
                rewardTable.addAction(sequence(scaleTo(.1f, .1f), parallel( scaleTo(1f, 1f, 0.5f, Interpolation.pow5)), delay(0.2F)));
                handleUserGetsReward(Integer.parseInt(spinWheel.getLuckyWinElement().toString()));
                rewardTable.act(delta);
            }
            //todo from here you get the reward info
           // Color color = (Color) spinWheel.getLuckyWinElement();
           // Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);

        }

        /*
        Color color = (Color) spinWheel.getLuckyWinElement();
        if (color != null)
            Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
*/

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
            spinWheel.spin(0.2F);
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new MainMenuScreen(game,false));
            dispose();
        }
//        countersTable.debug();
        //rewardTable.debug();

    }

    public void handleUserGetsReward(int reward){

        rewardTable.setBackground(tableBackground);
        rewardTable.add(getRightRewardPic(reward));
        rewardTable.row().padBottom(10f);
        rewardTable.add(continueBtn).width(400f).height(200f).fill();
        rewardTable.setVisible(true);
        rewardFirstShow = false;
        giveRewardToUser(reward);

    }

    public void giveRewardToUser(int reward){

        switch(reward){
            case 1:
                userProfile.setDiamonds(userProfile.getDiamonds()+5);
                diamonCounter.setText(userProfile.getDiamonds()+"");
                break;
            case 2:
                userProfile.setMoney(userProfile.getMoney()+100);
                coinCounter.setText(userProfile.getMoney()+"");
                break;
            case 3:
                userProfile.setSpins(userProfile.getSpins()+5);
                spinsCounter.setText(userProfile.getSpins()+"");
                break;
            case 4:
                userProfile.setMoney(userProfile.getMoney()+100);
                coinCounter.setText(userProfile.getMoney()+"");
                break;
            case 5:
                userProfile.setSpins(userProfile.getSpins()+2);
                spinsCounter.setText(userProfile.getSpins()+"");
                break;
            case 6:
                userProfile.setDiamonds(userProfile.getDiamonds()+5);
                diamonCounter.setText(userProfile.getDiamonds()+"");
                break;
            case 7:
                userProfile.setSpins(userProfile.getSpins()+3);
                spinsCounter.setText(userProfile.getSpins()+"");
                break;
            case 8:
                userProfile.setMoney(userProfile.getMoney()+200);
                coinCounter.setText(userProfile.getMoney()+"");
                break;
            case 9:
                userProfile.setSpins(userProfile.getSpins()+2);
                spinsCounter.setText(userProfile.getSpins()+"");
                break;
            case 10:
                userProfile.setMoney(userProfile.getMoney()+150);
                coinCounter.setText(userProfile.getMoney()+"");
                break;
            case 11:
                userProfile.setSpins(userProfile.getSpins()+1);
                spinsCounter.setText(userProfile.getSpins()+"");
                break;
            case 12:
                userProfile.setMoney(userProfile.getMoney()+500);
                coinCounter.setText(userProfile.getMoney()+"");
                break;
        }
    }

    public Image getRightRewardPic(int reward){
        Image imageToUse=null;

        switch(reward){
            case 1:
                imageToUse = diamondsRewardImg;
                break;
            case 2:
                imageToUse = moneyRewardImg;
                break;
            case 3:
                imageToUse = spinsRewardImage;
                break;
            case 4:
                imageToUse = moneyRewardImg;
                break;
            case 5:
                imageToUse = spinsRewardImage;
                break;
            case 6:
                imageToUse = diamondsRewardImg;
                break;
            case 7:
                imageToUse = spinsRewardImage;
                break;
            case 8:
                imageToUse = moneyRewardImg;
                break;
            case 9:
                imageToUse = spinsRewardImage;
                break;
            case 10:
                imageToUse = moneyRewardImg;
                break;
            case 11:
                imageToUse = spinsRewardImage;
                break;
            case 12:
                imageToUse = moneyRewardImg;
                break;
        }

        return imageToUse;
    }
    @Override
    public void dispose() {
        super.dispose();
        spinWheel.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            game.setScreen(new MainMenuScreen(game,false));
            dispose();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}