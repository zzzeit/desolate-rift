package com.mygdx.game.ui.UIDesign;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ui.Design;
import com.mygdx.game.ui.type.Image;

import static com.badlogic.gdx.Gdx.graphics;
import static com.mygdx.game.Main.frameBuffer;
import static com.mygdx.game.Main.spriteBatch;
import static com.mygdx.game.util.shaders.PixelShader.pixelShader;

public class Game extends Design {
    private Vector2 buttonPos = new Vector2(-graphics.getWidth() / 2 + 70, graphics.getHeight() / 2 - 70);

    public Game() {
//        addUI(new Image("clock", new Vector2(-graphics.getWidth()/2 + 70, graphics.getHeight()/2 - 70), 3));
        addUI(new Image("clock", new Vector2(0, 0), 3.4f) {
            float x = 0;
            @Override
            public void render(int layer) {
                x += 0.01f;
                getSprite().rotate((float) (10 * (Math.cos(x) * .5f)));
                frameBuffer.begin();
                Gdx.gl.glClearColor(0, 0, 0, 0);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                spriteBatch.begin();
                getSprite().draw(spriteBatch);
                spriteBatch.end();
                frameBuffer.end();
//
                newTexture = frameBuffer.getColorBufferTexture();
                newSprite = new Sprite(newTexture);
                newSprite.setCenter(buttonPos.x, buttonPos.y);
                newSprite.flip(false, true);
//
                spriteBatch.setShader(pixelShader);
                spriteBatch.begin();
                newSprite.draw(spriteBatch);
//                getSprite().draw(spriteBatch);
                spriteBatch.end();
                spriteBatch.setShader(null);
//                super.render(layer);
            }

        });
        addUI(new Image("clock_border", buttonPos, 3f) {
            @Override
            public void render(int layer) {
                super.render(layer);
            }
        });

        addUI(new Image("clock_pointer", buttonPos, 3f));
    }

    Image i;
    @Override
    protected void update() {
//        i = (Image) getUI(1);
//        i.getSprite().rotate(1);
    }
}
