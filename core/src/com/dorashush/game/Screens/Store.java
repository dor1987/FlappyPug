package com.dorashush.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Tools.BaseScreen;
import com.dorashush.game.Tools.MyCurrencyTextButton;
import com.dorashush.game.Tools.MyStorePowerUpBtn;
import com.dorashush.game.Tools.UserProfile;

/**
 * Created by Dor on 06/09/18.
 */

public class Store extends BaseScreen implements MyStorePowerUpBtn.BuyListener {

    private Image background, storePanel;
    private Image toggleBtn;//toggle btn is a temp things need to replace with real btn
    private AssetManager manager;
    private FlappyPug game;
    private MyCurrencyTextButton diamonCounter, coinCounter;
    private Drawable coinCounterImage, dimondCounterImage, invincBtnFrame, bonusTimeBtnFrame, powerUpBtnFrame,
            powerUpBuyBtnImage, powerUpBuyBtnImageClicked, upgradeOn, upgradeOff, startingTimeBtnFrame, speedRecudeBtnFrame;
    private MyStorePowerUpBtn shieldPowerUp, speedReducePowerUp, timeBonusPowerUp, startingTimeBonus;
    private Table countersTable, powerUpgradeTable;
    private Skin skin;
    private UserProfile userProfile;

    public Store(FlappyPug game) {
        this.manager = game.getManager();
        this.game = game;
        skin = new Skin(Gdx.files.internal("textSkin/comic-ui.json"));
        userProfile = UserProfile.getInstance();
        background = new Image(manager.get("images/loadingbackground.png", Texture.class));
        background.setFillParent(true);
        storePanel = new Image(manager.get("images/store/storepanel.png", Texture.class));
        storePanel.setFillParent(true);


        coinCounterImage = new TextureRegionDrawable(new TextureRegion(manager.get("images/coincounter.png", Texture.class)));
        dimondCounterImage = new TextureRegionDrawable(new TextureRegion(manager.get("images/dimondcounter.png", Texture.class)));
        powerUpBuyBtnImage = new TextureRegionDrawable(new TextureRegion(manager.get("images/store/buybtnnoclick.png", Texture.class)));
        powerUpBuyBtnImageClicked = new TextureRegionDrawable(new TextureRegion(manager.get("images/store/buybtnclick.png", Texture.class)));


        TextButton.TextButtonStyle coinCounterStyle = new TextButton.TextButtonStyle(coinCounterImage, coinCounterImage, coinCounterImage, skin.getFont("title"));
        TextButton.TextButtonStyle dimonCounterStyle = new TextButton.TextButtonStyle(dimondCounterImage, dimondCounterImage, dimondCounterImage, skin.getFont("title"));

        coinCounter = new MyCurrencyTextButton(userProfile.getMoney() + "", coinCounterStyle);
        diamonCounter = new MyCurrencyTextButton(userProfile.getDiamonds() + "", dimonCounterStyle);


        countersTable = new Table();

        countersTable.add(diamonCounter).expand().fill();
        countersTable.add(coinCounter).expand().fill();
        countersTable.setOrigin(Align.center);


        toggleBtn = new Image(manager.get("images/store/storetogglebtn.png", Texture.class));

        powerUpBtnFrame = new TextureRegionDrawable(new TextureRegion(manager.get("images/store/itemupgradeframe.png", Texture.class)));
        invincBtnFrame = new TextureRegionDrawable(new TextureRegion(manager.get("images/store/invinciblityframe.png", Texture.class)));
        bonusTimeBtnFrame = new TextureRegionDrawable(new TextureRegion(manager.get("images/store/timebonusfarme.png", Texture.class)));
        startingTimeBtnFrame = new TextureRegionDrawable(new TextureRegion(manager.get("images/store/startingtimeframe.png", Texture.class)));
        speedRecudeBtnFrame = new TextureRegionDrawable(new TextureRegion(manager.get("images/store/speedreduceframe.png", Texture.class)));

        upgradeOff = new TextureRegionDrawable(new TextureRegion(manager.get("images/store/upgradeno.png", Texture.class)));
        upgradeOn = new TextureRegionDrawable(new TextureRegion(manager.get("images/store/upgradeyes.png", Texture.class)));


        TextButton.TextButtonStyle emptyPowerUpStyle = new TextButton.TextButtonStyle(powerUpBtnFrame, powerUpBtnFrame, powerUpBtnFrame, skin.getFont("title"));
        TextButton.TextButtonStyle shieldPowerUpStyle = new TextButton.TextButtonStyle(invincBtnFrame, invincBtnFrame, invincBtnFrame, skin.getFont("title"));
        TextButton.TextButtonStyle bonusTimePowerUpStyle = new TextButton.TextButtonStyle(bonusTimeBtnFrame, bonusTimeBtnFrame, bonusTimeBtnFrame, skin.getFont("title"));
        TextButton.TextButtonStyle startingTimeBonusStyle = new TextButton.TextButtonStyle(startingTimeBtnFrame, startingTimeBtnFrame, startingTimeBtnFrame, skin.getFont("title"));
        TextButton.TextButtonStyle speedRecudePowerUpStyle = new TextButton.TextButtonStyle(speedRecudeBtnFrame, speedRecudeBtnFrame, speedRecudeBtnFrame, skin.getFont("title"));


        shieldPowerUp = new MyStorePowerUpBtn("shieldPowerUp", userProfile.getInvinciblityLevel(), shieldPowerUpStyle, powerUpBuyBtnImage, powerUpBuyBtnImageClicked, upgradeOn, upgradeOff);
        speedReducePowerUp = new MyStorePowerUpBtn("speedReducePowerUp", userProfile.getSpeedReduceLevel(), speedRecudePowerUpStyle, powerUpBuyBtnImage, powerUpBuyBtnImageClicked, upgradeOn, upgradeOff);
        timeBonusPowerUp = new MyStorePowerUpBtn("timeBonusPowerUp", userProfile.getTimeBonusLevel(), bonusTimePowerUpStyle, powerUpBuyBtnImage, powerUpBuyBtnImageClicked, upgradeOn, upgradeOff);
        startingTimeBonus = new MyStorePowerUpBtn("startingTimeBonus", userProfile.getStartingTimeLevel(), startingTimeBonusStyle, powerUpBuyBtnImage, powerUpBuyBtnImageClicked, upgradeOn, upgradeOff);

        shieldPowerUp.registerListener(this);
        speedReducePowerUp.registerListener(this);
        timeBonusPowerUp.registerListener(this);
        startingTimeBonus.registerListener(this);

        powerUpgradeTable = new Table();
        powerUpgradeTable.add(shieldPowerUp).expand().fill().padBottom(20f);
        powerUpgradeTable.row();
        powerUpgradeTable.add(speedReducePowerUp).expand().fill().padBottom(20f);
        powerUpgradeTable.row();
        powerUpgradeTable.add(timeBonusPowerUp).expand().fill().padBottom(20f);
        powerUpgradeTable.row();
        powerUpgradeTable.add(startingTimeBonus).expand().fill();


        powerUpgradeTable.setOrigin(Align.center);
    }

