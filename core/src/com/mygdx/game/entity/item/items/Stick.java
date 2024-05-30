package com.mygdx.game.entity.item.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.entity.item.ItemEntity;
import com.mygdx.game.entity.mob.hostile.Human;
import com.mygdx.game.entity.mob.player.PHuman;
import com.mygdx.game.entity.mob.player.Player;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
import static com.mygdx.game.Main.*;
import static com.mygdx.game.entity.mob.player.Player.players;
import static com.mygdx.game.util.IKeycodes.LEFTCLICK;
import static com.mygdx.game.util.MyInputProcessor.clickEvent;

public class Stick extends ItemEntity {
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

        fixtureDef.shape.setRadius(5f);
        fixtureDef.density = 0f;
        fixtureDef.filter.categoryBits = 0;
        fixtureDef.filter.maskBits = (short) ~0xFFFF;
        body.createFixture(fixtureDef);
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
        if (inCameraFrustum(getSprite())) {
            super.update();
            if (clickEvent.contains(LEFTCLICK)) {
                body.applyLinearImpulse(new Vector2(.01f, .01f), body.getPosition(), true);
            }

        }
    }

    @Override
    public Vector2 getPosition() {
        return null;
    }

    private float speed = 0.01f;
    public void setSpeed(float speed) {this.speed = speed;}
    @Override
    public void beCollected(Vector2 pos) {
        float angle = (float) Math.atan2(body.getPosition().y - pos.y, body.getPosition().x - pos.x);
        body.applyForceToCenter((float) (-speed * Math.cos(angle)), (float) (-speed * Math.sin(angle)), true);
        if (speed < .25f)
            speed += .005f;

    }

}
