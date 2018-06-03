package com.dorashush.game.Sprites;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Screens.PlayScreen;
import com.dorashush.game.Tools.BodyUserData;

import static com.dorashush.game.FlappyPug.SPEED_MODIFIER;
import static com.dorashush.game.FlappyPug.SPEED_TIME_JUMP;
import static com.dorashush.game.Screens.PlayScreen.STARTING_SPEED;

/**
 * Created by Dor on 03/27/18.
 */

public class Ground extends Enemy  {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;
    private Vector2 position;
    private Vector2 velocity;
    private TextureRegion ground;
    private Rectangle bounds;
    private Body b2body;
    private BodyUserData bodyUserData;
    private AssetManager manager;
    public World world;
    public float offSet,timer,gameTimer;
    private PlayScreen screen;

    public Ground(PlayScreen screen,float offSet){
        this.world = screen.getWorld();
        this.screen=screen;
        this.manager=  screen.getManager();
        this.offSet = offSet;
       // ground = new TextureRegion(manager.get("images/ground.png",Texture.class));

        //setBounds(getX(), getY(), 480 /FlappyPug.PPM, 10/FlappyPug.PPM );
        setBounds(getX(), getY(), 769 / FlappyPug.PPM, 15/FlappyPug.PPM );

        defineGround();
      //  setRegion(ground);
        setPosition(b2body.getPosition().x, b2body.getPosition().y);

        //velocity = new Vector2(STARTING_SPEED,0);

        bodyUserData = new BodyUserData();
        bodyUserData.collisionType = BodyUserData.CollisionType.ENEMY;
        b2body.setUserData(bodyUserData);
        //timer =0;
        //b2body.setLinearVelocity(velocity);
    //    gameTimer=0;
    }


    public void update(float dt){
        /*
        timer+=dt;
        gameTimer+=dt;
        setPosition(b2body.getPosition().x, b2body.getPosition().y);
        */
/*
        if(timer >= SPEED_TIME_JUMP) {
            velocity.add(SPEED_MODIFIER, 0);
            b2body.setLinearVelocity(velocity);
            timer=0;
        }
*/
/*
        tempVelocity = screen.getGameSpeed();
        if(tempVelocity!=velocity.x) {
            velocity.x = screen.getGameSpeed();
            b2body.setLinearVelocity(velocity);
        }
*/
    //    velocity.x = screen.getGameSpeed();
     //   b2body.setLinearVelocity(velocity);

    }


    public void defineGround(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(offSet,0); //temp need to think of better way
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape poly = new PolygonShape();
        //poly.setAsBox(480/FlappyPug.PPM,10/FlappyPug.PPM);
        poly.setAsBox(768/FlappyPug.PPM,10/FlappyPug.PPM);

        //Bits Testing
        fdef.filter.categoryBits = FlappyPug.BORDERS_BIT;
        fdef.filter.maskBits = FlappyPug.POWER_UP_BIT | FlappyPug.DOG_BIT ;



        fdef.shape = poly;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);

    }

    public float getPoisition(){
        return b2body.getPosition().x;
    }
    public void setPos(float x){
        //b2body.setTransform(new Vector2(x,0),b2body.getAngle());
    }
    public void setSpeed(float speedX){
        //velocity.x = speedX;
    }

    @Override
    public float getPoisitionY() {
        return 0;
    }

    @Override
    public void reposition(float x) {

    }

    @Override
    public float getPoisitionX() {
        return 0;
    }

    @Override
    public void setToRemove() {

    }
    public void speedReducePowerUpTaken(){
        /*
        velocity.x*=0.8;
        b2body.setLinearVelocity(velocity);
        */
    }
}
