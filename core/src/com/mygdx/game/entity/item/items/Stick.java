package com.mygdx.game.entity.item.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.entity.item.ItemEntity;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
import static com.mygdx.game.Main.*;
import static com.mygdx.game.util.IKeycodes.LEFTCLICK;
import static com.mygdx.game.util.MyInputProcessor.clickEvent;

public class Stick extends ItemEntity {
    private Body body;

    public Stick(Vector2 pos) {
        fixtureDef.shape = new CircleShape();
        fixtureDef.shape.setRadius(0.2f);
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.filter.categoryBits = 0x0003;
        fixtureDef.filter.maskBits = 0x0006 & ~0x0003;

        bodyDef.type = DynamicBody;
        bodyDef.linearDamping = 2f;
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(pos, 0);
        fixtureDef.shape.dispose();
        setSprite(itemAtlas.createSprite("stick"));
        getSprite().setScale(1/66f);
    }

    @Override
    public void render(int layer) {
        if (layer == 0) {
            getSprite().setPosition(body.getPosition().x - getSprite().getWidth() / 2, body.getPosition().y - getSprite().getHeight() / 2);
            getSprite().draw(spriteBatch);
        }

    }

    @Override
    public void update() {
        super.update();
        if (clickEvent.contains(LEFTCLICK)) {
            body.applyLinearImpulse(new Vector2(.01f, .01f), body.getPosition(), true);
        }
    }

    @Override
    public Vector2 getPosition() {
        return null;
    }
}
