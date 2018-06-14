package com.dorashush.game.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

import javax.swing.ButtonGroup;

/**
 * Created by Dor on 06/09/18.
 */

public class MyStorePowerUpBtn extends Button {
    private final Label label;
    private TextButton buyBtn;
    private ImageButton upgradeLevel1,upgradeLevel2,upgradeLevel3,upgradeLevel4,upgradeLevel5;
    private ImageButton[] arrayOfLevels;
    private int myLevel;
    private int cost;
    private UserProfile userProfile;
    private ButtonGroup upgradeGroup;
    private TextButton.TextButtonStyle style;
    private BuyListener mBuyListener;
    private String myName;

    public MyStorePowerUpBtn(String text,int level, Skin skin,Drawable buyBtnImage,Drawable buyBtnImageClicked,Drawable yesUpgradeImage,Drawable noUpgradeImage) {
        this(text,level,skin.get(TextButton.TextButtonStyle.class),buyBtnImage,buyBtnImageClicked,yesUpgradeImage,noUpgradeImage);
        setSkin(skin);
    }

    public MyStorePowerUpBtn(String text,int level, Skin skin, String styleName,Drawable buyBtnImage,Drawable buyBtnImageClicked,Drawable yesUpgradeImage,Drawable noUpgradeImage) {
        this(text,level,skin.get(styleName, TextButton.TextButtonStyle.class), buyBtnImage,buyBtnImageClicked,yesUpgradeImage,noUpgradeImage);
        setSkin(skin);
    }

