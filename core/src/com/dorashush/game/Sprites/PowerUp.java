package com.dorashush.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Screens.PlayScreen;
import com.dorashush.game.Tools.BodyUserData;

import java.util.Random;

/**
 * Created by Dor on 05/08/18.
 */

public abstract class PowerUp extends Sprite{
    protected World world;
    protected Vector2 velocity,velocityBeforeCatch,velocityAfterCatch;
    protected Body b2body;
    protected BodyUserData bodyUserData;
    protected AssetManager manager;
    protected TextureRegion powerUpSymbol;
    public float powerUpAngle;
    public float powerUpVelocity;
    protected char value;
    protected float stateTime;
    protected boolean coughtByPlayer;
    public boolean removed;

    public PowerUp(PlayScreen screen){
        this.world = screen.getWorld();
        this.manager = screen.getManager();

        stateTime = 0;
        definePowerUp();
        velocityBeforeCatch = new Vector2(-0.8f,getPlustOrMinus()*0.3f); //starting Speed
        velocityAfterCatch = new Vector2(0,5); //starting Speed

        coughtByPlayer = false;
        removed = false;
        //Testing for collision
        bodyUserData = new BodyUserData();
        bodyUserData.collisionType = BodyUserData.CollisionType.POWER_UP;
        b2body.setUserData(bodyUserData);

        ///////////////////////////////////////
        setBounds(0,0,40/ FlappyPug.PPM,40/FlappyPug.PPM);

        //setPosition(b2body.getPosition().x - getWidth()/2,b2body.getPosition().y - getHeight());
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);


    }

    public abstract void update(float dt);
    protected abstract void definePowerUp();
    public abstract void setToRemove();

    public void reverseVelocity(boolean x,boolean y){
        Gdx.app.log("Inside Reverse","inside Revreser");

        if(x) {
            //ballAngle*=-0.5;
            velocity.x = -velocity.x;
        }
        if(y) {
            //ballAngle*=-0.5;
            velocity.y = -velocity.y;
        }
    }

    public char getPowerUpValue() {
        return value;
    }

    public void onPlayerCaught() {
        coughtByPlayer = true;
        velocity = velocityAfterCatch;
    }

    public float getPlustOrMinus() {
        Random random = new Random();
        int result = random.nextInt(3); //get number between 1-2
        if(result>=2)
            return -1;
        else
            return 1;
    }
    
    public void setToAppear() {
        b2body.setTransform(FlappyPug.WIDTH/2/FlappyPug.PPM,(FlappyPug.HEIGHT/4-getHeight()/2)/FlappyPug.PPM,b2body.getAngle());
        removed =false;
        velocity = velocityBeforeCatch;
        setScale(1f);
        coughtByPlayer = false;
    }

}


