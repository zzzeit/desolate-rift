package com.mygdx.game.entity.obj.shape;

import static com.mygdx.game.Main.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.IEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockEntity extends Entity implements IEntity {
    public static List<BlockEntity> entities = new ArrayList<>();
    public static void upd() {
        for (BlockEntity b : entities){
            b.update();
            b.render();
        }
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
