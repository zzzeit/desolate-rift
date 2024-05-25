package com.mygdx.game.entity.obj.grounds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.obj.BlockEntity;

import static com.mygdx.game.Main.*;

public abstract class Ground extends BlockEntity {
    private Vector2 position;
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

    protected boolean spriteInCameraFrustum;
    @Override
    public void render(int layer) {
        if (inCameraFrustum(getSprite()))
            spriteInCameraFrustum = true;
        else
            spriteInCameraFrustum = false;

        if (spriteInCameraFrustum)
            getSprite().draw(spriteBatch);
    }

}
