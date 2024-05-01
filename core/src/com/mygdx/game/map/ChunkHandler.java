package com.mygdx.game.map;

import static com.mygdx.game.Main.getPlayerPos;
import static com.mygdx.game.entity.mob.MobileEntity.getMobInstance;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.mob.player.PHuman;
import com.mygdx.game.util.FastNoiseLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles the world generation
 */
public class ChunkHandler extends Entity {
    private static List<Chunk> chunks = new ArrayList<>();
    private static float[][] noiseData;
    private static float[] minMax = new float[] {100, 0, 0};
    int i;
    private FastNoiseLite noise;
    public ChunkHandler() {
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

        setTexture(new Texture("chunk.png"));
        getSpriteList().add(new Sprite(getTexture()));

        getSpriteList().add(new Sprite(new Texture("grass.png")));
        getSpriteList().get(4).setScale(1/33f);



        noise = new FastNoiseLite();
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        noise.SetFrequency(.020f);

        // Gather noise data
        i = 100;
        noiseData = new float[i+1][i+1];
        for (int x = 0; x < i + 1; x++)
        {
            for (int y = 0; y < i + 1; y++)
            {
                noiseData[x][y] = noise.GetNoise(x, y);
                if (minMax[0] > noise.GetNoise(x, y))
                    minMax[0] = noise.GetNoise(x, y);
                else if (minMax[1] < noise.GetNoise(x, y))
                    minMax[1] = noise.GetNoise(x, y);

            }
        }
        minMax[2] = (minMax[1] - Math.abs(minMax[0])) / 2;



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

        for (Chunk c : chunks) {
            getSpriteList().get(3).setCenter(c.getPosition().x, c.getPosition().y);
            getSpriteList().get(3).draw(spriteBatch);
            for (int y = 0; y < 15; y++) {
                for (int x = 0; x < 15; x++) {
                    if (noise.GetNoise(c.getBlock(x, y).x, c.getBlock(x, y).y) > minMax[1] * .2)
                        getSpriteList().get(4).setCenter(c.getBlock(x, y).x, c.getBlock(x, y).y);
                        getSpriteList().get(4).draw(spriteBatch);
                }
            }
        }

    }

    /**
     * Loads the chunks around the player
     */
    public void loadChunks() {
        Vector2 cp = getMobInstance(PHuman.class, 1).getChunkPosition(15);
        for (Chunk c : chunks) {
            if (cp == c.getPosition())
                c.loadChunk(true);
        }
    }


    public static float[][] getNoiseData() {return noiseData;}
    public static float[] getMinMax() {return minMax;}
}



/**
 * 15 x 15 Chunk
 */
class Chunk {
    private Vector3[][] blocks = new Vector3[15][15];
    private Vector2 position;
    private boolean active = false;
    private Sprite sprite;

    public Chunk(Vector2 pos) {
        position = pos;
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                blocks[y][x] = new Vector3(position.x + x - 7, position.y + y - 7, 0);
            }
        }
    }

    public void loadChunk(boolean b) {
        if (b) {

        } else {

        }
    }


    public Vector2 getPosition() {return position;}
    public Vector3[][] getBlocks() {return blocks;}
    public Vector3 getBlock(int x, int y) {return blocks[x][y];}

}

