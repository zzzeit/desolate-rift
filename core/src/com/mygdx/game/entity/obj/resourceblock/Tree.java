package com.mygdx.game.entity.obj.resourceblock;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entity.obj.shape.Disk;

import static com.mygdx.game.Main.*;

public class Tree extends Disk {
    public Tree(float x, float y) {
        super(x, y, .25f, BodyDef.BodyType.StaticBody, .1f);
        getSpriteList().add(new Sprite(textureAtlas.createSprite("tree1-5")));
        getSpriteList().add(new Sprite(textureAtlas.createSprite("tree1-3")));
        getSpriteList().get(0).setScale(1/33f);
        getSpriteList().get(0).setCenter(getPosition().x, getPosition().y);
        getSpriteList().get(1).setScale(1/33f);
        getSpriteList().get(1).setCenter(getPosition().x, getPosition().y);
    }

    private float var = random.nextFloat(0f, 180f), str = .2f;  // max str = .6f
    @Override
    public void render(int layer) {
        if (inCameraFrustum(getSpriteList().get(0))) {
//            if (var > 360)
//                var = 0;
            if (layer == 5) {
                var += .02f;
                getSpriteList().get(0).draw(spriteBatch);
                getSpriteList().get(0).setCenter((float) (getPosition().x + (Math.cos(var) * str)), (float) (getPosition().y + (Math.cos(var) * str )));
            } else if (layer == 6) {
                getSpriteList().get(1).draw(spriteBatch);
                getSpriteList().get(1).setCenter((float) (getPosition().x + (Math.cos(var) * (str + (str * .5f)))), (float) (getPosition().y + (Math.cos(var) * (str + (str * .5f)))));
            }
        }
    }

}
