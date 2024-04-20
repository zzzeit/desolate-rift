package com.mygdx.game.entity.obj.shape;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.mygdx.game.entity.obj.BlockEntity;

public abstract class Disk extends BlockEntity {
    private CircleShape Shape;
    public Disk(float x, float y, float r, BodyDef.BodyType BT, float density) {
        super(x, y, BT);
        Shape = new CircleShape();
        Shape.setRadius(r);
        getBody().createFixture(Shape, density);
        Shape.dispose();
    }

}
