package com.mygdx.game.entity.obj.blocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entity.obj.shape.Disk;

public class Ball extends Disk {
    private static int num_of_balls = 0;
    public static void instantiate(float x, float y, float r, BodyDef.BodyType BT, float density) {
        num_of_balls++;
        entities.add(new Ball(x, y, r, BT, density, Integer.toString(num_of_balls).concat("_BALL")));
    }

    public Ball(float x, float y, float r, BodyDef.BodyType BT, float density, String name) {
        super(x, y, r, BT, density);
        setName(name);
        getBody().getFixtureList().first().setRestitution(4f);
        getBody().getFixtureList().first().setFriction(4f);
    }

    @Override
    public void render(int layer) {

    }

    @Override
    public void update() {

    }

    @Override
    public Vector2 getPosition() {
        return null;
    }
}
