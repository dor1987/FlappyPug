package com.dorashush.game.Sprites;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

import static com.dorashush.game.FlappyPug.SPEED_MODIFIER;
import static com.dorashush.game.FlappyPug.SPEED_TIME_JUMP;
import static com.dorashush.game.Screens.PlayScreen.STARTING_SPEED;

/**
 * Created by Dor on 04/28/18.
 */

public class Background extends Sprite{
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private Body b2body;
    private TextureRegion backGround;
    private BodyUserData bodyUserData;
    private AssetManager manager;
    public World world;
    public float offSet,timer;

    public Background(PlayScreen screen, float offSet){
        this.world = screen.getWorld();
        this.manager=  screen.getManager();
        this.offSet = offSet;
        backGround = new TextureRegion(manager.get("images/backgroundfull.png",Texture.class));
        defineBackground();

        setBounds(getX(), getY(), 769 / FlappyPug.PPM, 400/FlappyPug.PPM );
        setRegion(backGround);


       // setPosition(b2body.getPosition().x , b2body.getPosition().y );
        setPosition(b2body.getPosition().x, b2body.getPosition().y);




        velocity = new Vector2(STARTING_SPEED,0);

        bodyUserData = new BodyUserData();
       // bodyUserData.collisionType = BodyUserData.CollisionType.ENEMY;
        b2body.setUserData(bodyUserData);
        timer = 0;
        b2body.setLinearVelocity(velocity);

    }


    public void update(float dt){
        timer+=dt;
       // setPosition(b2body.getPosition().x , b2body.getPosition().y);
        setPosition(b2body.getPosition().x, b2body.getPosition().y);
/*
        if(timer >= SPEED_TIME_JUMP) {
            velocity.add(SPEED_MODIFIER, 0);
            b2body.setLinearVelocity(velocity);
            timer=0;
        }
  */
        b2body.setLinearVelocity(velocity);

    }


    public void defineBackground(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(offSet,0); //temp need to think of better way

        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(768/FlappyPug.PPM,400/FlappyPug.PPM);

        //Bits Testing
        fdef.filter.categoryBits = FlappyPug.NOTHING_BIT;

        fdef.shape = poly;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);

    }

    public float getPoisition(){
        return b2body.getPosition().x;
    }
    public void setPos(float x){
        b2body.setTransform(new Vector2(x,0),b2body.getAngle());
    }
    public void setSpeed(float speedX){
        velocity.x = speedX;
    }
}
