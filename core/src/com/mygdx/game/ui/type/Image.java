package com.mygdx.game.ui.type;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ui.UI;
import com.mygdx.game.util.shaders.PixelShader;

import static com.mygdx.game.Main.*;
import static com.mygdx.game.util.Settings.WIN_HEIGHT;
import static com.mygdx.game.util.Settings.WIN_WIDTH;
import static com.mygdx.game.util.shaders.PixelShader.pixelShader;

public class Image extends UI {
    private Sprite sprite;

    public Image(String filename, Vector2 pos, float scale) {
        sprite = new Sprite(textureAtlas.findRegion(filename));
        sprite.setCenter(pos.x, pos.y);
        sprite.setScale(scale);
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, WIN_WIDTH, WIN_HEIGHT, false);

    }

    protected Texture newTexture;
    protected Sprite newSprite;
    @Override
    public void render(int layer) {
        spriteBatch.begin();
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

}
