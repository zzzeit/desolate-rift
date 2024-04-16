package com.mygdx.game.obj.shape;

import static com.mygdx.game.Main.*;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    public static List<Entity> entities = new ArrayList<>();
    private BodyDef bodyDef;
    private Body body;

    public Entity(BodyDef.BodyType BT) {
        bodyDef = new BodyDef();
        bodyDef.type = BT;
        body = world.createBody(bodyDef);
    }

    // Setter

    // Getter
    public Body getBody() {return body;}
}
