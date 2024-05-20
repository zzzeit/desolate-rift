package com.mygdx.game.map;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.util.FastNoiseLite;
import static com.mygdx.game.Main.random;

public abstract class Map implements  BlockList {
    private final int WIDTH, HEIGHT;
    private int[][] map;

    private final int[] seed = {random.nextInt(-9999, 9999), random.nextInt(-9999, 9999)};
    private FastNoiseLite noise = new FastNoiseLite();
    private int[][] noiseData;

    public Map(int w, int h) {
        WIDTH = w;
        HEIGHT = h;
        map = new int[HEIGHT][WIDTH];
        for (int row = 0; row < HEIGHT; row++)
            for (int col = 0; col < WIDTH; col++)
                map[row][col] = 0;

        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
    }

    public abstract void genMap();
    protected abstract void proceduralGeneration();

    // SETTER
    public void setMap(int[][] data) {map = data;}

    // GETTER
    public int[][] getMap() {return map;}
    public int[] getSeed() {return seed;}
}
