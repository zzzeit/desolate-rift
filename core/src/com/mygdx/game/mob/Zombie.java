package com.mygdx.game.mob;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.mygdx.game.obj.shape.Entity;

import static com.mygdx.game.Main.world;

public class Zombie extends Entity {
    public static int num_of_zom = 0;
    public static void instantiate(float x, float y) {
        num_of_zom++;
        entities.add(new Zombie(x, y, "Zom".concat(Integer.toString(num_of_zom))));
    }
    public static Zombie getZombie(int n) {
        for (Entity e : entities) {
            if (e instanceof Zombie)
                if (e.getName().equals("Zom".concat(Integer.toString(n))))
                    return (Zombie) e;
        }
        return null;
    }

    private Shape shape;
    private Body head, torso, armLeft, armRight;
    private float damp = 2f, angle, previousAngle = 0, dAngle = 0;
    public Zombie(float x, float y, String name) {
        super(x, y, BodyDef.BodyType.DynamicBody);
        setName(name);
        revoluteJointDef.enableLimit = true;

        // HEAD
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        head = world.createBody(bodyDef);
        shape = new CircleShape();
        shape.setRadius(.5f);
        head.createFixture(shape, 1f);
        head.setTransform(0f, 0f, 0f);
        shape.dispose();
        head.setAngularDamping(damp);
        head.setLinearDamping(damp);


        // TORSO
        torso = world.createBody(bodyDef);
        shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(1f, .25f, new Vector2(0f, 0f), 0);
        torso.createFixture(shape, .1f);
        torso.setAngularDamping(damp);
        torso.setLinearDamping(damp);

        // ARM
        armLeft = world.createBody(bodyDef);
        ((PolygonShape) shape).setAsBox(.25f, .75f);
        armLeft.createFixture(shape, .1f);
        armLeft.setAngularDamping(damp);
        armLeft.setLinearDamping(damp);

        revoluteJointDef.bodyA = torso;
        revoluteJointDef.bodyB = armLeft;
        revoluteJointDef.collideConnected = false;
        revoluteJointDef.localAnchorA.set(-.75f, 0);
        revoluteJointDef.localAnchorB.set(0f, -.50f);
        revoluteJointDef.upperAngle = (float) Math.toRadians(20);
        revoluteJointDef.lowerAngle = -(float) Math.toRadians(20);
        world.createJoint(revoluteJointDef);

        armRight = world.createBody(bodyDef);
        ((PolygonShape) shape).setAsBox(.25f, .75f);
        armRight.createFixture(shape, .1f);
        armRight.setAngularDamping(damp);
        armRight.setLinearDamping(damp);

        revoluteJointDef.bodyA = torso;
        revoluteJointDef.bodyB = armRight;
        revoluteJointDef.collideConnected = false;
        revoluteJointDef.localAnchorA.set(.75f, 0);
        revoluteJointDef.localAnchorB.set(0f, -.50f);
        revoluteJointDef.upperAngle = (float) Math.toRadians(20);
        revoluteJointDef.lowerAngle = -(float) Math.toRadians(20);

        world.createJoint(revoluteJointDef);
        shape.dispose();


        weldJointDef.bodyA = head;
        weldJointDef.bodyB = torso;
        weldJointDef.collideConnected = false;
        world.createJoint(weldJointDef);



    }

    // SETTER

    // GETTER
    public Body getHead() {return head;}
    public Body getTorso() {return torso;}
    public float getAngle() {return angle;}
    public float getDAngle() {return dAngle;}

    @Override
    public void update() {
        angle = head.getAngle();
        dAngle = previousAngle - angle;
        previousAngle = angle;
        System.out.println(Math.toDegrees(dAngle));
    }

    public void turnLeft() {head.applyTorque(2f, true);}
    public void turnRight() {head.applyTorque(-2f, true);}
    public void moveForward() {head.applyForceToCenter((float) (5f * Math.cos(getAngle() + Math.toRadians(90))), (float) (5f * Math.sin(getAngle() + Math.toRadians(90))), true);}
    public void moveBackward() {head.applyForceToCenter((float) (-5f * Math.cos(getAngle() + Math.toRadians(90))), (float) (-5f * Math.sin(getAngle() + Math.toRadians(90))), true);}
}
