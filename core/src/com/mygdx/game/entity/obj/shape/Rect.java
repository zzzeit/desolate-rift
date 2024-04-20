package com.mygdx.game.entity.obj.shape;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.entity.obj.BlockEntity;

public abstract class Rect extends BlockEntity {
    private PolygonShape Shape;

    public Rect(float x, float y, float width, float height, BodyDef.BodyType BT, float density) {
        super(x, y, BT);
        Shape = new PolygonShape();
        Shape.setAsBox(width/2, height/2);
        getBody().createFixture(Shape, density);
        Shape.dispose();
    }

}
