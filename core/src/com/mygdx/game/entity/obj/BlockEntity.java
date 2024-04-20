package com.mygdx.game.entity.obj;

import static com.mygdx.game.Main.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.IEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockEntity extends Entity implements IEntity {
    public static List<BlockEntity> entities = new ArrayList<>();
    public static void upd() {
        for (BlockEntity b : entities){
            b.update();
            spriteBatch.setProjectionMatrix(getCamera().combined);
            spriteBatch.begin();
            b.render();
            spriteBatch.end();
//            System.out.println("Hi");
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


}
