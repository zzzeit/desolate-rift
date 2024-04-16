package com.mygdx.game.obj;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.obj.shape.Rect;

public class Box extends Rect {
    private static int num_of_boxes = 0;
    public static void instantiate(float x, float y, float width, float height, BodyDef.BodyType BT, float density) {
        num_of_boxes++;
        entities.add(new Box(x, y, width, height, BT, density));
    }
    public Box(float x, float y, float width, float height, BodyDef.BodyType BT, float density) {
        super(x, y, width, height, BT, density);
        entities.add(this);
    }
}
