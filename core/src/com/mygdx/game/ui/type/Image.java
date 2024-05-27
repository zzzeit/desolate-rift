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

    float x = 0, y = 0.1f, m = .1f;
    Texture newTexture;
    Sprite newSprite;
    @Override
    public void render(int layer) {
        x += 0.01f;
        sprite.rotate((float) (10 * (Math.cos(x) * .5f)));
        frameBuffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
        spriteBatch.begin();
        sprite.draw(spriteBatch);
        spriteBatch.end();
        frameBuffer.end();

        newTexture = frameBuffer.getColorBufferTexture();
        newSprite = new Sprite(newTexture);
        newSprite.setOriginCenter();
        newSprite.setCenter(0, 0);
//        newSprite.setScale(3f);

        spriteBatch.setShader(pixelShader);
        spriteBatch.begin();
//        sprite.draw(spriteBatch);
        newSprite.draw(spriteBatch);

        spriteBatch.end();
//        sprite = new Sprite(newTexture);
        spriteBatch.setShader(null);
//        PixelShader.updPixSize(y);
//        y += m;
//        if (y > 20f)
//            m *= -1;
//        else if (y < 0.1f)
//            m *= -1;


    }
}
