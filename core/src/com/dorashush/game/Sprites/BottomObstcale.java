package com.dorashush.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Screens.PlayScreen;
import com.dorashush.game.Tools.BodyUserData;

import java.util.Random;

import static com.dorashush.game.FlappyPug.SPEED_MODIFIER;

/**
 * Created by Dor on 03/28/18.
 */

public class BottomObstcale extends Sprite {
    public static final int TUBE_WIDTH = 40;
    private static final int TUBE_GAP = 250;

    private Vector2 velocity;
    //private Rectangle bounds;
    private Body b2body;
    private BodyUserData bodyUserData;
    private AssetManager manager;
    public World world;
    public float x,topObstcaleY;
    private Random rand;

    public BottomObstcale(PlayScreen screen, float x , float topObstcaleY){
        this.world = screen.getWorld();
        this.manager=  screen.getManager();
        this.x = x;
        this.topObstcaleY = topObstcaleY;
        setBounds(getX(), getY(), TUBE_WIDTH /FlappyPug.PPM, 150/FlappyPug.PPM );
        rand = new Random();
        defineObstcale();
        velocity = new Vector2(0,0);

        bodyUserData = new BodyUserData();
        bodyUserData.collisionType = BodyUserData.CollisionType.ENEMY;
        b2body.setUserData(bodyUserData);


    }


    public void update(float dt){
        velocity.add(SPEED_MODIFIER,0);
        b2body.setLinearVelocity(velocity);

    }


    public void defineObstcale(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x/ FlappyPug.PPM,topObstcaleY -(TUBE_GAP + 150)/FlappyPug.PPM); //temp need to think of better way
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(TUBE_WIDTH/FlappyPug.PPM,150/FlappyPug.PPM);

        fdef.shape = poly;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
    }

    public float getPoisitionX(){
        return b2body.getPosition().x;
    }

    public float getPoisitionY(){
        return b2body.getPosition().y;
    }

    public void setPos(float x){
        b2body.setTransform(new Vector2(x,getHeight()),b2body.getAngle());
    }

    public void reposition(float x,float y){
        b2body.setTransform(new Vector2(x/FlappyPug.PPM,y - (TUBE_GAP + 150)/FlappyPug.PPM),b2body.getAngle());

    }

}