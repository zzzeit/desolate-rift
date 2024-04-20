package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private TextureRegion textureRegion;
    private int ssr = 0;  // Sprite sheet region

    // Setter
    public void setBody(Body b) {body = b;}
    public void setAngle(float a) {angle = a;}
    public void setDeltaAngle(float d) {deltaAngle = d;}
    public void setPrevAngle(float p) {prevAngle = p;}
    public void setName(String n) {name = n;}
    public void setSprite(Sprite s) {sprite = s;}
    public void setTexture(Texture t) {texture = t;}
    public void setTextureRegion(TextureRegion t) {textureRegion = t;}
    public void setSSR(int s) {ssr = s;}


    // Getter
    public Body getBody() {return body;}
    public float getAngle(boolean isDegree) {if (!isDegree) return angle; else return (float) Math.toDegrees(angle);}
    public float getDeltaAngle() {return deltaAngle;}
    public float getPrevAngle() {return prevAngle;}
    public String getName() {return name;}
    public char getName(int n) {return name.charAt(n);}
    public Sprite getSprite() {return sprite;}
    public Texture getTexture() {return texture;}
    public TextureRegion getTextureRegion() {return textureRegion;}
    public int getSSR() {return ssr;}

    public void calcDeltaAngle() {deltaAngle = prevAngle - angle; prevAngle = angle;}
    public void addAngle(float a) {angle += a;}

}
