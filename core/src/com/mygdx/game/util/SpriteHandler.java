package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class SpriteHandler {
    public static List<myTex> textures = new ArrayList<>();

    public static void addTexture(String filepath) {textures.add(new myTex(filepath));}

}

class myTex {
    private static int num = 0;
    private Texture t;

    public myTex(String filepath) {
        num++;
        t = new Texture(filepath);
    }

}