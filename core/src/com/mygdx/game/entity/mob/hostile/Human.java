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
import static com.mygdx.game.Main.world;
import static com.mygdx.game.util.Settings.angleBetweenPoints;

public class Human extends MobileEntity {
    public static int num_of_hum = 0;
    public static void instantiate(float x, float y) {
        num_of_hum++;
        mobs.add(new Human(x, y, Integer.toString(num_of_hum).concat("_HUM")));
    }
    
    private Disk head = new Disk(0, 0, .5f, BodyDef.BodyType.DynamicBody, 1f);
    private Rect torso = new Rect(0, 0, 2f, .5f, BodyDef.BodyType.DynamicBody, 1f);
    private float speed = 40f, damp = 4f, visionAngle;
    public Human(float x, float y, String name) {
        setName(name);
        revoluteJointDef.enableLimit = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // HEAD
        head.getBody().setAngularDamping(10);
        head.getBody().getFixtureList().first().setFriction(0.1f);
        head.getBody().setLinearDamping(8);
//        head.getBody().setFixedRotation(true);


        // TORSO
        torso.getBody().setAngularDamping(damp);
        torso.getBody().setLinearDamping(damp);
        torso.getBody().setSleepingAllowed(false);




        weldBodies2(head.getBody(), torso.getBody(), false);


        setTexture(new Texture("human.png"));
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
        getSpriteList().get(1).setCenter(getTorso().getPosition().x, getTorso().getPosition().y);
        getSpriteList().get(1).setRotation((float) (Math.toDegrees(getTorso().getBody().getAngle())));
        getSpriteList().get(1).draw(spriteBatch);

        getSpriteList().get(0).setCenter(getHead().getPosition().x, getHead().getPosition().y);
        getSpriteList().get(0).setRotation((float) (Math.toDegrees(getHead().getBody().getAngle())));
        getSpriteList().get(0).draw(spriteBatch);

    }

    public void turnLeft() {head.getBody().applyTorque(speed * .1f, true);}
    public void turnRight() {head.getBody().applyTorque(-speed * .1f, true);}
    public void moveLeft() {head.getBody().applyForceToCenter((float) (-speed * Math.cos(getAngle(false))), (float) (-speed * Math.sin(getAngle(false))), true);}
    public void moveRight() {head.getBody().applyForceToCenter((float) (speed * Math.cos(getAngle(false))), (float) (speed * Math.sin(getAngle(false))), true);}
    public void moveForward() {head.getBody().applyForceToCenter((float) (speed * Math.cos((getAngle(false) + Math.PI / 2))), (float) (speed * Math.sin((getAngle(false) + Math.PI / 2))), true);}
    public void moveBackward() {head.getBody().applyForceToCenter((float) (-speed * Math.cos((getAngle(false) + Math.PI / 2))), (float) (-speed * Math.sin((getAngle(false) + Math.PI / 2))), true);}
    public void rotate(float angle) {head.getBody().setTransform(head.getBody().getPosition().x, head.getBody().getPosition().y, (float) (head.getBody().getAngle() + Math.toRadians(angle)));}

    @Override
    public Vector2 getPosition() {
        return getHead().getPosition();
    }



    // SETTER


    // GETTER
    public Disk getHead() {return head;}
    public Rect getTorso() {return torso;}
}
