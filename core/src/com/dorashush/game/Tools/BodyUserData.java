package com.dorashush.game.Tools;

/**
 * Created by Dor on 03/27/18.
 */

public class BodyUserData {
    public enum CollisionType {
        UNKNOWN,
        SKY,
        GROUND,
        POWER_UP,
        ENEMY,
        DOG
    }

    public CollisionType collisionType = CollisionType.UNKNOWN;
}
