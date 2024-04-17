package com.mygdx.game.obj.shape;

import static com.mygdx.game.Main.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    public static List<Entity> entities = new ArrayList<>();
    public static void upd() {
        for (Entity e : entities){
            e.update();
        }
    }
    public BodyDef bodyDef;
    public FixtureDef shapeDef = new FixtureDef();
    public WeldJointDef weldJointDef = new WeldJointDef();
    public RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
    private Body body;
    private String name;

    public Entity(float x, float y, BodyDef.BodyType BT) {
        bodyDef = new BodyDef();
        bodyDef.type = BT;
        bodyDef.position.set(new Vector2(x, y));
        body = world.createBody(bodyDef);
        body.setAngularDamping(2f);
        body.setLinearDamping(2f);
    }

    public void update() {}

    // Setter
    public void setName(String n) {name = n;}

    // Getter
    public Body getBody() {return body;}
    public String getName() {return name;}
}
