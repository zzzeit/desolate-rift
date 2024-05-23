package com.mygdx.game.entity.obj.grounds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.obj.BlockEntity;

import static com.mygdx.game.Main.getTextureAtlas;
import static com.mygdx.game.Main.spriteBatch;

public class Grass extends Ground {
    public int decorator = 0;
    public Sprite decoratorSprite;
    public Grass(Vector2 pos) {
        super("grass", 33f, pos);
    }
    public Grass(Vector2 pos, int decorator) {
        super("grass", 33f, pos);
        this.decorator = decorator;
    }

    @Override
    public void render() {
        super.render();
        if (decorator != 0)
            if (decorator == 1) {
                setDecoratorSprite("smallSprite1", getPosition(), 1/33f);
                decoratorSprite.draw(spriteBatch);
            } else if (decorator == 2) {
                setDecoratorSprite("smallSprite2", getPosition(), 1/33f);
                decoratorSprite.draw(spriteBatch);
            } else if (decorator == 3) {
                setDecoratorSprite("smallSprite3", getPosition(), 1/33f);
                decoratorSprite.draw(spriteBatch);
            } else if (decorator == 4) {
                setDecoratorSprite("smallSprite4", getPosition(), 1/33f);
                decoratorSprite.draw(spriteBatch);
            }

    }

    private void setDecoratorSprite(String imagename, Vector2 pos, float scale) {
        decoratorSprite = textureAtlas.createSprite(imagename);
        decoratorSprite.setCenter(pos.x, pos.y);
        decoratorSprite.setScale(scale);
    }


}
