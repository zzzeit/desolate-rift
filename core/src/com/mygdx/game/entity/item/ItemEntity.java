package com.mygdx.game.entity.item;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.DestroyEntity;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.IEntity;

import static com.mygdx.game.Main.random;
import static com.mygdx.game.Main.world;

public abstract class ItemEntity extends Entity {
    public static Array<ItemEntity> items = new Array<>();
    protected static TextureAtlas itemAtlas = new TextureAtlas("./pack/items.atlas");
    public static void create(ItemEntity item) {
        items.add(item);
    }
    public static void ren(int layer) {
        for (ItemEntity item : items) {
            item.render(layer);
        }
    }
    public static void upd() {
        for (ItemEntity item : items) {
            item.update();
        }
    }


    private float x = random.nextFloat(0, 180);
    @Override
    public void update() {
        x += .09f;
        getSprite().setScale(1/66f + (float) (Math.cos(x * .3f) / 600));
        getSprite().setRotation((float) (Math.cos(x) * 10));
    }



    protected Body body;
    @Override
    public void destroy() {
        if (items.removeValue(this, true))
            world.destroyBody(body);
    }

    public void setBody(Body b) {body = b;}
    public Body getBody() {return body;}

    private float speed = 0.01f;
    public void setSpeed(float speed) {this.speed = speed;}
    public float getSpeed() {return speed;}
    public abstract void beCollected(Vector2 pos);

}
