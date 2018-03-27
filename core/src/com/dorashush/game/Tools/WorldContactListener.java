package com.dorashush.game.Tools;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by Dor on 03/27/18.
 */

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
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
