package com.mygdx.game.entity.mob.hostile;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entity.mob.MobileEntity;
import org.lwjgl.Sys;

import static com.mygdx.game.util.Settings.angleBetweenPoints;
import static com.mygdx.game.Main.world;

public class Zombie extends MobileEntity {
    public static int num_of_zom = 0;
    public static void instantiate(float x, float y) {
        num_of_zom++;
        mobs.add(new Zombie(x, y, "Zom".concat(Integer.toString(num_of_zom))));
    }
    public static Zombie getZombie(int n) {
        for (MobileEntity m : mobs) {
            if (m instanceof Zombie)
                if (m.getName().equals("Zom".concat(Integer.toString(n))))
                    return (Zombie) m;
        }
        return null;
    }

    private Shape shape;
    private Body head, torso, armLeft, armRight;
    private float speed = 10f, damp = 4f, angle, visionAngle, previousAngle = 0, dAngle = 0;
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
    public float getVisionAngle(boolean d) {
        if (!d) return visionAngle;
        else return (float) Math.toDegrees(visionAngle);
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


    public void turnLeft() {head.applyTorque(speed * .8f, true);}
    public void turnRight() {head.applyTorque(-speed * .8f, true);}
    public void moveForward() {head.applyForceToCenter((float) (speed * Math.cos((getAngle(false) + Math.PI / 2))), (float) (speed * Math.sin((getAngle(false) + Math.PI / 2))), true);}
    public void moveBackward() {head.applyForceToCenter((float) (-speed * Math.cos((getAngle(false) + Math.PI / 2))), (float) (-speed * Math.sin((getAngle(false) + Math.PI / 2))), true);}

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


    @Override
    public void render() {
    }
}
