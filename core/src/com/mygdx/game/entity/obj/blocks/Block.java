package com.mygdx.game.entity.obj.blocks;

import com.badlogic.gdx.physics.box2d.Body;

public interface Block {
    void checkAdjBlocks(Body b);

    /**
     *
     * @param r The number of times this method is called recursively
     */
    void updAdjBlocks(int r);
}
