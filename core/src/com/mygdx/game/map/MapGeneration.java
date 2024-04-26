package com.mygdx.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.util.FastNoiseLite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.Main.spriteBatch;
import static com.mygdx.game.map.Ground1.generateGrounds;
import static com.mygdx.game.map.Ground1.grounds;

public class MapGeneration extends Entity {
    private float[][] noiseData;
    private float[] minMax = new float[] {100, 0, 0};
    int i;
    Ground1 g1;
    public MapGeneration() {
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


        g1 = new Ground1(32, 32);
        generateGrounds();
        try {
            StructureManager.create("house1");
            StructureManager.load("house1");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }

// Gather noise data
        i = 50;
        noiseData = new float[i+1][i+1];


        for (int x = 0; x < i + 1; x++)
        {
            for (int y = 0; y < i + 1; y++)
            {
//                System.out.printf("Noise Data: [%f]\n", noise.GetNoise(x, y));
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
                    getSpriteList().get(1).setCenter(x, y);
                    getSpriteList().get(1).draw(spriteBatch);
                }
            }

        for (Ground1 g : grounds)
            g.draw();
    }

}

class Ground1 extends Entity {
    private static final int HORIZONTAL = 0, VERTICAL = 1;
    private static int MAP_WIDTH = 1, GROUNDSIZE = 51, starPos = (62 * (MAP_WIDTH/2));
    private int angle = 0;
    private Vector2 position;
    public static List<Ground1> grounds = new ArrayList<>();

    public static void generateGrounds() {
        for (int y = -starPos; y < starPos + 13; y += GROUNDSIZE + 13) {
            for (int x = -starPos; x < starPos + 13; x += GROUNDSIZE + 13) {
                grounds.add(new Ground1(x - 1, y - 1));
                System.out.println("X[" + x + "]    Y[" + y + "]");

            }
        }
//        grounds.add(new Ground1(0, -51 - 13));
//        grounds.add(new Ground1(-51 - 13, -51 - 13));
    }


    Ground1(int x, int y) {
        position = new Vector2(x, y);
        setTexture(new Texture("ground.png"));
        setSSR(0);
        getTextureRegionList().add(new TextureRegion(getTexture(), getSSR() * (1f / 3), 0f, ((getSSR() + 1) / 3f), 1f));
        getSpriteList().add(new Sprite(getTextureRegionList().get(0)));
        getSpriteList().get(0).setScale(1f);
    }

    public void draw() {
        getSpriteList().get(0).setCenter(position.x, position.y);
        getSpriteList().get(0).draw(spriteBatch);
    }


}

