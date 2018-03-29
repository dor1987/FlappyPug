package com.dorashush.game.Sprites;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Screens.PlayScreen;
import com.dorashush.game.Tools.BodyUserData;

/**
 * Created by Dor on 03/27/18.
 */

public class Dog extends Sprite{
    private static final float GRAVITY = (float)-0.18;
    private static final int MOVEMENT = 100;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private Body b2body;
    private BodyUserData bodyUserData;
    private AssetManager manager;
    public World world;

    public Dog(PlayScreen screen){
        this.world = screen.getWorld();
        this.manager=  screen.getManager();

        defineDog();
        velocity = new Vector2(0,0);

        bodyUserData = new BodyUserData();
        bodyUserData.collisionType = BodyUserData.CollisionType.DOG;
        b2body.setUserData(bodyUserData);


    }


    public void update(float dt){
        //setPosition(b2body.getPosition().x - getWidth()/2,b2body.getPosition().y - getHeight()/2);

        velocity.add(0,GRAVITY);
        b2body.setLinearVelocity(velocity);
        /*
        velocity.scl(dt);
        velocity.scl(1/dt);
    */
    }


    public void defineDog(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(FlappyPug.WIDTH/5/FlappyPug.PPM,(FlappyPug.HEIGHT/4-getHeight()/2)/FlappyPug.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20/FlappyPug.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void Fly(){
        velocity.y = (float)2.5;
    }

    public void Jump(){

    }
}
