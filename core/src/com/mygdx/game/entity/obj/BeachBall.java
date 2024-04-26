package com.mygdx.game.entity.obj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.Main;
import com.mygdx.game.entity.obj.shape.Disk;
import jdk.internal.org.jline.terminal.TerminalBuilder;

import static com.mygdx.game.Main.getCamera;
import static com.mygdx.game.Main.spriteBatch;

public class BeachBall extends Disk {
    private static int num_of_bBalls = 0;
    public static void instantiate(float x, float y, float r, BodyDef.BodyType BT, float density) {
        num_of_bBalls++;
        entities.add(new BeachBall(x, y, r, BT, density, Integer.toString(num_of_bBalls).concat("_BBALL")));
    }

    public BeachBall(float x, float y, float r, BodyDef.BodyType BT, float density, String name) {
        super(x, y, r, BT, density);
        setName(name);
        getBody().getFixtureList().first().setRestitution(1f);
        getBody().getFixtureList().first().setFriction(.2f);
        setTexture(new Texture("beachball.png"));
        setSprite(new Sprite(getTexture()));
        getSprite().setScale((r * 2)/128f);
    }

    @Override
    public void render() {
        getSprite().setCenter(getBody().getPosition().x, getBody().getPosition().y);
        getSprite().setRotation((float) Math.toDegrees(getBody().getAngle()));
        getSprite().draw(spriteBatch);

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
