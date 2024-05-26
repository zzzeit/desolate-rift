package com.mygdx.game.map.maps;

import static com.mygdx.game.Main.getTextureAtlas;
import static com.mygdx.game.Main.spriteBatch;
import static com.mygdx.game.util.FastNoiseLite.FractalType.FBm;
import static com.mygdx.game.util.FastNoiseLite.NoiseType.OpenSimplex2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.obj.BlockEntity;
import com.mygdx.game.entity.obj.grounds.*;
import com.mygdx.game.entity.obj.resourceblock.Tree;
import com.mygdx.game.map.Map;

import java.nio.channels.spi.SelectorProvider;

public class Plains extends Map {

    public Plains(int x, int y) {
        super(x, y);
        boundSprite.setScale(1/33f);
        boundSprite2.setScale(1/33f);
    }

    Vector2 pos;
    @Override
    public void genMap() {
        proceduralGeneration();
        int[][] m = getGroundData();
        for (int y = 0; y < getHEIGHT(); y++)
            for (int x = 0; x < getWIDTH(); x++) {
                pos = new Vector2(x - getWIDTH() / 2, y - getHEIGHT() / 2);
                if (m[y][x] == Grass) {
                    // Grass Decorator
                    if (grassDecorator[y][x] == 1)
                        Ground.instantiate(new Grass(pos, 1));
                    else if (grassDecorator[y][x] == 2)
                        Ground.instantiate(new Grass(pos, 2));
                    else if (grassDecorator[y][x] == 3)
                        Ground.instantiate(new Grass(pos, 3));
                    else if (grassDecorator[y][x] == 4)
                        Ground.instantiate(new Grass(pos, 4));
                    else
                        Ground.instantiate(new Grass(pos));

                    // Tree
                    if (natureBlocks[y][x] == Tree)
                        BlockEntity.create(new Tree(pos.x, pos.y));

                } else if (m[y][x] == Sand)
                    Ground.instantiate(new Sand(pos));
                else if (m[y][x] == Water)
                    Ground.instantiate(new Water(pos));
                else if (m[y][x] == Cobblestone)
                    Ground.instantiate(new Cobblestone(pos));
                else if (m[y][x] == DeepWater)
                    Ground.instantiate(new DeepWater(pos));
            }

    }

    Sprite boundSprite = getTextureAtlas().createSprite("bound1"), boundSprite2 = getTextureAtlas().createSprite("bound2");
    @Override
    public void renderBounds() {
        for (int y = 0; y < getHEIGHT(); y++)
            for (int x = 0; x < getWIDTH(); x++) {
                pos = new Vector2(x - getWIDTH() / 2, y - getHEIGHT() / 2);
                if (x == 0 && y == 0) {
                    boundSprite2.setRotation(90);
                    boundSprite2.setCenter(pos.x, pos.y);
                    boundSprite2.draw(spriteBatch);
                } else if (x == getWIDTH()-1 && y == 0) {
                    boundSprite2.setRotation(180);
                    boundSprite2.setCenter(pos.x, pos.y);
                    boundSprite2.draw(spriteBatch);
                } else if (y == getHEIGHT() - 1) {
                    boundSprite.setRotation(0);
                    boundSprite.setCenter(pos.x, pos.y);
                    boundSprite.draw(spriteBatch);
                } else if (y == 0) {
                    boundSprite.setRotation(180);
                    boundSprite.setCenter(pos.x, pos.y);
                    boundSprite.draw(spriteBatch);
                } else if (x == getWIDTH() - 1) {
                    boundSprite.setRotation(270);
                    boundSprite.setCenter(pos.x, pos.y);
                    boundSprite.draw(spriteBatch);
                } else if (x == 0) {
                    boundSprite.setRotation(90);
                    boundSprite.setCenter(pos.x, pos.y);
                    boundSprite.draw(spriteBatch);
                }
            }

    }


    private float[][] grassDecorator;
    private float[][] natureBlocks = new float[getHEIGHT()][getWIDTH()];
    @Override
    protected void proceduralGeneration() {
        modifyNoise(OpenSimplex2, 0.001f, FBm, 5, 1.390f, 5.71f, 3.11f);
        updateNoiseData();
        float[][] nD = getNoiseData();
        int w = getWIDTH(), h = getHEIGHT();
        for (int y = 0; y < h; y++)
            for(int x = 0; x < w; x++)
                if (nD[y][x] < (getNoiseHighLow()[0] * .6f))
                    setGroundData(x, y, Grass);
                else if (nD[y][x] > getNoiseHighLow()[0] * .8f)
                    setGroundData(x, y, DeepWater);
                else if (nD[y][x] > (getNoiseHighLow()[0] * .68f))
                    setGroundData(x, y, Water);
                else if (nD[y][x] > (getNoiseHighLow()[0] * .62f))
                    setGroundData(x, y, Cobblestone);
                else if (nD[y][x] > (getNoiseHighLow()[0] * .6f))
                    setGroundData(x, y, Sand);

        // Grass decorator
        modifyNoise(OpenSimplex2, 0.5f, FBm, 3, 2.68f, 1.94f, 3.66f);
        updateNoiseData();
        nD = getNoiseData();
        grassDecorator = new float[h][w];
        for (int y = 0; y < h; y++)
            for(int x = 0; x < w; x++)
                if (nD[y][x] > (getNoiseHighLow()[0] * .12f))
                    grassDecorator[y][x] = 1;
                else if (nD[y][x] > (getNoiseHighLow()[0] * .1f))
                    grassDecorator[y][x] = 2;
                else if (nD[y][x] > (getNoiseHighLow()[0] * .09f))
                    grassDecorator[y][x] = 3;
                else if (nD[y][x] > (getNoiseHighLow()[0] * .087f))
                    grassDecorator[y][x] = 4;

        // Nature Blocks
        for (int y = 0; y < h; y++)
            for(int x = 0; x < w; x++)
                if (nD[y][x] > (2))
                    natureBlocks[y][x] = Tree;

    }
}
