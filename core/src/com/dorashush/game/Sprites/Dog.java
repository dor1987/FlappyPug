package com.dorashush.game.Sprites;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Screens.PlayScreen;
import com.dorashush.game.Tools.BodyUserData;

/**
 * Created by Dor on 03/27/18.
 */

public class Dog extends Sprite{
    public enum State { FLYING, FALLING, SPEEDING, DEAD };
    public State currentState;
    public State previousState;

    public World world;

    private static final float GRAVITY = (float)-0.18;
    private static final int MOVEMENT = 100;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private Body b2body;
    private BodyUserData bodyUserData;
    private AssetManager manager;
    private boolean dogIsDead,flyAnimation;
    private  float stateTimer;


    public Dog(PlayScreen screen){
        this.world = screen.getWorld();
        this.manager=  screen.getManager();

        defineDog();
        velocity = new Vector2(0,0);

        dogIsDead = false;
        flyAnimation = false;

        stateTimer = 0;

        bodyUserData = new BodyUserData();
        bodyUserData.collisionType = BodyUserData.CollisionType.DOG;
        b2body.setUserData(bodyUserData);


    }


    public void update(float dt){
        //setPosition(b2body.getPosition().x - getWidth()/2,b2body.getPosition().y - getHeight()/2);

        velocity.add(0,GRAVITY);
        b2body.setLinearVelocity(velocity);

        //Todo move this to Get Texturse when implmented
        currentState = getState();
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
    }

    public State getState(){
        if(dogIsDead)
            return State.DEAD;
        else if(flyAnimation)
            return State.FLYING;
        else
            return State.FALLING;
    }


    public TextureRegion getFrame(float dt){
        //get dog current state. ie. jumping, running, standing...
        currentState = getState();

        TextureRegion region = null;

        //depending on the state, get corresponding animation keyFrame.
        //ToDo enter Frame control according to state && remove null from region
        switch(currentState){
            case DEAD:
                break;
            case FLYING:

                break;
            case FALLING:
                break;

            default:

                break;
        }


        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }

    public void defineDog(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(FlappyPug.WIDTH/5/FlappyPug.PPM,(FlappyPug.HEIGHT/4-getHeight()/2)/FlappyPug.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20/FlappyPug.PPM);

        //Bits Testing
        fdef.filter.categoryBits = FlappyPug.DOG_BIT;
        fdef.filter.maskBits = FlappyPug.ENEMY_BIT | FlappyPug.POWER_UP_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void Fly(){
        velocity.y = (float)2.5;
    }

    public void die() {

        if (!isDead()) {

            dogIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = FlappyPug.NOTHING_BIT;


            //doesnt have to be a list, but incase i'll make dog more than 1 body
            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }

            velocity.y = (float)3;
            velocity.x = (float)-2;

            //b2body.applyLinearImpulse(new Vector2(-2, 2.5f), b2body.getWorldCenter(), true);
        }
    }

    public boolean isDead(){
        return dogIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }
}
