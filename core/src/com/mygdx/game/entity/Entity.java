package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.mygdx.game.util.MyQueryCallback;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.Main.world;

/**
 * The base class for entities
 */
public abstract class Entity {
    public static int nPlayers = 0;
    public static int err = 0;

    /**
     *
     * @param v this is a vector 2 for the position you want to check
     * @return TRUE if it detects a body from the desired location
     */
    public static boolean bodyExists(Vector2 v) {
        // Define the AABB around the position
        float halfWidth = 0.1f; // Adjust this value based on the size of your bodies
        float halfHeight = 0.1f; // Adjust this value based on the size of your bodies
        Vector2 lowerBound = new Vector2(v.x - halfWidth, v.y - halfHeight);
        Vector2 upperBound = new Vector2(v.x + halfWidth, v.y + halfHeight);

        // Perform the query
        MyQueryCallback callback = new MyQueryCallback();
        world.QueryAABB(callback, lowerBound.x, lowerBound.y, upperBound.x, upperBound.y);
        return callback.isBodyFound();
    }

    public void render() {};
    public void update() {};
    public abstract Vector2 getPosition();


    private BodyDef bodyDef = new BodyDef();
    public WeldJointDef weldJointDef = new WeldJointDef();
    public RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
    private String name;

    private float angle = 0, deltaAngle = 0, prevAngle = 0;

    // Sprites
    private Sprite sprite;
    private Texture texture;
    private TextureRegion textureRegion;
    private List<TextureRegion> textureRegionList = new ArrayList<>();
    private List<Sprite> spriteList = new ArrayList<>();
    private int ssr = 0;  // Sprite sheet region

    // Setter
    public void setAngle(float a) {angle = a;}
    public void setDeltaAngle(float d) {deltaAngle = d;}
    public void setPrevAngle(float p) {prevAngle = p;}
    public void setName(String n) {name = n;}
    public void setSprite(Sprite s) {sprite = s;}
    public void setTexture(Texture t) {texture = t;}
    public void setTextureRegion(TextureRegion t) {textureRegion = t;}
    public void setSSR(int s) {ssr = s;}
    public void setBodyDef(BodyDef bd) {bodyDef = bd;}


    // Getter
    public float getAngle(boolean isDegree) {if (!isDegree) return angle; else return (float) Math.toDegrees(angle);}
    public float getDeltaAngle() {return deltaAngle;}
    public float getPrevAngle() {return prevAngle;}
    public String getName() {return name;}
    public char getName(int n) {return name.charAt(n);}
    public Sprite getSprite() {return sprite;}
    public Texture getTexture() {return texture;}
    public TextureRegion getTextureRegion() {return textureRegion;}
    public List<TextureRegion> getTextureRegionList() {return textureRegionList;}
    public List<Sprite> getSpriteList() {return spriteList;}
    public BodyDef getBodyDef() {return bodyDef;}

    public int getSSR() {return ssr;}

    public void calcDeltaAngle() {deltaAngle = prevAngle - angle; prevAngle = angle;}
    public void addAngle(float a) {angle += a;}


}
