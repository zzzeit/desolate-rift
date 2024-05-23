package com.mygdx.game.map;

import com.badlogic.gdx.math.Vector3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for structures that holds block positions
 * <p>
 * Each block will use the central position and angle to offset their respective position. The size of the canvas must be an odd integer and is a shape of square.
 * <br> The variable {@link #blockpositions} is a 2D array Vector 3 thus, the format is (x, y, BLOCKTYPE) where BLOCKTYPE is an int from {@link BlockList}.
 * </p>
 */
class Structure implements BlockList, Serializable {
    private static List<com.mygdx.game.entity.obj.blocks.MetalBox> structure = new ArrayList<>();
    private Vector3[][] blockpositions;
    private int[] position = new int[2];
    private int angle = 0, canvas_size, hCanvasSize;

    /**
     * This constructor will generate block data from the 2D map array argument and store it to {@link Structure#blockpositions}.
     *
     * @param x   X of central position
     * @param y   Y of central position
     * @param a   Angle of the structure
     * @param map A map 2D array of integers from {@link BlockList}
     */
    public Structure(int x, int y, int a, int[][] map) {
        position[0] = x;
        position[1] = y;
        angle = a;
        canvas_size = map.length;
        hCanvasSize = canvas_size / 2;
        blockpositions = new Vector3[canvas_size][canvas_size];
        for (int Y = 0; Y < canvas_size; Y++) {
            for (int X = 0; X < canvas_size; X++) {
                blockpositions[Y][X] = new Vector3(X - hCanvasSize, Y - hCanvasSize, map[Y][X]);
            }
        }
    }

    /**
     * This will flip the structure horizontally or vertically
     */
    public void flip(boolean hor, boolean ver) {
        for (int Y = 0; Y < canvas_size; Y++) {
            for (int X = 0; X < canvas_size; X++) {
                if (hor)
                    blockpositions[Y][X] = new Vector3(blockpositions[Y][X].x * -1, blockpositions[Y][X].y, blockpositions[Y][X].z);
                if (ver)
                    blockpositions[Y][X] = new Vector3(blockpositions[Y][X].x, blockpositions[Y][X].y * -1, blockpositions[Y][X].z);
            }
        }
    }

    // setter
    // getter
    public Vector3[][] getBlockpositions() {
        return blockpositions;
    }

}
