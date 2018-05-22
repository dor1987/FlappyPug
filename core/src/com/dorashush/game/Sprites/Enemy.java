package com.dorashush.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.dorashush.game.Screens.PlayScreen;

/**
 * Created by Dor on 03/31/18.
 */

public abstract class Enemy extends Sprite{
    public  boolean removed;
    protected boolean setToRemove;
    public float tempVelocity;
    public abstract void setSpeed(float speedX);
    public abstract float getPoisitionY();
    public abstract void reposition(float x);
    public abstract float getPoisitionX();
    public abstract void setToRemove();
    public abstract void update(float dt);
    public abstract void speedReducePowerUpTaken();

    }
