package com.dorashush.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Sprites.Dog;
import com.dorashush.game.Sprites.Enemy;
import com.dorashush.game.Sprites.PowerUp;

import static com.dorashush.game.Screens.PlayScreen.SPEED_BOOST;

/**
 * Created by Dor on 03/27/18.
 */

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int cDef = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        switch (cDef){
            case FlappyPug.DOG_BIT | FlappyPug.ENEMY_BIT:
                if(fixtureA.getFilterData().categoryBits == FlappyPug.DOG_BIT)
                    ((Dog) fixtureA.getUserData()).die();
                else
                    ((Dog) fixtureB.getUserData()).die();
                break;

            case FlappyPug.DOG_BIT | FlappyPug.BORDERS_BIT:
                if(fixtureA.getFilterData().categoryBits == FlappyPug.DOG_BIT)
                    ((Dog) fixtureA.getUserData()).die();
                else
                    ((Dog) fixtureB.getUserData()).die();
                break;

            case FlappyPug.DOG_BIT | FlappyPug.POWER_UP_BIT:
                char tempChar;
                if(fixtureA.getFilterData().categoryBits == FlappyPug.POWER_UP_BIT) {
                    tempChar = ((PowerUp) fixtureA.getUserData()).getPowerUpValue();
                    ((PowerUp) fixtureA.getUserData()).onPlayerCaught();
                    ((Dog) fixtureB.getUserData()).onLetterCought(tempChar);

                }
                else {
                    tempChar = ((PowerUp) fixtureB.getUserData()).getPowerUpValue();
                    ((PowerUp) fixtureB.getUserData()).onPlayerCaught();
                    ((Dog) fixtureA.getUserData()).onLetterCought(tempChar);
                }
                break;


        }


    }
    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


    private boolean fixtureIsCollisionType(Fixture fixture,  BodyUserData.CollisionType collisionType) {
        Body body = fixture.getBody();
        if (body != null) {
            BodyUserData bodyUserData = (BodyUserData)body.getUserData();
            if (bodyUserData != null) {
                return (bodyUserData.collisionType == collisionType);
            }
        }
        return false;
    }
}
