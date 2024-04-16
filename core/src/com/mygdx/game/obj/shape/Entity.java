package com.mygdx.game.obj.shape;

import static com.mygdx.game.Main.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    public static List<Entity> entities = new ArrayList<>();
    private BodyDef bodyDef;
    private Body body;

    public Entity(float x, float y, BodyDef.BodyType BT) {
        bodyDef = new BodyDef();
        bodyDef.type = BT;
        bodyDef.position.set(new Vector2(x, y));
        body = world.createBody(bodyDef);
    }

    // Setter

    // Getter
    public Body getBody() {return body;}
}
