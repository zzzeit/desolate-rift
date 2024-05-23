package com.mygdx.game.map;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.util.FastNoiseLite;
import org.lwjgl.Sys;

import static com.mygdx.game.Main.random;

public abstract class Map implements  BlockList {
    private final int WIDTH, HEIGHT;
    private int[][] map;

    private final int seed = random.nextInt(-9999, 9999);
    private FastNoiseLite noise = new FastNoiseLite();
    private final float[][] noiseData;
    private final float[] noiseHighLow = {-1000, 1000};

    public Map(int w, int h) {
        WIDTH = w;
        HEIGHT = h;
        map = new int[HEIGHT][WIDTH];
        for (int row = 0; row < HEIGHT; row++)
            for (int col = 0; col < WIDTH; col++)
                map[row][col] = 0;

        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        noise.SetSeed(seed);
        noise.SetFrequency(0.001f);
        noise.SetFractalType(FastNoiseLite.FractalType.FBm);
        noise.SetFractalOctaves(5);
        noise.SetFractalLacunarity(1.390f);
        noise.SetFractalGain(5.71f);
        noise.SetFractalWeightedStrength(3.11f);

        noiseData = new float[WIDTH][HEIGHT];
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

    /**
     * Modifies the {@link Map#map}
     */
    protected abstract void proceduralGeneration();

    // SETTER
    public void setMap(int[][] data) {map = data;}
    public void setMap(int x, int y, int v) {map[y][x] = v;}

    // GETTER
    public int[][] getMap() {return map;}
    public int getSeed() {return seed;}
    public float[][] getNoiseData() {return noiseData;}
    public float[] getNoiseHighLow() {return noiseHighLow;}
    public int getWIDTH() {return WIDTH;}
    public int getHEIGHT() {return HEIGHT;}
}
