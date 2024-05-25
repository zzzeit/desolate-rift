package com.mygdx.game.entity.mob.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entity.mob.IPlayer;
import com.mygdx.game.entity.mob.hostile.Human;
import com.mygdx.game.entity.obj.blocks.MetalBox;
import com.mygdx.game.map.Map;

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
        getTorso().getBody().setActive(false);
        getHead().getBody().getFixtureList().first().setFriction(5f);
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
        outOfBounds();
    }

    private void outOfBounds() {
        int hw = Map.mapSize[0]/2, hh = Map.mapSize[1]/2;
        if (getHead().getPosition().x > hw)
            getHead().getBody().applyForceToCenter(-50f, 0, true);
        if (getHead().getPosition().x < -hw)
            getHead().getBody().applyForceToCenter(50f, 0, true);
        if (getHead().getPosition().y > hh)
            getHead().getBody().applyForceToCenter(0, -50f, true);
        if (getHead().getPosition().y < -hh)
            getHead().getBody().applyForceToCenter(0, 50f, true);
    }

    @Override
    public void render(int layer) {
        super.render(layer);
    }


}
