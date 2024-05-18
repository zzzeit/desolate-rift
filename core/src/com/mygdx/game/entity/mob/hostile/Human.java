package com.mygdx.game.entity.mob.hostile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entity.mob.MobileEntity;

import static com.mygdx.game.Main.spriteBatch;
import static com.mygdx.game.Main.world;

public class Human extends MobileEntity {
    public static int num_of_hum = 0;
    public static void instantiate(float x, float y) {
        num_of_hum++;
        mobs.add(new Human(x, y, Integer.toString(num_of_hum).concat("_HUM")));
    }


    private Shape shape;
    private Body head, torso;
    private float speed = 40f, damp = 4f, visionAngle;
    public Human(float x, float y, String name) {
        setName(name);
        revoluteJointDef.enableLimit = true;
        getBodyDef().type = BodyDef.BodyType.DynamicBody;

        // HEAD
        head = world.createBody(getBodyDef());
        shape = new CircleShape();
        shape.setRadius(.5f);
        head.createFixture(shape, 1f);
        shape.dispose();
        head.setAngularDamping(10);
        head.getFixtureList().first().setFriction(0.1f);
        head.setLinearDamping(8);
        head.setFixedRotation(true);


        // TORSO
        torso = world.createBody(getBodyDef());
        shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(1f, .25f, new Vector2(0f, 0f), 0);
        torso.createFixture(shape, .1f);
        torso.setAngularDamping(damp);
        torso.setLinearDamping(damp);
        torso.setSleepingAllowed(false);


        shape.dispose();


        weldBodies2(head, torso, false);


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
        setAngle(getHead().getAngle());
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
        getSpriteList().get(1).setRotation((float) (Math.toDegrees(getTorso().getAngle())));
        getSpriteList().get(1).draw(spriteBatch);

        getSpriteList().get(0).setCenter(getHead().getPosition().x, getHead().getPosition().y);
        getSpriteList().get(0).setRotation((float) (Math.toDegrees(getHead().getAngle())));
        getSpriteList().get(0).draw(spriteBatch);

    }

    public void turnLeft() {head.applyTorque(speed * .05f, true);}
    public void turnRight() {head.applyTorque(-speed * .05f, true);}
    public void moveLeft() {head.applyForceToCenter((float) (-speed * Math.cos(getAngle(false))), (float) (-speed * Math.sin(getAngle(false))), true);}
    public void moveRight() {head.applyForceToCenter((float) (speed * Math.cos(getAngle(false))), (float) (speed * Math.sin(getAngle(false))), true);}
    public void moveForward() {head.applyForceToCenter((float) (speed * Math.cos((getAngle(false) + Math.PI / 2))), (float) (speed * Math.sin((getAngle(false) + Math.PI / 2))), true);}
    public void moveBackward() {head.applyForceToCenter((float) (-speed * Math.cos((getAngle(false) + Math.PI / 2))), (float) (-speed * Math.sin((getAngle(false) + Math.PI / 2))), true);}
    public void rotate(float angle) {head.setTransform(head.getPosition().x, head.getPosition().y, (float) (head.getAngle() + Math.toRadians(angle)));}

    @Override
    public Vector2 getPosition() {
        return getHead().getPosition();
    }



    // SETTER


    // GETTER
    public Body getHead() {return head;}
    public Body getTorso() {return torso;}
}
