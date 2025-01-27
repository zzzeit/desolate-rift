package com.mygdx.game.entity.obj;

import static com.mygdx.game.Main.*;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.DestroyEntity;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.obj.grounds.Ground;
import com.mygdx.game.entity.obj.resourceblock.Tree;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockEntity extends Entity{
    public static Array<BlockEntity> entities = new Array<>();
    public static Array<Ground> grounds = new Array<>();
    public static void create(BlockEntity b) {
        if (b instanceof Ground)
            grounds.add((Ground) b);
        else
            entities.add(b);
    }
    public static void upd() {
        for (BlockEntity b : entities){
            b.update();
        }
    }
    public static void ren(int layer) {
        if (layer == 0)
            for (Ground g : grounds) {
                g.render(layer);
            }

        if (layer == 3)
            for (BlockEntity b : entities)
                if (!(b instanceof Tree) && !(b instanceof Ground))
                    b.render(layer);

        for (BlockEntity b : entities)
            if (b instanceof Tree)
                b.render(layer);
    }

    public static <T extends BlockEntity> T getBlockInstance(Class<T> c, int n) {
        for (BlockEntity b : entities) {
            if (c.isInstance(b))
                if (b.getName(0) == Integer.toString(n).charAt(0))
                    return (c.cast(b));
        }
        return null;
    }



//    private boolean[] adj = new boolean[8];
//    @Override
//    public void checkAdjBlocks(Body body) {
//        Vector2 pos = body.getPosition();
//        adj = new boolean[8];
//
//        for (BlockEntity b : entities) {
//            if (b instanceof MetalBox) {
//                if (b.getPosition().x == pos.x + 1f && b.getPosition().y == pos.y)  //right
//                    adj[0] = true;
//                if (b.getPosition().x == pos.x + 1f && b.getPosition().y == pos.y + 1f) // upper right
//                    adj[1] = true;
//                if (b.getPosition().y == pos.y + 1f && b.getPosition().x == pos.x) // up
//                    adj[2] = true;
//                if (b.getPosition().x == pos.x - 1f && b.getPosition().y == pos.y + 1f) // upper left
//                    adj[3] = true;
//                if (b.getPosition().x == pos.x - 1f && b.getPosition().y == pos.y) // left
//                    adj[4] = true;
//                if (b.getPosition().x == pos.x - 1f && b.getPosition().y == pos.y - 1f) // lower left
//                    adj[5] = true;
//                if (b.getPosition().y == pos.y - 1f && b.getPosition().x == pos.x) // down
//                    adj[6] = true;
//                if (b.getPosition().x == pos.x + 1f && b.getPosition().y == pos.y - 1f) // lower right
//                    adj[7] = true;
//
//            }
//        }
//    }

//    @Override
//    public void updAdjBlocks(int r) {
//        if (r > 0)
//            for (int y = 0; y < 3; y++) {
//                for (int x = 0; x < 3; x++) {
//                    if (!(y == 1 && x == 1))
//                        if (bodyExists(new Vector2(getPosition().x + x - 1, getPosition().y + y - 1))) {
//                            for (BlockEntity be : entities) {
//                                if (be instanceof MetalBox)
//                                    if (be.getPosition().x == getPosition().x + x - 1 && be.getPosition().y == getPosition().y + y - 1) {
////                                        be.checkAdjBlocks();
//                                        be.updAdjBlocks(r - 1);
//                                        System.out.println("Updated Adjacent Blcoks");
//                                    }
//                            }
//    //                        System.out.println("Body Found");
//                        }
//                }
//            }
//    }

//    public boolean getAdj(int i) {return adj[i];}
//
//    @Override
//    public void chunkLoad() {
//        world.createBody(getBodyDef());
//    }
//
//    @Override
//    public void chunkUnload() {
//        getTexture().dispose();
//        world.destroyBody(getBody());
//    }

}