    public MyStorePowerUpBtn(String text, final int level, TextButton.TextButtonStyle style, Drawable buyBtnImage, Drawable buyBtnImageClicked, Drawable yesUpgradeImage, Drawable noUpgradeImage) {
        super();
        userProfile = UserProfile.getInstance();
        setStyle(style);
        this.style = style;
        myLevel = level;
        cost=1000;
        myName = text;
        TextButton.TextButtonStyle buyBtnStyle = new TextButton.TextButtonStyle(buyBtnImage,buyBtnImageClicked,buyBtnImage,style.font);

        buyBtn = new TextButton((cost*(myLevel+1)+""),buyBtnStyle);

        buyBtn.getLabel().setAlignment(Align.center);
        arrayOfLevels = new ImageButton[5];

        for(int i = 0;i<arrayOfLevels.length;i++){
            arrayOfLevels[i] =  new ImageButton(noUpgradeImage,yesUpgradeImage,yesUpgradeImage);
            arrayOfLevels[i].setTouchable(Touchable.disabled);
        }
/*
        upgradeLevel1 = new ImageButton(noUpgradeImage,yesUpgradeImage,yesUpgradeImage);
        upgradeLevel2 = new ImageButton(noUpgradeImage,yesUpgradeImage,yesUpgradeImage);
        upgradeLevel3 = new ImageButton(noUpgradeImage,yesUpgradeImage,yesUpgradeImage);
        upgradeLevel4 = new ImageButton(noUpgradeImage,yesUpgradeImage,yesUpgradeImage);
        upgradeLevel5 = new ImageButton(noUpgradeImage,yesUpgradeImage,yesUpgradeImage);

        upgradeLevel1.setTouchable(Touchable.disabled);
        upgradeLevel2.setTouchable(Touchable.disabled);
        upgradeLevel3.setTouchable(Touchable.disabled);
        upgradeLevel4.setTouchable(Touchable.disabled);
        upgradeLevel5.setTouchable(Touchable.disabled);
*/
        adjustLevels(myLevel);


        label = new Label(text, new Label.LabelStyle(style.font, style.fontColor));
        label.setAlignment(Align.center);
/*
        if((style.font.toString()).compareTo("font-title-export")==0) {
            add(label).center().fill().width(50f).padLeft(40f).padTop(15f).height(getHeight() / 2);
        }
        else{
            add(label).center().fill().width(50f).padLeft(5f).padTop(3f).height(getHeight() / 2);

        }
*/

            add(arrayOfLevels[0]).bottom().padBottom(50).padLeft(300f);
            add(arrayOfLevels[1]).bottom().padBottom(50).padLeft(20f);
            add(arrayOfLevels[2]).bottom().padBottom(50).padLeft(20f);
            add(arrayOfLevels[3]).bottom().padBottom(50).padLeft(20f);
            add(arrayOfLevels[4]).bottom().padBottom(50).padLeft(20f);

            add(buyBtn).expand().right().bottom().padBottom(50f).padRight(30f).width(250f).height(90f);

        setSize(getPrefWidth(), getPrefHeight());


        buyBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(myLevel<5) {
                    if(cost*(myLevel+1)<= userProfile.getMoney()) {
                        userProfile.setMoney(userProfile.getMoney()-(cost*(myLevel+1)));
                        myLevel += 1;
                        adjustLevels(myLevel);
                        adjustPrice();
                        updateGame();
                        if(mBuyListener!=null)
                            mBuyListener.updateAfterBuy();
                    }
                    else{
                        //todo implemnt no enough money window or sound
                    }
                }
            }
        });
    }

    public void setStyle (ButtonStyle style) {
        if (style == null) throw new NullPointerException("style cannot be null");
        if (!(style instanceof TextButton.TextButtonStyle)) throw new IllegalArgumentException("style must be a TextButtonStyle.");
        super.setStyle(style);
        this.style = (TextButton.TextButtonStyle)style;
        if (label != null) {
            TextButton.TextButtonStyle textButtonStyle = (TextButton.TextButtonStyle)style;
            Label.LabelStyle labelStyle = label.getStyle();
            labelStyle.font = textButtonStyle.font;
            labelStyle.fontColor = textButtonStyle.fontColor;
            label.setStyle(labelStyle);
        }
    }

    public TextButton.TextButtonStyle getStyle () {
        return style;
    }

    public void draw (Batch batch, float parentAlpha) {
        Color fontColor;
        if (isDisabled() && style.disabledFontColor != null)
            fontColor = style.disabledFontColor;
        else if (isPressed() && style.downFontColor != null)
            fontColor = style.downFontColor;
        else if (isChecked() && style.checkedFontColor != null)
            fontColor = (isOver() && style.checkedOverFontColor != null) ? style.checkedOverFontColor : style.checkedFontColor;
        else if (isOver() && style.overFontColor != null)
            fontColor = style.overFontColor;
        else
            fontColor = style.fontColor;
        if (fontColor != null) label.getStyle().fontColor = fontColor;
        super.draw(batch, parentAlpha);
    }

    public Label getLabel () {
        return label;
    }



    public Cell<Label> getLabelCell () {
        return getCell(label);
    }


    public void setText (String text) {
        label.setText(text);
    }

    public CharSequence getText () {
        return label.getText();
    }

    /** The style for a text button, see {@link TextButton}.
     * @author Nathan Sweet */
    static public class TextButtonStyle extends ButtonStyle {
        public BitmapFont font;
        /** Optional. */
        public Color fontColor, downFontColor, overFontColor, checkedFontColor, checkedOverFontColor, disabledFontColor;

        public TextButtonStyle () {
        }

        public TextButtonStyle (Drawable up, Drawable down, Drawable checked, BitmapFont font) {
            super(up, down, checked);
            this.font = font;
        }

        public TextButtonStyle (TextButton.TextButtonStyle style) {
            super(style);
            this.font = style.font;
            if (style.fontColor != null) this.fontColor = new Color(style.fontColor);
            if (style.downFontColor != null) this.downFontColor = new Color(style.downFontColor);
            if (style.overFontColor != null) this.overFontColor = new Color(style.overFontColor);
            if (style.checkedFontColor != null) this.checkedFontColor = new Color(style.checkedFontColor);
            if (style.checkedOverFontColor != null) this.checkedOverFontColor = new Color(style.checkedOverFontColor);
            if (style.disabledFontColor != null) this.disabledFontColor = new Color(style.disabledFontColor);
        }
    }

    public void adjustLevels(int level){
        for(int i = 0;i<level;i++ ){
        arrayOfLevels[i].setChecked(true);
        }
    }

    public void adjustPrice(){
        buyBtn.setText((cost*(myLevel+1)+""));
    }


    public interface BuyListener {
        void updateAfterBuy();
    }

    public void registerListener(BuyListener listener) {
        mBuyListener = listener;
    }

    public void deleteListener(BuyListener listener) {
        mBuyListener = null;
    }
    public void updateGame(){
        if(myName.compareTo("shieldPowerUp")==0){
            userProfile.setInvinciblityLevel(myLevel);
        }
        else if(myName.compareTo("speedReducePowerUp")==0){
            userProfile.setSpeedReduceLevel(myLevel);
        }

        else if(myName.compareTo("timeBonusPowerUp")==0){
            userProfile.setTimeBonusLevel(myLevel);
        }

        else if(myName.compareTo("startingTimeBonus")==0){
            userProfile.setStartingTimeLevel(myLevel);
        }
    }
}
