package com.dorashush.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Screens.PlayScreen;
import com.dorashush.game.Tools.BodyUserData;

/**
 * Created by Dor on 03/27/18.
 */

public class Sky extends Sprite {
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private Body b2body;
    private BodyUserData bodyUserData;
    private AssetManager manager;
    public World world;
    public float offSet;

    public Sky(PlayScreen screen,float offSet){
        this.world = screen.getWorld();
        this.manager=  screen.getManager();
        this.offSet = offSet;
        setBounds(getX(), getY(), 480 , 10 );

        defineSky();
        velocity = new Vector2(0,0);

        bodyUserData = new BodyUserData();
        bodyUserData.collisionType = BodyUserData.CollisionType.DOG;
        b2body.setUserData(bodyUserData);


    }


    public void update(float dt){
        velocity.add(-1f,0);
        b2body.setLinearVelocity(velocity);
    }


    public void defineSky(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(offSet, FlappyPug.HEIGHT/2-getHeight()); //temp need to think of better way
        Gdx.app.log("HERE",""+getHeight());
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(480,10);

        fdef.shape = poly;
        b2body.createFixture(fdef).setUserData(this);
    }

    public float getPoisition(){
        return b2body.getPosition().x;
    }
    public void setPos(float x){
        b2body.setTransform(new Vector2(x,FlappyPug.HEIGHT/2-getHeight()),b2body.getAngle());
    }

}