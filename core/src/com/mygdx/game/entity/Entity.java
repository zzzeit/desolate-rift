package com.mygdx.game.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public abstract class Entity {
    public BodyDef bodyDef;
    public FixtureDef shapeDef = new FixtureDef();
    public WeldJointDef weldJointDef = new WeldJointDef();
    public RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
    private Body body;
    private String name;

    // Setter
    public void setBody(Body b) {body = b;}
    public void setName(String n) {name = n;}

    // Getter
    public Body getBody() {return body;}
    public String getName() {return name;}

}
