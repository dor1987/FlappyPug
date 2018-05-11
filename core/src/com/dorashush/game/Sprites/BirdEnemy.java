package com.dorashush.game.Sprites;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Screens.PlayScreen;
import com.dorashush.game.Tools.BodyUserData;

import java.util.Random;

import static com.dorashush.game.Screens.PlayScreen.STARTING_SPEED;

/**
 * Created by Dor on 05/10/18.
 */

public class BirdEnemy extends Enemy{

    private static final int FLUCTUATION = 300;
    private static final int LOWEST_START = 35;
    public static final int OBSTACLE_WIDTH = 70;
    public static final int OBSTACLE_HEIGHT = 50;

    private Vector2 velocity;
    private Body b2body;
    private BodyUserData bodyUserData;
    private AssetManager manager;
    public World world;
    private Random rand;
    public float x,stateTimer;
    private Animation flyAnim;
    private Array<TextureRegion> frames;


    public BirdEnemy(PlayScreen screen, float x) {
        this.world = screen.getWorld();
        this.manager=  screen.getManager();
        this.x = x;

        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("bird1"), 0, 0, 700, 476));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("bird2"), 0, 0, 700, 476));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("bird3"), 0, 0, 700, 476));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("bird4"), 0, 0, 700, 476));

        flyAnim = new Animation(0.3f, frames);

        frames.clear();

        defineBird();


        setBounds(0, 0, OBSTACLE_WIDTH*2/FlappyPug.PPM , OBSTACLE_HEIGHT*2/FlappyPug.PPM );
        TextureRegion tempRegion = (TextureRegion) flyAnim.getKeyFrame(stateTimer,true);
        setRegion(tempRegion);


        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        stateTimer = 0;
        velocity = new Vector2(STARTING_SPEED,0);

        bodyUserData = new BodyUserData();
        bodyUserData.collisionType = BodyUserData.CollisionType.ENEMY;
        b2body.setUserData(bodyUserData);
        b2body.setLinearVelocity(velocity);


        setToRemove = false;
        removed = false;

    }

    public void update(float dt){
        //timer+=dt;

        if(setToRemove && !removed){
            world.destroyBody(b2body);
            removed = true;
        }

        else if(!removed) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(getFrame(dt));

            /*
        if(timer >= SPEED_TIME_JUMP) {
            velocity.add(SPEED_MODIFIER, 0);
            b2body.setLinearVelocity(velocity);
            timer=0;
        }
  */
            b2body.setLinearVelocity(velocity);
            //currentState = getState();

            //stateTimer = currentState == previousState ? stateTimer + dt : 0;
            //previousState = currentState;
        }


    }
    public TextureRegion getFrame(float dt){
        TextureRegion region = null;
        region = (TextureRegion) flyAnim.getKeyFrame(stateTimer,true);

        return region;
        }
    @Override
    public void setSpeed(float speedX) {
        velocity.x = speedX;

    }
    public float getPoisitionX(){
        return b2body.getPosition().x;
    }
    @Override
    public float getPoisitionY() {
        return b2body.getPosition().y;
    }

    public void setToRemove(){
        this.setToRemove= true;
    }
    public void reposition(float x){
        b2body.setTransform(new Vector2(x/FlappyPug.PPM,(rand.nextInt(FLUCTUATION)  + LOWEST_START)/FlappyPug.PPM),b2body.getAngle());

    }
    public void defineBird(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x/ FlappyPug.PPM,(rand.nextInt(FLUCTUATION)  + LOWEST_START)/FlappyPug.PPM); //temp need to think of better way
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(OBSTACLE_WIDTH/FlappyPug.PPM,OBSTACLE_HEIGHT/FlappyPug.PPM);

        //Bits Testing
        fdef.filter.categoryBits = FlappyPug.ENEMY_BIT;
        fdef.filter.maskBits = FlappyPug.DOG_BIT;

        fdef.shape = poly;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
    }
}
