package com.mygdx.game.entity.mob.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entity.mob.IPlayer;
import com.mygdx.game.entity.mob.hostile.Human;
import com.mygdx.game.entity.obj.blocks.MetalBox;

import static com.mygdx.game.util.IKeycodes.*;
import static com.mygdx.game.util.MyInputProcessor.*;

public class PHuman extends Human implements IPlayer {
    public static void instantiate(float x, float y) {
        nPlayers++;
        mobs.add(new PHuman(x, y, Integer.toString(nPlayers).concat("_PLAYER")));
    }


    public PHuman(float x, float y, String name) {
        super(x, y, name);
        setName(name);

    }



    // SILHOUETTE
    private Vector2 blockSilPos;
    @Override
    public void placeBlock() {
        // Check if any body was found
        if (bodyExists(blockSilPos)) {
            System.out.println("Cant place a bock here [" + err + "]");
            err++;
        }
        else
            MetalBox.instantiate(blockSilPos.x, blockSilPos.y, 1, BodyDef.BodyType.StaticBody, 1f);
    }

    @Override
    public Vector2 getChunkPosition(int m) {
        return new Vector2((getPosition().x / m) * m, (getPosition().x / m) * m);
    }


    @Override
    public void update() {
        super.update();
        blockSilPos = getSilPos(getPosition(), getAngle(false));
        if (events.contains(W))
            moveNorth();
        if (events.contains(A))
            moveWest();
        if (events.contains(S))
            moveSouth();
        if (events.contains(D))
            moveEast();
        if (clickEvent.contains(LEFTCLICK))
            placeBlock();
        if (events.contains(SPACE))
            placeBlock();
        getHead().getBody().setTransform(getPosition(), (float) Math.toRadians(mouseAngle));
        getTorso().getBody().setTransform(getPosition(), (float) Math.toRadians(mouseAngle));
//        System.out.printf("X[%f]  Y[%f]\n", Math.round(getPosition().x / 15) * 15f, Math.round(getPosition().y / 15) * 15f);
    }

    private void move(float angle) {
        float headAngle = getAngle(true), dif = Math.abs(angle - headAngle), fov = 40;
        if (headAngle > 180) {
            headAngle -= 180;
            if (headAngle < (angle - (fov/2f)))
                turnRight();
            else if (headAngle > (angle + (fov/2f)))
                turnLeft();
            else
                moveForward();
        }
        else {
            if (headAngle < (angle - (fov/2f)))
                turnLeft();
            else if (headAngle > (angle + (fov/2f)))
                turnRight();
            else
                moveForward();
        }




    }

    @Override
    public void render() {
        super.render();
    }


}
