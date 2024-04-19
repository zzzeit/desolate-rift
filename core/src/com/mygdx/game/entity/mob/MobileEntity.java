package com.mygdx.game.entity.mob;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.IEntity;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.Main.world;

public abstract class MobileEntity extends Entity implements IEntity {
    public static List<MobileEntity> mobs = new ArrayList<>();
    public static void upd() {
        for (MobileEntity m : mobs){
            m.update();
            m.render();
        }
    }

    public MobileEntity(float x, float y, BodyDef.BodyType BT) {
        bodyDef = new BodyDef();
        bodyDef.type = BT;
        bodyDef.position.set(new Vector2(x, y));
        setBody(world.createBody(bodyDef));
    }


    public void weldBodies2(Body b1, Body b2, Boolean collide) {
        weldJointDef.bodyA = b1;
        weldJointDef.bodyB = b2;
        weldJointDef.collideConnected = collide;
        world.createJoint(weldJointDef);
    }

}
