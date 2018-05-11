package com.dorashush.game.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Dor on 05/11/18.
 */

public class MyTextButton  extends Button {
    private final Label label,label2,label3;
    private TextButton.TextButtonStyle style;

    public MyTextButton (String text,String text2,String text3, Skin skin) {
        this(text,text2,text3, skin.get(TextButton.TextButtonStyle.class));
        setSkin(skin);
    }

    public MyTextButton (String text,String text2,String text3, Skin skin, String styleName) {
        this(text,text2,text3, skin.get(styleName, TextButton.TextButtonStyle.class));
        setSkin(skin);
    }

    public MyTextButton (String text,String text2,String text3, TextButton.TextButtonStyle style) {
        super();
        setStyle(style);
        this.style = style;
        label = new Label(text, new Label.LabelStyle(style.font, style.fontColor));
        label2 = new Label(text2, new Label.LabelStyle(style.font, style.fontColor));
        label3 = new Label(text3, new Label.LabelStyle(style.font, style.fontColor));

        label.setAlignment(Align.left);
        label2.setAlignment(Align.center);
        label3.setAlignment(Align.right);

        add(label).left().padRight(5f).fill().width(20f);
        add(label2).center().fill().padRight(20f).width(50f);
        add(label3).center().fill().width(50f);

        setSize(getPrefWidth(), getPrefHeight());
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
        if (label2 != null) {
            TextButton.TextButtonStyle textButtonStyle = (TextButton.TextButtonStyle)style;
            Label.LabelStyle labelStyle = label2.getStyle();
            labelStyle.font = textButtonStyle.font;
            labelStyle.fontColor = textButtonStyle.fontColor;
            label2.setStyle(labelStyle);
        }
        if (label3 != null) {
            TextButton.TextButtonStyle textButtonStyle = (TextButton.TextButtonStyle)style;
            Label.LabelStyle labelStyle = label3.getStyle();
            labelStyle.font = textButtonStyle.font;
            labelStyle.fontColor = textButtonStyle.fontColor;
            label3.setStyle(labelStyle);
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

    public Label getLabel2 () {
        return label2;
    }

    public Label getLabel3 () {
        return label3;
    }

    public Cell<Label> getLabelCell () {
        return getCell(label);
    }

    public Cell<Label> getLabelCell2 () {
        return getCell(label2);
    }

    public Cell<Label> getLabelCell3 () {
        return getCell(label3);
    }

    public void setText (String text) {
        label.setText(text);
    }
    public void setText2 (String text) {
        label2.setText(text);
    }
    public void setText3 (String text) {
        label3.setText(text);
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
}
