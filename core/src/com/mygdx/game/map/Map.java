package com.mygdx.game.map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.util.FastNoiseLite;

import static com.mygdx.game.Main.*;

public abstract class Map implements  BlockList {
    public static int[] mapSize = new int[2];
    private final int WIDTH, HEIGHT;
    private int[][] groundData;

    private final int seed = random.nextInt(-9999, 9999);
//    private final int seed = 0;
    private FastNoiseLite noise = new FastNoiseLite();
    private final float[][] noiseData;
    private final float[] noiseHighLow = {-1000, 1000};

    public Map(int w, int h) {
        WIDTH = w;
        HEIGHT = h;
        groundData = new int[HEIGHT][WIDTH];
        noiseData = new float[WIDTH][HEIGHT];
        for (int row = 0; row < HEIGHT; row++)
            for (int col = 0; col < WIDTH; col++)
                groundData[row][col] = 0;

        noise.SetSeed(seed);

        mapSize[0] = WIDTH;
        mapSize[1] = HEIGHT;
    }

    public void modifyNoise(FastNoiseLite.NoiseType noiseType, float frequency, FastNoiseLite.FractalType fractalType, int fractalOctaves, float fractalLacunarity, float fractalGain, float fractalWeightedStrength) {
        noise.SetNoiseType(noiseType);
        noise.SetFrequency(frequency);
        noise.SetFractalType(fractalType);
        noise.SetFractalOctaves(fractalOctaves);
        noise.SetFractalLacunarity(fractalLacunarity);
        noise.SetFractalGain(fractalGain);
        noise.SetFractalWeightedStrength(fractalWeightedStrength);
    }

    public void updateNoiseData() {
        for (int y = 0; y < HEIGHT; y++)
            for(int x = 0; x < WIDTH; x++) {
                noiseData[y][x] = noise.GetNoise(x, y);
                if (noiseData[y][x] > noiseHighLow[0])
                    noiseHighLow[0] = noiseData[y][x];
                else if (noiseData[y][x] < noiseHighLow[1])
                    noiseHighLow[1] = noiseData[y][x];
            }
    }

    public abstract void genMap();

    public abstract void renderBounds();

    /**
     * Modifies the {@link Map#groundData}
     */
    protected abstract void proceduralGeneration();

    // SETTER
    public void setGroundData(int[][] data) {groundData = data;}
    public void setGroundData(int x, int y, int v) {groundData[y][x] = v;}
    public void setNoise(FastNoiseLite noise) {this.noise = noise;}
    public void setSeed(int seed) {noise.SetSeed(seed);}

    // GETTER
    public int[][] getGroundData() {return groundData;}
    public int getSeed() {return seed;}
    public FastNoiseLite getNoise() {return noise;}
    public float[][] getNoiseData() {return noiseData;}
    public float[] getNoiseHighLow() {return noiseHighLow;}
    public int getWIDTH() {return WIDTH;}
    public int getHEIGHT() {return HEIGHT;}
}
