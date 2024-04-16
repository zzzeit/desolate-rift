package com.mygdx.game.obj.shape;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Rect extends Entity {
    private PolygonShape pShape;

    public Rect(float hx, float hy, BodyDef.BodyType BT, float density) {
        super(BT);
        pShape = new PolygonShape();
        pShape.setAsBox(hx, hy);
        getBody().createFixture(pShape, density);
        pShape.dispose();
    }

}
