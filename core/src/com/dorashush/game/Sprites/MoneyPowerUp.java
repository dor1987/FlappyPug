package com.dorashush.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Screens.PlayScreen;

/**
 * Created by Dor on 05/18/18.
 */

public class MoneyPowerUp extends PowerUp {

    public MoneyPowerUp(PlayScreen screen) {
        super(screen);
        powerUpSymbol = new TextureRegion(manager.get("images/currencypowerup.png",Texture.class));
        value = 'm';

        setRegion(powerUpSymbol);

    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        if(!coughtByPlayer) {
            if (b2body.getPosition().y >= FlappyPug.HEIGHT / 2 / FlappyPug.PPM - 30 / FlappyPug.PPM
                    || b2body.getPosition().y <= 20 / FlappyPug.PPM)
                reverseVelocity(false, true);
        }

        else if(coughtByPlayer){
            setScale(0.8f);

            if(b2body.getPosition().y >= FlappyPug.HEIGHT / 2 / FlappyPug.PPM - 20 / FlappyPug.PPM) {
                // world.destroyBody(b2body);
                removed = true;
            }
        }

        b2body.setLinearVelocity(velocity);


    }

    @Override
    protected void definePowerUp() {
        BodyDef bdef = new BodyDef();
        // bdef.position.set(240/ FlappyPug.PPM,500/FlappyPug.PPM);//need 2 change to randomly generate at the center
        //bdef.position.set(FlappyPug.WIDTH/2/FlappyPug.PPM,(FlappyPug.HEIGHT/4-getHeight()/2)/FlappyPug.PPM);

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
