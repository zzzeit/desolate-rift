package com.mygdx.game.entity.mob.player;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entity.mob.MobileEntity;

public abstract class Player extends MobileEntity {
    public Player(float x, float y, BodyDef.BodyType BT) {
        super(x, y, BT);
    }
}
