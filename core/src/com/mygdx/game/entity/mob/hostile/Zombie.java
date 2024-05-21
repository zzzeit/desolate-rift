package com.mygdx.game.entity.mob.hostile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entity.mob.MobileEntity;
import com.mygdx.game.entity.obj.shape.Disk;
import com.mygdx.game.entity.obj.shape.Rect;

import static com.mygdx.game.Main.spriteBatch;
import static com.mygdx.game.util.Settings.angleBetweenPoints;
import static com.mygdx.game.Main.world;

public class Zombie extends MobileEntity {
    public static int num_of_zom = 0;
    public static void instantiate(float x, float y) {
        num_of_zom++;
        mobs.add(new Zombie(x, y, Integer.toString(num_of_zom).concat("_ZOM")));
    }
//    private Body head, torso, armLeft, armRight;
    private Disk head;
    private Rect torso, armLeft, armRight;
    private float speed = 10f, damp = 4f, visionAngle;
    public Zombie(float x, float y, String name) {
        setName(name);
        revoluteJointDef.enableLimit = true;
        getBodyDef().type = BodyDef.BodyType.DynamicBody;

        // HEAD
        head = new Disk(0, 0, .5f, BodyDef.BodyType.DynamicBody, 1f);
        head.getBody().setAngularDamping(damp);
        head.getBody().setLinearDamping(damp);


        // TORSO
        torso = new Rect(0, 0, 2f, .5f, BodyDef.BodyType.DynamicBody, .1f);
        torso.getBody().setAngularDamping(damp);
        torso.getBody().setLinearDamping(damp);

        weldBodies2(head.getBody(), torso.getBody(), false);

        // ARM
        armLeft = new Rect(0, 0, .5f, 1.5f, BodyDef.BodyType.DynamicBody, .1f);
        armLeft.getBody().setAngularDamping(damp);
        armLeft.getBody().setLinearDamping(damp);
        revoluteBodies2(torso.getBody(), armLeft.getBody(), new Vector2(-.75f, 0), new Vector2(0, -.5f), false);

        armRight = new Rect(0, 0, .5f, 1.5f, BodyDef.BodyType.DynamicBody, .1f);
        armRight.getBody().setAngularDamping(damp);
        armRight.getBody().setLinearDamping(damp);
        revoluteBodies2(torso.getBody(), armRight.getBody(), new Vector2(.75f, 0), new Vector2(0, -.5f), false);




        setTexture(new Texture("zombie.png"));
        setSSR(0);
        getTextureRegionList().add(new TextureRegion(getTexture(), getSSR() * (1f/3), 0f, ((getSSR() + 1) / 3f), 1f));
        getSpriteList().add(new Sprite(getTextureRegionList().get(0)));
        getSpriteList().get(0).setScale(2/33f);
        setSSR(1);
        getTextureRegionList().add(new TextureRegion(getTexture(), getSSR() * (1f/3), 0f, ((getSSR() + 1) / 3f), 1f));
        getSpriteList().add(new Sprite(getTextureRegionList().get(1)));
        getSpriteList().get(1).setScale(2/33f);
        setSSR(2);
        getTextureRegionList().add(new TextureRegion(getTexture(), getSSR() * (1f/3), 0f, ((getSSR() + 1) / 3f), 1f));
        getSpriteList().add(new Sprite(getTextureRegionList().get(2)));
        getSpriteList().get(2).setScale(1.5f/33f);


    }


    @Override
    public void update() {
        setAngle(getHead().getBody().getAngle());
        calcDeltaAngle();
        while (getAngle(false) < 0) {
            addAngle((float) (Math.PI * 2));
        }
        while (getAngle(false) >= Math.PI * 2) {
            addAngle(-(float) (Math.PI * 2));
        }
        visionAngle = (float) (getAngle(false) + (Math.PI / 2));
        if (visionAngle > Math.PI * 2)
            visionAngle -= (float) (Math.PI * 2);
    }


    @Override
    public void render() {
        getSpriteList().get(1).setCenter(getHead().getPosition().x, getHead().getPosition().y);
        getSpriteList().get(1).rotate((float) (-1 * Math.toDegrees(getDeltaAngle())));
        getSpriteList().get(1).draw(spriteBatch);

        getSpriteList().get(2).setCenter(armLeft.getPosition().x, armLeft.getPosition().y);
        getSpriteList().get(2).flip(true, false);
        getSpriteList().get(2).setRotation((float) (Math.toDegrees(armLeft.getBody().getAngle())));
        getSpriteList().get(2).draw(spriteBatch);

        getSpriteList().get(2).setCenter(armRight.getPosition().x, armRight.getPosition().y);
        getSpriteList().get(2).flip(true, false);
        getSpriteList().get(2).setRotation((float) (Math.toDegrees(armRight.getBody().getAngle())));
        getSpriteList().get(2).draw(spriteBatch);

        getSpriteList().get(0).setCenter(getHead().getPosition().x, getHead().getPosition().y);
        getSpriteList().get(0).rotate((float) (-1 * Math.toDegrees(getDeltaAngle())));
        getSpriteList().get(0).draw(spriteBatch);

    }

    @Override
    public Vector2 getPosition() {
        return null;
    }


    public void turnLeft() {head.getBody().applyTorque(speed * .8f, true);}
    public void turnRight() {head.getBody().applyTorque(-speed * .8f, true);}
    public void moveForward() {head.getBody().applyForceToCenter((float) (speed * Math.cos((getAngle(false) + Math.PI / 2))), (float) (speed * Math.sin((getAngle(false) + Math.PI / 2))), true);}
    public void moveBackward() {head.getBody().applyForceToCenter((float) (-speed * Math.cos((getAngle(false) + Math.PI / 2))), (float) (-speed * Math.sin((getAngle(false) + Math.PI / 2))), true);}

    private float haD;
    public void faceToPoint(Vector2 center, Vector2 p2, float FOV) {
        float headAngle = getVisionAngle(false), angleBetween = (float) angleBetweenPoints(center, p2, false);
        haD = headAngle - angleBetween;
        // Ensure the difference is within the range of -π to π (or -180 to 180 degrees)
        while (haD < -Math.PI) {
            haD += 2 * Math.PI;
        }
        while (haD >= Math.PI) {
            haD -= 2 * Math.PI;
        }
        haD = (float) Math.toDegrees(haD);
        if (haD < 0 - (FOV / 2))
            turnLeft();
        else if (haD > 0 + (FOV / 2))
            turnRight();
        else
            moveForward();
//        System.out.printf("HA[%f]  AB[%f]\n", headAngle, angleBetween);
//        System.out.printf("HA[%f]   AB[%f]   DIFFERENCE[%f]\n", headAngle, angleBetween, hold);

    }


    // SETTER


    // GETTER
    public Disk getHead() {return head;}
    public Rect getTorso() {return torso;}
    public Rect getArmLeft() {return armLeft;}
    public Rect getArmRight() {return armRight;}
    public float getVisionAngle(boolean d) {
        if (!d) return visionAngle;
        else return (float) Math.toDegrees(visionAngle);
    }

}
