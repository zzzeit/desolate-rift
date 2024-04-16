package com.mygdx.game.obj;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.obj.shape.Rect;

public class Box extends Rect {
    public Box(float hx, float hy, BodyDef.BodyType BT, float density) {
        super(hx, hy, BT, density);
    }
}
