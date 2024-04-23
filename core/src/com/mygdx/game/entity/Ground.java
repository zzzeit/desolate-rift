package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.util.FastNoiseLite;

public class Ground extends Entity {
    private float[][] noiseData;
    private float[] minMax = new float[] {100, 0, 0};
    int i;
    public Ground() {
        setSSR(0);
        setTexture(new Texture("concrete.png"));
        getTextureRegionList().add(new TextureRegion(getTexture(), getSSR() * (1f / 3), 0f, ((getSSR() + 1) / 3f), 1 / 4f));
        getSpriteList().add(new Sprite(getTextureRegionList().get(0)));
        getSpriteList().get(0).setScale(1 / 33f);

        setSSR(1);
        getTextureRegionList().add(new TextureRegion(getTexture(), getSSR() * (1f / 3), 0f, ((getSSR() + 1) / 3f), 1 / 4f));
        getSpriteList().add(new Sprite(getTextureRegionList().get(1)));
        getSpriteList().get(1).setScale(1 / 33f);

        setSSR(2);
        getTextureRegionList().add(new TextureRegion(getTexture(), getSSR() * (1f / 3), 0f, ((getSSR() + 1) / 3f), 1 / 4f));
        getSpriteList().add(new Sprite(getTextureRegionList().get(2)));
        getSpriteList().get(2).setScale(1 / 33f);

        FastNoiseLite noise = new FastNoiseLite();
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        noise.SetFrequency(.09f);
        noise.SetSeed((int)(Math.random() * 100));

// Gather noise data
        noiseData = new float[128][128];

        i = 100;
        for (int x = 0; x < i + 1; x++)
        {
            for (int y = 0; y < i + 1; y++)
            {
                System.out.printf("Noise Data: [%f]\n", noise.GetNoise(x, y));
                noiseData[x][y] = noise.GetNoise(x, y);
                if (minMax[0] > noise.GetNoise(x, y))
                    minMax[0] = noise.GetNoise(x, y);
                else if (minMax[1] < noise.GetNoise(x, y))
                    minMax[1] = noise.GetNoise(x, y);

            }
        }
        minMax[2] = (minMax[1] - Math.abs(minMax[0])) / 2;
        System.out.println(minMax[2]);
    }

    public void draw(SpriteBatch spriteBatch) {
        for (int x = -(i/2); x < (i/2) + 1; x++)
            for (int y = -(i/2); y < (i/2) + 1; y++) {
                if (noiseData[x + (i/2)][y + (i/2)] < minMax[2]) {
                    getSpriteList().get(0).setCenter(x, y);
                    getSpriteList().get(0).draw(spriteBatch);
                }
                else if (noiseData[x + (i/2)][y + (i/2)] > minMax[2]) {
                    getSpriteList().get(2).setCenter(x, y);
                    getSpriteList().get(2).draw(spriteBatch);

                }
            }
    }

}
