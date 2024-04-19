package com.mygdx.game.entity.mob.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entity.mob.MobileEntity;

import static com.mygdx.game.Main.world;

public class Human extends MobileEntity {
    public static int num_of_hum = 0;
    public static void instantiate(float x, float y) {
        num_of_hum++;
        mobs.add(new Human(x, y, Integer.toString(num_of_hum).concat("_HUM")));
    }


    private Shape shape;
    private Body head, torso;
    private float speed = 20f, damp = 4f, angle, visionAngle, previousAngle = 0, dAngle = 0;
    public Human(float x, float y, String name) {
        super(x, y, BodyDef.BodyType.DynamicBody);
        setName(name);
        revoluteJointDef.enableLimit = true;

        // HEAD
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        head = world.createBody(bodyDef);
        shape = new CircleShape();
        shape.setRadius(.5f);
        head.createFixture(shape, 1f);
        shape.dispose();
        head.setAngularDamping(10);
        head.setLinearDamping(damp);
        head.setFixedRotation(true);


        // TORSO
        torso = world.createBody(bodyDef);
        shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(1f, .25f, new Vector2(0f, 0f), 0);
        torso.createFixture(shape, .1f);
        torso.setAngularDamping(damp);
        torso.setLinearDamping(damp);
        torso.setSleepingAllowed(false);


        shape.dispose();


        weldBodies2(head, torso, false);
    }

    // SETTER


    // GETTER
    public Body getHead() {return head;}
    public Body getTorso() {return torso;}
    public float getAngle(boolean d) {
        if (!d) return angle;
        else return (float) Math.toDegrees(angle);
    }
    public float getDAngle() {return dAngle;}

    @Override
    public void update() {
        angle = head.getAngle(); // Get the current angle
        dAngle = previousAngle - angle;
        previousAngle = angle;
        while (angle < 0) {
            angle += (float) (Math.PI * 2);
        }
        while (angle >= Math.PI * 2) {
            angle -= (float) (Math.PI * 2);
        }
        visionAngle = (float) (angle + (Math.PI / 2));
        if (visionAngle > Math.PI * 2)
            visionAngle -= (float) (Math.PI * 2);
    }
    @Override
    public void render() {

    }

    public void turnLeft() {head.applyTorque(speed * .05f, true);}
    public void turnRight() {head.applyTorque(-speed * .05f, true);}
    public void moveLeft() {head.applyForceToCenter((float) (-speed * Math.cos(getAngle(false))), (float) (-speed * Math.sin(getAngle(false))), true);}
    public void moveRight() {head.applyForceToCenter((float) (speed * Math.cos(getAngle(false))), (float) (speed * Math.sin(getAngle(false))), true);}
    public void moveForward() {head.applyForceToCenter((float) (speed * Math.cos((getAngle(false) + Math.PI / 2))), (float) (speed * Math.sin((getAngle(false) + Math.PI / 2))), true);}
    public void moveBackward() {head.applyForceToCenter((float) (-speed * Math.cos((getAngle(false) + Math.PI / 2))), (float) (-speed * Math.sin((getAngle(false) + Math.PI / 2))), true);}
    public void rotate(float angle) {head.setTransform(head.getPosition().x, head.getPosition().y, (float) (head.getAngle() + Math.toRadians(angle)));}



}
