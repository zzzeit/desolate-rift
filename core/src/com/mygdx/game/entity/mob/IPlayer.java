package com.mygdx.game.entity.mob;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.obj.BlockEntity;

public interface IPlayer {
    void placeBlock();
    boolean canPlaceBlock();

    // getSilPos() for block placement silhouettes position
    default Vector2 getSilPos(Vector2 pos, float angle) {
        return new Vector2(Math.round((pos.x + (2 * Math.cos(angle + (Math.PI/2)))) / .5f) * .5f, Math.round((pos.y + (2 * Math.sin(angle + (Math.PI/2)))) / .5f) * .5f);
    }
}
