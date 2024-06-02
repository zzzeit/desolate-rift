package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Main;
import com.mygdx.game.entity.item.ItemEntity;
import com.mygdx.game.entity.mob.MobileEntity;
import com.mygdx.game.entity.obj.BlockEntity;
import com.mygdx.game.map.Map;
import com.mygdx.game.map.maps.Plains;
import com.mygdx.game.util.MyQueryCallback;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.Main.world;

/**
 * The base class for entities
 */
public abstract class Entity implements DestroyEntity {
    public static int nPlayers = 0;
    public static int err = 0;
    public static BodyDef bodyDef = new BodyDef();
    public static WeldJointDef weldJointDef = new WeldJointDef();
    public static RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
    public static FixtureDef fixtureDef = new FixtureDef();
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

    public static void weldBodies2(Body b1, Body b2, Boolean collide) {
        weldJointDef.bodyA = b1;
        weldJointDef.bodyB = b2;
        weldJointDef.collideConnected = collide;
        world.createJoint(weldJointDef);
    }

    public static void revoluteBodies2(Body b1, Body b2, Vector2 anchor1, Vector2 anchor2, Boolean collide) {
        revoluteJointDef.bodyA = b1;
        revoluteJointDef.bodyB = b2;
        revoluteJointDef.collideConnected = collide;
        revoluteJointDef.localAnchorA.set(anchor1);
        revoluteJointDef.localAnchorB.set(anchor2);
        revoluteJointDef.upperAngle = (float) Math.toRadians(20);
        revoluteJointDef.lowerAngle = -(float) Math.toRadians(20);
        world.createJoint(revoluteJointDef);
    }

    public static boolean isWithinDistance(Body body1, Body body2, float range) {
        Vector2 position1 = body1.getPosition();
        Vector2 position2 = body2.getPosition();

        float distance = position1.dst(position2);

        return distance <= range;
    }

    public static void entityRender() {
        for (int i = 0; i < 10; i++) {
            BlockEntity.ren(i);
            MobileEntity.ren(i);
            ItemEntity.ren(i);
        }

        Main.map.renderBounds();
    }

    public static void entityUpdate() {
        BlockEntity.upd();
        MobileEntity.upd();
        ItemEntity.upd();
}

    public abstract void render(int layer);
    public abstract void update();
    public abstract Vector2 getPosition();




    private String name;
    private short collisionCategory;
    private float angle = 0, deltaAngle = 0, prevAngle = 0;

    // Sprites
    private Sprite sprite;
    private Texture texture;
    private TextureRegion textureRegion;
    private List<TextureRegion> textureRegionList = new ArrayList<>();
    private Array<Sprite> spriteList = new Array<>();
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
    public void setCollisionCategory(short c) {collisionCategory = c;}


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
    public Array<Sprite> getSpriteList() {return spriteList;}
    public short getCollisionCategory() {return collisionCategory;}

    public int getSSR() {return ssr;}

    public void calcDeltaAngle() {deltaAngle = prevAngle - angle; prevAngle = angle;}
    public void addAngle(float a) {angle += a;}


    @Override
    public void destroy() {

    }
}
