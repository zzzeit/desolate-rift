package com.mygdx.game.entity.obj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entity.obj.shape.Rect;

import static com.mygdx.game.Main.spriteBatch;

public class Box extends Rect {
    private static int num_of_boxes = 0;
    public static Box instantiate(float x, float y, float width, float height, BodyDef.BodyType BT, float density) {
        num_of_boxes++;
        entities.add(new Box(x, y, width, height, BT, density, Integer.toString(num_of_boxes).concat("_BOX")));
        return getBlockInstance(Box.class, num_of_boxes);
    }
    public Box(float x, float y, float width, float height, BodyDef.BodyType BT, float density, String name) {
        super(x, y, width, height, BT, density);
        setName(name);
        setTexture(new Texture("box.png"));
        setSprite(new Sprite(getTexture()));
        getSprite().setScale(width/33f);
    }

    @Override
    public void render() {
//        getSprite().setCenter(getBody().getPosition().x, getBody().getPosition().y);
//        getSprite().rotate((float) (-1 * Math.toDegrees(getDeltaAngle())));
//        getSprite().draw(spriteBatch);
    }

    @Override
    public void shapeRender() {

    }

    @Override
    public void update() {
        setAngle(getBody().getAngle());
        calcDeltaAngle();
    }

    @Override
    public Vector2 getPosition() {
        return null;
    }
}
