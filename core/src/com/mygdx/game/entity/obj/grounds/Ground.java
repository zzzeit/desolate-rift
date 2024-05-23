package com.mygdx.game.entity.obj.grounds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.obj.BlockEntity;

import static com.mygdx.game.Main.spriteBatch;

public abstract class Ground extends BlockEntity {
    private Vector2 position;
    public static TextureAtlas textureAtlas = new TextureAtlas("./pack/ground.atlas");
    public static void instantiate(Ground g) {
        grounds.add(g);
    }
    public static Sprite[] sprites = new Sprite[0];
    public Ground(String image, float resolution, Vector2 pos) {
        setSprite(textureAtlas.createSprite(image));
        getSprite().setScale(1 / resolution);
        getSprite().setCenter(pos.x, pos.y);
        position = pos;

    }


    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public void render() {
        getSprite().draw(spriteBatch);
    }

}
