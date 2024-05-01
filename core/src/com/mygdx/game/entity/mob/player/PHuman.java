package com.mygdx.game.entity.mob.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entity.mob.IPlayer;
import com.mygdx.game.entity.mob.hostile.Human;
import com.mygdx.game.entity.obj.MetalBox;

import static com.mygdx.game.util.IKeycodes.*;
import static com.mygdx.game.util.MyInputProcessor.clickEvent;
import static com.mygdx.game.util.MyInputProcessor.events;

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
            moveForward();
        if (events.contains(A))
            moveLeft();
        if (events.contains(S))
            moveBackward();
        if (events.contains(D))
            moveRight();
        if (clickEvent.contains(LEFTCLICK))
            placeBlock();
        if (events.contains(SPACE))
            placeBlock();

        System.out.printf("X[%f]  Y[%f]\n", Math.round(getPosition().x / 15) * 15f, Math.round(getPosition().y / 15) * 15f);
    }

    @Override
    public void render() {
        super.render();
    }


}