    public void show() {
        super.show();
        final float width = stage.getWidth();
        final float height = stage.getHeight();


        countersTable.setBounds(0, 0, width * 0.8f, height * 0.08f);
        countersTable.setPosition(width / 2f, height * 0.8f, Align.center);

        toggleBtn.setBounds(0, 0, width * 0.8f, height * 0.08f);
        toggleBtn.setPosition(width / 2f, height * 0.7f, Align.center);

        powerUpgradeTable.setBounds(0, 0, width * 0.8f, height * 0.5f);
        powerUpgradeTable.setPosition(width / 2f, height * 0.4f, Align.center);


        stage.addActor(background);
        stage.addActor(storePanel);
        stage.addActor(countersTable);
        stage.addActor(toggleBtn);
        stage.addActor(powerUpgradeTable);


    }


    @Override
    public void render(float delta) {
        super.render(delta);


        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new MainMenuScreen(game, false));
            dispose();
        }
    }

    public void dispose() {
        super.dispose();
        shieldPowerUp.deleteListener(this);
        speedReducePowerUp.deleteListener(this);
        timeBonusPowerUp.deleteListener(this);
        startingTimeBonus.deleteListener(this);
    }

    @Override
    public boolean keyDown(int keycode) {
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


    @Override
    public void updateAfterBuy() {
        coinCounter.setText(userProfile.getMoney()+"");
        diamonCounter.setText(userProfile.getDiamonds()+"");
    }
}