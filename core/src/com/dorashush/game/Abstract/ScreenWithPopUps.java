package com.dorashush.game.Abstract;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by Dor on 04/27/18.
 */

public abstract class ScreenWithPopUps implements Screen {

    public abstract void nameWindowControl(boolean openWindow);
    public abstract AssetManager getManager();

}

