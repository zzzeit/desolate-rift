package com.mygdx.game.map.maps;

import static com.mygdx.game.entity.obj.blocks.MetalBox.instantiate;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entity.obj.grounds.Grass;
import com.mygdx.game.entity.obj.grounds.Ground;
import com.mygdx.game.entity.obj.grounds.Sand;
import com.mygdx.game.entity.obj.grounds.Water;
import com.mygdx.game.map.Map;

public class Plains extends Map {

    public Plains(int x, int y) {
        super(x, y);
        proceduralGeneration();

    }

    @Override
    public void genMap() {
        int[][] m = getMap();
        for (int y = 0; y < getHEIGHT(); y++)
            for (int x = 0; x < getWIDTH(); x++)
                if (m[y][x] == 1)
                    Ground.instantiate(new Grass(new Vector2(x, y)));
                else if (m[y][x] == 2)
                    Ground.instantiate(new Sand(new Vector2(x, y)));
                else if (m[y][x] == 3)
                    Ground.instantiate(new Water(new Vector2(x, y)));
    }

    @Override
    protected void proceduralGeneration() {
        float[][] nD = getNoiseData();
        int w = getWIDTH(), h = getHEIGHT();
        for (int y = 0; y < h; y++)
            for(int x = 0; x < w; x++)
                if (nD[y][x] < (getNoiseHighLow()[0] * .6f))
                    setMap(x, y, 1);
                else if (nD[y][x] > (getNoiseHighLow()[0] * .68f))
                    setMap(x, y, 3);
                else if (nD[y][x] > (getNoiseHighLow()[0] * .6f))
                    setMap(x, y, 2);


    }
}
