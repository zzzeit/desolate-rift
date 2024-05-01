package com.mygdx.game.entity.obj;

public interface Block {
    void checkAdjBlocks();

    /**
     *
     * @param r The number of times this method is called recursively
     */
    void updAdjBlocks(int r);
}
