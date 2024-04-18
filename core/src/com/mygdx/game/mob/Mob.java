package com.mygdx.game.mob;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.mygdx.game.obj.shape.Entity;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.Main.world;
import static com.mygdx.game.util.Settings.angleBetweenPoints;

public abstract class Mob {
    public static List<Mob> mobs = new ArrayList<>();
    public static void upd() {
        for (Mob m : mobs){
            m.update();
        }
    }

    public BodyDef bodyDef;
    public FixtureDef shapeDef = new FixtureDef();
    public WeldJointDef weldJointDef = new WeldJointDef();
    public RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
    private Body body;
    private String name;

    public Mob(float x, float y, BodyDef.BodyType BT) {
        bodyDef = new BodyDef();
        bodyDef.type = BT;
        bodyDef.position.set(new Vector2(x, y));
        body = world.createBody(bodyDef);
    }

    // SETTER
    public void setName(String n) {name = n;}

    // GETTER
    public Body getBody() {return body;}
    public String getName() {return name;}

    public void update() {}
    public void weldBodies2(Body b1, Body b2, Boolean collide) {
        weldJointDef.bodyA = b1;
        weldJointDef.bodyB = b2;
        weldJointDef.collideConnected = collide;
        world.createJoint(weldJointDef);
    }

}
