package com.mygdx.game.entity.obj.resourceblock;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entity.obj.shape.Disk;

import static com.mygdx.game.Main.spriteBatch;
import static com.mygdx.game.Main.textureAtlas;

public class Tree extends Disk {
    public Tree(float x, float y) {
        super(x, y, .4f, BodyDef.BodyType.StaticBody, .1f);
        setSprite(new Sprite(textureAtlas.createSprite("tree")));
        getSprite().setScale(1/33f);
        getSprite().setCenter(getPosition().x, getPosition().y);
    }

    @Override
    public void render() {
        getSprite().draw(spriteBatch);
    }

}
