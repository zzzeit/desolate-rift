package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private float angle = 0, deltaAngle = 0, prevAngle = 0;

    // Sprites
    private Sprite sprite;
    private Texture texture;

    // Setter
    public void setBody(Body b) {body = b;}
    public void setAngle(float a) {angle = a;}
    public void setDeltaAngle(float d) {deltaAngle = d;}
    public void setPrevAngle(float p) {prevAngle = p;}
    public void setName(String n) {name = n;}
    public void setSprite(Sprite s) {sprite = s;}
    public void setTexture(Texture t) {texture = t;}


    // Getter
    public Body getBody() {return body;}
    public float getAngle() {return angle;}
    public float getDeltaAngle() {return deltaAngle;}
    public float getPrevAngle() {return prevAngle;}
    public String getName() {return name;}
    public char getName(int n) {return name.charAt(n);}
    public Sprite getSprite() {return sprite;}
    public Texture getTexture() {return texture;}

    public void calcDeltaAngle() {deltaAngle = prevAngle - angle; prevAngle = angle;}

}
