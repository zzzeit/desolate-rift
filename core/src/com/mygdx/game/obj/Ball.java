package com.mygdx.game.obj;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.obj.shape.Disk;

public class Ball extends Disk {
    private static int num_of_balls = 0;
    public static void ballInst(float x, float y, float r, BodyDef.BodyType BT, float density) {
        num_of_balls++;
        entities.add(new Ball(x, y, r, BT, density));
    }

    public Ball(float x, float y, float r, BodyDef.BodyType BT, float density) {
        super(x, y, r, BT, density);
        getBody().getFixtureList().first().setRestitution(.9f);
        getBody().getFixtureList().first().setFriction(.5f);
    }
}
