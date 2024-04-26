package com.mygdx.game.entity.obj;

import static com.mygdx.game.Main.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.IEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockEntity extends Entity implements IEntity, Block {
    public static List<BlockEntity> entities = new ArrayList<>();
    public static void upd() {
        for (BlockEntity b : entities){
            b.update();
            spriteBatch.setProjectionMatrix(getCamera().combined);
            spriteBatch.begin();
            b.render();
            spriteBatch.end();
//            System.out.println("Hi");
            b.shapeRender();
        }
    }
    public static <T extends BlockEntity> T getBlockInstance(Class<T> c, int n) {
        for (BlockEntity b : entities) {
            if (c.isInstance(b))
                if (b.getName(0) == Integer.toString(n).charAt(0))
                    return (c.cast(b));
        }
        return null;
    }

    public BlockEntity(float x, float y, BodyDef.BodyType BT) {
        bodyDef = new BodyDef();
        bodyDef.type = BT;
        bodyDef.position.set(new Vector2(x, y));
        setBody(world.createBody(bodyDef));
        getBody().setAngularDamping(.5f);
        getBody().setLinearDamping(.5f);
    }

    public BlockEntity() {};


    private boolean[] adj = new boolean[8];
    @Override
    public void updCheckAdjBlocks() {
        Vector2 pos = getBody().getPosition();
        adj = new boolean[8];

        for (BlockEntity b : entities) {
            if (b instanceof MetalBox) {
                if (b.getPosition().x == pos.x + 1f && b.getPosition().y == pos.y)  //right
                    adj[0] = true;
                if (b.getPosition().x == pos.x + 1f && b.getPosition().y == pos.y + 1f) // upper right
                    adj[1] = true;
                if (b.getPosition().y == pos.y + 1f && b.getPosition().x == pos.x) // up
                    adj[2] = true;
                if (b.getPosition().x == pos.x - 1f && b.getPosition().y == pos.y + 1f) // upper left
                    adj[3] = true;
                if (b.getPosition().x == pos.x - 1f && b.getPosition().y == pos.y) // left
                    adj[4] = true;
                if (b.getPosition().x == pos.x - 1f && b.getPosition().y == pos.y - 1f) // lower left
                    adj[5] = true;
                if (b.getPosition().y == pos.y - 1f && b.getPosition().x == pos.x) // down
                    adj[6] = true;
                if (b.getPosition().x == pos.x + 1f && b.getPosition().y == pos.y - 1f) // lower right
                    adj[7] = true;

            }
        }
    }

    public boolean getAdj(int i) {return adj[i];}

}
