package com.dorashush.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Screens.PlayScreen;

import sun.font.TextLabel;

/**
 * Created by Dor on 05/10/18.
 */

public class TimeAddPowerUp extends PowerUp {
    private TextureRegion TimeAddImage;

    public TimeAddPowerUp(PlayScreen screen) {
        super(screen);
        powerUpSymbol = new TextureRegion(manager.get("images/sandclock.png",Texture.class));
        TimeAddImage = new TextureRegion(manager.get("images/timeaddtion.png",Texture.class));

        value = 't';

        setRegion(powerUpSymbol);

    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
/*
        if(!coughtByPlayer) {
            if (b2body.getPosition().y >= FlappyPug.HEIGHT / 2 / FlappyPug.PPM - 30 / FlappyPug.PPM
                    || b2body.getPosition().y <= 20 / FlappyPug.PPM)
                reverseVelocity(false, true);
        }
*/
         if(coughtByPlayer){
            //setRegion(TimeAddImage);
            setScale(0.8f);

            if(b2body.getPosition().y >= FlappyPug.HEIGHT / 2 / FlappyPug.PPM - 20 / FlappyPug.PPM) {
               // world.destroyBody(b2body);
                removed = true;
            }
        }
         else{
             if(gameCam.position.x - (gameCam.viewportWidth / 2) > b2body.getPosition().x && !removed) {
                 Gdx.app.log("PowerUp","TA Removed");
                 setToRemove();
             }
         }
        b2body.setLinearVelocity(velocity);


    }

    @Override
    protected void definePowerUp() {
        BodyDef bdef = new BodyDef();
        // bdef.position.set(240/ FlappyPug.PPM,500/FlappyPug.PPM);//need 2 change to randomly generate at the center
      //  bdef.position.set(FlappyPug.WIDTH/2/FlappyPug.PPM,(FlappyPug.HEIGHT/4-getHeight()/2)/FlappyPug.PPM);

        //bdef.position.set(getX(),getY());

        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 /FlappyPug.PPM);


        fdef.filter.categoryBits = FlappyPug.POWER_UP_BIT;
        fdef.filter.maskBits = FlappyPug.BORDERS_BIT | FlappyPug.DOG_BIT ;

        fdef.shape = shape;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void setToRemove() {
        removed = true;

    }


}
