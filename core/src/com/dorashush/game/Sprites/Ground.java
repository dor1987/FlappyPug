package com.dorashush.game.Sprites;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

/**
 * Created by Dor on 03/27/18.
 */

public class Ground extends Sprite {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private Body b2body;
    private BodyUserData bodyUserData;
    private AssetManager manager;
    public World world;
    public float offSet;

    public Ground(PlayScreen screen,float offSet){
        this.world = screen.getWorld();
        this.manager=  screen.getManager();
        this.offSet = offSet;
        setBounds(getX(), getY(), 480 /FlappyPug.PPM, 10/FlappyPug.PPM );

        defineGround();
        velocity = new Vector2(0,0);

        bodyUserData = new BodyUserData();
        bodyUserData.collisionType = BodyUserData.CollisionType.GROUND;
        b2body.setUserData(bodyUserData);


    }


    public void update(float dt){
        velocity.add(SPEED_MODIFIER,0);
        b2body.setLinearVelocity(velocity);

    }


    public void defineGround(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(offSet,getHeight()); //temp need to think of better way
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(480/FlappyPug.PPM,10/FlappyPug.PPM);

        fdef.shape = poly;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);

    }

    public float getPoisition(){
        return b2body.getPosition().x;
    }
    public void setPos(float x){
        b2body.setTransform(new Vector2(x,getHeight()),b2body.getAngle());
    }

}
