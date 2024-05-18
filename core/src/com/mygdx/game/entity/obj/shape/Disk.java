package com.mygdx.game.entity.obj.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.mygdx.game.entity.obj.BlockEntity;

import static com.mygdx.game.Main.world;

public abstract class Disk extends BlockEntity {
    private CircleShape Shape;
    private Body body;
    public Disk(float x, float y, float r, BodyDef.BodyType BT, float density) {
        setBodyDef(new BodyDef());
        getBodyDef().type = BT;
        getBodyDef().position.set(new Vector2(x, y));
        body = world.createBody(getBodyDef());
        body.setAngularDamping(.5f);
        body.setLinearDamping(.5f);
        Shape = new CircleShape();
        Shape.setRadius(r);
        body.createFixture(Shape, density);
        Shape.dispose();
    }

    public Body getBody() {return body;}

}
