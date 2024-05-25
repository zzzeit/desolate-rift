package com.mygdx.game.entity.obj.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.entity.obj.BlockEntity;

import static com.mygdx.game.Main.world;

public class Rect extends BlockEntity {
    private PolygonShape Shape;
    private Body body;
    public Rect(float x, float y, float width, float height, BodyDef.BodyType BT, float density) {
        bodyDef.type = BT;
        bodyDef.position.set(new Vector2(x, y));
        body = world.createBody(bodyDef);
        body.setAngularDamping(.5f);
        body.setLinearDamping(.5f);

        Shape = new PolygonShape();
        Shape.setAsBox(width/2, height/2);
        body.createFixture(Shape, density);
        Shape.dispose();

    }

    public Body getBody() {return body;}

    @Override
    public void render(int layer) {

    }

    @Override
    public Vector2 getPosition() {return body.getPosition();}
}
