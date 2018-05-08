package com.dorashush.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import com.badlogic.gdx.utils.Array;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Scenes.Hud;
import com.dorashush.game.Screens.PlayScreen;
import com.dorashush.game.Tools.BodyUserData;

import javax.swing.plaf.synth.Region;

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
    private boolean dogIsDead,flyAnimation,gotP,gotU,gotG;
    private  float stateTimer,invisibaleTimer;
    private TextureRegion dogFall,dogFly;

    //animation
    private Animation flyAnim,fallAnim,deathAnim,startingAnim;
    private Array<TextureRegion> frames;
    private float alpha;

    public Dog(PlayScreen screen) {
        this.world = screen.getWorld();
        this.manager = screen.getManager();


        //animations
        frames = new Array<TextureRegion>();
            frames.add(new TextureRegion(screen.getAtlas().findRegion("fire1"), 0, 0, 700, 557));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("fire2"), 0, 0, 700, 557));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("fire3"), 0, 0, 700, 557));

        flyAnim = new Animation(0.3f, frames);

        frames.clear();


        frames.add(new TextureRegion(screen.getAtlas().findRegion("falling1"), 0, 0, 700, 557));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("falling2"), 0, 0, 700, 557));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("falling3"), 0, 0, 700, 557));

        fallAnim = new Animation(0.3f, frames);

        frames.clear();


        frames.add(new TextureRegion(screen.getAtlas().findRegion("death"), 0, 0, 700, 557));

        deathAnim = new Animation(0.2f, frames);

        frames.clear();


        //dogFly = new TextureRegion(new Texture("images/fireon.png"));
        //dogFall = new TextureRegion(new Texture("images/fireoff.png"));
        //dogFly = new TextureRegion(manager.get("images/fireon.png",Texture.class));
       // dogFall = new TextureRegion(manager.get("images/fireoff.png",Texture.class));

        defineDog();
        velocity = new Vector2(0,0);

        dogIsDead = false;
        flyAnimation = false;
        setBounds(0,0,78/FlappyPug.PPM,60/FlappyPug.PPM);
        TextureRegion tempRegion = (TextureRegion) fallAnim.getKeyFrame(stateTimer,true);
        if(!tempRegion.isFlipX())
            tempRegion.flip(true,false);
        setRegion(tempRegion);

       setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);


        stateTimer = 0;

        bodyUserData = new BodyUserData();
        bodyUserData.collisionType = BodyUserData.CollisionType.DOG;
        b2body.setUserData(bodyUserData);

        invisibaleTimer = 0;
        gotP = false;
        gotU = false;
        gotG = false;
        alpha =.0f;

    }


    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
        //setRegion(dogFall);
        if(checkIfAllLettersCought()){
            activateMegaPowerup();
        }

        if(invisibaleTimer>0){
            invisibaleTimer-=dt;
            if(invisibaleTimer<=3){
                alpha+=dt;
                this.setAlpha(+5f*(float)Math.sin(alpha) + .5f);
            }
        }
        else{
            invisable(false);
        }

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
        else if(b2body.getLinearVelocity().y>0) {
            return State.FLYING;
        }
        else {
            return State.FALLING;
        }
    }


    public TextureRegion getFrame(float dt){
        //get dog current state. ie. jumping, running, standing...
        currentState = getState();

        TextureRegion region = null;

        //depending on the state, get corresponding animation keyFrame.
        //ToDo enter Frame control according to state && remove null from region
        switch(currentState){
            case DEAD:
                //region =  dogFall;
                region = (TextureRegion) deathAnim.getKeyFrame(stateTimer,true);
                break;
            case FLYING:
               // region =  dogFly;
                region = (TextureRegion) flyAnim.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
               // region =  dogFall;
                region = (TextureRegion) fallAnim.getKeyFrame(stateTimer,true);
                break;

            default:
                region = (TextureRegion) fallAnim.getKeyFrame(stateTimer,true);
                break;
        }


        if(!region.isFlipX())
            region.flip(true,false);

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
        shape.setRadius(23/FlappyPug.PPM);

        //Bits Testing
        fdef.filter.categoryBits = FlappyPug.DOG_BIT;
        fdef.filter.maskBits = FlappyPug.ENEMY_BIT | FlappyPug.POWER_UP_BIT |FlappyPug.BORDERS_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void Fly(){
       // velocity.y = (float)2.5;
        velocity.y = (float)1.5;

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
    public void invisable(boolean isInvisable) {
        Filter filter = new Filter();
        filter.categoryBits = FlappyPug.DOG_BIT;

      if(isInvisable) {
          //Will be used as a powerup
          invisibaleTimer = 10;
          this.setAlpha(0.5f);
          filter.maskBits =  FlappyPug.BORDERS_BIT;
      }

      else{
          filter.maskBits = FlappyPug.POWER_UP_BIT | FlappyPug.BORDERS_BIT | FlappyPug.ENEMY_BIT;
            this.setAlpha(1f);
      }
            //doesnt have to be a list, but incase i'll make dog more than 1 body
            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }

    }

    public boolean isDead(){
        return dogIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void onLetterCought(char value){
        switch(value){
            case 'p':
                gotP = true;
                break;
            case 'u':
                gotU = true;
                break;
            case 'g':
                gotG = true;
                break;
        }
        Hud.onLetterChangedStatus(true,value);


    }
    public boolean checkIfAllLettersCought(){
        if(gotP&&gotU&&gotG)
            return true;
        else
            return false;
    }

    public void activateMegaPowerup(){
        invisable(true);
        gotP = false;
        gotU = false;
        gotG = false;
        Hud.hideAllLetters();
    }

}
