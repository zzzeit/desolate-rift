package com.mygdx.game.util;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

public class MyQueryCallback implements QueryCallback {
    private boolean bodyFound = false;
    @Override
    public boolean reportFixture(Fixture fixture) {
        bodyFound = true;
        return true;
    }
    public boolean isBodyFound() {return bodyFound;}
}
